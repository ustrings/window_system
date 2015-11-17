#ifndef __SURPLUS_URL_INCLUDE__
#define __SURPLUS_URL_INCLUDE__
#include "business.h"
#include "tool_hash_map_new.h"
#include "dir_process.h"
#include <pthread.h>
#include <stdio.h>
#include <assert.h>

#define SURPLUS_URL_LEN 40960

struct surplus_url
{
	//attribute
	struct business *m_pObj;	
        HashMap* pSurplusUrlHashMap;
        pthread_rwlock_t surplus_mutex;
        struct CDirProcess* dir_util;
	int flag;
};

struct business* new_surplus_url();

bool  load_surplus_url(struct business* pop, char* name, int flag, char* path);

bool  reload_surplus_url(struct business* pop, char* name, int flag, int isAuto);

void  destory_surplus_url(struct business* pop);

#endif
