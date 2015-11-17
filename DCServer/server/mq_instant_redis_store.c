#include "dc_global.h"


static   zctx_t * g_redis_ctx = NULL;
redisContext *g_redis_context[16] = {NULL};
static void * mq_redis_store_server_socket = NULL;
static void * mq_redis_store_client_socket = {NULL};
pthread_mutex_t pvredis;
//ÎÄ¼þÖ¸Õë

void mq_redis_store_client_socket_init()
{
    pthread_mutex_init(&pvredis, NULL);
    g_redis_ctx = zmq_init(1);
    mq_redis_store_client_socket = zmq_socket(g_redis_ctx, ZMQ_PUSH);
    zmq_bind(mq_redis_store_client_socket, "inproc://zmq_outside_redis_store_inproc_mq");
}

void mq_redis_store_server_socket_init()
{
    mq_redis_store_server_socket = zmq_socket(g_redis_ctx, ZMQ_PULL);
    zmq_connect(mq_redis_store_server_socket, "inproc://zmq_outside_redis_store_inproc_mq");
    printf("tengda redis store %p ,%p : %s\n", g_redis_ctx ,mq_redis_store_server_socket, "inproc://zmq_outside_redis_store_inproc_mq");
}

void * outside_redis_store_server_thread(void *args)
{
    printf("outside redis_store_server_thread started ... \n");
    mq_redis_store_server_socket_init();
    while (1)
    {
		char msg[4096] = {0};
        	int nRecvLen = zmq_recv(mq_redis_store_server_socket, msg, 4096, 0);
        	if(-1 == nRecvLen)
        	{
			continue;
		}

		if(NULL == msg) 
		{
			continue;
		}
		printf("tengda msg: %s\n", msg);
        	if(redis_store(msg, nRecvLen)<0)
		{
			continue;
		}
    }
}

void redis_store_send_msg(char * msg)
{
    pthread_mutex_lock(&pvredis);
    zmq_send(mq_redis_store_client_socket, msg, strlen(msg), 0);
    pthread_mutex_unlock(&pvredis);
}

int  redis_store(char* msg, int len)
{
		if(NULL  == msg)
		{
			return -1;
		}
		int i = 0;
                char *kbuf=msg;
                char *token[20] ;
                while((token[i++] = strsep (&kbuf,"::"))!=NULL);
                if(strlen(token[0]) == 0||strlen(token[2]) == 0||strlen(token[4]) == 0)
                {
                        return -1;
                }
		char *buf = token[4];
		char *p[40];
		int in = 0;
		char *outer_ptr=NULL;
		char *inner_ptr=NULL;
		while((p[in]=strtok_r(buf,",",&outer_ptr))!=NULL) {
			//buf=p[in];
			//while((p[in]=strtok_r(buf,",",&inner_ptr))!=NULL) {
				in++;
			//	buf = NULL;
			//}
		buf = NULL;
		}
                char szADUA[1024] = {0};
                strcpy(szADUA, token[0]);
                strcat(szADUA, token[2]);
		for(i=0; i < g_redis_list_len; i++)
		{
			int h = 0;
			for(; h < in; h++)
			{
				char command[2048] = {0};
				//sprintf(command,"hset AdUa_Aid %s %s", szADUA, token[4]);
				sprintf(command,"sadd %s %s", szADUA, p[h]);
				printf("%s \n", command);
				redisReply *reply = redisCommand(g_redis_context[i],"sadd %s   %s", szADUA, p[h]);
				if (NULL == reply) {
					printf("redis [%s] store failed\n",command );
					freeReplyObject(reply);
					continue;
				}
				if((REDIS_ERR == reply) && (strcasecmp(g_redis_context[i]->err,REDIS_ERR_EOF) == 0))
				{
					printf("redis [%s] store failed\n",command );
					freeReplyObject(reply);
					continue;
				}
				freeReplyObject(reply);
			}
		}
		return 0;
}

void load_redis_init()
{
	int i = 0;
        for(i=0; i < g_redis_list_len; i++)
        {
		char ip[16] = {0};
		int port = 0;
		int db = -1 ;
		char* kbuf = instant_redis_list[i];
		printf("%s\n", kbuf);
		char* p[20] = {NULL};
		char * outer_ptr = NULL;
		int in = 0;
		while((p[in]=strtok_r(kbuf,"|",&outer_ptr))!=NULL)
		{
			in++;
			kbuf=NULL;
		}
		int j = 0;
		for(; j< in; j++)
		{
			printf("p[%d] : %s\n",j, p[j]);	
			switch(j)
			{
			case 0:
				strcpy(ip, p[j]);	
				break;
			case 1:
				port = atoi(p[j]);
				break;
			case 2:
				db = atoi(p[j]);
				break;
			}
		}
		printf("ip : %s, port : %d, db : %d\n", ip, port, db);
		g_redis_context[i] = redisConnect(ip, port);		
		if (g_redis_context[i]->err) {
			printf("load redis : %s:%d failed\n", ip,port);
			continue;
		}
		char db_select[16] = {0};
		sprintf(db_select, "select %d", db);
		redisReply *reply = redisCommand(g_redis_context[i], db_select);
		if((REDIS_ERR == reply) && (strcasecmp(g_redis_context[i]->err,REDIS_ERR_EOF) == 0))
		{
			printf("Failed to execute command[select %d]\n", db);
			freeReplyObject(reply);
			continue;
		}
	}
}

