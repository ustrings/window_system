#include "ip2address.h"
#include <string.h>

#define MAX_LINE_LEN 1024
#define MAX_IP_LEN 20

static int ip2int(const char *sip, unsigned int *nip);
static int ip2str(unsigned int nip, char *sip, unsigned int maxlen);
static int ipnode_cmp(const void *ipNode1, const void *ipNode2);

typedef struct IPNode{
	unsigned int ip1;
	unsigned int ip2;
	char addr[MAX_ADDR_LEN];
} IPNode;

struct IP2AddressData{
	IPNode *ipNodes;
	unsigned int size; 
	unsigned int capacity;
};

typedef struct ADDRInfo{
	const char *addr;
	AddressType type;
} ADDRInfo;

IP2AddressData *ip2address_init(const char *conffile){
	IP2AddressData *pData = (IP2AddressData *)calloc(1, sizeof(IP2AddressData));
	if(NULL == pData){
		fprintf(stderr, "in %s : allocate memory for IP2AddressData error.\n", __FUNCTION__);
		return NULL;
	}
	FILE *pFile = fopen(conffile, "r");
	if(NULL == pFile){
		fprintf(stderr, "in %s : can't open file:%s\n", __FUNCTION__, conffile);
		return NULL;
	}
	//get total num	
	char line[MAX_LINE_LEN];
	if(fgets(line, sizeof(line), pFile) == NULL){
		fprintf(stderr, "in %s : get total number line error.\n", __FUNCTION__);
		return NULL;
	}
	unsigned int total = 0;
	if(sscanf(line, "%u", &total) != 1){
		fprintf(stderr, "in %s : sscanf total number error.\n", __FUNCTION__);
		return NULL;
	}
	if(total < 1){
		fprintf(stderr, "in %s : invalid total number:%u.\n", __FUNCTION__, total);
		return NULL;
	}

	pData->capacity = total;
	pData->size = 0;
	pData->ipNodes = (IPNode *)calloc(pData->capacity, sizeof(IPNode));
	if(NULL == pData->ipNodes){
		fprintf(stderr, "in %s : allocate for pData->ipNodes error.\n", __FUNCTION__);
		return NULL;
	}	
	
	char ip1[MAX_IP_LEN];
	char ip2[MAX_IP_LEN];
	pData->size = 0;
	char space[32];
	while(fgets(line, sizeof(line), pFile)){
		IPNode *pn = &pData->ipNodes[pData->size];
		if(sscanf(line, "%s%s%[ ]%[^\r\n]", ip1, ip2, space, pn->addr) != 4){
			fprintf(stderr, "in %s : invalid ip data!\n", __FUNCTION__);
			return NULL;
		}
		if(ip2int(ip1, &pn->ip1) != 0){
			fprintf(stderr, "invalid ip:%s\n", ip1);
			return NULL;
		}
		
		if(ip2int(ip2, &pn->ip2) != 0){
			fprintf(stderr, "invalid ip:%s\n", ip2);
			return NULL;
		}
		pData->size++;

		if(pData->size % 10000 == 0){
			//fprintf(stderr, "%d ip segments processed.\n", pData->size);
		}
	}
	fprintf(stderr, "%d ip segements loaded.\n", pData->size);

	fclose(pFile);
	qsort(pData->ipNodes, pData->size, sizeof(IPNode), ipnode_cmp); 
	return pData;
}

int ip2address_conv(IP2AddressData *pData, const char *sip, char out[MAX_ADDR_LEN]){
	unsigned int nip = 0;
	if(ip2int(sip, &nip) != 0){
		fprintf(stderr, "in %s :invalid ip:%s\n", __FUNCTION__, sip);
		return -1;
	}
	unsigned int low = 0;
	unsigned int high = pData->size - 1;
	unsigned int mid = 0;
	while(low <= high){
		mid = low + (high - low) / 2;
		IPNode *pn = &pData->ipNodes[mid];
		if(pn->ip1 <= nip && nip <= pn->ip2){
			strncpy(out, pn->addr, MAX_ADDR_LEN);
			out[MAX_ADDR_LEN - 1] = '\0';
			return 0;
		}
		else if(pn->ip1 > nip){
			low = low;
			high = mid - 1;
		}
		else{	//pn->ip2 < nip
			low = mid + 1;
			high = high;
		}
	}
	return -1;
}

static ADDRInfo g_ADDR[] = {
	{"江苏省南京市", AT_NANJING},
	{"江苏省无锡市", AT_WUXI},
	{"江苏省徐州市", AT_XUZHOU},
	{"江苏省常州市", AT_CHANGZHOU},
	{"江苏省苏州市", AT_SUZHOU},
	{"江苏省南通市", AT_NANTONG},
	{"江苏省连云港", AT_LIANYUNGANG},
	{"江苏省淮安市", AT_HUAIAN},
	{"江苏省盐城市", AT_YANCHENG},
	{"江苏省扬州市", AT_YANGZHOU},
	{"江苏省镇江市", AT_ZHENJIANG},
	{"江苏省泰州市", AT_TAIZHOU},
	{"江苏省宿迁市", AT_SUQIAN}
};

AddressType ip2address_conv2(IP2AddressData *pData, const char *sip){
	char addr[MAX_ADDR_LEN];
	if(ip2address_conv(pData, sip, addr) != 0){
		return AT_ERROR;
	}
	int i = 0;
	for(; i < sizeof(g_ADDR) / sizeof(g_ADDR[0]); ++i){
		//fprintf(stderr, "%s => %s\n", addr, g_ADDR[i].addr);
		//fprintf(stderr, "%d\n", strncmp(addr, g_ADDR[i].addr, strlen(g_ADDR[i].addr)));
		if(strncmp(addr, g_ADDR[i].addr, strlen(g_ADDR[i].addr)) == 0){
			return g_ADDR[i].type;
		}
	}
	return AT_OTHER;
}

void ip2address_conv_print(IP2AddressData *pData, const char *sip){
	char addr[MAX_ADDR_LEN];
	if(ip2address_conv(pData, sip, addr) != 0){
		return;
	}
	fprintf(stdout, "%s => %s\n", sip, addr);
}

void ip2address_fini(IP2AddressData *pData){
	if(pData){
		if(pData->ipNodes){
			free(pData->ipNodes);
		}
		free(pData);
	}
}

static int ip2int(const char *sip, unsigned int *nip){
	union {
		unsigned int nip;
		unsigned char parts[4];
	} ipUN;
	int parts[4];
	if(sscanf(sip, "%d.%d.%d.%d", &parts[3], &parts[2], &parts[1], &parts[0]) != 4){
		return -1;
	}
	int i;
	for(i = 0; i < 4; ++i){
		ipUN.parts[i] = (unsigned char)parts[i];
	}
	*nip = ipUN.nip;
	return 0;
}

static int ip2str(unsigned int nip, char *sip, unsigned int maxlen){
	unsigned char *p = (unsigned char *)&nip;
	snprintf(sip, maxlen, "%d.%d.%d.%d", p[3], p[2], p[1], p[0]);
	return 0;
}

static int ipnode_cmp(const void *ipNode1, const void *ipNode2){
	IPNode *nd1 = (IPNode *)ipNode1;
	IPNode *nd2 = (IPNode *)ipNode2;
	if(nd1->ip1 < nd2->ip1){
		return -1;
	}
	else if(nd1->ip1 > nd2->ip1){
		return 1;
	}
	else{
		return 0;
	}
}
