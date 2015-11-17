package com.hidata.framework.cache.redis;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.hidata.framework.cache.redis.RedisPoolManager;
import com.hidata.framework.util.JsonUtil;

/**
 * redis操作，用到了连接池，所有不能直接new,jedis，通过这个工具类，把拿链接对象，操作，关闭链接封装在一起。
 * 
 * @author chenjinzhao
 * 
 */
public class RedisUtil {

	private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);
	private static RedisPoolManager pool = RedisPoolManager.getInstance();

	public static void hset(String key, String field, String value) {
		Jedis client = null;
		try {
			client = pool.getJedis();
			client.hset(key, field, value);
		} catch (Exception e) {
			if (null != client) {
				pool.releaseBrokenJedis(client);
			}
			logger.error("redis hset operation failed:", e);
		} finally {
			if (null != client) {
				pool.releaseJedis(client);
			}
		}
	}

	public static void sadd(String key, String... members) {
		Jedis client = null;
		try {
			client = pool.getJedis();
			client.sadd(key, members);
		} catch (Exception e) {
			if (null != client) {
				pool.releaseBrokenJedis(client);
			}
			logger.error("redis hset operation failed:", e);
		} finally {
			if (null != client) {
				pool.releaseJedis(client);
			}
		}
	}

	/**
	 * 从连接池得到一个客户端
	 * 
	 * @return
	 */
	public static Jedis getJedisClientFromPool() {
		Jedis client = null;
		try {
			client = pool.getJedis();
		} catch (Exception e) {
			if (null != client) {
				pool.releaseBrokenJedis(client);
			}
			e.printStackTrace();
			logger.error("redis hset operation failed:", e);
		}
		return client;

	}

	/**
	 * 释放客户端
	 * 
	 * @param client
	 */
	public static void releaseJedisClientFromPool(Jedis client) {
		if (null != client) {
			pool.releaseJedis(client);
		}
	}

	public static Set<String> keys(String keyPattern) {
		Jedis client = null;
		Set<String> keys = null;
		try {
			client = pool.getJedis();
			keys = client.keys(keyPattern);
		} catch (Exception e) {
			if (null != client) {
				pool.releaseBrokenJedis(client);
			}
			logger.error("redis hset operation failed:", e);
		} finally {
			if (null != client) {
				pool.releaseJedis(client);
			}
		}
		return keys;
	}

	public static String hget(String key, String field) {
		Jedis client = null;
		String value = null;
		try {
			client = pool.getJedis();
			if (client.exists(key)) {// key存在 再取值
				value = client.hget(key, field);
			}
		} catch (Exception e) {
			if (null != client) {
				pool.releaseBrokenJedis(client);
			}
			logger.error("redis hgetAll operation failed:", e);
		} finally {
			if (null != client) {
				pool.releaseJedis(client);
			}
		}
		return value;
	}

	/**
	 * 把一个vo对象，属性名字作为field，属性值作为value，插入redis
	 * 
	 * @param key
	 * @param obj
	 * @throws IOException
	 */
	public static void hsetOjbect(String key, Object obj) throws IOException {
		String jsonStr = JsonUtil.beanToJson(obj);
		JSONObject jsonObj = JSONObject.fromObject(jsonStr);
		Set<String> jsonKeys = jsonObj.keySet();
		for (String field : jsonKeys) {
			String value = jsonObj.getString(field);
			if (StringUtils.isNotBlank(value)) {
				hset(key, field, value);
			}
		}
	}

	public static Map<String, String> hgetAll(String key) {
		Jedis client = null;
		Map<String, String> fieldValue = null;
		try {
			client = pool.getJedis();
			if (client.exists(key)) {// key存在 再取值
				fieldValue = client.hgetAll(key);

			}
		} catch (Exception e) {
			if (null != client) {
				pool.releaseBrokenJedis(client);
			}
			logger.error("redis hgetAll operation failed:", e);
		} finally {
			if (null != client) {
				pool.releaseJedis(client);
			}
		}
		return fieldValue;
	}

	public static boolean isExists(String key) {
		boolean value = false;
		Jedis client = null;
		try {
			client = pool.getJedis();
			value = client.exists(key);
		} catch (Exception e) {
			if (null != client) {
				pool.releaseBrokenJedis(client);
			}
			logger.error("redis exists operation failed:", e);
		} finally {
			if (null != client) {
				pool.releaseJedis(client);
			}
		}
		return value;
	}

	public static void del(String key) {
		Jedis client = null;
		try {
			client = pool.getJedis();
			client.del(key);
		} catch (Exception e) {
			if (null != client) {
				pool.releaseBrokenJedis(client);
			}
			logger.error("redis exists operation failed:", e);
		} finally {
			if (null != client) {
				pool.releaseJedis(client);
			}
		}
	}

	public static String get(String key) {
		String value = null;
		Jedis client = null;
		try {
			client = pool.getJedis();
			value = client.get(key);
		} catch (Exception e) {
			if (null != client) {
				pool.releaseBrokenJedis(client);
			}
			logger.error("redis exists operation failed:", e);
		} finally {
			if (null != client) {
				pool.releaseJedis(client);
			}
		}
		return value;
	}

	public static void set(String key, String value) {
		Jedis client = null;
		try {
			client = pool.getJedis();
			client.set(key, value);
		} catch (Exception e) {
			if (null != client) {
				pool.releaseBrokenJedis(client);
			}
			logger.error("redis exists operation failed:", e);
		} finally {
			if (null != client) {
				pool.releaseJedis(client);
			}
		}
	}
}
