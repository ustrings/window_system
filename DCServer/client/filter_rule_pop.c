/*
* pop rule
* we check the host and uri, then make a zero mq msg to the adForce server
* there are 3 detail rules
* 1. search a key world
* 2. explorer a web url that one of white name list
* 3. not exceed N times
*/

#include "dc_global.h"

/*global reference*/
extern void hash_map_read_file(HashMap* psHashmap, const char * ccFilename);
extern HashMap* hash_map_new(unsigned int length);
extern int hash_map_insert(HashMap* psHashMap, char*start, int len, long* in_val);
extern int hash_map_find(HashMap* psHashMap, char*start, int len, long* out_val);
HashMap* hash_map_load_file(const char * ccFilename, unsigned int length);

extern void mq_pop_win_sender_pop_init();
extern void mq_pop_mobile_sender_pop_init();
extern void pop_mq_direct_send_msg_tunneled(const int tid, u_char *buffer, const struct pfring_pkthdr * hdr, const struct http_request_kinfo * http);
extern void pop_mq_direct_send_msg(const int tid, u_char *buffer, const struct pfring_pkthdr * hdr, const struct http_request_kinfo * http);
extern void pop_mq_mobile_send_msg_tunneled(const int tid, u_char *buffer, const struct pfring_pkthdr * hdr, const struct http_request_kinfo * http);
extern void pop_mq_mobile_send_msg(const int tid, u_char *buffer, const struct pfring_pkthdr * hdr, const struct http_request_kinfo * http);

extern void rule_pop_cal_total_ip(const int iTid, const struct pfring_pkthdr* hdr, const struct http_request_kinfo * http);

#define SITE_WHITE_SUM 100000
#define HTML_TYPE_SIZE 3

//弹窗网站的白名单
HashMap* g_pWhiteSiteHashMap = NULL;
HashMap* g_pNoWhiteSiteHashMap = NULL;

//弹窗网站的移动名单
HashMap* g_pMoblieSiteHashMap = NULL;

int g_iPopNum[THREAD_NUM] = {0};

//弹窗的html页面类型
char* g_pcHtmlType[HTML_TYPE_SIZE] = {".htm", ".html", ".shtml"};

/*init pop filtered urls*/
static void rule_pop_load_filter_url()
{
	g_pNoWhiteSiteHashMap = hash_map_new(SITE_WHITE_SUM);

	char sFileName[2048] = {0};
	DIR * dirp; //directory pointer
	dirp = opendir(FILE_SENDER_OTHER_URL_CONF);
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
			strcpy(sFileName, FILE_SENDER_OTHER_URL_CONF);
			strcat(sFileName, direntp->d_name);
			if(stat(sFileName, &st) == -1)
			{
				printf("get %s file info failed\n", sFileName);				
				continue;
			}
			if((st.st_mode& S_IFMT) == S_IFDIR)//judge is or not directory
			{
			}
			else
			{
				printf("%s\n", sFileName);
				hash_map_read_file(g_pNoWhiteSiteHashMap, sFileName);
				printf("rule_pop_load_filter_url hash table size : %d\n", g_pNoWhiteSiteHashMap->size);	
			}
		}
	}
	if(dirp != NULL) closedir(dirp);
	return;
}

/*init mobile pop filtered urls*/
static void rule_pop_load_mobile_url()
{
	g_pMoblieSiteHashMap = hash_map_new(SITE_WHITE_SUM);

	char sFileName[2048] = {0};
	DIR * dirp; //directory pointer
	dirp = opendir(FILE_SENDER_MOBILE_URL_CONF);
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
			strcpy(sFileName, FILE_SENDER_MOBILE_URL_CONF);
			strcat(sFileName, direntp->d_name);
			if(stat(sFileName, &st) == -1)
			{
				printf("get %s file info failed\n", sFileName);				
				continue;
			}
			if((st.st_mode& S_IFMT) == S_IFDIR)//judge is or not directory
			{
			}
			else
			{
				printf("%s\n", sFileName);
				hash_map_read_file(g_pMoblieSiteHashMap, sFileName);
				printf("rule_pop_load_mobile_url hash table size : %d\n", g_pMoblieSiteHashMap->size);	
			}
		}
	}
	if(dirp != NULL) closedir(dirp);
	return;
}

/*check host url white name list*/
static bool rule_pop_check_host(const u_char * host_start, const u_char * host_end)
{
	if(g_conf_pop_name_list == 0)
	{
		if (hash_map_find(g_pWhiteSiteHashMap, host_start, host_end-host_start+1, NULL) < 0)
			return false;
		else
			return true;
	}
	else
	{
		if (hash_map_find(g_pWhiteSiteHashMap, host_start, host_end-host_start+1, NULL) < 0)
			return true;
		else
			return false;
	}
}

static bool rule_pop_jc_check_host(const u_char * host_start, const u_char * host_end , char * uri_start, char * uri_end)
{
	if(g_conf_pop_name_list == 0)
	{   
		if (hash_map_find(g_pNoWhiteSiteHashMap, host_start, host_end-host_start+1, NULL) < 0)
			return false;
		else
			return true;
	}   
	else
	{   
		if (hash_map_find(g_pNoWhiteSiteHashMap, host_start, host_end-host_start+1, NULL) < 0)
			return true;
		else
			return false;
	}   
}

/*inti pop windows*/
int rule_pop_init()
{
	/*init mq to adForce*/
	mq_pop_win_sender_pop_init();

	/*load filter url*/
	rule_pop_load_filter_url();
	if(g_conf_enable_pop_mobile)
	{
		mq_pop_mobile_sender_pop_init();
		rule_pop_load_mobile_url();
	}

	return 0;
}

/************************************************************************/
//  [7/31/2014 fushengli]                                               
//  判断请求页面是否为首页或者html页面                                                                   
/************************************************************************/
static bool rule_pop_is_html(const u_char * host_start, const u_char * host_end, char * uri_start, char * uri_end)
{
	if(rule_pop_jc_check_host(host_start, host_end,uri_start, uri_end))
	{
		return true;
	}
	/*1. check host url*/
	if(!rule_pop_check_host(host_start, host_end))
	{
		//printf("rule_pop_is_html1 come a %.*s, rule_pop_check_host\n", host_end-host_start+1, host_start);
		return false;
	}

	int iUriLen = uri_end - uri_start + 1; 

	//the url is too long or too small, is not page url
	if (iUriLen > 512)
	{
		return false;
	}


	/*pop main page*/
	if(((iUriLen == 1) && (*uri_start == '/')) || ( (*uri_start == '/') && (*(uri_start+1) == '?')))
	{
		return true;
	}

	int iPos = 0;
	while (iPos < iUriLen && (*(uri_start + iPos) != '?'))
	{
		iPos++;
	}

	//含有?则需要判断大于6个字符才能比较shtml
	if (iPos < iUriLen && iPos <= 6)
	{
		return false;
	}

	char* pcEndPos = uri_end;
	if (iPos < iUriLen)
	{
		pcEndPos = uri_start + iPos - 1;
	}

	int t = 0;
	int i = 0;
	int iHtmlTypeLen = 0;
	for (; t < HTML_TYPE_SIZE; t++)
	{
		i = 0;
		iHtmlTypeLen = strlen(g_pcHtmlType[t]);
		for (; i < iHtmlTypeLen; i++)
		{
			if (g_pcHtmlType[t][iHtmlTypeLen - i - 1] != *(pcEndPos - i))
			{
				break;
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
//  [7/31/2014 fushengli]                                               
//  判断请求是否为移动网站                                                                  
/************************************************************************/
static bool rule_pop_is_mobile_site(const u_char * host_start, const u_char * host_end, const u_char * uri_begin, const u_char* uri_end)
{
	char host[512] = {0};
	if(host_end - host_start + 1 > 512)
	{
		return false;
	}
	memcpy(host, host_start, (host_end - host_start + 1));
	if(hash_map_find(g_pMoblieSiteHashMap, host_start, (host_end - host_start + 1), NULL) < 0)
	{
		//printf("host not in nobile list : %s\n", host);
		return false;
	}

	int iUriLen = uri_end - uri_begin + 1;
	if(iUriLen > 2048)
	{
		return false;
	}
	return true;
}

void printJcApk(const u_char *uri_begin, const u_char *uri_end, const u_char *host_start, const u_char *host_end)
{
	if (uri_end - uri_begin + 1 > 512 || host_end - host_start + 1 > 128)
	{
		return;
	}
	char uri[512] = {0};
	memcpy(uri, uri_begin, uri_end - uri_begin + 1);
	char * pos = strstr(uri,".apk");
	if (pos != NULL)
	{
		char host[128] = {0};
		memcpy(host, host_start, host_end - host_start + 1);
		printf("printJcApk=%s%s\n", host, uri);
	}
}

/*main loop*/
int rule_pop_main_loop(const int thread_id, const u_char *buffer, const struct pfring_pkthdr* hdr,
	const struct http_request_kinfo * http)
{
	/*except null values*/
	if ((!buffer)||(!http->uri_end) || (!http->uri_start) || (!http->host_end)
		|| (!http->host_start || (!http->ua_end) || (!http->ua_start)))
	{
		return -1;
	}
	/*except post*/
	if(1 == http->http_type)
	{
		return -1;
	}
	
	//printJcApk(http->uri_start, http->uri_end, http->host_start, http->host_end);	
	
	//rule_pop_cal_total_ip(thread_id, hdr, http);
	if (g_conf_enable_pop_mobile && rule_pop_is_mobile_site(http->host_start, http->host_end, http->uri_start, http->uri_end))
	{

		if(hdr->extended_hdr.parsed_pkt.tunnel.tunnel_id != NO_TUNNEL_ID)
		{
			g_iPopNum[thread_id]++;
			pop_mq_mobile_send_msg_tunneled(thread_id, buffer, hdr, http);
		}
		else
		{
			g_iPopNum[thread_id]++;
			pop_mq_mobile_send_msg(thread_id, buffer, hdr, http);
		}

		return 1;
	}
	else if(rule_pop_is_html(http->host_start, http->host_end, http->uri_start, http->uri_end))
	{
		/*check need pop*/
		if(hdr->extended_hdr.parsed_pkt.tunnel.tunnel_id != NO_TUNNEL_ID)
		{
			g_iPopNum[thread_id]++;
			pop_mq_direct_send_msg_tunneled(thread_id, buffer, hdr, http);
		}
		else
		{
			g_iPopNum[thread_id]++;
			pop_mq_direct_send_msg(thread_id, buffer, hdr, http);
		}

		return 1;
	}

	return -1;
}
