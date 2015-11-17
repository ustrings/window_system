/*
 *	author : zhaoyunzhou
 *	time   : 2014-11-22
 *	function : process business
 */

#ifndef __CONTROL_INCLUDE__
#define __CONTROL_INCLUDE__
#include <stdio.h>
#include <unistd.h>
#include "business.h"

struct control
{
	//attribute
	int business_num;
	struct business* table[16];
	//method
	void (*load)(struct control * buss, char * name, int flag, char* cfg_path);
	void (*update)( struct control * buss, char* name, int flag, int isAuto);
	void (*destory)( struct control * buss );
};

//   business init
struct control* business_init( int num);

//   set business
void load(struct control * buss, char * name, int flag, char* cfg_path); 

//   Update business
void update( struct control * buss, char *name, int flag , int isAuto);

//   Destroy bustroy
void destory( struct control * buss );

#endif
