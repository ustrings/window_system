#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <time.h>
#include <pthread.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <dirent.h>
#include "dc_global.h"

extern HashMap *g_pQCHashMap = NULL;
HashMap *g_pFileHashMap = NULL;
static HashMap *g_pNewFileHashMap = NULL;
static HashMap *g_pOldFileHashMap = NULL;
pthread_mutex_t pointer_changer ;

static int g_init = -1;

extern void load_query_content(char* filePath)

int file_reload_scan(char* sPathName , HashMap * pFileHashMap)
{
	char sFileName[512] = {0};
	DIR * dirp; //directory pointer
	dirp = opendir(sPathName);
	if(dirp == NULL)
	{
		return -1;
	}
	struct dirent* direntp;
	struct stat st;
	while((direntp = readdir(dirp)) != NULL)
	{
		if((strcmp(direntp->d_name,".")!=0) && (strcmp(direntp->d_name,"..")!=0))
		{
			memset(sFileName, '\0', 512);
			strcpy(sFileName, sPathName);
			strcat(sFileName, direntp->d_name);
			if(stat(sFileName, &st) == -1)
			{
				printf("get %s file info failed\n", sFileName);
				return -1;
			}
			else
			{
				char* gFile = malloc(42);
				memset(gFile, 0 , 42);
				memcpy(gFile, direntp->d_name, strlen(direntp->d_name));
				/*printf("filename  : %s\n", direntp->d_name);
				printf("create time : %s\n", ctime(&st.st_ctime));
				printf("last modify time : %s\n",ctime(&st.st_mtime));
				printf("last visit time : %s\n", ctime(&st.st_atime));*/

				char s[32] = {0};
				strftime(s, sizeof(s), "%Y-%m-%d %H:%M", localtime(&st.st_mtime));
				char* mTime = NULL;
				int ret =  hash_map_find(pFileHashMap, gFile, strlen(gFile),  (long*)&mTime);
				if(ret < 0) // no find this file
				{
					//printf("find new file\n");
					mTime = malloc(42);
					memset(mTime, 0 ,42);
					memcpy(mTime, s, strlen(s));
					hash_map_insert(pFileHashMap, gFile, strlen(gFile), (long*)mTime);
					load_query_content(sFileName);
				}
				else // find file  then compare value
				{
					break;
				}
			}
		}
	}
	return 0;
}
static int file_scan(char* sPathName )
{
	char sFileName[512] = {0};
	DIR * dirp; //directory pointer
	dirp = opendir(sPathName);
	if(dirp == NULL)
	{
		return -1;
	}
	struct dirent* direntp;
	struct stat st;
	while((direntp = readdir(dirp)) != NULL)
	{
		if((strcmp(direntp->d_name,".")!=0) && (strcmp(direntp->d_name,"..")!=0))
		{
			memset(sFileName, '\0', 512);
			strcpy(sFileName, sPathName);
			strcat(sFileName, direntp->d_name);
			if(stat(sFileName, &st) == -1)
			{
				printf("get %s file info failed\n", sFileName);
				return -1;
			}
			else
			{
				char* gFile = malloc(42);
				memset(gFile, 0 , 42);
				memcpy(gFile, direntp->d_name, strlen(direntp->d_name));
				/*printf("filename  : %s\n", direntp->d_name);
				printf("create time : %s\n", ctime(&st.st_ctime));
				printf("last modify time : %s\n",ctime(&st.st_mtime));
				printf("last visit time : %s\n", ctime(&st.st_atime));*/

				char s[32] = {0};
				strftime(s, sizeof(s), "%Y-%m-%d %H:%M", localtime(&st.st_mtime));
				char* mTime = NULL;
				int ret =  hash_map_find(g_pFileHashMap, gFile, strlen(gFile),  (long*)&mTime);
				if(ret < 0) // no find this file
				{
					//printf("find new file\n");
					/*if(g_init)
					{
						mTime = malloc(42);
						memset(mTime, 0 ,42);
						memcpy(mTime, s, strlen(s));
						hash_map_insert(g_pFileHashMap, gFile, strlen(gFile), (long*)mTime);
						load_query_content(sFileName);
						continue;
					}
					else*/
					{
						
						if(g_pFileHashMap == g_pOldFileHashMap)
						{
							file_reload_scan("./conf/", g_pNewFileHashMap);
						}
						else
						{
							file_reload_scan("./conf/", g_pOldFileHashMap);
						}
						pthread_mutex_lock(&pointer_changer);
						if(g_pFileHashMap == g_pOldFileHashMap)
						{
							g_pFileHashMap = g_pNewFileHashMap;
						}
						else
						{
							g_pFileHashMap = g_pOldFileHashMap;
						}
						//printf("hashtable have change : file %p\n", g_pFileHashMap);
						pthread_mutex_unlock(&pointer_changer);
					}
				}
				else // find file  then compare value
				{
					if(strcmp(mTime, s) == 0)
					{
						//printf("not find new file\n");
						//g_init = 0;
						continue;
					}
					else
					{
						//printf("find new file but time have update\n");
						if(g_pFileHashMap == g_pOldFileHashMap)
						{
							file_reload_scan("./conf/", g_pNewFileHashMap);	
						}
						else
						{
							file_reload_scan("./conf/", g_pOldFileHashMap);
						}
						pthread_mutex_lock(&pointer_changer);
						if(g_pFileHashMap == g_pOldFileHashMap)
						{
							g_pFileHashMap = g_pNewFileHashMap;
						}
						else
						{
							g_pFileHashMap = g_pOldFileHashMap;
						}
						pthread_mutex_unlock(&pointer_changer);
					}
				}
			}
		}
	}
	return 0;
}

int init_file_scan_load()
{
	g_init = 1;
	g_pQCHashMap = hash_map_new(40960);
	g_pNewFileHashMap = hash_map_new(4096);	
	g_pOldFileHashMap = hash_map_new(4096);
	//printf("new : %p, old : %p\n", g_pNewFileHashMap, g_pOldFileHashMap);	
	pthread_mutex_init(&pointer_changer, NULL);
	g_pFileHashMap = g_pOldFileHashMap;
	//file_scan("./conf/");
	file_reload_scan("./conf/", g_pFileHashMap);
}

void* cc_check_point()
{
	while(1)
	{
		//printf("this time hash pointer : %p, size : %d\n", g_pFileHashMap, g_pFileHashMap->size);
		sleep(4);
	}
}

/*int main()
{
	init_load_cfg();
	pthread_t pfilescan;
	pthread_t pfilecreate;
	int thread_ret = 0;
	thread_ret = pthread_create(&pfilescan, NULL, cc_time_scan, NULL);
	if(thread_ret != 0)
	{
		printf("create file scan thread failed\n");
		return -1;
	}
	thread_ret = pthread_create(&pfilecreate, NULL, cc_check_point, NULL);
	if(thread_ret != 0)
	{
		printf("create file scan thread failed\n");
		return -1;
	}
	
	pthread_join(pfilescan, NULL);
	pthread_join(pfilecreate, NULL);
	return 0;
}*/


