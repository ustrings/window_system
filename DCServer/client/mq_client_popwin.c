#include "dc_global.h"
#include "zhelpers.h"
#include <sys/timeb.h>

#define MAX_IP_LEN 32
#define WIN_SENDER_LEN 8
#define WIN_SENDER_SUB_LEN 2
#define TOTAL_IP_SUM 250000
#define TEST

extern char g_currTime[16][64];
static void* g_pvHashWinSender[MAX_NUM_THREADS] = { NULL };
//extern unsigned long hash_map_get_hash_number(char *start, int strlen, int hashLen);
extern char** common_get_range_addr(const char * addr_begin, int num);
extern HashMap* hash_map_new(unsigned int length);
extern int hash_map_insert(HashMap* psHashMap, char*start, int len, long* in_val);
extern int hash_map_find(HashMap* psHashMap, char*start, int len, long* out_val);

static pthread_mutex_t zmq_send_lock;

/*存储所有的IP信息*/
HashMap* g_pTotalIP[THREAD_NUM] = {NULL};

/************************************************************************/
//  [8/6/2014 fushengli]                                               
//  初始化zmq context                                                                   
/************************************************************************/
void mq_pop_win_sender_pop_init()
{
	int i = 0;
	for (; i < MAX_NUM_THREADS; i++)
	{
		void *pvCtx = zmq_ctx_new();
		g_pvHashWinSender[i] = zmq_socket (pvCtx, ZMQ_PUSH);
		char cNames[64] = {'\0'};
		sprintf(cNames, "%s%d" , "tcp://127.0.0.1:",  (8110 + i));
		zmq_connect(g_pvHashWinSender[i], cNames);
		printf("mq_pop_win_client_init ipc name=%s\n", cNames);
	}

	for (i = 0; i < MAX_NUM_THREADS; i++)
	{
		g_pTotalIP[i] = hash_map_new(TOTAL_IP_SUM);
	}
	return;
}

/************************************************************************/
//  [8/6/2014 fushengli]                                               
//                                                                     
/************************************************************************/
static void mq_pop_win_str_append(char ** dst, const char * str)
{
    const char * append_str = str;
    while(*append_str != '\0')
    {
        **dst = *append_str;
        (*dst)++;
        append_str++;
    }
}

static void mq_pop_win_str_append_(char ** dst, const char * start, const char * end)
{
    const char * append_str = start;
    while(append_str<=end)
    {
        **dst = *append_str;
        (*dst)++;
        append_str++;
    }
}

static void mq_pop_win_int_append(char **str, u_int num)
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

static void mq_pop_win_MAC_append_(char **str,  u_int8_t dmac[6])
{
    char buf[40];
    snprintf(buf, sizeof(buf), "%d",  dmac[0]);
    mq_pop_win_str_append(str, buf);

    int i = 1;
    for(i=1; i < 6; i++)
    {
        snprintf(buf, sizeof(buf), "%d",  dmac[i]);
        mq_pop_win_str_append(str, ":");
        mq_pop_win_str_append(str, buf);
    }
}

static void mq_pop_win_MAC_append(char **str, const u_char *mac) {
    static char hex[] = "0123456789ABCDEF";

  u_int i, j;

  if ((j = *mac >> 4) != 0)
    *(*str)++ = hex[j];
  else
    *(*str)++ = '0';

  *(*str)++ = hex[*mac++ & 0xf];

  for(i = 5; (int)--i >= 0;) {
    *(*str)++ = ':';
    if ((j = *mac >> 4) != 0)
      *(*str)++ = hex[j];
    else
      *(*str)++ = '0';

    *(*str)++ = hex[*mac++ & 0xf];
  }
}

static void inet_ntoa_r(unsigned int ip, char ipstr[MAX_IP_LEN])
{
	unsigned char *p = (unsigned char *)&ip;
	snprintf(ipstr, MAX_IP_LEN, "%d.%d.%d.%d", p[3], p[2], p[1], p[0]);
}

static int mq_pop_win_IP_Hash_append(char ** dst, u_int32_t ip)
{
	char ipstr[MAX_IP_LEN];
	inet_ntoa_r(ip, ipstr);
	mq_pop_win_str_append(dst, ipstr);
	return hash_map_get_hash_number(ipstr, strlen(ipstr), 16);
}

static void mq_pop_win_IP_append(char ** dst, u_int32_t ip)
{
	char ipstr[MAX_IP_LEN];
	inet_ntoa_r(ip, ipstr);
	mq_pop_win_str_append(dst, ipstr);
}

/************************************************************************/
//  计算用户IP数量                                                               
/************************************************************************/
void rule_pop_cal_total_ip(const int iTid, const struct pfring_pkthdr* hdr, const struct http_request_kinfo * http)
{
	char ua[1024] = {0};
	int ua_len = http->ua_end - http->ua_start;
	if(ua_len > 1024 || http->ua_start == NULL || http->ua_end == NULL)
	{
		return;
	}
	memcpy(ua, http->ua_start, ua_len);
	u_int32_t ip = 0;
	if(hdr->extended_hdr.parsed_pkt.tunnel.tunnel_id != NO_TUNNEL_ID)
	{
		ip = hdr->extended_hdr.parsed_pkt.tunnel.tunneled_ip_src.v4;
	}
	else
	{
		ip = hdr->extended_hdr.parsed_pkt.ipv4_src;
	}

	if (0 != ip && strlen(ua) != 0)
	{
		char ipstr[MAX_IP_LEN];
		inet_ntoa_r(ip, ipstr);
		char IPUA[1040] = {0};
		strcpy(IPUA, ipstr);
		strcat(IPUA, ua);
		int iNum = hash_map_get_hash_number(ipstr, strlen(ipstr), 16);

		int iRes = hash_map_find(g_pTotalIP[iNum], IPUA, strlen(IPUA), NULL);
		if (iRes >= 0)
		{
			return;
		}

		hash_map_insert(g_pTotalIP[iNum], IPUA, strlen(IPUA), NULL);
	}

	return;
}

void pop_mq_direct_send_msg_tunneled(const int tid, u_char *buffer, const struct pfring_pkthdr * hdr, const struct http_request_kinfo * http)
{
   // http_parser_print_payload(buffer, 500);

    char jsonstr[1024*5] = {'\0'};
    char * destJson = jsonstr;

    char * sequence = "{\"sequence\":\"";
    char * ack = "\",\"ackNumber\":\"";
    char * ipLength = "\",\"ipLength\":\"";
    char * tcpOptionLength = "\",\"tcpOptionLength\":\"";
    char * srcPort = "\",\"srcPort\":\"";
    char * dstPort = "\",\"dstPort\":\"";
    char * Cookie = "\",\"Cookie\":\"";
    char * srcIP = "\",\"srcIP\":\"";
    char * dstIP = "\",\"dstIP\":\"";
    char * dstMac = "\",\"dstMac\":\"";
    char * srcMac = "\",\"srcMac\":\"";
    char * siteUrl = "\",\"siteUrl\":\"";
    char * siteUri = "\",\"siteUri\":\"";
    char * pcUa = "\",\"User-Agent\":\"";
    char* pcSec = "\",\"sec\":\"";
    char * end =   "\"}";

    struct tcphdr * tcp = (struct tcphdr *)(&buffer[hdr->extended_hdr.parsed_pkt.offset.payload_offset
            +sizeof(struct compact_ip_hdr)]);

    mq_pop_win_str_append(&destJson, sequence);
    mq_pop_win_int_append(&destJson, ntohl(tcp->seq));

    mq_pop_win_str_append(&destJson, ack);
    mq_pop_win_int_append(&destJson, ntohl(tcp->ack_seq));

    u_int16_t* ip = (u_int16_t *)(&buffer[2+hdr->extended_hdr.parsed_pkt.offset.payload_offset]);

    mq_pop_win_str_append(&destJson, ipLength);
    mq_pop_win_int_append(&destJson, htons(*ip));

    mq_pop_win_str_append(&destJson, tcpOptionLength);
    mq_pop_win_int_append(&destJson, tcp->doff * 4 - (sizeof(struct tcphdr)));

    mq_pop_win_str_append(&destJson, srcPort);
    mq_pop_win_int_append(&destJson, hdr->extended_hdr.parsed_pkt.tunnel.tunneled_l4_src_port);

    mq_pop_win_str_append(&destJson, dstPort);
    mq_pop_win_int_append(&destJson, hdr->extended_hdr.parsed_pkt.tunnel.tunneled_l4_dst_port);

    //add by zhaoyunzhou 2014/08/22
	if (http->cookies_start && http->cookies_end)
	{
		mq_pop_win_str_append(&destJson, Cookie);
		mq_pop_win_str_append_(&destJson, http->cookies_start, http->cookies_end);
	}
	
    mq_pop_win_str_append(&destJson, srcIP);
    int iSendNum = mq_pop_win_IP_Hash_append(&destJson, hdr->extended_hdr.parsed_pkt.tunnel.tunneled_ip_src.v4);

    mq_pop_win_str_append(&destJson, dstIP);
    mq_pop_win_IP_append(&destJson, hdr->extended_hdr.parsed_pkt.tunnel.tunneled_ip_dst.v4);

    mq_pop_win_str_append(&destJson, dstMac);
    mq_pop_win_MAC_append_(&destJson, hdr->extended_hdr.parsed_pkt.dmac);

    mq_pop_win_str_append(&destJson, srcMac);
    mq_pop_win_MAC_append_(&destJson, hdr->extended_hdr.parsed_pkt.smac);

    mq_pop_win_str_append(&destJson, siteUrl);
    mq_pop_win_str_append_(&destJson, http->host_start,http->host_end);

	mq_pop_win_str_append(&destJson, siteUri);
    mq_pop_win_str_append_(&destJson, http->uri_start, http->uri_end);

	char sectamp[64] = {0};
	sprintf(sectamp, "%u", http->sec);
	mq_pop_win_str_append(&destJson, pcSec);
	mq_pop_win_str_append(&destJson, sectamp);
	
	mq_pop_win_str_append(&destJson, pcUa);
	mq_pop_win_str_append_(&destJson, http->ua_start, http->ua_end);

    mq_pop_win_str_append(&destJson, end);
	*destJson = '\0';
	pthread_mutex_lock(&zmq_send_lock);
	s_send (g_pvHashWinSender[iSendNum], jsonstr);
	pthread_mutex_unlock(&zmq_send_lock);

    //zframe_t *frame = zframe_new (jsonstr, strlen(jsonstr));
    //zframe_send (&frame, g_pop_mq_direct_client[tid], 0);
    //LOGT("mq send data: %s\n", jsonstr);
    //fflush(stdout);
}

void pop_mq_direct_send_msg(const int tid, u_char *buffer, const struct pfring_pkthdr * hdr, const struct http_request_kinfo * http)
{
    //http_parser_print_payload(buffer,hdr->len+10);

	char jsonstr[2048*5] = {'\0'};
	char * destJson = jsonstr;
	char * sequence = "{\"sequence\":\"";
	char * ack = "\",\"ackNumber\":\"";
	char * ipLength = "\",\"ipLength\":\"";
	char * tcpOptionLength = "\",\"tcpOptionLength\":\"";
	char * srcPort = "\",\"srcPort\":\"";
	char * dstPort = "\",\"dstPort\":\"";
	char * Cookie =  "\",\"Cookie\":\"";
	char * srcIP = "\",\"srcIP\":\"";
	char * dstIP = "\",\"dstIP\":\"";
	char * dstMac = "\",\"dstMac\":\"";
	char * srcMac = "\",\"srcMac\":\"";
	char * siteUrl = "\",\"siteUrl\":\"";
	char * siteUri = "\",\"siteUri\":\"";
	char * pcUa = "\",\"User-Agent\":\"";
	char * pcSec = "\",\"sec\":\"";
	char * pcClientSec = "\",\"clientSendTime\":\"";
	char * end =   "\"}";

	mq_pop_win_str_append(&destJson, sequence);
	mq_pop_win_int_append(&destJson, hdr->extended_hdr.parsed_pkt.tcp.seq_num);

	mq_pop_win_str_append(&destJson, ack);
	mq_pop_win_int_append(&destJson, hdr->extended_hdr.parsed_pkt.tcp.ack_num);

	u_int16_t* ip = (u_int16_t *)(&buffer[2+hdr->extended_hdr.parsed_pkt.offset.l3_offset]);

	mq_pop_win_str_append(&destJson, ipLength);
	mq_pop_win_int_append(&destJson,htons(*ip) );

	struct tcphdr * tcp = (struct tcphdr *)(&buffer[hdr->extended_hdr.parsed_pkt.offset.l4_offset]);
	mq_pop_win_str_append(&destJson, tcpOptionLength);
	mq_pop_win_int_append(&destJson, tcp->doff * 4 - (sizeof(struct tcphdr)));

	mq_pop_win_str_append(&destJson, srcPort);
	mq_pop_win_int_append(&destJson, hdr->extended_hdr.parsed_pkt.l4_src_port);

	mq_pop_win_str_append(&destJson, dstPort);
	mq_pop_win_int_append(&destJson, hdr->extended_hdr.parsed_pkt.l4_dst_port);

	if (http->cookies_start && http->cookies_end)
	{
		mq_pop_win_str_append(&destJson, Cookie);
		mq_pop_win_str_append_(&destJson, http->cookies_start, http->cookies_end);	
	}

	mq_pop_win_str_append(&destJson, srcIP);
	int iSendNum = mq_pop_win_IP_Hash_append(&destJson, hdr->extended_hdr.parsed_pkt.ipv4_src);

	mq_pop_win_str_append(&destJson, dstIP);
	mq_pop_win_IP_append(&destJson, hdr->extended_hdr.parsed_pkt.ipv4_dst);

	mq_pop_win_str_append(&destJson, dstMac);
	mq_pop_win_MAC_append_(&destJson, hdr->extended_hdr.parsed_pkt.dmac);

	mq_pop_win_str_append(&destJson, srcMac);
	mq_pop_win_MAC_append_(&destJson, hdr->extended_hdr.parsed_pkt.smac);

	mq_pop_win_str_append(&destJson, siteUrl);
	mq_pop_win_str_append_(&destJson, http->host_start, http->host_end);


	mq_pop_win_str_append(&destJson, siteUri);
	mq_pop_win_str_append_(&destJson, http->uri_start, http->uri_end);

	char sectamp[64] = {0};
	sprintf(sectamp, "%u", http->sec);

	mq_pop_win_str_append(&destJson, pcSec);
	mq_pop_win_str_append(&destJson, sectamp);
        /*char currTime[64] = {0};
        struct timeval start1;
        gettimeofday(&start1,0);
        sprintf(currTime,"%ld",((long)start1.tv_sec*1000+(long)start1.tv_usec/1000));*/

	/*mq_pop_win_str_append(&destJson, pcClientSec);
	mq_pop_win_str_append(&destJson, g_currTime[tid]);*/

	mq_pop_win_str_append(&destJson, pcUa);
	mq_pop_win_str_append_(&destJson, http->ua_start, http->ua_end);


	mq_pop_win_str_append(&destJson, end);
	*destJson = '\0';

	pthread_mutex_lock(&zmq_send_lock);
	s_send (g_pvHashWinSender[iSendNum], jsonstr);
	pthread_mutex_unlock(&zmq_send_lock);

    //zframe_t *frame = zframe_new (jsonstr, strlen(jsonstr));
    //zframe_send (&frame, g_pop_mq_direct_client[tid], 0);
    //LOGT("mq send data: %s\n", jsonstr);
   //fflush(stdout);
}
