#include "dc_global.h"

char * g_conf_jc_front_adr = "tcp://127.0.0.1:8010";
char g_conf_jc_src_mac[6];
char g_conf_jc_dst_mac[6];

//jc每个用户每个策略打击的次数
int g_conf_jc_limit;

//jc每个用户打击次数
int g_conf_jc_total_limit;

static void string_to_mac(const char * mac_string, char *mac)
{
	int i = 0;
	char * curr_dot_index = strstr(mac_string, ",");
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


void load_root_jc_cfg(zzconfig_t * root)
{
	char * jc_limit = zz_config_resolve(root, "jc/jc_limit", "5");
	printf("jc/jc_limit=%s\n", jc_limit);
	g_conf_jc_limit = atoi(jc_limit);
	g_conf_jc_limit = g_conf_jc_limit > 500 ? 50 :g_conf_jc_limit;

	char * jc_total_limit = zz_config_resolve(root, "jc/jc_total_limit", "10");
	printf("jc/jc_limit=%s\n", jc_total_limit);
	g_conf_jc_total_limit = atoi(jc_total_limit);
	g_conf_jc_total_limit = g_conf_jc_total_limit > 500 ? 50 :g_conf_jc_total_limit;

	g_conf_send_device = zz_config_resolve(root, "jc/send_dev", "em1");

	char * jc_src_mac = zz_config_resolve(root, "jc/src_mac", "");

	char * jc_dst_mac = zz_config_resolve(root, "jc/dst_mac", "");

	string_to_mac(jc_src_mac, g_conf_jc_src_mac);
	string_to_mac(jc_dst_mac, g_conf_jc_dst_mac);

	g_conf_jc_front_adr = zz_config_resolve(root, "jc/mq_end_adr", "tcp://127.0.0.1:8010");

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


int load_jc_site_cfg(struct JC_S * jc_sites)
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
		jc_sites[i].uri_len = strlen(jc_sites[i].uri);
		jc_sites[i].percent =  atoi(zz_config_resolve(child, "percent", ""));
		jc_sites[i].type =  atoi(zz_config_resolve(child, "type", "0"));
		jc_sites[i].delay =  atoi(zz_config_resolve(child, "delay", "3600"));
		jc_sites[i].flag = zz_config_resolve(child, "flag", "");

		printf("load_jc_cfg name=%s host=%s delay=%d type=%d flag=%s\n", 
			jc_sites[i].name, jc_sites[i].host, jc_sites[i].delay, jc_sites[i].type, jc_sites[i].flag);

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

		child = zz_config_next(child);
		i++;
	}
	return i;
}
