#ifndef __MTHREAD_NCLUDE__
#define __MTHREAD_NCLUDE__
#include <pthread.h>
#include <stdio.h>

struct mthread
{
	//attribute
	pthread_t mt;

	//method
	void (* start_thread)(struct mthread* thread, void* arg);

	void (* end_thread)(struct mthread* thread);	

};

struct mthread*  new_thread();

void*  monitor_thread_func(void* arg);

void  start_thread(struct mthread* thread, void * arg);

void  end_thread(struct mthread* thread);

#endif
