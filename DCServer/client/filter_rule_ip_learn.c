#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>

#include "dc_global.h"

extern int tool_hash_table_find(struct TOOL_HASH_NODE ** tool_hash_table, char *start, int len, long * out_val);
extern int tool_hash_table_insert(struct TOOL_HASH_NODE ** tool_hash_table, char *start, int len, long * in_val);
extern struct TOOL_HASH_NODE ** tool_hash_table_new();

static struct TOOL_HASH_NODE ** ip_learn_hash_table = NULL;
static FILE * file_ip_learn = NULL;

int ip_learn_check (long ip)
{
    struct in_addr in;
    in.s_addr = htonl(ip);
    char * ip_str = inet_ntoa(in);

    int ret = tool_hash_table_insert(ip_learn_hash_table, ip_str, strlen(ip_str), NULL);
    if(ret == 0)
    {
        return -1;
    }
    return 0;
}
/*
 * init filtered host from cfg
 */

void ip_learn_init()
{
    ip_learn_hash_table =  tool_hash_table_new();
    //file_ip_learn = fopen("../conf/ip_learn.txt","a");
}

void test_ip_learn()
{
    ip_learn_init();
    ip_learn_check(3729224509);
    ip_learn_check(2085532422);
}