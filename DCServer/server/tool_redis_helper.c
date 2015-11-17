#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "hiredis.h"
#include "dc_global.h"
static pthread_mutex_t redis_lock;
static redisContext *tool_reids_context;

void tool_redis_helper_init()
{
	tool_reids_context = redisConnect("127.0.0.1", 6379);
	if (tool_reids_context != NULL && tool_reids_context->err)
	{
		printf("Error: %s\n", tool_reids_context->errstr);
	}
}

int tool_reids_helper_get_advid(SPopUserContent* psUserContent, const char * ccpIPUa)
{
	pthread_mutex_lock(&redis_lock);
	redisReply *reply;

	reply = redisCommand(tool_reids_context,"HGET %s %s", ccpIPUa, "adid_filed");
	//redisAppendCommand(tool_reids_context,"HGet %s %s", md5_ipua, "adid_filed");
	//redisAppendCommand(tool_reids_context,"HGet %s %s", md5_ipua, "ts_filed");

	//get advid
	//redisGetReply(tool_reids_context,(void**)&reply);
	if (NULL != reply && reply->type == REDIS_REPLY_STRING)
	{
		psUserContent->ad_id = atoi(reply->str);
		printf("redis reply->str adid_filed=%s\n", reply->str);
	}
	else
	{
		freeReplyObject(reply); 
		pthread_mutex_unlock(&redis_lock);
		return -1;
	}

	freeReplyObject(reply);
	
	reply = redisCommand(tool_reids_context,"HGET %s %s", ccpIPUa, "ts_filed");
	// get ts
	//redisGetReply(tool_reids_context,(void**)&reply);
	if (NULL != reply && reply->type == REDIS_REPLY_STRING)
	{
		psUserContent->ts = atol(reply->str);
		printf("redis reply->strts_filed =%s\n", reply->str);
		freeReplyObject(reply); 
		pthread_mutex_unlock(&redis_lock);
		return 1;
	}

	freeReplyObject(reply); 
	pthread_mutex_unlock(&redis_lock);
	return -1;
}

//void tool_redis_helper_refresh_data(struct TOOL_HASH_NODE ** ip_ua_hash_table)
//{
//	redisReply *reply = redisCommand(tool_reids_context, "SET foo bar");
//
//	reply=(redisReply*)redisCommand(tool_reids_context,"keys md5*");
//	printf("redis keys f*: %lu\n", reply->elements);
//	if (REDIS_REPLY_ERROR == reply->type || REDIS_REPLY_NIL == reply->type)
//	{
//		printf("the result of redis nil or error.");
//		return;
//	}
//
//	size_t len = reply->elements;
//	size_t i;
//	for (i = 0; i < reply->elements; i++)
//	{
//		redisAppendCommand(tool_reids_context,"HGetALL %s", reply->element[i]->str);
//	}
//	freeReplyObject(reply); 
//
//	redisReply *piplineReply;
//	for (i = 0; i < len; i++)
//	{
//		redisGetReply(tool_reids_context,(void**)&piplineReply);
//		if (REDIS_REPLY_ERROR == piplineReply->type || REDIS_REPLY_NIL == piplineReply->type)
//		{
//			continue;
//		}
//		size_t j;
//		for (j = 0; j < piplineReply->elements; j++)
//		{ 
//			printf("pipleline%lu: %s\n", i, piplineReply->element[j]->str);
//		}
//
//		freeReplyObject(piplineReply); 
//	}
//
//	return;
//}