#include <pcap.h>
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <time.h>
#include <string.h>


struct eth_header_sender
{
    unsigned char   h_dest[6];
    unsigned char   h_source[6];
    u_int16_t       h_proto;
};

struct ip_header_sender
{
#if BYTE_ORDER == LITTLE_ENDIAN
    u_int32_t	ihl:4,		/* header length */
            version:4;			/* version */
#else
  u_int32_t	version:4,			/* version */
    ihl:4;		/* header length */
#endif
    u_int8_t	tos;			/* type of service */
    u_int16_t	tot_len;			/* total length */
    u_int16_t	id;			/* identification */
    u_int16_t	frag_off;			/* fragment offset field */
    u_int8_t	ttl;			/* time to live */
    u_int8_t	protocol;			/* protocol */
    u_int16_t	check;			/* checksum */
    u_int32_t saddr, daddr;	/* source and dest address */
};

struct tcp_header_sender
{
    u_short th_sport; /* source port */
    u_short th_dport; /* destination port */
    u_int th_seq; /* sequence number */
    u_int th_ack; /* acknowledgement number */
    u_char th_offx2; /* data offset, rsvd */
    u_char th_flags;
    u_short th_win; /* window */
    u_short th_sum; /* checksum */
    u_short th_urp; /* urgent pointer */
};

struct pseudo_head {
    uint32_t saddr;
    uint32_t daddr;
    char zero;
    char proto;
    unsigned short len;
};
/*
 * Checksum routine for Internet Protocol family headers (C Version)
 *
 * Borrowed from DHCPd
 */


/* ******************************************* */


static u_int32_t in_cksum2(unsigned char *buf,
        unsigned nbytes, u_int32_t sum) {
    uint i;

    /* Checksum all the pairs of bytes first... */
    for (i = 0; i < (nbytes & ~1U); i += 2) {
        sum += (u_int16_t) ntohs(*((u_int16_t *)(buf + i)));
        /* Add carry. */
        if(sum > 0xFFFF)
            sum -= 0xFFFF;
    }

    /* If there's a single byte left over, checksum it, too.   Network
       byte order is big-endian, so the remaining byte is the high byte. */
    if(i < nbytes) {
#ifdef DEBUG_CHECKSUM_VERBOSE
    debug ("sum = %x", sum);
#endif
        sum += buf [i] << 8;
        /* Add carry. */
        if(sum > 0xFFFF)
            sum -= 0xFFFF;
    }

    return sum;
}
static int cal_sum2(unsigned short *p, int len)
{
    int sum = 0;
    while(len > 0) {
        sum += *p++;
        len -= 2;
    }
    return sum;
}

static u_int32_t wrapsum (u_int32_t sum) {
    sum = ~sum & 0xFFFF;
    return htons(sum);
}

static unsigned short in_cksum(unsigned short *p,
        int len, int sum)
{
    int n = len;

    while(n > 1) {
        sum += *p++;
        n -= 2;
    }
    if(n == 1)
        sum += *(unsigned char *)p;

    sum = (sum >> 16) + (sum & 0xffff);
    sum += (sum >> 16);

    unsigned short ret = ~sum;
    return ret;
}

inline static int cal_sum(unsigned short *p, int len)
{
    int sum = 0;
    while(len > 0) {
        sum += *p++;
        len -= 2;
    }
    return sum;
}

static unsigned short cal_tcp_cksum(
        unsigned short *tcp_header, struct ip_header_sender *ip_header, int ip_total_len)
{
    struct pseudo_head ph;
    int tcp_len = ip_total_len - sizeof(struct ip_header_sender);

    ph.len = htons(tcp_len);
    ph.saddr = ip_header->saddr;
    ph.daddr = ip_header->daddr;
    ph.proto = ip_header->protocol;
    ph.zero = 0;

    int sum = cal_sum(
            (unsigned short *)&ph, sizeof(ph));

    return in_cksum(tcp_header, tcp_len, sum);
}
/* ******************************************* */

static int forge_tcp_packet(u_char *pkt_to_send, u_int pkt_len,
        char dmac[6], char smac[6],
        u_int32_t src_ip, u_int32_t dst_ip,
        u_int16_t src_port, u_int16_t dst_port,
        u_int32_t seq, u_int32_t ack,
        char * content,
        int content_len)
{
    /* Reset packet */
    memset(pkt_to_send, 0, pkt_len);

    /*eth*/
    int i;
    for(i=0; i<6; i++) pkt_to_send[i] = dmac[i];
    for(i=0; i<6; i++) pkt_to_send[6+i] = smac[i]; 
    pkt_to_send[12] = 0x08, pkt_to_send[13] = 0x00; /* IP */

    struct ip_header_sender * ip_header = (struct ip_header_sender*) &pkt_to_send[sizeof(struct eth_header_sender)];
    ip_header->ihl = 5;
    ip_header->version = 4;
    ip_header->tos = 0;

    int total_len = content_len+sizeof(struct tcp_header_sender)+sizeof(struct ip_header_sender);

    ip_header->tot_len = htons(total_len);
    ip_header->id = htons(2012);
    ip_header->ttl = 249;
    ip_header->frag_off = htons(0x4000);
    ip_header->protocol = IPPROTO_TCP;
    ip_header->daddr = htonl(dst_ip);
    ip_header->saddr = htonl(src_ip);
    ip_header->check = wrapsum(in_cksum2((unsigned char *)ip_header, sizeof(struct ip_header_sender), 0));

    struct tcp_header_sender * tcp_header = (struct tcp_header_sender*) &pkt_to_send[sizeof(struct eth_header_sender) + sizeof(struct ip_header_sender)];
    tcp_header->th_sport = htons(src_port);
    tcp_header->th_dport = htons(dst_port);
    tcp_header->th_seq = htonl(seq);
    tcp_header->th_ack = htonl(ack);
    tcp_header->th_win = htons(0x3fe4);
    tcp_header->th_offx2 = 0x50;
    tcp_header->th_flags = 0x19;
    tcp_header->th_sum = 0; /* It must be 0 to compute the checksum */

    char * content_p = (char *) &pkt_to_send[sizeof(struct eth_header_sender) + sizeof(struct ip_header_sender)+sizeof(struct tcp_header_sender)];
    memcpy(content_p, content, content_len);

    tcp_header->th_sum = cal_tcp_cksum(tcp_header, ip_header, total_len);
    return total_len+sizeof(struct eth_header_sender);
}

static  pcap_t* handle;
extern char * g_conf_send_device;

void pkt_sender_init()
{
    char ebuf[256];
    handle = pcap_open_live(g_conf_send_device, 65535, 0, 0, ebuf);
    if (handle == NULL)
    {
        fprintf(stderr, "Couldn't open file %s\n", ebuf);
        return ;
    }
    printf("pkt sender init success\n");
}

extern void http_parser_print_payload(const u_char *payload, int len);
void pkt_sender_send(char * redirect_url, int total_len,
        char src_mac[6], char dst_mac[6],
        u_int32_t src_ip, u_int32_t dst_ip,
        u_int16_t src_port, u_int16_t dst_port,
        u_int32_t seq, u_int32_t ack)
{
    u_char *pkt_to_send = (u_char *)malloc(2048);

    char content[2048];
    char * format = "HTTP/1.1 302 Moved Temporarily\r\nLocation: %s\r\nConnection: close\r\nContent-Type:text/html\r\nContent-length:0\r\n\r\n";

    sprintf(content, format, redirect_url);

    int pkt_send_len = forge_tcp_packet(pkt_to_send, 2048, dst_mac, src_mac,
            dst_ip, src_ip, dst_port, src_port,
            ack, seq+total_len,
            content,
            strlen(content));

    http_parser_print_payload(pkt_to_send, pkt_send_len);
    int rc = pcap_sendpacket(handle, pkt_to_send , pkt_send_len) ;

    if(rc != 0)
    {
        printf("Attempting to send invalid packet \n");
        return;
    }
}