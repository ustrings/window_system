#ifndef __HOST_WHITE_INCLUDE__
#define __HOST_WHITE_INCLUDE__
#include "business.h"
#include "tool_hash_map_new.h"
#include "dir_process.h"
#include <pthread.h>
#include <stdio.h>
#include <assert.h>

#define SITE_TOTAL_WHITE_SUM 100000

struct white_host
{
	//attribute
	struct business *m_pObj;	
        HashMap* pTotalWhiteSiteHashMap;
        pthread_rwlock_t white_host_mutex;
        struct CDirProcess* dir_util;
	int flag;
};

struct business* new_white_host();

bool  load_white_host(struct business* pop, char* name, int flag, char* path);

bool  reload_white_host(struct business* pop, char* name, int flag, int isAuto);

void  destory_white_host(struct business* pop);

#endif
