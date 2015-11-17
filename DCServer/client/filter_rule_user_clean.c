
#include "dc_global.h"
/*
 * user clean algorithm
 * -----------------------------------------------
 * a user:
 * click(some http get pkts)
 * click(some http get pkts)
 * ... ...
 * click(some http get pkts)
 * -----------------------------------------------
 * 1. first identify a user:
 * userid = ip + ua ===> need a aquick md5 algorithm.
 * 2. second clean:
 * we make 2 cache,
 * A: which cached last 10s http get pkts urls; ===> need a aquick md5 algorithm
 * B: which cached curr 10s http get pkts urls
 * when a get pkt is captured:
 * first compare the referer with A, if contain, then ignore it. if not add to B. ===> need a aquick hash find.
 * after 10s come, clear A, move B to A, clear B.
 * 3. multi channel
 * the intel 82599 network card multi channel banlance by IP hash, so we think same user in same channel.
 * then we did not use lock with different multi channel.
 */

extern void mq_file_thread_start();

struct IP_UA_URL_NODE
{
    int ip_ua_url_hashA;
    int ip_ua_url_hashB;
    char * json_str;
    u_int8_t send_flag;
    struct IP_UA_URL_NODE * next;
};

#define IP_UA_table_size       1000000  // IP+UA+URL 150wan/s * 10 s
#define IP_UA_NODE_POOL_SIZE   1500000  // IP+UA+URL 150wan/s * 10 s

struct IPUANODE  * A_ip_ua_url_table[MAX_NUM_THREADS][IP_UA_table_size];
struct IPUANODE  * B_ip_ua_url_table[MAX_NUM_THREADS][IP_UA_table_size];

int   A_ip_ua_url_table_size[MAX_NUM_THREADS];
int   B_ip_ua_url_table_size[MAX_NUM_THREADS];

char  * A_mem_pool[MAX_NUM_THREADS];
char  * B_mem_pool[MAX_NUM_THREADS];
int   g_mem_offset[MAX_NUM_THREADS];

char  * A_json_mem_pool[MAX_NUM_THREADS];
char  * B_json_mem_pool[MAX_NUM_THREADS];
int   g_json_mem_offset[MAX_NUM_THREADS];

unsigned long uc_ip_ua_url_crypt_table[0x1000];

extern void mq_file_send_msg(const char * msg, long tid);

//alloc mem from pool
void * user_clean_alloc_mem(int tid, int len)
{
    void * ret = (g_period_flag[tid]<0? A_mem_pool[tid] + g_mem_offset[tid] : B_mem_pool[tid] + g_mem_offset[tid]);
    g_mem_offset[tid] += len;
    if (g_mem_offset[tid]  > (sizeof(struct IP_UA_URL_NODE) * IP_UA_NODE_POOL_SIZE- 1000))
    {
        printf("mem poll is used out\n");
        g_mem_offset[tid] = 0;
    }
    return ret;
}

//alloc json mem from pool
void * user_clean_alloc_json_mem(int tid, int len)
{
    void * ret = (g_period_flag[tid]<0? A_json_mem_pool[tid] + g_json_mem_offset[tid] : B_json_mem_pool[tid] + g_json_mem_offset[tid]);
    g_json_mem_offset[tid] += len;
    if (g_json_mem_offset[tid]  > (10000 * IP_UA_NODE_POOL_SIZE- 3000))
    {
        printf("json mem poll is used out\n");
        g_json_mem_offset[tid] = 0;
    }
    return ret;
}

/*
 * init table of hash key
*/
void user_clean_init_ip_ua_url_crypt_table()
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
            uc_ip_ua_url_crypt_table[index2] = ( temp1|temp2 );
       }
   }
}

/*
 * host hash
*/
unsigned long user_clean_get_ip_ua_url_hash(u_int ip,
        u_char *host_start, u_char *host_end,
        u_char *ua_start, u_char *ua_end,
        u_char *uri_start, u_char *uri_end, unsigned long dwHashType)
{

    unsigned long seed1 = 0x7FED7FED;
    unsigned long seed2 = 0xEEEEEEEE;
    int ch;

    while (ip)
    {
        ch = ip % 10 + '0';


        seed1 = uc_ip_ua_url_crypt_table[(dwHashType << 8) + ch] ^ (seed1 + seed2);
        seed2 = ch + seed1 + seed2 + (seed2 << 5) + 3;
        ip = ip / 10;
    }
    DBG("\nua:");
    unsigned char *key  = (unsigned char *)(ua_start);
    int j = 0;
    while(( key <= ua_end ) && (j<15) )
    {
        j++;
        ch = *key++;
        DBG("%c", ch);
        seed1 = uc_ip_ua_url_crypt_table[(dwHashType << 8) + ch] ^ (seed1 + seed2);
        seed2 = ch + seed1 + seed2 + (seed2 << 5) + 3;
    }

    DBG("\nurl:");
    int z = 0;
    if((host_end > 10) && (host_start > 10))
    {
        char * prefix = "http://";
        while( z<7 )
        {
            ch = *(prefix+z);
            DBG("%c", ch);
            seed1 = uc_ip_ua_url_crypt_table[(dwHashType << 8) + ch] ^ (seed1 + seed2);
            seed2 = ch + seed1 + seed2 + (seed2 << 5) + 3;
            z++;
        }

        key  = (unsigned char *)(host_start);
        while(( key <= host_end ) && (z<200))
        {
            z++;
            ch = *key++;
            DBG("%c", ch);
            seed1 = uc_ip_ua_url_crypt_table[(dwHashType << 8) + ch] ^ (seed1 + seed2);
            seed2 = ch + seed1 + seed2 + (seed2 << 5) + 3;
        }
    }

    key  = (unsigned char *)(uri_start);

    while(( key <= uri_end ) && (z<200))
    {
        z++;
        ch = *key++;
        DBG("%c", ch);
        seed1 = uc_ip_ua_url_crypt_table[(dwHashType << 8) + ch] ^ (seed1 + seed2);
        seed2 = ch + seed1 + seed2 + (seed2 << 5) + 3;
    }
    DBG("\n", ch);
    return seed1;
}

extern void mq_storm_send_msg(const char * msg, long tid);
/*
 * check by hash A B, if not exist then insert
 */
int user_clean_check_append_ip_ua_url(int thread_id, u_int ip,
        u_char *host_start, u_char *host_end,
        u_char *ua_start, u_char *ua_end,
        u_char *uri_start, u_char *uri_end, char * json_str,
        int is_add)
{
    const int HASH_OFFSET = 0, HASH_A = 1, HASH_B = 2;
    unsigned int nHash  = user_clean_get_ip_ua_url_hash(ip, ua_start, ua_end, host_start, host_end,uri_start, uri_end, HASH_OFFSET);
    unsigned int nHashA = user_clean_get_ip_ua_url_hash(ip, ua_start, ua_end, host_start, host_end,uri_start, uri_end, HASH_A    );
    unsigned int nHashB = user_clean_get_ip_ua_url_hash(ip, ua_start, ua_end, host_start, host_end,uri_start, uri_end, HASH_B    );
    unsigned int nHashStart = nHash % IP_UA_table_size, nHashPos = nHashStart;

    //check curr
    if (A_ip_ua_url_table[thread_id][nHashPos])
    {
        struct IP_UA_URL_NODE * currNode = A_ip_ua_url_table[thread_id][nHashPos];
        while(currNode)
        {
            if (currNode->ip_ua_url_hashA  == nHashA && currNode->ip_ua_url_hashB == nHashB)
            {
                if(!is_add)
                {
                    if(currNode->send_flag == 0)
                    {
                        DBG("ok, find parent send it.insert = %d, json=%s \n", is_add, currNode->json_str);
                        g_rx_pkt_send_num[thread_id]++;
                        mq_file_send_msg(currNode->json_str, thread_id);
                        currNode->send_flag = 1;
                        return -2;
                    }
                    else
                    {
                        //the parent hase been send, and I may be other's parent? slow performance...should check the host is real url
                        return -3;
                    }
                }

                return nHashPos;
            }
            currNode = currNode->next;
        }
    }
    //check last
    else if (B_ip_ua_url_table[thread_id][nHashPos])
    {
        struct IP_UA_URL_NODE * currNode = B_ip_ua_url_table[thread_id][nHashPos];
        while(currNode)
        {
            if (currNode->ip_ua_url_hashA  == nHashA && currNode->ip_ua_url_hashB == nHashB)
            {
                if (!is_add)
                {
                    if (currNode->send_flag == 0)
                    {
                        DBG("ok, find parent send it.insert = %d, json=%s \n", is_add, currNode->json_str);
                        g_rx_pkt_send_num[thread_id]++;
                        mq_file_send_msg(currNode->json_str, thread_id);
                        currNode->send_flag = 1;
                        return -2;
                    }
                    else if (currNode->send_flag == 1)
                    {
                        //the parent hase been send, and I may be other's parent? slow performance... should check the host is real url
                        return -3;
                    }
                }
                return nHashPos;
            }
            currNode = currNode->next;
        }
    }

    if(!is_add) return -1;

    g_period_flag[thread_id] <0 ?
            (A_ip_ua_url_table_size[thread_id]++):  (B_ip_ua_url_table_size[thread_id]++);

    //insert to curr
    struct IP_UA_URL_NODE * nextNode = (struct IP_UA_URL_NODE*)user_clean_alloc_mem(thread_id, sizeof(struct IP_UA_URL_NODE));
    nextNode->ip_ua_url_hashA = nHashA;
    nextNode->ip_ua_url_hashB = nHashB;
    nextNode->json_str = json_str;
    nextNode->send_flag = 0;


    if (g_period_flag[thread_id] <0 ?
            A_ip_ua_url_table[thread_id][nHashPos] : B_ip_ua_url_table[thread_id][nHashPos])
    {
        nextNode->next = (g_period_flag[thread_id] <0 ?
                              A_ip_ua_url_table[thread_id][nHashPos] : B_ip_ua_url_table[thread_id][nHashPos]); //add host to first
        g_period_flag[thread_id] <0 ?( A_ip_ua_url_table[thread_id][nHashPos] = nextNode)
                :( B_ip_ua_url_table[thread_id][nHashPos] = nextNode);
    }
    else
    {
        nextNode->next = NULL;
        g_period_flag[thread_id] <0 ? (A_ip_ua_url_table[thread_id][nHashPos] = nextNode)
                : (B_ip_ua_url_table[thread_id][nHashPos] = nextNode);       //add a new host
    }

    //DEBUG("not find ip ua, insert", nHashPos);
    return nHashPos;
}

/*
 * a period come here
 */
int user_clean_time_out(int thread_id)
{
    //first 10s
    if(g_period_flag[thread_id] == -2)
    {
        printf("clear none, g_flag=%d, g_offset=%d, A_table_size=%d, B_table_size=%d\n", 
            g_period_flag[thread_id], g_mem_offset[thread_id],
            A_ip_ua_url_table_size[thread_id], B_ip_ua_url_table_size[thread_id]);

        g_period_flag[thread_id] = 1;
        g_mem_offset[thread_id] = 0 ;
        g_json_mem_offset[thread_id] = 0;
        B_ip_ua_url_table_size[thread_id] = 0;
        return 0;
    }
    //curr is A
    if(g_period_flag[thread_id] > 0)
    {
        printf("clear A, g_flag=%d, g_offset=%d, A_table_size=%d, B_table_size=%d\n", 
            g_period_flag[thread_id], g_mem_offset[thread_id],
            A_ip_ua_url_table_size[thread_id], B_ip_ua_url_table_size[thread_id]);

        memset(A_ip_ua_url_table[thread_id], 0, sizeof(void*) * IP_UA_table_size);
       // memset(A_mem_pool[threadid], 0, sizeof(struct IP_UA_URL_NODE) * 15000000);
        g_period_flag[thread_id] = -1;
        g_mem_offset[thread_id] = 0 ;
        g_json_mem_offset[thread_id] = 0;
        A_ip_ua_url_table_size[thread_id] = 0;
    }
    //curr is B
    else
    {
        printf("clear B, g_flag=%d, g_offset=%d, A_table_size=%d, B_table_size=%d\n", 
            g_period_flag[thread_id], g_mem_offset[thread_id],
            A_ip_ua_url_table_size[thread_id], B_ip_ua_url_table_size[thread_id]);

        memset(B_ip_ua_url_table[thread_id], 0, sizeof(void*) * IP_UA_table_size);
        //memset(B_mem_pool[threadid], 0, sizeof(struct IP_UA_URL_NODE) * 15000000);
        g_period_flag[thread_id] = 1;
        g_mem_offset[thread_id] = 0 ;
        g_json_mem_offset[thread_id] = 0;
        B_ip_ua_url_table_size[thread_id] = 0;
    }
    return 0;
}



//init mem pool
int user_clean_init_mem_pool()
{
    int i;
    int memsize = sizeof(struct IP_UA_URL_NODE) * IP_UA_NODE_POOL_SIZE;
    printf("init mem poll, size: %d\n", memsize);
    for (i = 0; i < g_conf_capture_thread_num; ++i)
    {
        A_mem_pool[i] = malloc(memsize); //150wan * 10 s
        B_mem_pool[i] = malloc(memsize); //150wan * 10 s
        g_mem_offset[i] = 0;
    }

    int mem_json_size = 10000 * IP_UA_NODE_POOL_SIZE;
    printf("init json mem poll, size: %d\n", memsize);
    for (i = 0; i < g_conf_capture_thread_num; ++i)
    {
        A_json_mem_pool[i] = malloc(mem_json_size); //150wan * 10 s
        B_json_mem_pool[i] = malloc(mem_json_size); //150wan * 10 s
        g_json_mem_offset[i] = 0;
    }

    printf("init hash table\n");
    for (i = 0; i < g_conf_capture_thread_num; ++i)
    {
         memset(A_ip_ua_url_table[i], 0, sizeof(void*) * IP_UA_table_size);
         memset(B_ip_ua_url_table[i], 0, sizeof(void*) * IP_UA_table_size);
         A_ip_ua_url_table_size[i] = 0;
         B_ip_ua_url_table_size[i] = 0;
    }
    printf("init period flag\n");
    for (i = 0; i < g_conf_capture_thread_num; ++i)
    {
         g_period_flag[i] = -2;
         g_period_last_time[i] = 0;
    }
    return 0;
}

/*
 * init hash table
 */
int user_clean_init()
{
    printf("begin to init user clean \n");
    mq_file_thread_start();
    user_clean_init_ip_ua_url_crypt_table();
    user_clean_init_mem_pool();
    return 0;
}

int user_clean_check_parent(long second, int thread_id,
        u_int ip,
        u_char *ua_start, u_char *ua_end,
        u_char *host_start, u_char *host_end,
        u_char *uri_start, u_char *uri_end,
        char * json_str)
{
    return user_clean_check_append_ip_ua_url(thread_id, ip, ua_start, ua_end, host_start,
            host_end,uri_start, uri_end, json_str, 0);
}

int user_clean_append_queue(long second, int thread_id,
        u_int ip,
        u_char *ua_start, u_char *ua_end,
        u_char *host_start, u_char *host_end,
        u_char *uri_start, u_char *uri_end,
        char * json_str)
{

    return user_clean_check_append_ip_ua_url(thread_id, ip, ua_start, ua_end, host_start,host_end,
            uri_start, uri_end, json_str, 1);
}

int user_clean_main(const  int thread_id, const struct http_request_kinfo * http, const char * json_str)
{
    if ((!http->ua_end) || (!http->ua_start) || (!http->host_end) || (!http->host_end)
            || (!http->uri_start) || (!http->uri_start))
    {
        DBG("discard json:%s\n", json_str);
        return 0;
    }
    //no Referer, direct add into hash table
    else if ((!http->referer_end) || (!http->referer_start))
    {
        user_clean_append_queue((u_int) http->sec, thread_id, http->sip, http->ua_start,
                http->ua_end, http->host_start, http->host_end, http->uri_start, http->uri_end, json_str);
        DBG("add json:%s\n", json_str);
    }
    //has Referer, check parent first and add into hash table
    else if ((http->referer_end) && (http->referer_start))
    {
        user_clean_check_parent((u_int) http->sec, thread_id,
                http->sip, http->ua_start, http->ua_end, 0, 0, http->referer_start, http->referer_end, json_str);
        user_clean_append_queue((u_int) http->sec, thread_id, http->sip, http->ua_start,
                http->ua_end, http->host_start, http->host_end, http->uri_start, http->uri_end, json_str);
        DBG("add json:%s\n", json_str);
    }
    else
    {
        DBG("discard json:%s\n", json_str);
    }
    return 0;
}

int uc_main_test()
{
    user_clean_init();
    char * ua = "Mozilla/5.0 (Macintos";
    char * referer = "http://tongji.cnzz.com/main.php?c=site&a=show&siteid=1000368406";
    char * uri = "tongji.cnzz.com/main.php?c=site&a=show&siteid=1000368406";
    return 0;
}

