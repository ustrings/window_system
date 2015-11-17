#define _GNU_SOURCE
#include "dc_global.h"

char g_currTime[16][64] = {0};
extern int http_parser_level_1(int thread_id, struct pfring_pkthdr * hdr, const u_char *buffer, struct http_request_kinfo *http);
extern int http_parser_level_2(struct pfring_pkthdr * hdr, const u_char *buffer, struct http_request_kinfo *httprequest);
extern int http_parser_level_3(struct http_request_kinfo *httprequest, char *str, struct pfring_pkthdr *hdr);

extern int rule_pop_main_loop(const int thread_id, const u_char *buffer, const struct pfring_pkthdr* hdr,
        const struct http_request_kinfo * http);
extern int rule_jc_main_loop(const  int thread_id, const u_char *buffer, const struct pfring_pkthdr* hdr,
        const struct http_request_kinfo * http);
extern int rule_record_url_main_loop(const  int thread_id, const u_char *buffer, const struct pfring_pkthdr* hdr,
        const struct http_request_kinfo * http);

extern int user_clean_main(const  int thread_id, const struct http_request_kinfo * http, const char * json_str);
extern int user_clean_time_out(int thread_id);
extern void * user_clean_alloc_json_mem(int tid, int len);

extern int tcp_reassemble_period_time_out(int thread_id);
extern int tcp_reassemble_check(const  int thread_id, const u_char *buffer, const struct pfring_pkthdr* hdr,
        const struct http_request_kinfo * http, const char * json_str);

extern void rule_record_url_load_url();

static int http_cap_filter_check_period(int thread_id, long second)
{
    if(g_period_last_time[thread_id] == 0)
    {
        g_period_last_time[thread_id] = second;
        return -1;
    }
    else if(second - g_period_last_time[thread_id] > 10)
    {
        /*update the queue*/
        user_clean_time_out(thread_id);

        if(g_conf_enable_tcp_reassemble)
            tcp_reassemble_period_time_out(thread_id);

        g_period_last_time[thread_id] = second;
        return 1;
    }
    return -1;
}

/*main filter loop*/
int http_cap_filter_main(const int thread_id, const u_char *buffer, const struct pfring_pkthdr* hdr)
{
	if(NULL == buffer)
	{
		return -1;
	}
    int ret = 0;
    struct http_request_kinfo http;
	/*memset(g_currTime[thread_id], 0, 64);
    struct timeval start1;
    gettimeofday(&start1,0);
    sprintf(g_currTime[thread_id],"%ld",((long)start1.tv_sec*1000+(long)start1.tv_usec/1000));*/

    //1、parser http offset
    ret = http_parser_level_1(thread_id, hdr, buffer, &http);
    if(ret != 0)
    {
        //DBG("level 1 error: %d\n", ret);
        return ret;
    }

    //2、parser http header
    ret = http_parser_level_2(hdr, buffer, &http);
    if(ret != 0)
    {
        //DBG("level 2 error: %d\n", ret);
        return ret;
    }

    if(hdr->extended_hdr.parsed_pkt.tunnel.tunnel_id != NO_TUNNEL_ID)
    {
        g_rx_pkt_gre_num[thread_id]++;
    }
    else
    {
        g_rx_pkt_http_num[thread_id]++;
    }

	rule_pop_main_loop(thread_id, buffer, hdr, &http);

	return 0;
}




