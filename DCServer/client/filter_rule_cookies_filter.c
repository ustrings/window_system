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

static struct TOOL_HASH_NODE ** cookies_hash_table = NULL;
extern struct TOOL_HASH_NODE ** tool_hash_table_new();

/*
 * read filtered host from file by mmap
 */
char * cookies_filter_read_file(const char * filename)
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
int cookies_filter_load_conf (const char* filename)
{
    char * file_content = cookies_filter_read_file(filename);
    int curr_len = 0;
    char * curr_p = NULL;
    while ((*file_content != '\0'))
    {
        int index = 0;
        curr_p = file_content;

        struct TOOL_HASH_NODE ** cookies_val_hash_table =  NULL;
        while (*file_content != '\n')
        {
            if(curr_p == NULL) {curr_p = file_content; curr_len = 0;}
            curr_len++;
            if((*file_content == ',') && (index == 0))
            {
                long p_hash_table = 0;
                int ret = tool_hash_table_find(cookies_hash_table, curr_p, curr_len-1, &p_hash_table);
                if(ret < 0)
                {
                    cookies_val_hash_table =  tool_hash_table_new();
                    tool_hash_table_insert(cookies_hash_table, curr_p, curr_len-1, (long)cookies_val_hash_table);
                }
                else
                {
                    cookies_val_hash_table = (struct TOOL_HASH_NODE **)p_hash_table;
                }

                curr_p = NULL; curr_len = 0;
                index++;
            }
            else if((*file_content == ',') && (index == 1))
            {
                tool_hash_table_insert(cookies_val_hash_table, curr_p, curr_len-1, 0);
                curr_p = NULL; curr_len = 0;
                index++;
            }

            file_content++;

        }
        file_content++;
    }
    return 0;
}

int cookies_filter_check (const char* host, const int host_len, const char* cookies, const int cookies_len)
{
    long p_hash_table = 0;
    int ret = tool_hash_table_find(cookies_hash_table, host, host_len, &p_hash_table);
    if(ret < 0)
    {
        return -1;
    }
    else
    {
        struct TOOL_HASH_NODE ** cookies_val_hash_table = (struct TOOL_HASH_NODE **)p_hash_table;
        return tool_hash_table_find(cookies_val_hash_table, cookies, cookies_len, NULL);
    }
}
/*
 * init filtered host from cfg
 */

void cookies_filter_init()
{
    cookies_hash_table =  tool_hash_table_new();
    cookies_filter_load_conf ("../conf/cookies_filter.txt");
}

void test_cookies_filter()
{
    cookies_filter_init();

    char *host1 = "www.sina.com.cn";
    char *cookies1 = "abcdefghi";
    int ret = cookies_filter_check(host1, strlen(host1), cookies1, strlen(cookies1));
    printf("test: host = %s, len=%d, cookies=%s, len=%d, ret = %d\n", host1, strlen(host1), cookies1, strlen(cookies1),ret);

    char *host2 = "www.sina.com.cn";
    char *cookies2 = "abcdefghi2";
    ret = cookies_filter_check(host2, strlen(host2), cookies2, strlen(cookies2));
    printf("test: host = %s, len=%d, cookies=%s, len=%d, ret = %d\n", host2, strlen(host2), cookies2, strlen(cookies2),ret);

    char *host3 = "www.sina.com.cn";
    char *cookies3 = "xcvvcxvzxcvasdf";
    ret = cookies_filter_check(host3, strlen(host3), cookies3, strlen(cookies3));
    printf("test: host = %s, len=%d, cookies=%s, len=%d, ret = %d\n", host3, strlen(host3), cookies3, strlen(cookies3),ret);

    char *host4 = "www.xxxx.com";
    char *cookies4 = "xcvvcxvzxcvasdf";
    ret = cookies_filter_check(host4, strlen(host4), cookies4, strlen(cookies4));
    printf("test: host = %s, len=%d, cookies=%s, len=%d, ret = %d\n", host4, strlen(host4), cookies4, strlen(cookies4),ret);
}