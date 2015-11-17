package com.vaolan.sspserver.service.impl;

import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import com.hidata.framework.cache.redis.JedisPoolWriper;
import com.hidata.framework.util.DateUtil;
import com.vaolan.sspserver.filter.UserFilter;
import com.vaolan.sspserver.model.AdFilterElement;
import com.vaolan.sspserver.model.AdvPlan;
import com.vaolan.sspserver.service.AdRetrievalCommonService;
import com.vaolan.sspserver.service.DyAdService;
import com.vaolan.sspserver.timer.DBInfoFresh;
import com.vaolan.sspserver.util.Constant;

@Service
public class DyAdServiceImpl implements DyAdService {

	@Resource(name = "dbinfo")
	private DBInfoFresh dbInfo;

	@Autowired
	private UserFilter userFilter;
	
	//专门用来记录临时key的redis
	@Resource(name = "jedisPool20_6400")
	private JedisPoolWriper jedisPool20_6400;

	@Autowired
	private AdRetrievalCommonService adRetrievalCommonService;

	private Logger shurllogger = Logger.getLogger("shurl");
	
	
	private Logger reqinfologger = Logger.getLogger("reqinfo");

	public static void main(String[] args) {

	}

	@Override
	public AdvPlan dyAdRetrieveForShPortal(String width, String height,
			AdFilterElement adFilterElement) {

		AdvPlan advPlan = null;
		/**
		advPlan = adRetrievalCommonService.adRetrieveForSHDX(adFilterElement,
				DBInfoFresh.SH_PROTAL_CHANNEL,null);
		**/
		if(null!=advPlan){
			shurllogger.error("统一url,获取广告没获取到广告!!,请检查是否有可投放的广告");
		}

		return advPlan;

	}


	@Override
	public String genImpressionUUID(String clientIp, String url,
			String referrer, String userAgent) {
		String uuid = UUID.randomUUID().toString();

		if (StringUtils.isNotBlank(uuid)) {
			Jedis client = jedisPool20_6400.getJedis();
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
			int sec = 30;
			pl.expire(Constant.IMPRESS_ID_KEY_PRIEXL + uuid, sec);

			pl.sync();
			jedisPool20_6400.releaseJedis(client);
		}
		return uuid;
	}

	@Override
	public boolean isNeedExecStatCode(String cookieValue, String impuuid,
			String adId, String referrer) {
		boolean b = false;
		Jedis client = jedisPool20_6400.getJedis();

		// 只有此次请求在 redis 中有记录才去处理它的状态
		if (client.exists(Constant.IMPRESS_ID_KEY_PRIEXL + impuuid)) {
			Pipeline pl = client.pipelined();
			pl.del(Constant.IMPRESS_ID_KEY_PRIEXL + impuuid);
			pl.sync();
           
			boolean realImp = false;

			// referrer 为空为测试，也不需要判断直接的放过
			if (StringUtils.isNotBlank(referrer)) {

				// 广告在一个页面 展现给一个用户看，如果这个key 两次出现的间隔时间<30s 则认为是作弊，
				String key = "impfreq_" + cookieValue + "_" + referrer;

				// 当前展示，1分钟之内，被展示。
				if (client.exists(key)) {
					String lastImpTimeStr = client.get(key);
					long lastImpTime = Long.parseLong(lastImpTimeStr);
					long thisImpTime = System.currentTimeMillis();
					
					// 如果当前展示，距离上次展示已经超过30s，则认为是真实的展示，不然就是NHT
					if (thisImpTime - lastImpTime >= 30000) {
						realImp = true;
					}

					reqinfologger.info("time:"+System.currentTimeMillis()+"ua正常，但是多次请求："+key);
					// 更新这次展示的点击时间
					client.set(key, thisImpTime + "");
					client.expire(key, 60);
				} else {
					realImp = true;
					long thisImpTime = System.currentTimeMillis();
					client.set(key, thisImpTime + "");
					client.expire(key, 60);
				}
			} else {
				realImp = true;
			}
			
			b=true;
		}
		jedisPool20_6400.releaseJedis(client);
		return b;
	}

	@Override
	public String getStatCode(String pageId) {
		String statCode = "";
		Jedis client = jedisPool20_6400.getJedis();
		statCode = client.get(pageId);
		jedisPool20_6400.releaseJedis(client);
		
		return statCode;
	}

}
