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

#include "zmq.h"
#include "zmq_utils.h"
#include "../libs/czmq-2.0.3/include/czmq.h"

#include "zz_config.h"
#include <regex.h>
#include "c_api.h"
#include "trans_code.h"
#include "ip2address.h"
#include "tool_hash_map_new.h"
#include "redis_manager.h"
#include "gbk2utf8.h"

//全局配置参数g_conf_*
extern int g_conf_enable_one_pop_window;
extern int g_conf_enable_pop_window;
extern int g_conf_enable_pop_mobile;
extern int g_conf_enable_radius_recv;
extern int g_conf_enable_jc;
extern int g_conf_record_url;
extern int g_conf_enable_radius;

extern char * g_conf_send_device;
extern int  g_conf_capture_thread_num;
extern int g_conf_crowd_fact_num ;
extern int  g_conf_suplus_fact_num ;
extern int g_conf_pop_white_host_fact_num ;
extern int g_conf_pop_white_host_fact_num1 ;
extern int g_conf_ad_total_count;

extern char * g_conf_pop_front_adr;
extern char * g_conf_pop_win_adr ;
extern char * g_conf_surplus_url_adr ;
extern char * g_conf_pass_back_adr ;
extern char * g_conf_instant_adr;
extern char * g_conf_radius_recv_adr ;
extern char * g_conf_mobile_adr ;
extern char * g_conf_redis_adr ;
extern char * g_conf_http_write_adr ;

extern int g_conf_pop_mobile_delay ;
extern int g_conf_pop_mobile_factdelay ;
extern int g_conf_pop_mobile_max_num ;
extern int g_conf_pop_mobile_fact_num ;

extern int g_conf_pop_surplus;
extern int g_conf_suplus_max_num;
extern int g_conf_suplus_fact_num ;
extern int  g_conf_suplus_ad_max_num;
extern int g_conf_suplus_delay;
extern int g_conf_suplus_factdelay;

extern int g_conf_pop_white_host;
extern int g_conf_pop_white_host_max_num;
extern int g_conf_pop_white_host_fact_num ;
extern int g_conf_pop_white_host_delay;
extern int g_conf_pop_white_host_factdelay;

extern int g_conf_crowd_max_num;
extern int  g_conf_crowd_ad_max_num ;
extern int g_conf_crowd_fact_num ;
extern int g_conf_crowd_delay;
extern int g_conf_crowd_factdelay;

extern int g_conf_pop_white_host1;
extern int g_conf_pop_white_host_delay1;
extern int g_conf_pop_white_host_factdelay1;
extern int g_conf_pop_white_host_max_num1;
extern int g_conf_pop_white_host_fact_num1 ;

extern int g_conf_pop_after_search;
extern int g_conf_after_delay ;
extern int g_conf_after_max_num;

extern int g_conf_pop_ip_search;
extern int g_conf_crowd_search;
extern int g_conf_crowd_server;
extern int g_conf_filter_ad_max_num;
extern int g_conf_specify_ad_max_num;
extern int g_conf_filter_ad_delay;
extern int g_conf_specify_ad_delay;
extern int g_conf_offline_pop;
extern int g_conf_pop_ip_append;
extern int g_conf_pop_ip_refresh;
extern int g_conf_pop_name_list;
extern int g_conf_jc_limit;
extern int g_conf_jc_total_limit;
extern char g_conf_jc_src_mac[6];
extern char g_conf_jc_dst_mac[6];
extern char * g_conf_jc_front_adr;
extern char * instant_redis_list[16];
extern int g_redis_list_len;

extern IP2AddressData *g_pMainIPAreaFilterData;

//全局共享变量 g_share_*
#define MAX_NUM_THREADS        16
#define THREAD_NUM 16

#define FILE_RECORD_CONF "/home/DCServer/conf/record_url.cfg"
#define FILE_DC_CONF "/home/DCServer/conf/dc.cfg"
#define FILE_JC_CONF "/home/DCServer/conf/jc.cfg"
#define FILE_POP_KEYWORD_CONF "/home/DCServer/conf/pop_keyword.cfg"
#define FILE_POP_URL_CONF "/home/DCServer/conf/pop_url/"
#define SITE_WHITE_TOTAL_CONF "/home/DCServer/conf/host_white_list.cfg"
#define FILE_POP_TARGET_CONF "/home/DCServer/conf/pop_target/"
#define FILE_POP_TARGET_CONF1 "/home/DCServer/conf/pop_target1/"
#define SURPLUS_POP_URL_CONF "/home/DCServer/conf/surplus_url/"
#define QUERY_CONTENT_CONF "/home/DCServer/conf/query_content/"
#define QUERY_RULE_CONF "/home/DCServer/conf/query_rule.cfg"
#define ADUA_RULE_CONF "/home/DCServer/conf/offlinecrowd/"
#define IP_BALACK_CONF "/home/DCServer/conf/ip_black_list.cfg"
#define AD_WHITE_CONF "/home/DCServer/conf/ad_white_list.cfg"
#define SITE_BLACK_CONF "/home/DCServer/conf/host_black_list.cfg"
#define ILLEGAL_WORD_CONF "/home/DCServer/conf/illegalword/illegalword.cfg"
#define QUERY_URL_PATH "/home/DCServer/conf/query_url/"
#define RECORD_HTTP_PATH "/home/DCServer/mq_info/"
#define DICT_PATH "/home/DCServer/conf/dict/jieba.dict.utf8"
#define HMM_PATH "/home/DCServer/conf/dict/hmm_model.utf8"
#define USER_DICT "/home/DCServer/conf/dict/user.dict.utf8"
#define STOP_DICT "/home/DCServer/conf/dict/stopword.txt"

extern void*			   g_pvMqReceiver		[MAX_NUM_THREADS];
extern void*			   g_pvMobileReceiver		[MAX_NUM_THREADS];

extern int  g_is_shutdown;

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

struct JC_S
{
    char * name;
    char * host;
    int host_len;
    char * uri;
    int uri_len;
	int delay;
    int percent;
	int type;
    char * dst_url[8];
	char * flag;
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


#define ADUA_RULE_LEN 4000000
#define AD_MAX_LEN 16

typedef struct
{
        int iAdid;
        int iscore;
}AdInfo;

typedef struct
{
        int adlen ;
        AdInfo  pvAdInfo[AD_MAX_LEN];
}AdFire;


//存储每个用户的弹窗次数和最后时间
typedef struct
{
	int iFactCount;
	int iFactTime;
	int iCount;
	long lTime;
}FilterUserInfo;

//存储搜索分隔字符
typedef struct SSiteWord
{
	int wordLen;
	char* word[WORD_MAX_LEN];
}SiteWord;

//存储mq传送数据
typedef struct SMqHttpMessage
{
	char pcCookie[4096];
	char pcSrcIP[16];
	char pcUri[8182];
	char host[256];
	char pcSec[64];
	long lSec;
	char pcUa[512];
	char pcNoEncUa[512];
}MqHttpMessage;

//存储指定用户白名单
typedef struct
{
	char * pcPopWhiteAds[64];
	FilterUserInfo userInfo[64];
	int len;
}SFilerAdAccount;

//存储JC每个用户的信息
typedef struct JCUser
{
	int totalNum;
	int iStrategyNum;
	char* pcStrategys[64];
	long lTimes[64];
	int iCounts[64];
}SJCUSer;

typedef struct FileContent
{
	char* cpData[128];
	int iNum;
}SFileContent;

//存储符合条件的IP和UA
extern HashMap* g_pPopIpHashMaps[THREAD_NUM];
extern int g_iReceiverNum[THREAD_NUM];
extern int g_iJCSenderNum[THREAD_NUM];
extern SFilerAdAccount g_filterAdAccount;
extern SFilerAdAccount g_TotalAdAccount;;
#endif
