#include "dc_global.h"
#include "redis_manager.h"

static   zctx_t * g_ctx = NULL;
static void * mq_redis_server_socket;
static void * mq_redis_client_socket;
pthread_mutex_t store_redis_lock;
static redisContext* g_conn = NULL;
extern HashMap* g_pSurplusIP[THREAD_NUM];
extern HashMap* g_pSurplusAD[THREAD_NUM];

extern HashMap* g_pFilterConditionIP[THREAD_NUM];
extern HashMap* g_pFilterConditionAD[THREAD_NUM];

extern unsigned int hash_map_hash_value(char* start, int len);

int redis_init()
{
	g_conn = ConnRedis("127.0.0.1" , 6380);
	if(NULL == g_conn)
	{
		return -1;
	}
	return 1;
}

//key: ADUA, value : adua实际打击次数::ad::ad实际打击次数
void load_all_hash(char* msg, int type)
{
	redisReply *reply = redisCommand(g_conn, msg);
	if(NULL != reply && reply->type == REDIS_REPLY_ARRAY)
	{
		int i =0 ;
		char szBuff[128] = {0};
		for(; i< reply->elements; i++)
		{
			if((i % 2) ==0)
			{
				memset(szBuff,'\0', 128);
				strcpy(szBuff, reply->element[i]->str);
			}
			else
			{

                                char *buf=reply->element[i]->str;
                                char *token[20] ;
                                int i = 0;
                                while((token[i++] = strsep (&buf,"::"))!=NULL);
                                if(strlen(token[0]) == 0||strlen(token[2]) == 0||strlen(token[4]) == 0)
                                {
                                       continue;
                                }
				unsigned int iTid = hash_map_hash_value(token[4], strlen(token[4]))%16;
				//printf("[adua : %s ,打击次数: %d, 上次打击成功时间 : %d] -- [ad : %s, 打击次数: %d] , iTid : %d\n", szBuff , atoi(token[0]), atoi(token[2]), token[4], atoi(token[6]), iTid);
				int iRes = -1;
				if(type == 0)
				{
					long * count = NULL;
					iRes = hash_map_find(g_pSurplusAD[iTid], token[4], strlen(token[4]),(long*)&count);
					if(iRes < 0)
					{
						count = (long*)malloc(sizeof(long));
						(*count) = 0;
						(*count) = atoi(token[6]);
						hash_map_insert(g_pSurplusAD[iTid], token[4], strlen(token[4]),(long*)count);
					}
					FilterUserInfo* pUserInfo = NULL;
					iRes = -1;
					iRes = hash_map_find(g_pSurplusIP[iTid], szBuff, strlen(szBuff), (long*)&pUserInfo);
					if (iRes < 0)
					{
						pUserInfo = (FilterUserInfo*)malloc(sizeof(FilterUserInfo));
        					memset(pUserInfo, 0, sizeof(FilterUserInfo));
        					pUserInfo->iFactCount = atoi(token[0]);
						pUserInfo->iFactTime = atoi(token[2]);
        					hash_map_insert(g_pSurplusIP[iTid], szBuff, strlen(szBuff), (long*)pUserInfo);
					}
				}
				else if(type == 1)
				{
					
					long * count = NULL;
					iRes = hash_map_find(g_pFilterConditionAD[iTid], token[4], strlen(token[4]),(long*)&count);
					if(iRes < 0)
					{
						count = (long*)malloc(sizeof(long));
						(*count) = 0;
						(*count) =  atoi(token[6]);
						hash_map_insert(g_pFilterConditionAD[iTid], token[4], strlen(token[4]),(long*)count);
					}
					FilterUserInfo* pUserInfo = NULL;
					iRes = -1;
					iRes = hash_map_find(g_pFilterConditionIP[iTid], szBuff, strlen(szBuff), (long*)&pUserInfo);
					if (iRes >= 0)
					{
						pUserInfo = (FilterUserInfo*)malloc(sizeof(FilterUserInfo));
        					memset(pUserInfo, 0, sizeof(FilterUserInfo));
        					pUserInfo->iFactCount = atoi(token[0]);
						pUserInfo->iFactTime = atoi(token[2]);
        					hash_map_insert(g_pFilterConditionIP[iTid], szBuff, strlen(szBuff), (long*)pUserInfo);
					}
				}
			}
		}
	}
}

void mq_redis_store_client_init()
{
    	pthread_mutex_init(&store_redis_lock, NULL);
	if(redis_init() > 1)
	{
		printf("[ERROR] : redis store surplus count failed\n");
		return ;
	}
    	g_ctx = zmq_init(1);
    	mq_redis_client_socket = zmq_socket(g_ctx, ZMQ_PUSH);
    	zmq_bind(mq_redis_client_socket, "inproc://mq_count_redis_store");
    	printf( "redis store client connect -- inproc://mq_count_redis_store \n");
	load_all_hash("hgetall surplus", 0);
	load_all_hash("hgetall crowd", 1);
}

void mq_redis_store_server_init()
{
    	mq_redis_server_socket = zmq_socket(g_ctx, ZMQ_PULL);
    	zmq_connect(mq_redis_server_socket, "inproc://mq_count_redis_store");
    	printf("redis store server bind : inproc://mq_count_redis_store \n");
}

void * redis_store_server_thread(void *args)
{
    	printf("count redis store server thread started ... \n");
    	mq_redis_store_server_init();
    	while (1)
    	{
		char msg[4096] = {0};
        	int nRecvLen = zmq_recv (mq_redis_server_socket, msg, 4096, 0);
        	if(-1 == nRecvLen)
        	{
			continue;
		}
		redisReply *reply = redisCommand(g_conn, msg);
		if((REDIS_ERR == reply) && (strcasecmp(g_conn->err,REDIS_ERR_EOF) == 0))
		{
			freeReplyObject(reply);
			continue;
		}
    	}
	closeRedis(g_conn);
}

void count_send_redis_msg(const char * msg, int iTid)
{
    	pthread_mutex_lock(&store_redis_lock);
    	zmq_send(mq_redis_client_socket, msg, strlen(msg), 0);
    	pthread_mutex_unlock(&store_redis_lock);
}

