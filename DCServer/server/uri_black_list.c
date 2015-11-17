#include "uri_black_list.h"
extern HashMap* g_pBlackUriHashMap;

struct business* new_black_uri()
{
	struct business* pObj = (struct business*)malloc(sizeof(struct business));
	if(NULL == pObj)
	{
		printf("printf malloc business failed\n");
		return NULL;
	}
	struct black_uri* pop = (struct black_uri*)malloc(sizeof(struct black_uri));
	if(NULL == pop)
	{
		printf("printf malloc black_uri failed\n");
		free(pObj);
		pObj = NULL;
		return NULL;
	}
	memset(pop, 0 , sizeof(struct black_uri));
	pObj->pDerivedObj = pop;
	pop->dir_util = create_instance();
	pObj->load_business = load_black_uri;
	pObj->reload_business = reload_black_uri;
	pObj->destory_business = destory_black_uri;
	pop->m_pObj = pObj;
	return pObj;
}

bool  load_black_uri(struct business* pop, char* name, int flag, char* path)
{
	if(strcmp(name, "uri_black_list") != 0)
	{
		return false;
	}
	struct black_uri * top = pop->pDerivedObj;
        if(top->m_pObj != pop)
        {   
                return false;
        }
	//top->pTargetedPopHashMap = hash_map_new(POP_TARGET_LEN);
	g_pBlackUriHashMap = hash_map_new(URI_BLACK_SUM);
	if(NULL == g_pBlackUriHashMap)
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
		hash_map_read_file(g_pBlackUriHashMap,dir_table[i]->szFileName);
	}
 	printf("path : %s, pointer : %p --> size : %d\n",path,  g_pBlackUriHashMap, g_pBlackUriHashMap->size);	
	return true;
}

bool reload_black_uri(struct business* pop, char* name, int flag)
{
	struct black_uri *top = pop->pDerivedObj;
	if( (strcmp(name, "uri_black_list") != 0) || (flag == top->flag))
	{
		return false;
	}
        if(top->m_pObj != pop)
        {   
                return false;
        }

	HashMap* pBlackUriHashMap = hash_map_new(URI_BLACK_SUM);
	HashMap* temp = NULL;
	top->flag = flag;
	top->dir_util->dir_reload(top->dir_util);
	
	CFileTable ** dir_table = top->dir_util->cfTable;
        int i = 0;
        for(; i < top->dir_util->nFileNum; i++)
        {
		hash_map_read_file(pBlackUriHashMap,dir_table[i]->szFileName);
        }	
	temp = g_pBlackUriHashMap;
	g_pBlackUriHashMap = pBlackUriHashMap;
	pBlackUriHashMap = temp;

 	printf("black_uri_list reload pointer : %p --> size : %d\n", g_pBlackUriHashMap, g_pBlackUriHashMap->size);	
	//hash table delete
	sleep(5);
	hash_map_destory(pBlackUriHashMap);	
	return true;
}

void destory_black_uri(struct business* pop)
{
	struct black_uri* top = pop->pDerivedObj;
	assert(NULL != pop);
	top->dir_util->destroy_instance(top->dir_util);
	hash_map_destory(g_pBlackUriHashMap);
	free(top);
	top = NULL;
	free(pop);
	pop = NULL;
}
