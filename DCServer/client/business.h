#ifndef __BUSINESS_INCLUDE__
#define __BUSINESS_INCLUDE__
#include "tool_hash_map_new.h"
#include "dir_process.h"
#include <pthread.h>
#include <stdbool.h>
#define HTML_TYPE_SIZE 3

struct business
{
	void *pDerivedObj ;

        bool (*load_business)( struct business* pop, char* name, int flag, char* path);

        bool (*reload_business)( struct business* pop, char* name, int flag, int isAuto);

        void  (*destory_business)(struct business* pop);	
};

#endif
