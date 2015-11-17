#include "dc_global.h"

void* g_pvOnlineSender = NULL;
pthread_mutex_t online_sender_mutex;
/************************************************************************/
//  [8/26/2014 zhaoyunzhou]                                             
//  online send cookie and adid info zeromq  init                                                  
/************************************************************************/
void mq_online_client_init()
{
    pthread_mutex_init(&online_sender_mutex, NULL);
    void * ctx = zctx_new ();
    g_pvOnlineSender = zsocket_new (ctx, ZMQ_PUSH);
    zsocket_connect(g_pvOnlineSender, g_conf_http_write_adr);
    printf("push tcp://61.152.73.246:5558, p=%d\n", g_pvOnlineSender);
    return ;
}

static int s_send (void *socket, char *string) 
{
	int rc = zmq_send(socket, string, strlen(string), 1);	
	return (rc);
}

/************************************************************************/
//  [8/26/2014 zhaoyunzhou]                                             
//  send cookie and adid info                                    
/************************************************************************/
void online_mq_direct_send_msg(char* pcMessage)
{
	pthread_mutex_lock(&online_sender_mutex);
	s_send(g_pvOnlineSender, pcMessage);
	pthread_mutex_unlock(&online_sender_mutex);
        //LOGT("pujie send data: %s\n", pcMessage);
}
