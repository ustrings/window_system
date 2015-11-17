#include "dc_global.h"

/*global reference*/
extern void mq_file_send_msg(const char * msg, long tid);
extern void mq_file_thread_start();
extern void mq_file_cache_put(const char * str_msg, int len);
extern void mq_record_send_msg(const char * msg, long tid);
extern void mq_record_client_init();

static struct RECORD_URL_S *rule_record_urls[16] = {NULL};
static int rule_record_urls_len[16] = {0};

int load_record_url_cfg(struct RECORD_URL_S * record_url)
{
	//printf ("begin load load_record_url_cfg: \n");

	FILE *fp= fopen(FILE_RECORD_CONF,"r");
	if(!fp) return -1;

	int i = 0;
	while(!feof(fp))
	{
		char wd[512]={'\0'};
		fgets(wd, 512, fp);
		wd[strlen(wd) - 1] = '\0';

		if(strlen(wd)<5)
			break;

		char * uri_index = strstr(wd, "/");
		if(!uri_index)
			continue;

		record_url[i].host = malloc(512);
		memcpy(record_url[i].host, wd, uri_index-wd);
		record_url[i].host[uri_index-wd] = '\0';
		record_url[i].host_len = strlen(record_url[i].host );

		record_url[i].uri = malloc(512);
		strcpy(record_url[i].uri, uri_index);
		record_url[i].uri_len = strlen(record_url[i].uri );

		//        int err = regcomp(&record_url[i].uri_re, record_url[i].uri, REG_EXTENDED);
		//        char errbuf [128];
		//        if (err)
		//        {
		//            regerror(err, &record_url[i].uri_re, errbuf, sizeof(errbuf));
		//            printf("error: regcomp: %s\n", errbuf);
		//            return -1;
		//        }
		printf("host:%s, uri:%s, host_len:%d, uri_len:%d\n", record_url[i].host, record_url[i].uri,
			record_url[i].host_len, record_url[i].uri_len);
		i++;
	}
	printf("load record url %d\n", i);
	fclose(fp);

	return i;
}

/*init jc target sites*/
static void rule_record_url_load_url()
{
	int i;
	for(i=0; i < 16; i++)
	{
		rule_record_urls[i] = malloc(64 * sizeof(struct RECORD_URL_S));
		rule_record_urls_len[i] = load_record_url_cfg(rule_record_urls[i]);
	}
}

/*init jc target sites*/
void rule_record_url_init()
{
    rule_record_url_load_url();
    //mq_record_client_init();
}

static int rule_record_url_check(const int ciTid, const char * pcHost, const char * pcUri)
{
    int i;
    for(i = 0; i < rule_record_urls_len[ciTid]; i++)
    {
        if((rule_record_urls[ciTid][i].host_len == strlen(pcHost))
                &&(strcmp(rule_record_urls[ciTid][i].host, pcHost) == 0)
                &&(strcmp(rule_record_urls[ciTid][i].uri, pcUri) == 0))
        {
            return 1;
        }
    }
    return -1;
}

/*main loop*/
int rule_record_url_main_loop(const int iTid, char* pcMessage, const MqHttpMessage* psMqHttpMes)
{
    if(rule_record_url_check(iTid, psMqHttpMes->host, psMqHttpMes->pcUri) < 0)
    {
        return -1;
    }

    //printf("rule_record_url_main_loop=%s\n", pcMessage);
    //mq_record_send_msg(pcMessage, iTid);
	mq_file_cache_put(pcMessage, strlen(pcMessage));
    return 1;
}
