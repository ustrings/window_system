#include "dc_global.h"

void * g_mq_file_client[MAX_NUM_THREADS]   = { NULL };

static void * mq_file_server_socket;
static char  * mq_file_mem_pool;
static char  * mq_file_mem_pool_curr;
static int mem_pool_len = 0;
static int mem_pool_len_max = 10000;
static char g_fileName[256] = {'\0'};
static FILE * mqStream = NULL;
static pthread_mutex_t mq_write_lock;

extern int fast_write_file(const char* file_name,const char * start, int len);
extern void get_file_name(char * prefix, char * result);

void mq_file_cache_init()
{
    mq_file_mem_pool = malloc(mem_pool_len_max); //1K * 1000,000 = 10M
    mq_file_mem_pool_curr = mq_file_mem_pool;
    mem_pool_len = 0;
}

static int isMqDayChanged()
{
	static int day = 0;
	time_t timep;
	time(&timep);

	struct tm * tm;
	tm = localtime (&(timep));

	if(day != tm->tm_mday)
	{
		printf("tm->tm_mday=%d\n", day);
		day = tm->tm_mday;
		return 1;
	}
	return 0;
}

int mq_write_file(const char* file_name,const char * start, int len)
{
	if(mqStream == NULL)
	{
		mqStream = fopen(file_name, "at+");
	}
	fwrite(start, len, 1, mqStream);
	fwrite("\n", 1, 1, mqStream);
	fflush(mqStream);
	return 0;
}

void mq_file_cache_put(const char * str_msg, int len)
{
	pthread_mutex_lock(&mq_write_lock);
	if (isMqDayChanged())
	{
		if (mqStream)
		{
			fclose(mqStream);
			mqStream = NULL;
		}

		memset(g_fileName, 0, 256);
		get_file_name("urlrecord", g_fileName);
		printf("mq file name = %s\n", g_fileName);
	}

	mq_write_file(g_fileName, str_msg, len);
	pthread_mutex_unlock(&mq_write_lock);
}

void mq_file_socket_init()
{
    zctx_t * ctx = zctx_new ();
    mq_file_server_socket = zsocket_new (ctx, ZMQ_PULL);
    zsocket_bind (mq_file_server_socket, "inproc://mq_file_inproc_mq");

    int i= 0;
    for(; i<16; i++)
    {
        g_mq_file_client[i] = zsocket_new (ctx, ZMQ_PUSH);
        zsocket_connect (g_mq_file_client[i], "inproc://mq_file_inproc_mq");
    }
}

static void * mq_file_server_thread(void *args)
{
    printf("mq_file_server_thread started ... \n");

    while (1)
    {
        char *msg = zstr_recv (mq_file_server_socket);
        if(msg)
        {
            mq_file_cache_put(msg, strlen(msg));
        }
    }
}

void mq_file_send_msg(const char * msg, long tid)
{
    zframe_t *frame = zframe_new (msg, strlen(msg));
    zframe_send (&frame, g_mq_file_client[tid], 0);
}

void mq_file_thread_start()
{
    //初始化内存
    mq_file_cache_init();

    //初始化sokect
    mq_file_socket_init();

    //启动服务端
    zthread_new (mq_file_server_thread, 0);
}
