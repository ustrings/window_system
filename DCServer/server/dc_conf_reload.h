#ifndef __DC_CONF_INCLUDE__
#define __DC_CONF_INCLUDE__
#include "business.h"
#include "dc_global.h"
#include <pthread.h>
#include <stdio.h>
#include <assert.h>

#define AD_WHITE_LIST_LEN 2097152

struct dc_conf
{
	//attribute
	struct business *m_pObj;
	char szPath[128];
	int flag;
};

struct business* new_dc_conf();

bool  load_dc_conf(struct business* pop, char* name, int flag, char* path);

bool  reload_dc_conf(struct business* pop, char* name, int flag, int isAuto);

void  destory_dc_conf(struct business* pop);

#endif
