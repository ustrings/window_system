#include "sender_url_list.h"
extern HashMap* g_pWhiteSiteHashMap;

struct business* new_sender_url()
{
	struct business* pObj = (struct business*)malloc(sizeof(struct business));
	if(NULL == pObj)
	{
		return NULL;
	}
	struct sender_url* pop = (struct sender_url*)malloc(sizeof(struct sender_url));
	if(NULL == pop)
	{
		free(pObj);
		pObj = NULL;
		return NULL;
	}
	memset(pop, 0 , sizeof(struct sender_url));
	pObj->pDerivedObj = pop;
	pop->dir_util = create_instance();
	pObj->load_business = load_sender_url;
	pObj->reload_business = reload_sender_url;
	pObj->destory_business = destory_sender_url;
	pop->m_pObj = pObj;
	return pObj;
}

bool  load_sender_url(struct business* pop, char* name, int flag, char* path)
{
	if(strcmp(name, "sender_url") != 0)
	{
		return false;
	}
	struct sender_url * top = pop->pDerivedObj;
	if(top->m_pObj != pop)
	{
		return false;
	}
	g_pWhiteSiteHashMap = hash_map_new(SITE_WHITE_SUM);
	top->flag = flag;
	top->dir_util->set_path(top->dir_util, path);
	top->dir_util->set_length(top->dir_util, 128);
	top->dir_util->dir_init(top->dir_util);
	CFileTable ** dir_table = top->dir_util->cfTable;
	assert(NULL != dir_table);
	int i = 0;
	for(; i < top->dir_util->nFileNum; i++ )
	{
		hash_map_read_file(g_pWhiteSiteHashMap, dir_table[i]->szFileName);
	}
 	printf("404 path : %s, pointer : %p --> size : %d\n", path , g_pWhiteSiteHashMap, g_pWhiteSiteHashMap->size);	
	return true;
}

bool reload_sender_url(struct business* pop, char* name, int flag, int isAuto)
{
	struct sender_url *top = pop->pDerivedObj;
		
	if( (strcmp(name, "sender_url") != 0))
	{
		return false;
	}
	if(isAuto != 0 && (flag == top->flag)) //0 : auto , 1 : handle
	{
		return false;
	}
	else
	{
		//auto load
		if(!top->dir_util->dir_compare(top->dir_util))
		{
			return false;
		}
	}
        if(top->m_pObj != pop)
        {   
                return false;
        }
	//HashMap* pAdWhiteHashMap = hash_map_load_file(top->szPath, AD_WHITE_LIST_LEN);
	HashMap* pWhiteSiteHashMap = hash_map_new(SITE_WHITE_SUM);
	HashMap* temp = NULL;
	top->flag = flag;
	top->dir_util->dir_reload(top->dir_util);
	CFileTable ** dir_table = top->dir_util->cfTable;
	int i = 0;
	for(; i < top->dir_util->nFileNum; i++)
	{
		hash_map_read_file(pWhiteSiteHashMap, dir_table[i]->szFileName);
	}
	
	temp = g_pWhiteSiteHashMap;
	g_pWhiteSiteHashMap = pWhiteSiteHashMap;
	pWhiteSiteHashMap = temp;

 	printf("404_url_list reload pointer : %p --> size : %d\n",  g_pWhiteSiteHashMap, g_pWhiteSiteHashMap->size);	
	//hash table delete
	sleep(5);
	hash_map_destory(pWhiteSiteHashMap);	
	return true;
}

void destory_sender_url(struct business* pop)
{
	struct sender_url* top = pop->pDerivedObj;
	assert(NULL != pop);
	hash_map_destory(g_pWhiteSiteHashMap);
	free(top);
	top = NULL;
	free(pop);
	pop = NULL;
}
