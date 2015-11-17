#define _GNU_SOURCE

#include <pcap.h>
#include "dc_global.h"

extern int http_cap_filter_main(const  int thread_id, const u_char *buffer, const struct pfring_pkthdr* hdr);
/*
 * the function of capture packet
 */
void* http_cap_offline_thread_func(void* _id)
{
    char errbuf[256];
    pcap_t *handle = pcap_open_offline("./caoli.cap"  , errbuf);
    if (handle == NULL) {
        fprintf(stderr, "Couldn't open file %s\n", errbuf);
        exit(EXIT_FAILURE);
    }

    struct pcap_pkthdr *pktHeader;
    int status;
    u_char *pktData;
    do
    {
        struct pfring_pkthdr hdr;

        status = pcap_next_ex(handle, &pktHeader, (const u_char**)&pktData);
        hdr.caplen = pktHeader->caplen;
        hdr.len = pktHeader->caplen;

        if(status > 0)
        {
            memset((void *) &hdr.extended_hdr.parsed_pkt, 0, sizeof(struct pkt_parsing_info));
            pfring_parse_pkt((u_char *) pktData, &hdr, 5, 1, 1);

            http_cap_filter_main(0, pktData, &hdr);
        }
        else
        {
            printf("error %d\n", status);
            break;
        }
    }while(1);
    pcap_close(handle);
    printf("\nCapture complete.\n");
    return(NULL);
}

/*
 * start to capture the packets
 */
int http_cap_offline_start(const char * device)
{
    pthread_create(&g_cap_thread[0], NULL, http_cap_offline_thread_func, (void*)0);
    return 0;
}





