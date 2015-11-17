#include "context_monitor.h"

struct monitor* create_monitor()
{
	struct monitor* mon = (struct monitor*)malloc(sizeof(struct monitor));
	assert(NULL != mon);
	mon->monitor_init = monitor_init;
	mon->func_loop_check = monitor_loop_check;
	mon->destroy_monitor = destroy_monitor;
	return mon; 
}

int monitor_init( struct monitor * mon )
{
	assert(NULL != mon);	
	zzconfig_t * root = zz_config_load (MONITOR_CONFIG);
	assert(NULL != root);
	int num = zz_config_getfileline(root);
	mon->buss = business_init(num);
	zzconfig_t *child = zz_config_child(root);
	while(child)
	{
		char* name = zz_config_name(child);
                char* cflag = zz_config_value(child);
		int flag = atoi(cflag);
		zzconfig_t *slibe = zz_config_child(child);
		char* path = zz_config_value(slibe);
		mon->buss->load(mon->buss, name, flag, path);
		child = zz_config_next(child);
	}
	return 0;
}


void  monitor_loop_check( struct monitor * mon )
{
	if (NULL == mon)
	{
	    return;
	}

        zzconfig_t * root = zz_config_load (MONITOR_CONFIG);
	if (NULL == root)
	{
	    return;
	}
	zzconfig_t *child = zz_config_child(root);
        while(child)
        {
                char* name = zz_config_name(child);
                char* cflag = zz_config_value(child);
		int flag = atoi(cflag);
		zzconfig_t *slibe = zz_config_child(child);
		char* path = zz_config_value(slibe);
                slibe = zz_config_next(slibe);
                char* isAuto = zz_config_value(slibe);
                int AutoFlag = atoi(isAuto);
                mon->buss->update(mon->buss, name, flag, AutoFlag);
                child = zz_config_next(child);
        }	
}

void destroy_monitor(struct monitor * mon)
{
	mon->buss->destory(mon->buss);
	if(mon)
	{
		free(mon);
		mon = NULL;
	}
}
