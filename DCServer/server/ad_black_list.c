#include "ad_black_list.h"
extern HashMap* g_pAdWhiteHashMap;

struct business* new_radius_black()
{
	struct business* pObj = (struct business*)malloc(sizeof(struct business));
	if(NULL == pObj)
	{
		return NULL;
	}
	struct radius_black* pop = (struct radius_black*)malloc(sizeof(struct radius_black));
	if(NULL == pop)
	{
		free(pObj);
		pObj = NULL;
		return NULL;
	}
	memset(pop, 0 , sizeof(struct radius_black));
	pObj->pDerivedObj = pop;
	pthread_rwlock_init(&(pop->radius_black_lock), NULL);
	pop->dir_util = create_instance();
	pObj->load_business = load_radius_black;
	pObj->reload_business = reload_radius_black;
	pObj->destory_business = destory_radius_black;
	pop->m_pObj = pObj;
	return pObj;
}

bool  load_radius_black(struct business* pop, char* name, int flag, char* path)
{
	if(strcmp(name, "ad_black_list") != 0)
	{
		return false;
	}
	struct radius_black * top = pop->pDerivedObj;
	if(top->m_pObj != pop)
	{
		return false;
	}
	g_pAdWhiteHashMap = hash_map_new(AD_WHITE_LIST_LEN);
	//g_pAdWhiteHashMap = hash_map_load_file(path, AD_WHITE_LIST_LEN);
	top->flag = flag;
	top->dir_util->set_path(top->dir_util, path);
	top->dir_util->set_length(top->dir_util, 128);
	top->dir_util->dir_init(top->dir_util);
	CFileTable ** dir_table = top->dir_util->cfTable;
	if(NULL == dir_table)
	{
		return false;
	}
	int i = 0;
	for(; i < top->dir_util->nFileNum; i++ )
	{
		hash_map_read_file(g_pAdWhiteHashMap, dir_table[i]->szFileName);
	}
 	printf("path : %s, pointer : %p --> size : %d\n", path , g_pAdWhiteHashMap, g_pAdWhiteHashMap->size);	
	return true;
}

bool reload_radius_black(struct business* pop, char* name, int flag, int isAuto)
{
	struct radius_black *top = pop->pDerivedObj;
	if( (strcmp(name, "ad_black_list") != 0) )
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
	//HashMap* pAdWhiteHashMap = hash_map_load_file(top->szPath, AD_WHITE_LIST_LEN);
	HashMap* pAdWhiteHashMap = hash_map_new(AD_WHITE_LIST_LEN);
	HashMap* temp = NULL;
	top->flag = flag;
	top->dir_util->dir_reload(top->dir_util);
	CFileTable ** dir_table = top->dir_util->cfTable;
	int i = 0;
	for(; i < top->dir_util->nFileNum; i++)
	{
		hash_map_read_file(pAdWhiteHashMap, dir_table[i]->szFileName);
	}
	
	pthread_rwlock_rdlock(&top->radius_black_lock);
	temp = g_pAdWhiteHashMap;
	g_pAdWhiteHashMap = pAdWhiteHashMap;
	pAdWhiteHashMap = temp;
	pthread_rwlock_unlock(&top->radius_black_lock);

 	printf("ad_black_list reload pointer : %p --> size : %d\n",  g_pAdWhiteHashMap, g_pAdWhiteHashMap->size);	
	//hash table delete
	sleep(5);
	hash_map_destory(pAdWhiteHashMap);	
	return true;
}

void destory_radius_black(struct business* pop)
{
	struct radius_black* top = pop->pDerivedObj;
	hash_map_destory(g_pAdWhiteHashMap);
	pthread_rwlock_destroy(&top->radius_black_lock );		
	free(top);
	top = NULL;
	free(pop);
	pop = NULL;
}
