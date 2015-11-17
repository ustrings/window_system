#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "hiredis.h"
#include "dc_global.h"
static redisContext *tool_reids_context;

static void  ConnRedis(char* sIp , int nPort)
{
               tool_reids_context = redisConnect(sIp, nPort);
               if(tool_reids_context->err)
               {
                       printf("connection error: %s", tool_reids_context->err);
              }
}
      
static  int SendCommand( char* sCommand, char* szOut)
{
              redisReply *reply = redisCommand(tool_reids_context, sCommand);
             if (NULL == reply) {
              printf("Failed to execute command[%s]. reason : redisCOmmand return NULL\n", sCommand);
                      freeReplyObject(reply);
                      return -1;
              }
              if((REDIS_ERR == reply) && (strcasecmp(tool_reids_context->err,REDIS_ERR_EOF) == 0))
              {
                      printf("Failed to execute command[%s]. reason : reply conn have expire\n",sCommand);
                      freeReplyObject(reply);
                      return -1;
              }
              if(NULL != reply && reply->type == REDIS_REPLY_ARRAY)
	      {
			int i =0 ;
			char szBuff[64] = {0};
			for(; i< reply->elements; i++)
			{
				if((i % 2) ==0)
				{
					memset(szBuff,'\0', 64);
					strcpy(szBuff, reply->element[i]->str);
				}
				else
				{
					strcat(szBuff, reply->element[i]->str);
					memcpy(szOut, szBuff, strlen(szBuff));
				}
			}
	      }
              freeReplyObject(reply);
              return 0;
}
      
static void closeRedis()
{
      redisFree(tool_reids_context);
}
	
void radius_redis_load_init()
{
	ConnRedis("127.0.0.1" , 6379);
}

int radius_redis_getall(char* szOut)
{
	if(SendCommand("select 3", NULL)<0)
	{
		printf("select failed\n");
		return -1;
	}
	if(SendCommand("hgetall cxxIPUA", szOut)<0)
	{
		printf("hgetall failed\n");
		return -1;
	}
	return 0;
}

void radius_redis_exit()
{
	closeRedis();
}
