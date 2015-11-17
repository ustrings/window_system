package com.vaolan.adtimer.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.pool.impl.GenericObjectPool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 一个程序里面多连接池情况，生产多个redis连接池情况
 * @author chenjinzhao
 *
 */
public class JedisPoolFactory {

	private static Map<String, JedisPool> poolMap = new HashMap<String, JedisPool>();

	/**
	 * 得到一个redis连接池
	 * @param ip
	 * @param port
	 * @param maxActive
	 * @return
	 */
	public static JedisPool getPool(String ip, int port, int maxActive) {
		
		JedisPool pool = poolMap.get(ip+"_"+port);
		if (pool == null) {
			JedisPoolConfig config = new JedisPoolConfig();
			// 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
			// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
			config.setMaxTotal(maxActive);
			// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
			config.setMaxIdle(5);
			// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
			config.setMaxWaitMillis(1000 * 100);
			// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
			config.setTestOnBorrow(true);
			pool = new JedisPool(config, ip, port);
			poolMap.put(ip+"_"+port, pool);
		}
		return pool;
	}
	
	/**
	 * 归还链接到特定的连接池
	 * @param pool
	 * @param redis
	 */
	 public static void returnResource(JedisPool pool, Jedis redis) {
	        if (redis != null) {
	            pool.returnResource(redis);
	        }
	    }
}
