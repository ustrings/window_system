#include "dc_global.h"

void * g_mq_direct_client[MAX_NUM_THREADS]   = { NULL };

extern char** common_get_range_addr(const char * addr_begin, int num);

void mq_storm_client_init(const char * gateway_begin, int num)
{
    char** gateway_addrs = common_get_range_addr(gateway_begin, num);


    int i =0;
    for(; i < g_conf_capture_thread_num; i++, gateway_addrs++)
    {
        void * ctx = zctx_new ();
        g_mq_direct_client[i] = zsocket_new (ctx, ZMQ_PUSH);
        zsocket_connect(g_mq_direct_client[i], *gateway_addrs);
        printf("storm mq connect to %s\n", *gateway_addrs);
    }
    return ;
}
void mq_storm_send_msg(const char * msg, long tid)
{
    //fwrite(msg, strlen(msg), 1, g_files[tid]);

    zframe_t *frame = zframe_new (msg, strlen(msg)+1);
    zframe_send (&frame, g_mq_direct_client[tid], 0);
}

