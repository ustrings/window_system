#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include "dc_global.h"

#define nTableSize  40960
unsigned long g_tool_crypt_table[0x1000];

/*
 * init table of hash key
*/
void init_tool_crypt_table()
{
    static int called = 0;
    if(called)
        return;
    else
        called = 1;

    unsigned long seed = 0x00100001, index1 = 0, index2 = 0, i;
    for( index1 = 0; index1 < 0x100; index1++ )
    {
        for( index2 = index1, i = 0; i < 5; i++, index2 += 0x100 )
        {
            unsigned long temp1, temp2;
            seed = (seed * 125 + 3) % 0x2AAAAB;
            temp1 = (seed & 0xFFFF) << 0x10;
            seed = (seed * 125 + 3) % 0x2AAAAB;
            temp2 = (seed & 0xFFFF);
            g_tool_crypt_table[index2] = ( temp1|temp2 );
        }
    }
}

/*
 * host hash
*/
unsigned long tool_hash_table_get_hash(char *start, int len, unsigned long dwHashType)
{
    //printf("%s\n", *hostname);
    unsigned char *key  = (unsigned char *)(start);
    unsigned long seed1 = 0x7FED7FED;
    unsigned long seed2 = 0xEEEEEEEE;
    int ch;

    int i = 0;
    while(( i < len ) && (*key != '\0'))
    {
        i++;
        ch = *key++;
        seed1 = g_tool_crypt_table[(dwHashType << 8) + ch] ^ (seed1 + seed2);
        seed2 = ch + seed1 + seed2 + (seed2 << 5) + 3;
    }
    return seed1;
}

int tool_hash_table_find(struct TOOL_HASH_NODE ** tool_hash_table, char *start, int len, long * out_val)
{
    return tool_hash_table_check_append(tool_hash_table, start, len, NULL, 0, out_val);
}

int tool_hash_table_insert(struct TOOL_HASH_NODE ** tool_hash_table, char *start, int len, long * in_val)
{
    return tool_hash_table_check_append(tool_hash_table, start, len, in_val, 1, in_val);
}

void tool_hash_table_clear(struct TOOL_HASH_NODE ** tool_hash_table)
{
    memset(tool_hash_table, 0, sizeof(struct TOOL_HASH_NODE *)*nTableSize);
}

void tool_hash_table_load_file(struct TOOL_HASH_NODE ** tool_hash_table, const char * filename)
{
    FILE *fp= fopen(filename,"r");
    if(!fp) return;

    while(!feof(fp))
    {
        char wd[255]={'\0'};
        fgets(wd, 255, fp);
        if(strlen(wd) < 5) break;
        wd[strlen(wd) - 1] = '\0';
        tool_hash_table_insert(tool_hash_table, wd, strlen(wd), 0);
        //printf("tool hash table load: %s\n", wd);
    }
    fclose(fp);
}

/*
 * check by hash A B, if not exist then insert
 */
int tool_hash_table_check_append(struct TOOL_HASH_NODE ** tool_hash_table, char *start, int len, long * in_val,
        int bInsert, long * out_val)
{
    const int HASH_OFFSET = 0, HASH_A = 1, HASH_B = 2;
    unsigned int nHash  = tool_hash_table_get_hash(start, len, HASH_OFFSET);
    unsigned int nHashA = tool_hash_table_get_hash(start, len, HASH_A    );
    unsigned int nHashB = tool_hash_table_get_hash(start, len, HASH_B    );
    unsigned int nHashStart = nHash % nTableSize, nHashPos = nHashStart;

    if (tool_hash_table[nHashPos])
    {
        struct TOOL_HASH_NODE * currNode = tool_hash_table[nHashPos];
        while(currNode)
        {
            if (currNode->hashA  == nHashA && currNode->hashB == nHashB)
            {
                if(bInsert)
                {
                    if(out_val != NULL)
                        currNode->value = out_val;
                }
                else
                {
                    if(out_val != NULL)
                        *out_val = (long)currNode->value;
                }
                return nHashPos; //find the same host
            }
            currNode = currNode->next;
        }
    }

    if(bInsert)
    {
        //printf("hashtable: insert a %.*s\n", len, start);
        struct TOOL_HASH_NODE * nextNode = (struct TOOL_HASH_NODE*)malloc(sizeof(struct TOOL_HASH_NODE));
        nextNode->hashA = nHashA;
        nextNode->hashB = nHashB;
        nextNode->value = in_val;
        if (tool_hash_table[nHashPos])
        {
            nextNode->next = tool_hash_table[nHashPos]; //add host to first
            tool_hash_table[nHashPos] = nextNode;
        }
        else
        {
            nextNode->next = NULL;
            tool_hash_table[nHashPos] = nextNode;  //add a new host
        }

        return 0;
    }
    return -1;
}

/*
 * init hash table
 */
struct TOOL_HASH_NODE ** tool_hash_table_new()
{
    init_tool_crypt_table();
    struct TOOL_HASH_NODE ** tool_hash_table = (struct TOOL_HASH_NODE**)malloc(sizeof(struct TOOL_HASH_NODE *)*nTableSize);
    memset(tool_hash_table, 0, sizeof(struct TOOL_HASH_NODE *)*nTableSize);
    return tool_hash_table;
}

struct TOOL_HASH_NODE ** tool_hash_table_load_file_new(const char * filename)
{
    init_tool_crypt_table();
    struct TOOL_HASH_NODE ** tool_hash_table = (struct TOOL_HASH_NODE**)malloc(sizeof(struct TOOL_HASH_NODE *)*nTableSize);
    tool_hash_table_load_file(tool_hash_table, filename);
    return tool_hash_table;
}

