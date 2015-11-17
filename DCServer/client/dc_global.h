#ifndef _DC_GLOBAL_H_
#define _DC_GLOBAL_H_

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <memory.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <time.h>

#include <signal.h>
#include <sched.h>
#include <sys/types.h>
#include <sys/timeb.h>

#include "pfring.h"
#include "pfring_zero.h"
#include "zmq.h"
#include "zmq_utils.h"
#include "czmq.h"
#include "tool_hash_map_new.h"

#include "zz_config.h"
#include <regex.h>

#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
//全局配置参数g_conf_*
extern int g_conf_enable_pop_window;
extern int g_conf_enable_pop_mobile;
extern int g_conf_enable_jc;
extern int g_conf_enable_http_clean;
extern int g_conf_enable_tcp_reassemble;
extern int g_conf_record_url;

extern char *  g_conf_capture_type;
extern char * g_conf_cap_device;
extern char * g_conf_send_device;
extern int   g_conf_capture_thread_num;

extern char * g_conf_pop_front_adr;
extern int  g_conf_pop_count_max_num;
extern int g_conf_pop_ip_search;
extern int g_conf_pop_ip_append;
extern int g_conf_pop_ip_refresh;
extern int g_conf_pop_name_list;
extern int g_conf_pop_delay;
extern char g_conf_jc_src_mac[6];
extern char g_conf_jc_dst_mac[6];
extern int g_conf_rouji_delay;
extern int g_conf_lan_network;
extern int g_confg_jc_day_pv;
extern int g_confg_jc_site_load_period;
extern char * g_conf_jc_front_adr;

extern int  g_conf_watermark;
extern int  g_conf_direction;

//全局共享变量 g_share_*
#define MAX_NUM_THREADS        16
#define THREAD_NUM 16

#define FILE_RECORD_CONF "/home/DCServer/conf/record_url.cfg"
#define FILE_DC_CONF "/home/DCServer/conf/dc.cfg"
#define FILE_JC_CONF "/home/DCServer/conf/jc.cfg"
#define FILE_POP_IP_CONF "/home/DCServer/conf/pop_ip.cfg"
#define FILE_POP_KEYWORD_CONF "/home/DCServer/conf/pop_keyword.cfg"
#define FILE_POP_URL_CONF "/home/DCServer/conf/pop_url.cfg"
#define FILE_SENDER_OTHER_URL_CONF "/home/DCServer/conf/sender_other_url/"
#define FILE_SENDER_MOBILE_URL_CONF "/home/DCServer/conf/sender_mobile_url/"
#define QUERY_CONTENT_CONF "/home/DCServer/conf/query_content.cfg"
#define QUERY_RULE_CONF "/home/DCServer/conf/query_rule.cfg"
#define IP_BALACK_CONF "/home/DCServer/conf/ip_black_list.cfg"
#define QUERY_URL_PATH "/home/DCServer/conf/query_url.cfg"

extern unsigned long long  g_rx_pkt_num         [MAX_NUM_THREADS];
extern unsigned long long  g_rx_pkt_byte        [MAX_NUM_THREADS];
extern unsigned long long  g_rx_pkt_send_num    [MAX_NUM_THREADS];
extern unsigned long long  g_rx_pkt_gre_num     [MAX_NUM_THREADS];
extern unsigned long long  g_rx_pkt_http_num    [MAX_NUM_THREADS];
extern unsigned long long  g_pkt_drop_num		[MAX_NUM_THREADS];

extern pfring  *           g_rx_rings           [MAX_NUM_THREADS];
extern pthread_t           g_cap_thread         [MAX_NUM_THREADS];

extern pfring_dna_cluster *dna_cluster_handle;

extern int  g_is_shutdown;

int  g_period_flag     [MAX_NUM_THREADS];
long g_period_last_time[MAX_NUM_THREADS];

/////////////////////////////////////////////////////////////////
/* http parser used structure */
struct http_request_kinfo
{
    u_char *uri_start;
    u_char *uri_end;
    u_char *host_start;
    u_char *host_end;
    u_char *ua_start;
    u_char *ua_end;
    u_char *referer_start;
    u_char *referer_end;
    u_char *cookies_start;
    u_char *cookies_end;
    u_char *content_len_start;
    u_char *content_len_end;
    u_char *post_start;
    u_char *post_end;
    int http_type;
    u_int sip;
    u_int dip;
    time_t sec;
    time_t usec;
    char timestamp[25];
    u_char *content;        /* tcp payload */
    u_int len;              /* tcp payload length */
    int header_state;       /* record context */
};

struct compact_eth_hdr
{
  unsigned char   h_dest[6];
  unsigned char   h_source[6];
  u_int16_t       h_proto;
};

struct compact_ip_hdr
{
  u_int8_t	ihl:4,
            version:4;
  u_int8_t	tos;
  u_int16_t	tot_len;
  u_int16_t	id;
  u_int16_t	frag_off;
  u_int8_t	ttl;
  u_int8_t	protocol;
  u_int16_t	check;
  u_int32_t	saddr;
  u_int32_t	daddr;
};

/////////////////////////////////////////////////////////////////
/*log util*/
#ifdef ENABLE_DEBUG
    #define DBG(...) \
    do{ \
        fprintf(stdout, __VA_ARGS__); \
    }while(0);
#else
#define DBG(...)
#endif

#ifdef ENABLE_DEBUG
    #define DBGT(...) \
    do{ \
        struct timeb tp; \
        struct tm * tm; \
        ftime ( &tp ); \
        tm = localtime (   & ( tp.time )   ); \
        fprintf(stdout, "%d:%d:%d:%d\n", (tm -> tm_hour),(tm -> tm_min),(tm ->tm_sec),(tp.millitm));\
        fprintf(stdout, __VA_ARGS__); \
    }while(0);
#else
#define DBGT(...)
#endif

#define LOG(...) \
    do{ \
        fprintf(stdout, __VA_ARGS__); \
    }while(0);

#define LOGT(...) \
    do{ \
        struct timeb tp; \
        struct tm * tm; \
        ftime ( &tp ); \
        tm = localtime (   & ( tp.time )   ); \
        fprintf(stdout, "%d:%d:%d:%d\n", (tm -> tm_hour),(tm -> tm_min),(tm ->tm_sec),(tp.millitm));\
        fprintf(stdout, __VA_ARGS__); \
    }while(0);
/////////////////////////////////////////////////////////////////

typedef struct
{
    void *ctx;
    void *s;
}ZmqDCClient;


struct JC_S
{
    char * name;
    char * host;
    int host_len;
    char * uri;
    int uri_len;
    int percent;
    int curr_times;
    int curr_pv;
    char * dst_url[8];
    int dst_url_len;
    int dst_rodom_index;
    regex_t  uri_re;
};

typedef struct POP_User_S
{
    long ts;
    int ad_id;
}SPopUserContent;

struct RECORD_URL_S
{
    char * host;
    int host_len;
    char * uri;
    int uri_len;
    regex_t uri_re;
};

#define WORD_MAX_LEN 10
#define ADVID_MAX_LEN 16

//存储广告ID
typedef struct SAdvId
{
	int advIdLen;
	int advId[ADVID_MAX_LEN];
}AdvId;

//存储搜索分隔字符
typedef struct SSiteWord
{
	int wordLen;
	char* word[WORD_MAX_LEN];
}SiteWord;

extern int g_iPopNum[THREAD_NUM];
extern HashMap* g_pTotalIP[THREAD_NUM];
#endif
