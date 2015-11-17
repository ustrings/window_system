#include "dc_global.h"

static char g_fileName[256] = {'\0'};
static FILE * mqStream = NULL;
pthread_mutex_t online_write_lock;

extern int fast_write_file(const char* file_name,const char * start, int len);

void online_file_name(char * prefix, char * result)
{
      struct timeb tp;
      struct tm * tm;
      ftime ( &tp );
      tm = localtime (   & ( tp.time )   );

      sprintf(result, "%s%s_%d%02d%02d%02d.txt", RECORD_HTTP_PATH, prefix, tm->tm_year+1900, tm->tm_mon+1, tm->tm_mday, tm->tm_hour);
}


static int isMqDayChanged()
{
	static int day = 0;
	time_t timep;
	time(&timep);

	struct tm * tm;
	tm = localtime (&(timep));

	if(day != tm->tm_hour)
	{
		//printf("tm->tm_hour=%d\n", day);
		day = tm->tm_hour;
		return 1;
	}
	return 0;
}

int online_write_file(const char* file_name,const char * start, int len)
{
	if(mqStream == NULL)
	{
		mqStream = fopen(file_name, "at+");
	}
	fwrite(start, len, 1, mqStream);
	fwrite("\n", 1, 1, mqStream);
	fflush(mqStream);
	return 0;
}


void online_file_cache_put(const char * str_msg, int len, char* prefix)
{
	pthread_mutex_lock(&online_write_lock);
	if (isMqDayChanged())
	{
		if (mqStream)
		{
			fclose(mqStream);
			mqStream = NULL;
		}

		memset(g_fileName, 0, 256);
		online_file_name(prefix, g_fileName);
	}

	online_write_file(g_fileName, str_msg, len);
	//fast_write_file(g_fileName, str_msg, len);
	pthread_mutex_unlock(&online_write_lock);
}

