#include "dc_global.h"


#define SEARCH_AD  40960
HashMap* g_pSearchAdUaHashMap[THREAD_NUM] = {NULL};
void* g_pvInstantRecver = NULL;

extern unsigned int hash_map_hash_value(char* start, int len);

void mq_instant_recv_init()
{
	int i = 0;
	for(; i < THREAD_NUM; i++)
	{
		g_pSearchAdUaHashMap[i] = hash_map_new(SEARCH_AD);
	}

        void * ctx = zctx_new ();
    	g_pvInstantRecver = zsocket_new (ctx, ZMQ_PULL);
    	zsocket_bind(g_pvInstantRecver, g_conf_instant_adr);
    	printf("instant pop recv push %s, p=%d\n", g_conf_instant_adr, g_pvInstantRecver); 

}


void* instant_count_record()
{

    while(1)
    {
        char packet[1024] = {0}; 
        int nRecvLen = zmq_recv(g_pvInstantRecver, packet, 1024, 1);  
        if(-1 != nRecvLen)
        {   
		printf("instant record : %s\n", packet); 
                char *buf=packet;
		char *token[20] ;
		int i = 0;
		while((token[i++] = strsep (&buf,"::"))!=NULL);
		if(strlen(token[0]) == 0||strlen(token[2]) == 0)
		{
			continue;
		}
		// ad:ua
                char szADUA[1024] = {0}; 
                strcpy(szADUA, token[0]);
                strcat(szADUA, token[2]);
		unsigned int iTid = hash_map_hash_value(token[0], strlen(token[0]))%16;
		//char* KWord = NULL;
		int iRes = hash_map_find(g_pSearchAdUaHashMap[iTid], szADUA, strlen(szADUA), NULL);//(char*)&KWord);
		if (iRes < 0)
		{
			//KWord = (char*)malloc(sizeof(128));
			//memset(KWord, 0, sizeof(128));
			//memcpy(KWord, token[4], strlen(token[4]));
			hash_map_insert(g_pSearchAdUaHashMap[iTid], szADUA, strlen(szADUA), NULL);//(long*)KWord);
		}
	}

    }
}

