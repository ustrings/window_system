
#ifndef DIR_PROCESS
#define DIR_PROCESS
#include <assert.h>
#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <time.h>

typedef struct CFile
{
        char  szFileMTime[32];
        char  szFileName[512];
}CFileTable;

struct CDirProcess
{
        //attribute
        CFileTable ** cfTable;
        int nFileNum;
        char szPath[128];
        int length;
        //method
        int (* dir_init )(struct CDirProcess* dir_process);
        void (*set_length )( struct CDirProcess* dir_process, int len );
        int (*get_file_num )( struct CDirProcess* dir_process );
        void (*set_path )( struct CDirProcess* dir_process, char* path );
        void (*dir_foreach )( struct CDirProcess* dir_process );
	bool (*dir_compare )( struct CDirProcess* dir_process );
        void (*destroy_instance)(struct CDirProcess* dir_process);
	void (*dir_reload )( struct CDirProcess* dir_process );

};

//  Create dir class
struct CDirProcess* create_instance( );

//  Free dir class
void destroy_instance(struct CDirProcess* dir_process);

//  Set dir max file number
void set_length( struct CDirProcess* dir_process, int len );

//  Get fact file number
int get_file_num( struct CDirProcess* dir_process );

//  Set dir class process path
void set_path( struct CDirProcess* dir_process, char* path );

//  Dir foreach
void dir_foreach( struct CDirProcess* dir_process );

// check is load
bool dir_compare( struct CDirProcess* dir_process );

//  Dir Reload
void dir_reload( struct CDirProcess* dir_process );

//  Dir init 
int dir_init( struct CDirProcess* dir_process );

#endif
