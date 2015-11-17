#include "surplus_url_business.h"
extern HashMap* g_pSurplusSiteHashMap;

struct business* new_surplus_url()
{
	struct business* pObj = (struct business*)malloc(sizeof(struct business));
	if(NULL == pObj)
	{
		printf("malloc surplus url business failed\n");
		return NULL;
	}
	memset(pObj, 0, sizeof(struct business));
	struct surplus_url* pop = (struct surplus_url*)malloc(sizeof(struct surplus_url));
	if(NULL == pop)
	{
		printf("malloc surplus url failed\n");
		free(pObj);
		pObj = NULL;
		return NULL;
	}
	memset(pop, 0 , sizeof(struct surplus_url));
	pObj->pDerivedObj = pop;
	pthread_rwlock_init(&(pop->surplus_mutex), NULL);
	pop->dir_util = create_instance();
	pObj->load_business = load_surplus_url;
	pObj->reload_business = reload_surplus_url;
	pObj->destory_business = destory_surplus_url;
	pop->m_pObj = pObj;
	return pObj;
}

bool  load_surplus_url(struct business* pop, char* name, int flag, char* path)
{
	if(strcmp(name, "surplus_url") != 0)
	{
		return false;
	}
	struct surplus_url * top = pop->pDerivedObj;
        if(top->m_pObj != pop)
        {   
                return false;
        } 
	//top->pSurplusUrlHashMap = hash_map_new(SURPLUS_URL_LEN);
	g_pSurplusSiteHashMap = hash_map_new(SURPLUS_URL_LEN);
	top->flag = flag;
	top->dir_util->set_path(top->dir_util, path);
	top->dir_util->set_length(top->dir_util, 128);
	top->dir_util->dir_init(top->dir_util);

	CFileTable ** dir_table = top->dir_util->cfTable;
	if(NULL == dir_table){return false;}
	int i = 0;
	for(; i < top->dir_util->nFileNum; i++ )
	{
		hash_map_read_file(g_pSurplusSiteHashMap,dir_table[i]->szFileName);
		//rule_pop_load_file_host(g_pSurplusSiteHashMap,dir_table[i]->szFileName);
	}
 	printf("path : %s, pointer : %p --> size : %d\n",path , g_pSurplusSiteHashMap, g_pSurplusSiteHashMap->size);	
	return true;
}

bool reload_surplus_url(struct business* pop, char* name, int flag, int isAuto)
{
	struct surplus_url *top = pop->pDerivedObj;
	if( (strcmp(name, "surplus_url") != 0) )
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

	HashMap* pSurplusUrlHashMap = hash_map_new(SURPLUS_URL_LEN);
	HashMap* temp = NULL;
	top->flag = flag;
	top->dir_util->dir_reload(top->dir_util);
	
	CFileTable ** dir_table = top->dir_util->cfTable;
        int i = 0;
        for(; i < top->dir_util->nFileNum; i++)
        {
		hash_map_read_file(pSurplusUrlHashMap,dir_table[i]->szFileName);
		//rule_pop_load_file_host(pSurplusUrlHashMap,dir_table[i]->szFileName);
        }	
	pthread_rwlock_rdlock(&top->surplus_mutex);
	temp = g_pSurplusSiteHashMap;
	//top->pSurplusUrlHashMap = pSurplusUrlHashMap;
	g_pSurplusSiteHashMap = pSurplusUrlHashMap;
	pSurplusUrlHashMap = temp;
	pthread_rwlock_unlock(&top->surplus_mutex);

 	printf("surplus url reload pointer : %p --> size : %d\n", g_pSurplusSiteHashMap, g_pSurplusSiteHashMap->size);
	sleep(5);	
	//hash table delete
	hash_map_destory(pSurplusUrlHashMap);	
	return true;
}

void destory_surplus_url(struct business* pop)
{
	struct surplus_url* top = pop->pDerivedObj;
	assert(NULL != pop);
	top->dir_util->destroy_instance(top->dir_util);
	hash_map_destory(g_pSurplusSiteHashMap);
	pthread_rwlock_destroy(&top->surplus_mutex );		
	free(top);
	top = NULL;
	free(pop);
	pop = NULL;
}
