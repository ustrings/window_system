#define _GNU_SOURCE
#include "dc_global.h"
#include "ip2address.h"

extern int rule_jc_main_loop(const int iTid, char* pcMessage, const MqHttpMessage* psMqHttpMes);
extern int rule_pop_main_loop(const int iTid, char* pcMessage, const MqHttpMessage* psMqHttpMes);
extern int rule_mobile_pop_main_loop(const int iTid, char* pcMessage, const MqHttpMessage* psMqHttpMes);
extern int rule_record_url_main_loop(const int iTid, char* pcMessage, const MqHttpMessage* psMqHttpMes);
extern int rule_radius_main_loop(const int iTid, char* pcMessage, MqHttpMessage* psMqHttpMes, int **aidArray, int* len);

//IP区域投放过滤信息
IP2AddressData *g_pMainIPAreaFilterData;

/************************************************************************/
// 载入IP信息                                                                    
/************************************************************************/
void initMainIPAreaFilter()
{
	g_pMainIPAreaFilterData = ip2address_init("../conf/ip2address_utf8.txt");
	if(NULL == g_pMainIPAreaFilterData)
	{
		fprintf(stderr, "in %s : ip2address_init error\n", __FUNCTION__);
	}
}

int http_cap_filter_mobile(const int iTid, char* pcMessage, const MqHttpMessage* psMqHttpMes)
{
	if(g_conf_enable_pop_mobile)	
	{
		int ret = rule_mobile_pop_main_loop(iTid, pcMessage, psMqHttpMes);
        	if(ret > 0)
		{
            		return 0;
		}
	}

}

/*main filter loop*/
int http_cap_filter_main(const int iTid, char* pcMessage, const MqHttpMessage* psMqHttpMes)
{
	AddressType type = ip2address_conv2(g_pMainIPAreaFilterData, psMqHttpMes->pcSrcIP);
	//if (type == AT_NANJING || type == AT_WUXI || type == AT_SUZHOU || type == AT_ERROR || type == AT_RESERVED || type == AT_OTHER)
	//if (type == AT_NANJING ||type == AT_XUZHOU||type == AT_SUZHOU|| type == AT_ERROR || type == AT_RESERVED )
	if (type == AT_NANJING ||type == AT_XUZHOU|| type == AT_ERROR || type == AT_RESERVED )
	{   
		return 0;
	}
	
	//3、jc
	int ret = 0;
    if(g_conf_enable_jc)
	{
		ret = rule_jc_main_loop(iTid, pcMessage, psMqHttpMes);
		if(ret > 0)
		{
			//printf( "http_cap_filter_main Thread %d rule_jc_main_loop %f\n", thread_id, (double)(clock() - start) / CLOCKS_PER_SEC); 
			return 0;
		}
	}

    	//4 即搜即投
	if(g_conf_crowd_search)
	{
		ret = rule_pop_crowd_search_loop(iTid, pcMessage, psMqHttpMes);
		if(ret > 0)
		{
			return 0;
		}
	}

    //5、pop windows
    if  (g_conf_enable_pop_window)
    {
        	ret = rule_pop_main_loop(iTid, pcMessage, psMqHttpMes);
        	if(ret > 0)
		{
			//printf( "http_cap_filter_main Thread %d g_conf_enable_pop_window %f\n", thread_id, (double)(clock() - start) / CLOCKS_PER_SEC); 
            		return 0;
		}
    }


	//5、商品详情和购物车数据
	if (g_conf_record_url)
	{
		ret = rule_record_url_main_loop(iTid, pcMessage, psMqHttpMes);
		if(ret > 0)
		{
			//printf( "http_cap_filter_main Thread %d rule_record_url_main_loop %f\n", iTid, (double)(clock() - start) / CLOCKS_PER_SEC); 
			return 0;
		}
	}

    return 0;
}




