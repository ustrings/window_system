#ifndef __SENDER_URL_INCLUDE__
#define __SENDER_URL_INCLUDE__
#include "business.h"
#include "tool_hash_map_new.h"
#include "dir_process.h"
#include <pthread.h>
#include <stdio.h>
#include <assert.h>

#define SITE_WHITE_SUM 100000

struct sender_url
{
	//attribute
	struct business *m_pObj;
	char szPath[128];
	struct CDirProcess* dir_util;
	int flag;
};

struct business* new_sender_url();

bool  load_sender_url(struct business* pop, char* name, int flag, char* path);

bool  reload_sender_url(struct business* pop, char* name, int flag, int isAuto);

void  destory_sender_url(struct business* pop);

#endif
