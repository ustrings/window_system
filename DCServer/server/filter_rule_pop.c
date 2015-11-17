/*
* pop rule
* we check the host and uri, then make a zero mq msg to the adForce server
* there are 3 detail rules
* 1. search a key world
* 2. explorer a web url that one of white name list
* 3. not exceed N times
*/

#include "dc_global.h"
#include "md5.h"
#include "ip2address.h"
#include "data_pass_back.h"
#include "context_monitor.h"

/*global reference*/

extern void pop_mq_direct_send_surplus_msg(const int iTid, char* pcMessage,char* szTypeStr,char *cTotalAdvIds, char* sAd);
extern void pop_mq_direct_send_crowd_msg(const int iTid, char* pcMessage, char* szTypeStr,char *cTotalAdvIds, char* sAd);
extern int load_pop_filer_ip_cfg(char * rule_pop_white_ips[64]);
extern void tool_redis_helper_init();
extern void mq_pop_win_client_init();
extern void mq_redis_store_client_init();
extern unsigned int hash_map_hash_value(char* start, int len);
extern void tool_file_read_content(char* filename, SFileContent* content);

bool check_ad_ua_white(MqHttpMessage* psMqHttpMes , char* adv);

#define TOTAL_IP_SUM 250000
#define POP_HISTORY_IPUA_LEN 40960
#define SITE_WHITE_SUM 10000
#define SITE_TOTAL_WHITE_SUM 100000
#define SITE_SURPLUS_SUM 1000
#define IP_BLACK_SUM 1000000
#define HTML_TYPE_SIZE 3
#define AD_WHITE_LIST_LEN 2097152
#define TOTAL_AD_LEN 8388608
#define POP_TARGET_LEN 40960
#define SITE_BLACK_SUM 10000

/*ip 白名单*/
char * rule_pop_white_ips[64];

static int rule_pop_white_ip_len = 0;

static long rule_pop_redis_refresh_ts[16] = {0};


static HashMap* g_pBlackIpHashMap = NULL;

/*弹窗网站的白名单*/
static HashMap* g_pWhiteSiteHashMap = NULL;

HashMap* g_pBlackUriHashMap = NULL;

/*剩余网站的白名单*/
HashMap* g_pSurplusSiteHashMap = NULL;

/*定投hash表*/
HashMap* g_pTargetedPopHashMap = NULL;

/*定投hash表*/
HashMap* g_pTargetedPopHashMap1 = NULL;

/*域名黑名单表*/
HashMap* g_pBlackSiteHashMap = NULL;

/*域名白名单表*/
HashMap* g_pTotalWhiteSiteHashMap = NULL;

/*存储符合条件的IP和UA*/
HashMap* g_pPopIpHashMaps[THREAD_NUM] = {NULL};

/*存储符合条件的IP信息*/
HashMap* g_pFilterConditionIP[THREAD_NUM] = {NULL};
HashMap* g_pFilterConditionAD[THREAD_NUM] = {NULL};

/*存储符合条件的IP信息*/
HashMap* g_pSurplusIP[THREAD_NUM] = {NULL};

HashMap* g_pSurplusAD[THREAD_NUM] = {NULL};

HashMap* g_pTargetIP[THREAD_NUM] = {NULL};

HashMap* g_pTotalAD[THREAD_NUM] = {NULL};

HashMap* g_pOneCountIP[THREAD_NUM] = {NULL};

HashMap* g_pTargetIP1[THREAD_NUM] = {NULL};

/*存储所有的IP信息*/
HashMap* g_pTotalIP[THREAD_NUM] = {NULL};

//指定用户IP+UA投放
static HashMap* g_pSpecifyAdUa = NULL;

HashMap * g_pAdWhiteHashMap = NULL;

/*md5[adua] , value = adid*/
static HashMap* g_pAdUaHashMap;

//IP区域投放过滤信息
static IP2AddressData *g_pIPAreaFilterData;

//回传类
struct pass_back* g_pass;

//投放间隔
static int g_iPushInteral = 10;

/*存储jc的个数*/
int g_iReceiverNum[THREAD_NUM] = {0};

int g_iJCSenderNum[THREAD_NUM] = {0};

unsigned int g_iTotalNum[THREAD_NUM] = {0};

/*弹窗的html页面类型*/
static char* g_pcHtmlType[HTML_TYPE_SIZE] = {".htm", ".html", ".shtml"};

static SFileContent* g_IllegalWords;

extern pthread_rwlock_t adua_lock;
extern pthread_rwlock_t radius_lock;
extern HashMap* g_pRadiusHashMap;
extern HashMap* g_pSearchAdUaHashMap[THREAD_NUM];

/*init pop filtered ips*/
static void rule_pop_init_condition_hash()
{
	int i = 0;
	for(; i < THREAD_NUM; i++)
	{
		g_pFilterConditionIP[i] = hash_map_new(POP_HISTORY_IPUA_LEN);
		g_pFilterConditionAD[i] = hash_map_new(POP_HISTORY_IPUA_LEN);
	}

	i = 0;
	for(; i < THREAD_NUM; i++)
	{
		g_pSurplusIP[i] = hash_map_new(TOTAL_IP_SUM);
		g_pSurplusAD[i] = hash_map_new(TOTAL_IP_SUM);
        	g_pTotalIP[i] = hash_map_new(TOTAL_IP_SUM);
        	g_pTotalAD[i] = hash_map_new(TOTAL_IP_SUM);
        	//g_pOneCountIP[i] = hash_map_new(TOTAL_IP_SUM);
		g_pTargetIP[i] = hash_map_new(TOTAL_IP_SUM);
		g_pTargetIP1[i] = hash_map_new(TOTAL_IP_SUM);
	}
	
	g_pSpecifyAdUa = hash_map_new(1024);
	g_pAdUaHashMap = hash_map_new(ADUA_RULE_LEN);
}

static void rule_pop_load_illegal_words()
{
	g_IllegalWords = (SFileContent*)malloc(sizeof(SFileContent));
	memset(g_IllegalWords, 0, sizeof(SFileContent));
	tool_file_read_content(ILLEGAL_WORD_CONF, g_IllegalWords);
	printf("rule_pop_load_illegal_words size=%d \n", g_IllegalWords->iNum);
}

/*init pop filtered ips*/
static void rule_pop_load_filter_ip()
{
	rule_pop_white_ip_len = load_pop_filer_ip_cfg(rule_pop_white_ips);
}

/*init pop history ips*/
static void rule_pop_load_history_ip()
{
	//tool_redis_helper_init();
	int i = 0;
	for(; i<THREAD_NUM; i++)
	{
		g_pPopIpHashMaps[i] = hash_map_new(POP_HISTORY_IPUA_LEN);
		rule_pop_redis_refresh_ts[i] = 0;
	}
}

/*load pop black host*/
void rule_pop_load_black_host()
{
        g_pBlackSiteHashMap = hash_map_load_file(SITE_BLACK_CONF, SITE_BLACK_SUM);
        printf("rule_pop_load_black_host size=%d \n", g_pBlackSiteHashMap->size);
}

/*init pop filtered urls*/
bool rule_pop_load_file_host( char* host)
{
	if(hash_map_find(g_pBlackSiteHashMap, host, strlen(host), NULL) >= 0) 
	{    
		return true;
	}

	return false;
}

bool rule_pop_load_black_uri(MqHttpMessage* psMqHttpMes)
{
	char szUri[1024] = {0};
	strcat(szUri, psMqHttpMes->host);
	strcat(szUri, psMqHttpMes->pcUri);
	if(hash_map_find(g_pBlackUriHashMap, szUri, strlen(szUri), NULL) >= 0)
	{
		return false;
	}
	return true;
}

/************************************************************************/
//  [10/13/2014 zhaoyunzhou]   
//  load pop_target directory
/************************************************************************/
static int pop_target_load_query_dictory()
{
	g_pTargetedPopHashMap = hash_map_new(POP_TARGET_LEN);

	char sFileName[2048] = {0};
	DIR * dirp; //directory pointer
	dirp = opendir(FILE_POP_TARGET_CONF);
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
			strcpy(sFileName, FILE_POP_TARGET_CONF);
			strcat(sFileName, direntp->d_name);
			if(stat(sFileName, &st) == -1)
			{
				printf("get %s file info failed\n", sFileName);				
				continue;
			}
			if((st.st_mode& S_IFMT) == S_IFDIR)//judge is or not directory
			{
				//char szNewPath[2048] = {0};
				//strcpy(szNewPath, FILE_POP_URL_CONF);
				//strcat(szNewPath, direntp->d_name);
				//rule_pop_load_query_content(szNewPath);
			}
			else
			{
				printf("%s\n", sFileName);
				hash_map_read_file(g_pTargetedPopHashMap, sFileName);
				printf("pop_target_host hash table size : %d\n", g_pTargetedPopHashMap->size);	
			}
		}
	}
	if(dirp != NULL) closedir(dirp);
	return 0;
}

/************************************************************************/
//  [12/10/2014 zhaoyunzhou]   
//  load pop_target1 directory
/************************************************************************/
static int pop_target1_load_query_dictory()
{
        g_pTargetedPopHashMap1 = hash_map_new(POP_TARGET_LEN);

        char sFileName[2048] = {0};
        DIR * dirp; 
        dirp = opendir(FILE_POP_TARGET_CONF1);
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
                        strcpy(sFileName, FILE_POP_TARGET_CONF1);
                        strcat(sFileName, direntp->d_name);
                        if(stat(sFileName, &st) == -1)
                        {    
                                printf("get %s file info failed\n", sFileName);     
                                continue;
                        } 
                        if((st.st_mode& S_IFMT) == S_IFDIR)//judge is or not directory
                        {
					//
                        }
                        else
                        {
                                printf("%s\n", sFileName);
                                printf("pop_target_host1 hash table size : %d\n", g_pTargetedPopHashMap1->size);
                        }
                }
        }
        if(dirp != NULL) closedir(dirp);
        return 0;
}

/************************************************************************/
//  [9/3/2014 zhaoyunzhou]   
//  load pop_url directory
/************************************************************************/
static int pop_url_load_query_dictory()
{
	g_pWhiteSiteHashMap = hash_map_new(SITE_WHITE_SUM);

	char sFileName[2048] = {0};
	DIR * dirp; //directory pointer
	dirp = opendir(FILE_POP_URL_CONF);
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
			strcpy(sFileName, FILE_POP_URL_CONF);
			strcat(sFileName, direntp->d_name);
			if(stat(sFileName, &st) == -1)
			{
				printf("get %s file info failed\n", sFileName);				
				continue;
			}
			if((st.st_mode& S_IFMT) == S_IFDIR)//judge is or not directory
			{
				//char szNewPath[2048] = {0};
				//strcpy(szNewPath, FILE_POP_URL_CONF);
				//strcat(szNewPath, direntp->d_name);
				//rule_pop_load_query_content(szNewPath);
			}
			else
			{
				printf("%s\n", sFileName);
				hash_map_read_file(g_pWhiteSiteHashMap, sFileName);
				printf("pop_url hash table size : %d\n", g_pWhiteSiteHashMap->size);	
			}
		}
	}
	if(dirp != NULL) closedir(dirp);
	return 0;
}


/************************************************************************/
//   载入IP黑名单                                                                  
/************************************************************************/
static void rule_pop_load_black_ip()
{
	g_pBlackIpHashMap = hash_map_load_file(IP_BALACK_CONF, IP_BLACK_SUM);
	printf("rule_pop_load_black_ip size=%d \n", g_pBlackIpHashMap->size);
}

/************************************************************************/
//  [9/12/2014 zhaoyunzhou]                                               
//   load ad white                                                           
/************************************************************************/
static void rule_pop_load_ad_white_ip()
{
	g_pAdWhiteHashMap = hash_map_load_file(AD_WHITE_CONF, AD_WHITE_LIST_LEN);
	printf("rule_pop_load_ad_white_ip size=%d \n", g_pAdWhiteHashMap->size);
}


/************************************************************************/                                          
//  对ip和ua进行MD5加密                                                                   
/************************************************************************/
void rule_pop_encrypt_ipua_md5(const char* ipua, char* md5_ipua)
{
	MD5_CTX md5;  
	MD5Init(&md5);   

	unsigned char decrypt[16];   
	MD5Update(&md5, (unsigned char*)ipua, strlen((char *)ipua));  
	MD5Final(&md5, decrypt);  

	int i=0;
	for(i=0; i<16; i++)  
	{  
		sprintf(md5_ipua, "%02x", decrypt[i]);
		md5_ipua = md5_ipua + 2;
	}
}

/************************************************************************/
//  [8/21/2014 zhaoyunzhou]                                               
//  pop ad by ad+ua info                                                  
/************************************************************************/
static bool radius_pop_check_pop_ip(int iTid, MqHttpMessage* psMqHttpMes, char* adv)
{
	if(adv == NULL)
	{
		return false;
	}
	/*make a ip_ua string*/
	int iIPStrLen = strlen(psMqHttpMes->pcSrcIP);
	int iUaStrLen = strlen(psMqHttpMes->pcNoEncUa);
	if (iIPStrLen + iUaStrLen >= 512)
	{
		return false;
	}

	char szADUA[1024] = {0};
	strcpy(szADUA, adv);
	strcat(szADUA, psMqHttpMes->pcNoEncUa);
	char* padvId = NULL;
	
	int ret = hash_map_find(g_pSearchAdUaHashMap[iTid], szADUA, strlen(szADUA), (long*)&padvId);
	if(ret < 0 )
	{
		return false;
	}

	return true;
}

/************************************************************************/                                             
//  pop ad by ad+ua info                                                  
/************************************************************************/
static bool radius_pop_check_offline_crowd(int thread_id, MqHttpMessage* psMqHttpMes, char* sAd)
{
	if(sAd == NULL)
	{
		return false;
	}
	/*make a ip_ua string*/
	int iIPStrLen = strlen(psMqHttpMes->pcSrcIP);
	int iUaStrLen = strlen(psMqHttpMes->pcUa);
	if (iIPStrLen + iUaStrLen >= 512)
	{
		return false;
	}

	char cAdMD5s[33] = {'\0'};
	rule_pop_encrypt_ipua_md5(sAd, cAdMD5s);

	char cUaMD5s[33] = {'\0'};
	rule_pop_encrypt_ipua_md5(psMqHttpMes->pcUa, cUaMD5s);

	char cAdUaMD5[33] = {'\0'};
	memcpy(cAdUaMD5, cAdMD5s, 16);
	memcpy((void*)(cAdUaMD5+16), cUaMD5s, 16);

	pthread_rwlock_rdlock(&adua_lock);
	int ret = hash_map_find(g_pAdUaHashMap, cAdUaMD5, strlen(cAdUaMD5), NULL);
	pthread_rwlock_unlock(&adua_lock);

	if(ret >= 0)
	{
		return true;
	}

	return false;
}

/************************************************************************/
//  检查此用户是否在即搜即投和追投的人群中                                                                    
/************************************************************************/
static int rule_pop_check_pop_ip(int thread_id, u_int curr_second, MqHttpMessage* psMqHttpMes, int * ad_id)
{
	/*make a ip_ua string*/
	int iIPStrLen = strlen(psMqHttpMes->pcSrcIP);
	int iUaStrLen = strlen(psMqHttpMes->pcUa);
	if (iIPStrLen + iUaStrLen >= 512)
	{
		return 0;
	}

	char cIPUas[512] = {'\0'};
	memcpy(cIPUas, psMqHttpMes->pcSrcIP, iIPStrLen);
	memcpy(cIPUas + iIPStrLen, psMqHttpMes->pcUa, iUaStrLen);
	cIPUas[iIPStrLen + iUaStrLen + 1] = '\0';

	//判断是否符合关键字过滤
	AdvId* sAdvId = NULL;

	if (hash_map_find(g_pPopIpHashMaps[0], cIPUas, strlen(cIPUas), (long*)&sAdvId) >= 0)
	{
		*ad_id = sAdvId->advId[0];
		return 1;
	}

	if (0 == g_conf_pop_ip_append)
	{
		return 1;
	}

	char cIPUaMD5s[33] = {'\0'};
	rule_pop_encrypt_ipua_md5(cIPUas, cIPUaMD5s);

	SPopUserContent sUserContent;
	sUserContent.ad_id = 0;
	sUserContent.ts = 0;

	int result = tool_reids_helper_get_advid(&sUserContent, cIPUaMD5s);
	if (1 != result)
	{
		return -1;
	}

	*ad_id = sUserContent.ad_id;

	if (curr_second - sUserContent.ts < g_conf_suplus_delay)
	{
		printf("md5_ipua:%s, time out %ld\n", cIPUas, curr_second - sUserContent.ts);
		return -1;
	}

	return 1;
}

/*check host url white name list*/
static bool rule_pop_check_host(char* pcHost)
{
	if(g_conf_pop_name_list == 0)
	{
		if (hash_map_find(g_pWhiteSiteHashMap, pcHost, strlen(pcHost), NULL) < 0)
			return false;
		else
			return true;
	}
	else
	{
		if (hash_map_find(g_pWhiteSiteHashMap,  pcHost, strlen(pcHost), NULL) < 0)
			return true;
		else
			return false;
	}

}

/*check ip white name list*/
static int rule_pop_filter_ip(char* cpSrcIP, int iTid, char* adv)
{
	if (hash_map_find(g_pBlackIpHashMap, cpSrcIP, strlen(cpSrcIP), NULL) >= 0)
	{
		return -1;
	}

	if(rule_pop_white_ip_len == 0)
	{
		return 1;
	}
	
	int i;

	if(NULL  == adv)
	{
		return false;
	}
	for(i=0; i < rule_pop_white_ip_len; i++)
	{
		//if(strcmp(rule_pop_white_ips[i], cpSrcIP) == 0)
		if(adv != NULL && strcmp(rule_pop_white_ips[i], adv) == 0)
		{
			//printf("the ip is equal %s\n", adv);
			return 1;
		}
	}

	return -1;
}

bool check_ad_ua_white( MqHttpMessage* psMqHttpMes, char* adv )
{
	return true;
}

/************************************************************************/
// 载入IP信息                                                                    
/************************************************************************/
static void initIPAreaFilter()
{
	/*g_pIPAreaFilterData = ip2address_init("../conf/ip2address_utf8.txt");
	if(NULL == g_pIPAreaFilterData)
	{
		fprintf(stderr, "in %s : ip2address_init error\n", __FUNCTION__);
	}*/
}

static void pass_back_init()
{
	g_pass =  create_pass_back();
        int ret = pthread_create(&g_pass->pass_thread, NULL, get_data_pass_back, g_pass);
        if (ret != 0)  
        {   
                printf("Create pthread error!\n");  
                return ;  
        }	
}

/*inti pop windows*/
int rule_pop_init()
{
	/*初始化存储符合条件的IP列表*/
	rule_pop_init_condition_hash();

	/*init mq to adForce*/
	mq_pop_win_client_init();

	/*redis store target count zeromq*/
	mq_redis_store_client_init();

	/*load filter ip*/
	rule_pop_load_filter_ip();

	pop_url_load_query_dictory();
	/*load history ip list*/
	rule_pop_load_history_ip();

	//载入IP黑名单
	rule_pop_load_black_ip();

	rule_pop_load_illegal_words();

	initIPAreaFilter();

	/*回传初始化*/
	pass_back_init();

	return 0;
}

/*init pop ip ua hash table*/
static void rule_pop_refresh(int thread, long curr_ts)
{
	if((curr_ts - rule_pop_redis_refresh_ts[thread]) > g_conf_pop_ip_refresh)
	{
		//tool_redis_helper_refresh_data(rule_pop_history_ip_hash_table[thread]);
		//rule_pop_redis_refresh_ts[thread] = curr_ts;

		//printf("loaded new conf thread %d at %ld\n", thread, curr_ts);
	}
}

/************************************************************************/
//  判断请求页面是否为首页或者html页面                                                                   
/************************************************************************/
static bool rule_pop_is_html(MqHttpMessage* psMqHttpMes)
{
	int iUriLen = strlen(psMqHttpMes->pcUri); 

	/*only pop main page*/
	if(((iUriLen == 1) && (*psMqHttpMes->pcUri == '/')) || ( (*psMqHttpMes->pcUri == '/') && (*(psMqHttpMes->pcUri+1) == '?')))
	{
		return true;
	}

	//printf("rule_pop_is_html host=%s uri=%s \n", psMqHttpMes->host, psMqHttpMes->pcUri);

	
	int t = 0;
	char* pcUriEnd = psMqHttpMes->pcUri + iUriLen - 1;
	char* end = strchr(psMqHttpMes->pcUri, '?');
	char szBuff[512] = {0};
	if(end)
	{
		int endlen = end - psMqHttpMes->pcUri ;
		if(endlen > 512)
		{
			return false;
		}
		strncpy(szBuff, psMqHttpMes->pcUri, endlen);
		if(endlen < 6)
		{
			return false;
		}
	}
	else
	{
		if(iUriLen < 6 || iUriLen > 512)
		{
			return false;
		}
	}
	for (; t < HTML_TYPE_SIZE; t++)
	{
		int iHtmlTypeLen = strlen(g_pcHtmlType[t]);
		int i = 0;
		for (; i < iHtmlTypeLen; i++)
		{
			if(end)
			{
				if (g_pcHtmlType[t][iHtmlTypeLen - i - 1] != *(szBuff+strlen(szBuff)-1 - i))
				{
					break;
				}
			}
			else
			{
				if (g_pcHtmlType[t][iHtmlTypeLen - i - 1] != *(pcUriEnd - i))
				{ 
					break;
				}	
			}
		}

		if (i == iHtmlTypeLen)
		{
			//printf("rule_pop_is_html is html host=%s uri=%s\n", psMqHttpMes->host, psMqHttpMes->pcUri);
			return true;
		}
	}

	return false;
}

/************************************************************************/
//  判断请求页面是否为首页或者html页面                                                                   
/************************************************************************/
static bool rule_pop_is_white_site(MqHttpMessage* psMqHttpMes)
{
	/*1. check host url*/
	if(!rule_pop_check_host(psMqHttpMes->host))
	{
		//printf("rule_pop_is_white_site come a %s.\n", psMqHttpMes->host);
		return false;
	}

	return rule_pop_is_html(psMqHttpMes);
}

/************************************************************************/
//  判断请求页面是否为首页或者html页面                                                                   
/************************************************************************/
static bool rule_pop_is_suplus_site(MqHttpMessage* psMqHttpMes)
{
	/*1. check host url*/
	if(hash_map_find(g_pSurplusSiteHashMap, psMqHttpMes->host, strlen(psMqHttpMes->host), NULL) < 0)
	{
		return false;
	}

	return rule_pop_is_html(psMqHttpMes);
}

static bool rule_pop_is_send_target_message(const int iTid, MqHttpMessage* psMqHttpMes, char* sAd)
{
	if(sAd == NULL)
	{
		return false;
	}
    g_iTotalNum[iTid]++;
    if (0 != g_iTotalNum[iTid] % 2)
    {
            return false;
    }

    char szADUA[1024]={0};
    strcpy(szADUA, sAd);
    strcat(szADUA, psMqHttpMes->pcUa);
    FilterUserInfo* pUserInfo = NULL;
    int iRes = hash_map_find(g_pTargetIP[iTid], szADUA, strlen(szADUA), (long*)&pUserInfo);
    if (iRes >= 0)
    {
		printf("SERVER: thread[%d]--%s--%s--%s::target count : %d have targeted count : %d, targeted time : %d, first time %d, next target time %d\n", iTid, psMqHttpMes->pcSrcIP, sAd, psMqHttpMes->pcUa, pUserInfo->iCount, pUserInfo->iFactCount, pUserInfo->iFactTime, pUserInfo->lTime, psMqHttpMes->lSec);
		if(pUserInfo->iCount >= g_conf_pop_white_host_max_num)
		{
			return false;
		}
		if(pUserInfo->iFactCount < g_conf_pop_white_host_fact_num) //先判断实际打击的次数是不是达到了限量
		{
			if(pUserInfo->iFactTime == 0)
			{
				// 这是第一次打击
				if(psMqHttpMes->lSec - pUserInfo->lTime < g_conf_pop_white_host_factdelay)
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
				if(psMqHttpMes->lSec - pUserInfo->iFactTime < g_conf_pop_white_host_delay)
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
    hash_map_insert(g_pTargetIP[iTid], szADUA, strlen(szADUA), (long*)pUserInfo);
    return true;
}

static bool rule_pop_is_send_target_message1(const int iTid, MqHttpMessage* psMqHttpMes, char* sAd)
{
	if(sAd == NULL)
	{
		return false;
	}
    g_iTotalNum[iTid]++;
    if (0 != g_iTotalNum[iTid] % 2)
    {
            return false;
    }

    char szADUA[1024]={0};
    strcpy(szADUA, sAd);
    strcat(szADUA, psMqHttpMes->pcUa);
    FilterUserInfo* pUserInfo = NULL;
    int iRes = hash_map_find(g_pTargetIP1[iTid], szADUA, strlen(szADUA), (long*)&pUserInfo);
    if (iRes >= 0)
    {
		printf("SERVER: thread[%d]--%s--%s--%s::target count : %d have targeted count : %d, targeted time : %d, first time %d, next target time %d\n", iTid, psMqHttpMes->pcSrcIP, sAd, psMqHttpMes->pcUa, pUserInfo->iCount, pUserInfo->iFactCount, pUserInfo->iFactTime, pUserInfo->lTime, psMqHttpMes->lSec);
		if(pUserInfo->iCount >= g_conf_pop_white_host_max_num1)
		{
			return false;
		}
		if(pUserInfo->iFactCount < g_conf_pop_white_host_fact_num1) //先判断实际打击的次数是不是达到了限量
		{
			if(pUserInfo->iFactTime == 0)
			{
				// 这是第一次打击
				if(psMqHttpMes->lSec - pUserInfo->lTime < g_conf_pop_white_host_factdelay1)
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
				if(psMqHttpMes->lSec - pUserInfo->iFactTime < g_conf_pop_white_host_delay1)
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
    hash_map_insert(g_pTargetIP1[iTid], szADUA, strlen(szADUA), (long*)pUserInfo);
    return true;
}

static bool rule_pop_ip_send_surplus_message(const int iTid, MqHttpMessage* psMqHttpMes)
{
	FilterUserInfo* pUserInfo = NULL;
	int iRes = hash_map_find(g_pOneCountIP[iTid], psMqHttpMes->pcSrcIP, strlen(psMqHttpMes->pcSrcIP), (long*)&pUserInfo);
	if(iRes >= 0)
	{
		return false;
	}
	pUserInfo = (FilterUserInfo*)malloc(sizeof(FilterUserInfo));
        memset(pUserInfo, 0, sizeof(FilterUserInfo));
        pUserInfo->iCount++;
        hash_map_insert(g_pOneCountIP[iTid], psMqHttpMes->pcSrcIP, strlen(psMqHttpMes->pcSrcIP), (long*)pUserInfo);
	return true;
}

static bool rule_pop_is_send_surplus_message(const int iTid, MqHttpMessage* psMqHttpMes, char* sAd)
{
    	/*g_iTotalNum[iTid]++;
    	if (0 != g_iTotalNum[iTid] % g_iPushInteral)
        {    
                return false;   
        } */   
	if(NULL == sAd)
	{
		return false;
	}    
        char szADUA[1024]={0};
        //strcpy(szADUA, psMqHttpMes->pcSrcIP);
        strcpy(szADUA, sAd);
        strcat(szADUA, psMqHttpMes->pcUa);
        long *count = NULL;
        int res = hash_map_find(g_pSurplusAD[iTid], sAd, sAd,(long*)&count);
        if(res >= 0)
        {    
                if((*count) >= g_conf_suplus_ad_max_num)
                {    
                        return false;
                }    
        }

	FilterUserInfo* pUserInfo = NULL;
	int iRes = hash_map_find(g_pSurplusIP[iTid], szADUA, strlen(szADUA), (long*)&pUserInfo);
	if (iRes >= 0)
	{
		//printf("SERVER: thread[%d]--%s--%s--%s::target count : %d have targeted count : %d, targeted time : %d, first time %d, next target time %d\n", iTid, psMqHttpMes->pcSrcIP, sAd, psMqHttpMes->pcUa, pUserInfo->iCount, pUserInfo->iFactCount, pUserInfo->iFactTime, pUserInfo->lTime, psMqHttpMes->lSec);
		if(pUserInfo->iCount >= g_conf_suplus_max_num)
		{
			return false;
		}
                if(pUserInfo->iFactCount < g_conf_suplus_fact_num) 
                {
                        if(pUserInfo->iFactTime == 0)
                        {
                                
                                if(psMqHttpMes->lSec - pUserInfo->lTime < g_conf_suplus_factdelay)
                                {
                                        
                                        return false;
                                }
                                else
                                {
                                        
                                        pUserInfo->iCount++;
                                        pUserInfo->lTime = psMqHttpMes->lSec;
                                        return true;
                                }
                        }
                        else if(pUserInfo->iFactTime > 0)
                        {
                               
                                if(psMqHttpMes->lSec - pUserInfo->iFactTime < g_conf_suplus_delay)
                                {
                                        
                                        return false;
                                }
                                else
                                {
                                        
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
	hash_map_insert(g_pSurplusIP[iTid], szADUA, strlen(szADUA), (long*)pUserInfo);
	return true;
}

/************************************************************************/
//  记录弹窗的用户                                                                  
/************************************************************************/
static bool rule_pop_is_send_pop_message(const int iTid, MqHttpMessage* psMqHttpMes, char* sAd)
{
	if(NULL  == sAd)
	{
		return false;
	}
	char szADUA[1024]={0};
	strcpy(szADUA, sAd);
	strcat(szADUA, psMqHttpMes->pcUa);
        long *count = NULL;
        int res = hash_map_find(g_pFilterConditionAD[iTid], sAd, sAd,(long*)&count);
        if(res >= 0)
        {    
                if((*count) >= g_conf_crowd_ad_max_num)
                {    
                        return false;
                }    
        }


	FilterUserInfo* pUserInfo = NULL;
	int iRes = hash_map_find(g_pFilterConditionIP[iTid], szADUA, strlen(szADUA), (long*)&pUserInfo);
	if (iRes >= 0)
	{
                if(pUserInfo->iCount >= g_conf_crowd_max_num)
                {
                        return false;
                }
                if(pUserInfo->iFactCount < g_conf_crowd_fact_num)
                {
                        if(pUserInfo->iFactTime == 0)
                        {

                                if(psMqHttpMes->lSec - pUserInfo->lTime < g_conf_crowd_factdelay)
                                {

                                        return false;
                                }
                                else
                                {

					pUserInfo->iCount++;
                                        pUserInfo->lTime = psMqHttpMes->lSec;
                                        return true;
                                }
                        }
                        else if(pUserInfo->iFactTime > 0)
                        {

                                if(psMqHttpMes->lSec - pUserInfo->iFactTime < g_conf_crowd_delay)
                                {

                                        return false;
                                }
                                else
                                {

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
	hash_map_insert(g_pFilterConditionIP[iTid], szADUA, strlen(szADUA), (long*)pUserInfo);

	return true;
}

/************************************************************************/
//  即搜即投的指定用户投放                                                                  
/************************************************************************/
static bool filter_ad_is_send_pop_message(const int iTid, MqHttpMessage* psMqHttpMes, char* sAd)
{
	if(NULL == sAd)
	{
		return false;
	}
	int i = 0;
	for (; i < g_filterAdAccount.len; i++)
	{
		if (0 == strcmp(g_filterAdAccount.pcPopWhiteAds[i], sAd))
		{
			if (g_filterAdAccount.userInfo[i].iCount >= g_conf_filter_ad_max_num 
				|| (psMqHttpMes->lSec - g_filterAdAccount.userInfo[i].lTime) < g_conf_filter_ad_delay)
			{
				return false;
			}
			printf("filter_ad_is_send_pop_message %s\n", sAd);
			g_filterAdAccount.userInfo[i].lTime = psMqHttpMes->lSec;
			g_filterAdAccount.userInfo[i].iCount++;
			return true;
		}
	}
	return false;
}


/************************************************************************/
//  [10/11/2014 zhaoyunzhou]
//  定向投放检查
/************************************************************************/

static bool specify_host_send_pop_message(const int iTid, MqHttpMessage* psMqHttpMes)
{
	int iRes = hash_map_find(g_pTargetedPopHashMap, psMqHttpMes->host, strlen(psMqHttpMes->host) , NULL);
	if(iRes < 0)
	{
		return false;
	}
		
	return rule_pop_is_html(psMqHttpMes);
}

static bool specify_host_send_pop_message1(const int iTid, MqHttpMessage* psMqHttpMes)
{
	int iRes = hash_map_find(g_pTargetedPopHashMap1, psMqHttpMes->host, strlen(psMqHttpMes->host) , NULL);
	if(iRes < 0)
	{
		return false;
	}

	return rule_pop_is_html(psMqHttpMes);
}

/************************************************************************/
//  记录用户指定AD投放                                                                  
/************************************************************************/
static bool specify_adua_send_pop_message(const int iTid, MqHttpMessage* psMqHttpMes, char* sAd)
{
	char szADUA[1024]={0};
	strcpy(szADUA, sAd);
	strcat(szADUA, psMqHttpMes->pcUa);

	FilterUserInfo* pUserInfo = NULL;
	int iRes = hash_map_find(g_pSpecifyAdUa, szADUA, strlen(szADUA), (long*)&pUserInfo);
	if (iRes >= 0)
	{
		//printf("sec : %ld count:%d time:%ld delaytime:%d\n", psMqHttpMes->lSec, pUserInfo->iCount,pUserInfo->lTime, g_conf_crowd_delay);
		if (pUserInfo->iCount > g_conf_specify_ad_max_num || (psMqHttpMes->lSec - pUserInfo->lTime) < g_conf_specify_ad_delay)
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
	hash_map_insert(g_pSpecifyAdUa, szADUA, strlen(szADUA), (long*)pUserInfo);
	return true;
}

/************************************************************************/
//  指定用户Ad投放                                                                  
/************************************************************************/
static int specify_ad_is_send_pop_message(const int iTid, MqHttpMessage* psMqHttpMes, char* sAd)
{
	if (NULL == sAd)
	{
		return 0;
	}

	int i = 0;
	for (; i < g_TotalAdAccount.len; i++)
	{
		if (0 == strcmp(g_TotalAdAccount.pcPopWhiteAds[i], sAd))
		{
			if (specify_adua_send_pop_message(iTid, psMqHttpMes, sAd))
			{
				return 1;
			}
			else
			{
				return 2;
			}
		}
	}

	return 0;
}

/************************************************************************/
//  [9/12/2014 zhaoyunzhou]                                               
//  check is or not have pop                                    
/************************************************************************/
static bool check_ad_ua_black(MqHttpMessage* psMqHttpMes , char* adv)
{
	if(NULL  == adv)
	{
		return false;
	}
	/*make a ip_ua string*/
	int iIPStrLen = strlen(psMqHttpMes->pcSrcIP);
	int iUaStrLen = strlen(psMqHttpMes->pcUa);
	if (iIPStrLen + iUaStrLen >= 512)
	{
		return false;
	}

	int adret = hash_map_find(g_pAdWhiteHashMap, adv, strlen(adv), NULL);
	if(adret >= 0)
	{
		return true;
	}

	return false;
}

/************************************************************************/
//  计算用户IP数量                                                               
/************************************************************************/
static void rule_pop_cal_total_ip(const int iTid, MqHttpMessage* psMqHttpMes,char* adv)
{
        if (50000 == g_pTotalIP[iTid]->size)
        {
                g_iPushInteral = 1;
        }


        char IPUA[530] = {0};
        strcpy(IPUA, psMqHttpMes->pcSrcIP);
        strcat(IPUA, psMqHttpMes->pcUa);
        int iRes = hash_map_find(g_pTotalIP[iTid], adv, strlen(adv), NULL);
        if (iRes >= 0)
        {
                return;
        }

        hash_map_insert(g_pTotalIP[iTid], adv, strlen(adv), NULL);
        return;
}

/************************************************************************/
//  是否在白名单里面                                                              
/************************************************************************/
static bool rule_pop_in_total_white_site(MqHttpMessage* psMqHttpMes)
{
	int iRes = hash_map_find(g_pTotalWhiteSiteHashMap, psMqHttpMes->host, strlen(psMqHttpMes->host) , NULL);
	if(iRes < 0)
	{
		return false;
	}

	return true;
}

static bool check_post_ad_count(char* adv, int iTid)
{
	if(NULL  == adv)
	{
		return false;
	}
	FilterUserInfo* pUserInfo = NULL;
	int iRes = hash_map_find(g_pTotalAD[iTid], adv, strlen(adv), (long*)&pUserInfo);
	if (iRes >= 0)
	{
		if(pUserInfo->iCount >= g_conf_ad_total_count)
	        {
	                 return false;
	        }
	        pUserInfo->iCount++;
	        //printf("ad : %s, count : %d\n", adv, pUserInfo->iCount);
	        return true;
	}

        pUserInfo = (FilterUserInfo*)malloc(sizeof(FilterUserInfo));
        memset(pUserInfo, 0, sizeof(FilterUserInfo));
        pUserInfo->iCount++;
        hash_map_insert(g_pTotalAD[iTid], adv, strlen(adv), (long*)pUserInfo);
        return true;
}

/************************************************************************/
// Is the request uri have illegal word                                                                    
/************************************************************************/
static bool isUriHaveIllegalWord(MqHttpMessage* psMqHttpMes)
{
	int i = 0;
	for (; i < g_IllegalWords->iNum; i++)
	{
		if (strstr(psMqHttpMes->pcUri, g_IllegalWords->cpData[i]))
		{
			return true;
		}
	}
	
	return false;
}


/*main loop*/
int rule_pop_main_loop(const int tid, char* pcMessage, MqHttpMessage* psMqHttpMes)
{
	if (isUriHaveIllegalWord(psMqHttpMes))
	{
		return -1;
	}

	char *adv = NULL;
	pthread_rwlock_rdlock(&radius_lock);
	int nRadiusRet = hash_map_find(g_pRadiusHashMap, psMqHttpMes->pcSrcIP, strlen(psMqHttpMes->pcSrcIP), (long*)&adv);
	pthread_rwlock_unlock(&radius_lock);
	if (nRadiusRet < 0)
	{
        	/*if (1 == g_conf_pop_surplus && rule_pop_is_suplus_site(psMqHttpMes) && rule_pop_ip_send_surplus_message(tid, psMqHttpMes))
        	{    
                	char szAdvIdFlag[10] = {0}; 
                	szAdvIdFlag[0] = '0'; 
                	pop_mq_direct_send_surplus_msg(tid, pcMessage, szAdvIdFlag, adv);
                	return 1;
        	}*/
		return -1; 
	}
	int iTid = hash_map_hash_value(adv, strlen(adv))%16; //重新修改tid

	//先过滤域名黑名单
	if(rule_pop_load_file_host(psMqHttpMes->host))
	{
		return -1;
	}
	//检查是不是在域名白名单里面
	if (!rule_pop_in_total_white_site(psMqHttpMes))
	{
		return -1;
	}

	rule_pop_cal_total_ip(iTid, psMqHttpMes, adv);
    if(check_ad_ua_black(psMqHttpMes, adv))
    {    
            return -1;
    }       
	//ip check
	if (rule_pop_filter_ip(psMqHttpMes->pcSrcIP, iTid, adv) < 0)
	{
		//printf("rule_pop_main_loop come a %.*s, rule_pop_filter_ip\n", psMqHttpMes->host);
		return -1;
	}

	//指定域名投放1
	if (1 == g_conf_pop_white_host1 && specify_host_send_pop_message1(iTid, psMqHttpMes) && rule_pop_load_black_uri(psMqHttpMes))
	{
		//属于定投网址的分开投，防止出现白框
		if (rule_pop_is_send_target_message1(iTid, psMqHttpMes, adv))
		{
			char szTypeFlag[10] = {0};
			szTypeFlag[0] = '3';
			pop_mq_direct_send_crowd_msg(iTid, pcMessage, szTypeFlag, "0", adv);
		}	
		return 1;
	}

	//指定域名投放
	if (1 == g_conf_pop_white_host && specify_host_send_pop_message(iTid, psMqHttpMes) && rule_pop_load_black_uri(psMqHttpMes))
	{
		//属于定投网址的分开投，防止出现白框
		if (rule_pop_is_send_target_message(iTid, psMqHttpMes, adv))
		{
			char szTypeFlag[10] = {0};
			szTypeFlag[0] = '2';
			pop_mq_direct_send_crowd_msg(iTid, pcMessage, szTypeFlag, "0", adv);
		}	
		return 1;
	}

	/*only pop main page and html page*/
	/*if(rule_pop_is_white_site(psMqHttpMes) && rule_pop_load_black_uri(psMqHttpMes))
	{
		//printf("rule_pop_is_white_site host is %s\n", psMqHttpMes->host);
		char cTotalAdvIds[64] = {0};
		int iAdvId = -1;
		
		//对指定用户进行弹窗
		int iResult = specify_ad_is_send_pop_message(iTid, psMqHttpMes, adv);
		if (1 == iResult)
		{
			cTotalAdvIds[0] = '0';
			pop_mq_direct_send_crowd_msg(iTid, pcMessage, cTotalAdvIds, adv);
			return 1;
		}
		else if (2 == iResult)
		{
			//如果是指定用户则放弃此流量，防止网页重复嵌套出白框
			return 1;
		}
		
		//immediate search pop
		if(1 == g_conf_pop_ip_search && radius_pop_check_pop_ip(iTid,psMqHttpMes,adv))
		{
			//只要在即搜即投和离线人群中则不进行剩余流量弹窗，防止离线人群的弹窗嵌套多次导致白框
			if (filter_ad_is_send_pop_message(iTid, psMqHttpMes, adv) )
			{
				cTotalAdvIds[0] = '7';
				pop_mq_direct_send_crowd_msg(iTid, pcMessage, cTotalAdvIds, adv);
			}

			return 1;
		}

		//offline crowd pop
		if (1 == g_conf_offline_pop && radius_pop_check_offline_crowd(iTid, psMqHttpMes, adv))
		{
			if (rule_pop_is_send_pop_message(iTid, psMqHttpMes, adv))
			{
				cTotalAdvIds[0] = '1';
				pop_mq_direct_send_crowd_msg(iTid, pcMessage, cTotalAdvIds, adv);
			}

			return 1;
		}
	}*/

	if(rule_pop_is_suplus_site(psMqHttpMes) && rule_pop_load_black_uri(psMqHttpMes)&& check_post_ad_count(adv, iTid))
	{
		if(1 == g_conf_pop_ip_search && radius_pop_check_pop_ip(iTid,psMqHttpMes,adv))
		{
			//只要在即搜即投和离线人群中则不进行剩余流量弹窗，防止离线人群的弹窗嵌套多次导致白框
			if (rule_pop_is_send_pop_message(iTid, psMqHttpMes, adv))
			{
				char szTypeStr[64] = {0};
				szTypeStr[0] = '4';
				pop_mq_direct_send_crowd_msg(iTid, pcMessage, szTypeStr,"0", adv);
			}

			return 1;
		}

		if (1 == g_conf_pop_surplus && rule_pop_is_send_surplus_message(iTid, psMqHttpMes, adv))
		{
                	char szTypeFlag[10] = {0};
                	szTypeFlag[0] = '0';
			pop_mq_direct_send_surplus_msg(iTid, pcMessage, szTypeFlag,"0", adv);
			return 1;
		}
	}

	return -1;
}
