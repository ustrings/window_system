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

static struct TOOL_HASH_NODE ** host_learn_hash_table = NULL;
static FILE * file_host_learn = NULL;

int host_learn_check (char * host, int len)
{
    int ret = tool_hash_table_insert(host_learn_hash_table, host, len, NULL);
    if(ret == 0)
    {
        char host_str[1024]={'\0'};
        snprintf(host_str, len+1, "%s\n", host);
        fputs(host_str, file_host_learn);

        fflush(file_host_learn);
        printf("host learn %s\n", host_str);
    }
    return 0;
}
/*
 * init filtered host from cfg
 */

void host_learn_init()
{
    host_learn_hash_table =  tool_hash_table_new();
    file_host_learn = fopen("../conf/ip_learn.txt","a");
}

void test_host_learn()
{
    host_learn_init();
    char * host = "abcdef";
    host_learn_check(host, strlen(host));
    host_learn_check(host, strlen(host));
}