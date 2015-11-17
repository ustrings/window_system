#include "host_white_list.h"
extern HashMap* g_pTotalWhiteSiteHashMap;
extern void hash_map_host_white_read_file(HashMap* psHashmap, const char * ccFilename);

struct business* new_white_host()
{
	struct business* pObj = (struct business*)malloc(sizeof(struct business));
	if(NULL == pObj)
	{
		return NULL;
	}
	struct white_host* pop = (struct white_host*)malloc(sizeof(struct white_host));
	if(NULL == pop)
	{
		free(pObj);
		pObj = NULL;
		return NULL;
	}
	memset(pop, 0 , sizeof(struct white_host));
	pObj->pDerivedObj = pop;
	pthread_rwlock_init(&(pop->white_host_mutex), NULL);
	pop->dir_util = create_instance();
	pObj->load_business = load_white_host;
	pObj->reload_business = reload_white_host;
	pObj->destory_business = destory_white_host;
	pop->m_pObj = pObj;
	return pObj;
}

bool load_white_host(struct business* pop, char* name, int flag, char* path)
{
	if(strcmp(name, "white_host_list") != 0)
	{
		return false;
	}
	struct white_host * top = pop->pDerivedObj;
	if(top->m_pObj != pop)
	{
		return false;
	}
	//top->pTargetedPopHashMap = hash_map_new(POP_TARGET_LEN);
	g_pTotalWhiteSiteHashMap = hash_map_new(SITE_TOTAL_WHITE_SUM);
	top->flag = flag;
	top->dir_util->set_path(top->dir_util, path);
	top->dir_util->set_length(top->dir_util, 128);
	top->dir_util->dir_init(top->dir_util);

	CFileTable ** dir_table = top->dir_util->cfTable;
	assert(NULL != dir_table);
	int i = 0;
	for(; i < top->dir_util->nFileNum; i++ )
	{
		hash_map_host_white_read_file(g_pTotalWhiteSiteHashMap, dir_table[i]->szFileName);
	}
 	printf("path : %s, pointer : %p --> size : %d\n",path,  g_pTotalWhiteSiteHashMap, g_pTotalWhiteSiteHashMap->size);	
	return true;
}

bool reload_white_host(struct business* pop, char* name, int flag, int isAuto)
{
	struct white_host *top = pop->pDerivedObj;
	if( (strcmp(name, "white_host_list") != 0) || top->m_pObj != pop)
	{
		return false;
	}
    if(isAuto != 0 && (flag == top->flag) ) 
    {  //0 : auto , 1 : handle 
        return false;
    }   
    else
    {   
		if(!top->dir_util->dir_compare(top->dir_util))
		{   
			return false;
		}   
    }
	HashMap* pTotalWhiteSiteHashMap = hash_map_new(SITE_TOTAL_WHITE_SUM);
	HashMap* temp = NULL;
	top->flag = flag;
	top->dir_util->dir_reload(top->dir_util);
	
	CFileTable ** dir_table = top->dir_util->cfTable;
    int i = 0;
    for(; i < top->dir_util->nFileNum; i++)
    {
		hash_map_host_white_read_file(pTotalWhiteSiteHashMap,dir_table[i]->szFileName);
    }	
	pthread_rwlock_rdlock(&top->white_host_mutex);
	temp = g_pTotalWhiteSiteHashMap;
	//top->pTargetedPopHashMap = pTargetedPopHashMap;
	g_pTotalWhiteSiteHashMap = pTotalWhiteSiteHashMap;
	pTotalWhiteSiteHashMap = temp;
	pthread_rwlock_unlock(&top->white_host_mutex);

 	printf("white_host_list reload pointer : %p --> size : %d\n", g_pTotalWhiteSiteHashMap, g_pTotalWhiteSiteHashMap->size);	
	//hash table delete
	sleep(3);
	hash_map_destory(pTotalWhiteSiteHashMap);	
	return true;
}

void destory_white_host(struct business* pop)
{
	struct white_host* top = pop->pDerivedObj;
	assert(NULL != pop);
	top->dir_util->destroy_instance(top->dir_util);
	hash_map_destory(g_pTotalWhiteSiteHashMap);
	pthread_rwlock_destroy(&top->white_host_mutex );		
	free(top);
	top = NULL;
	free(pop);
	pop = NULL;
}
