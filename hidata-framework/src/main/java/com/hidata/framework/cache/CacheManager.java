package com.hidata.framework.cache;

import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.exception.MemcachedException;

import com.hidata.framework.log.LogManager;

/**
 * cache 操作类,管理cache，目前使用couchbase： {@link CBaseClient}
 * @author houzhaowei
 */

public class CacheManager{
	
    //线上服务器缓存
	private static final CBaseClient client = CBaseClient.getInstance();
	
	//本地缓存类，方便本机调试
	//private static final LocalCacheClient client = LocalCacheClient.getInstance();
	
	// log
	private static LogManager logger = LogManager.getLogger(CBaseClient.class);
	// log key
	private static final String LOG_KEY = "CacheManager";
	
	private CacheManager(){}
	
	
	/**
	 * 插入一条记录
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean set(String key,Object value){
		return set(key, value ,CBaseClient.EXP_TIME);
	}
	
	/**
	 * 插入一条记录
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean set(String key,Object value,int expTime){
		boolean result = false;
		try {
			client.getClient().set(key, expTime, value);
			result = true;
		} catch (TimeoutException e) {
			logger.error(LOG_KEY, "set data to cache time out:" + e.getMessage());
		} catch (InterruptedException e) {
			logger.error(LOG_KEY, "set data to cache interrupted:" + e.getMessage());
		} catch (MemcachedException e) {
			logger.error(LOG_KEY, "set data to cache exception:" + e.getMessage());
		}
		return result;
	}
	
	/**
	 * 插入一条记录
	 * @param key
	 * @param expTime 超时时间
	 * @param value
	 * @return 插入是否成功
	 */
	public static boolean set(String key,int expTime,String value){
		boolean result = false;
		try {
			client.getClient().set(key, expTime, value);
			result = true;
		} catch (TimeoutException e) {
			logger.error(LOG_KEY, "set data to cache time out:" + e.getMessage());
		} catch (InterruptedException e) {
			logger.error(LOG_KEY, "set data to cache interrupted:" + e.getMessage());
		} catch (MemcachedException e) {
			logger.error(LOG_KEY, "set data to cache exception:" + e.getMessage());
		}
		return result;
	}
	
	/**
	 * 根据key 获取内容
	 * @param key
	 * @return 获取的内容
	 */
	public static Object get(String key){
		Object obj = null;
		try {
			obj = client.getClient().get(key);
		} catch (TimeoutException e) {
			logger.error(LOG_KEY, "get data to cache time out:" + e.getMessage());
		} catch (InterruptedException e) {
			logger.error(LOG_KEY, "get data to cache interrupted:" + e.getMessage());
		} catch (MemcachedException e) {
			logger.error(LOG_KEY, "get data to cache exception:" + e.getMessage());
		}
		if(null == obj){
			logger.error(LOG_KEY, "Get Object is NUll!");
		}
		return obj;
	}
}
