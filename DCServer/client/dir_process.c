#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <dirent.h>
#include "dir_process.h"

struct CDirProcess* create_instance( int num )
{
	struct CDirProcess* instance = (struct CDirProcess*)malloc(sizeof(struct CDirProcess));
	assert(NULL != instance);
	memset(instance, 0, sizeof(struct CDirProcess));
	instance->dir_init = dir_init;
	instance->set_length = set_length;
	instance->get_file_num = get_file_num;
	instance->set_path = set_path;
	instance->dir_compare = dir_compare;
	instance->dir_foreach = dir_foreach;
	instance->dir_reload = dir_reload;
	instance->destroy_instance = destroy_instance;
	
	return instance;
}

void destroy_instance(struct CDirProcess* dir_process)
{
	CFileTable ** dir_table = dir_process->cfTable;
	assert(NULL != dir_table);
	int i = 0;
	for(; i < dir_process->nFileNum; i++ )
	{
		if(NULL != dir_table[i])
		{
			free(dir_table[i]);
			dir_table[i] = NULL;
		}
	}	
	if(dir_table != NULL)
	{
		free(dir_table);
		dir_table = NULL;
	}
	
	if(dir_process != NULL)
	{
		free(dir_process);
		dir_process = NULL;
	}
}

void set_length( struct CDirProcess* dir_process, int len )
{
	assert(NULL != dir_process);
	dir_process->length = len;
}

int get_file_num( struct CDirProcess* dir_process )
{
	assert(NULL != dir_process);
	return dir_process->nFileNum;
}

void set_path( struct CDirProcess* dir_process, char* path )
{
	assert(NULL != dir_process);
	assert(NULL != path);
	strcpy(dir_process->szPath, path);
}

void dir_foreach( struct CDirProcess* dir_process )
{
	assert(NULL != dir_process);
	CFileTable ** dir_table = dir_process->cfTable;	
	assert(NULL != dir_table);
	int i = 0 ;

	for(; i < dir_process->nFileNum; i++)
	{
		printf("%s -- %s\n", dir_table[i]->szFileName, dir_table[i]->szFileMTime );
	}
}

void dir_reload( struct CDirProcess* dir_process )
{
	assert(NULL != dir_process);
        CFileTable ** dir_table = dir_process->cfTable;
	assert(NULL != dir_table);
        int i = 0;
        for(; i < dir_process->nFileNum; i++ )
        {
                if(NULL != dir_table[i])
                {
                        free(dir_table[i]);
                        dir_table[i] = NULL;
                }
        }
        if(dir_table != NULL)
        {
                free(dir_table);
                dir_table = NULL;
        }
	dir_process->nFileNum = 0;
	dir_init(dir_process);
}

bool dir_compare( struct CDirProcess* dir_process )
{
	assert(NULL != dir_process);
	CFileTable ** dir_table = dir_process->cfTable;
	assert(NULL != dir_table);

	char sFileName[512] = {0};
	int nFileNum = 0;
	DIR * dirp; //directory pointer
        dirp = opendir(dir_process->szPath);
        if(dirp == NULL)
        {
                return false;
        }
        struct dirent* direntp;
        struct stat st;
        while((direntp = readdir(dirp)) != NULL)
        {
                if((strcmp(direntp->d_name,".")!=0) && (strcmp(direntp->d_name,"..")!=0))
                {
                        memset(sFileName, '\0', 512);
                        strcpy(sFileName, dir_process->szPath);
                        strcat(sFileName, direntp->d_name);
                        if(stat(sFileName, &st) == -1)
                        {
                                printf("get %s file info failed\n", sFileName);
                                return false;
                        }
                        else
                        {
				char sTime[32] = {0};
				strftime(sTime, sizeof(sTime), "%Y-%m-%d %H:%M", localtime(&st.st_mtime));
        			int i = 0;
        			for(; i < dir_process->nFileNum; i++ )
        			{
                			if(NULL != dir_table[i])
                			{
						if(strcmp(sFileName, dir_table[i]->szFileName) == 0)
						{
							if(strcmp(sTime , dir_table[i]->szFileMTime) != 0)
							{
								return true;
							}
						}
                			}
        			}
				nFileNum++;
			}

		}
	}
	if(nFileNum != dir_process->nFileNum)
	{
		return true;
	}
	return false;
}

int dir_init( struct CDirProcess* dir_process )
{
	assert(NULL != dir_process);
	if(0 == dir_process->length)
	{
		dir_process->length = 64;
	}
	int i = 0 ;
	dir_process->cfTable = (CFileTable**)malloc(sizeof(CFileTable )*(dir_process->length));
	assert(NULL != dir_process->cfTable);
	memset(dir_process->cfTable, 0, sizeof(CFileTable )*(dir_process->length));

	char sFileName[512] = {0};
	DIR * dirp; //directory pointer
        dirp = opendir(dir_process->szPath);
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
                        strcpy(sFileName, dir_process->szPath);
                        strcat(sFileName, direntp->d_name);
                        if(stat(sFileName, &st) == -1)
                        {
                                printf("get %s file info failed\n", sFileName);
                                return -1;
                        }
                        else
                        {
				char sTime[32] = {0};
				strftime(sTime, sizeof(sTime), "%Y-%m-%d %H:%M", localtime(&st.st_mtime));
				CFileTable * cTable = (CFileTable*)malloc(sizeof(CFileTable));
				memset(cTable, 0, sizeof(CFileTable));
				memcpy(cTable->szFileName, sFileName, strlen(sFileName));
				memcpy(cTable->szFileMTime, sTime, strlen(sTime));
				dir_process->cfTable[dir_process->nFileNum] = cTable;
				dir_process->nFileNum++;
			}

		}
	}
	return 0;	
}
