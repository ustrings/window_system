#include "dc_global.h"

#include <sys/timeb.h>

static void * g_pop_mq_record_client[MAX_NUM_THREADS]   = { NULL };
extern char** common_get_range_addr(const char * addr_begin, int num);

/************************************************************************/
/* 初始化url记录zmq                                                       */
/************************************************************************/
void mq_record_client_init()
{
	int i= 0;
	for(; i<16; i++)
	{
		void * ctx = zctx_new ();
		g_pop_mq_record_client[i] = zsocket_new (ctx, ZMQ_PUSH);
		zsocket_connect (g_pop_mq_record_client[i], "ipc://mq_record_inproc_mq");
	}

	return;
}

/************************************************************************/
/* 发送过虑的url报文                                                       */
/************************************************************************/
void mq_record_send_msg(const char * msg, long tid)
{
	zframe_t *frame = zframe_new (msg, strlen(msg));
	zframe_send (&frame, g_pop_mq_record_client[tid], 0);
}

