CC := gcc
CFLAGS := -g -W -Wall -O 
LINK := $(CC)

PFRINGDIR  =    ../libs/PF_RING-5.6.1/userland/lib
PCAPDIR    =    ../libs/PF_RING-5.6.1/userland/libpcap-1.1.1-ring
PFRING_KERNEL = ../libs/PF_RING-5.6.1/kernel

ZMQDIR = ../libs/zeromq-4.0.3
CZMQDIR = ../libs/czmq-2.0.3
REDISDIR = ../libs/hiredis

ALL_INCS := -I. -I${PFRING_KERNEL} -I${REDISDIR} -I${PFRING_KERNEL}/plugins -I${PFRINGDIR} -I${PCAPDIR} -I${ZMQDIR}/include -I${CZMQDIR}/include

LIBPATH := -L${ZMQDIR}/libs/ -L/usr/local/lib/ -L${CZMQDIR}/src/.libs/
LIBS := ${ZMQDIR}/src/.libs/libzmq.so.3.1.0 ${REDISDIR}/libhiredis.so -lczmq -lpthread ${PFRINGDIR}/libpfring.a ${PCAPDIR}/libpcap.a -lrt

DC_OBJS   := dc_conf_monitor.o mq_client_jc.o filter_rule_record_url.o tool_redis_helper.o dc_conf_load.o filter_rule_jc.o zz_config.o pkt_sender.o dc_global.o pkt_capture_offline.o mq_client_write_file.o filter_rule_ip_learn.o filter_rule_ip_learn_mgr.o filter_rule_cookies_filter.o tool_hash_map.o filter_rule_host_filter.o filter_rule_host_learn_mgr.o filter_rule_host_learn.o filter_rule_main.o filter_rule_pop.o filter_rule_tcp_reassemble.o filter_rule_user_clean.o mq_client_popwin.o mq_client_storm.o pkt_capture_dna.o pkt_capture_zero.o pkt_parser.o tool_write_file.o dc_main.o mq_client_record.o tool_hash_map_new.o mq_client_mobile.o

httpdc_client: $(DC_OBJS)
		$(LINK) -g -o httpdc_client $(DC_OBJS) $(LIBPATH) $(LIBS)
		cp httpdc_client  ../bin_radius/

all: httpdc

dc_global.o: dc_global.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) dc_global.c
dc_main.o: dc_main.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) dc_main.c
dc_test.o: dc_test.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) dc_test.c
filter_rule_host_filter.o: filter_rule_host_filter.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) filter_rule_host_filter.c
filter_rule_host_learn_mgr.o: filter_rule_host_learn_mgr.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) filter_rule_host_learn_mgr.c
filter_rule_host_learn.o: filter_rule_host_learn.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) filter_rule_host_learn.c
filter_rule_main.o: filter_rule_main.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) filter_rule_main.c
filter_rule_pop.o: filter_rule_pop.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) filter_rule_pop.c
filter_rule_tcp_reassemble.o: filter_rule_tcp_reassemble.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) filter_rule_tcp_reassemble.c
filter_rule_user_clean.o: filter_rule_user_clean.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) filter_rule_user_clean.c
mq_client_popwin.o: mq_client_popwin.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) mq_client_popwin.c
mq_client_storm.o: mq_client_storm.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) mq_client_storm.c
pkt_capture_dna.o: pkt_capture_dna.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) pkt_capture_dna.c
pkt_capture_zero.o: pkt_capture_zero.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) pkt_capture_zero.c
pkt_parser.o: pkt_parser.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) pkt_parser.c
tool_write_file.o: tool_write_file.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) tool_write_file.c
filter_rule_cookies_filter.o: filter_rule_cookies_filter.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) filter_rule_cookies_filter.c
tool_hash_map.o: tool_hash_map.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) tool_hash_map.c
filter_rule_ip_learn.o: filter_rule_ip_learn.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) filter_rule_ip_learn.c
filter_rule_ip_learn_mgr.o: filter_rule_ip_learn_mgr.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) filter_rule_ip_learn_mgr.c
pkt_capture_offline.o: pkt_capture_offline.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) pkt_capture_offline.c
mq_client_write_file.o: mq_client_write_file.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) mq_client_write_file.c
pkt_sender.o: pkt_sender.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) pkt_sender.c
zz_config.o: zz_config.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) zz_config.c
filter_rule_jc.o: filter_rule_jc.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) filter_rule_jc.c
dc_conf_load.o: dc_conf_load.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) dc_conf_load.c
dc_conf_monitor.o: dc_conf_monitor.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) dc_conf_monitor.c
tool_redis_helper.o: tool_redis_helper.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) tool_redis_helper.c
mq_client_jc.o: mq_client_jc.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) mq_client_jc.c
filter_rule_record_url.o: filter_rule_record_url.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) filter_rule_record_url.c
mq_client_record.o: mq_client_record.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) mq_client_record.c
tool_hash_map_new.o: tool_hash_map_new.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) tool_hash_map_new.c
mq_client_mobile.o: mq_client_mobile.c
		$(CC) -c $(CFLAGS) $(ALL_INCS) mq_client_mobile.c
clean:
		rm -rf $(DC_OBJS) $(ZERO_OBJS) $(TEST_OBJS) httpdc_client


