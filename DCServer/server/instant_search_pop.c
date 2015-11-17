#include "dc_global.h"
#include "url_code.h"

#define QUERY_CONTENT_LEN 40960
#define QUERY_RULE_LEN 20480
#define COMPETE_SITE_SIZE 128
#define NUMBER_OF_SAMPLES       (2048)

void* g_mqInstantSender = NULL;
HashMap* g_pQCHashMap = NULL;
pthread_mutex_t instant_lock;
pthread_mutex_t Advid_Lock;
HashMap* g_pQRHashMap = NULL;
HashMap* g_pAfterSearchHashMap[THREAD_NUM] = {NULL};
/*存储竞品网站*/
HashMap* g_pCompeteSiteHashMap = NULL;
/*存储竞品网站内的url*/
HashMap* g_pCompeteUrlHashMaps[COMPETE_SITE_SIZE] = {NULL};
extern void online_mq_direct_send_msg(char* pcMessage);
extern void pop_mq_direct_send_crowd_msg(const int iTid, char* pcMessage, char* szTypeStr,char *cTotalAdvIds, char* sAd);
extern void http_file_send_msg(const char * msg, int iTid);
extern void mq_redis_store_client_socket_init();
extern void redis_store_send_msg(const char * msg);
extern void rule_crown_search_init();
extern void load_redis_init();
extern unsigned int hash_map_hash_value(char* start, int len);
extern pthread_rwlock_t radius_lock;
extern HashMap* g_pRadiusHashMap;
extern HashMap* g_pvStopHashMap;
extern struct MixSegment* g_handle ;

void load_instant_hash_init()
{
	int i = 0;
	for(; i<THREAD_NUM;i++)
	{
		g_pAfterSearchHashMap[i] = hash_map_new(4096);
	}
}

void mq_instant_search_init()
{
	pthread_mutex_init(&instant_lock, NULL);
	pthread_mutex_init(&Advid_Lock, NULL);
        void * ctx = zmq_init(1);
    	g_mqInstantSender = zmq_socket(ctx, ZMQ_PUSH);
    	zmq_connect(g_mqInstantSender, g_conf_instant_adr);
    	printf("instant search push %s, p=%p\n", g_conf_instant_adr, g_mqInstantSender); 
    	return ;
}


/************************************************************************/
//  [7/30/2014 fushengli]   
//  载入网站和搜索关键字分割符对应列表                                         
/************************************************************************/
static void rule_pop_load_query_rule()
{
	g_pQRHashMap = hash_map_new(QUERY_RULE_LEN);

	zzconfig_t * root = zz_config_load (QUERY_RULE_CONF);
	zzconfig_t * child_advid = zz_config_child(root);
	int j = 0;
	while (child_advid)
	{
		char* host = zz_config_value(child_advid);
		//printf("%d adv id %s\n", j, host);
		regex_t* preg = NULL;
		int ret = hash_map_find(g_pQRHashMap, host, strlen(host), (long*)&preg);
		if (ret < 0)
		{
			zzconfig_t* grand_child_advid = zz_config_child(child_advid);
			if (grand_child_advid)
			{
				char* pvalue = zz_config_value(grand_child_advid);
				char* str = (char*)malloc(128);
				memset(str, 0, 128);
				strcat(str, "(^|&|\\?|#)(");
				strcat(str, pvalue);
				strcat(str, ")=([^&#]*)(&|$|#)");
				//printf("loadQueryRule=%s", str);
				preg = (regex_t*)malloc(sizeof(regex_t));
				memset(preg, 0, sizeof(regex_t));
				int err = regcomp(preg, str, REG_EXTENDED);
				if (0 != err)
				{
					printf("regcomp %s error.\n", str);
				}
				else
				{
					hash_map_insert(g_pQRHashMap, host, strlen(host), (long*)preg);
				}
			}
		}

		j++;
		child_advid = zz_config_next(child_advid);
	}

	printf("rule_pop_load_query_rule record num=%d\n", j);
}

/************************************************************************/
//  载入竞品url网站配置文件中每个site的所有竞品url                                                                   
/************************************************************************/
static void rule_pop_load_compete_url( zzconfig_t * pcChildHost, HashMap* pcQueryUrlHashMap, int iAdvId ) 
{
	AdFire* psAdvId = NULL;
	char* pcUrl = NULL;
	int j = 0;
	zzconfig_t * pcChildUrl = zz_config_child(pcChildHost);
	while (pcChildUrl)
	{
		psAdvId = NULL;
		pcUrl = zz_config_value(pcChildUrl);
		int ret = hash_map_find(pcQueryUrlHashMap, pcUrl, strlen(pcUrl), (long*)&psAdvId);
		if (ret < 0)
		{
			psAdvId = (AdFire*)malloc(sizeof(AdFire));
			memset(psAdvId, 0, sizeof(AdFire));
			psAdvId->pvAdInfo[psAdvId->adlen].iAdid = iAdvId;
			psAdvId->pvAdInfo[psAdvId->adlen].iscore = 100;
			psAdvId->adlen++;
			hash_map_insert(pcQueryUrlHashMap, pcUrl, strlen(pcUrl), (long*)psAdvId);
		}
		else
		{
			int i = 0;
			for (; i < psAdvId->adlen; i++)
			{
				if (iAdvId == psAdvId->pvAdInfo[i].iAdid)
				{
					break;
				}
			}

			if (i == psAdvId->adlen && i < ADVID_MAX_LEN)
			{
				psAdvId->pvAdInfo[psAdvId->adlen].iAdid = iAdvId;
				psAdvId->pvAdInfo[psAdvId->adlen].iscore = 100;
				psAdvId->adlen++;
			}
		}
		j++;
		pcChildUrl = zz_config_next(pcChildUrl);
	}

	//printf("rule_pop_load_compete_url url len is %d\n", j);
}


/************************************************************************/                                          
//  载入竞品url网站的配置文件                                                                 
/************************************************************************/
static void rule_pop_load_compete_site(char* pcFilePath)
{
	zzconfig_t * root = zz_config_load (pcFilePath);
	zzconfig_t * pcChildAdvId = zz_config_child(root);
	int iAdvId = 0;
	bool bhaveKey = false;
	int i = 0;
	char* pcHost = NULL;
	HashMap* pcQueryUrlHashMap = NULL;
	zzconfig_t * pcChildHost = NULL;
	while (pcChildAdvId)
	{
		iAdvId = atoi(zz_config_value(pcChildAdvId));
		//printf("rule_pop_load_compete_site adv id %d\n", iAdvId);

		pcChildHost = zz_config_child(pcChildAdvId);
		while(pcChildHost)
		{
			pcHost = zz_config_value(pcChildHost);
			//printf("rule_pop_load_compete_site host is %s\n", pcHost);
			pcQueryUrlHashMap = NULL;
			int ret = hash_map_find(g_pCompeteSiteHashMap, pcHost, strlen(pcHost), (long*)&pcQueryUrlHashMap);
			if (ret < 0)
			{
				g_pCompeteUrlHashMaps[i] = hash_map_new(QUERY_CONTENT_LEN);
				pcQueryUrlHashMap = g_pCompeteUrlHashMaps[i];
				hash_map_insert(g_pCompeteSiteHashMap, pcHost, strlen(pcHost), (long*)g_pCompeteUrlHashMaps[i]);
				i++;
			}

			rule_pop_load_compete_url(pcChildHost, pcQueryUrlHashMap, iAdvId);
			pcChildHost = zz_config_next(pcChildHost);
		}

		pcChildAdvId = zz_config_next(pcChildAdvId);
	}
}


/************************************************************************/                                      
//  载入竞品url网站的配置文件                                                                 
/************************************************************************/
static int rule_pop_load_compete_dictory()
{
	g_pCompeteSiteHashMap = hash_map_new(COMPETE_SITE_SIZE);

	char sFileName[2048] = {0};
	DIR * dirp; //directory pointer
	dirp = opendir(QUERY_URL_PATH);
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
			strcpy(sFileName, QUERY_URL_PATH);
			strcat(sFileName, direntp->d_name);
			if(stat(sFileName, &st) == -1)
			{
				printf("get %s file info failed\n", sFileName);				
				continue;
			}
			if((st.st_mode& S_IFMT) == S_IFDIR)//judge is or not directory
			{
				//char szNewPath[2048] = {0};
				//strcpy(szNewPath, QUERY_URL_PATH);
				//strcat(szNewPath, direntp->d_name);
			}
			else
			{
				printf("%s\n", sFileName);
				rule_pop_load_compete_site(sFileName);		
				//printf("hash table size : %d\n", g_pQCHashMap->size);	
			}
		}
	}
	if(dirp != NULL) closedir(dirp);
	return 0;
}

bool remove_dump_data(int *aid, int len, int Aid)
{
	int i = 0;
	for (; i < len; i++)
	{
		if(aid[i]  == Aid)
		{
			return true;
		}
	}
	return false;
}

/************************************************************************/                                            
//  检查uri中是否含有需要过滤的关键词                                                                   
/************************************************************************/
static int rule_pop_check_contain_key(int thread_id, MqHttpMessage* psMqHttpMes, int *aid, int *len)// AdFire** psAdvId)
{
	//AdFire* kps = (*psAdvId);
	int iUriLen = strlen(psMqHttpMes->pcUri);
	int iHostLen = strlen(psMqHttpMes->host);

	//搜索竞品url
	/*HashMap* pCompeteUrlHashMap = NULL;
	if (hash_map_find(g_pCompeteSiteHashMap, psMqHttpMes->host, iHostLen, (long*)&pCompeteUrlHashMap) >= 0)
	{
		if ((iUriLen + iHostLen) < 128)
		{
			char cHostUri[128] = {'\0'};
			memcpy(cHostUri, psMqHttpMes->host, iHostLen);
			memcpy(cHostUri + iHostLen, psMqHttpMes->pcUri, iUriLen);
			cHostUri[iHostLen + iUriLen + 1] = '\0';
			//printf("rule_pop_check_contain_key before thread id=%d hostUri=%s\n", thread_id, cHostUri);

			if (hash_map_find(pCompeteUrlHashMap, cHostUri, strlen(cHostUri), (long*)psAdvId) >= 0)
			{
				//printf("rule_pop_check_contain_key contain thread id=%d hostUri=%s\n", thread_id, cHostUri);
				return 1;
			}
		}
	}*/

	//是否搜索网站关键词
	regex_t* pReg = NULL;
	int ret = hash_map_find(g_pQRHashMap, psMqHttpMes->host, iHostLen, (long*)&pReg);
	if (ret < 0)
	{
		return -1;
	}


	regmatch_t subs[16];
	int res = regexec(pReg, psMqHttpMes->pcUri, (size_t)16, subs, 0);
	if (REG_NOMATCH == res)
	{
		//printf("regcomp not match.\n"); 
		return -1;
	}
	else
	{
		//匹配关键字
		if (-1 != subs[3].rm_so)
		{
			char word[512] = {'\0'};
			char desk[512] = {'\0'};
			char fact[512] = {'\0'};
			strncpy(word, (psMqHttpMes->pcUri + subs[3].rm_so), (subs[3].rm_eo - subs[3].rm_so));
			word[subs[3].rm_eo] = '\0';

			//printf("rule_pop_check_contain_key word threadId=%d word=%s \n", thread_id, word);
			UrlGB2312Decode(word, desk);
			int ret = get_character_code_type(desk);
			char* result = NULL;
			if(ret == 1)
			{
				printf("search word is utf8 \n");
				result = MixSegmentCut(g_handle, desk, "-MEG-");
			}
			else
			{
				//g2u(desk,strlen(desk),fact, 512);
				buffer_gbk2utf8(desk, 512, fact);
				printf("search word is gbk , %s\n", fact);
				result = MixSegmentCut(g_handle, fact, "-MEG-");
			}
			int keyword_num = 0;
			int  cmp_num = 0;
			if(result != NULL)
			{
				printf("fenci result: %s\n", result);
				char* kbuf = result;
				char* p[120] ;	
				char * outer_ptr = NULL;
				int in = 0;
				while((p[in]=strtok_r(kbuf,"-MEG-",&outer_ptr))!=NULL)
				{
					in++;
					kbuf=NULL;
				}
				int j = 0;
				for(; j< in; j++)
				{
					if((p[j] == NULL)||(p[j][0] == '\0'))
					{
						continue;
					}
					printf("%s\n",p[j]);
					if(hash_map_find(g_pvStopHashMap, p[j],strlen(p[j]), NULL) < 0)
					{
						keyword_num++;
						AdFire* AdvId = NULL;
						pthread_mutex_lock(&Advid_Lock);
						int ret = hash_map_find(g_pQCHashMap, p[j], strlen(p[j]), (long*)&AdvId);
						pthread_mutex_unlock(&Advid_Lock);
						if(ret < 0)
						{
							continue;
						}
						else
						{
							int i = 0 ;
							for (; i < AdvId->adlen; i++)
							{
								if(remove_dump_data(aid, (*len), AdvId->pvAdInfo[i].iAdid))
								{continue;}
								aid[(*len)] = AdvId->pvAdInfo[i].iAdid;
								//kps->pvAdInfo[kps->adlen].iAdid = AdvId->pvAdInfo[i].iAdid;
								//kps->pvAdInfo[kps->adlen].iscore = AdvId->pvAdInfo[i].iscore;
								//(kps->adlen)++;
								(*len)++;
							}
							cmp_num++;
						}
					}
				}
			}
			free(result);
			printf("比较相等分词的数量：%d, 分词以后的数量去掉停用词的数量 : %d\n ", cmp_num, keyword_num);
			if(keyword_num == 1 && cmp_num == 1)
			{
				return 1;
			}
			if(cmp_num >= 2)
			{
				return 1;
			}
		}
	}

	return -1;
}

static int instant_send (void *socket, char *string)
{
        if(socket == NULL || NULL == string)
        {
	                return -1;
        }

        int rc = zmq_send(socket, string, strlen(string), 1);
        return (rc);
}
/*check search ad ua operation*/
void  radius_pop_check_search_wd(u_int curr_second, const int iTid, MqHttpMessage* psMqHttpMes, char *sPcad, int aid[], int aid_len)
{
	/*AdFire* sAdvId = NULL;
	sAdvId = (AdFire*)malloc(sizeof(AdFire));
	if(NULL  == sAdvId)
	{
		printf("malloc AdFire failed\n");
		return ;
	}
	memset(sAdvId, 0, sizeof(AdFire));*/
	/*make a ip_ua string*/
	int iIPStrLen = strlen(psMqHttpMes->pcSrcIP);
	int iUaStrLen = strlen(psMqHttpMes->pcUa);
	if (iIPStrLen + iUaStrLen >= 512)
	{
		return;
	}

	if ( NULL != sPcad)
	{
		char cStore[4096] = {0};
		
		char szADUA[2048]={0};
		strcpy(szADUA, sPcad);
		strcat(szADUA, "::");
		//strcat(szADUA, psMqHttpMes->pcUa);
		//strcat(szADUA, "::");
		strcat(szADUA, psMqHttpMes->pcNoEncUa);
		strcat(szADUA, "::");
	
		AdFire * adStore = (AdFire*)malloc(sizeof(AdFire));
		memset(adStore, 0, sizeof(AdFire));
		int i = 0;
		char szAdvId[128]={0};
 		for (; i < aid_len; i++)
 		{
			pthread_mutex_lock(&Advid_Lock);
			int iAdid = aid[i];//sAdvId->pvAdInfo[i].iAdid;
			//int iscore = sAdvId->pvAdInfo[i].iscore;
			pthread_mutex_unlock(&Advid_Lock);

			char sPrefix[10] = {0};
			sprintf(sPrefix, "%d", iAdid);
			strcpy(cStore, sPrefix);
			strcat(cStore, "###");
			strcat(cStore, psMqHttpMes->host);
			strcat(cStore, "\t");
			strcat(cStore, psMqHttpMes->pcCookie);
			strcat(cStore, "\t");
			strcat(cStore, psMqHttpMes->pcUri);
			strcat(cStore, "\t");
			strcat(cStore, sPcad);
			strcat(cStore, "\t");
			strcat(cStore, psMqHttpMes->pcSec);
			http_file_send_msg(cStore, iTid);
			
			if(iAdid >0)
			{
				char szBuff[10] = {0};
				if(i == 0)
				{
					sprintf(szBuff, "%d", iAdid);
					strcat(szAdvId, szBuff);
				}
				else
				{

					sprintf(szBuff, ",%d", iAdid);
					strcat(szAdvId, szBuff);
				}
				adStore->pvAdInfo[adStore->adlen].iAdid = iAdid;
				//adStore->pvAdInfo[adStore->adlen].iscore = iscore;
				adStore->adlen++;
			}
			else
			{
				continue;
			}

  		}
		if(strlen(szAdvId) > 0)
		{
			char pSend[4096] = {0};
		
			strcpy(pSend, sPcad);
			strcat(pSend, "\t");
			strcat(pSend, psMqHttpMes->pcSrcIP);
			strcat(pSend, "\t");
			strcat(pSend, psMqHttpMes->pcUa);
			strcat(pSend, "\t");
			strcat(pSend, psMqHttpMes->pcUri);
			strcat(pSend, "\t");
			strcat(pSend, psMqHttpMes->host);
			strcat(pSend, "\t");
			strcat(pSend, szAdvId);
			strcat(pSend, "\t");
			strcat(pSend, psMqHttpMes->pcSec);
			online_mq_direct_send_msg(pSend);
		}
		
		strcat(szADUA, szAdvId);
		redis_store_send_msg(szADUA);
		pthread_mutex_lock(&instant_lock);
		printf("%p, [%d] crowd client search msg : %s\n", g_mqInstantSender, iTid,  szADUA);
		instant_send(g_mqInstantSender, szADUA);

		pthread_mutex_unlock(&instant_lock);
	}
	return;
}

static bool pop_after_send_pop_message(const int iTid, MqHttpMessage* psMqHttpMes, char* sAd)
{
        char szADUA[1024]={0};
        strcpy(szADUA, sAd);
        strcat(szADUA, psMqHttpMes->pcUa);

        FilterUserInfo* pUserInfo = NULL;
        int iRes = hash_map_find(g_pAfterSearchHashMap[iTid], szADUA, strlen(szADUA), (long*)&pUserInfo);
        if (iRes >= 0)
        {
                //printf("sec : %ld count:%d time:%ld delaytime:%d\n", psMqHttpMes->lSec, pUserInfo->iCount,pUserInfo->lTime, g_conf_crowd_delay);
                if (pUserInfo->iCount > g_conf_after_max_num || (psMqHttpMes->lSec - pUserInfo->lTime) < g_conf_after_delay)
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
        hash_map_insert(g_pAfterSearchHashMap[iTid], szADUA, strlen(szADUA), (long*)pUserInfo);
        return true;
}


/*针对百度*/
static void pop_after_search(const int iTid, char* pcMessage, MqHttpMessage* psMqHttpMes, char* adv, int aid[], int aid_len)
{

        char cTotalAdvId[64] = {0};
        int i = 0;
        for (; i < aid_len; i++)
        {
                if (0 == i)
                {
                        sprintf(cTotalAdvId, "%s%d", cTotalAdvId, aid[i]);
                }
                else
                {
                        sprintf(cTotalAdvId, "%s_%d", cTotalAdvId, aid[i]);
                }
        }
	if(pop_after_send_pop_message(iTid, psMqHttpMes, adv))
	{
        	pop_mq_direct_send_crowd_msg(iTid, pcMessage, "5", cTotalAdvId , adv);             
	}  
}

void pop_crowd_search_init()
{
	load_instant_hash_init();
	rule_crown_search_init();
	/*init instant serach  */
	mq_instant_search_init();
	mq_redis_store_client_socket_init();
	load_redis_init();
	//载入竞品网站url
	rule_pop_load_compete_dictory();
	/*load search rule*/
	rule_pop_load_query_rule();
}

int rule_pop_crowd_search_loop(const int tid, char* pcMessage, MqHttpMessage* psMqHttpMes)
{
	char *adv = NULL;
	pthread_rwlock_rdlock(&radius_lock);
	int nRadiusRet = hash_map_find(g_pRadiusHashMap, psMqHttpMes->pcSrcIP, strlen(psMqHttpMes->pcSrcIP), (long*)&adv);
	pthread_rwlock_unlock(&radius_lock);
	if (nRadiusRet < 0)
	{
		return -1; 
	}
	unsigned int iTid = hash_map_hash_value(adv, strlen(adv))%16; //重新修改tid
	int aid[24] = {0};
	int aid_len = 0;
	if (rule_pop_check_contain_key(iTid,psMqHttpMes, aid, &aid_len) < 0)
	{
		return -1;
	}

	if(1 == g_conf_pop_after_search)
	{
		pop_after_search(iTid, pcMessage, psMqHttpMes, adv, aid, aid_len);
		return 1;
	}
	u_int uiSec = 0;
	// 即搜即投记录符合人群
	radius_pop_check_search_wd(uiSec, iTid, psMqHttpMes, adv, aid, aid_len);

	return 1;
}
