#define _GNU_SOURCE
#include "dc_global.h"
#include "zhelpers.h"

extern int http_cap_filter_main(const int iTid, char* pcMessage, const MqHttpMessage* psMqHttpMes);
extern void rule_pop_encrypt_ipua_md5(const char* ipua, char* md5_ipua);
extern unsigned long hash_map_get_hash(char *start, int len, unsigned long dwHashType);
extern pthread_rwlock_t adua_lock;
/************************************************************************/
//  [8/8/2014 fushengli]                                               
//  解析发送信息获取http内容                                                                   
/************************************************************************/
static void mq_message_analysis_json(const int iTid, char* pcMessage)
{
	char * Cookie = "\",\"Cookie\":\"";
	char * pcSrcIP = "\",\"srcIP\":\"";
	char * pcDstIP = "\",\"dstIP\":\"";
	char * pcSiteUrl = "\",\"siteUrl\":\"";
	char * pcSiteUri = "\",\"siteUri\":\"";
	char * pcSec = "\",\"sec\":\"";
	char * pcUa = "\",\"User-Agent\":\"";
	char * end =   "\"}";

	MqHttpMessage psMqHttpMes = {{'\0'}, {'\0'}, {'\0'} ,{'\0'}, {'\0'}, 0, {'\0'} };
	if(strstr(pcMessage, Cookie) != NULL)
	{
		char* pcCookieBegin = strstr(pcMessage, Cookie) + strlen(Cookie);
		char* pcCookieEnd = strstr(pcMessage, pcSrcIP);

		if(!pcCookieBegin || !pcCookieEnd)
		{
			//printf("pcCookieBegin or pcCookieEnd is NULL\n");
			return -1;
		}
		memcpy(psMqHttpMes.pcCookie, pcCookieBegin, (pcCookieEnd - pcCookieBegin));	
	}
	else
	{
		memcpy(psMqHttpMes.pcCookie, "NULL", strlen("NULL"));
	}
	char* pcBeginIP = strstr(pcMessage, pcSrcIP) + strlen(pcSrcIP);
	char* pcEndIP = strstr(pcMessage, pcDstIP);
	if ((NULL == pcBeginIP)||(NULL  == pcEndIP)||(pcEndIP - pcBeginIP >= 16))
	{
		return;
	}

	memcpy(psMqHttpMes.pcSrcIP, pcBeginIP, (pcEndIP - pcBeginIP));

	char* pcBeginUrl = strstr(pcMessage, pcSiteUrl) + strlen(pcSiteUrl);
	char* pcEndUrl = strstr(pcMessage, pcSiteUri);
	if (pcEndUrl - pcBeginUrl >= 256)
	{
		return;
	}

	memcpy(psMqHttpMes.host, pcBeginUrl, (pcEndUrl - pcBeginUrl));

	char* pcBeginUri = strstr(pcMessage, pcSiteUri) + strlen(pcSiteUri);
	char* pcEndUri = strstr(pcMessage, pcSec);
	if (pcEndUri - pcBeginUri >= 512)
	{
		return;
	}
	memcpy(psMqHttpMes.pcUri, pcBeginUri, (pcEndUri - pcBeginUri));

	char* pcBeginSec = strstr(pcMessage, pcSec) + strlen(pcSec);
	char* pcEndSec = strstr(pcMessage, pcUa);
	if(!pcBeginSec || !pcEndSec)
	{
		return;
	}
	//psMqHttpMes.lSec = strtol(pcBeginSec, &pcBeginSec, 10);
	memcpy(psMqHttpMes.pcSec, pcBeginSec, (pcEndSec-pcBeginSec));
	psMqHttpMes.lSec = atoi(psMqHttpMes.pcSec);

	char* pcBeginUa = strstr(pcMessage, pcUa) + strlen(pcUa);
	char* pcEndUa = strstr(pcMessage, end);
	if (NULL == pcBeginUa || NULL == pcEndUa || (pcEndUa - pcBeginUa) >= 512 || (pcEndUa - pcBeginUa) < 0)
	{
		return;
	}
	char UATemp[512] = {0};
	memcpy(UATemp, pcBeginUa, (pcEndUa - pcBeginUa));
	//if(strstr(UATemp, "Mobile") || strstr(UATemp, "Mac"))
	if(strstr(UATemp, "Android") || strstr(UATemp, "Mac"))
	{
		return;
	}
	char cUaMD5s[64] = {'\0'};
    	unsigned long nHash  = hash_map_get_hash(UATemp, strlen(UATemp), 0);
    	unsigned long nHashA = hash_map_get_hash(UATemp, strlen(UATemp), 1    );
    	unsigned long nHashB = hash_map_get_hash(UATemp, strlen(UATemp), 2    );
        //rule_pop_encrypt_ipua_md5(UATemp, cUaMD5s);
	sprintf(cUaMD5s, "%u%u%u", nHash, nHashA, nHashB);
	memcpy(psMqHttpMes.pcUa, cUaMD5s, strlen(cUaMD5s));
	memcpy(psMqHttpMes.pcNoEncUa, pcBeginUa, (pcEndUa - pcBeginUa));
	http_cap_filter_main(iTid, pcMessage, &psMqHttpMes);
}


static void mq_mobile_message_analysis_json(const int iTid, char* pcMessage)
{
	char * Cookie = "\",\"Cookie\":\"";
	char * pcSrcIP = "\",\"srcIP\":\"";
	char * pcDstIP = "\",\"dstIP\":\"";
	char * pcSiteUrl = "\",\"siteUrl\":\"";
	char * pcSiteUri = "\",\"siteUri\":\"";
	char * pcSec = "\",\"sec\":\"";
	char * pcUa = "\",\"User-Agent\":\"";
	char * end =   "\"}";

	MqHttpMessage psMqHttpMes = {{'\0'}, {'\0'}, {'\0'} ,{'\0'}, {'\0'}, 0, {'\0'} };
	if(strstr(pcMessage, Cookie) != NULL)
	{
		char* pcCookieBegin = strstr(pcMessage, Cookie) + strlen(Cookie);
		char* pcCookieEnd = strstr(pcMessage, pcSrcIP);

		if(!pcCookieBegin || !pcCookieEnd)
		{
			printf("pcCookieBegin or pcCookieEnd is NULL\n");
			return -1;
		}
		memcpy(psMqHttpMes.pcCookie, pcCookieBegin, (pcCookieEnd - pcCookieBegin));	
	}
	else
	{
		memcpy(psMqHttpMes.pcCookie, "NULL", strlen("NULL"));
	}
	char* pcBeginIP = strstr(pcMessage, pcSrcIP) + strlen(pcSrcIP);
	char* pcEndIP = strstr(pcMessage, pcDstIP);
	if (pcEndIP - pcBeginIP >= 16)
	{
		return;
	}

	memcpy(psMqHttpMes.pcSrcIP, pcBeginIP, (pcEndIP - pcBeginIP));

	char* pcBeginUrl = strstr(pcMessage, pcSiteUrl) + strlen(pcSiteUrl);
	char* pcEndUrl = strstr(pcMessage, pcSiteUri);
	if (pcEndUrl - pcBeginUrl >= 256)
	{
		printf("host is too long \n");
		return;
	}

	memcpy(psMqHttpMes.host, pcBeginUrl, (pcEndUrl - pcBeginUrl));

	char* pcBeginUri = strstr(pcMessage, pcSiteUri) + strlen(pcSiteUri);
	char* pcEndUri = strstr(pcMessage, pcSec);
	if (pcEndUri - pcBeginUri >= 8182)
	{
		printf("uri is too long  > 8182\n");
		return;
	}
	memcpy(psMqHttpMes.pcUri, pcBeginUri, (pcEndUri - pcBeginUri));

	char* pcBeginSec = strstr(pcMessage, pcSec) + strlen(pcSec);
	char* pcEndSec = strstr(pcMessage, pcUa);
	if(!pcBeginSec || !pcEndSec)
	{
		printf("sec is NULL\n");
		return;
	}
	//psMqHttpMes.lSec = strtol(pcBeginSec, &pcBeginSec, 10);
	memcpy(psMqHttpMes.pcSec, pcBeginSec, (pcEndSec-pcBeginSec));
	psMqHttpMes.lSec = atoi(psMqHttpMes.pcSec);

	char* pcBeginUa = strstr(pcMessage, pcUa) + strlen(pcUa);
	char* pcEndUa = strstr(pcMessage, end);
	if (NULL == pcBeginUa || NULL == pcEndUa || (pcEndUa - pcBeginUa) >= 2083 || (pcEndUa - pcBeginUa) < 0)
	{
		printf("ua is too long > 2083\n");
		return;
	}
	char UATemp[512] = {0};
	memcpy(UATemp, pcBeginUa, (pcEndUa - pcBeginUa));
	if(NULL  == strstr(UATemp, "Mobile"))
	{
		return;
	}
	char cUaMD5s[64] = {'\0'};
    	unsigned long nHash  = hash_map_get_hash(UATemp, strlen(UATemp), 0);
    	unsigned long nHashA = hash_map_get_hash(UATemp, strlen(UATemp), 1    );
    	unsigned long nHashB = hash_map_get_hash(UATemp, strlen(UATemp), 2    );
        //rule_pop_encrypt_ipua_md5(UATemp, cUaMD5s);
	sprintf(cUaMD5s, "%u%u%u", nHash, nHashA, nHashB);
	memcpy(psMqHttpMes.pcUa, cUaMD5s, strlen(cUaMD5s));
	memcpy(psMqHttpMes.pcNoEncUa, pcBeginUa, (pcEndUa - pcBeginUa));
	http_cap_filter_mobile(iTid, pcMessage, &psMqHttpMes);
}
/*
 * the function of capture packet
 */
void* mq_message_analysis_thread_func(void* _id)
{
	int iTid = (long)_id;
	while (1) 
	{
		char szPacket[65535] = {0};
		int nRecvLen =  zmq_recv(g_pvMqReceiver[iTid], szPacket, 65535, 0);	
		if(-1 == nRecvLen)
		{
			continue;
		}
		//printf("%s\n", szPacket);
		mq_message_analysis_json(iTid, szPacket);
		g_iReceiverNum[iTid]++;
	}
    return(NULL);
}

void* mq_message_analysis_mobile_func(void* _id)
{
	int iTid = (long)_id;
	while (1) 
	{
		char szPacket[65535] = {0};
		int nRecvLen =  zmq_recv(g_pvMobileReceiver[iTid], szPacket, 65535, 0);	
		if(-1 == nRecvLen)
		{
			continue;
		}
		//printf("%s\n", szPacket);
		mq_mobile_message_analysis_json(iTid, szPacket);
	}
	return(NULL);
}
