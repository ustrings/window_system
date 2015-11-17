#define _GNU_SOURCE
#include <signal.h>
#include <string.h>
#include "zhelpers.h"
#include "dc_global.h"
#include "data_pass_back.h"
#include "context_monitor.h"
#include "filter_rule_jc_record.h"
#include "mthread.h"

#define ALARM_SLEEP 5
struct monitor* g_monitor;
int g_curr_ip_time = 0;

int g_iPopTempNum[16] = {0};
int g_iJCTempNum[16] = {0};

void* g_pvMqReceiver[MAX_NUM_THREADS] = {NULL};
void* g_pvMobileReceiver[MAX_NUM_THREADS];
void* g_pRadiusMqReceiver = NULL;

extern int rule_jc_init();
extern int rule_pop_init();
extern void mobile_client_init();
extern int rule_radius_init();
extern void pop_crowd_search_init();
extern int rule_one_pop_init();
extern int user_clean_init();
extern int tcp_reassemble_init();
extern void mq_instant_recv_init();
extern void* instant_count_record();
extern void load_dc_cfg();
extern void rule_record_url_init();
extern void* mq_message_analysis_thread_func(void* _id);
extern void* mq_radius_message_thread_func(void* context);
extern void* mq_message_analysis_mobile_func(void* _id);
extern void * outside_redis_store_server_thread(void *args);
extern void * redis_store_server_thread(void *args);
extern void signal_register();
extern void destory_mutex();
extern void * http_file_server_thread(void *args);
extern void initMainIPAreaFilter();
extern struct pass_back* g_pass;
extern HashMap* g_pTotalIP[MAX_NUM_THREADS];

/************************************************************************/
//  [8/8/2014 fushengli]                                               
//  初始化zmp接收器                                                                   
/************************************************************************/
static void server_main_receiver_pop_init()
{
	int i = 0;
	for (; i < MAX_NUM_THREADS; i++)
	{
		void *pvCtx = zmq_ctx_new();
		g_pvMqReceiver[i] = zmq_socket (pvCtx, ZMQ_PULL);
		char cNames[64] = {'\0'};
		sprintf(cNames, "%s%d" , "tcp://127.0.0.1:", (8110+i));
		//zmq_connect(g_pvMqReceiver[i], cNames);
		zmq_bind(g_pvMqReceiver[i], cNames);
		printf("server_main_receiver_pop_init pull tcp name=%s\n", cNames);
	}

	return;
}


static void server_main_receiver_mobile_init()
{
	int i = 0;
	for (; i < MAX_NUM_THREADS; i++)
	{
		void *pvCtx = zmq_ctx_new();
		g_pvMobileReceiver[i] = zmq_socket (pvCtx, ZMQ_PULL);
		char cNames[64] = {'\0'};
		sprintf(cNames, "%s%d" , "tcp://127.0.0.1:", (8210+i));
		zmq_bind(g_pvMobileReceiver[i], cNames);
		printf("server_main_receiver_mobile_init pull tcp name=%s\n", cNames);
	}

	return;
}

void server_main_receiver_radius_init()
{
        void * pvCtx = zmq_init(1);
        g_pRadiusMqReceiver = zmq_socket (pvCtx, ZMQ_SUB);
        zmq_setsockopt (g_pRadiusMqReceiver, ZMQ_SUBSCRIBE, "", 0);
        zmq_connect(g_pRadiusMqReceiver, g_conf_radius_recv_adr);
        printf("radius_mq_receive_init pull ipc name=%s\n", g_conf_radius_recv_adr);
        return;
}

/*
* compute the period of now time and before time
*/
int dc_time (struct timeval * now, struct timeval * before)
{
        time_t delta_seconds;
        time_t delta_microseconds;

        /*compute delta in second, 1/10's and 1/1000's second units*/
        delta_seconds      = now -> tv_sec  - before -> tv_sec;
        delta_microseconds = now -> tv_usec - before -> tv_usec;

        if(delta_microseconds < 0)
        {   
                /* manually carry a one from the seconds field */
                delta_microseconds += 1000000;  /* 1e6 */
                -- delta_seconds;
        }   
        return((delta_seconds * 1000) + delta_microseconds/1000);
}

/*
* print the packet statics log
*/
void dc_print_stats()
{
	static struct timeval startTime, currTime, lastTime;
	static int is_first = 1;
	int itotal_ipua_num = 0;
	/*first time in function*/
	if(is_first)
	{
		gettimeofday(&lastTime, NULL);
		is_first = 0;
		return;
	}

	gettimeofday(&currTime, NULL);
	int curr_ip_time = dc_time(&currTime, &lastTime);
	g_curr_ip_time += curr_ip_time;
    if((g_curr_ip_time/1000) % 600 == 0)
	{
        int i =0 ;
        for(; i< g_conf_capture_thread_num && NULL != g_pTotalIP[i]; i++)
        {
			itotal_ipua_num += g_pTotalIP[i]->size;
        }
		printf(" ============================total_ip_num=%d\n", itotal_ipua_num);

		g_jc_record_write_file();
    }
    lastTime = currTime;

	printf("\ng_pPopIpHashMap");
	int num = 0;
	int i = 0;
	for (i = 0; i < THREAD_NUM; i++)
	{
		num = 0;
		if (g_pPopIpHashMaps[i] != NULL)
		{
			num = g_pPopIpHashMaps[i]->size;
		}

		printf(" num%d=%d", i, num);
	}
	printf("\n");

	printf("\ng_iReceiverNum");
	int iTotal = 0;
	for (i = 0; i < THREAD_NUM; i++)
	{
		iTotal += (g_iReceiverNum[i] - g_iPopTempNum[i]);
		printf(" num%d=%d", i, g_iReceiverNum[i] - g_iPopTempNum[i]);
	}
	printf(" totalNum=%d\n", iTotal);

	printf("\ng_iJCSenderNum");
	iTotal = 0;
	int j = 0;
	for (j = 0; j < THREAD_NUM; j++)
	{
		iTotal += (g_iJCSenderNum[j] - g_iJCTempNum[j]);
		printf(" num%d=%d %d", j, g_iJCSenderNum[j] - g_iJCTempNum[j], g_iJCSenderNum[j]);
	}
	printf(" totalNum=%d\n", iTotal);
}

/*
* the alarm function for exsit.
*/
void signal_exist_func()
{
	static int called = 0;

	fprintf(stderr, "receive leaving alarm ...\n");

	/*check reentrant*/
	if(called)
		return;
	else
		called = 1;

	g_is_shutdown = 1;

	exit(0);
}

/*
* the alarm function for logging.
*/
void signal_logging_func()
{
	if (g_is_shutdown)
		return;

	dc_print_stats();

	alarm(ALARM_SLEEP);
	signal(SIGALRM, signal_logging_func);

	int i = 0;
	for (; i < 16; i++)
	{
		g_iPopTempNum[i] = g_iReceiverNum[i];
		g_iJCTempNum[i] = g_iJCSenderNum[i];
	}
}


void* print_thread_func(void* id)
{
	while(1)
	{
		dc_print_stats();
		int i = 0;
		for (; i < 16; i++)
		{
			g_iPopTempNum[i] = g_iReceiverNum[i];
			g_iJCTempNum[i] = g_iJCSenderNum[i];
		}

		sleep(10);
	}
}

void conf_monitor_init()
{
    g_monitor = create_monitor() ;
	g_monitor->monitor_init( g_monitor );
}

/************************************************************************/
//  [10/31/2014]                                               
//  打印程序的启动时间                                                                   
/************************************************************************/
void printStartTime()
{
	time_t timep;
	time (&timep);
	printf("the dcserver start time is  %s.", ctime(&timep));
}

/*
* dc capture, capture http pkts and send to storm
*/
int main(int argc, char *argv[])
{
	printStartTime();

	server_main_receiver_pop_init();

	initMainIPAreaFilter();	

	load_dc_cfg();

    /*init monitor*/
	conf_monitor_init();

	if(g_conf_enable_pop_mobile)
	{
		server_main_receiver_mobile_init();
		mobile_client_init();	
	}

	/*init http write file and people search*/
	if(g_conf_crowd_search)
		pop_crowd_search_init();

	/*init radius info*/
	if(g_conf_enable_radius_recv)
	    rule_radius_init();

	/*init http pkt clean*/
	if(g_conf_record_url)
		rule_record_url_init();

	/*init pop window*/
	if(g_conf_enable_pop_window)
		rule_pop_init();

	/*init pop jc*/
	if(g_conf_enable_jc)
		rule_jc_init();

	int ret = 0;
	int i = 0;

	/*register the alarm of exist signal*/
	signal(SIGINT,  signal_exist_func);
	signal(SIGTERM, signal_exist_func);
	signal(SIGINT,  signal_exist_func);

	//radius server use
	/*wake up print log*/
	//signal(SIGALRM, signal_logging_func);
	//alarm(ALARM_SLEEP);
	/*start confile monitor ...*/	
	struct mthread* thread = new_thread();
	thread->start_thread(thread, g_monitor);	

	pthread_t pRadius;
	pthread_t pfileWrite;
	pthread_t pRedisStore;
	pthread_t analysisThread[MAX_NUM_THREADS];
	pthread_t mobiletTread[MAX_NUM_THREADS];
	if(g_conf_enable_radius_recv)
	{
		ret = pthread_create(&pRadius, NULL, mq_radius_message_thread_func, g_pRadiusMqReceiver);
		if (ret != 0)  
		{  
			printf("Create pthread error!\n");  
			return -1;  
		}
	}

	ret = pthread_create(&pRedisStore, NULL, redis_store_server_thread, NULL);
	if(ret != 0)
	{
		printf("create pthread error\n");
		return -1;
	}

	pthread_t RedisThread;
	if(g_conf_crowd_search)
	{
		ret = pthread_create(&pfileWrite, NULL, http_file_server_thread, NULL);
		if (ret != 0)  
		{  
			printf("Create pthread error!\n");  
			return -1;  
		}
        	ret = pthread_create(&RedisThread, NULL, outside_redis_store_server_thread, NULL);
        	if (ret != 0)
        	{
                	printf("Create RedisThread error!\n");
                	return -1;
        	}	
	}

	pthread_t CrowdThread;
	if(g_conf_crowd_server)
	{
		mq_instant_recv_init();
        	ret = pthread_create(&CrowdThread, NULL, instant_count_record, NULL);
        	if (ret != 0)
        	{
                	printf("Create CrowdThread error!\n");
                	return -1;
        	}	
	}

	for (i = 0; i < MAX_NUM_THREADS; i++)
	{
		ret = pthread_create(&analysisThread[i], NULL, mq_message_analysis_thread_func, (void*)i);
		if (ret != 0)  
		{  
			printf("Create cap thread error!\n");  
			return -1;  
		}
	}

	if (g_conf_enable_pop_mobile)
	{
		for (i = 0; i < MAX_NUM_THREADS; i++)
		{
			ret = pthread_create(&mobiletTread[i], NULL, mq_message_analysis_mobile_func, (void*)i);
			if (ret != 0)  
			{  
				printf("Create cap thread error!\n");  
				return -1;  
			}
		}
	}
	
	pthread_t printThread;
	ret = pthread_create(&printThread, NULL, print_thread_func, NULL);
	if (ret != 0)  
	{  
		printf("Create printThread error!\n");  
		return -1;  
	}
	pthread_join(printThread, NULL);

	if(g_conf_enable_radius_recv)
	{
		pthread_join(pRadius, NULL);
	}

	if(g_conf_crowd_search)
	{
		pthread_join(pfileWrite, NULL);
	}
	if (g_conf_enable_pop_mobile)
	{
		for(i = 0; i < MAX_NUM_THREADS; i++)
		{
			pthread_join(mobiletTread[i], NULL);
		}
	}

	for(i = 0; i < MAX_NUM_THREADS; i++)
	{
		pthread_join(analysisThread[i], NULL);
	}
	pthread_join(pfileWrite, NULL);
	destory_mutex();

	//shutdown confile monitor
	thread->end_thread(thread);
	g_monitor->destroy_monitor(g_monitor);
	if(g_conf_crowd_server)
	{
		pthread_join(CrowdThread, NULL);
		pthread_join(RedisThread, NULL);
	}
	return 0;
}
