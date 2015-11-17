#include "dc_global.h"


extern void mq_online_client_init();

static   zctx_t * g_ctx = NULL;
static void * mq_http_server_socket;
static void * mq_http_client_socket[MAX_NUM_THREADS];
//ÎÄ¼þÖ¸Õë
HashMap * g_pFileNameHashMap = NULL;
pthread_mutex_t online_write_lock;
pthread_mutex_t online_send_lock;


typedef struct  
{
        FILE* lmqStream;
        char createTime[32];
}KFileType;

void online_file_name(char * prefix, char * result) 
{
      struct timeb tp;
      struct tm * tm;
      ftime ( &tp );
      tm = localtime (   & ( tp.time )   );

      sprintf(result, "%s%s_%d%02d%02d%02d.txt", RECORD_HTTP_PATH, prefix, tm->tm_year+1900, tm->tm_mon+1, tm->tm_mday, tm->tm_hour);
}


int online_write_file(FILE* lmqStream, const char* file_name,const char * start, int len)
{
	if(lmqStream == NULL)
	{
		lmqStream = fopen(file_name, "at+");
	}
	fwrite(start, len, 1, lmqStream);
	fwrite("\n", 1, 1, lmqStream);
	fflush(lmqStream);
	return 0;
}

void online_file_cache_put(const char * str_msg, int len)
{
	KFileType * KFCStream = NULL;
	char fileName[256] = {0};
	char* front = strstr(str_msg, "###");
	char prefix[24] = {0};
	strncpy(prefix, str_msg, front - str_msg);
        online_file_name(prefix, fileName);
        
	if(hash_map_find(g_pFileNameHashMap, prefix, strlen(prefix), (long*)&KFCStream) < 0)
        {       
                char ctime[32] = {0};
                struct tm *ptr = NULL;
                time_t lt;
                lt = time(NULL);
                ptr = localtime(&lt);
                strftime(ctime, sizeof(ctime), "%Y-%m-%d %H", ptr);

                KFCStream = (KFileType*)malloc(sizeof(KFileType));
                memset(KFCStream, 0 , sizeof(KFileType));
                KFCStream->lmqStream = fopen(fileName, "at+");
                strcpy(KFCStream->createTime , ctime);
                if(NULL == KFCStream->lmqStream)
                {
                        return;
                }
                hash_map_insert(g_pFileNameHashMap, prefix, strlen(prefix), (long*)KFCStream);
        }       
        else    
        {   
                char s[32] = {0};
                struct tm *ptr = NULL;
                time_t lt;
                lt = time(NULL);
                ptr = localtime(&lt);
                strftime(s, sizeof(s), "%Y-%m-%d %H", ptr);
                if(strcmp(s, KFCStream->createTime) > 0 )
                {
                        memset(fileName, 0 , 256);
                        online_file_name(prefix, fileName);
                        fclose(KFCStream->lmqStream);
                        KFCStream->lmqStream = NULL;
                        KFCStream->lmqStream = fopen(fileName, "at+");
                        memset(KFCStream->createTime, 0, 32);
                        strcpy(KFCStream->createTime , s);
                        if(NULL == KFCStream->lmqStream)
                        {
                                return;
                        }
                        hash_map_insert(g_pFileNameHashMap, prefix, strlen(prefix), (long*)KFCStream);
                }    
        }

	online_write_file(KFCStream->lmqStream, fileName, strstr(str_msg, "###") + 3, len);
}

void mq_http_client_socket_init()
{
    g_ctx = zmq_init(1);
    int i = 0; 
    for(; i < MAX_NUM_THREADS; i++)
    {
	    mq_http_client_socket[i] = zmq_socket(g_ctx, ZMQ_PUSH);
            zmq_bind(mq_http_client_socket[i], "inproc://zmq_http_inproc_mq");
    }
    //mq_http_client_socket = zmq_socket(g_ctx, ZMQ_PUSH);
    //zmq_connect(mq_http_client_socket, "inproc://zmq_http_inproc_mq");
}

void mq_http_server_socket_init()
{
    mq_http_server_socket = zmq_socket(g_ctx, ZMQ_PULL);
    zmq_connect(mq_http_server_socket, "inproc://zmq_http_inproc_mq");
}

void * http_file_server_thread(void *args)
{
    printf("http_file_server_thread started ... \n");
    mq_http_server_socket_init();
    while (1)
    {
		char msg[4096] = {0};
        int nRecvLen = zmq_recv (mq_http_server_socket, msg, 4096, 1);
        if(-1 == nRecvLen)
        {
			continue;
		}
		//printf("er liang msg: %s\n", msg);
        online_file_cache_put(msg, nRecvLen);
    }
}

void http_file_send_msg(const char * msg, int iTid)
{
    //pthread_mutex_lock(&online_send_lock);
    zmq_send(mq_http_client_socket[iTid], msg, strlen(msg), 1);
    //pthread_mutex_unlock(&online_send_lock);
}

void rule_crown_search_init()
{
	mq_http_client_socket_init();
	//online 
	mq_online_client_init();

	g_pFileNameHashMap = hash_map_new(100);
}
