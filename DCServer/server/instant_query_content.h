#ifndef __QUERY_CONTENT_INCLUDE__
#define __QUERY_CONTENT_INCLUDE__
#include "business.h"
#include "tool_hash_map_new.h"
#include "dir_process.h"
#include "url_code.h"
#include <pthread.h>
#include <stdio.h>
#include <assert.h>

#define SURPLUS_URL_LEN 40960

struct query_content
{
	//attribute
	struct business *m_pObj;
	pthread_rwlock_t content_mutex;	
        struct CDirProcess* dir_util;
	int flag;
};

struct business* new_query_content();

bool  load_query_content(struct business* pop, char* name, int flag, char* path);

bool  reload_query_content(struct business* pop, char* name, int flag, int isAuto);

void  destory_query_content(struct business* pop);

#endif
