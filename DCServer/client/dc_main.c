#define _GNU_SOURCE
#include <signal.h>
#include <string.h>

#include "dc_global.h"
#include "context_monitor.h"
#include "mthread.h"
#define ALARM_SLEEP             5
int g_debug_info = 0;

extern int http_cap_dna_start(const char * device);
extern int http_cap_zero_start(const char * device);
extern int http_cap_offline_start(const char * device);

extern int rule_jc_init();
extern int rule_pop_init();
extern int user_clean_init();
extern int tcp_reassemble_init();

extern void load_dc_cfg();
extern void rule_record_url_init();

int g_iPopTempNum[THREAD_NUM] = {0};
int g_curr_ip_time = 0;
struct monitor* g_monitor;
/*
* print cmd line format
*/
void printHelp(void) {
	printf("dc capture(C) 2013-2014 caoli\n\n");
	printf("-h               Print this help\n");
	printf("-i <device>      Device name (No device@channel), and dna:ethX for DNA\n");
	printf("-m <watermark>   set watermark\n");
	printf("-n <channel_num> set channel_num\n");
	printf("-g <gateway> set mq gateway addr for storm\n");
	printf("-p <gateway> set mq gateway addr for pop windows\n");
}

/*
* get cmd line parameters
*/


void init_args(int argc, char *argv[])
{
	//   char c;
	//   while((c = getopt(argc,argv,"hi:n:m:g:p:")) != -1)
	//   {
	//    switch(c) {
	//    case 'h':
	//      printHelp();
	//      return;
	//    case 'i':
	//      g_cap_device = strdup(optarg);
	//      break;
	//    case 'm':
	//      g_watermark = atoi(optarg);
	//      break;
	//    case 'n':
	//      g_total_channel_num = atoi(optarg);
	//      break;
	//    case 'g':
	//      g_storm_mq_gateway = strdup(optarg);
	//    case 'p':
	//      g_pop_mq_gateway = strdup(optarg);
	//      break;
	//    }
	//  }
}

/*
* compute the period of now time and before time
*/
double dc_delta_time (struct timeval * now, struct timeval * before)
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
	return((double)(delta_seconds * 1000) + (double)delta_microseconds/1000);
}

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
	int iTotalIPNum = 0;

	/*first time in function*/
	if(is_first)
	{
		gettimeofday(&startTime, NULL);
		gettimeofday(&lastTime, NULL);
		is_first = 0;
		return;
	}

	/*get the curr period*/
	gettimeofday(&currTime, NULL);
	double curr_period_time = dc_delta_time(&currTime, &lastTime);
	int curr_ip_time = dc_time(&currTime, &lastTime);
	g_curr_ip_time += curr_ip_time;
	if((g_curr_ip_time/1000) % 600 == 0)
	{
		int i =0 ;
		for(; i< g_conf_capture_thread_num; i++)
		{
			iTotalIPNum += g_pTotalIP[i]->size;
		}
		printf(" ============================total_ip_num=%d\n", iTotalIPNum);
	}
	lastTime = currTime;

	/*total statics */
	double total_speed_pkts = 0;
	double total_speed_pkts_gre = 0;
	double total_speed_pkts_http = 0;
	double total_speed_send_pkts = 0;
	double total_speed_bytes = 0;
	double total_drop_pkts = 0;

	int i;
	for(i=0; i < g_conf_capture_thread_num; i++)
	{
		/*get channel statics*/
		pfring_stat pfringStat;
		if(pfring_stats(g_rx_rings[i], &pfringStat) < 0)
			continue;

		/*curr period pkts and bytes, speed of both*/
		unsigned long long curr_pkts = g_rx_pkt_num[i];
		unsigned long long curr_pkts_gre = g_rx_pkt_gre_num[i];
		unsigned long long curr_pkts_http = g_rx_pkt_http_num[i];
		unsigned long long curr_send_pkts = g_rx_pkt_send_num[i];
		unsigned int	   curr_ipua_num = g_pTotalIP[i]->size;
		unsigned long long curr_bytes = g_rx_pkt_byte[i];
		unsigned long long curr_drop_num = pfringStat.drop - g_pkt_drop_num[i];

		g_rx_pkt_num[i] = 0;
		g_rx_pkt_byte[i] = 0;
		g_rx_pkt_send_num[i] = 0;
		g_rx_pkt_gre_num[i] = 0;
		g_rx_pkt_http_num[i] = 0;
		g_pkt_drop_num[i] = pfringStat.drop;

		double speed_pkts  =  (double)(curr_pkts*1000)/curr_period_time;
		double speed_pkt_gre  =  (double)(curr_pkts_gre*1000)/curr_period_time;
		double speed_pkt_http  =  (double)(curr_pkts_http*1000)/curr_period_time;
		double speed_send_pkts  =  (double)(curr_send_pkts*1000)/curr_period_time;
		double speed_bytes   = ((double)8*curr_bytes)/(curr_period_time*1000);
		double dDropPackets = (double)(curr_drop_num*1000)/curr_period_time;

		fprintf(stdout, "=========================\n"
			"channel=%d, bytes speed=%.2fM, pkts speed=%0.1f, raw speed=%0.1f/%0.1f, send speed=%0.1f, drop pkts=%u \n",
			i,  speed_bytes, speed_pkts, speed_pkt_gre, speed_pkt_http, speed_send_pkts,(unsigned int)dDropPackets);

		/*save to total statics*/
		total_speed_pkts  += speed_pkts;
		total_speed_send_pkts  += speed_send_pkts;
		total_speed_bytes += speed_bytes;
		total_speed_pkts_gre += speed_pkt_gre;
		total_speed_pkts_http += speed_pkt_http;
		total_drop_pkts   += dDropPackets;
	}

	fprintf(stdout,
		"total channel, bytes speed=%.2fM, pkts speed=%0.1f, raw speed=%0.1f/%0.1f, send speed=%0.1f, drop pkts=%u \n",
		total_speed_bytes, total_speed_pkts, total_speed_pkts_gre, total_speed_pkts_http, total_speed_send_pkts,
		(unsigned int)total_drop_pkts);

	printf("\ng_iPopNum");
	int iTotal = 0;
	for (i = 0; i < THREAD_NUM; i++)
	{
		iTotal += (g_iPopNum[i] - g_iPopTempNum[i]);
		printf(" num%d=%d", i, g_iPopNum[i] - g_iPopTempNum[i]);
	}
	printf(" totalNum=%d\n", iTotal);
}

/*
* the alarm function for exsit.
*/
void signal_exist_func()
{
	static int called = 0;
	int i;

	fprintf(stderr, "receive leaving alarm ...\n");

	/*check reentrant*/
	if(called)
		return;
	else
		called = 1;

	g_is_shutdown = 1;

	/*shut down pfring*/
	for(i=0; i<g_conf_capture_thread_num; i++)
		pfring_shutdown(g_rx_rings[i]);

	/*shut down thread*/
	for(i=0; i<g_conf_capture_thread_num; i++) {
		pthread_join(g_cap_thread[i], NULL);
		pfring_close(g_rx_rings[i]);
	}

#ifndef ENABLE_POP_WINDOWS
	if(dna_cluster_handle)
		dna_cluster_destroy(dna_cluster_handle);
#endif


	exit(0);
}

/*
* the alarm function for logging.
*/
void signal_logging_func()
{
	static int time = 0;
	time++;
	if (g_is_shutdown)
		return;
	dc_print_stats();
	if(time%60 == 0)
	{

	}
	alarm(ALARM_SLEEP);
	signal(SIGALRM, signal_logging_func);

	int i = 0;
	for (; i < THREAD_NUM; i++)
	{
		g_iPopTempNum[i] = g_iPopNum[i];
	}
}

void conf_monitor_init()
{
	g_monitor = create_monitor() ;
	g_monitor->monitor_init( g_monitor );
}

/*
* dc capture, capture http pkts and send to storm
*/
int main(int argc, char *argv[])
{
	int ret;
	init_args(argc, argv);

	load_dc_cfg();

	/*init tcp reassemble*/
	if(g_conf_enable_tcp_reassemble)
		tcp_reassemble_init();

	/*init http pkt clean*/
	if(g_conf_record_url)
		rule_record_url_init();

	/*init http pkt clean*/
	if(g_conf_enable_http_clean)
		user_clean_init();


	conf_monitor_init();	
	rule_pop_init();


        struct mthread* thread = new_thread();
	thread->start_thread(thread, g_monitor);

	//begin capture pkt
	if(strcmp(g_conf_capture_type, "file") == 0)
	{
		printf("use file capture.\n");
		g_conf_capture_thread_num = 1;
		if ((ret=http_cap_offline_start(g_conf_cap_device)) != 0)
		{
			return ret;
		}
	}
	else if(strcmp(g_conf_capture_type, "zero") == 0)
	{
		printf("use zero capture.\n");
		if ((ret=http_cap_zero_start(g_conf_cap_device)) != 0)
		{
			return ret;
		}
	}
	else
	{
		printf("use dna capture.\n");
		if ((ret=http_cap_dna_start(g_conf_cap_device)) != 0)
		{
			return ret;
		}
	}

	//register the alarm of exist signal
	signal(SIGINT,  signal_exist_func);
	signal(SIGTERM, signal_exist_func);
	signal(SIGINT,  signal_exist_func);

	//wake up print log
	signal(SIGALRM, signal_logging_func);
	alarm(ALARM_SLEEP);

	//wait all threads
	int i;
	for(i=0; i<g_conf_capture_thread_num; i++)
		pthread_join(g_cap_thread[i], NULL);

        thread->end_thread(thread);
	g_monitor->destroy_monitor(g_monitor);
	return 0;
}
