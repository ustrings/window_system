#include "mthread.h"
#include "context_monitor.h"

void* monitor_thread_func(void* arg)
{
	struct monitor* monitor= (struct monitor*)arg;
	while(1)
	{
		monitor_loop_check( monitor );	
		sleep(4);
	}
}

struct mthread* new_thread()
{
	struct mthread* thread = (struct mthread*)malloc(sizeof(struct mthread));
	assert(NULL != thread);
	thread->start_thread = start_thread;
	thread->end_thread = end_thread;
	return thread;
}

void  start_thread(struct mthread* thread, void * arg)
{
	int ret = 0;
	ret = pthread_create(&(thread->mt), NULL, monitor_thread_func, arg);
	if(ret != 0)
	{
		printf("create pthread error!\n");
		exit(-1); 
	}
	printf("start monitor ...\n");
}

void end_thread(struct mthread* thread)
{
	if(NULL != thread);
	{
		pthread_join(thread->mt , NULL);
		free(thread);
		thread = NULL;
	}
	printf("shutdown monitor ...\n");
}


