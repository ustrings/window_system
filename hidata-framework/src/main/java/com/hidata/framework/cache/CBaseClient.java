package com.hidata.framework.cache;

import java.io.IOException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;

import com.hidata.framework.log.LogManager;


public class CBaseClient {

	// 连接本地client
	private static final String LOCAL_URI = "127.0.0.1:11211";
	// 超时时间
	public static final int EXP_TIME = 24*60*60;
	// cbase client 实例
	private MemcachedClient client = null;
	
	// log
	private LogManager logger = LogManager.getLogger(CBaseClient.class);
	// log key
	private static final String LOG_KEY = "cbaseclient";
	
	private static CBaseClient cbaseClient;
	
	private CBaseClient(){
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(
	               AddrUtil.getAddresses (LOCAL_URI));
		try {
			client = builder.build();
		} catch (IOException e) {
			logger.error(LOG_KEY, "error while creating mencache client:" + e.getMessage());
		}
	}
	
	protected static CBaseClient getInstance(){
		if(null == cbaseClient){
			cbaseClient = new CBaseClient();
		}
		return cbaseClient;
	}
	
	/**
	 * 获取Cbase client
	 * @return cbase client
	 */
	public MemcachedClient getClient(){
		if(null == client){
			logger.error(LOG_KEY, "CouchbaseClient is null and will return it!");
		}
		return client;
	}
	
	
}
