#ifndef __DATA_PASS_BACK_INCLUDE__
#define __DATA_PASS_BACK_INCLUDE__
#include "dc_global.h"

struct pass_back
{
	//attribute
	void* pvSender;
	void* context;
	pthread_t pass_thread;

	//method
	void ( *destory_pass_back )( struct pass_back* pass );
};

struct pass_back* create_pass_back();

void* get_data_pass_back( struct pass_back* pass );

void destory_pass_back( struct pass_back* pass );

#endif
