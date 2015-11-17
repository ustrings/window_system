/**
 * @brief : 通过ip定位位置, 使用纯真IP数据+KD树
 */
#ifndef __IP_SEARCH_H__
#define __IP_SEARCH_H__

#include <stdio.h>
#include <stdlib.h>

#define MAX_ADDR_LEN 256
typedef enum AddressType{
	AT_ERROR = 0,		//处理出错
	AT_NANJING = 1,		//南京
	AT_WUXI = 2,		//无锡
	AT_XUZHOU = 3,		//徐州
	AT_CHANGZHOU = 4,	//常州
	AT_SUZHOU = 5,		//苏州
	AT_NANTONG = 6,		//南通
	AT_LIANYUNGANG = 7,	//连云港
	AT_HUAIAN = 8,		//淮安
	AT_YANCHENG = 9,	//盐城
	AT_YANGZHOU = 10,	//扬州
	AT_ZHENJIANG = 11,	//镇江
	AT_TAIZHOU = 12,	//泰州
	AT_SUQIAN = 13,		//宿迁

	AT_OTHER = 9999,	//其他
	
	AT_RESERVED = 10000
} AddressType;

typedef struct IP2AddressData IP2AddressData;

/**
 * @brief 导入ip=>address的对应关系, 每个进程调用一次.
 * @param [in] conffile: 配置文件路径,可以用相对路径，例如: ../conf/ip2address.txt
 * @return int
 * 	NULL, error
 * 	IP2AddressData *, ok
 */
IP2AddressData *ip2address_init(const char *conffile);

/**
 * @brief 将ip转化为地址字符串, 线程安全
 * @param [in] *pData : IP2AddressData, 
 * @param [in] ip : const char *, ip为字符串形式, 例如: 192.168.1.110
 * @param [out] addr : char[MAX_ADDR_LEN], 输出地址
 * @return int
 * 	-1, error
 * 	0, ok
 */
int ip2address_conv(IP2AddressData *pData, const char *ip, char addr[MAX_ADDR_LEN]);

/**
 * @brief 将ip转化为地址编号, 线程安全
 * @param [in] *pData : IP2AddressData, 
 * @param [in] ip : const char *, ip为字符串形式, 例如: 192.168.1.110
 * @return AddressType
 */
AddressType ip2address_conv2(IP2AddressData *pData, const char *ip);


/**
 * @brief 释放资源, 每个进程调用一次
 */
void ip2address_fini(IP2AddressData *pData);

#endif
