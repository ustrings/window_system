package com.vaolan.sspserver.timer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;

import com.hidata.framework.cache.redis.JedisPoolWriper;
import com.vaolan.sspserver.model.AdvPlan;
import com.vaolan.sspserver.model.AdvWareHouse;
import com.vaolan.sspserver.service.AdPlanTotalInfoCom;
import com.vaolan.sspserver.util.Constant;

/**
 * 
 */

public class DBInfoFresh {
	private static Logger logger = Logger.getLogger(DBInfoFresh.class);
	
	private static Logger dbfreshlogger = Logger.getLogger("dbfresh");


	/**
	 * 诏兰自己的广告 2
	 */
	public static String PUSH_CHANNEL = "2";
	
	/**
	 * 青稞视频 6
	 */
	public static String QINGKOO_VIDEO_CHANNEL = "6";
	
	/**
	 * 上海视频 7
	 */
	public static String SH_PROTAL_CHANNEL="7";

	/**
	 * 我们自己的弹窗广告chann = 2
	 */
	private Map<String, AdvPlan> advPlanMap2 = null;
	
	
	private Map<String, AdvPlan> advPlanMap6 = null;
	
	
	private Map<String, AdvPlan> advPlanMap7 = null;
	
	/**
	 * 存放 关键词对应广告 id
	 */
	private Map<String, Set<String>> adsKeywordMap = new HashMap<String, Set<String>>();

	private AdvWareHouse advWareHouse = new AdvWareHouse();

	@Resource(name = "jedisPool_adstat")
	private JedisPoolWriper jedisPool;

	@Autowired
	private AdPlanTotalInfoCom adPlanTotalInfo;

	public DBInfoFresh() {
		logger.info("....DBInfoFresh构造函数执行...");
		advPlanMap2 = new HashMap<String, AdvPlan>();
		
		advPlanMap6 = new HashMap<String, AdvPlan>();
		
		advPlanMap7 = new HashMap<String, AdvPlan>();
		// DBInfoFresh.CHANNEL = channel;
	}

	/**
	 * 
	 * */
	@PostConstruct
	public void init() {
		logger.info("对象生成后，初始化AdvInfo....");
		getPvCount();
		freshAdvInfo();
		logger.info("...AdvInfo初始化完毕...");
	}

	public AdvWareHouse getAdvWareHouse() {
		return advWareHouse;
	}

	public void setAdvWareHouse(AdvWareHouse advWareHouse) {
		this.advWareHouse = advWareHouse;
	}

	/**
	 * 获取广告计划当前分钟的pvcount
	 * */
	public void getadMinMap() {
		
		long start = System.currentTimeMillis();
		
		Jedis client = jedisPool.getJedis();
		Map<String,String>  adMinMap = client.hgetAll(Constant.AD_MIN_NUM);
		advWareHouse.setAdMinMap(adMinMap);
		jedisPool.releaseJedis(client);

		long end = System.currentTimeMillis();
		long time = end-start;
		
		dbfreshlogger.info("adMinMap fresh" + adMinMap.toString());
		
		dbfreshlogger.info("########adMinMap fresh,用时:" + time);
	}
	
	/**
	 * 获取广告计划 pvCount
	 * */
	public void getPvCount() {
		
		long start = System.currentTimeMillis();
		Jedis client = jedisPool.getJedis();
		String dayPvCountInfo = client.get(Constant.REDIS_PVCOUNT);
		advWareHouse.initDayPvCount(dayPvCountInfo);
		jedisPool.releaseJedis(client);
		
		long end = System.currentTimeMillis();
		
		long time = end-start;
		dbfreshlogger.info("AdPvCountJob pvCountInfo: 用时："+time);
	}
	
	/**
	 * 获取当前的广告的投放 pv 量的 字符串结果
	 * @return
	 */
	public String getPvCountStr() {
		Jedis client = jedisPool.getJedis();
		String dayPvCountInfo = client.get(Constant.REDIS_PVCOUNT);
		advWareHouse.initDayPvCount(dayPvCountInfo);
		jedisPool.releaseJedis(client);
		return dayPvCountInfo;
	}

	/**
	 * 获取系统所有弹窗的广告信息c=
	 * */
	public void freshAdvInfo() {
		logger.info("starting freshAdvInfo....");
		dbfreshlogger.info("starting freshAdvInfo....");
		
		advPlanMap2.clear();
		advPlanMap6.clear();
		advPlanMap7.clear();
		adsKeywordMap.clear();

		adPlanTotalInfo.getAdvPlans(advPlanMap2, DBInfoFresh.PUSH_CHANNEL);
		adPlanTotalInfo.getAdvPlans(advPlanMap6, DBInfoFresh.QINGKOO_VIDEO_CHANNEL);
		adPlanTotalInfo.getAdvPlans(advPlanMap7, DBInfoFresh.SH_PROTAL_CHANNEL);
		
		// 首次初始化初始化的是直接初始化的
		if(adsKeywordMap.size() <= 0) {
			adPlanTotalInfo.getAdKeywordsvPlans(adsKeywordMap, DBInfoFresh.PUSH_CHANNEL);
		} else {
			// 刷新初始化先初始化一个新 map 之后再把引用返回给老的 map
			Map<String, Set<String>> new_adsKeywordMap = new HashMap<String, Set<String>>();
			adPlanTotalInfo.getAdKeywordsvPlans(new_adsKeywordMap, DBInfoFresh.PUSH_CHANNEL);
			adsKeywordMap = new_adsKeywordMap;
		}
		
		/**
		for (String adId : advPlanMap6.keySet()) {
			logger.info("channel:6 -------青稞视频------- " + adId + " name : " + advPlanMap6.get(adId).getAdInstance().getAdName());
			dbfreshlogger.info("channel:6 -------青稞视频------- " + adId + " name : " + advPlanMap6.get(adId).getAdInstance().getAdName());
		}*/

		
		/**
		for (String adId : advPlanMap7.keySet()) {
			logger.info("channel:7 -------上海门户------- " + adId + " name : " + advPlanMap7.get(adId).getAdInstance().getAdName());
			dbfreshlogger.info("channel:7 -------上海门户------- " + adId + " name : " + advPlanMap7.get(adId).getAdInstance().getAdName());
		}*/

		for (String adId : advPlanMap2.keySet()) {
			
			AdvPlan advPlan = advPlanMap2.get(adId);
			String  adType = advPlan.getAdInstance().getAdType();
			if("0".equals(adType)){
				logger.info("channel:2---push--- " + adId +"," + advPlanMap2.get(adId).getAdInstance().getAdName()  +this.targetInfo(advPlan) );
				//dbfreshlogger.info("channel:2-------诏兰push------- " + adId + advPlanMap2.get(adId).getAdInstance().getAdName()  +"，人群精准" );
			}else{
				logger.info("channel:2---push--- " + adId +","+ advPlanMap2.get(adId).getAdInstance().getAdName() +this.targetInfo(advPlan) );
				//dbfreshlogger.info("channel:2-------诏兰push------- " + adId + advPlanMap2.get(adId).getAdInstance().getAdName()  +"，盲投" );
			}
			
		}

	}
	
	
	private String targetInfo(AdvPlan advPlan){

		Set<String> targets = new HashSet<String>();
		if (advPlan.isAdAcctTargetFilter()) {
			targets.add("ad帐号定向");
		}

		if (advPlan.isSiteFilter()) {
			targets.add("域名定向");
		}
		if (advPlan.isUrlFilter()) {
			targets.add("url定向");
		}
		if (advPlan.getIsPosIpTargetFilter()) {
			targets.add("IP正向定向");
		}
		if (advPlan.getIsNegIpTargetFilter()) {
			targets.add("IP负向定向");
		}
		if (advPlan.isRegionTargetFilter()) {
			targets.add("地域定向");
		}
		if (advPlan.isLabelFilter()) {
			targets.add("标签定向");
		}
		
		if(advPlan.isAdDeviceTarget()){
			targets.add("设备定向");
		}
		
		if (advPlan.isKeywordFilter()) {
			if(advPlan.getAdPlanKeyWord().getIsJisoujitou().equals("1")) {
				targets.add("关键词定向-即搜即投");
			} else {
				targets.add("关键词定向-离线关键词");
			}
		}
		if ("1".equals(advPlan.getAdTimeFrequency().getIsUniform())) {
			targets.add("均匀投放["
					+ advPlan.getAdTimeFrequency().getMinuteLimit()
					+ "]");
		}
		
		return targets.toString();
	}

	

	public Map<String, AdvPlan> getAdvPlanMap2() {
		return advPlanMap2;
	}
	
	public Map<String, AdvPlan> getAdvPlanMap6() {
		return advPlanMap6;
	}
	
	public Map<String, AdvPlan> getAdvPlanMap7() {
		return advPlanMap7;
	}
	
	public Map<String, Set<String>> getAdsKeywordMap() {
		return adsKeywordMap;
	}

	public void destroy() {

	}

}
