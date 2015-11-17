#ifndef __MONITOR_INCLUDE__
#define __MONITOR_INCLUDE__
#include "zz_config.h"
#include "control.h"

#define MONITOR_CONFIG "/home/DCServer/conf/client_monitor.cfg"

struct monitor
{
	//attribue
	struct control* buss;
	
	//method

	int (*monitor_init)(struct monitor* mon);

	void  (*func_loop_check)(struct monitor* mon);

	void  (*destroy_monitor)(struct monitor * mon);
};


void destroy_monitor(struct monitor * mon);

void  monitor_loop_check( struct monitor * mon );

int monitor_init( struct monitor * mon );

struct monitor* create_monitor();

#endif


