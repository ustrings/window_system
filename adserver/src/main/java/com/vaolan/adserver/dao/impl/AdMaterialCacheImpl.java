package com.vaolan.adserver.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import nl.bitwalker.useragentutils.UserAgent;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

import com.hidata.framework.cache.redis.RedisPoolManager;
import com.hidata.framework.util.JsonUtil;
import com.hidata.framework.util.http.RequestUtil;
import com.vaolan.adserver.dao.AdMaterialCacheDao;
import com.vaolan.adserver.model.AdMaterialCache;

@Component
public class AdMaterialCacheImpl implements AdMaterialCacheDao {

	private static Logger log = Logger.getLogger(AdMaterialCacheImpl.class);
	private RedisPoolManager pool = RedisPoolManager.getInstance();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaolan.adserver.dao.impl.AdMaterialCache#hset(java.lang.String,
	 * java.util.List)
	 */
	@Override
	public <T> void hset(String key, String field,T obj) {
		Jedis client = null;
		try {
			client = this.pool.getJedis();
			client.hset(key, field,JsonUtil.beanToJson(obj));
		} catch (Exception e) {
			if (null != client) {
				this.pool.releaseBrokenJedis(client);
			}
			log.error("redis hset operation failed:", e);
		} finally {
			if (null != client) {
				this.pool.releaseJedis(client);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaolan.adserver.dao.impl.AdMaterialCache#hset(java.lang.String,
	 * java.util.List)
	 */
	@Override
	public void hsets(String key, List<AdMaterialCache> list) {
		Jedis client = null;
		try {
			client = this.pool.getJedis();
			for (AdMaterialCache material : list) {
				client.hset(key, material.getAd_m_id(),
						JsonUtil.beanToJson(material));
			}
		} catch (Exception e) {
			if (null != client) {
				this.pool.releaseBrokenJedis(client);
			}
			log.error("redis hset operation failed:", e);
		} finally {
			if (null != client) {
				this.pool.releaseJedis(client);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vaolan.adserver.dao.impl.AdMaterialCache#hgetAll(java.lang.String)
	 */
	@Override
	public <T> T hget(String key, String field,Class<T> classType) {
		Jedis client = null;
		T adMaterial = null;
		try {
			client = this.pool.getJedis();
			if (StringUtils.isNotBlank(client.hget(key, field))) {// key存在 再取值
				adMaterial = JsonUtil.jsonToBean(client.hget(key, field),classType);
			}
		} catch (Exception e) {
			if (null != client) {
				this.pool.releaseBrokenJedis(client);
			}
			log.error("redis hgetAll operation failed:", e);
		} finally {
			if (null != client) {
				this.pool.releaseJedis(client);
			}
		}
		return adMaterial;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vaolan.adserver.dao.impl.AdMaterialCache#hgetAll(java.lang.String)
	 */
	@Override
	public List<AdMaterialCache> hgetAll(String key) {
		Jedis client = null;
		List<AdMaterialCache> list = null;
		try {
			client = this.pool.getJedis();
			if (client.exists(key)) {// key存在 再取值
				Map<String, String> fieldValue = client.hgetAll(key);
				list = new ArrayList<AdMaterialCache>(fieldValue.size());
				Iterator<String> it = fieldValue.values().iterator();
				while (it.hasNext()) {
					list.add(JsonUtil.jsonToBean(it.next(),
							AdMaterialCache.class));
				}
			}
		} catch (Exception e) {
			if (null != client) {
				this.pool.releaseBrokenJedis(client);
			}
			log.error("redis hgetAll operation failed:", e);
		} finally {
			if (null != client) {
				this.pool.releaseJedis(client);
			}
		}
		return list;
	}
	
	
	
	
	@Override
	public Set<String> smembers(String key) {
		
		Jedis client = null;
		Set<String> dynamicAdSet = new HashSet<String>();
		try {
			client = this.pool.getJedis();
			dynamicAdSet = client.smembers(key);
		} catch (Exception e) {
			if (null != client) {
				this.pool.releaseBrokenJedis(client);
			}
			log.error("redis hgetAll operation failed:", e);
		} finally {
			if (null != client) {
				this.pool.releaseJedis(client);
			}
		}
		return dynamicAdSet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vaolan.adserver.dao.impl.AdMaterialCache#isExists(java.lang.String)
	 */
	@Override
	public boolean isExists(String key) {
		boolean value = false;
		Jedis client = null;
		try {
			client = this.pool.getJedis();
			value = client.exists(key);
		} catch (Exception e) {
			if (null != client) {
				this.pool.releaseBrokenJedis(client);
			}
			log.error("redis exists operation failed:", e);
		} finally {
			if (null != client) {
				this.pool.releaseJedis(client);
			}
		}
		return value;
	}

	@Override
	public Map<String,String> hegtTerminal(String key) {
		Jedis client = null;
		Map<String,String> map = null;
		try {
			client = this.pool.getJedis();

			if (client.exists(key)) {// key存在 再取值
				map = client.hgetAll(key);
			}
		} catch (Exception e) {
			if (null != client) {
				this.pool.releaseBrokenJedis(client);
			}
			log.error("redis hgetAll operation failed:", e);
		} finally {
			if (null != client) {
				this.pool.releaseJedis(client);
			}
		}
		return map;
	}

	@Override
	public Map<String, String> getUserAngetMassage(HttpServletRequest  request) {
		Map<String, String> map = new HashMap<String, String>();
		String ua = RequestUtil.getUserAgent(request);
		UserAgent useragent = new UserAgent(ua);
		String browser = useragent.getBrowser().toString();//浏览器
		String system = useragent.getOperatingSystem().toString();//操作系统
		String device = useragent.getOperatingSystem().getDeviceType().toString();
		map.put("browser", browser);
		map.put("system", system);
		map.put("device", device);
		return map;
	}
	
	/**
	 * 添加key值 并且 设定key值的存活时间为 1分钟
	 */
	@Override
	public void add(String key, String type, String value) {
		Jedis client = null;
		try {
			client = this.pool.getJedis();
			client.hset(key, type, value);
			client.expire(key, 60);
		} catch (Exception e) {
			if(null != client){
				this.pool.releaseBrokenJedis(client);
			}
			e.printStackTrace();
		}finally{
			if(null != client){
				this.pool.releaseJedis(client);
			}
		}
	
	}

}
