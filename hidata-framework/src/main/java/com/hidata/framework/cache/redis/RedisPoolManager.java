package com.hidata.framework.cache.redis;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
/**
 * redis pool管理类 
 * @pj
 * */
public class RedisPoolManager {

	private static Logger logger= Logger.getLogger(RedisPoolManager.class);
	private  Configuration configure = new Configuration();
	private  JedisPool jedisPool = null;
	
    private RedisPoolManager(){
    	try{
			jedisPool = new JedisPool(configure.configure(),configure.getHost(),configure.getPort());
		}catch(Exception e){
			logger.error("Jedis Pool init failed:", e);
		}
	}
    
	private static class SingletonHolder{
		private static RedisPoolManager instance = new RedisPoolManager();
	}
	public static RedisPoolManager getInstance(){
		return SingletonHolder.instance;
	}
	/**
	 * 从连接池中获取client<br>
	 * 用完客户端，调用调用{@link releaseJedis}把资源放回池中
	 * */
	public Jedis getJedis(){
		return jedisPool.getResource();
	}
	/**
	 * 把client释放回pool
	 * */
	public void releaseJedis(Jedis jedis){
		jedisPool.returnResource(jedis);
		jedis = null;
	}
	/**
	 * 把被损坏的Jedis释放回pool
	 * */
	public void releaseBrokenJedis(Jedis jedis) {
		jedisPool.returnBrokenResource(jedis);
		jedis = null;
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		jedisPool.destroy();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RedisPoolManager pool = RedisPoolManager.getInstance();
		Jedis redis = pool.getJedis();
		
		System.out.println(redis.get("key2"));
		System.out.println(redis.dbSize());
		pool.releaseJedis(redis);
		
	}

}
