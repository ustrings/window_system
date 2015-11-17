#include "dc_global.h"
#include "zhelpers.h"
#include <sys/timeb.h>

#define MAX_IP_LEN 32
#define WIN_SENDER_LEN 8
#define WIN_SENDER_SUB_LEN 2
#define TOTAL_IP_SUM 250000

static void* g_pvMobileHashWinSender[MAX_NUM_THREADS] = { NULL };
//extern unsigned long hash_map_get_hash_number(char *start, int strlen, int hashLen);
extern char** common_get_range_addr(const char * addr_begin, int num);
extern HashMap* hash_map_new(unsigned int length);
extern int hash_map_insert(HashMap* psHashMap, char*start, int len, long* in_val);
extern int hash_map_find(HashMap* psHashMap, char*start, int len, long* out_val);

static pthread_mutex_t zmq_mobile_send_lock;

/************************************************************************/
//  [8/6/2014 fushengli]                                               
//  ≥ı ºªØzmq context                                                                 
/************************************************************************/
void mq_pop_mobile_sender_pop_init()
{
	int i = 0;
	for (; i < MAX_NUM_THREADS; i++)
	{
		void *pvCtx = zmq_ctx_new();
		g_pvMobileHashWinSender[i] = zmq_socket (pvCtx, ZMQ_PUSH);
		char cNames[64] = {'\0'};
		sprintf(cNames, "%s%d" , "tcp://127.0.0.1:",  (8210 + i));
		zmq_connect(g_pvMobileHashWinSender[i], cNames);
		printf("mq_pop_mobile_client_init tcp name=%s\n", cNames);
	}

	return;
}

/************************************************************************/
//  [8/6/2014 fushengli]                                               
//                                                                
/************************************************************************/
static void mq_pop_mobile_str_append(char ** dst, const char * str)
{
    const char * append_str = str;
    while(*append_str != '\0')
    {
        **dst = *append_str;
        (*dst)++;
        append_str++;
    }
}

static void mq_pop_mobile_str_append_(char ** dst, const char * start, const char * end)
{
    const char * append_str = start;
    while(append_str<=end)
    {
        **dst = *append_str;
        (*dst)++;
        append_str++;
    }
}

static void mq_pop_mobile_int_append(char **str, u_int num)
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

static void mq_pop_mobile_MAC_append_(char **str,  u_int8_t dmac[6])
{
    char buf[40];
    snprintf(buf, sizeof(buf), "%d",  dmac[0]);
    mq_pop_mobile_str_append(str, buf);

    int i = 1;
    for(i=1; i < 6; i++)
    {
        snprintf(buf, sizeof(buf), "%d",  dmac[i]);
        mq_pop_mobile_str_append(str, ":");
        mq_pop_mobile_str_append(str, buf);
    }
}

static void inet_ntoa_r(unsigned int ip, char ipstr[MAX_IP_LEN])
{
	unsigned char *p = (unsigned char *)&ip;
	snprintf(ipstr, MAX_IP_LEN, "%d.%d.%d.%d", p[3], p[2], p[1], p[0]);
}

static int mq_pop_mobile_IP_Hash_append(char ** dst, u_int32_t ip)
{
	char ipstr[MAX_IP_LEN];
	inet_ntoa_r(ip, ipstr);
	mq_pop_mobile_str_append(dst, ipstr);
	return hash_map_get_hash_number(ipstr, strlen(ipstr), 16);
}

static void mq_pop_mobile_IP_append(char ** dst, u_int32_t ip)
{
	char ipstr[MAX_IP_LEN];
	inet_ntoa_r(ip, ipstr);
	mq_pop_mobile_str_append(dst, ipstr);
}

void pop_mq_mobile_send_msg_tunneled(const int tid, u_char *buffer, const struct pfring_pkthdr * hdr, const struct http_request_kinfo * http)
{
   // http_parser_print_payload(buffer, 500);
    char jsonstr[2048*5] = {'\0'};
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

    mq_pop_mobile_str_append(&destJson, sequence);
    mq_pop_mobile_int_append(&destJson, ntohl(tcp->seq));

    mq_pop_mobile_str_append(&destJson, ack);
    mq_pop_mobile_int_append(&destJson, ntohl(tcp->ack_seq));

    u_int16_t* ip = (u_int16_t *)(&buffer[2+hdr->extended_hdr.parsed_pkt.offset.payload_offset]);

    mq_pop_mobile_str_append(&destJson, ipLength);
    mq_pop_mobile_int_append(&destJson, htons(*ip));

    mq_pop_mobile_str_append(&destJson, tcpOptionLength);
    mq_pop_mobile_int_append(&destJson, tcp->doff * 4 - (sizeof(struct tcphdr)));

    mq_pop_mobile_str_append(&destJson, srcPort);
    mq_pop_mobile_int_append(&destJson, hdr->extended_hdr.parsed_pkt.tunnel.tunneled_l4_src_port);

    mq_pop_mobile_str_append(&destJson, dstPort);
    mq_pop_mobile_int_append(&destJson, hdr->extended_hdr.parsed_pkt.tunnel.tunneled_l4_dst_port);

    //add by zhaoyunzhou 2014/08/22
	if (http->cookies_start && http->cookies_end)
	{
		mq_pop_mobile_str_append(&destJson, Cookie);
		mq_pop_mobile_str_append_(&destJson, http->cookies_start, http->cookies_end);
	}
	
    mq_pop_mobile_str_append(&destJson, srcIP);
    int iSendNum = mq_pop_mobile_IP_Hash_append(&destJson, hdr->extended_hdr.parsed_pkt.tunnel.tunneled_ip_src.v4);

    mq_pop_mobile_str_append(&destJson, dstIP);
    mq_pop_mobile_IP_append(&destJson, hdr->extended_hdr.parsed_pkt.tunnel.tunneled_ip_dst.v4);

    mq_pop_mobile_str_append(&destJson, dstMac);
    mq_pop_mobile_MAC_append_(&destJson, hdr->extended_hdr.parsed_pkt.dmac);

    mq_pop_mobile_str_append(&destJson, srcMac);
    mq_pop_mobile_MAC_append_(&destJson, hdr->extended_hdr.parsed_pkt.smac);

    mq_pop_mobile_str_append(&destJson, siteUrl);
    mq_pop_mobile_str_append_(&destJson, http->host_start,http->host_end);

	mq_pop_mobile_str_append(&destJson, siteUri);
    mq_pop_mobile_str_append_(&destJson, http->uri_start, http->uri_end);

	char sectamp[64] = {0};
	sprintf(sectamp, "%u", http->sec);
	mq_pop_mobile_str_append(&destJson, pcSec);
	mq_pop_mobile_str_append(&destJson, sectamp);
	
	mq_pop_mobile_str_append(&destJson, pcUa);
	mq_pop_mobile_str_append_(&destJson, http->ua_start, http->ua_end);

    mq_pop_mobile_str_append(&destJson, end);
	*destJson = '\0';
	pthread_mutex_lock(&zmq_mobile_send_lock);
	s_send (g_pvMobileHashWinSender[iSendNum], jsonstr);
	pthread_mutex_unlock(&zmq_mobile_send_lock);
}


void pop_mq_mobile_send_msg(const int tid, u_char *buffer, const struct pfring_pkthdr * hdr, const struct http_request_kinfo * http)
{
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
	char * end =   "\"}";

	mq_pop_mobile_str_append(&destJson, sequence);
	mq_pop_mobile_int_append(&destJson, hdr->extended_hdr.parsed_pkt.tcp.seq_num);

	mq_pop_mobile_str_append(&destJson, ack);
	mq_pop_mobile_int_append(&destJson, hdr->extended_hdr.parsed_pkt.tcp.ack_num);

	u_int16_t* ip = (u_int16_t *)(&buffer[2+hdr->extended_hdr.parsed_pkt.offset.l3_offset]);

	mq_pop_mobile_str_append(&destJson, ipLength);
	mq_pop_mobile_int_append(&destJson,htons(*ip) );

	struct tcphdr * tcp = (struct tcphdr *)(&buffer[hdr->extended_hdr.parsed_pkt.offset.l4_offset]);
	mq_pop_mobile_str_append(&destJson, tcpOptionLength);
	mq_pop_mobile_int_append(&destJson, tcp->doff * 4 - (sizeof(struct tcphdr)));

	mq_pop_mobile_str_append(&destJson, srcPort);
	mq_pop_mobile_int_append(&destJson, hdr->extended_hdr.parsed_pkt.l4_src_port);

	mq_pop_mobile_str_append(&destJson, dstPort);
	mq_pop_mobile_int_append(&destJson, hdr->extended_hdr.parsed_pkt.l4_dst_port);

	if (http->cookies_start && http->cookies_end)
	{
		mq_pop_mobile_str_append(&destJson, Cookie);
		mq_pop_mobile_str_append_(&destJson, http->cookies_start, http->cookies_end);	
	}

	mq_pop_mobile_str_append(&destJson, srcIP);
	int iSendNum = mq_pop_mobile_IP_Hash_append(&destJson, hdr->extended_hdr.parsed_pkt.ipv4_src);

	mq_pop_mobile_str_append(&destJson, dstIP);
	mq_pop_mobile_IP_append(&destJson, hdr->extended_hdr.parsed_pkt.ipv4_dst);

	mq_pop_mobile_str_append(&destJson, dstMac);
	mq_pop_mobile_MAC_append_(&destJson, hdr->extended_hdr.parsed_pkt.dmac);

	mq_pop_mobile_str_append(&destJson, srcMac);
	mq_pop_mobile_MAC_append_(&destJson, hdr->extended_hdr.parsed_pkt.smac);

	mq_pop_mobile_str_append(&destJson, siteUrl);
	mq_pop_mobile_str_append_(&destJson, http->host_start, http->host_end);

	if(http->uri_end - http->uri_start +1 > 2048)
	{
		return ;
	}
	mq_pop_mobile_str_append(&destJson, siteUri);
	mq_pop_mobile_str_append_(&destJson, http->uri_start, http->uri_end);

	char sectamp[64] = {0};
	sprintf(sectamp, "%u", http->sec);
		
	mq_pop_mobile_str_append(&destJson, pcSec);
	mq_pop_mobile_str_append(&destJson, sectamp);

	mq_pop_mobile_str_append(&destJson, pcUa);
	mq_pop_mobile_str_append_(&destJson, http->ua_start, http->ua_end);

	mq_pop_mobile_str_append(&destJson, end);
	*destJson = '\0';

	pthread_mutex_lock(&zmq_mobile_send_lock);
	s_send (g_pvMobileHashWinSender[iSendNum], jsonstr);
	pthread_mutex_unlock(&zmq_mobile_send_lock);
}
