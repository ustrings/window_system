#include "data_pass_back.h"
extern HashMap* g_pTargetIP[THREAD_NUM];
extern HashMap* g_pTargetIP1[THREAD_NUM];
extern HashMap* g_pSurplusIP[THREAD_NUM];
extern HashMap* g_pSurplusAD[THREAD_NUM];
extern HashMap* g_pFilterConditionIP[THREAD_NUM];
extern HashMap* g_pFilterConditionAD[THREAD_NUM];
extern unsigned int hash_map_hash_value(char* start, int len);
extern void count_send_redis_msg(const char * msg, int iTid);

struct pass_back* create_pass_back()
{
	struct pass_back* pass = (struct pass_back*)malloc(sizeof(struct pass_back));
	if(NULL == pass){return NULL;}
	pass->destory_pass_back = destory_pass_back;
	pass->context = zctx_new();
	pass->pvSender = zsocket_new(pass->context, ZMQ_PULL);
	zsocket_bind(pass->pvSender, g_conf_pass_back_adr);

	printf("pass back info : %s\n", g_conf_pass_back_adr);
	return pass;
}

int curr_time()
{
	time_t timep;
	time(&timep);
	return timep;
}

void* get_data_pass_back( struct pass_back* pass )
{
    while(1)
    {
        char packet[1024] = {0}; 
        int nRecvLen = zmq_recv(pass->pvSender, packet, 1024, 1);  
        if(-1 != nRecvLen)
        {   
		//printf("%s\n", packet); 
                char *buf=packet;
		char *token[20] ;
		int i = 0;
		while((token[i++] = strsep (&buf,"::"))!=NULL);
		if(strlen(token[0]) == 0||strlen(token[2]) == 0||strlen(token[4]) == 0||strlen(token[6]) ==0)
		{
			continue;
		}
		if(strstr(token[4], "wispr") != NULL || strstr(token[4], "spider")!=NULL)
		{
			continue;
		}
		// ip:ad:ua:type
		char sIp[16] = {0};
		strcpy(sIp, token[0]); 
                char szADUA[1024] = {0}; 
                strcpy(szADUA, token[2]);
                strcat(szADUA, token[4]);
		int type = atoi(token[6]);
		//int iTid = hash_map_hash_value(sIp, strlen(sIp))%16; 
		unsigned int iTid = hash_map_hash_value(token[2], strlen(token[2]))%16;
		printf("PASSBACK: thread[%d] %s--%s--%s--%d\n", iTid, sIp, token[2], token[4], type);
                //int* iCount = NULL;
                FilterUserInfo* pUserInfo = NULL;
                if(type == 2)
                {   
			int iRes = -1;
                       	iRes = hash_map_find(g_pTargetIP[iTid], szADUA, strlen(szADUA), (long*)&pUserInfo);
                        if(iRes >= 0)
                        {  	
				//if(pUserInfo->iFactTime == 0)
				{
					pUserInfo->iFactCount++;
					pUserInfo->iFactTime = curr_time();
				}
                        }    
                }   
                else if(type == 3)
                {
                        int iRes = -1;
                        iRes = hash_map_find(g_pTargetIP1[iTid], szADUA, strlen(szADUA), (long*)&pUserInfo);
                        if(iRes >= 0)
                        {
                                //if(pUserInfo->iFactTime == 0)
                                {
                                        pUserInfo->iFactCount++;
                                        pUserInfo->iFactTime = curr_time();
                                }
                        }
                } 
                else if( type == 0 )
                {    
                        int iRes = -1; 
		        long * count = NULL;
		        iRes = hash_map_find(g_pSurplusAD[iTid], token[2], strlen(token[2]),(long*)&count);
		        if(iRes >= 0)
		        {   
		               (*count)++;
		        }   
		        else
		        {   
		               count = (long*)malloc(sizeof(long));
		              (*count) = 0;
		              (*count)++;
		               hash_map_insert(g_pSurplusAD[iTid], token[2], strlen(token[2]),(long*)count);
		        }    
		        iRes = -1;	
                       	iRes = hash_map_find(g_pSurplusIP[iTid], szADUA, strlen(szADUA), (long*)&pUserInfo);
                        if (iRes >= 0)
                        {  
				//if(pUserInfo->iFactTime == 0)
				{  
                               		pUserInfo->iFactCount++;
					pUserInfo->iFactTime = curr_time();
				}
				char szMsg[128] = {0};
				sprintf(szMsg, "hset surplus %s %d::%d::%s::%d", szADUA, pUserInfo->iFactCount, pUserInfo->iFactTime, token[2], *count);
				count_send_redis_msg(szMsg, iTid);
                        }   
                }
		else if( type == 4 )
		{
                        int iRes = -1; 
		        long * count = NULL;
		        iRes = hash_map_find(g_pFilterConditionAD[iTid], token[2], strlen(token[2]),(long*)&count);
		        if(iRes >= 0)
		        {   
		               (*count)++;
		        }   
		        else
		        {   
		               count = (long*)malloc(sizeof(long));
		              (*count) = 0;
		              (*count)++;
		               hash_map_insert(g_pFilterConditionAD[iTid], token[2], strlen(token[2]),(long*)count);
		        }    
		        iRes = -1;	
	        	FilterUserInfo* pUserInfo = NULL;
        		iRes = hash_map_find(g_pFilterConditionIP[iTid], szADUA, strlen(szADUA), (long*)&pUserInfo);
			if(iRes >= 0)
			{
				//if(pUserInfo->iFactTime == 0)
				{
					pUserInfo->iFactCount++;
					pUserInfo->iFactTime = curr_time();
				}
				char szMsg[128] = {0};
				sprintf(szMsg, "hset crowd %s %d::%d::%s::%d", szADUA, pUserInfo->iFactCount, pUserInfo->iFactTime, token[2], *count);
				count_send_redis_msg(szMsg, iTid);
			}
		}
	}
    }
}

void destory_pass_back( struct pass_back* pass )
{
	zctx__socket_destroy(pass->context, pass->pvSender);
	zctx_destroy(&pass->context);
	pthread_join(pass->pass_thread, NULL);	
}

