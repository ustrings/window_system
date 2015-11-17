#include "pop_target1_business.h"
extern HashMap* g_pTargetedPopHashMap1;

struct business* new_pop_target1()
{
	struct business* pObj = (struct business*)malloc(sizeof(struct business));
	if(NULL == pObj)
	{
		printf("malloc pop_target1 business failed\n");
		return NULL;
	}
	struct pop_target1* pop = (struct pop_target1*)malloc(sizeof(struct pop_target1));
	if(NULL == pop)
	{
		printf("malloc pop_target1 failed\n");
		free(pObj);
		pObj = NULL;
		return NULL;
	}
	memset(pop, 0 , sizeof(struct pop_target1));
	pObj->pDerivedObj = pop;
	pthread_rwlock_init(&(pop->target1_mutex), NULL);
	pop->dir_util = create_instance();
	pObj->load_business = load_pop_target1;
	pObj->reload_business = reload_pop_target1;
	pObj->destory_business = destory_pop_target1;
	pop->m_pObj = pObj;
	return pObj;
}

bool  load_pop_target1(struct business* pop, char* name, int flag, char* path)
{
	struct pop_target1 * top = pop->pDerivedObj;
        if(top->m_pObj != pop)
        {   
                return false;
        }
	if(strcmp(name, "pop_target1") != 0)
	{
		return false;
	}
	//top->pTargetedPopHashMap = hash_map_new(POP_TARGET_LEN);
	g_pTargetedPopHashMap1 = hash_map_new(POP_TARGET_LEN);
	top->flag = flag;
	top->dir_util->set_path(top->dir_util, path);
	top->dir_util->set_length(top->dir_util, 128);
	top->dir_util->dir_init(top->dir_util);

	CFileTable ** dir_table = top->dir_util->cfTable;
	if(NULL == dir_table){return false;}
	int i = 0;
	for(; i < top->dir_util->nFileNum; i++ )
	{
		hash_map_read_file(g_pTargetedPopHashMap1,dir_table[i]->szFileName);
		//rule_pop_load_file_host(g_pTargetedPopHashMap1,dir_table[i]->szFileName);
	}
 	printf("path : %s, pointer : %p --> size : %d\n",path, g_pTargetedPopHashMap1, g_pTargetedPopHashMap1->size);	
	return true;
}

bool reload_pop_target1(struct business* pop, char* name, int flag, int isAuto)
{
	struct pop_target1 *top = pop->pDerivedObj;
	if( (strcmp(name, "pop_target1") != 0) )
	{
		return false;
	}
        if(top->m_pObj != pop)
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
	HashMap* pTargetedPopHashMap1 = hash_map_new(POP_TARGET_LEN);
	HashMap* temp = NULL;
	top->flag = flag;
	top->dir_util->dir_reload(top->dir_util);
	
	CFileTable ** dir_table = top->dir_util->cfTable;
        int i = 0;
        for(; i < top->dir_util->nFileNum; i++)
        {
		hash_map_read_file(pTargetedPopHashMap1,dir_table[i]->szFileName);
		//rule_pop_load_file_host(pTargetedPopHashMap1,dir_table[i]->szFileName);
        }	
	pthread_rwlock_rdlock(&top->target1_mutex);
	temp = g_pTargetedPopHashMap1;
	//top->pTargetedPopHashMap = pTargetedPopHashMap;
	g_pTargetedPopHashMap1 = pTargetedPopHashMap1;
	pTargetedPopHashMap1 = temp;
	pthread_rwlock_unlock(&top->target1_mutex);

 	printf("poptarget1 reload pointer : %p --> size : %d\n", g_pTargetedPopHashMap1, g_pTargetedPopHashMap1->size);	
	sleep(5);
	//hash table delete
	hash_map_destory(pTargetedPopHashMap1);	
	return true;
}

void destory_pop_target1(struct business* pop)
{
	struct pop_target1* top = pop->pDerivedObj;
	assert(NULL != pop);
	top->dir_util->destroy_instance(top->dir_util);
	hash_map_destory(g_pTargetedPopHashMap1);
	pthread_rwlock_destroy(&top->target1_mutex );		
	free(top);
	top = NULL;
	free(pop);
	pop = NULL;
}
