package com.vaolan.adserver.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import com.hidata.framework.cache.redis.RedisUtil;
import com.hidata.framework.util.DateUtil;
import com.vaolan.adserver.dao.AdMaterialCacheDao;
import com.vaolan.adserver.dao.AdMaterialDao;
import com.vaolan.adserver.model.AdMaterialCache;
import com.vaolan.adserver.service.AdMaterialService;
import com.vaolan.adserver.util.Config;
import com.vaolan.adserver.util.Constant;

@Component
public class AdMaterialServiceImpl implements AdMaterialService {

	@Autowired
	private AdMaterialDao dao;
	@Autowired
	private AdMaterialCacheDao cache;

	private static Logger logger = Logger
			.getLogger(AdMaterialServiceImpl.class);

	@Override
	public <T> T findMaterialById(String adId, String meterialId,Class<T> classType) {
		T material = null;
		material = cache.hget(Constant.AD_ALL_INFO+adId, meterialId,classType);
		if (null == material) {
			material = dao.findMaterialById(adId, meterialId,classType);
			if (null != material) {
				cache.hset(Constant.AD_ALL_INFO+adId, meterialId,material);
			}
		}
		return material;
	}

	@Override
	public List<AdMaterialCache> findMaterialByAdId(String adId) {
		List<AdMaterialCache> listInCache = null;
		listInCache = cache.hgetAll(Constant.AD_ALL_INFO+adId); // 先从缓存中查找 若没有则在数据库中查找，在存放到缓存中
		if (null == listInCache) {
			List<AdMaterialCache> listMaterialInDb = dao.findMaterialByAdId(adId);
			
			List<AdMaterialCache> listExtLinkInDb = dao.findExtLinkByAdId(adId);
			
			if (null != listMaterialInDb && listMaterialInDb.size() > 0) {
				listInCache = listMaterialInDb;
				
			}else{
				listInCache = listExtLinkInDb;
			}
			
			cache.hsets(Constant.AD_ALL_INFO+adId, listInCache);
			
		}
		return listInCache;
	}

	
	@Override
	public String genImpressionUUID(String clientIp, String url,
			String referrer, String userAgent) {
		String uuid = UUID.randomUUID().toString();

		if (StringUtils.isNotBlank(uuid)) {
			Jedis client = RedisUtil.getJedisClientFromPool();
			Pipeline pl = client.pipelined();

			pl.hset(Constant.IMPRESS_ID_KEY_PRIEXL + uuid, "ip",
					clientIp == null ? "" : clientIp);
			pl.hset(Constant.IMPRESS_ID_KEY_PRIEXL + uuid, "user-agent",
					userAgent == null ? "" : userAgent);
			pl.hset(Constant.IMPRESS_ID_KEY_PRIEXL + uuid, "ts",
					DateUtil.getCurrentDateTimeStr());
			pl.hset(Constant.IMPRESS_ID_KEY_PRIEXL + uuid, "url",
					url == null ? "" : url);
			pl.hset(Constant.IMPRESS_ID_KEY_PRIEXL + uuid, "referrer",
					referrer == null ? "" : referrer);

			// 可能有爬虫，只是访问了url，增加一个imp_uuid 但是没有执行里面的htmlcode，导致imp_uuid永远不能删除，
			// 积累的，越来越多，这时候就设置一个自动删除的时间
			if (StringUtils.isNotBlank(Config.getProperty("imp_uuid_expire"))) {
				int sec = Integer.parseInt(Config
						.getProperty("imp_uuid_expire"));
				pl.expire(Constant.IMPRESS_ID_KEY_PRIEXL + uuid, sec);
			}

			pl.sync();
			RedisUtil.releaseJedisClientFromPool(client);
		}
		return uuid;
	}

	@Override
	public boolean isNeedExecStatCode(String impuuid) {
		boolean b = false;
		Jedis client = RedisUtil.getJedisClientFromPool();

		if (client.exists(Constant.IMPRESS_ID_KEY_PRIEXL + impuuid)) {
			b = true;
			Pipeline pl = client.pipelined();
			pl.del(Constant.IMPRESS_ID_KEY_PRIEXL + impuuid);
			pl.sync();
		}

		RedisUtil.releaseJedisClientFromPool(client);
		return b;
	}

	@Override
	public void processNHTstat(String adId, String isHaveNHTStat) {
		Jedis client = RedisUtil.getJedisClientFromPool();

		// 如果NHT模式启用，则NHT请求+1，后续有timer 定时判断NHT请求是否达到 设定的量，如果达到会做处理
		if (client.exists(Constant.NHT_STAT_PATTERN_PRIEXL + adId)
				&& "1".equals(isHaveNHTStat)) {
			logger.debug("广告预设的NHT流量记录，key: "
					+ Constant.NHT_STAT_PATTERN_PRIEXL + adId);
			Pipeline pl = client.pipelined();
			pl.hincrBy(Constant.NHT_STAT_PATTERN_PRIEXL + adId, "pvNum", 1);
			pl.sync();
		}

		RedisUtil.releaseJedisClientFromPool(client);
	}

	@Override
	public String getJsVersion() {
		String jsVersion = "";
		Jedis client = RedisUtil.getJedisClientFromPool();

		jsVersion = client.get(Constant.JS_VERSION);
		RedisUtil.releaseJedisClientFromPool(client);

		if (StringUtils.isBlank(jsVersion)) {
			jsVersion = "1";
		}

		return jsVersion;
	}

	@Override
	/**
	 * 根据广告ID获取终端信息
	 */
	public Map<String, String> findValueByAdIdAndType(String adId) {
		String key = "ad_term_black_" + adId;
		return cache.hegtTerminal(key);
	}

	/**
	 * 获取用户终端信息
	 */
	@Override
	public Map<String, String> getUserAgentMassage(HttpServletRequest request) {
		return cache.getUserAngetMassage(request);
	}

	/**
	 * 根据key只判断是否存在value
	 */
	public Boolean getValueByKey(String key) {
		return cache.isExists(key);
	}

	@Override
	public void add(String key, String type, String value) {
		cache.add(key, type, value);
	}

	@Override
	public String findAdDynamicMaterial() {
		
		String adId = null;
		
		Set<String> adIdsSet = cache.smembers(Constant.DYNAMIC_ADS);

		if(adIdsSet !=null && adIdsSet.size()>0){
			Object[] adIds = adIdsSet.toArray();

			java.util.Random r = new java.util.Random();
		    adId = adIds[r.nextInt(adIds.length)] + "";
		}

		return adId;
	}
}
