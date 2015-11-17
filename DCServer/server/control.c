#include "control.h"
#include "pop_target_business.h"
#include "pop_target1_business.h"
#include "surplus_url_business.h"
#include "ad_black_list.h"
#include "host_black_list.h"
#include "host_white_list.h"
#include "dc_conf_reload.h"
#include "uri_black_list.h"
#include "instant_query_content.h"

struct control* business_init(int business_num)
{
	struct control* buss = (struct control*)malloc(sizeof(struct control));	
	if(NULL != buss)
	{
		memset(buss, 0, sizeof(struct control));
		buss->load = load;
		buss->update = update;
		buss->destory = destory;
		struct business* host_black = new_black_host();
		buss->table[buss->business_num++] = host_black;
		struct business* target = new_pop_target(); 
		buss->table[buss->business_num++] = target;
		struct business* target1 = new_pop_target1(); 
		buss->table[buss->business_num++] = target1;
		struct business* surplus = new_surplus_url();
		buss->table[buss->business_num++] = surplus;
		struct business* ad_black = new_radius_black();
		buss->table[buss->business_num++] = ad_black;
		struct business* white_black = new_white_host();
		buss->table[buss->business_num++] = white_black;
		struct business* dc_conf = new_dc_conf();
		buss->table[buss->business_num++] = dc_conf;
		struct business* black_uri = new_black_uri();
		buss->table[buss->business_num++] = black_uri;
		struct business* query_content = new_query_content();
		buss->table[buss->business_num++] = query_content;
		return buss;
	}
	else
	{
		printf("malloc struct control failed\n");
	}
	return NULL;
}

bool load(struct control* buss, char * name, int flag, char* cfg_path)
{
	if((NULL == buss)||(NULL == name)||(NULL == cfg_path))
	{
		return false;
	}
	int i = 0;
	for(; i < buss->business_num; i++)
	{
		if(NULL != buss->table[i])
		{
			if(buss->table[i]->load_business(buss->table[i],name, flag, cfg_path))
			{
				break;
			}
		}
	}
	return true;
}


bool update( struct control* buss, char* name, int flag , int isAuto)
{
	if((NULL == buss)||(NULL == name))
	{
		return NULL;
	}
	int i = 0;
	for(; i < buss->business_num; i++)
	{
		if(NULL != buss->table[i])
		{
			if(buss->table[i]->reload_business(buss->table[i], name, flag, isAuto))
			{
				break;
			}
		}
	}	
	return true;
}

void destory( struct control* buss )
{
	int i = 0;
	for(; i < buss->business_num; i++)
	{
		if(NULL != buss->table[i])
		{
			buss->table[i]->destory_business(buss->table[i]);
		}
	}
	
	if(buss)
	{
		free(buss);
		buss = NULL;
	}
}


