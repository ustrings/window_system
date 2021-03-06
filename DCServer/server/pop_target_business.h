#ifndef __POP_TARGET_INCLUDE__
#define __POP_TARGET_INCLUDE__
#include "business.h"
#include "tool_hash_map_new.h"
#include "dir_process.h"
#include <pthread.h>
#include <stdio.h>
#include <assert.h>

#define POP_TARGET_LEN 40960

struct pop_target
{
	//attribute
	struct business *m_pObj;	
        HashMap* pTargetedPopHashMap;
        pthread_rwlock_t target_mutex;
        struct CDirProcess* dir_util;
	int flag;
};

struct business* new_pop_target();

bool  load_pop_target(struct business* pop, char* name, int flag, char* path);

bool  reload_pop_target(struct business* pop, char* name, int flag, int isAuto);

void  destory_pop_target(struct business* pop);

#endif
