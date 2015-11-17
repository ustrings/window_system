#include "host_black_list.h"
extern HashMap* g_pBlackSiteHashMap;

struct business* new_black_host()
{
	struct business* pObj = (struct business*)malloc(sizeof(struct business));
	if(NULL == pObj)
	{
		printf("printf malloc business failed\n");
		return NULL;
	}
	struct black_host* pop = (struct black_host*)malloc(sizeof(struct black_host));
	if(NULL == pop)
	{
		printf("printf malloc black_host failed\n");
		free(pObj);
		pObj = NULL;
		return NULL;
	}
	memset(pop, 0 , sizeof(struct black_host));
	pObj->pDerivedObj = pop;
	pthread_rwlock_init(&(pop->black_host_mutex), NULL);
	pop->dir_util = create_instance();
	pObj->load_business = load_black_host;
	pObj->reload_business = reload_black_host;
	pObj->destory_business = destory_black_host;
	pop->m_pObj = pObj;
	return pObj;
}

bool  load_black_host(struct business* pop, char* name, int flag, char* path)
{
	if(strcmp(name, "black_host_list") != 0)
	{
		return false;
	}
	struct black_host * top = pop->pDerivedObj;
        if(top->m_pObj != pop)
        {   
                return false;
        }
	//top->pTargetedPopHashMap = hash_map_new(POP_TARGET_LEN);
	g_pBlackSiteHashMap = hash_map_new(SITE_BLACK_SUM);
	if(NULL == g_pBlackSiteHashMap)
	{
		printf("hash map new failed\n");
		return ;
	}
	top->flag = flag;
	top->dir_util->set_path(top->dir_util, path);
	top->dir_util->set_length(top->dir_util, 128);
	top->dir_util->dir_init(top->dir_util);

	CFileTable ** dir_table = top->dir_util->cfTable;
	if(NULL == dir_table){return false;}
	int i = 0;
	for(; i < top->dir_util->nFileNum; i++ )
	{
		hash_map_read_file(g_pBlackSiteHashMap,dir_table[i]->szFileName);
	}
 	printf("path : %s, pointer : %p --> size : %d\n",path,  g_pBlackSiteHashMap, g_pBlackSiteHashMap->size);	
	return true;
}

bool reload_black_host(struct business* pop, char* name, int flag, int isAuto)
{
	struct black_host *top = pop->pDerivedObj;
	if( (strcmp(name, "black_host_list") != 0) )
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
   		//auto == 0 : auto 
                if(!top->dir_util->dir_compare(top->dir_util))
                {   
                        return false;
                }   
        }
	HashMap* pBlackSiteHashMap = hash_map_new(SITE_BLACK_SUM);
	HashMap* temp = NULL;
	top->flag = flag;
	top->dir_util->dir_reload(top->dir_util);
	
	CFileTable ** dir_table = top->dir_util->cfTable;
        int i = 0;
        for(; i < top->dir_util->nFileNum; i++)
        {
		hash_map_read_file(pBlackSiteHashMap,dir_table[i]->szFileName);
        }	
	pthread_rwlock_rdlock(&top->black_host_mutex);
	temp = g_pBlackSiteHashMap;
	//top->pTargetedPopHashMap = pTargetedPopHashMap;
	g_pBlackSiteHashMap = pBlackSiteHashMap;
	pBlackSiteHashMap = temp;
	pthread_rwlock_unlock(&top->black_host_mutex);

 	printf("black_host_list reload pointer : %p --> size : %d\n", g_pBlackSiteHashMap, g_pBlackSiteHashMap->size);	
	//hash table delete
	sleep(5);
	hash_map_destory(pBlackSiteHashMap);	
	return true;
}

void destory_black_host(struct business* pop)
{
	struct black_host* top = pop->pDerivedObj;
	assert(NULL != pop);
	top->dir_util->destroy_instance(top->dir_util);
	hash_map_destory(g_pBlackSiteHashMap);
	pthread_rwlock_destroy(&top->black_host_mutex );		
	free(top);
	top = NULL;
	free(pop);
	pop = NULL;
}
