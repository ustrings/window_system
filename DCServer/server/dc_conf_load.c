#include <signal.h>
#include <string.h>

#include "dc_global.h"

int g_conf_enable_pop_window = 0;
int g_conf_enable_radius_recv = 0;
int g_conf_enable_pop_mobile = 0;
int g_conf_enable_jc = 0;
//add by zhaoyunzhou at 2014/08/20
int g_conf_enable_radius = 0;

int g_conf_record_url = 0;
int g_conf_capture_thread_num = 16;
int g_conf_ad_total_count = 0;

char * g_conf_pop_front_adr = "tcp://127.0.0.1:9050";
char * g_conf_pop_win_adr = "tcp://127.0.0.1:9050";
char * g_conf_surplus_url_adr = "tcp://127.0.0.1:9050";
char * g_conf_pass_back_adr = "tcp://127.0.0.1:9050";
char * g_conf_instant_adr = "tcp://127.0.0.1:9050";
char * g_conf_radius_recv_adr = "tcp://127.0.0.1:9050";
char * g_conf_redis_adr = "127.0.0.1";
char * g_conf_http_write_adr = "tcp://61.152.73.246:5558";

//剩余流量最大发送次数
int  g_conf_suplus_max_num = 1;
int  g_conf_suplus_ad_max_num = 1;
int  g_conf_suplus_fact_num = 1;
int  g_conf_suplus_delay = 36000;
int  g_conf_suplus_factdelay = 600;

//人群发送次数
int  g_conf_crowd_max_num = 1;
int  g_conf_crowd_ad_max_num = 1;
int  g_conf_crowd_fact_num = 1;
int  g_conf_crowd_delay = 36000;
int  g_conf_crowd_factdelay = 600;

//特定域名投放最大次数
int g_conf_pop_white_host_max_num = 1;
int g_conf_pop_white_host_fact_num = 1;
int g_conf_pop_white_host_delay = 36000;
int g_conf_pop_white_host_factdelay = 600;

//特定域名投放最大次数
int g_conf_pop_white_host_max_num1 = 1;
int g_conf_pop_white_host_fact_num1 = 1;
int g_conf_pop_white_host_delay1 = 36000;
int g_conf_pop_white_host_factdelay1 = 600;

//过滤AD的发送次数
int g_conf_filter_ad_max_num = 1;

//指定Ad的投放次数
int g_conf_specify_ad_max_num = 1;

int g_conf_pop_ip_refresh = 60;

//即搜即投
int g_conf_pop_ip_search = 0;

//针对域名进行投放
int g_conf_pop_white_host = 0;

//针对域名进行投放
int g_conf_pop_white_host1 = 0;

//人群搜索
int g_conf_crowd_search = 0;
int g_conf_crowd_server = 0;

//针对百度
int g_conf_pop_after_search = 0;
int g_conf_after_delay = 0;
int g_conf_after_max_num = 0;

//是否对剩余流量弹窗
int g_conf_pop_surplus = 0;

//离线人群弹窗
int g_conf_offline_pop = 0;

//是否追投
int g_conf_pop_ip_append = 0;

int g_conf_pop_name_list = 0;

//过滤AD的发送间隔
int g_conf_filter_ad_delay = 36000;

//指定用户AD的投放
int g_conf_specify_ad_delay = 36000;

char * g_conf_send_device = "eth0";

char * instant_redis_list[16] = {NULL};
int g_redis_list_len = 0;
//即搜即投人群过滤
SFilerAdAccount g_filterAdAccount;

//指定人群投放
SFilerAdAccount g_TotalAdAccount;

extern void load_root_jc_cfg(zzconfig_t * root);
extern void dc_load_pop_mobile(zzconfig_t * root);
/************************************************************************/
//  载入即搜即投用户限制AD不影响其他用户                         
//                                                                     
/************************************************************************/
void load_pop_filer_ad_cfg(zzconfig_t * root)
{
	g_filterAdAccount.len = 0;
	int i = 0;
	for (; i < 64; i++)
	{
		g_filterAdAccount.userInfo[i].iCount = 0;
		g_filterAdAccount.userInfo[i].lTime = 0;
	}
	
	zzconfig_t * child = zz_config_locate(root, "pop_windows/filter_ad");
	zzconfig_t * child_ip = zz_config_child(child);
	while (child_ip)
	{
		g_filterAdAccount.pcPopWhiteAds[g_filterAdAccount.len] = zz_config_value(child_ip);
		printf("filter %d pop filtered ad %s\n", g_filterAdAccount.len, g_filterAdAccount.pcPopWhiteAds[g_filterAdAccount.len]);
		child_ip = zz_config_next(child_ip);
		g_filterAdAccount.len++;
	}
	printf("the pop filtered ad len =%d\n", g_filterAdAccount.len);
}


/************************************************************************/
//  对指定用户指定Ad                         
//                                                                     
/************************************************************************/
void load_pop_total_user_ad_cfg(zzconfig_t * root)
{
	g_TotalAdAccount.len = 0;
	int i = 0;
	for (; i < 64; i++)
	{
		g_TotalAdAccount.userInfo[i].iCount = 0;
		g_TotalAdAccount.userInfo[i].lTime = 0;
	}

	zzconfig_t * child = zz_config_locate(root, "pop_windows/filter_total_ad");
	zzconfig_t * child_ip = zz_config_child(child);
	while (child_ip)
	{
		g_TotalAdAccount.pcPopWhiteAds[g_TotalAdAccount.len] = zz_config_value(child_ip);
		printf("specify %d pop filtered ad %s\n", g_TotalAdAccount.len, g_TotalAdAccount.pcPopWhiteAds[g_TotalAdAccount.len]);
		child_ip = zz_config_next(child_ip);
		g_TotalAdAccount.len++;
	}
	printf("the pop filtered total ad len =%d\n", g_TotalAdAccount.len);
}

void dc_load_surplus_url(zzconfig_t * root)
{
	char * pop_suplus_delay = zz_config_resolve(root, "pop_windows/pop_surplus_delay", "36000");
	g_conf_suplus_delay = atoi(pop_suplus_delay);
	//g_conf_suplus_delay = g_conf_suplus_delay < 10 ? 10 :g_conf_suplus_delay;
	printf("pop_windows/pop_surplus_delay=%d\n", g_conf_suplus_delay);

	char * pop_suplus_factdelay = zz_config_resolve(root, "pop_windows/pop_surplus_factdelay", "36000");
	g_conf_suplus_factdelay = atoi(pop_suplus_factdelay);
	//g_conf_suplus_factdelay = g_conf_suplus_factdelay < 60 ? 60 :g_conf_suplus_factdelay;
	printf("pop_windows/pop_surplus_factdelay=%d\n", g_conf_suplus_factdelay);

	char * pop_suplus_max_num = zz_config_resolve(root, "pop_windows/pop_surplus_limit", "1");
	g_conf_suplus_max_num = atoi(pop_suplus_max_num);
	g_conf_suplus_max_num = g_conf_suplus_max_num > 500 ? 100 :g_conf_suplus_max_num;
	printf("pop_windows/pop_surplus_limit=%d\n", g_conf_suplus_max_num);

        char * pop_suplus_ad_max_num = zz_config_resolve(root, "pop_windows/pop_surplus_ad_limit", "1");
	g_conf_suplus_ad_max_num = atoi(pop_suplus_ad_max_num);
	g_conf_suplus_ad_max_num = g_conf_suplus_ad_max_num > 500 ? 100 :g_conf_suplus_ad_max_num;
	printf("pop_windows/pop_surplus_ad_limit=%d\n", g_conf_suplus_ad_max_num);

	char * pop_suplus_fact_num = zz_config_resolve(root, "pop_windows/pop_surplus_fact", "1");
	g_conf_suplus_fact_num = atoi(pop_suplus_fact_num);
	g_conf_suplus_fact_num = g_conf_suplus_fact_num > 500 ? 100 :g_conf_suplus_fact_num;
	printf("pop_windows/pop_surplus_fact=%d\n", g_conf_suplus_fact_num);

}

void dc_load_pop_crowd(zzconfig_t * root)
{
	//即搜即投的人群信息查询
	char * pcCrowdSearch = zz_config_resolve(root, "instant_pop/crowd_search", "false");
	printf("instant_pop/crowd_search=%s\n", pcCrowdSearch);
	if(strcmp(pcCrowdSearch, "true") == 0)
	{
		g_conf_crowd_search = 1;
	}
	
	//即搜即投的人群信息查询
	char * pcCrowdServer = zz_config_resolve(root, "instant_pop/crowd_server", "false");
	printf("instant_pop/crowd_server=%s\n", pcCrowdServer);
	if(strcmp(pcCrowdServer, "true") == 0)
	{
		g_conf_crowd_server = 1;
	}
	char * pop_crowd_delay = zz_config_resolve(root, "instant_pop/pop_crowd_delay", "36000");
	g_conf_crowd_delay = atoi(pop_crowd_delay);
	g_conf_crowd_delay = g_conf_crowd_delay < 10 ? 10 :g_conf_crowd_delay;
	printf("instant_pop/pop_crowd_delay=%d\n", g_conf_crowd_delay);

	char * pop_crowd_factdelay = zz_config_resolve(root, "instant_pop/pop_crowd_factdelay", "36000");
	g_conf_crowd_factdelay = atoi(pop_crowd_factdelay);
	g_conf_crowd_factdelay = g_conf_crowd_factdelay < 60 ? 60 :g_conf_crowd_factdelay;
	printf("instant_pop/pop_crowd_factdelay=%d\n", g_conf_crowd_factdelay);

	char * pop_crowd_max_num = zz_config_resolve(root, "instant_pop/pop_crowd_limit", "1");
	g_conf_crowd_max_num = atoi(pop_crowd_max_num);
	g_conf_crowd_max_num = g_conf_crowd_max_num > 500 ? 100 :g_conf_crowd_max_num;
	printf("instant_pop/pop_crowd_limit=%d\n", g_conf_crowd_max_num);

        char * pop_crowd_ad_max_num = zz_config_resolve(root, "instant_pop/pop_crowd_ad_limit", "1");
	g_conf_crowd_ad_max_num = atoi(pop_crowd_ad_max_num);
	g_conf_crowd_ad_max_num = g_conf_crowd_ad_max_num > 500 ? 100 :g_conf_crowd_ad_max_num;
	printf("instant_pop/pop_crowd_ad_limit=%d\n", g_conf_crowd_ad_max_num);

	char * pop_crowd_fact_num = zz_config_resolve(root, "instant_pop/pop_crowd_fact", "1");
	g_conf_crowd_fact_num = atoi(pop_crowd_fact_num);
	g_conf_crowd_fact_num = g_conf_crowd_fact_num > 500 ? 100 :g_conf_crowd_fact_num;
	printf("instant_pop/pop_crowd_fact=%d\n", g_conf_crowd_fact_num);

	//针对百度弹窗
	char * pcPopAfterSearch = zz_config_resolve(root, "instant_pop/pop_after_search", "false");
	printf("instant_pop/pop_after_search=%s\n", pcPopAfterSearch);
	if(strcmp(pcPopAfterSearch, "true") == 0)
	{
		g_conf_pop_after_search = 1;
	}
	else
	{
		g_conf_pop_after_search = 0;
	
	}

	char * pop_after_delay = zz_config_resolve(root, "instant_pop/pop_after_delay", "36000");
	g_conf_after_delay = atoi(pop_after_delay);
	g_conf_after_delay = g_conf_after_delay < 10 ? 10 :g_conf_after_delay;
	printf("instant_pop/pop_after_delay=%d\n", g_conf_after_delay);

	char * pop_after_max_num = zz_config_resolve(root, "instant_pop/pop_after_limit", "1");
	g_conf_after_max_num = atoi(pop_after_max_num);
	g_conf_after_max_num = g_conf_after_max_num > 500 ? 100 :g_conf_after_max_num;
	printf("instant_pop/pop_after_limit=%d\n", g_conf_after_max_num);

	zzconfig_t * child = zz_config_locate(root, "instant_pop/redis_list");
	zzconfig_t * child_ip = zz_config_child(child);
	int j = 0;
	while (child_ip)
	{
		instant_redis_list[j]= zz_config_value(child_ip);
		printf("%d pop filtered ip %s\n", j, instant_redis_list[j]);
		child_ip = zz_config_next(child_ip);
		j++;	
	}
	g_redis_list_len = j;
	printf("the pop filtered ip len =%d\n", j);
}

void dc_load_pop_target1(zzconfig_t * root)
{
	char * pop_white_host_delay1 = zz_config_resolve(root, "pop_windows/pop_white_host_delay1", "36000");
	g_conf_pop_white_host_delay1 = atoi(pop_white_host_delay1);
	g_conf_pop_white_host_delay1 = g_conf_pop_white_host_delay1 < 10 ? 10 :g_conf_pop_white_host_delay1;
	printf("pop_windows/pop_white_host_delay1=%d\n", g_conf_pop_white_host_delay1);

	char * pop_white_host_factdelay1 = zz_config_resolve(root, "pop_windows/pop_white_host_factdelay1", "36000");
	g_conf_pop_white_host_factdelay1 = atoi(pop_white_host_factdelay1);
	g_conf_pop_white_host_factdelay1 = g_conf_pop_white_host_factdelay1 < 10 ? 10 :g_conf_pop_white_host_factdelay1;
	printf("pop_windows/pop_white_host_factdelay1=%d\n", g_conf_pop_white_host_factdelay1);

	char * pop_white_host_max_num1 = zz_config_resolve(root, "pop_windows/pop_white_host_limit1", "1");
	g_conf_pop_white_host_max_num1 = atoi(pop_white_host_max_num1);
	g_conf_pop_white_host_max_num1 = g_conf_pop_white_host_max_num1 > 500 ? 100 :g_conf_pop_white_host_max_num1;
	printf("pop_windows/pop_white_host_limit1=%d\n", g_conf_pop_white_host_max_num1);


	char * pop_white_host_fact_num1 = zz_config_resolve(root, "pop_windows/pop_white_host_fact1", "1");
	g_conf_pop_white_host_fact_num1 = atoi(pop_white_host_fact_num1);
	g_conf_pop_white_host_fact_num1 = g_conf_pop_white_host_fact_num1 > 500 ? 100 :g_conf_pop_white_host_fact_num1;
	printf("pop_windows/pop_white_host_fact1=%d\n", g_conf_pop_white_host_fact_num1);
}

void dc_load_pop_target(zzconfig_t * root)
{
	char * pop_white_host_delay = zz_config_resolve(root, "pop_windows/pop_white_host_delay", "36000");
	g_conf_pop_white_host_delay = atoi(pop_white_host_delay);
	g_conf_pop_white_host_delay = g_conf_pop_white_host_delay < 10 ? 10 :g_conf_pop_white_host_delay;
	printf("pop_windows/pop_white_host_delay=%d\n", g_conf_pop_white_host_delay);

	char * pop_white_host_factdelay = zz_config_resolve(root, "pop_windows/pop_white_host_factdelay", "36000");
	g_conf_pop_white_host_factdelay = atoi(pop_white_host_factdelay);
	g_conf_pop_white_host_factdelay = g_conf_pop_white_host_factdelay < 10 ? 10 :g_conf_pop_white_host_factdelay;
	printf("pop_windows/pop_white_host_factdelay=%d\n", g_conf_pop_white_host_factdelay);

	char * pop_white_host_max_num = zz_config_resolve(root, "pop_windows/pop_white_host_limit", "1");
	g_conf_pop_white_host_max_num = atoi(pop_white_host_max_num);
	g_conf_pop_white_host_max_num = g_conf_pop_white_host_max_num > 500 ? 100 :g_conf_pop_white_host_max_num;
	printf("pop_windows/pop_white_host_limit=%d\n", g_conf_pop_white_host_max_num);

	char * pop_white_host_fact_num = zz_config_resolve(root, "pop_windows/pop_white_host_fact", "1");
	g_conf_pop_white_host_fact_num = atoi(pop_white_host_fact_num);
	g_conf_pop_white_host_fact_num = g_conf_pop_white_host_fact_num > 500 ? 100 :g_conf_pop_white_host_fact_num;
	printf("pop_windows/pop_white_host_fact=%d\n", g_conf_pop_white_host_fact_num);

}

void load_enable_list(zzconfig_t * root)
{
      char* ad_total_limit = zz_config_resolve(root, "pop_windows/ad_max_limit", "2");
       g_conf_ad_total_count = atoi(ad_total_limit);
        //g_conf_ad_total_count = g_conf_ad_total_count > 10 ? 10 :g_conf_ad_total_count;
	printf("pop_windows/ad_total_limit=%d\n", g_conf_ad_total_count);
	
	//针对特定的域名进行投放
	char *pcPopWhiteHost = zz_config_resolve(root, "pop_windows/pop_white_host", "false");
	printf("pop_windows/pop_white_host=%s\n", pcPopWhiteHost);
	if(strcmp(pcPopWhiteHost, "true") == 0)
    	{
        	g_conf_pop_white_host = 1;
		dc_load_pop_target(root);
    	}
	else
	{
        	g_conf_pop_white_host = 0;
	
	}

	char *pcPopWhiteHost1 = zz_config_resolve(root, "pop_windows/pop_white_host1", "false");
	printf("pop_windows/pop_white_host1=%s\n", pcPopWhiteHost1);
	if(strcmp(pcPopWhiteHost1, "true") == 0)
	{
		g_conf_pop_white_host1 = 1;
		dc_load_pop_target1( root);
	}
	else
	{
        g_conf_pop_white_host1 = 0;
	}


	//剩余流量弹窗
	char * pcSurplus = zz_config_resolve(root, "pop_windows/pop_surplus_url", "false");
	printf("pop_windows/pop_surplus_url=%s\n", pcSurplus);
	if(strcmp(pcSurplus, "true") == 0)
	{
		dc_load_surplus_url( root);
		g_conf_pop_surplus = 1;
	}
	else
	{
		g_conf_pop_surplus = 0;
	
	}

	//离线人群投放
	char * pcOfflinePop = zz_config_resolve(root, "pop_windows/pop_offline_crowd", "false");
	printf("pop_windows/pop_offline_crowd=%s\n", pcOfflinePop);
	if(strcmp(pcOfflinePop, "true") == 0)
	{
		g_conf_offline_pop = 1;
	}
	else
	{
		g_conf_offline_pop = 0;
	
	}

	if(g_conf_offline_pop)
	{
		dc_load_pop_crowd( root);
	}
	//追投
	char * pcAppend = zz_config_resolve(root, "pop_windows/pop_ip_append", "false");
	printf("pop_windows/pop_ip_append=%s\n", pcAppend);
	if(strcmp(pcAppend, "true") == 0)
	{
		g_conf_pop_ip_append = 1;
	}
	else
	{
		g_conf_pop_ip_append = 0;
	
	}
}

void load_dc_cfg()
{
    printf ("begin load load_dc_cfg: \n");
    zzconfig_t * root = zz_config_load (FILE_DC_CONF);

    //功能使能
    char * enable_pop_window = zz_config_resolve(root, "global/pop_window", "disable");
	char * enable_pop_mobile = zz_config_resolve(root, "global/pop_mobile", "disable");
	char * enable_radius_recv = zz_config_resolve(root, "global/radius_recv", "disable");
    char * enable_jc         = zz_config_resolve(root, "global/jc", "disable");
    char * enable_record_url = zz_config_resolve(root, "global/record_url", "disable");
    char * enable_pop_ip_search = zz_config_resolve(root, "global/pop_ip_search", "disable");

    if(strcmp(enable_pop_window, "enable") == 0)
	{
        g_conf_enable_pop_window = 1;
	}

	if(strcmp(enable_pop_mobile, "enable") == 0)
	{
		g_conf_enable_pop_mobile = 1;
		dc_load_pop_mobile(root);
	}

	if(strcmp(enable_radius_recv, "enable") == 0)
		g_conf_enable_radius_recv = 1;

	if(strcmp(enable_jc, "enable") == 0)
	{
		g_conf_enable_jc = 1;
		load_root_jc_cfg(root);
	}

	if(strcmp(enable_pop_ip_search, "enable") == 0)
	{
		g_conf_pop_ip_search = 1;
		dc_load_pop_crowd( root);
	}

    if(strcmp(enable_record_url, "enable") == 0)
        g_conf_record_url = 1;

	printf("pop_window:%s, jc:%s \n", enable_pop_window, enable_jc);

	char * filter_ad_delay = zz_config_resolve(root, "pop_windows/filter_ad_delay", "36000");
	g_conf_filter_ad_delay = atoi(filter_ad_delay);
	g_conf_filter_ad_delay = g_conf_filter_ad_delay < 10 ? 10 :g_conf_filter_ad_delay;
	printf("pop_windows/filter_ad_delay=%d\n", g_conf_filter_ad_delay);

	char * filter_ad_limit = zz_config_resolve(root, "pop_windows/filter_ad_limit", "1");
	g_conf_filter_ad_max_num = atoi(filter_ad_limit);
	g_conf_filter_ad_max_num = g_conf_filter_ad_max_num > 500 ? 100 :g_conf_filter_ad_max_num;
	printf("pop_windows/filter_ad_limit=%d\n", g_conf_filter_ad_max_num);

	char * specify_ad_delay = zz_config_resolve(root, "pop_windows/specify_ad_delay", "36000");
	g_conf_specify_ad_delay = atoi(specify_ad_delay);
	g_conf_specify_ad_delay = g_conf_specify_ad_delay < 10 ? 10 :g_conf_specify_ad_delay;
	printf("pop_windows/specify_ad_delay=%d\n", g_conf_specify_ad_delay);

	char * specify_ad_limit = zz_config_resolve(root, "pop_windows/specify_ad_limit", "1");
	g_conf_specify_ad_max_num = atoi(specify_ad_limit);
	g_conf_specify_ad_max_num = g_conf_specify_ad_max_num > 500 ? 100 :g_conf_specify_ad_max_num;
	printf("pop_windows/specify_ad_limit=%d\n", g_conf_specify_ad_max_num);

	char * pop_name_list = zz_config_resolve(root, "pop_windows/name_list", "0");
	if(strcmp(pop_name_list, "black") == 0)
	g_conf_pop_name_list = 1;
	
	load_enable_list(root);

    char * pop_ip_refresh = zz_config_resolve(root, "pop_windows/pop_ip_refresh", "60");
    g_conf_pop_ip_refresh = atoi(pop_ip_refresh);

	load_pop_filer_ad_cfg(root);
	load_pop_total_user_ad_cfg(root);

    //弹窗服务器的地址和端口
    g_conf_pop_front_adr = zz_config_resolve(root, "pop_windows/mq_front_adr", "tcp://127.0.0.1:9010");
    g_conf_pop_win_adr = zz_config_resolve(root, "pop_windows/pop_win_adr", "tcp://127.0.0.1:9010");
    g_conf_surplus_url_adr = zz_config_resolve(root, "pop_windows/surplus_url_adr", "tcp://127.0.0.1:9010");
    g_conf_pass_back_adr = zz_config_resolve(root, "pop_windows/pass_back_adr", "tcp://127.0.0.1:9010");
    g_conf_instant_adr = zz_config_resolve(root, "pop_windows/instant_adr", "tcp://127.0.0.1:9010");
    g_conf_radius_recv_adr = zz_config_resolve(root, "pop_windows/radius_recv_adr", "tcp://127.0.0.1:9010");
    g_conf_redis_adr = zz_config_resolve(root, "pop_windows/redis_adr", "127.0.0.1");
    g_conf_http_write_adr = zz_config_resolve(root, "pop_windows/http_write_adr", "tcp://61.152.73.246:5558");
}

int load_pop_filer_ip_cfg(char * rule_pop_white_ips[64])
{
	printf ("begin load load_pop_filer_ip_cfg: \n");
	zzconfig_t * root = zz_config_load (FILE_DC_CONF);
	zzconfig_t * child = zz_config_locate(root, "pop_windows/filter_ip");
	zzconfig_t * child_ip = zz_config_child(child);
	int j = 0;
	while (child_ip)
	{
		rule_pop_white_ips[j]= zz_config_value(child_ip);
		printf("%d pop filtered ip %s\n", j, rule_pop_white_ips[j]);
		child_ip = zz_config_next(child_ip);
		j++;
	}
	printf("the pop filtered ip len =%d\n", j);
	return j;
}
