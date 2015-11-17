#include "dc_global.h"

/*global reference*/
extern void pop_mq_direct_send_crowd_msg(const int iTid, char* pcMessage, char *cTotalAdvIds, char* sAd);
extern bool check_ad_ua_white(MqHttpMessage* psMqHttpMes , char* adv);

#define MOBILE_HOST_LEN  4000000
#define MOBILE_AD_LEN  40960
#define MOBILE_AD_WHILE_CONF  "/home/DCServer/conf/mobile_white_ad_list/"
/*靠靠靠靠靠*/
char* g_pcUAType[2] = {"iPhone", "iPad"};

/*定投hash表*/
HashMap* g_pMobileHashMap[THREAD_NUM] = {NULL};
HashMap* g_pMobileAdHashMap = NULL;
/*key = ip, value = ad*/
extern HashMap* g_pRadiusHashMap;
extern pthread_rwlock_t radius_lock;

void rule_mobile_init_condition_hash()
{
	int i = 0;
	for(; i < THREAD_NUM; i++)
	{
		g_pMobileHashMap[i] = hash_map_new(MOBILE_HOST_LEN);
	}
}

static int load_mobile_ad_white_dictory()
{
	g_pMobileAdHashMap = hash_map_new(MOBILE_AD_LEN);

	char sFileName[2048] = {0};
	DIR * dirp; 
	dirp = opendir(MOBILE_AD_WHILE_CONF);
	if(dirp == NULL) 
	{ 
		return -1;
	}

	struct dirent* direntp;
	struct stat st; 
	while((direntp = readdir(dirp)) != NULL)
	{
		if((strcmp(direntp->d_name,".")!=0) && (strcmp(direntp->d_name,"..")!=0))
		{
			memset(sFileName, '\0', 2048);
			strcpy(sFileName, MOBILE_AD_WHILE_CONF);
			strcat(sFileName, direntp->d_name);
			if(stat(sFileName, &st) == -1)
			{
				printf("get %s file info failed\n", sFileName);				
				continue;
			}
			if((st.st_mode& S_IFMT) == S_IFDIR)
			{
					printf("is a dir\n");
			}
			else
			{
				printf("%s\n", sFileName);
				hash_map_read_file(g_pMobileAdHashMap, sFileName);
				printf("mobile ad white list hash table size : %d\n", g_pMobileAdHashMap->size);	
			}
		}
	}
	if(dirp != NULL) closedir(dirp);
	return 0;
}

static bool rule_pop_nopass_send_mobile_message(const int iTid, MqHttpMessage* psMqHttpMes, char* sAd)
{
	char szADUA[1024]={0};
	strcpy(szADUA, sAd);
	strcat(szADUA, psMqHttpMes->pcUa);
	FilterUserInfo* pUserInfo = NULL;
	int iRes = hash_map_find(g_pMobileHashMap[iTid], szADUA, strlen(szADUA), (long*)&pUserInfo);
	if (iRes >= 0)
	{
		if (pUserInfo->iCount >= g_conf_pop_mobile_max_num || (psMqHttpMes->lSec - pUserInfo->lTime) < g_conf_pop_mobile_delay)
		{
			return false;
		}

		pUserInfo->lTime = psMqHttpMes->lSec;
		pUserInfo->iCount++;
		return true;
	}

	pUserInfo = (FilterUserInfo*)malloc(sizeof(FilterUserInfo));
	memset(pUserInfo, 0, sizeof(FilterUserInfo));
	pUserInfo->iCount++;
	pUserInfo->lTime = psMqHttpMes->lSec;
	hash_map_insert(g_pMobileHashMap[iTid], szADUA, strlen(szADUA), (long*)pUserInfo);
	return true;
}

static bool rule_pop_is_send_mobile_message(const int iTid, MqHttpMessage* psMqHttpMes, char* sAd)
{
		if(sAd == NULL)
		{
			return false;
		}

        char szADUA[1024]={0};
        strcpy(szADUA, sAd);
        strcat(szADUA, psMqHttpMes->pcUa);
        FilterUserInfo* pUserInfo = NULL;
        int iRes = hash_map_find(g_pMobileHashMap[iTid], szADUA, strlen(szADUA), (long*)&pUserInfo);
        if (iRes >= 0)
        {
			printf("SERVER: thread[%d]--%s--%s--%s::target count : %d have targeted count : %d, targeted time : %d, first time %d, next target time %d\n", iTid, psMqHttpMes->pcSrcIP, sAd, psMqHttpMes->pcUa, pUserInfo->iCount, pUserInfo->iFactCount, pUserInfo->iFactTime, pUserInfo->lTime, psMqHttpMes->lSec);
			if(pUserInfo->iCount >= g_conf_pop_mobile_max_num)
			{
				return false;
			}
			if(pUserInfo->iFactCount < g_conf_pop_mobile_fact_num) //先判断实际打击的次数是不是达到了限量
			{
				if(pUserInfo->iFactTime == 0)
				{
					// 这是第一次打击
					if(psMqHttpMes->lSec - pUserInfo->lTime < g_conf_pop_mobile_factdelay)
					{
						//说明这一次没有超过15分钟直接返回
						return false;
					}
					else
					{
						//超过15分钟了在打击一次
						pUserInfo->iCount++;
						pUserInfo->lTime = psMqHttpMes->lSec;
						return true;
					}
				}
				else if(pUserInfo->iFactTime > 0)
				{
					//这是说明打击成功了
					if(psMqHttpMes->lSec - pUserInfo->iFactTime < g_conf_pop_mobile_delay)
					{
						//打击成功以后过了一个小时以后再次打击
						return false;
					}
					else
					{
						//过了一个小时了再次打击
						pUserInfo->iCount++;
						pUserInfo->lTime = psMqHttpMes->lSec;
						pUserInfo->iFactTime = 0;
                				return true;
					}
				}
			}
			return false;
		}

        pUserInfo = (FilterUserInfo*)malloc(sizeof(FilterUserInfo));
        memset(pUserInfo, 0, sizeof(FilterUserInfo));
        pUserInfo->iCount++;
        pUserInfo->lTime = psMqHttpMes->lSec;
        hash_map_insert(g_pMobileHashMap[iTid], szADUA, strlen(szADUA), (long*)pUserInfo);
        return true;
}

/*靠靠*/
static bool kill_apple( MqHttpMessage* psMqHttpMes )
{
        int t = 0;
        for (; t < 2; t++)
        {
                if(strstr(psMqHttpMes->pcNoEncUa, g_pcUAType[t])!= NULL)
                {
                        return false;
                }
        }
        return true;
}


bool check_mobile_ad_white( MqHttpMessage* psMqHttpMes, char* adv )
{
	char* begin = "__biz=" ;
	char* end = "&" ;

	char Ad[512] = {0};
	char* AdBegin = strstr(psMqHttpMes->pcUri, begin);
	if(AdBegin == NULL)
	{
		return false;
	}
	AdBegin = AdBegin + strlen(begin);
	char* AdEnd = strstr(psMqHttpMes->pcUri, end);
	if(NULL == AdEnd)
	{
		return false;
	}

	if(AdEnd - AdBegin <= 0)
	{
		return false;
	}
	memcpy(Ad, AdBegin, AdEnd - AdBegin);
	printf("AD : %s\n", Ad);
	int ret = hash_map_find(g_pMobileAdHashMap, Ad, strlen(Ad), NULL);
	if(ret >= 0)
	{
		return true;
	}
	return false;
}

void mobile_client_init()
{
	rule_mobile_init_condition_hash();
	load_mobile_ad_white_dictory();
}

int rule_mobile_pop_main_loop(const int iTid, char* pcMessage, const MqHttpMessage* psMqHttpMes)
{
		char *adv = NULL;
		pthread_rwlock_rdlock(&radius_lock);
		int nRadiusRet = hash_map_find(g_pRadiusHashMap, psMqHttpMes->pcSrcIP, strlen(psMqHttpMes->pcSrcIP), (long*)&adv);
		pthread_rwlock_unlock(&radius_lock);
		if (nRadiusRet < 0)
		{
			return -1;
		}
		if(!check_mobile_ad_white(psMqHttpMes, adv))
		{   
			printf("not in ad ua white list \n"); 
			return -1;
		}

        printf("UA : %s\n", psMqHttpMes->pcNoEncUa);
		/*if(!kill_apple(psMqHttpMes))
		{
			return false;
		}*/
				//属于定投网址的分开投，防止出现白框
		if(rule_pop_nopass_send_mobile_message(iTid, psMqHttpMes, adv))
		{
			pop_mq_direct_send_mobile_msg(iTid, pcMessage, "4", adv);
		}	
}
