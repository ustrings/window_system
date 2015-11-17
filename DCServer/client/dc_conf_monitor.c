#include <signal.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <errno.h>
#include <time.h>

#include "dc_global.h"

extern int load_jc_cfg(struct JC_S * jc_sites);

static time_t last_time = -1;
static int g_conf_AB_flag = 0;
static struct JC_S jc_sites_A[64];
static int jc_sites_A_len = 0;
static struct JC_S jc_sites_B[64];
static int jc_sites_B_len = 0;

void check_jc_cfg()
{
    printf ("begin load check_jc_cfg: \n");

    struct stat buf;
    if(stat(FILE_JC_CONF, &buf) == -1)
    {
        perror("stat");
        exit(1);
    }

    if(last_time == -1)
    {
        last_time = buf.st_mtime;
        return;
    }

    if(buf.st_mtime != last_time)
    {
        last_time = buf.st_mtime;
        if(g_conf_AB_flag == 1)
        {
            jc_sites_A_len = load_jc_cfg(jc_sites_A);
            g_conf_AB_flag = 0;
        }
        else
        {
            jc_sites_B_len = load_jc_cfg(jc_sites_B);
            g_conf_AB_flag = 1;
        }
    }
}

void check_jc_cfg_mem(int * curr_flag, struct JC_S ** jc_sites, int * jc_len)
{
    if(g_conf_AB_flag == curr_flag)
    {
        return;
    }
    if(g_conf_AB_flag == 0)
    {
        *jc_sites = jc_sites_A;
        *jc_len = jc_sites_A_len;
    }
    else
    {
        *jc_sites = jc_sites_B;
        *jc_len = jc_sites_B_len;
    }
    *curr_flag = g_conf_AB_flag;
}