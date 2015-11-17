package com.vaolan.ckserver.server.impl;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import com.hidata.framework.cache.redis.JedisPoolWriper;
import com.hidata.framework.cache.redis.RedisUtil;
import com.hidata.framework.util.DateUtil;
import com.hidata.framework.util.JsonUtil;
import com.hidata.framework.util.UserAgentUtil;
import com.vaolan.ckserver.dao.AdStateInfoDao;
import com.vaolan.ckserver.model.AdHostStatInfo;
import com.vaolan.ckserver.model.AdStatBase;
import com.vaolan.ckserver.model.AdStatInfo;
import com.vaolan.ckserver.model.CookieUser;
import com.vaolan.ckserver.model.ImpressInfo;
import com.vaolan.ckserver.model.IpAppendPvInfo;
import com.vaolan.ckserver.model.MaterialStatBase;
import com.vaolan.ckserver.server.AdLogService;
import com.vaolan.ckserver.server.LoadAdCrowdService;
import com.vaolan.ckserver.util.AdStatLogUtil;
import com.vaolan.ckserver.util.Constant;

/**
 * 记录广告曝光，点击信息，1、详细信息记录。2、实时计算出统计信息放入redis
 * 
 * @author chenjinzhao
 * 
 */
@Service
public class AdLogServiceImpl implements AdLogService {

	// 存储广告的，一天的uv明细，key：广告id，value：uv明细
	public static Map<String, Set<String>> todayAd_uv = new HashMap<String, Set<String>>();

	// 存储广告的，一天的ip明细，key：广告id，value：ip明细
	public static Map<String, Set<String>> todayAd_ip = new HashMap<String, Set<String>>();

	// 存储广告的， 当前小时的uv明细，key：广告id，value：uv明细
	public static Map<String, Set<String>> currentHourAd_uv = new HashMap<String, Set<String>>();

	// 存储广告的，当前小时的ip明细，key：广告id，value：ip明细
	public static Map<String, Set<String>> currentHourAd_ip = new HashMap<String, Set<String>>();

//	@Resource(name = "jedisWritePoolOne")
//	private JedisPoolWriper jedisWritePoolOne;

//	@Resource(name = "jedisWritePoolTwo")
//	private JedisPoolWriper jedisWritePoolTwo;

//	@Resource(name = "jedisWritePoolThree")
//	private JedisPoolWriper jedisWritePoolThree;

	@Autowired
	private LoadAdCrowdService loadAdCrowdService;
	
	@Autowired
	private AdStateInfoDao adStateInfoDao;

	public static Logger logger = Logger.getLogger(AdLogServiceImpl.class);

	
	
	private boolean isImpVaild(ImpressInfo impressInfo){
		boolean b = true;
		
		String adId =  impressInfo.getAdId();
		String mId = impressInfo.getMaterialId();
		
		//如果adId 不是数字则非正常
		try{
			Integer.parseInt(adId);
		}catch(Exception e){
			b = false;
			return b;
		}
		
		if(StringUtils.isNotBlank(mId)){
			//如果mId 不为空，且不是数字则非正常，为空则正常，因为有的广告没有物料
			try{
				Integer.parseInt(mId);
			}catch(Exception e){
				b = false;
				return b;
			}
		}
		
		return b ;
		
	}
	@Override
	public void savePvInfo(ImpressInfo impressInfo, CookieUser cookieUser,
			boolean isNewCookie) {

		
		if(!this.isImpVaild(impressInfo)){
			return;
		}
		
		Jedis client = RedisUtil.getJedisClientFromPool();

		try {
			if (isNewCookie) {
				// cookie信息写磁盘
				AdStatLogUtil.saveCookieUser(cookieUser);
			}
			// 根据广告获取对应的人群ids 逗号分隔
			if (!StringUtils.isEmpty(impressInfo.getAdId())) {
				String crowdIds = loadAdCrowdService
						.getCrowdIdsByAdId(impressInfo.getAdId());
				impressInfo.setCrowdIds(crowdIds);
			}
			// pv信息写磁盘
			AdStatLogUtil.saveImpressLog(impressInfo);

			// this.setBidImpNum(impressInfo, client);
			
			// 从广告的维度记录pv信息 
			if (client.exists(Constant.AD_STAT_KEY_PREFIX //adstat_main_
					+ impressInfo.getAdId())) {
				notAdfirstPVPipline(impressInfo, cookieUser, client);
			} else {
				adFirstPV(impressInfo, cookieUser, client);
			}

			if (StringUtils.isNotBlank(impressInfo.getMaterialId())) {
				// 从物料的维度记录pv信息
				if (client.exists(Constant.MATERIAL_STAT_KEY_PREFIX
						+ impressInfo.getMaterialId())) {
					notMaterialfirstPVPipline(impressInfo, cookieUser, client);
				} else {
					materialFirstPV(impressInfo, cookieUser, client);
				}
			}
			
			// 从域名为度记录 PV
//			if(StringUtil.isNotEmpty(impressInfo.getImpressUrl())){
//				this.adHostPvRecode(impressInfo,client);
//			}else{
//				logger.warn("广告host维度统计pv失败,原因投放的url为空...");
//			}
			
			//this.adHostPvRecode(impressInfo, client);
			this.adMinPvRecode(impressInfo, client);

			// 记录追投信息
			// this.savePvForIpAppendShow(cookieUser, impressInfo.getAdId());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("记录广告曝光信息出错:" + e.getMessage(), e);
		} finally {
			RedisUtil.releaseJedisClientFromPool(client);
		}

	}

	/**
	 * 记录某个广告投放到那些域名，以及数量
	 * 
	 * @param impressInfo
	 * @param client
	 *//*
	private void adHostPvRecode(ImpressInfo impressInfo, Jedis client) {

		String host = "";
		try {
			URL url = new URL(impressInfo.getImpressUrl());
			host = url.getHost();
			Pipeline pl = client.pipelined();

			pl.zincrby(Constant.AD_HOST_STAT + impressInfo.getAdId(), 1, host);

			pl.sync();
		} catch (Exception e) {
			logger.warn("背景url为空，或者解析host 出错！！");
			
		}

	}*/
	
	/**
	 * 请求过来之后统计某域名下  单个广告的展现量
	 * @param impressInfo	
	 * @param client
	 * @author xiaoming
	 */
	private void adHostPvRecode(ImpressInfo impressInfo, Jedis client) {
		
		String host = "";
		try {
			URL url = new URL(impressInfo.getImpressUrl());
			host = url.getHost();
			//如果不是第一次
			if (client.exists(Constant.AD_HOST_KEY + host + "_" + impressInfo.getAdId())) {
				notAdfirstPVHost(impressInfo, client, host);
			//如果是第一次
			} else {
				adFirstPVHost(impressInfo, client, host);
			}
		} catch (Exception e) {
			logger.warn("背景url为空，或者解析host 出错！！");
			
		}
		
	}
	
	/**
	 * 请求过来之后统计某域名下  单个广告的点击量
	 * @param impressInfo
	 * @param client
	 * @author xiaoming
	 */
	private void adHostClickRecode(ImpressInfo impressInfo, Jedis client){
		String host = "";
		try {
			URL url = new URL(impressInfo.getImpressUrl());
			host = url.getHost();
			String adHostkey = Constant.AD_HOST_KEY + host + "_"  + impressInfo.getAdId();  
			Pipeline pl = client.pipelined();
			// 当天的点击数量
			
			pl.hincrBy(adHostkey, "dayClick_host", 1);

			pl.sync();
		} catch (Exception e) {
			logger.warn("背景url为空，或者解析host 出错！！");
		}
	}
	
	/**
	 * 当第一次过来的 时候,直接插入数据
	 * @param impressInfo
	 * @param client
	 * @param Host
	 * @author xiaoming
	 */
	
	private void adFirstPVHost(ImpressInfo impressInfo, Jedis client, String host) throws Exception{
		
			String adHostDayKey = Constant.AD_HOST_KEY + host + "_" + impressInfo.getAdId();
			
			String adHostDayUvKey = Constant.AD_HOST_UV  +  host + "_" +  impressInfo.getAdId();

			String adHostDayIpKey = Constant.AD_HOST_IP  +  host + "_" +  impressInfo.getAdId();


			AdHostStatInfo adHostStatInfo = new AdHostStatInfo();
			
			adHostStatInfo.setAdId_key(impressInfo.getAdId());//广告ID
			adHostStatInfo.setHost_key(host);
			adHostStatInfo.setDayPv_host("1");
			adHostStatInfo.setDayClick_host("0");
			adHostStatInfo.setDayUv_host("1");
			adHostStatInfo.setDayIp_host("1");
			String jsonStr = JsonUtil.beanToJson(adHostStatInfo);
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			Set<String> jsonKeys = jsonObj.keySet();
			for (String field : jsonKeys) {
				String value = jsonObj.getString(field);
				if (StringUtils.isNotBlank(value)) {
					client.hset(adHostDayKey,field, value);
				}
			}
			client.sadd(adHostDayUvKey, impressInfo.getUid());
			client.sadd(adHostDayIpKey, impressInfo.getSrcIP());
	}
	
	/**
	 * 当不是当第一次过来时,进行更新
	 * @param impressInfo
	 * @param client
	 * @param Host
	 * @author xiaoming
	 */
	private void notAdfirstPVHost(ImpressInfo impressInfo, Jedis client, String host){
		Pipeline pl = client.pipelined();

		String adHostkey = Constant.AD_HOST_KEY + host + "_"  + impressInfo.getAdId();  

		String adHostDayUvKey = Constant.AD_HOST_UV  +  host + "_" +  impressInfo.getAdId();

		String adHostDayIpKey = Constant.AD_HOST_IP  +  host + "_" +  impressInfo.getAdId();


		// 当前广告的当天的pv量，后续有timer，每天凌晨零点零分零秒去更把这个只持久化到数据库，并删除该记录
		pl.hincrBy(adHostkey, "dayPv_host", 1); // key 值 + 1


		// 广告当天的uv量，放入去重的set中，然后set的数量就是uv量
		pl.sadd(adHostDayUvKey, impressInfo.getUid());

		// 广告当天的ip量，放入去重的set中，然后set的数量就是ip量
		pl.sadd(adHostDayIpKey, impressInfo.getSrcIP());

		logger.debug("当前广告id的实时统计信息_以域名为维度。");

		pl.sync();
	}

	/**
	 * 记录某个广告当前分钟的数量
	 * 
	 * @param impressInfo
	 * @param client
	 */
	private void adMinPvRecode(ImpressInfo impressInfo, Jedis client) {

		Pipeline p =client.pipelined();
		p.hincrBy(Constant.AD_MIN_NUM, "adId_" + impressInfo.getAdId(), 1);
		p.sync();
	}

	@Override
	public void saveClickInfo(ImpressInfo impressInfo, CookieUser cookieUser) {

		
		if(!this.isImpVaild(impressInfo)){
			return;
		}
		
		
		
		Jedis client = RedisUtil.getJedisClientFromPool();

		//boolean realClick = false;//判断真实点击
		boolean realClick = true;

	/*	if (StringUtils.isNotBlank(impressInfo.getImpuuid())) {
			// 当前展示，1分钟之内，被点击过。
			if (client.exists("click_" + impressInfo.getImpuuid())) {
				String lastClickTimeStr = client.get("click_"
						+ impressInfo.getImpuuid());
				long lastClickTime = Long.parseLong(lastClickTimeStr);

				long thisClickTime = System.currentTimeMillis();

				// 如果当前点击，距离上次点击已经超过30s，则认为是真实的点击，不然就是NHT
				if (thisClickTime - lastClickTime >= 30000) {
					realClick = true;
				}

				// 更新这次展示的点击时间
				client.set("click_" + impressInfo.getImpuuid(), thisClickTime
						+ "");
				client.expire("click_" + impressInfo.getImpuuid(), 60);
			} else {

				realClick = true;
				long thisClickTime = System.currentTimeMillis();
				client.set("click_" + impressInfo.getImpuuid(), thisClickTime
						+ "");
				client.expire("click_" + impressInfo.getImpuuid(), 60);
			}
		} else {
			realClick = true;
		}*/

		try {

			// this.setBidClickNum(impressInfo, client);
			if (realClick) {

				// 根据广告获取对应的人群ids 逗号分隔
				if (!StringUtils.isEmpty(impressInfo.getAdId())) {
					String crowdIds = loadAdCrowdService
							.getCrowdIdsByAdId(impressInfo.getAdId());
					impressInfo.setCrowdIds(crowdIds);
				}
				// 点击信息写入磁盘
				AdStatLogUtil.saveClickLog(impressInfo);
				
				//统计点击  以广告为维度;
				
				adClickInfoPipline(impressInfo, cookieUser, client);
				// adClickInfo(impressInfo, cookieUser);
				// materialClickInfo(impressInfo, cookieUser);
				//统计点击 以物料为维度
				
				
				if (StringUtils.isNotBlank(impressInfo.getMaterialId())) {
					materialClickInfoPipline(impressInfo, cookieUser, client);
				}
				
				//统计点击 以HOST为维度
				
				// 从域名为度记录 点击
				/*if(StringUtil.isNotEmpty(impressInfo.getImpressUrl())){
					this.adHostClickRecode(impressInfo,client);
				}else{
					logger.warn("广告host维度统计Click失败,原因投放的url为空...");
				}*/
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存点击信息出错:" + e.getMessage(), e);
		} finally {
			RedisUtil.releaseJedisClientFromPool(client);
		}

	}
	
	/**
	 * 统计点击关闭按钮的信息
	 */
	@Override
	public void saveCloseClickInfo(ImpressInfo impressInfo) {
		if(!this.isImpVaild(impressInfo)){
			return;
		}
		Jedis client = RedisUtil.getJedisClientFromPool();

		try {
				// 根据广告获取对应的人群ids 逗号分隔
				if (!StringUtils.isEmpty(impressInfo.getAdId())) {
					String crowdIds = loadAdCrowdService
							.getCrowdIdsByAdId(impressInfo.getAdId());
					impressInfo.setCrowdIds(crowdIds);
				}
				//统计关闭按钮被点击的量
				adCloseClickInfo(impressInfo, client);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存关闭点击信息出错:" + e.getMessage(), e);
		} finally {
			RedisUtil.releaseJedisClientFromPool(client);
		}

	}
	
	/**
	 * 统计关闭按钮
	 * @param impressInfo
	 * @param client
	 */
	private void adCloseClickInfo(ImpressInfo impressInfo, Jedis client){
		String key = Constant.AD_STAT_KEY_PREFIX + impressInfo.getAdId();

		Pipeline pl = client.pipelined();
		
		// 当天关闭按钮点击数量
		pl.hincrBy(key, "today_close_num", 1);
		// 当前小时关闭按钮点击量
		pl.hincrBy(key, "currentHour_close_num", 1);
		pl.sync();
	}
	/**
	 * 从广告维度记录点击信息 redis pipline 版本
	 * 
	 * @param impresInfo
	 * @param cookieUser
	 * @param jedis
	 * @throws Exception
	 */
	private void adClickInfoPipline(ImpressInfo impresInfo,
			CookieUser cookieUser, Jedis jedis) throws Exception {

		String key = Constant.AD_STAT_KEY_PREFIX + impresInfo.getAdId();

		Pipeline pl = jedis.pipelined();
		// 总共点击数量
		pl.hincrBy(key, "total_click_num", 1);

		// 当天的点击数量
		pl.hincrBy(key, "today_click_num", 1);

		// 当天移动端的点击量
		if (UserAgentUtil.isMoveDevice(cookieUser.getUserAgent())) {
			pl.hincrBy(key, "today_mobile_click_num", 1);
		}

		// 当前小时的点击量
		pl.hincrBy(key, "currentHour_click_num", 1);

		// 当前小时移动端的点击量
		if (UserAgentUtil.isMoveDevice(cookieUser.getUserAgent())) {
			pl.hincrBy(key, "currentHour_mobile_click_num", 1);
		}

		if (StringUtils.isNotBlank(impresInfo.getImpuuid())) {
			pl.hincrBy(Constant.IMP_CLICK_KEY, impresInfo.getImpuuid(), 1);
		}
		pl.sync();

	}

	/**
	 * 从物料维度记录点击信息,redis pipline 版本
	 * 
	 * @param impressInfo
	 * @param cookieUser
	 * @param jedis
	 * @throws Exception
	 */
	private void materialClickInfoPipline(ImpressInfo impressInfo,
			CookieUser cookieUser, Jedis jedis) throws Exception {

		String key = Constant.MATERIAL_STAT_KEY_PREFIX
				+ impressInfo.getMaterialId();

		Pipeline pl = jedis.pipelined();
		// 总共点击数量
		pl.hincrBy(key, "total_click_num", 1);

		// 当天的点击数量
		pl.hincrBy(key, "today_click_num", 1);

		// 当天移动端的点击量
		if (UserAgentUtil.isMoveDevice(cookieUser.getUserAgent())) {
			pl.hincrBy(key, "today_mobile_click_num", 1);
		}

		// 当前小时的点击量
		pl.hincrBy(key, "currentHour_click_num", 1);

		// 当前小时移动端的点击量
		if (UserAgentUtil.isMoveDevice(cookieUser.getUserAgent())) {
			pl.hincrBy(key, "currentHour_mobile_click_num", 1);
		}
		pl.sync();

	}

	/**
	 * 更新广告的统计信息，redis pipline版
	 * 
	 * @param impressInfo
	 * @param cookieUser
	 * @param jedis
	 * @throws Exception
	 */
	private void notAdfirstPVPipline(ImpressInfo impressInfo,
			CookieUser cookieUser, Jedis jedis) throws Exception {

		Pipeline pl = jedis.pipelined();

		String key = Constant.AD_STAT_KEY_PREFIX + impressInfo.getAdId();  //adstat_main_

		String adDayUvKey = Constant.AD_STAT_DAY_UV_KEY_PREFIX // adstat_day_uv_
				+ impressInfo.getAdId();

		String adDayIpKey = Constant.AD_STAT_DAY_IP_KEY_PREFIX
				+ impressInfo.getAdId();

		String adHourUvKey = Constant.AD_STAT_HOUR_UV_KEY_PREFIX
				+ impressInfo.getAdId();

		String adHourIpKey = Constant.AD_STAT_HOUR_IP_KEY_PREFIX
				+ impressInfo.getAdId();

		// 当前广告的所有pv量
		pl.hincrBy(key, "total_pv_num", 1);// key 值 + 1

		// 当前广告的当天的pv量，后续有timer，每天凌晨零点零分零秒去更把这个只持久化到数据库，并清零重新计算
		pl.hincrBy(key, "today_pv_num", 1); // key 值 + 1

		if (UserAgentUtil.isMoveDevice(cookieUser.getUserAgent())) {
			// 当前广告的当天移动端的pv量，后续有timer，每天凌晨零点零分零秒去更把这个只持久化到数据库，并清零重新计算
			pl.hincrBy(key, "today_mobile_pv_num", 1);
		}

		// 广告当天的uv量，放入去重的set中，然后set的数量就是uv量
		pl.sadd(adDayUvKey, impressInfo.getUid());

		// 广告当天的ip量，放入去重的set中，然后set的数量就是ip量
		pl.sadd(adDayIpKey, impressInfo.getSrcIP());

		// 当前广告的当前小时的pv量，后续有timer，每个小时去更把这个只持久化到数据库，并清零重新计算
		pl.hincrBy(key, "currentHour_pv_num", 1);

		if (UserAgentUtil.isMoveDevice(cookieUser.getUserAgent())) {
			// 当前广告的当前小时的移动端pv量，后续有timer，每个小时去更把这个只持久化到数据库，并清零重新计算
			pl.hincrBy(key, "currentHour_mobile_pv_num", 1);
		}

		// 广告当前小时的uv量，放入去重的set中，然后set的数量就是uv量
		pl.sadd(adHourUvKey, impressInfo.getUid());

		// 广告当前小时的ip量，放入去重的set中，然后set的数量就是ip量
		pl.sadd(adHourIpKey, impressInfo.getSrcIP());

		logger.debug("当前广告id的实时统计信息");

		pl.sync();

	}

	/**
	 * 当前广告非第一次曝光信息记录(以物料为维度的记录)
	 * 
	 * @param impressInfo
	 * @param cookieUser
	 * @throws Exception
	 * @author chenjinzhao
	 */
	private void notMaterialfirstPV(ImpressInfo impressInfo,
			CookieUser cookieUser) throws Exception {
		Map<String, String> adStatInfoMap = RedisUtil
				.hgetAll(Constant.MATERIAL_STAT_KEY_PREFIX
						+ impressInfo.getMaterialId());
		// 把一个广告的所有统计信息，从redis中取出来，组成一个对象
		JSONObject adStatJSON = JSONObject.fromObject(adStatInfoMap);
		AdStatInfo adStatInfo = JsonUtil.jsonToBean(adStatJSON.toString(),
				AdStatInfo.class);

		// 当前广告的所有pv量
		adStatInfo.setTotal_pv_num(Integer.toString(Integer.parseInt(adStatInfo
				.getTotal_pv_num()) + 1));

		String todayPvNum = adStatInfo.getToday_pv_num();

		// 当前广告的当天的pv量，后续有timer，每天凌晨零点零分零秒去更把这个只持久化到数据库，并清零重新计算
		adStatInfo.setToday_pv_num(Integer.toString(Integer.parseInt(adStatInfo
				.getToday_pv_num()) + 1));

		if (UserAgentUtil.isMoveDevice(cookieUser.getUserAgent())) {
			// 当前广告的当天移动端的pv量，后续有timer，每天凌晨零点零分零秒去更把这个只持久化到数据库，并清零重新计算
			adStatInfo.setToday_mobile_pv_num(Integer.toString(Integer
					.parseInt(adStatInfo.getToday_mobile_pv_num()) + 1));
		}

		// 广告一天的uv明细操作,从而实时计算出广告的一天的uv量，
		Set<String> todayAd_uvSet = todayAd_uv
				.get(Constant.MATERIAL_STAT_KEY_PREFIX
						+ impressInfo.getMaterialId());
		if (todayAd_uvSet == null) {
			todayAd_uvSet = new HashSet<String>();
			todayAd_uvSet.add(impressInfo.getUid());

			todayAd_uv.put(
					Constant.MATERIAL_STAT_KEY_PREFIX
							+ impressInfo.getMaterialId(), todayAd_uvSet);
		} else {

			if ("0".equals(todayPvNum)) {
				todayAd_uvSet.clear();
			}

			todayAd_uvSet.add(impressInfo.getUid());
			todayAd_uv.put(
					Constant.MATERIAL_STAT_KEY_PREFIX
							+ impressInfo.getMaterialId(), todayAd_uvSet);
		}

		// 当前广告的当天的uv量
		adStatInfo.setToday_uv_num(todayAd_uvSet.size() + "");

		// 广告一天的ip明细操作,从而实时计算出广告的一天的ip量
		Set<String> todayAd_ipSet = todayAd_ip
				.get(Constant.MATERIAL_STAT_KEY_PREFIX
						+ impressInfo.getMaterialId());
		if (todayAd_ipSet == null) {
			todayAd_ipSet = new HashSet<String>();
			todayAd_ipSet.add(impressInfo.getSrcIP());

			todayAd_ip.put(
					Constant.MATERIAL_STAT_KEY_PREFIX
							+ impressInfo.getMaterialId(), todayAd_ipSet);
		} else {

			if ("0".equals(todayPvNum)) {
				todayAd_ipSet.clear();
			}

			todayAd_ipSet.add(impressInfo.getSrcIP());
			todayAd_ip.put(
					Constant.MATERIAL_STAT_KEY_PREFIX
							+ impressInfo.getMaterialId(), todayAd_ipSet);
		}

		// 当前广告的当天的ip量
		adStatInfo.setToday_ip_num(todayAd_ipSet.size() + "");

		String currentHourPvNum = adStatInfo.getCurrentHour_pv_num();

		// 当前广告的当前小时的pv量，后续有timer，每个小时去更把这个只持久化到数据库，并清零重新计算
		adStatInfo.setCurrentHour_pv_num(Integer.toString(Integer
				.parseInt(adStatInfo.getCurrentHour_pv_num()) + 1));

		if (UserAgentUtil.isMoveDevice(cookieUser.getUserAgent())) {
			// 当前广告的当前小时的移动端pv量，后续有timer，每个小时去更把这个只持久化到数据库，并清零重新计算
			adStatInfo.setCurrentHour_mobile_pv_num(Integer.toString(Integer
					.parseInt(adStatInfo.getCurrentHour_mobile_pv_num()) + 1));
		}

		// 广告当前小时的uv明细操作,从而实时计算出广告的当前小时的uv量，
		Set<String> currentHourAd_uvSet = currentHourAd_uv
				.get(Constant.MATERIAL_STAT_KEY_PREFIX
						+ impressInfo.getMaterialId());
		if (currentHourAd_uvSet == null) {
			currentHourAd_uvSet = new HashSet<String>();
			currentHourAd_uvSet.add(impressInfo.getUid());

			currentHourAd_uv.put(Constant.MATERIAL_STAT_KEY_PREFIX
					+ impressInfo.getMaterialId(), currentHourAd_uvSet);
		} else {

			// 如果当前小时pv是0，则是到了新的一个小时，uvSet清空
			if ("0".equals(currentHourPvNum)) {
				currentHourAd_uvSet.clear();
			}

			currentHourAd_uvSet.add(impressInfo.getUid());
			currentHourAd_uv.put(Constant.MATERIAL_STAT_KEY_PREFIX
					+ impressInfo.getMaterialId(), currentHourAd_uvSet);
		}

		// 当前广告的当前小时的uv量
		adStatInfo.setCurrentHour_uv_num(currentHourAd_uvSet.size() + "");

		// 广告当前小时的ip明细操作,从而实时计算出广告的当前小时的ip量
		Set<String> currentHourAd_ipSet = currentHourAd_ip
				.get(Constant.MATERIAL_STAT_KEY_PREFIX
						+ impressInfo.getMaterialId());
		if (currentHourAd_ipSet == null) {
			currentHourAd_ipSet = new HashSet<String>();
			currentHourAd_ipSet.add(impressInfo.getSrcIP());

			currentHourAd_ip.put(Constant.MATERIAL_STAT_KEY_PREFIX
					+ impressInfo.getMaterialId(), currentHourAd_ipSet);
		} else {

			// 如果当前小时pv是0，则是到了新的一个小时，ipSet清空
			if ("0".equals(currentHourPvNum)) {
				currentHourAd_ipSet.clear();
			}

			currentHourAd_ipSet.add(impressInfo.getSrcIP());
			currentHourAd_ip.put(Constant.MATERIAL_STAT_KEY_PREFIX
					+ impressInfo.getMaterialId(), currentHourAd_ipSet);
		}

		// 当前广告的当前小时的ip量
		adStatInfo.setCurrentHour_ip_num(currentHourAd_ipSet.size() + "");

		logger.debug("当前广告id的实时统计信息:" + impressInfo.getMaterialId() + "==="
				+ adStatInfo.toString());

		RedisUtil
				.hsetOjbect(
						Constant.MATERIAL_STAT_KEY_PREFIX
								+ impressInfo.getMaterialId(), adStatInfo);

	}

	/**
	 * 当前广告非第一次曝光信息记录(以物料为维度的记录),redis pipline版本
	 * 
	 * @param impressInfo
	 * @param cookieUser
	 * @param jedis
	 * @throws Exception
	 */
	private void notMaterialfirstPVPipline(ImpressInfo impressInfo,
			CookieUser cookieUser, Jedis jedis) throws Exception {

		Pipeline pl = jedis.pipelined();

		String key = Constant.MATERIAL_STAT_KEY_PREFIX
				+ impressInfo.getMaterialId();

		String maDayUvKey = Constant.MATERIAL_STAT_DAY_UV_KEY_PREFIX
				+ impressInfo.getMaterialId();

		String maDayIpKey = Constant.MATERIAL_STAT_DAY_IP_KEY_PREFIX
				+ impressInfo.getMaterialId();

		String maHourUvKey = Constant.MATERIAL_STAT_HOUR_UV_KEY_PREFIX
				+ impressInfo.getMaterialId();

		String maHourIpKey = Constant.MATERIAL_STAT_HOUR_IP_KEY_PREFIX
				+ impressInfo.getMaterialId();

		// 当前物料的所有pv量
		pl.hincrBy(key, "total_pv_num", 1);

		// 当前物料的当天的pv量，后续有timer，每天凌晨零点零分零秒去更把这个只持久化到数据库，并清零重新计算
		pl.hincrBy(key, "today_pv_num", 1);

		if (UserAgentUtil.isMoveDevice(cookieUser.getUserAgent())) {
			// 当前物料的当天移动端的pv量，后续有timer，每天凌晨零点零分零秒去更把这个只持久化到数据库，并清零重新计算
			pl.hincrBy(key, "today_mobile_pv_num", 1);
		}

		// 物料当天的uv量，放入去重的set中，然后set的数量就是uv量，后续进程
		pl.sadd(maDayUvKey, impressInfo.getUid());

		// 物料当天的ip量，放入去重的set中，然后set的数量就是ip量
		pl.sadd(maDayIpKey, impressInfo.getSrcIP());

		// 当前物料的当前小时的pv量，后续有timer，每个小时去更把这个只持久化到数据库，并清零重新计算
		pl.hincrBy(key, "currentHour_pv_num", 1);

		if (UserAgentUtil.isMoveDevice(cookieUser.getUserAgent())) {
			// 当前物料的当前小时的移动端pv量，后续有timer，每个小时去更把这个只持久化到数据库，并清零重新计算
			pl.hincrBy(key, "currentHour_mobile_pv_num", 1);
		}

		// 物料当前小时的uv量，放入去重的set中，然后set的数量就是uv量
		pl.sadd(maHourUvKey, impressInfo.getUid());

		// 物料当前小时的ip量，放入去重的set中，然后set的数量就是ip量
		pl.sadd(maHourIpKey, impressInfo.getSrcIP());

		pl.sync();

		logger.debug("当前物料id的实时统计信息:" + impressInfo.getMaterialId());

	}

	/**
	 * 广告第一次曝光记录信息
	 * 
	 * @param impressInfo
	 * @param cookieUser
	 * @author chenjinzhao
	 * @throws IOException
	 */
	private void adFirstPV(ImpressInfo impressInfo, CookieUser cookieUser,
			Jedis client) throws IOException {

		String adDayUvKey = Constant.AD_STAT_DAY_UV_KEY_PREFIX  //adstat_day_uv_
				+ impressInfo.getAdId();

		String adDayIpKey = Constant.AD_STAT_DAY_IP_KEY_PREFIX // adstat_day_ip_
				+ impressInfo.getAdId();

		String adHourUvKey = Constant.AD_STAT_HOUR_UV_KEY_PREFIX // adstat_hour_uv_
				+ impressInfo.getAdId();

		String adHourIpKey = Constant.AD_STAT_HOUR_IP_KEY_PREFIX  // adstat_hour_ip_
				+ impressInfo.getAdId();
		
		//如果是第一次,往数据库中插入数据;
		String adId = impressInfo.getAdId();
		String currentDay = DateUtil.getCurrentDayBeginDateTimeStr();
		String currentHour = DateUtil.getCurrentHourBeginDateTimeStr();
		
		//判断天的时间是否存在
		List<AdStatBase> adStatBaseDays = adStateInfoDao.findAdstatBaseByAdIdAndTime(adId, currentDay, "D");
		if(adStatBaseDays != null && adStatBaseDays.size() > 0){
			
		}else{
			AdStatBase adStatBaseDay = new AdStatBase();
			adStatBaseDay.setAdId(adId);
            adStatBaseDay.setPvNum("0");
            adStatBaseDay.setClickNum("0");
            adStatBaseDay.setIpNum("0");
            adStatBaseDay.setMobileClickNum("0");
            adStatBaseDay.setMobilePvNum("0");
            adStatBaseDay.setNumType("D");
            adStatBaseDay.setTs(currentDay);
            adStatBaseDay.setUvNum("0");
            adStatBaseDay.setCloseNum("0");
            adStateInfoDao.saveAdStatBase(adStatBaseDay);
            
		}
		//判断小时的时间是否存在
		List<AdStatBase> adStatBaseHours = adStateInfoDao.findAdstatBaseByAdIdAndTime(adId, currentHour, "H");
		if(adStatBaseHours != null && adStatBaseHours.size() > 0){
			
		}else{
			AdStatBase adStatBaseHour = new AdStatBase();
            adStatBaseHour.setAdId(adId);
            adStatBaseHour.setPvNum("0");
            adStatBaseHour.setClickNum("0");
            adStatBaseHour.setIpNum("0");
            adStatBaseHour.setMobileClickNum("0");
            adStatBaseHour.setMobilePvNum("0");
            adStatBaseHour.setNumType("H");
            adStatBaseHour.setTs(DateUtil.getCurrentHourBeginDateTimeStr());
            adStatBaseHour.setUvNum("0");
            adStatBaseHour.setCloseNum("0");
            adStateInfoDao.saveAdStatBase(adStatBaseHour);
		}
		AdStatInfo adStatInfo = new AdStatInfo();
		adStatInfo.setAdId(impressInfo.getAdId());
		adStatInfo.setMaterialId(impressInfo.getMaterialId());
		
		// 全量
		adStatInfo.setTotal_click_num("0");
		adStatInfo.setTotal_pv_num("1");

		// 当天的
		adStatInfo.setToday_pv_num("1");
		adStatInfo.setToday_click_num("0");
		adStatInfo.setToday_uv_num("1");
		adStatInfo.setToday_ip_num("1");
		adStatInfo.setToday_mobile_click_num("0");
		//点击关闭按钮的量
		adStatInfo.setToday_close_num("0");
		

		if (UserAgentUtil.isMoveDevice(cookieUser.getUserAgent())) {
			adStatInfo.setToday_mobile_pv_num("1");
		} else {
			adStatInfo.setToday_mobile_pv_num("0");
		}

		// 当前小时的
		adStatInfo.setCurrentHour_pv_num("1");
		adStatInfo.setCurrentHour_click_num("0");
		adStatInfo.setCurrentHour_uv_num("1");
		adStatInfo.setCurrentHour_ip_num("1");
		adStatInfo.setCurrentHour_mobile_click_num("0");
		
		//点击关闭按钮的量
		adStatInfo.setCurrentHour_close_num("0");

		if (UserAgentUtil.isMoveDevice(cookieUser.getUserAgent())) {
			adStatInfo.setCurrentHour_mobile_pv_num("1");
		} else {
			adStatInfo.setCurrentHour_mobile_pv_num("0");
		}

		

		String jsonStr = JsonUtil.beanToJson(adStatInfo);
		JSONObject jsonObj = JSONObject.fromObject(jsonStr);
		Set<String> jsonKeys = jsonObj.keySet();
		for (String field : jsonKeys) {
			String value = jsonObj.getString(field);
			if (StringUtils.isNotBlank(value)) {
				client.hset(
						Constant.AD_STAT_KEY_PREFIX + impressInfo.getAdId(),
						field, value);
			}
		}

		client.sadd(adDayUvKey, impressInfo.getUid());
		client.sadd(adDayIpKey, impressInfo.getSrcIP());

		client.sadd(adHourUvKey, impressInfo.getUid());
		client.sadd(adHourIpKey, impressInfo.getSrcIP());

	}

	/**
	 * 物料第一次曝光记录信息
	 * 
	 * @param impressInfo
	 * @param cookieUser
	 * @author chenjinzhao
	 * @throws IOException
	 */
	private void materialFirstPV(ImpressInfo impressInfo,
			CookieUser cookieUser, Jedis client) throws IOException {

		String maDayUvKey = Constant.MATERIAL_STAT_DAY_UV_KEY_PREFIX
				+ impressInfo.getMaterialId();

		String maDayIpKey = Constant.MATERIAL_STAT_DAY_IP_KEY_PREFIX
				+ impressInfo.getMaterialId();

		String maHourUvKey = Constant.MATERIAL_STAT_HOUR_UV_KEY_PREFIX
				+ impressInfo.getMaterialId();

		String maHourIpKey = Constant.MATERIAL_STAT_HOUR_IP_KEY_PREFIX
				+ impressInfo.getMaterialId();
		
		//广告物料ID
		String materialId =  impressInfo.getMaterialId();
		//当前天的时间
		String currentDay = DateUtil.getCurrentDayBeginDateTimeStr();
		//当前小时的时间
		String currentHour = DateUtil.getCurrentHourBeginDateTimeStr();
		
		//判断当前天的时间数据是否存在;
		List<MaterialStatBase> materialStatBaseDays = adStateInfoDao.findMaterialStatBaseByIdAndTime(materialId, currentDay, "D");
		if(materialStatBaseDays != null && materialStatBaseDays.size() == 0){
			MaterialStatBase maStatBaseDay = new MaterialStatBase();
			maStatBaseDay.setMaterial_id(materialId);
			maStatBaseDay.setPvNum("0");
			maStatBaseDay.setClickNum("0");
			maStatBaseDay.setIpNum("0");
			maStatBaseDay.setMobileClickNum("0");
			maStatBaseDay.setMobilePvNum("0");
			maStatBaseDay.setNumType("D");
			maStatBaseDay.setTs(currentDay);
			maStatBaseDay.setUvNum("0");
			adStateInfoDao.saveMaterialStatNase(maStatBaseDay);
		}
		//判断当前小时时间输进去是否存在;
		List<MaterialStatBase> materialStatBaseHours = adStateInfoDao.findMaterialStatBaseByIdAndTime(materialId, currentHour, "H");
		if(materialStatBaseHours != null && materialStatBaseHours.size() == 0){
			MaterialStatBase maStatBaseHour = new MaterialStatBase();
			maStatBaseHour.setMaterial_id(materialId);
			maStatBaseHour.setPvNum("0");
			maStatBaseHour.setClickNum("0");
			maStatBaseHour.setIpNum("0");
			maStatBaseHour.setMobileClickNum("0");
			maStatBaseHour.setMobilePvNum("0");
			maStatBaseHour.setNumType("H");
			maStatBaseHour.setTs(currentHour);
			maStatBaseHour.setUvNum("0");
			adStateInfoDao.saveMaterialStatNase(maStatBaseHour);
		}
		
		
		
		
		AdStatInfo adStatInfo = new AdStatInfo();
		// 全量
		adStatInfo.setTotal_click_num("0");
		adStatInfo.setTotal_pv_num("1");

		// 当天的
		adStatInfo.setToday_uv_num("1");
		adStatInfo.setToday_ip_num("1");
		adStatInfo.setToday_click_num("0");
		adStatInfo.setToday_mobile_click_num("0");
		adStatInfo.setToday_pv_num("1");

		if (UserAgentUtil.isMoveDevice(cookieUser.getUserAgent())) {
			adStatInfo.setToday_mobile_pv_num("1");
		} else {
			adStatInfo.setToday_mobile_pv_num("0");
		}

		// 当前小时的
		adStatInfo.setCurrentHour_click_num("0");
		adStatInfo.setCurrentHour_ip_num("1");
		adStatInfo.setCurrentHour_mobile_click_num("0");

		if (UserAgentUtil.isMoveDevice(cookieUser.getUserAgent())) {
			adStatInfo.setCurrentHour_mobile_pv_num("1");
		} else {
			adStatInfo.setCurrentHour_mobile_pv_num("0");
		}

		adStatInfo.setCurrentHour_pv_num("1");
		adStatInfo.setCurrentHour_uv_num("1");

		String jsonStr = JsonUtil.beanToJson(adStatInfo);
		JSONObject jsonObj = JSONObject.fromObject(jsonStr);
		Set<String> jsonKeys = jsonObj.keySet();
		for (String field : jsonKeys) {
			String value = jsonObj.getString(field);
			if (StringUtils.isNotBlank(value)) {
				client.hset(
						Constant.MATERIAL_STAT_KEY_PREFIX
								+ impressInfo.getMaterialId(), field, value);
			}
		}

		client.sadd(maDayUvKey, impressInfo.getUid());
		client.sadd(maDayIpKey, impressInfo.getSrcIP());

		client.sadd(maHourUvKey, impressInfo.getUid());
		client.sadd(maHourIpKey, impressInfo.getSrcIP());

	}

	/**
	 * 设置广告各个维度pv数，channel_domain_slot_adId
	 * 
	 * @author chenjinzhao
	 * @param impressInfo
	 * @param client
	 */
	private void setBidImpNum(ImpressInfo impressInfo, Jedis client) {

		if (StringUtils.isNotBlank(impressInfo.getChannel())
				&& StringUtils.isNotBlank(impressInfo.getDomain())
				&& StringUtils.isNotBlank(impressInfo.getSize())
				&& StringUtils.isNotBlank(impressInfo.getViewscreen())) {

			Pipeline pl = client.pipelined();

			String key4 = impressInfo.getChannel() + "_"
					+ impressInfo.getDomain() + "_" + impressInfo.getSize()
					+ "_" + impressInfo.getViewscreen() + "_"
					+ impressInfo.getAdId();

			pl.hincrBy(key4, "imp_num", 1);

			String key3 = impressInfo.getChannel() + "_"
					+ impressInfo.getDomain() + "_" + impressInfo.getSize()
					+ "_" + impressInfo.getViewscreen();

			pl.hincrBy(key3, "imp_num", 1);

			String key2 = impressInfo.getChannel() + "_"
					+ impressInfo.getDomain();

			pl.hincrBy(key2, "imp_num", 1);

			String key1 = impressInfo.getChannel();

			pl.hincrBy(key1, "imp_num", 1);

			pl.sync();

		}

	}

	/**
	 * 设置广告各个维度click数，channel_domain_slot_adId
	 * 
	 * @author chenjinzhao
	 * @param impressInfo
	 * @param client
	 */
	private void setBidClickNum(ImpressInfo impressInfo, Jedis client) {

		if (StringUtils.isNotBlank(impressInfo.getChannel())
				&& StringUtils.isNotBlank(impressInfo.getDomain())
				&& StringUtils.isNotBlank(impressInfo.getSize())
				&& StringUtils.isNotBlank(impressInfo.getViewscreen())) {
			Pipeline pl = client.pipelined();

			String key4 = impressInfo.getChannel() + "_"
					+ impressInfo.getDomain() + "_" + impressInfo.getSize()
					+ "_" + impressInfo.getViewscreen() + "_"
					+ impressInfo.getAdId();

			pl.hincrBy(key4, "click_num", 1);

			String key3 = impressInfo.getChannel() + "_"
					+ impressInfo.getDomain() + "_" + impressInfo.getSize()
					+ "_" + impressInfo.getViewscreen();

			pl.hincrBy(key3, "click_num", 1);

			String key2 = impressInfo.getChannel() + "_"
					+ impressInfo.getDomain();

			pl.hincrBy(key2, "click_num", 1);

			String key1 = impressInfo.getChannel();

			pl.hincrBy(key1, "click_num", 1);

			pl.sync();
		}

	}

	/**
	 * 保存待追投PV数据 保存逻辑： 1、通过广告ID,判断是否存在对应的待追投广告，如果存在保存。
	 * 2、将IP,UA,TS,AD_ID保存到两个不同的redis数据库,key值为IP+UA加密串
	 * 3、分别存储到采集前置器和广告服务对应的redis上
	 * 
	 * @param cookieUserInfo
	 * @param adId
	 * @return
	 * @author zhoubin
	 */
//	private boolean savePvForIpAppendShow(CookieUser cookieUserInfo, String adId) {
//
//		try {
//
//			// 获取正常广告和追投广告对应redis
//			String appedAdId = getAppendAdIdFromRedis(adId, jedisWritePoolThree);
//			// 没有对应的追投广告,不能追投
//			if (StringUtils.isBlank(appedAdId)) {
//
//				return true;
//			}
//
//			// 将IP,UA,TS,AD_ID保存到两个不同的redis数据库,key值为IP+UA加密串
//
//			IpAppendPvInfo paraIpAppendPv = new IpAppendPvInfo();
//
//			paraIpAppendPv.setAdId(appedAdId);
//
//			paraIpAppendPv.setSrcIp(cookieUserInfo.getIpAddress());
//
//			paraIpAppendPv.setUa(cookieUserInfo.getUserAgent());
//
//			paraIpAppendPv.setIpUaKey(JavaMd5Util.md5Encryp(
//					cookieUserInfo.getIpAddress(),
//					cookieUserInfo.getUserAgent()));
//
//			long time = DateUtil.dateDDMMSSStringToLong(
//					cookieUserInfo.getCreateTime()).longValue() / 1000;
//			paraIpAppendPv.setTs(String.valueOf(time));
//
//			this.saveIpAppendPvToRedis(paraIpAppendPv, jedisWritePoolOne);
//
//			this.saveIpAppendPvToRedis(paraIpAppendPv, jedisWritePoolTwo);
//
//		} catch (Exception e) {
//			logger.error("savePvForIpAppendShow into redis failed!", e);
//		}
//
//		return true;
//	}

	private boolean saveIpAppendPvToRedis(IpAppendPvInfo paraIpAppendPvInfo,
			JedisPoolWriper paraJedisPool) {

		Jedis redis = paraJedisPool.getJedis();

		try {
			if (null != redis) {
				Pipeline pl = redis.pipelined();
				// 设置ip
				pl.hset(paraIpAppendPvInfo.getIpUaKey(), "ip_filed",
						paraIpAppendPvInfo.getSrcIp());
				pl.hset(paraIpAppendPvInfo.getIpUaKey(), "ua_filed",
						paraIpAppendPvInfo.getUa());
				pl.hset(paraIpAppendPvInfo.getIpUaKey(), "adid_filed",
						paraIpAppendPvInfo.getAdId());
				pl.hset(paraIpAppendPvInfo.getIpUaKey(), "ts_filed",
						paraIpAppendPvInfo.getTs());
				pl.expire(paraIpAppendPvInfo.getIpUaKey(), 1800);

				pl.sync();
			}
		} catch (Exception e) {
			logger.error("saveIpAppendPvToRedis into redis failed!", e);
		} finally {
			paraJedisPool.releaseJedis(redis);
		}

		return true;
	}

	/**
	 * 获取绑定对应广告ID
	 * 
	 * @param paraAdId
	 * @param paraJedisPool
	 * @return
	 * @author zhoubin
	 */
	private String getAppendAdIdFromRedis(String paraAdId,
			JedisPoolWriper paraJedisPool) {

		String appendAdId = "";

		Jedis redis = paraJedisPool.getJedis();
		try {
			String ipAppendKey = Constant.AD_IP_APPED_REL_KEY_PREFIX + paraAdId;

			appendAdId = redis.get(ipAppendKey);

		} catch (Exception e) {
			logger.error("saveIpAppendPvToRedis into redis failed!", e);
		} finally {
			paraJedisPool.releaseJedis(redis);
		}

		return appendAdId;
	}

	public static void main(String[] args) {
		Jedis jedis = new Jedis("114.111.162.219");

		Pipeline pl = jedis.pipelined();

		long start = System.currentTimeMillis();

		for (int i = 0; i < 30; i++) {
			pl.sadd("kaka", i + "");

		}

		pl.sync();

		System.out.println(pl);

		Pipeline pl1 = jedis.pipelined();

		System.out.println(pl1);

		long end = System.currentTimeMillis();

		System.out.println(end - start);
	}

}
