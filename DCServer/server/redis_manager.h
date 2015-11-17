#ifndef __REDISMANAGER__H__
#define __REDISMANAGER__H__

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "hiredis.h"
redisContext*  ConnRedis(char* sIp , int nPort);

int SendCommand(redisContext* conn, char* sCommand);

void closeRedis(redisContext* conn);

#endif
