package com.hidata.ad.web.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;

import com.hidata.ad.web.dao.AdMaterialCacheDao;
import com.hidata.ad.web.model.AdMaterialCache;
import com.hidata.framework.cache.redis.RedisPoolManager;
import com.hidata.framework.util.JsonUtil;


@Repository
public class AdMaterialCacheImpl implements AdMaterialCacheDao {

	private RedisPoolManager pool = RedisPoolManager.getInstance();
	
	/* (non-Javadoc)
	 * @see com.vaolan.adserver.dao.impl.AdMaterialCache#hset(java.lang.String, java.util.List)
	 */
	@Override
	public void hset(String key,List<AdMaterialCache> list){
		Jedis client = null;
		try{
			client = this.pool.getJedis();
			for(AdMaterialCache material:list){
				client.hset(key,material.getAd_m_id(), JsonUtil.beanToJson(material));
			}
		}catch(Exception e){
			if(null != client){
				this.pool.releaseBrokenJedis(client);
			}
		}finally{
			if(null != client){
				this.pool.releaseJedis(client);
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.vaolan.adserver.dao.impl.AdMaterialCache#hgetAll(java.lang.String)
	 */
	@Override
	public List<AdMaterialCache> hgetAll(String key){
		Jedis client = null;
		List<AdMaterialCache> list = null;
		try{
			client = this.pool.getJedis();
			Map<String,String> fieldValue = client.hgetAll(key);
			list = new ArrayList<AdMaterialCache>(fieldValue.size());
			Iterator<String> it = fieldValue.values().iterator();
			while(it.hasNext()){
				list.add(JsonUtil.jsonToBean(it.next(),AdMaterialCache.class));
			}
		}catch(Exception e){
			if(null != client){
				this.pool.releaseBrokenJedis(client);
			}
		}finally{
			if(null != client){
				this.pool.releaseJedis(client);
			}
		}
		return list;
	}
	/* (non-Javadoc)
	 * @see com.vaolan.adserver.dao.impl.AdMaterialCache#isExists(java.lang.String)
	 */
	@Override
	public boolean isExists(String key){
		boolean value = false;
		Jedis client = null;
		try{
			client = this.pool.getJedis();
			value = client.exists(key);
		}catch(Exception e){
			if(null != client){
				this.pool.releaseBrokenJedis(client);
			}
		}finally{
			if(null != client){
				this.pool.releaseJedis(client);
			}
		}
		return value;
	}
	
	/**
	 * 删除某个key
	 */
	@Override
	public void del(String key) {

		Jedis client = null;
		try{
			client = this.pool.getJedis();
			client.del(key);
		}catch(Exception e){
			if(null != client){
				this.pool.releaseBrokenJedis(client);
			}
		}finally{
			if(null != client){
				this.pool.releaseJedis(client);
			}
		}
	}
	@Override
	public void add(String key, String type, String value) {
		Jedis client = null;
		try {
			client = this.pool.getJedis();
			client.hset(key, type, value);
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
