#ifndef __URI_BLACK_INCLUDE__
#define __URI_BLACK_INCLUDE__
#include "business.h"
#include "tool_hash_map_new.h"
#include "dir_process.h"
#include <pthread.h>
#include <stdio.h>
#include <assert.h>

#define URI_BLACK_SUM 10000

struct black_uri
{
	//attribute
	struct business *m_pObj;	
        struct CDirProcess* dir_util;
	int flag;
};

struct business* new_black_uri();

bool  load_black_uri(struct business* pop, char* name, int flag, char* path);

bool  reload_black_uri(struct business* pop, char* name, int flag);

void  destory_black_uri(struct business* pop);

#endif
