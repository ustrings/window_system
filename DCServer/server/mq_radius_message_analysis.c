#define _GNU_SOURCE
#include "dc_global.h"
#include "zmq.h"
#include "zhelpers.h"
#include "hiredis.h"
#include <math.h>

extern HashMap* hash_map_new(unsigned int length);
extern hash_map_size(HashMap* psHashMap);
extern int hash_map_insert(HashMap* psHashMap, char*start, int len, long* in_val);
extern int hash_map_find(HashMap* psHashMap, char*start, int len, long* out_val);
extern void server_main_receiver_radius_init();

#define RADIUS_IP_UA_LEN 8388608
HashMap* g_pRadiusHashMap = NULL;
static int radius_pid = 0;
static pthread_mutex_t redis_radius_mut;
extern pthread_mutex_t online_write_lock;
extern pthread_mutex_t online_send_lock;
pthread_rwlock_t adua_lock;
pthread_rwlock_t radius_lock;


void signal_register()
{
	int ret;
        ret = kill(radius_pid,SIGUSR1);
	if(ret == 0)
	{
		printf("SIGUSR1 signal send succeed\n");
	}
	else
	{
		printf("SIGUSR1 signa send failed\n");
	}
	exit(0);
}

int load_radius_redis_data_init()
{
	pthread_rwlock_init(&radius_lock, NULL);
	pthread_rwlock_init(&adua_lock, NULL);
	pthread_mutex_init(&redis_radius_mut , NULL);
	pthread_mutex_init(&online_write_lock, NULL);
	pthread_mutex_init(&online_send_lock, NULL);
	g_pRadiusHashMap = hash_map_new(RADIUS_IP_UA_LEN);
	char* buf = g_conf_redis_adr;
	char* p[20] = {NULL};
	char * outer_ptr = NULL;
	int in = 0;
	while((p[in]=strtok_r(buf,"|",&outer_ptr))!=NULL)
	{
		in++;
		buf=NULL;
	}
	int port = atoi(p[1]);
	redisContext *tool_reids_context = redisConnect(p[0], port);
	if (tool_reids_context->err) {
		return -1;
	}
	char db[16] = {0};
	sprintf(db, "select %s", p[2]);
	redisReply *reply = redisCommand(tool_reids_context, db);
	if((REDIS_ERR == reply) && (strcasecmp(tool_reids_context->err,REDIS_ERR_EOF) == 0))
	{
		printf("Failed to execute command[select 3]\n");
		freeReplyObject(reply);
		return -1;
	}
	
	reply = redisCommand(tool_reids_context, "hgetall cxxIPUA");
	if(NULL != reply && reply->type == REDIS_REPLY_ARRAY)
	{
		int i =0 ;
		char szBuff[512] = {0};
		for(; i< reply->elements; i++)
		{
			if((i % 2) ==0)
			{
				memset(szBuff,'\0', 512);
				strcpy(szBuff, reply->element[i]->str);
			}
			else
			{
				char* sRadiushash = NULL;
				if(0 > hash_map_find(g_pRadiusHashMap, szBuff, strlen(szBuff),(long*)&sRadiushash))
				{
					sRadiushash = (char*)malloc(128);
					memset(sRadiushash, 0, 128);
					memcpy(sRadiushash, reply->element[i]->str, strlen(reply->element[i]->str));
					hash_map_insert(g_pRadiusHashMap,szBuff, strlen(szBuff), (long*)sRadiushash);
				}
			}
		}
	}
	printf("radis number : %d\n", g_pRadiusHashMap->size);
	return 0;
}


/*
 * the function of capture packet
 */
void* mq_radius_message_thread_func(void* context)
{
	while (1) 
	{
		u_char packet[512] = {0};
		int nRecvLen = 0;
		nRecvLen = zmq_recv(context, packet, 512, 1);
		if(-1 != nRecvLen)
		{
			char * result = strtok(packet, ":");
			char sBuff[16] = {0};
			memcpy(sBuff, result, strlen(result));
			char sAd[496] = {0};
			result = strtok(NULL, ":");
			memcpy(sAd, result, strlen(result));

			char * ppcv = NULL;
			pthread_rwlock_rdlock(&radius_lock);
			int ret = hash_map_find(g_pRadiusHashMap, sBuff, strlen(sBuff), (long*)&ppcv);
			pthread_rwlock_unlock(&radius_lock);
			if(ret < 0)
			{
				ppcv = (char*)malloc(496);
				memset(ppcv, 0, 496);
				memcpy(ppcv, sAd, strlen(sAd));
				pthread_rwlock_wrlock(&radius_lock);
				hash_map_insert(g_pRadiusHashMap, sBuff, strlen(sBuff), (long*)ppcv);
				pthread_rwlock_unlock(&radius_lock);
				//free(ppcv);
			}
			else
			{
				if(strcmp(sAd , ppcv)!= 0 )
				{
					pthread_rwlock_wrlock(&radius_lock);
					memset(ppcv, 0, 496);
					memcpy(ppcv, sAd, strlen(sAd));
					pthread_rwlock_unlock(&radius_lock);
				}

			}
			//load_radius_ip_ua(g_pRadiusHashMap, packet, strlen(packet));	
		}
		else
		{
			continue;
		}
	}
    	return(NULL);
}

/*inti radius server*/
int rule_radius_init()
{
	/*init radius ip+ua to dc_server*/
	load_radius_redis_data_init();

	server_main_receiver_radius_init();
	
	return 0;
}


void destory_mutex()
{
	pthread_rwlock_destroy(&radius_lock);
	pthread_rwlock_destroy(&adua_lock);
	pthread_mutex_destroy(&redis_radius_mut);
	pthread_mutex_destroy(&online_write_lock);
	pthread_mutex_destroy(&online_send_lock);
}
