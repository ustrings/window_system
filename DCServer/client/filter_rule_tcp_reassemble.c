
#include "dc_global.h"

struct POST_DATA_SEG_NODE
{
    int post_data_seg_hashA;
    int post_data_seg_hashB;
    char * json_str;
    struct POST_DATA_SEG_NODE * next;
};

#define POST_SEG_table_size    1000 // IP+UA+URL 150wan/s * 10 s
#define POST_SEG_NODE_POOL_SIZE   1000  // IP+UA+URL 150wan/s * 10 s

struct POST_DATA_SEG_NODE  * A_post_data_seg_table[MAX_NUM_THREADS][POST_SEG_table_size];
struct POST_DATA_SEG_NODE  * B_post_data_seg_table[MAX_NUM_THREADS][POST_SEG_table_size];

int   A_post_data_seg_table_size[MAX_NUM_THREADS];
int   B_post_data_seg_table_size[MAX_NUM_THREADS];

char  * A_post_data_mem_pool[MAX_NUM_THREADS];
char  * B_post_data_mem_pool[MAX_NUM_THREADS];
int   g_post_data_mem_offset[MAX_NUM_THREADS];


unsigned long post_data_url_crypt_table[0x1000];

int  g_post_data_period_flag     [MAX_NUM_THREADS];
long g_post_data_period_last_time[MAX_NUM_THREADS];

//alloc mem from pool
void * tcp_reassemble_alloc_mem(int tid, int len)
{
    void * ret = (g_post_data_period_flag[tid]<0? A_post_data_mem_pool[tid] + g_post_data_mem_offset[tid] :
            A_post_data_mem_pool[tid] + g_post_data_mem_offset[tid]);
    g_post_data_mem_offset[tid] += len;
    if (g_post_data_mem_offset[tid]  > (sizeof(struct POST_DATA_SEG_NODE) * POST_SEG_NODE_POOL_SIZE- 1000))
    {
        printf("mem poll is used out\n");
        g_post_data_mem_offset[tid] = 0;
    }
    return ret;
}

/*
 * init table of hash key
*/
void tcp_reassemble_init_ip_ua_url_crypt_table()
{
    unsigned long seed = 0x00100001, index1 = 0, index2 = 0, i;
    for( index1 = 0; index1 < 0x100; index1++ )
    {
        for( index2 = index1, i = 0; i < 5; i++, index2 += 0x100 )
        {
            unsigned long temp1, temp2;
            seed = (seed * 125 + 3) % 0x2AAAAB;
            temp1 = (seed & 0xFFFF) << 0x10;
            seed = (seed * 125 + 3) % 0x2AAAAB;
            temp2 = (seed & 0xFFFF);
            post_data_url_crypt_table[index2] = ( temp1|temp2 );
        }
    }
}

/*
 * host hash
*/
unsigned long tcp_reassemble_get_ip_ua_url_hash(u_int src_ip, u_int dst_ip,
        u_int src_port,u_int dst_port, unsigned long dwHashType)
{

    unsigned long seed1 = 0x7FED7FED;
    unsigned long seed2 = 0xEEEEEEEE;
    int ch;

    while (src_ip)
    {
        ch = src_ip % 10 + '0';
        seed1 = post_data_url_crypt_table[(dwHashType << 8) + ch] ^ (seed1 + seed2);
        seed2 = ch + seed1 + seed2 + (seed2 << 5) + 3;
        src_ip = src_ip / 10;
    }
    while (dst_ip)
    {
        ch = dst_ip % 10 + '0';
        seed1 = post_data_url_crypt_table[(dwHashType << 8) + ch] ^ (seed1 + seed2);
        seed2 = ch + seed1 + seed2 + (seed2 << 5) + 3;
        dst_ip = dst_ip / 10;
    }
    while (src_port)
    {
        ch = src_port % 10 + '0';
        seed1 = post_data_url_crypt_table[(dwHashType << 8) + ch] ^ (seed1 + seed2);
        seed2 = ch + seed1 + seed2 + (seed2 << 5) + 3;
        src_port = src_port / 10;
    }
    while (dst_port)
    {
        ch = dst_port % 10 + '0';
        seed1 = post_data_url_crypt_table[(dwHashType << 8) + ch] ^ (seed1 + seed2);
        seed2 = ch + seed1 + seed2 + (seed2 << 5) + 3;
        dst_port = dst_port / 10;
    }
    return seed1;
}

/*
 * check by hash A B, if not exist then insert
 */
int tcp_reassemble_check_append_ip_ua_url(int thread_id, u_int src_ip, u_int dst_ip,
        u_int src_port,u_int dst_port, char * json_str,
        int is_add)
{
    const int HASH_OFFSET = 0, HASH_A = 1, HASH_B = 2;
    unsigned int nHash  = tcp_reassemble_get_ip_ua_url_hash(src_ip, dst_ip, src_port, dst_port, HASH_OFFSET);
    unsigned int nHashA = tcp_reassemble_get_ip_ua_url_hash(src_ip, dst_ip, src_port, dst_port, HASH_A    );
    unsigned int nHashB = tcp_reassemble_get_ip_ua_url_hash(src_ip, dst_ip, src_port, dst_port, HASH_B    );
    unsigned int nHashStart = nHash % POST_SEG_table_size, nHashPos = nHashStart;

    //check curr
    if (A_post_data_seg_table[thread_id][nHashPos])
    {
        struct POST_DATA_SEG_NODE * currNode = A_post_data_seg_table[thread_id][nHashPos];
        while(currNode)
        {
            if (currNode->post_data_seg_hashA  == nHashA && currNode->post_data_seg_hashB == nHashB)
            {

                return nHashPos;
            }
            currNode = currNode->next;
        }
    }
            //check last
    else if (B_post_data_seg_table[thread_id][nHashPos])
    {
        struct POST_DATA_SEG_NODE * currNode = B_post_data_seg_table[thread_id][nHashPos];
        while(currNode)
        {
            if (currNode->post_data_seg_hashA  == nHashA && currNode->post_data_seg_hashB == nHashB)
            {
                return nHashPos;
            }
            currNode = currNode->next;
        }
    }

    if(!is_add) return -1;

    g_post_data_period_flag[thread_id] <0 ?
            (A_post_data_seg_table_size[thread_id]++):  (B_post_data_seg_table_size[thread_id]++);

    //insert to curr
    struct POST_DATA_SEG_NODE * nextNode = (struct POST_DATA_SEG_NODE*)tcp_reassemble_alloc_mem(thread_id, sizeof(struct POST_DATA_SEG_NODE));
    nextNode->post_data_seg_hashA = nHashA;
    nextNode->post_data_seg_hashB = nHashB;
    nextNode->json_str = json_str;


    if (g_post_data_period_flag[thread_id] <0 ?
            A_post_data_seg_table[thread_id][nHashPos] : B_post_data_seg_table[thread_id][nHashPos])
    {
        nextNode->next = (g_post_data_period_flag[thread_id] <0 ?
                A_post_data_seg_table[thread_id][nHashPos] : B_post_data_seg_table[thread_id][nHashPos]); //add host to first
        g_post_data_period_flag[thread_id] <0 ?( A_post_data_seg_table[thread_id][nHashPos] = nextNode)
                :( B_post_data_seg_table[thread_id][nHashPos] = nextNode);
    }
    else
    {
        nextNode->next = NULL;
        g_post_data_period_flag[thread_id] <0 ? (A_post_data_seg_table[thread_id][nHashPos] = nextNode)
                : (B_post_data_seg_table[thread_id][nHashPos] = nextNode);       //add a new host
    }

    //DEBUG("not find ip ua, insert", nHashPos);
    return nHashPos;
}

/*
 * a period come here
 */
int tcp_reassemble_period_time_out(int thread_id)
{
    //first 10s
    if(g_post_data_period_flag[thread_id] == -2)
    {
        printf("clear none, g_flag=%d, g_offset=%d, A_table_size=%d, B_table_size=%d\n",
                g_post_data_period_flag[thread_id], g_post_data_mem_offset[thread_id],
                A_post_data_seg_table_size[thread_id], A_post_data_seg_table_size[thread_id]);

        g_post_data_period_flag[thread_id] = 1;
        g_post_data_mem_offset[thread_id] = 0 ;
        B_post_data_seg_table_size[thread_id] = 0;
        return 0;
    }
    //curr is A
    if(g_post_data_period_flag[thread_id] > 0)
    {
        printf("clear A, g_flag=%d, g_offset=%d, A_table_size=%d, B_table_size=%d\n",
                g_post_data_period_flag[thread_id], g_post_data_mem_offset[thread_id],
                A_post_data_seg_table_size[thread_id], B_post_data_seg_table_size[thread_id]);

        memset(A_post_data_seg_table[thread_id], 0, sizeof(void*) * POST_SEG_table_size);
        // memset(A_mem_pool[threadid], 0, sizeof(struct IP_UA_URL_NODE) * 15000000);
        g_post_data_period_flag[thread_id] = -1;
        g_post_data_mem_offset[thread_id] = 0 ;
        A_post_data_seg_table_size[thread_id] = 0;
    }
            //curr is B
    else
    {
        printf("clear B, g_flag=%d, g_offset=%d, A_table_size=%d, B_table_size=%d\n",
                g_post_data_period_flag[thread_id], g_post_data_mem_offset[thread_id],
                A_post_data_seg_table_size[thread_id], B_post_data_seg_table_size[thread_id]);

        memset(B_post_data_seg_table[thread_id], 0, sizeof(void*) * POST_SEG_table_size);
        //memset(B_mem_pool[threadid], 0, sizeof(struct IP_UA_URL_NODE) * 15000000);
        g_post_data_period_flag[thread_id] = 1;
        g_post_data_mem_offset[thread_id] = 0 ;
        B_post_data_seg_table_size[thread_id] = 0;
    }
    return 0;
}




//init mem pool
int tcp_reassemble_init_mem_pool()
{
    int i;
    int memsize = sizeof(struct POST_DATA_SEG_NODE) * POST_SEG_table_size;
    printf("init mem poll, size: %d\n", memsize);
    for (i = 0; i < g_conf_capture_thread_num; ++i)
    {
        A_post_data_mem_pool[i] = malloc(memsize); //150wan * 10 s
        B_post_data_mem_pool[i] = malloc(memsize); //150wan * 10 s
        g_post_data_mem_offset[i] = 0;
    }

    printf("init hash table\n");
    for (i = 0; i < g_conf_capture_thread_num; ++i)
    {
        memset(A_post_data_seg_table[i], 0, sizeof(void*) * POST_SEG_table_size);
        memset(B_post_data_seg_table[i], 0, sizeof(void*) * POST_SEG_table_size);
        A_post_data_seg_table_size[i] = 0;
        B_post_data_seg_table_size[i] = 0;
    }
    printf("init period flag\n");
    for (i = 0; i < g_conf_capture_thread_num; ++i)
    {
        g_post_data_period_flag[i] = -2;
        g_post_data_period_last_time[i] = 0;
    }
    return 0;
}

/*
 * init hash table
 */
int tcp_reassemble_init()
{
    printf("begin to init user clean \n");
    tcp_reassemble_init_ip_ua_url_crypt_table();
    tcp_reassemble_init_mem_pool();
    return 0;
}

int tcp_reassemble_check(const  int thread_id, const u_char *buffer, const struct pfring_pkthdr* hdr,
        const struct http_request_kinfo * http, const char * json_str)
{
    printf("a new post head not ready, %s\n",json_str);


    tcp_reassemble_check_append_ip_ua_url(thread_id, hdr->extended_hdr.parsed_pkt.ipv4_src,
            hdr->extended_hdr.parsed_pkt.ipv4_dst,
            hdr->extended_hdr.parsed_pkt.l4_src_port,
            hdr->extended_hdr.parsed_pkt.l4_dst_port,json_str,1);
    return 0;
}
