#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "dc_global.h"

unsigned long long  g_rx_pkt_num    [MAX_NUM_THREADS] = { 0 };
unsigned long long  g_rx_pkt_send_num    [MAX_NUM_THREADS] = { 0 };
unsigned long long  g_rx_pkt_byte   [MAX_NUM_THREADS] = { 0 };
unsigned long long  g_rx_pkt_gre_num   [MAX_NUM_THREADS] = { 0 };
unsigned long long  g_rx_pkt_http_num   [MAX_NUM_THREADS] = { 0 };
unsigned long long  g_pkt_drop_num   [MAX_NUM_THREADS] = { 0 };

pfring  *           g_rx_rings      [MAX_NUM_THREADS] = { NULL };
pthread_t           g_cap_thread    [MAX_NUM_THREADS];

int g_is_shutdown = 0;

char** common_get_range_addr(const char * addr_begin, int num)
{
    if(num > 16) return NULL;
    char ** p_addr = (char **)malloc(16*sizeof(char*));
    char ** p_addr_offset = p_addr;

    const char * s_port = strstr(strstr(addr_begin, ":")+1, ":") + 1 ;
    int port = atoi(s_port);
    int prefix_len = strlen(addr_begin) - strlen(s_port);
    char  prefix[100] = {0};
    memcpy(prefix,addr_begin,prefix_len-1);

    printf ("front from %s, to %d, %d\n", prefix,   port, num);

    int i;
    for (i = 0; i < num; ++i, p_addr_offset++)
    {
        int curr_port = port + i;
        char * addr = (char *)malloc(128);
        sprintf(addr, "%s:%d", prefix, curr_port);
        *p_addr_offset = addr;
    }

    return p_addr;
}


int isDayChanged(long curr)
{
    static int day = 0;

    struct tm * tm;
    tm = localtime (   & (curr)   );

    if(day != tm->tm_mday)
    {
        day = tm->tm_mday;
        return 1;
    }
    return -1;
}

void get_file_name(char * prefix, char * result)
{
    struct timeb tp;
    struct tm * tm;
    ftime ( &tp );
    tm = localtime (   & ( tp.time )   );

    sprintf(result, "%s.%d-%02d-%02d.txt", prefix, tm->tm_year+1900, tm->tm_mon+1, tm->tm_mday);
}
