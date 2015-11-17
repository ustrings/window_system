#include "control.h"
#include "sender_url_list.h"

struct control* business_init(int business_num)
{
	struct control* buss = (struct control*)malloc(sizeof(struct control));	
	if(NULL != buss)
	{
		memset(buss, 0, sizeof(struct control));
		buss->load = load;
		buss->update = update;
		buss->destory = destory;
		struct business* sender_url = new_sender_url();
		buss->table[buss->business_num++] = sender_url;
		
		return buss;
	}
	return NULL;
}

void load(struct control* buss, char * name, int flag, char* cfg_path)
{
	assert(NULL != buss);
	assert(NULL != name);
	assert(NULL != cfg_path);
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
}


void update( struct control* buss, char* name, int flag , int isAuto)
{
	assert(NULL != buss);
	assert(NULL != name);
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


