/************************************************************************/
//  [12/10/2014]                                               
//  记录JC符合条件的成功记录                                                                   
/************************************************************************/
#include "filter_rule_jc_record.h"

/*global reference*/
extern HashMap* hash_map_new(unsigned int length);
extern int hash_map_insert(HashMap* psHashMap, char*start, int len, long* in_val);
extern int hash_map_find(HashMap* psHashMap, char*start, int len, long* out_val);
extern int load_jc_record_cfg(SSiteRecord* siteRecord);

#define JC_IP_SIZE 100000
#define JC_RECORD_CFG "/home/DCServer/conf/jc_record.cfg"
#define JC_RECORD_FILENAME "./log/jcrecord"

SJcRecord* g_psJcRecord = NULL;

/************************************************************************/
//  载入文件                                                                  
/************************************************************************/
int load_jc_record_cfg(SSiteRecord* siteRecord)
{
	int i = 0;
	zzconfig_t * root = zz_config_load (JC_RECORD_CFG);
	zzconfig_t * child = zz_config_child(root);
	while (child)
	{
		siteRecord[i].pJCHashIPs = hash_map_new(JC_IP_SIZE);

		printf("%s\n", zz_config_value(child));
		siteRecord[i].host = zz_config_value(child);
		zzconfig_t * child_dst = zz_config_locate(child, "dst");
		zzconfig_t * child_dst_url = zz_config_child(child_dst);
		int j = 0;
		while (child_dst_url)
		{
			siteRecord[i].url[j] = zz_config_value(child_dst_url);
			child_dst_url = zz_config_next(child_dst_url);
			j++;
		}

		siteRecord[i].length = j;
		child = zz_config_next(child);
		i++;
	}

	return i;
}

/************************************************************************/
//  初始化                                                                   
/************************************************************************/
struct JcRecord* initialJcRecord()
{
	SJcRecord* pjcRecord = (SJcRecord*) malloc (sizeof(SJcRecord));
	memset(pjcRecord, 0, sizeof(SJcRecord));
	pjcRecord->pJCHashRecords = hash_map_new(JC_RECORD_HASH_LEN);

	SSiteRecord* siteRecords = (SSiteRecord*) malloc (JC_RECORD_ARRAY_LEN * sizeof(SSiteRecord));
	memset(siteRecords, 0, JC_RECORD_ARRAY_LEN * sizeof(SSiteRecord));
	pjcRecord->iSiteLen = load_jc_record_cfg(siteRecords);
	pjcRecord->pSiteRecords = siteRecords;

	int i = 0;
	for (i = 0; i < pjcRecord->iSiteLen; i++)
	{
		//printf("%s, %d, %s\n",  siteRecords[i].host, siteRecords[i].length, siteRecords[i].url[0]);
		hash_map_insert(pjcRecord->pJCHashRecords, siteRecords[i].host, strlen(siteRecords[i].host), (long*)&siteRecords[i]);
	}

	//init function
	pjcRecord->queryJCRecord = queryJCRecord;
	pjcRecord->writeJcRecordFile = writeJcRecordFile;

	return pjcRecord;
}

/************************************************************************/
//  判断日期变化并获取文件名称                                                                   
/************************************************************************/
static bool isDayChanged(time_t curr, char* pcFileName)
{
	static int iJcFileDay = -1;
	struct tm * tm;
	tm = localtime(&(curr));
	sprintf(pcFileName, "%s_%d%02d%02d", JC_RECORD_FILENAME, (1900+tm->tm_year), 1+tm->tm_mon, tm->tm_mday);
	if(iJcFileDay != tm->tm_mday)
	{
		iJcFileDay = tm->tm_mday;
		return true;
	}
	 
	return false;
}

/************************************************************************/
//  记录符合条件的记录                                                                   
/************************************************************************/
void writeJcRecordFile(struct JcRecord* pSJcRecord)
{
	char pcFileName[128] = {0};
	time_t timep;
	time (&timep);
	if (isDayChanged(timep, pcFileName))
	{
		if (pSJcRecord->pJCFileRecord)
		{
			  fclose(pSJcRecord->pJCFileRecord);
		}
		pSJcRecord->pJCFileRecord = fopen(pcFileName, "a+");
	}
	
	if (NULL == pSJcRecord->pJCFileRecord)
	{
		printf("can not access file %s\n", pcFileName);
		return;
	}
	
	char cTtime[128] = {0};
	sprintf(cTtime, "%s", ctime(&timep));

	fputs("==========================\n", pSJcRecord->pJCFileRecord);
	int i = 0;
	int j = 0;
	char str[256] = {0};
	for (i = 0; i < pSJcRecord->iSiteLen; i++)
	{
		for (j = 0; j < pSJcRecord->pSiteRecords[i].length; j++)
		{
			sprintf(str, "%.*s Count=%-8d IPNum=%-8d siteUrl=%s%s\n" , strlen(cTtime) - 1, cTtime,
				pSJcRecord->pSiteRecords[i].count[j], pSJcRecord->pSiteRecords[i].ipNum, pSJcRecord->pSiteRecords[i].host, pSJcRecord->pSiteRecords[i].url[j]);
			fputs(str, pSJcRecord->pJCFileRecord);
		}
	}

	fflush(pSJcRecord->pJCFileRecord);
	return;
}

/************************************************************************/
//  过滤符合条件的记录                                                                   
/************************************************************************/
int queryJCRecord(struct JcRecord* pSJcRecord, const MqHttpMessage* psMqHttpMes)
{
	SSiteRecord* pSSiteRecord;
	int ret = hash_map_find(pSJcRecord->pJCHashRecords, psMqHttpMes->host, strlen(psMqHttpMes->host), (long*)&pSSiteRecord);
	if (ret >= 0 && NULL != pSSiteRecord)
	{
		//printf("getJCRecord=%s, %s\n", pcHost, pcUri);
		int i = 0;
		bool bHaveSite = false;
		for (; i < pSSiteRecord->length; i++)
		{
			if (0 == strcmp(pSSiteRecord->url[i], psMqHttpMes->pcUri))
			{
				pSSiteRecord->count[i]++;
				bHaveSite = true;
				printf("getJCRecord=%s, %d, %s\n",  pSSiteRecord[0].host, pSSiteRecord[0].length, pSSiteRecord[0].url[0]);
				break;
			}
		}

		if (bHaveSite)
		{
			int result = hash_map_find(pSSiteRecord->pJCHashIPs, psMqHttpMes->pcSrcIP, strlen(psMqHttpMes->pcSrcIP), NULL);
			if (result < 0)
			{
				pSSiteRecord->ipNum++;
				hash_map_insert(pSSiteRecord->pJCHashIPs, psMqHttpMes->pcSrcIP, strlen(psMqHttpMes->pcSrcIP), NULL);
			}
			return 1;
		}
	}

	return 0;
}

/************************************************************************/
//  初始化jc日志记录                                                                    
/************************************************************************/
void g_jc_record_init()
{
	g_psJcRecord = initialJcRecord();
}

void g_jc_record_write_file()
{
	if (NULL != g_psJcRecord)
	{
		g_psJcRecord->writeJcRecordFile(g_psJcRecord);
	}
}
