#define _GNU_SOURCE
#include "dc_global.h"

pfring_dna_cluster *dna_cluster_handle = NULL;
extern int http_cap_filter_main(const  int thread_id, const u_char *buffer, const struct pfring_pkthdr* hdr);

/*
 * the function of capture packet
 */
void* http_cap_zero_thread_func(void* _id)
{
    int s;
    long thread_id = (long)_id;
    u_int numCPU = sysconf(_SC_NPROCESSORS_ONLN );
    u_long core_id = (thread_id+2) % numCPU;

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

        if (pfring_recv(g_rx_rings[thread_id], &buffer, 0, &hdr, 1) > 0)
        {
            if (g_is_shutdown) break;
            g_rx_pkt_num[thread_id]++;
            g_rx_pkt_byte[thread_id] += (unsigned long long) hdr.len;

            //if (hdr.ts.tv_sec == 0)
            //memset((void *) &hdr.extended_hdr.parsed_pkt, 0, sizeof(struct pkt_parsing_info));
            //pfring_parse_pkt((u_char *) buffer, &hdr, 5, 1, 0);

            struct pfring_pkthdr * hdr_parserd = *(long *) buffer;
            http_cap_filter_main(thread_id, buffer, hdr_parserd);
        }
    }
    return(NULL);
}
static char * pkt_capture_zero_mem_pool;
static int pkt_capture_zero_mem_pool_max_len = 1000000 * sizeof(struct pfring_pkthdr);
static int pkt_capture_zero_mem_pool_curr_offset;

void pkt_capture_zero_mem_init()
{
    pkt_capture_zero_mem_pool = malloc(pkt_capture_zero_mem_pool_max_len); //1K * 1000,000 = 10M
    pkt_capture_zero_mem_pool_curr_offset = 0;
}
char * pkt_capture_zero_mem_alloc()
{
    if(pkt_capture_zero_mem_pool_curr_offset > (pkt_capture_zero_mem_pool_max_len - sizeof(struct pfring_pkthdr)))
    {
        pkt_capture_zero_mem_pool_curr_offset = 0;
    }
    pkt_capture_zero_mem_pool_curr_offset += sizeof(struct pfring_pkthdr);
    return pkt_capture_zero_mem_pool+pkt_capture_zero_mem_pool_curr_offset-sizeof(struct pfring_pkthdr);
}
static int pkt_hash_function(const u_char *buffer, const u_int16_t buffer_len,
                             const pfring_dna_cluster_slaves_info *slaves_info,
                             u_int32_t *id_mask, u_int32_t *hash)
{
    struct pfring_pkthdr * hdr = (struct pfring_pkthdr *) pkt_capture_zero_mem_alloc();
    hdr->len = hdr->caplen = buffer_len;
    memset((void *) &hdr->extended_hdr.parsed_pkt, 0, sizeof(struct pkt_parsing_info));
    pfring_parse_pkt(buffer, hdr, 5, 1, 0);

    long sip = 0;
    if(hdr->extended_hdr.parsed_pkt.tunnel.tunnel_id != NO_TUNNEL_ID)
    {
        if(IPPROTO_TCP != hdr->extended_hdr.parsed_pkt.tunnel.tunneled_proto)
        {
            return DNA_CLUSTER_DROP;
        }

        if(hdr->extended_hdr.parsed_pkt.tunnel.tunneled_l4_dst_port != 80)
        {
            return DNA_CLUSTER_DROP;
        }

        *(long*)buffer = hdr;
        //printf("ip ===%d\n", hdr.extended_hdr.parsed_pkt.tunnel.tunneled_ip_src.v4);
        sip = hdr->extended_hdr.parsed_pkt.tunnel.tunneled_ip_src.v4;
    }
    else
    {
        if(IPPROTO_TCP != hdr->extended_hdr.parsed_pkt.l3_proto)
        {
            return DNA_CLUSTER_DROP;
        }

        if (hdr->extended_hdr.parsed_pkt.l4_dst_port != 80)
        {
            return DNA_CLUSTER_DROP;
        }
        *(long*)buffer = hdr;
        sip = hdr->extended_hdr.parsed_pkt.ipv4_src;
    }


    u_int32_t slave_idx = (sip) % slaves_info->num_slaves;
    *id_mask = (1 << slave_idx);

    return DNA_CLUSTER_PASS;
}

/*
 * start to capture the packets
 */
int http_cap_zero_start(const char * device)
{
    pkt_capture_zero_mem_init();

    /*create a cluster*/
    if ((dna_cluster_handle = dna_cluster_create(11, g_conf_capture_thread_num, DNA_CLUSTER_NO_ADDITIONAL_BUFFERS))
            == NULL)
    {
      fprintf(stderr, "Error creating DNA Cluster\n");
      return(-1);
    }

    /*set attr*/
    dna_cluster_low_level_settings(dna_cluster_handle,  8192, 8192,  0);

    if (dna_cluster_set_mode(dna_cluster_handle, recv_only_mode) < 0)
    {
      printf("dna_cluster_set_mode error\n");
      return(-1);
    }

    /*open device*/
    pfring * pf = pfring_open(device, 65535 /* snaplen */, PF_RING_PROMISC);
    if(pf == NULL)
    {
      printf("pfring_open %s error [%s]\n", device, strerror(errno));
      return(-1);
    }

    /*set name*/
    char buf[256];
    snprintf(buf, sizeof(buf), "pfdnacluster_multithread-cluster-%d-socket-%d", 11, 0);
    pfring_set_application_name(pf, buf);

    /*add device to cluster*/
    if (dna_cluster_register_ring(dna_cluster_handle, pf) < 0)
    {
      fprintf(stderr, "Error registering rx socket\n");
      dna_cluster_destroy(dna_cluster_handle);
      return -1;
    }

    /*set attr*/
    dna_cluster_set_wait_mode(dna_cluster_handle, 0);
    dna_cluster_set_cpu_affinity(dna_cluster_handle, 0, 1);
    dna_cluster_set_thread_name(dna_cluster_handle, "rx-thread", "tx-thread");

    /*set hash function*/
    dna_cluster_set_distribution_function(dna_cluster_handle, pkt_hash_function);

    /*enable*/
    if (dna_cluster_enable(dna_cluster_handle) < 0)
    {
      fprintf(stderr, "Error enabling the engine; dna NICs already in use?\n");
      dna_cluster_destroy(dna_cluster_handle);
      return -1;
    }

    /*init thread*/
    int i,rc;
    for (i = 0; i < g_conf_capture_thread_num; i++)
    {
      /*crate sub pfring*/
      snprintf(buf, sizeof(buf), "dnacluster:%d@%ld", 11, i);
      g_rx_rings[i] = pfring_open(buf, 65535, PF_RING_PROMISC);
      if (g_rx_rings[i] == NULL)
      {
        printf("pfring_open %s error [%s]\n", device, strerror(errno));
        return(-1);
      }

      /*set name*/
      snprintf(buf, sizeof(buf), "pfdnacluster_multithread-cluster-%d-thread-%ld", 11, i);
      pfring_set_application_name(g_rx_rings[i], buf);

      /*set attr*/
      if ((rc = pfring_set_socket_mode(g_rx_rings[i], recv_only_mode)) != 0)
        fprintf(stderr, "pfring_set_socket_mode returned [rc=%d]\n", rc);

//       if((rc = pfring_set_poll_watermark(g_rx_rings[i], g_watermark)) != 0)
//            printf("pfring_set_poll_watermark returned [ret=%d][watermark=%d]\n", rc, g_watermark);

      /*enable*/
      pfring_enable_ring(g_rx_rings[i]);

      /*create thread*/
      pthread_create(&g_cap_thread[i], NULL, http_cap_zero_thread_func, (void *) i);
    }
}







