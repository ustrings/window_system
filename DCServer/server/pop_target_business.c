#include "pop_target_business.h"
extern HashMap* g_pTargetedPopHashMap;

struct business* new_pop_target()
{
	struct business* pObj = (struct business*)malloc(sizeof(struct business));
	if(NULL == pObj)
	{
		return NULL;
	}
	struct pop_target* pop = (struct pop_target*)malloc(sizeof(struct pop_target));
	if(NULL == pop)
	{
		free(pObj);
		pObj = NULL;
		return NULL;
	}
	memset(pop, 0 , sizeof(struct pop_target));
	pObj->pDerivedObj = pop;
	pthread_rwlock_init(&(pop->target_mutex), NULL);
	pop->dir_util = create_instance();
	pObj->load_business = load_pop_target;
	pObj->reload_business = reload_pop_target;
	pObj->destory_business = destory_pop_target;
	pop->m_pObj = pObj;
	return pObj;
}

bool  load_pop_target(struct business* pop, char* name, int flag, char* path)
{
	if(strcmp(name, "pop_target") != 0 )
	{
		return false;
	}
	struct pop_target * top = pop->pDerivedObj;
        if(top->m_pObj != pop)
        {   
                return false;
        } 
	//top->pTargetedPopHashMap = hash_map_new(POP_TARGET_LEN);
	g_pTargetedPopHashMap = hash_map_new(POP_TARGET_LEN);
	top->flag = flag;
	top->dir_util->set_path(top->dir_util, path);
	top->dir_util->set_length(top->dir_util, 128);
	top->dir_util->dir_init(top->dir_util);

	CFileTable ** dir_table = top->dir_util->cfTable;
	if(NULL == dir_table){return false;}
	int i = 0;
	for(; i < top->dir_util->nFileNum; i++ )
	{
		hash_map_read_file(g_pTargetedPopHashMap,dir_table[i]->szFileName);
		//rule_pop_load_file_host(g_pTargetedPopHashMap,dir_table[i]->szFileName);
	}
 	printf("path : %s, pointer : %p --> size : %d\n",path, g_pTargetedPopHashMap, g_pTargetedPopHashMap->size);	
	return true;
}

bool reload_pop_target(struct business* pop, char* name, int flag, int isAuto)
{
	struct pop_target *top = pop->pDerivedObj;
        if(top->m_pObj != pop)
        {   
                return false;
        } 
	if( (strcmp(name, "pop_target") != 0) )
	{
		return false;
	}
        if(isAuto != 0 && (flag == top->flag)) 
        {   
                return false;
        }   
        else
        {   
    
                if(!top->dir_util->dir_compare(top->dir_util))
                {   
                        return false;
                }   
        }
	HashMap* pTargetedPopHashMap = hash_map_new(POP_TARGET_LEN);
	HashMap* temp = NULL;
	top->flag = flag;
	top->dir_util->dir_reload(top->dir_util);
	
	CFileTable ** dir_table = top->dir_util->cfTable;
        int i = 0;
        for(; i < top->dir_util->nFileNum; i++)
        {
		hash_map_read_file(pTargetedPopHashMap,dir_table[i]->szFileName);
		//rule_pop_load_file_host(pTargetedPopHashMap,dir_table[i]->szFileName);
        }	
	pthread_rwlock_rdlock(&top->target_mutex);
	temp = g_pTargetedPopHashMap;
	//top->pTargetedPopHashMap = pTargetedPopHashMap;
	g_pTargetedPopHashMap = pTargetedPopHashMap;
	pTargetedPopHashMap = temp;
	pthread_rwlock_unlock(&top->target_mutex);

 	printf("pop_target reload pointer : %p --> size : %d\n", g_pTargetedPopHashMap, g_pTargetedPopHashMap->size);	
	sleep(5);
	//hash table delete
	hash_map_destory(pTargetedPopHashMap);	
	return true;
}

void destory_pop_target(struct business* pop)
{
	struct pop_target* top = pop->pDerivedObj;
	assert(NULL != pop);
	top->dir_util->destroy_instance(top->dir_util);
	hash_map_destory(g_pTargetedPopHashMap);
	pthread_rwlock_destroy(&top->target_mutex );		
	free(top);
	top = NULL;
	free(pop);
	pop = NULL;
}
