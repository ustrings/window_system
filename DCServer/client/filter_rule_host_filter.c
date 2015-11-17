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

static struct TOOL_HASH_NODE ** host_hash_table = NULL;
extern struct TOOL_HASH_NODE ** tool_hash_table_new();

/*
 * read filtered host from file by mmap
 */
char * host_filter_read_file(const char * filename)
{
    int fd=open(filename,O_RDWR|O_CREAT,S_IRUSR|S_IWUSR);
    long size = lseek(fd, 0, SEEK_END);

    lseek(fd,0,SEEK_SET);

    char * hostnames=(char*)mmap(0,size,PROT_READ|PROT_WRITE,MAP_PRIVATE,fd,0);
    if (MAP_FAILED==hostnames)
    {
        perror("mmap");
        return NULL;
    }
    return hostnames;
}
/*
 * load filtered host
 */
int host_filter_load_conf (const char* filename)
{
    char * file_content = host_filter_read_file(filename);
    int curr_len = 0;
    char * curr_p = NULL;
    while ((*file_content != '\0'))
    {
        int index = 0;
        curr_p = file_content;
        curr_len = 0;

        while ((*file_content != '\n') && (index < 3))
        {
            file_content++;
            curr_len++;
        }
        tool_hash_table_insert(host_hash_table, curr_p, curr_len, NULL);
        file_content++;
    }
    return 0;
}

int host_filter_check (const char* host, const int host_len)
{
    int ret = tool_hash_table_find(host_hash_table, host, host_len, NULL);
    if(ret <= 0)
    {
        return -1;
    }
    return 1;
}
/*
 * init filtered host from cfg
 */

void host_filter_init()
{
    host_hash_table =  tool_hash_table_new();
    host_filter_load_conf ("../conf/host_filter.txt");
}

void test_host_filter()
{
    host_filter_init();

    char * host1 = "www.sina.com.cn";
    int ret = host_filter_check(host1, strlen(host1));
    printf("host 1 ret = %d\n", ret);

    char * host2 = "www.xxxx.com";
    ret = host_filter_check(host2, strlen(host2));
    printf("host 2 ret = %d\n", ret);
}