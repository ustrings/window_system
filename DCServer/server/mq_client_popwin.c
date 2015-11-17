#include "dc_global.h"

void* g_pvWinSender = NULL;
void* g_pvSurplusSender = NULL;
void* g_pvMobileSender = NULL;
extern pthread_rwlock_t radius_lock;
pthread_mutex_t win_sender_lock;
pthread_mutex_t mobile_sender_lock;
pthread_mutex_t surplus_sender_lock;
extern HashMap* g_pRadiusHashMap;

extern char* base64_encode(const char* data, int data_len);
/************************************************************************/
//  [8/12/2014 fushengli]                                               
//  初始化发送信息                                                                   
/************************************************************************/
void mq_pop_win_client_init()
{
	pthread_mutex_init(&win_sender_lock, NULL);
	pthread_mutex_init(&surplus_sender_lock, NULL);
	pthread_mutex_init(&mobile_sender_lock, NULL);
	void * ctx = zctx_new ();
    g_pvWinSender = zsocket_new (ctx, ZMQ_PUSH);
    //zsocket_connect(g_pvWinSender, "tcp://61.160.181.46:9010");
    zsocket_connect(g_pvWinSender, g_conf_pop_win_adr);
    //zsocket_connect(g_pvWinSender, "tcp://61.152.73.246:9010");
    printf("push %s, p=%d\n", g_conf_pop_win_adr, g_pvWinSender);

	if (1 == g_conf_pop_surplus)
	{
		void * vpCtx = zctx_new ();
		g_pvSurplusSender = zsocket_new (vpCtx, ZMQ_PUSH);
		zsocket_connect(g_pvSurplusSender, g_conf_surplus_url_adr);
		printf("push %s, p=%d\n", g_conf_surplus_url_adr, g_pvSurplusSender);
	}
	
	if(1 == g_conf_enable_pop_mobile)
	{
		void * vpCtx = zctx_new ();
		g_pvMobileSender = zsocket_new (vpCtx, ZMQ_PUSH);
		zsocket_connect(g_pvMobileSender, g_conf_mobile_adr);
		printf("push %s, p=%d\n", g_conf_mobile_adr, g_pvMobileSender);
	}
	
    return ;
}

static int s_send (void *socket, char *string) 
{
	if(socket == NULL || NULL == string)
	{
		return -1;
	}
	int rc = zmq_send(socket, string, strlen(string), 1);
	
	return (rc);
}

/************************************************************************/
//  [8/12/2014 fushengli]                                               
//  设置广告ID并发送信息                                                                  
/************************************************************************/
void pop_mq_direct_send_crowd_msg(const int iTid, char* pcMessage, char *cTotalAdvIds, char* sAd)
{

	char * advIdStr = "\",\"advId\":\"";
	char * adiusStr = "\",\"radius\":\"";
	char * Cookie = "\",\"Cookie\":\"";
	char * pcSrcIP = "\",\"srcIP\":\"";
	char * pcUa = "\",\"User-Agent\":\"";
	char * end =   "\"}";
	//next remove cookie message
	char szPcMessage[4096] = {0};
	char* Aduis = strstr(pcMessage, end);
	char* pcUaBegin = strstr(pcMessage, pcUa)+strlen(pcUa) ;
	char* pcUaEnd = strstr(pcMessage, Aduis);
	if(strstr(pcMessage, Cookie) != NULL)
	{
		char* pcCookieBegin = strstr(pcMessage, Cookie) ;
		char* pcCookieEnd = strstr(pcMessage, pcSrcIP);
		int front = pcCookieBegin - pcMessage;
		memcpy(szPcMessage, pcMessage, front);
		strncat(szPcMessage, pcCookieEnd, pcUaBegin - pcCookieEnd);
	}
	else
	{
		memcpy(szPcMessage, pcMessage, pcUaBegin-pcMessage);
	}
	//strncat(szPcMessage, pcCookieEnd, pcUaBegin - pcCookieEnd);

	char UATemp[512] = {0};
	memcpy(UATemp, pcUaBegin, (pcUaEnd - pcUaBegin));
        char cUaMD5s[33] = {'\0'};
         unsigned int nHash  = hash_map_get_hash(UATemp, strlen(UATemp), 0);
        unsigned int nHashA = hash_map_get_hash(UATemp, strlen(UATemp), 1    );
        unsigned int nHashB = hash_map_get_hash(UATemp, strlen(UATemp), 2    );
        //rule_pop_encrypt_ipua_md5(UATemp, cUaMD5s);
        sprintf(cUaMD5s, "%u%u%u", nHash, nHashA, nHashB);
	strcat(szPcMessage, cUaMD5s);
	
	if(NULL != cTotalAdvIds && 0 != strlen(cTotalAdvIds))
	{
		strcat(szPcMessage, advIdStr);
		strcat(szPcMessage, cTotalAdvIds);
	}
	
    	if(NULL != sAd && 0 != strlen(sAd))
	{
		strcat(szPcMessage,adiusStr);	
		strcat(szPcMessage, sAd);
	}
	
	strcat(szPcMessage, end);
	strcat(szPcMessage, "\0");

	char *pcBase64Message = base64_encode(szPcMessage, strlen(szPcMessage)); 
	int i = 0;
	for (; i < strlen(pcBase64Message); i++)
	{
		*(pcBase64Message + i) = *(pcBase64Message + i) - 10;
	}

	pthread_mutex_lock(&win_sender_lock);
	s_send(g_pvWinSender, pcBase64Message);
	pthread_mutex_unlock(&win_sender_lock);
        //LOGT("instant send data: %s\n", szPcMessage);
        //fflush(stdout);
	free(pcBase64Message); 
}

/**********************************************************************/
// send mobile data
/*********************************************************************/
void pop_mq_direct_send_mobile_msg(const int iTid, char* pcMessage, char *cTotalAdvIds, char* sAd)
{
	if(sAd == NULL)
	{
		return;
	}

	char * advIdStr = "\",\"advId\":\"";
	char * adiusStr = "\",\"radius\":\"";
	char * Cookie = "\",\"Cookie\":\"";
	char * pcSrcIP = "\",\"srcIP\":\"";
	char * pcUa = "\",\"User-Agent\":\"";
	char * end =   "\"}";
	//next remove cookie message
	char szPcMessage[4096] = {0};
	char* Aduis = strstr(pcMessage, end);
	char* pcUaBegin = strstr(pcMessage, pcUa)+strlen(pcUa) ;
	char* pcUaEnd = strstr(pcMessage, Aduis);
	if(strstr(pcMessage, Cookie) != NULL)
	{
		char* pcCookieBegin = strstr(pcMessage, Cookie) ;
		char* pcCookieEnd = strstr(pcMessage, pcSrcIP);
		int front = pcCookieBegin - pcMessage;
		memcpy(szPcMessage, pcMessage, front);
		strncat(szPcMessage, pcCookieEnd, pcUaBegin - pcCookieEnd);
	}
	else
	{
		memcpy(szPcMessage, pcMessage, pcUaBegin-pcMessage);
	}
	//strncat(szPcMessage, pcCookieEnd, pcUaBegin - pcCookieEnd);

	char UATemp[512] = {0};
	memcpy(UATemp, pcUaBegin, (pcUaEnd - pcUaBegin));
        char cUaMD5s[33] = {'\0'};
         unsigned int nHash  = hash_map_get_hash(UATemp, strlen(UATemp), 0);
        unsigned int nHashA = hash_map_get_hash(UATemp, strlen(UATemp), 1    );
        unsigned int nHashB = hash_map_get_hash(UATemp, strlen(UATemp), 2    );
        //rule_pop_encrypt_ipua_md5(UATemp, cUaMD5s);
        sprintf(cUaMD5s, "%u%u%u", nHash, nHashA, nHashB);
	strcat(szPcMessage, cUaMD5s);
	
	if(NULL != cTotalAdvIds && 0 != strlen(cTotalAdvIds))
	{
		strcat(szPcMessage, advIdStr);
		strcat(szPcMessage, cTotalAdvIds);
	}
	
    	if(NULL != sAd && 0 != strlen(sAd))
	{
		strcat(szPcMessage,adiusStr);	
		strcat(szPcMessage, sAd);
	}
	
	strcat(szPcMessage, end);
	strcat(szPcMessage, "\0");

	char *pcBase64Message = base64_encode(szPcMessage, strlen(szPcMessage)); 
	int i = 0;
	for (; i < strlen(pcBase64Message); i++)
	{
		*(pcBase64Message + i) = *(pcBase64Message + i) - 10;
	}

	pthread_mutex_lock(&mobile_sender_lock);
	s_send(g_pvMobileSender, pcBase64Message);
	pthread_mutex_unlock(&mobile_sender_lock);
        LOGT("mobile send data: %s\n", szPcMessage);
        //fflush(stdout);
	free(pcBase64Message); 
}
/************************************************************************/
//  [8/12/2014 fushengli]                                               
//  设置广告ID并发送信息                                                                  
/************************************************************************/
void pop_mq_direct_send_surplus_msg(const int iTid, char* pcMessage, char *cTotalAdvIds, char* sAd)
{
	//printf("pcMessage : %s\n", pcMessage);
	char * advIdStr = "\",\"advId\":\"";
	char * adiusStr = "\",\"radius\":\"";
	char * Cookie = "\",\"Cookie\":\"";
	char * pcSrcIP = "\",\"srcIP\":\"";
	char * pcDstIP = "\",\"dstIP\":\"";
        char * pcUa = "\",\"User-Agent\":\"";
	char * end =   "\"}";
	char* pcBeginIP = strstr(pcMessage, pcSrcIP) + strlen(pcSrcIP);
	char* pcEndIP = strstr(pcMessage, pcDstIP);
        char* pcUaBegin = strstr(pcMessage, pcUa)+strlen(pcUa) ;
        char* pcUaEnd = strstr(pcMessage, end);
	if (pcEndIP - pcBeginIP >= 16)
	{
		return;
	}
	char srcip[16] = {0};
	memcpy(srcip, pcBeginIP, (pcEndIP - pcBeginIP));
	//next remove cookie message
	char szPcMessage[4096] = {0};
	char* Aduis = strstr(pcMessage, end) ;	
	if(strstr(pcMessage, Cookie) != NULL)
	{
		char* pcCookieBegin = strstr(pcMessage, Cookie) ;
		char* pcCookieEnd = strstr(pcMessage, pcSrcIP);
		
		int front = pcCookieBegin - pcMessage;
		memcpy(szPcMessage, pcMessage, front);
		strncat(szPcMessage, pcCookieEnd, pcUaBegin-pcCookieEnd);
	}
	else
	{
		memcpy(szPcMessage, pcMessage, pcUaBegin  - pcMessage);
	}
        char UATemp[512] = {0};
        memcpy(UATemp, pcUaBegin, (pcUaEnd - pcUaBegin));
        char cUaMD5s[64] = {'\0'};
        unsigned long nHash  = hash_map_get_hash(UATemp, strlen(UATemp), 0);
        unsigned long nHashA = hash_map_get_hash(UATemp, strlen(UATemp), 1    );
        unsigned long nHashB = hash_map_get_hash(UATemp, strlen(UATemp), 2    );
		sprintf(cUaMD5s, "%u%u%u", nHash, nHashA, nHashB);
        strcat(szPcMessage, cUaMD5s);

        if(NULL != cTotalAdvIds && 0 != strlen(cTotalAdvIds))
        {
                strcat(szPcMessage, advIdStr);
                strcat(szPcMessage, cTotalAdvIds);
        }

	if(NULL != sAd && 0 != strlen(sAd))
	{		
		strcat(szPcMessage, adiusStr);
		strcat(szPcMessage, sAd);
	}

	strcat(szPcMessage, end);
	strcat(szPcMessage, "\0");

	char *pcBase64Message = base64_encode(szPcMessage, strlen(szPcMessage)); 
	int i = 0;
	for (; i < strlen(pcBase64Message); i++)
	{
		*(pcBase64Message + i) = *(pcBase64Message + i) - 10;
	}
	pthread_mutex_lock(&surplus_sender_lock);
	s_send(g_pvSurplusSender, pcBase64Message);
	pthread_mutex_unlock(&surplus_sender_lock);
	//LOGT("mq send data: %s\n", szPcMessage);
	//fflush(stdout);
	free(pcBase64Message); 
}
