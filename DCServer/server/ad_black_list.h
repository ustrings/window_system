#ifndef __RADIUS_BLACK_INCLUDE__
#define __RADIUS_BLACK_INCLUDE__
#include "business.h"
#include "tool_hash_map_new.h"
#include "dir_process.h"
#include <pthread.h>
#include <stdio.h>
#include <assert.h>

#define AD_WHITE_LIST_LEN 2097152

struct radius_black
{
	//attribute
	struct business *m_pObj;
	char szPath[128];
        HashMap* pAdWhiteHashMap;
        pthread_rwlock_t radius_black_lock;
	struct CDirProcess* dir_util;
	int flag;
};

struct business* new_radius_black();

bool  load_radius_black(struct business* pop, char* name, int flag, char* path);

bool  reload_radius_black(struct business* pop, char* name, int flag, int isAuto);

void  destory_radius_black(struct business* pop);

#endif
