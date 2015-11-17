#include <signal.h>
#include <string.h>

#include "dc_global.h"

int g_conf_enable_pop_window = 0;
int g_conf_enable_pop_mobile = 0;
int g_conf_enable_jc = 0;

int g_conf_enable_http_clean = 0;
int g_conf_enable_tcp_reassemble = 0;

int g_conf_record_url = 0;

char *  g_conf_capture_type  = "dna";
int   g_conf_capture_thread_num = 8;

char * g_conf_jc_front_adr = "tcp://127.0.0.1:8050";
char g_conf_jc_src_mac[6];
char g_conf_jc_dst_mac[6];
int g_conf_rouji_delay = 3600;
int g_conf_lan_network = 0;
int g_confg_jc_day_pv = 100;
int g_confg_jc_site_load_period = 600;

char * g_conf_pop_front_adr = "tcp://127.0.0.1:9050";
int  g_conf_pop_count_max_num = 1000;
int g_conf_pop_ip_refresh = 60;

//是否即搜即投
int g_conf_pop_ip_search = 0;

//是否追投
int g_conf_pop_ip_append = 0;

int g_conf_pop_name_list = 0;
int g_conf_pop_delay = 600;

char * g_conf_cap_device = "dna0";
char * g_conf_send_device = "eth0";

int  g_conf_watermark = 1;
int  g_conf_direction = 1;

static void string_to_mac(const char * mac_string, char *mac)
{
    int i = 0;
    char * curr_dot_index = strstr(mac_string,",");
    char * last_dot_index = mac_string;

    while((curr_dot_index != NULL) &&(i<6))
    {
        char m[8] = {'\0'};
        sprintf(m, "%.*s", curr_dot_index-last_dot_index, last_dot_index);

        int t = 0;
        sscanf(m, "0x%x",&t);
        mac[i] = (char)t&(0x00ff);

        last_dot_index = curr_dot_index+1;
        curr_dot_index=strstr(last_dot_index+1,",");
        i++;
    }

    char m[8] = {'\0'};
    sprintf(m, "%s", last_dot_index+2);
    int t = 0;
    sscanf(m, "%x",&t);
    mac[i] = (char)t&(0x00ff);
}

void load_dc_cfg()
{
    printf ("begin load load_dc_cfg: \n");
    zzconfig_t * root = zz_config_load (FILE_DC_CONF);

    //功能使能
    char * enable_pop_window = zz_config_resolve(root, "global/pop_window", "disable");
    char * enable_jc         = zz_config_resolve(root, "global/jc", "disable");
    char * enable_http_clean = zz_config_resolve(root, "global/http_clean", "disable");
    char * enable_tcp_reassemble = zz_config_resolve(root, "global/tcp_reassemble", "disable");
    char * enable_record_url = zz_config_resolve(root, "global/record_url", "disable");
	char * enable_pop_mobile = zz_config_resolve(root, "global/pop_mobile", "disable");

	if(strcmp(enable_pop_mobile, "enable") == 0)
		g_conf_enable_pop_mobile = 1;
    if(strcmp(enable_pop_window, "enable") == 0)
        g_conf_enable_pop_window = 1;
    if(strcmp(enable_jc, "enable") == 0)
        g_conf_enable_jc = 1;
    if(strcmp(enable_http_clean, "enable") == 0)
        g_conf_enable_http_clean = 1;
    if(strcmp(enable_tcp_reassemble, "enable") == 0)
        g_conf_enable_tcp_reassemble = 1;
    if(strcmp(enable_record_url, "enable") == 0)
        g_conf_record_url = 1;

    g_conf_cap_device = zz_config_resolve(root, "capture_dev", "dna0");
    g_conf_send_device = zz_config_resolve(root, "jc/send_dev", "em1");

    //抓包方式和线程数
    g_conf_capture_type = zz_config_resolve(root, "capture_type", "dna");
    char * capture_thread_num = zz_config_resolve(root, "http_clean/thread_num", "8");
    g_conf_capture_thread_num = atoi(capture_thread_num);

	printf("g_conf_capture_thread_num=%s\n", capture_thread_num);

    char * pop_delay = zz_config_resolve(root, "pop_windows/pop_delay", "600");
    g_conf_pop_delay = atoi(pop_delay);

    char * pop_name_list = zz_config_resolve(root, "pop_windows/name_list", "0");
    if(strcmp(pop_name_list, "black") == 0)
        g_conf_pop_name_list = 1;

    char * jc_src_mac = zz_config_resolve(root, "jc/src_mac", "");
    char * jc_dst_mac = zz_config_resolve(root, "jc/dst_mac", "");

    char * rouji_delay = zz_config_resolve(root, "jc/rouji_delay", "600");
    g_conf_rouji_delay = atoi(rouji_delay);

    char * lan_network = zz_config_resolve(root, "jc/lan_network", "0");
    g_conf_lan_network = atoi(lan_network);

    char * jc_day_pv = zz_config_resolve(root, "jc/day_pv", "100");
    g_confg_jc_day_pv = atoi(jc_day_pv);


    char * site_load_period = zz_config_resolve(root, "jc/site_load_period", "600");
    g_confg_jc_site_load_period = atoi(site_load_period);

    string_to_mac(jc_src_mac, g_conf_jc_src_mac);
    string_to_mac(jc_dst_mac, g_conf_jc_dst_mac);

    char * pcSearch = zz_config_resolve(root, "pop_windows/pop_ip_search", "false");
    if(strcmp(pcSearch, "true") == 0)
    {
        g_conf_pop_ip_search = 1;
    }

	char * pcAppend = zz_config_resolve(root, "pop_windows/pop_ip_append", "false");
	if(strcmp(pcAppend, "true") == 0)
	{
		g_conf_pop_ip_append = 1;
	}

    char * pop_ip_refresh = zz_config_resolve(root, "pop_windows/pop_ip_refresh", "60");
    g_conf_pop_ip_refresh = atoi(pop_ip_refresh);

    char * pop_count_max_num = zz_config_resolve(root, "pop_windows/pop_limit", "10");
    g_conf_pop_count_max_num = atoi(pop_count_max_num);

    //弹窗服务器的地址和端口
    g_conf_pop_front_adr = zz_config_resolve(root, "pop_windows/mq_front_adr", "tcp://127.0.0.1:9050");

    g_conf_jc_front_adr = zz_config_resolve(root, "jc/mq_end_adr", "tcp://127.0.0.1:8050");

    printf("pop_mobile : %s, pop_window:%s, jc:%s, http_clean:%s, tco_reassemble:%s \n",
            enable_pop_mobile,enable_pop_window, enable_jc, enable_http_clean, enable_tcp_reassemble);
    printf("capture type:%s, capture dev:%s, thread num:%d, send dev:%s, name_list:%d, config refresh:%d\n",
            g_conf_capture_type, g_conf_cap_device, g_conf_capture_thread_num, g_conf_send_device, g_conf_pop_name_list, g_confg_jc_site_load_period);
    printf("src mac:%02X,%02X,%02X,%02X,%02X,%02X, dst mac:%02X,%02X,%02X,%02X,%02X,%02X\n",
            g_conf_jc_src_mac[0],g_conf_jc_src_mac[1],g_conf_jc_src_mac[2],g_conf_jc_src_mac[3],g_conf_jc_src_mac[4],g_conf_jc_src_mac[5],
            g_conf_jc_dst_mac[0],g_conf_jc_dst_mac[1],g_conf_jc_dst_mac[2],g_conf_jc_dst_mac[3],g_conf_jc_dst_mac[4],g_conf_jc_dst_mac[5]);

}



int load_jc_filer_ip_cfg(char * rule_jc_filter_ips[64])
{
    printf ("begin load load_jc_filer_ip_cfg: \n");
    zzconfig_t * root = zz_config_load (FILE_DC_CONF);

    zzconfig_t * child = zz_config_locate(root, "jc/filter_ip");
    zzconfig_t * child_ip = zz_config_child(child);
    int j = 0;
    while (child_ip)
    {
        rule_jc_filter_ips[j]= zz_config_value(child_ip);
        printf("%d jc filtered ip %s\n", j, rule_jc_filter_ips[j]);
        child_ip = zz_config_next(child_ip);
        j++;
    }
    return j;
}

int load_jc_cfg(struct JC_S * jc_sites)
{
    //printf ("begin load load_jc_cfg: \n");
    zzconfig_t * root = zz_config_load (FILE_JC_CONF);

    int i = 0;
    zzconfig_t * child = zz_config_child(root);
    while (child)
    {
        char * enable =  zz_config_resolve(child, "enable", "yes");
        if(strcmp(enable, "yes") != 0)
        {
            continue;
        }

        jc_sites[i].name = zz_config_value(child);
        jc_sites[i].host =  zz_config_resolve(child, "host", "");
        jc_sites[i].host_len = strlen(jc_sites[i].host );
        jc_sites[i].uri =  zz_config_resolve(child, "uri", "");
        jc_sites[i].uri_len = strlen(jc_sites[i].uri );
        jc_sites[i].percent =  atoi(zz_config_resolve(child, "percent", ""));

        int err = regcomp(&jc_sites[i].uri_re, jc_sites[i].uri, REG_EXTENDED);
        char errbuf [128];
        if (err)
        {
            regerror(err, &jc_sites[i].uri_re, errbuf, sizeof(errbuf));
            printf("error: regcomp: %s\n", errbuf);
            return -1;
        }

        zzconfig_t * child_dst = zz_config_locate(child, "dst");
        zzconfig_t * child_dst_url = zz_config_child(child_dst);
        int j = 0;
        while (child_dst_url)
        {
            jc_sites[i].dst_url[j] = zz_config_value(child_dst_url);
            child_dst_url = zz_config_next(child_dst_url);
            j++;
        }
        jc_sites[i].dst_url_len = j;
        jc_sites[i].dst_rodom_index = 0;
        jc_sites[i].curr_times = 0;
        jc_sites[i].curr_pv = 0;

        //printf("%i jc name %s, host %s, dst count %d\n", i, jc_sites[i].name, jc_sites[i].host, j);

        child = zz_config_next(child);
        i++;
    }
    return i;
}

int load_record_url_cfg(struct RECORD_URL_S * record_url)
{
    //printf ("begin load load_record_url_cfg: \n");

    FILE *fp= fopen(FILE_RECORD_CONF,"r");
    if(!fp) return -1;

    int i = 0;
    while(!feof(fp))
    {
        char wd[512]={'\0'};
        fgets(wd, 512, fp);
        wd[strlen(wd) - 1] = '\0';

        if(strlen(wd)<5)
            break;

        char * uri_index = strstr(wd, "/");
        if(!uri_index)
            continue;

        record_url[i].host = malloc(512);
        memcpy(record_url[i].host, wd, uri_index-wd);
        record_url[i].host[uri_index-wd] = '\0';
        record_url[i].host_len = strlen(record_url[i].host );

        record_url[i].uri = malloc(512);
        strcpy(record_url[i].uri, uri_index);
        record_url[i].uri_len = strlen(record_url[i].uri );

//        int err = regcomp(&record_url[i].uri_re, record_url[i].uri, REG_EXTENDED);
//        char errbuf [128];
//        if (err)
//        {
//            regerror(err, &record_url[i].uri_re, errbuf, sizeof(errbuf));
//            printf("error: regcomp: %s\n", errbuf);
//            return -1;
//        }
        //printf("host:%s, uri:%s, host_len:%d, uri_len:%d\n", record_url[i].host, record_url[i].uri,
          //      record_url[i].host_len, record_url[i].uri_len);
        i++;
    }
    printf("load record url %d\n", i);
    fclose(fp);

    return i;
}
