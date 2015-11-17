#include "dc_conf_reload.h"

extern void dc_load_pop_mobile(zzconfig_t * root);
extern void load_enable_list(zzconfig_t * root);

struct business* new_dc_conf()
{
	struct business* pObj = (struct business*)malloc(sizeof(struct business));
	if(NULL == pObj)
	{
		return NULL;
	}
	struct dc_conf* pop = (struct dc_conf*)malloc(sizeof(struct dc_conf));
	if(NULL == pop)
	{
		free(pObj);
		pObj = NULL;
		return NULL;
	}
	memset(pop, 0 , sizeof(struct dc_conf));
	pObj->pDerivedObj = pop;
	pObj->load_business = load_dc_conf;
	pObj->reload_business = reload_dc_conf;
	pObj->destory_business = destory_dc_conf;
	pop->m_pObj = pObj;
	return pObj;
}

bool  load_dc_conf(struct business* pop, char* name, int flag, char* path)
{
	if( (strcmp(name, "dc_conf") != 0))
	{
		return false;
	}
	struct dc_conf *top = pop->pDerivedObj;
        if(top->m_pObj != pop)
        {   
                return false;
        }
	top->flag = flag;
	strcpy(top->szPath, path);
	return true;
}

bool reload_dc_conf(struct business* pop, char* name, int flag, int isAuto)
{
	struct dc_conf *top = pop->pDerivedObj;
	if( (strcmp(name, "dc_conf") != 0) || (flag == top->flag))
	{
		return false;
	}
        if(top->m_pObj != pop)
        {   
                return false;
        }
	top->flag = flag;
	printf ("begin reload load_dc_cfg: \n");	
	zzconfig_t * root = zz_config_load (top->szPath);

	char * enable_pop_window = zz_config_resolve(root, "global/pop_window", "disable");
	char * enable_pop_mobile = zz_config_resolve(root, "global/pop_mobile", "disable");
	char * enable_radius_recv = zz_config_resolve(root, "global/radius_recv", "disable");
	char * enable_record_url = zz_config_resolve(root, "global/record_url", "disable");
    	if(strcmp(enable_pop_window, "enable") == 0)
	{
	        g_conf_enable_pop_window = 1;
	}
	else
	{
	        g_conf_enable_pop_window = 0;
	}
        if(strcmp(enable_pop_mobile, "enable") == 0)
	{
	        g_conf_enable_pop_mobile = 1;
		dc_load_pop_mobile(root);
	}
	else
	{
		    g_conf_enable_pop_mobile = 0;
	
	}
	if(strcmp(enable_radius_recv, "enable") == 0)
	{
		    g_conf_enable_radius_recv = 1;
	}
	else
	{
		    g_conf_enable_radius_recv = 0;
	}
	if(strcmp(enable_record_url, "enable") == 0)
	{
		    g_conf_record_url = 1;
	}
	else
	{
		    g_conf_record_url = 0;
	}

	load_enable_list(root);

	sleep(3);
	return true;
}

void destory_dc_conf(struct business* pop)
{
	struct dc_conf* top = pop->pDerivedObj;
	assert(NULL != pop);
	free(top);
	top = NULL;
	free(pop);
	pop = NULL;
}
