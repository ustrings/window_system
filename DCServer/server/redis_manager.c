#include "redis_manager.h"


redisContext*  ConnRedis(char* sIp , int nPort)
{
	redisContext * conn = redisConnect(sIp, nPort);
	if(conn->err)
	{
		return NULL;
	}
	return conn;
}

int SendCommand(redisContext* conn, char* sCommand)
{
	redisReply *reply = redisCommand(conn, sCommand);
	if (NULL == reply) {
		freeReplyObject(reply);
		return -1;
	}
	if((REDIS_ERR == reply) && (strcasecmp(conn->err,REDIS_ERR_EOF) == 0))
	{
		freeReplyObject(reply);
		return -1;
	}
	freeReplyObject(reply);
	return 0;
}

void closeRedis(redisContext* conn)
{
	redisFree(conn);
}
