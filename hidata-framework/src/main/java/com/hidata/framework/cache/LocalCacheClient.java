package com.hidata.framework.cache;

import java.util.HashMap;

import com.hidata.framework.log.LogManager;

import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.exception.MemcachedException;

/**
 * 模拟缓存类，方便在本机调试
 * @author Li Hongxu 2012-04-15
 *
 */
public class LocalCacheClient {
	
	private static HashMap<String,Object> map;
	
	private static LocalCacheClient instance;
	
	// log
	private static LogManager logger = LogManager.getLogger(CBaseClient.class);
	// log key
	private static final String LOG_KEY = "CacheManager";
	
	private LocalCacheClient(){
		this.map=new HashMap<String,Object>();
	}
	
	public static LocalCacheClient getInstance(){
		if(instance==null){
			return new LocalCacheClient();
		}
		else{
			return instance;
		}
	}
	
	/**
	 * 模拟set操作，本地调试不考虑过期时间
	 * @param key
	 * @param exp
	 * @param value
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public final boolean set(final String key, final int exp, final Object value) throws TimeoutException,InterruptedException,MemcachedException{
		Object o=this.map.put(key, value);
		return o!=null?true:false;
	}
	
	/**
	 * 模拟get操作
	 * @param key
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public Object get(final String key)throws TimeoutException,InterruptedException,MemcachedException{
		return map.get(key);
		
	}
	
	public static LocalCacheClient getClient(){
		if(null == map){
			logger.error(LOG_KEY, "CouchbaseClient is null and will return it!");
		}
		return getInstance();
	}

}
