#include "dc_global.h"

/*global reference*/
extern int load_record_url_cfg(struct RECORD_URL_S * record_url);
extern void mq_file_send_msg(const char * msg, long tid);
extern void mq_file_thread_start();
extern void mq_file_cache_put(const char * str_msg, int len);
extern void mq_record_send_msg(const char * msg, long tid);
extern void mq_record_client_init();

static struct RECORD_URL_S *rule_record_urls[16] = {NULL};
static int rule_record_urls_len[16] = {0};

/*init jc target sites*/
void rule_record_url_init()
{
    //rule_record_url_load_url();
    //mq_record_client_init();
}

/*init jc target sites*/
void rule_record_url_load_url()
{
    int i;
    for(i=0; i < 16; i++)
    {
        rule_record_urls[i] = malloc(64 * sizeof(struct RECORD_URL_S));
        rule_record_urls_len[i] = load_record_url_cfg(rule_record_urls[i]);
    }
}

static int  str_begin_with(char * start, char * end, char * begin, int len)
{
    char * src = start;
    char * dst = begin;
    int i=0;
    while((src<=end) && (i<len) && ((*src) == (*dst)))
    {
        src++;
        dst++;
        i++;
    }
    if(i == len)
        return 1;
    else
        return -1;
}

static int rule_record_url_check(const  int thread_id, char * host_start, char * host_end, char * uri_start, char * uri_end)
{
    int i;
    for(i=0; i<rule_record_urls_len[thread_id]; i++)
    {
        if((rule_record_urls[thread_id][i].host_len == host_end-host_start+1)
                &&(strncmp(rule_record_urls[thread_id][i].host, host_start, host_end-host_start+1) == 0)
                )
        {
            //printf("new %.*s\n", host_end-host_start+1, host_start );
            if(str_begin_with(uri_start, uri_end, rule_record_urls[thread_id][i].uri, rule_record_urls[thread_id][i].uri_len) >0)
                return 1;
        }
    }
    return -1;
}

static void rule_record_url_int_append(char **str, long num)
{
    if(num == 0)
    {
        *(*str)++ = '0';
        return;
    }
    int i = 0;
    char temp[64] = {0};
    while (num)
    {
        temp[i] = num % 10 + '0';
        num = num / 10;
        i++;
    }
    temp[i] = 0;
    i = i - 1;
    while (i >= 0)
    {
        *(*str)++ = temp[i];
        i--;
    }
}
static void rule_record_url_str_append(char ** dst, const char * str)
{
    const char * append_str = str;
    while(*append_str != '\0')
    {
        **dst = *append_str;
        (*dst)++;
        append_str++;
    }
}

static void rule_record_url_str_append_(char ** dst, const char * start, const char * end)
{
    if((end -start) < 1)
        return;

    const char * append_str = start;
    while(append_str<=end)
    {
        if ((*append_str == 34) || (*append_str == 13))
        {**dst = ' ';}
        else
        {
            **dst = *append_str;
        }

        (*dst)++;
        append_str++;
    }
}
static void rule_record_url_IP_append(char ** dst, u_int32_t ip)
{
    struct in_addr in;
    in.s_addr = htonl(ip);
    rule_record_url_str_append(dst, inet_ntoa(in));
}

/*main loop*/
int rule_record_url_main_loop(const  int thread_id, const u_char *buffer, const struct pfring_pkthdr* hdr,
        const struct http_request_kinfo * http)
{
	return -1;

    /*except null values*/
    if ((!http->uri_end) || (!http->uri_start) || (!http->host_end)
            || (!http->host_start || (!http->ua_end) || (!http->ua_start)))
    {
        return -1;
    }

    /*except post*/
    if(http->http_type == 1)
        return -1;

    if(rule_record_url_check(thread_id, http->host_start, http->host_end, http->uri_start, http->uri_end) < 0)
    {
        return -1;
    }

    if(((http->host_end-http->host_start+1) + (http->uri_end-http->uri_start+1) + 1) >2048)
        return -1;

    http_parser_print_payload(buffer, hdr->caplen);


    char * url = (char*) malloc(hdr->caplen);
    memset(url, '\0', hdr->caplen);
    char * dst = url;

    rule_record_url_int_append(&dst, http->sec);
    rule_record_url_str_append(&dst, "\t");
    rule_record_url_IP_append(&dst, http->sip);
    rule_record_url_str_append(&dst, "\t");
    rule_record_url_str_append_(&dst, http->ua_start, http->ua_end);
    rule_record_url_str_append(&dst, "\t");
    rule_record_url_str_append_(&dst, http->host_start, http->host_end);
    rule_record_url_str_append(&dst, "\thttp://");
    rule_record_url_str_append_(&dst, http->host_start, http->host_end);
    rule_record_url_str_append_(&dst, http->uri_start, http->uri_end);
    rule_record_url_str_append(&dst, "\t");
    rule_record_url_str_append_(&dst, http->cookies_start, http->cookies_end);
    rule_record_url_str_append(&dst, "\t");
    rule_record_url_str_append_(&dst, http->referer_start, http->referer_end);
    rule_record_url_str_append(&dst, "\n");
    *dst = '\0';

    //printf("rule_record_url_int_append=%s", url);
    mq_record_send_msg(url, thread_id);
	//mq_file_cache_put(url, strlen(url));
    free(url);
    return 1;
}
