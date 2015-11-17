#define _GNU_SOURCE
#include "dc_global.h"

extern int http_cap_filter_main(const  int thread_id, const u_char *buffer, const struct pfring_pkthdr* hdr);
/*
 * the function of capture packet
 */
void* http_cap_dna_thread_func(void* _id)
{
    int s;
    long thread_id = (long)_id;
    u_int numCPU = sysconf(_SC_NPROCESSORS_ONLN );
    u_long core_id = thread_id % numCPU;

    if(numCPU > 1)
    {
        cpu_set_t cpuset;
        CPU_ZERO(&cpuset);
        CPU_SET(core_id, &cpuset);
        if((s = pthread_setaffinity_np(pthread_self(), sizeof(cpu_set_t), &cpuset)) != 0)
        {
            printf("Error while binding thread %ld to core %ld: errno=%i\n", thread_id, core_id, s);
        }
        else
        {
            printf("Set thread %lu on core %lu/%u\n", thread_id, core_id, numCPU);
        }
    }

    while(1)
    {
        u_char *buffer = NULL;
        struct pfring_pkthdr hdr;
        if(g_is_shutdown) break;
        if (pfring_recv(g_rx_rings[thread_id], &buffer, 0, &hdr, 1) > 0) {
            if (g_is_shutdown) break;
            g_rx_pkt_num[thread_id]++;
            g_rx_pkt_byte[thread_id] += (unsigned long long) hdr.len;

            //if(hdr.ts.tv_sec == 0)
            {
                memset((void *) &hdr.extended_hdr.parsed_pkt, 0, sizeof(struct pkt_parsing_info));
                pfring_parse_pkt((u_char *) buffer, &hdr, 5, 1, 1);
            }

            http_cap_filter_main(thread_id, buffer, &hdr);
        }
    }
    return(NULL);
}

/*
 * start to capture the packets
 */
int http_cap_dna_start(const char * device)
{
    int ret;
    /*open the pring*/
    g_conf_capture_thread_num = pfring_open_multichannel(device, 65535, PF_RING_PROMISC, g_rx_rings);
    if(g_conf_capture_thread_num <= 0)
    {
        printf("pfring_open_multichannel() returned %d\n", g_conf_capture_thread_num);
        return(-1);
    }

    /*print pfring version*/
    u_int32_t version;
    pfring_version(g_rx_rings[0], &version);
    printf("Using PF_RING v.%d.%d.%d\n",(version & 0xFFFF0000) >> 16,(version & 0x0000FF00) >> 8,version & 0x000000FF);

    int i;
    for(i=0; i<g_conf_capture_thread_num; i++)
    {
        char buf[32];

        /*set pfring name*/
        snprintf(buf, sizeof(buf), "pfcount_multichannel-thread %d", i);
        pfring_set_application_name(g_rx_rings[i], buf);

		printf("http_cap_dna_start %d\n", i);

        /*set only capture rx*/
        if((ret = pfring_set_direction(g_rx_rings[i], rx_only_direction)) != 0)
            printf("pfring_set_direction returned [ret=%d][direction=%d]\n", ret, g_conf_direction);

        /*set water makr*/
        
        if((ret = pfring_set_poll_watermark(g_rx_rings[i], 1)) != 0)
            printf("pfring_set_poll_watermark returned [ret=%d][watermark=%d]\n", ret, g_conf_watermark);

        /*set capture poll time*/
        /*if(g_poll_duration > 0)
            pfring_set_poll_duration(g_rx_rings[i], g_poll_duration);*/

        /*set rehash*/
        //pfring_enable_rss_rehash(g_rx_rings[i]);
        /*ixgbe_set_rss_type(g_rx_rings[i],1);*/

        /*set begin to capture*/
        pfring_enable_ring(g_rx_rings[i]);
        pthread_create(&g_cap_thread[i], NULL, http_cap_dna_thread_func, (void*)i);
    }
    return 0;
}





