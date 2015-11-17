#include "dc_global.h"

static void * g_ip_learn_mq_client[MAX_NUM_THREADS]   = { NULL };
static void * ip_learn_mq_socket;
extern int ip_learn_check (char * host, int len);
extern void ip_learn_init();

void ip_learn_check_mq_init()
{
    /*init server sokcet*/
    void * ctx = zctx_new ();
    ip_learn_mq_socket = zsocket_new (ctx, ZMQ_PULL);
    zmq_bind (ip_learn_mq_socket, "inproc://host_learn_mq");

    /*init client sokcet*/
    int i= 0;
    for(; i<16; i++)
    {
        g_ip_learn_mq_client[i] = zsocket_new (ctx, ZMQ_PUSH);
        zsocket_connect (g_ip_learn_mq_client[i], "inproc://host_learn_mq");
    }
}
/*
 * api of cache mgr
*/
void ip_learn_check_async(char * host, int len, long tid)
{
    zframe_t *frame = zframe_new (host, len);
    zframe_send (&frame, g_ip_learn_mq_client[tid], 0);
}

/*
 * thread loop to receive host and append to cache
 */
static void * ip_learn_thread_func(void *args)
{
    while (1)
    {
        char * host = zstr_recv (ip_learn_mq_socket);
        ip_learn_check(host, strlen(host));
        free(host);
    }
}
/*
 * start thread
 */
void ip_learn_thread_init()
{
    printf("start host learn thread...\n");
    ip_learn_init();
    ip_learn_check_mq_init();
    zthread_new(ip_learn_thread_func, (void*)0);
}
