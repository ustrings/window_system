#include "dc_global.h"
#include "instant_query_content.h"

#define QUERY_CONTENT_LEN 40960
HashMap* g_pvStopHashMap = NULL;
extern HashMap* g_pQCHashMap;
struct MixSegment* g_handle ;

struct business* new_query_content()
{
	struct business* pObj = (struct business*)malloc(sizeof(struct business));
	if(NULL == pObj)
	{
		printf("malloc query_content business failed\n");
		return NULL;
	}
	memset(pObj, 0, sizeof(struct business));
	struct query_content* pop = (struct query_content*)malloc(sizeof(struct query_content));
	if(NULL == pop)
	{
		printf("malloc query_content failed\n");
		free(pObj);
		pObj = NULL;
		return NULL;
	}
	memset(pop, 0 , sizeof(struct query_content));
	pObj->pDerivedObj = pop;
	pthread_rwlock_init(&(pop->content_mutex), NULL);
	pop->dir_util = create_instance();
	pObj->load_business = load_query_content;
	pObj->reload_business = reload_query_content;
	pObj->destory_business = destory_query_content;
	pop->m_pObj = pObj;
	return pObj;
}

char * get_name(char** start , int *len)
{
        char* value = NULL;
        char* readptr = *start;
        while (isspace ((unsigned char) *readptr))
                readptr++;
        while (*readptr != '=') {
                readptr++;
        }
        if(*readptr == '=')
        {
                readptr++;
                while (isspace ((unsigned char) *readptr))
                        readptr++;
                if (*readptr == '"' || *readptr == '\'') {
                        char *endquote = strchr (readptr + 1, *readptr);
                        if(endquote)
                        {
                                *len = endquote - readptr - 1;
                                value = readptr+1;
                        }
                }
                else    
                {
                        char *endquote = strstr (readptr + 1, "\r");
                        if(!endquote)
                        {
                                endquote = strstr (readptr + 1, "\n");
                        }
                        *len = endquote - readptr - 1;
                        value = readptr;
                }
        }
        return value;
}


void rule_pop_load_query_content(HashMap* pQCHashMap, char* filePath)
{
        FILE *file = fopen (filePath, "r");
        if (!file)
                return ;

        int lineno = 0;
        char cur_line[1024] = {0};
	int iadvId = 0;
        while (fgets (cur_line, 1024, file)) {
                int length = strlen (cur_line);
                while (length && isspace ((unsigned char)cur_line [length - 1]))
                        cur_line [--length] = 0;
                char *scanner = cur_line;
                while(*scanner == ' ')
                        scanner++;
                int level = (scanner - cur_line) /4;
                int len = 0;
                char *value =  get_name(&scanner , &len);
		if(NULL == value)
		{
			printf("advid is NULL\n");
		}
                if(level == 0)
                {
			iadvId = atoi(value);
                }
		else
		{
			char dest[1024] = {0};
			UrlGB2312Decode(value,dest );
			char* result = MixSegmentCut(g_handle, dest, "-MEG-");
			if(NULL != result)
			{
				char* kbuf = result;
				char* p[120] = {NULL};
				char * outer_ptr = NULL;
				int in = 0;
				while((p[in]=strtok_r(kbuf,"-MEG-",&outer_ptr))!=NULL)
				{
					in++;
					kbuf = NULL;
				}
				int j = 0;
				for(; j< in; j++)
				{
					if(hash_map_find(g_pvStopHashMap, p[j],p[j], NULL) < 0)
					{
						AdFire* padvId = NULL;
						int ret = hash_map_find(pQCHashMap,  p[j], strlen(p[j]), (long*)&padvId);
						if(ret < 0)
						{
							padvId = (AdFire*)malloc(sizeof(AdFire));
							memset(padvId, 0, sizeof(AdFire));
							padvId->pvAdInfo[padvId->adlen].iAdid = iadvId;
							padvId->pvAdInfo[padvId->adlen].iscore = 100;
							padvId->adlen++;
							hash_map_insert(pQCHashMap, p[j], strlen(p[j]), (long*)padvId);
						}
						else
						{
							int i = 0;
							for (; i < padvId->adlen; i++)
							{
								if (iadvId == padvId->pvAdInfo[i].iAdid)
								{break;}
							}
							if (i == padvId->adlen && i < AD_MAX_LEN)
							{
								padvId->pvAdInfo[padvId->adlen].iAdid = iadvId;
								padvId->pvAdInfo[padvId->adlen].iscore = 100;
								padvId->adlen++;
							}
						}
					}
				}
			}
			free(result);
		}
        }
        fclose(file);
}

bool  load_query_content(struct business* pop, char* name, int flag, char* path)
{
	if(strcmp(name, "query_content") != 0)
	{
		return false;
	}
	struct query_content * top = pop->pDerivedObj;
        if(top->m_pObj != pop)
        {   
                return false;
        } 
	//top->pSurplusUrlHashMap = hash_map_new(SURPLUS_URL_LEN);
	g_handle = NewMixSegment(DICT_PATH, HMM_PATH, USER_DICT);
	g_pQCHashMap = hash_map_new(QUERY_CONTENT_LEN);
	g_pvStopHashMap = load_nolen_limit_hash_map(STOP_DICT , 40960);
	top->flag = flag;
	top->dir_util->set_path(top->dir_util, path);
	top->dir_util->set_length(top->dir_util, 128);
	top->dir_util->dir_init(top->dir_util);

	CFileTable ** dir_table = top->dir_util->cfTable;
	if(NULL == dir_table){return false;}
	int i = 0;
	for(; i < top->dir_util->nFileNum; i++ )
	{
		printf("%s\n", dir_table[i]->szFileName);
		rule_pop_load_query_content(g_pQCHashMap, dir_table[i]->szFileName);
		//rule_pop_load_file_host(g_pSurplusSiteHashMap,dir_table[i]->szFileName);
	}
 	printf("path : %s, pointer : %p --> size : %d\n",path , g_pQCHashMap, g_pQCHashMap->size);	
	return true;
}

bool reload_query_content(struct business* pop, char* name, int flag, int isAuto)
{
	struct query_content *top = pop->pDerivedObj;
	if( (strcmp(name, "query_content") != 0) )
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

	HashMap* pQCHashMap = hash_map_new(QUERY_CONTENT_LEN);
	HashMap* temp = NULL;
	top->flag = flag;
	top->dir_util->dir_reload(top->dir_util);
	
	CFileTable ** dir_table = top->dir_util->cfTable;
        int i = 0;
        for(; i < top->dir_util->nFileNum; i++)
        {
		rule_pop_load_query_content(pQCHashMap, dir_table[i]->szFileName);
		//rule_pop_load_file_host(pSurplusUrlHashMap,dir_table[i]->szFileName);
        }	
	pthread_rwlock_rdlock(&top->content_mutex);
	temp = g_pQCHashMap;
	//top->pSurplusUrlHashMap = pSurplusUrlHashMap;
	g_pQCHashMap = pQCHashMap;
	pQCHashMap = temp;
	pthread_rwlock_unlock(&top->content_mutex);

 	printf("query_content reload pointer : %p --> size : %d\n", g_pQCHashMap, g_pQCHashMap->size);
	sleep(5);	
	//hash table delete
	hash_map_destory(pQCHashMap);	
	return true;
}

void destory_query_content(struct business* pop)
{
	struct query_content* top = pop->pDerivedObj;
	assert(NULL != pop);
	top->dir_util->destroy_instance(top->dir_util);
	hash_map_destory(g_pQCHashMap);
	pthread_rwlock_destroy(&top->content_mutex );		
	free(top);
	top = NULL;
	free(pop);
	pop = NULL;
}
