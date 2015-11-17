CC := gcc
CFLAGS := -g -O3 -W -Wall 
LINK := $(CC)

PFRINGDIR  =    ../libs/PF_RING-5.6.1/userland/lib
PCAPDIR    =    ../libs/PF_RING-5.6.1/userland/libpcap-1.1.1-ring
PFRING_KERNEL = ../libs/PF_RING-5.6.1/kernel

ZMQDIR = ../libs/zeromq-4.0.3
CZMQDIR = ../libs/czmq-2.0.3
REDISDIR = ../libs/hiredis
JIEBADIR = ../libs/cjieba

ALL_INCS := -I. -I${REDISDIR} -I${ZMQDIR}/include -I${CZMQDIR}/include -I${JIEBADIR}/include -I${ICONVDIR}/include

LIBPATH := -L${ZMQDIR}/libs/ -L/usr/local/lib/ -L${CZMQDIR}/src/.libs/ -L${JIEBADIR}/lib/ -L${ICONVDIR}/lib/
LIBS := ${ZMQDIR}/src/.libs/libzmq.so.3.1.0 ${REDISDIR}/libhiredis.so -lczmq -lpthread -lrt -lcjieba -lm

DC_OBJS   := mq_client_jc.o filter_rule_record_url.o tool_redis_helper.o dc_conf_load.o filter_rule_jc_record.o redis_manager.o\
	     filter_rule_jc.o zz_config.o dc_global.o mq_client_write_file.o tool_hash_map.o instant_search_pop.o\
	     filter_rule_main.o filter_rule_pop.o mq_client_popwin.o mq_client_online.o  mq_message_analysis.o mq_count_redis_store.o \
	     tool_write_file.o mq_http_write_file.o base64.o data_pass_back.o filter_rule_mobile.o instant_record.o\
	     dc_server_main.o md5.o mq_client_record.o tool_hash_map_new.o mq_radius_message_analysis.o ip2address.o\
	     context_monitor.o control.o dir_process.o mthread.o pop_target_business.o surplus_url_business.o ad_black_list.o \
	     host_black_list.o host_white_list.o pop_target1_business.o dc_conf_reload.o uri_black_list.o dc_conf_jc.o dc_conf_mobile.o \
	     mq_instant_redis_store.o trans_code.o url_code.o gbk2utf8.o instant_query_content.o

httpdc_server: $(DC_OBJS)
		$(LINK) -g -o httpdc_server $(DC_OBJS) $(LIBPATH) $(LIBS)
		cp ./httpdc_server  ../bin_radius/

all: httpdc_server

dc_global.o: dc_global.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) dc_global.c
dc_server_main.o: dc_server_main.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) dc_server_main.c
filter_rule_main.o: filter_rule_main.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) filter_rule_main.c
filter_rule_pop.o: filter_rule_pop.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) filter_rule_pop.c
mq_client_popwin.o: mq_client_popwin.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) mq_client_popwin.c
mq_message_analysis.o: mq_message_analysis.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) mq_message_analysis.c
mq_radius_message_analysis.o: mq_radius_message_analysis.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) mq_radius_message_analysis.c
tool_write_file.o: tool_write_file.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) tool_write_file.c
mq_http_write_file.o: mq_http_write_file.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) mq_http_write_file.c
tool_hash_map.o: tool_hash_map.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) tool_hash_map.c
mq_client_write_file.o: mq_client_write_file.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) mq_client_write_file.c
zz_config.o: zz_config.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) zz_config.c
filter_rule_jc.o: filter_rule_jc.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) filter_rule_jc.c
filter_rule_mobile.o: filter_rule_mobile.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) filter_rule_mobile.c
dc_conf_load.o: dc_conf_load.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) dc_conf_load.c
tool_redis_helper.o: tool_redis_helper.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) tool_redis_helper.c
tool_radius_redis_helper.o: tool_radius_redis_helper.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) tool_radius_redis_helper.c
mq_client_jc.o: mq_client_jc.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) mq_client_jc.c
filter_rule_record_url.o: filter_rule_record_url.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) filter_rule_record_url.c
mq_client_record.o: mq_client_record.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) mq_client_record.c
mq_client_online.o: mq_client_online.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) mq_client_online.c
md5.o: md5.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) md5.c
tool_hash_map_new.o: tool_hash_map_new.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) tool_hash_map_new.c
ip2address.o: ip2address.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) ip2address.c
base64.o: base64.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) base64.c		
data_pass_back.o: data_pass_back.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) data_pass_back.c		
context_monitor.o: context_monitor.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) context_monitor.c		
control.o: control.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) control.c
dir_process.o: dir_process.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) dir_process.c		
mthread.o: mthread.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) mthread.c
pop_target_business.o: pop_target_business.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) pop_target_business.c		
pop_target1_business.o: pop_target1_business.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) pop_target1_business.c		
surplus_url_business.o: surplus_url_business.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) surplus_url_business.c		
ad_black_list.o: ad_black_list.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) ad_black_list.c		
host_black_list.o: host_black_list.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) host_black_list.c		
host_white_list.o: host_white_list.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) host_white_list.c		
dc_conf_reload.o: dc_conf_reload.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) dc_conf_reload.c		
uri_black_list.h.o: uri_black_list.h.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) uri_black_list.h.c		
dc_conf_jc.o: dc_conf_jc.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) dc_conf_jc.c		
dc_conf_mobile.o:dc_conf_mobile.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) dc_conf_mobile.c		
filter_rule_jc_record.o: filter_rule_jc_record.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) filter_rule_jc_record.c	
instant_search_pop.o: instant_search_pop.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) instant_search_pop.c	
instant_record.o: instant_record.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) instant_record.c	
mq_count_redis_store.o: mq_count_redis_store.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) mq_count_redis_store.c	
redis_manager.o: redis_manager.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) redis_manager.c	
mq_instant_redis_store.o: mq_instant_redis_store.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) mq_instant_redis_store.c	
trans_code.o: trans_code.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) trans_code.c	
url_code.o: url_code.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) url_code.c	
instant_query_content.o: instant_query_content.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) instant_query_content.c
gbk2utf8.o: gbk2utf8.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) gbk2utf8.c	
clean:
		rm -rf $(DC_OBJS) $(ZERO_OBJS) $(TEST_OBJS) httpdc_server


