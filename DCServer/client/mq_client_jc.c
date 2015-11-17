#include "dc_global.h"
#include "zhelpers.h"
#include <sys/timeb.h>

#define JC_SENDER_LEN 8

static void * g_jc_mq_direct_client[MAX_NUM_THREADS]   = { NULL };
extern char** common_get_range_addr(const char * addr_begin, int num);
static void* g_pvWJCSender[JC_SENDER_LEN] = { NULL };

void mq_jc_win_client_init(const char * gateway_begin, int num)
{
    char** gateway_addrs = common_get_range_addr(gateway_begin, num);

    int i =0;
    for(; i < g_conf_capture_thread_num; i++, gateway_addrs++)
    {
        void * ctx = zctx_new ();
        g_jc_mq_direct_client[i] = zsocket_new (ctx, ZMQ_PUSH);
        zsocket_connect(g_jc_mq_direct_client[i], *gateway_addrs);
        printf("jc mq connect to %s\n", *gateway_addrs);
    }
    return ;
}


/************************************************************************/
//  [8/6/2014 fushengli]                                               
//  ³õÊ¼»¯zmq context                                                                   
/************************************************************************/
void mq_jc_win_sender_init()
{
	int i = 0;
	int j = 0;
	for (; i < JC_SENDER_LEN; i++)
	{
		void *pvCtx = zmq_ctx_new();
		g_pvWJCSender[i] = zmq_socket (pvCtx, ZMQ_PUSH);
		char cNames[64] = {'\0'};
		sprintf(cNames, "%s%d" , "ipc://jcsender",  i);
		zsocket_bind(g_pvWJCSender[i], cNames);
		printf("mq_jc_win_sender_init ipc name=%s\n", cNames);
	}

	return;
}


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

static void mq_pop_win_IP_append(char ** dst, u_int32_t ip)
{
    struct in_addr in;
    in.s_addr = htonl(ip);
    mq_pop_win_str_append(dst, inet_ntoa(in));
}


//    private long sequence;
//    private int ipLength;
//    private int tcpOptionLength;
//    private int srcPort;
//    private int dstPort;
//    private InetAddress srcIP;
//    private InetAddress dstIP;
//    private byte[] dstMac;
//    private byte[] srcMac;
//    private String siteUrl;

void jc_mq_direct_send_msg_tunneled(long tid, u_char *buffer, struct pfring_pkthdr * hdr, char * host_start, char * host_end, char * uri_start, char * uri_end)
{
   // http_parser_print_payload(buffer, 500);

    char jsonstr[1024*5] = {'\0'};
    char * destJson = jsonstr;

    char * sequence = "{\"sequence\":";
    char * ack = ",\"ackNumber\":";
    char * ipLength = ",\"ipLength\":";
    char * tcpOptionLength = ",\"tcpOptionLength\":";
    char * srcPort = ",\"srcPort\":";
    char * dstPort = ",\"dstPort\":";
    char * srcIP = ",\"srcIP\":";
    char * dstIP = ",\"dstIP\":";
    char * dstMac = ",\"dstMac\":\"";
    char * srcMac = "\",\"srcMac\":\"";
    char * siteUrl = "\",\"siteUrl\":\"";
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

    mq_pop_win_str_append(&destJson, srcIP);
    mq_pop_win_IP_append(&destJson, hdr->extended_hdr.parsed_pkt.tunnel.tunneled_ip_src.v4);

    mq_pop_win_str_append(&destJson, dstIP);
    mq_pop_win_IP_append(&destJson, hdr->extended_hdr.parsed_pkt.tunnel.tunneled_ip_dst.v4);

    mq_pop_win_str_append(&destJson, dstMac);
    mq_pop_win_MAC_append_(&destJson, hdr->extended_hdr.parsed_pkt.dmac);

    mq_pop_win_str_append(&destJson, srcMac);
    mq_pop_win_MAC_append_(&destJson, hdr->extended_hdr.parsed_pkt.smac);

    mq_pop_win_str_append(&destJson, siteUrl);
    mq_pop_win_str_append_(&destJson, host_start,host_end);
    mq_pop_win_str_append_(&destJson, uri_start,uri_end);

    mq_pop_win_str_append(&destJson, end);
	s_send (g_pvWJCSender[tid], jsonstr);
    //zframe_t *frame = zframe_new (jsonstr, strlen(jsonstr));
    //zframe_send (&frame, g_jc_mq_direct_client[tid], 0);
    //LOGT("jc send data: %s\n", jsonstr);
    //fflush(stdout);
}


void jc_mq_direct_send_msg(long tid, u_char *buffer, struct pfring_pkthdr * hdr, char * host_start, char * host_end, char * uri_start, char * uri_end)
{
   // http_parser_print_payload(buffer,hdr->len+10);

    char jsonstr[1024*5] = {'\0'};
    char * destJson = jsonstr;

    char * sequence = "{\"sequence\":";
    char * ack = ",\"ackNumber\":";
    char * ipLength = ",\"ipLength\":";
    char * tcpOptionLength = ",\"tcpOptionLength\":";
    char * srcPort = ",\"srcPort\":";
    char * dstPort = ",\"dstPort\":";
    char * srcIP = ",\"srcIP\":";
    char * dstIP = ",\"dstIP\":";
    char * dstMac = ",\"dstMac\":\"";
    char * srcMac = "\",\"srcMac\":\"";
    char * siteUrl = "\",\"siteUrl\":\"";
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

    mq_pop_win_str_append(&destJson, srcIP);
    mq_pop_win_IP_append(&destJson, hdr->extended_hdr.parsed_pkt.ipv4_src);

    mq_pop_win_str_append(&destJson, dstIP);
    mq_pop_win_IP_append(&destJson, hdr->extended_hdr.parsed_pkt.ipv4_dst);

    mq_pop_win_str_append(&destJson, dstMac);
    mq_pop_win_MAC_append_(&destJson, hdr->extended_hdr.parsed_pkt.dmac);

    mq_pop_win_str_append(&destJson, srcMac);
    mq_pop_win_MAC_append_(&destJson, hdr->extended_hdr.parsed_pkt.smac);

    mq_pop_win_str_append(&destJson, siteUrl);
    mq_pop_win_str_append_(&destJson, host_start,host_end);
    mq_pop_win_str_append_(&destJson, uri_start,uri_end);

    mq_pop_win_str_append(&destJson, end);

	s_send (g_pvWJCSender[tid], jsonstr);
    //zframe_t *frame = zframe_new (jsonstr, strlen(jsonstr));
    //zframe_send (&frame, g_jc_mq_direct_client[tid], 0);
    //LOGT("jc send data: %s\n", jsonstr);
    //fflush(stdout);
}