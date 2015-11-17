#include "dc_global.h"

//“∆∂ØµØ¥∞
char * g_conf_mobile_adr = "tcp://127.0.0.1:9012";
int g_conf_pop_mobile_delay = 0;
int g_conf_pop_mobile_factdelay = 0;
int g_conf_pop_mobile_max_num = 0;
int g_conf_pop_mobile_fact_num = 0;

void dc_load_pop_mobile(zzconfig_t * root)
{
	/*“∆∂ØµØ¥∞≈‰÷√œÓ*/
	g_conf_mobile_adr = zz_config_resolve(root, "pop_mobile/mobile_adr", "tcp://127.0.0.1:9010");

	char * pop_mobile_delay = zz_config_resolve(root, "pop_mobile/pop_mobile_delay", "36000");
	g_conf_pop_mobile_delay = atoi(pop_mobile_delay);
	g_conf_pop_mobile_delay = g_conf_pop_mobile_delay < 10 ? 10 :g_conf_pop_mobile_delay;
	printf("pop_mobile/pop_mobile_delay=%d\n", g_conf_pop_mobile_delay);

	char * pop_mobile_factdelay = zz_config_resolve(root, "pop_mobile/pop_mobile_factdelay", "36000");
	g_conf_pop_mobile_factdelay = atoi(pop_mobile_factdelay);
	g_conf_pop_mobile_factdelay = g_conf_pop_mobile_factdelay < 60 ? 60 :g_conf_pop_mobile_factdelay;
	printf("pop_mobile/pop_mobile_factdelay=%d\n", g_conf_pop_mobile_factdelay);

	char * pop_mobile_max_num = zz_config_resolve(root, "pop_mobile/pop_mobile_limit", "1");
	g_conf_pop_mobile_max_num = atoi(pop_mobile_max_num);
	g_conf_pop_mobile_max_num = g_conf_pop_mobile_max_num > 1000 ? 1000 :g_conf_pop_mobile_max_num;
	printf("pop_mobile/pop_mobile_max_num=%d\n", g_conf_pop_mobile_max_num);

	char * pop_mobile_fact_num = zz_config_resolve(root, "pop_mobile/pop_mobile_fact", "1");
	g_conf_pop_mobile_fact_num = atoi(pop_mobile_fact_num);
	g_conf_pop_mobile_fact_num = g_conf_pop_mobile_fact_num > 10 ? 10 :g_conf_pop_mobile_fact_num;
	printf("pop_mobile/pop_mobile_fact_num=%d\n", g_conf_pop_mobile_fact_num);

}
