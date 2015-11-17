/************************************************************************/
//  [12/10/2014]                                               
//  记录JC符合条件的成功记录                                                                   
/************************************************************************/
#ifndef _FILTER_RULE_RECORD_H_
#define _FILTER_RULE_RECORD_H_

#include "dc_global.h"

extern void g_jc_record_init();
extern void g_jc_record_write_file();

#define JCFILE_PATH "jc_record.txt"
#define JC_RECORD_HASH_LEN 256
#define JC_RECORD_ARRAY_LEN 64

typedef struct SiteRecord                                     
{ 
	/*过滤的url*/
	char* url[10];                              

	/*过滤的长度*/
	int length;

	/*网站*/
	char* host;

	/*命中次数*/
	int count[10];

	/*ip次数*/
	int ipNum;

	//ip hash
	HashMap* pJCHashIPs;

}SSiteRecord; 

typedef struct JcRecord                                     
{
	HashMap* pJCHashRecords; 
	FILE* pJCFileRecord;

	SSiteRecord* pSiteRecords;
	int iSiteLen;

	//function
	int (*queryJCRecord)(struct JcRecord* pSJcRecord, const MqHttpMessage* psMqHttpMes);
	void (*writeJcRecordFile)(struct JcRecord* pSJcRecord);
}SJcRecord;

//function
struct JcRecord* initialJcRecord();                               
int queryJCRecord(struct JcRecord* pSJcRecord, const MqHttpMessage* psMqHttpMes);
void writeJcRecordFile(struct JcRecord* pSJcRecord);

//js record global variable 
extern SJcRecord* g_psJcRecord;

#endif  //  