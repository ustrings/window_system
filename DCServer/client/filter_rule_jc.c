/*
 * jc rule
 * we check the host and uri, then make a response to the http client
 * there are 3 detail rules
 * 1. host uri matched
 * 2. ip filter matched
 * 3. time matched
 */

#include "dc_global.h"

/*global reference*/
extern int tool_hash_table_find(struct TOOL_HASH_NODE ** tool_hash_table, char *start, int len, long * out_val);
extern int tool_hash_table_insert(struct TOOL_HASH_NODE ** tool_hash_table, char *start, int len, long * in_val);
extern struct TOOL_HASH_NODE ** tool_hash_table_new();
extern void tool_hash_table_clear(struct TOOL_HASH_NODE ** tool_hash_table);

extern int load_jc_cfg(struct JC_S * jc_sites);
extern int load_jc_filer_ip_cfg(char * rule_jc_filter_ips[64]);

extern void pkt_sender_init();
extern void pkt_sender_send(char * redirect_url, int total_len,
        char src_mac[6], char dst_mac[6],u_int32_t src_ip, u_int32_t dst_ip,
        u_int16_t src_port, u_int16_t dst_port,u_int32_t seq, u_int32_t ack);

extern int isDayChanged(long curr);
extern void get_file_name(char * prefix, char * result);

/*mq init*/
extern void mq_jc_win_client_init(const char * gateway_begin, int num);
extern void mq_jc_win_sender_init();
extern void jc_mq_direct_send_msg(long tid, u_char *buffer, struct pfring_pkthdr * hdr, char * host_start, char * host_end, char * uri_start, char * uri_end);
extern void jc_mq_direct_send_msg_tunneled(long tid, u_char *buffer, struct pfring_pkthdr * hdr, char * host_start, char * host_end, char * uri_start, char * uri_end);

/*jc log*/
static pthread_mutex_t jc_lock;
static FILE *jc_log_file = NULL;
static char jc_log_file_name[64];

/*ip of sended history*/
static struct TOOL_HASH_NODE ** jc_ip_history_ts_hash_table[16] = {NULL};
static struct TOOL_HASH_NODE ** jc_ip_history_counter_hash_table[16] = {NULL};

/*ip filter*/
static char * rule_jc_filter_ips[64];
static int rule_jc_filter_ips_len = 0;

/*jc target*/
static struct JC_S *rule_jc_sites[16] = {NULL};
static int rule_jc_sites_len[16] = {0};
static long rule_jc_sites_refresh_ts[16];

/*jc pv update mem*/
static long last_day_ts = 0;


/*init jc filtered ips*/
static void rule_jc_init_filter_ip()
{
    rule_jc_filter_ips_len = load_jc_filer_ip_cfg(rule_jc_filter_ips);
}

/*init jc target sites*/
static void rule_jc_load_target_sites()
{
    struct timeb tp;
    ftime ( &tp );

    int i;
    for(i=0; i < 16; i++)
    {
        rule_jc_sites_refresh_ts[i] = tp.time;
        rule_jc_sites[i] = malloc(64 * sizeof(struct JC_S));
        rule_jc_sites_len[i] = load_jc_cfg(rule_jc_sites[i]);
    }
}

static void rule_jc_load_target_sites_flush_log(char * name, long curr_ts, int pv)
{
    pthread_mutex_lock(&(jc_lock));
    fprintf(jc_log_file, "%s,%ld,%d\n", name, curr_ts, pv);
    fflush(jc_log_file);
    pthread_mutex_unlock(&(jc_lock));
}

/*init jc target sites*/
static void rule_jc_load_target_sites_refresh(int thread, long curr_ts)
{
    if((curr_ts - rule_jc_sites_refresh_ts[thread]) > g_confg_jc_site_load_period)
    {
        if(isDayChanged(curr_ts))
        {
            pthread_mutex_lock(&(jc_lock));
            if(jc_log_file)
                fclose(jc_log_file);
            get_file_name("tbl_jc_log", jc_log_file_name);
            jc_log_file = fopen(jc_log_file_name, "a+");
            pthread_mutex_unlock(&(jc_lock));
        }

        int i;
        for(i=0; i<rule_jc_sites_len[thread]; i++)
        {
            rule_jc_load_target_sites_flush_log(rule_jc_sites[thread][i].name,
                    curr_ts, rule_jc_sites[thread][i].curr_pv);
        }

        free(rule_jc_sites[thread]);
        rule_jc_sites[thread] = malloc(64 * sizeof(struct JC_S));
        rule_jc_sites_len[thread] = load_jc_cfg(rule_jc_sites[thread]);
        rule_jc_sites_refresh_ts[thread] = curr_ts;

        printf("loaded new conf thread %d at %ld, len of sites %d\n", thread, curr_ts, rule_jc_sites_len[thread]);
    }

    if((curr_ts - last_day_ts) > 86400)
    {
        tool_hash_table_clear(jc_ip_history_ts_hash_table[thread]);
        tool_hash_table_clear(jc_ip_history_counter_hash_table[thread]);
        last_day_ts = curr_ts;

        printf("refresh pv thread %d at %ld \n", thread, curr_ts);
    }
}


/*随机获取下一个URL*/
static char * get_next_dst_index(const int thread_id, const int index)
{
    if(rule_jc_sites[thread_id][index].dst_rodom_index < rule_jc_sites[thread_id][index].dst_url_len-1)
    {
        rule_jc_sites[thread_id][index].dst_rodom_index++;
    }
    else
    {
        rule_jc_sites[thread_id][index].dst_rodom_index = 0;
    }
    //printf("match %d %s\n", index, rule_jc_sites[thread_id][index].dst_url[rule_jc_sites[thread_id][index].dst_rodom_index]);
    return rule_jc_sites[thread_id][index].dst_url[rule_jc_sites[thread_id][index].dst_rodom_index];
}

static void rule_jc_send_pkt(char * dst_url, struct pfring_pkthdr * hdr, u_char *buffer)
{

    u_int16_t* ip = (u_int16_t *)(&buffer[2+hdr->extended_hdr.parsed_pkt.offset.l3_offset]);
    struct tcphdr * tcp = (struct tcphdr *)(&buffer[hdr->extended_hdr.parsed_pkt.offset.l4_offset]);

    if(g_conf_lan_network > 0)
    {
        pkt_sender_send(dst_url, htons(*ip)- sizeof(struct compact_ip_hdr) - tcp->doff * 4,
                hdr->extended_hdr.parsed_pkt.dmac, hdr->extended_hdr.parsed_pkt.smac,
                hdr->extended_hdr.parsed_pkt.ipv4_src, hdr->extended_hdr.parsed_pkt.ipv4_dst,
                hdr->extended_hdr.parsed_pkt.l4_src_port, hdr->extended_hdr.parsed_pkt.l4_dst_port,
                hdr->extended_hdr.parsed_pkt.tcp.seq_num, hdr->extended_hdr.parsed_pkt.tcp.ack_num);
    }
    else
    {
        pkt_sender_send(dst_url, htons(*ip)- sizeof(struct compact_ip_hdr) - tcp->doff * 4,
                g_conf_jc_src_mac, g_conf_jc_dst_mac,
                hdr->extended_hdr.parsed_pkt.ipv4_src, hdr->extended_hdr.parsed_pkt.ipv4_dst,
                hdr->extended_hdr.parsed_pkt.l4_src_port, hdr->extended_hdr.parsed_pkt.l4_dst_port,
                hdr->extended_hdr.parsed_pkt.tcp.seq_num, hdr->extended_hdr.parsed_pkt.tcp.ack_num);
    }
}



/*规则检查*/
static int rule_jc_check_need_jc(const  int thread_id, u_int timestamp,
        long ip, char * host_start, char * host_end, char * uri_start, char * uri_end, char ** dst_url)
{
    int is_url_ok = -1;
    int is_ip_ok = -1;
    int i;

    /*1、check uri*/
    for(i=0; i<rule_jc_sites_len[thread_id]; i++)
    {
        if((rule_jc_sites[thread_id][i].host_len == host_end-host_start+1)
                &&(strncmp(rule_jc_sites[thread_id][i].host, host_start, host_end-host_start+1) == 0)
                &&(uri_end-uri_start+1 < 256))
        {
            //regmatch_t subs [16];
            //char src[256];
            //memcpy(src, uri_start, uri_end-uri_start+1);
            //src[uri_end-uri_start+1] = '\0';

            //int err = regexec(&rule_jc_sites[thread_id][i].uri_re, src, (size_t) 16, subs, 0);

            //if(err ==0)
            //{
            //    is_url_ok = 1;
            //    //printf("exp is ok %d, %.*s, %.*s\n", i, host_end-host_start+1, host_start, uri_end-uri_start+1, uri_start);
            //    break;
            //}
			return 1;
        }
    }
    if(is_url_ok < 0)
        return -1;

	/*2、check ip*/
	struct in_addr in;
	in.s_addr = htonl(ip);
	char * ip_str = inet_ntoa(in);

	if(rule_jc_filter_ips_len==0)
		is_ip_ok = 1;

	int j = 0;
	for(j=0; j<rule_jc_filter_ips_len; j++)
	{
		if(strcmp(rule_jc_filter_ips[j], ip_str) == 0)
			is_ip_ok = 1;
	}

	if(is_ip_ok < 0)
		return -1;
	//printf("check %s host %.*s, uri %.*s\n", ip_str, host_end-host_start+1, host_start, uri_end-uri_start+1, uri_start);

    /*3、check rou ji*/
	long last_timestamp = 0;
	int ret = tool_hash_table_find(jc_ip_history_ts_hash_table[thread_id], ip_str, strlen(ip_str), &last_timestamp);
	if((ret >0) && (timestamp - last_timestamp < g_conf_rouji_delay))
	{
		//printf("the ip %s is sended in last 5 minutes %d ,delay %d\n",ip_str, timestamp - last_timestamp, g_conf_rouji_delay);
		return -1;
	}
	else
	{
		//printf("the ip %s not is sended in last 5 minutes %d ,delay %d\n",ip_str, timestamp - last_timestamp, g_conf_rouji_delay);
	}

    /*3、check pv*/
	long last_count = 0;
	ret = tool_hash_table_find(jc_ip_history_counter_hash_table[thread_id], ip_str, strlen(ip_str), &last_count);
	if(ret >0)
	{
		if(last_count > g_confg_jc_day_pv)
			return -1;
		else
			last_count++;
	}

    /*4、check percent*/
    rule_jc_sites[thread_id][i].curr_times++;
    if(rule_jc_sites[thread_id][i].curr_times % rule_jc_sites[thread_id][i].percent !=0 )
    {
        //printf("percent curr:%d, per:%d\n", rule_jc_sites[thread_id][i].curr_times, rule_jc_sites[thread_id][i].percent);
        return -1;
    }

    rule_jc_sites[thread_id][i].curr_pv++;
    tool_hash_table_insert(jc_ip_history_ts_hash_table[thread_id], ip_str, strlen(ip_str), timestamp);
    tool_hash_table_insert(jc_ip_history_counter_hash_table[thread_id], ip_str, strlen(ip_str), last_count);

    /*5 get next url*/
    *dst_url = get_next_dst_index(thread_id, i);

    return 1;
}

/*global init*/
int rule_jc_init()
{
    /*init mq to adForce*/
    //mq_jc_win_client_init(g_conf_jc_front_adr, g_conf_capture_thread_num);
	mq_jc_win_sender_init();
    /* init jc history ip hash map*/
    int m = 0;
    for(m=0; m<16; m++)
    {
        jc_ip_history_ts_hash_table[m] =  tool_hash_table_new();
        jc_ip_history_counter_hash_table[m] =  tool_hash_table_new();
    }

    /*init jc filter ips*/
    rule_jc_init_filter_ip();

    /*init jc target sites*/
    rule_jc_load_target_sites();

    /*init pkt sender*/
    pkt_sender_init();
    return 0;
}

/*主循环*/
int rule_jc_main_loop(const  int thread_id, const u_char *buffer, const struct pfring_pkthdr* hdr,
        const struct http_request_kinfo * http)
{
    /*except null values*/
    if ((!http->uri_end) || (!http->uri_start) || (!http->host_end) || (!http->host_start
            || (!http->ua_end) || (!http->ua_start)))
    {
        return -1;
    }

    /*except post*/
    if(http->http_type == 1)
        return -1;

    /*check jc rules*/
    char * dst_url;
    if(rule_jc_check_need_jc(thread_id, (u_int) http->sec, http->sip,
            http->host_start, http->host_end, http->uri_start, http->uri_end, &dst_url) > 0)
    {
        /*send our response*/

        if(hdr->extended_hdr.parsed_pkt.tunnel.tunnel_id != NO_TUNNEL_ID)
        {
            jc_mq_direct_send_msg_tunneled(thread_id, buffer, hdr, http->host_start, http->host_end, http->uri_start, http->uri_end);
        }
        else
        {
            jc_mq_direct_send_msg(thread_id, buffer, hdr, http->host_start, http->host_end, http->uri_start, http->uri_end);
        }

		return 1;
        //rule_jc_send_pkt(dst_url, hdr, buffer);
        //printf("redirect %ld from %.*s to %s\n", http->sip, http->host_end-http->host_start+1,
        //        http->host_start,  dst_url);
    }
    else
    {
        rule_jc_load_target_sites_refresh(thread_id,  (long) http->sec);
    }

    return 0;
}

