#ifndef __HOST_BLACK_INCLUDE__
#define __HOST_BLACK_INCLUDE__
#include "business.h"
#include "tool_hash_map_new.h"
#include "dir_process.h"
#include <pthread.h>
#include <stdio.h>
#include <assert.h>

#define SITE_BLACK_SUM 10000

struct black_host
{
	//attribute
	struct business *m_pObj;	
        HashMap* pBlackSiteHashMap;
        pthread_rwlock_t black_host_mutex;
        struct CDirProcess* dir_util;
	int flag;
};

struct business* new_black_host();

bool  load_black_host(struct business* pop, char* name, int flag, char* path);

bool  reload_black_host(struct business* pop, char* name, int flag, int isAuto);

void  destory_black_host(struct business* pop);

#endif
