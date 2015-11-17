#include "dc_global.h"
#include "zhelpers.h"

static void* g_jcSender = NULL;
pthread_mutex_t jc_mut;
extern char* base64_encode(const char* data, int data_len);

/************************************************************************/
//  [8/8/2014 fushengli]                                               
//                                                                     
/************************************************************************/
void mq_jc_win_client_init()
{
	void * ctx = zctx_new ();
	g_jcSender = zsocket_new (ctx, ZMQ_PUSH);

	//char cNames[64] = {'\0'};
	//sprintf(cNames, "%s%d" , "tcp://222.68.194.41:", (8010+i));
	//sprintf(cNames, "%s%d" , "tcp://61.152.73.246:", (8010+i));
	//sprintf(cNames, "%s%d" , "tcp://139.159.33.227:", (8010+i));
	zsocket_connect(g_jcSender, g_conf_jc_front_adr);
	printf("jc push %s\n", g_conf_jc_front_adr);
	
    return ;
}

/************************************************************************/
//  [8/8/2014 fushengli]                                               
//  通过zmq发送信息                                                                  
/************************************************************************/
void jc_mq_direct_send_msg(int iTid, char* pcMessage, char* name)
{
	char * Cookie = "\",\"Cookie\":\"";;
	char * siteUri = "\",\"siteUri\":\"";
	char* Sec = "\",\"sec\":\"";
	char * srcIP = "\",\"srcIP\":\"";
	char * pcUa = "\",\"User-Agent\":\"";
	char * jcKey = "\",\"jcKey\":\"";
	char * end =   "\"}";

	char szPcMessage[4096] = {0};
	if(strstr(pcMessage, Cookie) != NULL)
	{
		//strcat cookie front
		//printf("%s\n", pcMessage);
		char* pcCookieBegin = strstr(pcMessage, Cookie) ;
		int front = pcCookieBegin - pcMessage;
		memcpy(szPcMessage, pcMessage, front);

		char * pcSrcIp = strstr(pcMessage, srcIP);
		char * UA = strstr(pcMessage, pcUa);
		strncat(szPcMessage, pcSrcIp, UA - pcSrcIp);

		strcat(szPcMessage, jcKey);
		strcat(szPcMessage, name);

		// remove ua next
		strcat(szPcMessage, end);
	}
	else
	{
		char * pcSec = strstr(pcMessage, Sec);
		memcpy(szPcMessage, pcMessage, pcSec-pcMessage);
		
		strcat(szPcMessage, jcKey);
		strcat(szPcMessage, name);

		strcat(szPcMessage, end);
	}

	char *pcBase64Message = base64_encode(szPcMessage, strlen(szPcMessage)); 
        int i = 0;
        for (; i < strlen(pcBase64Message); i++)
        {
                *(pcBase64Message + i) = *(pcBase64Message + i) - 10;
        }	

	pthread_mutex_lock(&jc_mut);	
	s_send(g_jcSender, pcBase64Message);  
	pthread_mutex_unlock(&jc_mut);
	//LOGT("jc send data: %s\n", szPcMessage);
	//fflush(stdout);
	free(pcBase64Message);
}
