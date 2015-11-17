package com.vaolan.adtimer.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

import com.hidata.framework.cache.redis.RedisUtil;
import com.hidata.framework.util.DateUtil;
import com.vaolan.adtimer.model.AdHostStatBase;
import com.vaolan.adtimer.model.AdStatBase;
import com.vaolan.adtimer.model.AdStatInfo;
import com.vaolan.adtimer.model.MaterialStatBase;
import com.vaolan.adtimer.service.StatService;
import com.vaolan.adtimer.util.Constant;
import com.vaolan.adtimer.util.TimerUtil;

/**
 * 实时把redis中广告pv，点击的信息同步到mysql供前台查询， 不是准实时的，是每隔1分钟同步一次
 * 
 * @author chenjinzhao
 * 
 */
@Component
public class RtAdStat2MysqlTimerTask extends TimerTask {

	@Autowired
	StatService statService;

	private static Logger minlogger = LoggerFactory
			.getLogger("adtimer_min");

	@Override
	public void run() {

		try {
			minlogger.info("1分钟实时同步到广告统计信息到数据库："
					+ DateUtil.getCurrentDateTimeStr());

			long start = System.currentTimeMillis();
			AdStatInfoToMysqlRT();
			adHostStatInfoToMysqlMin();
			long end = System.currentTimeMillis();

			long t = end - start;

			minlogger.info("1分钟实时同步到广告统计信息到数据库,一共用时:" + t);
		} catch (Exception e) {
			e.printStackTrace();
			minlogger.error("1分钟实时同步到广告统计信息到数据库出错:" + e.getMessage(), e);

		}

	}

	private void AdStatInfoToMysqlRT() throws Exception {

		Jedis client = RedisUtil.getJedisClientFromPool();
		// 得到所有广告统计信息在redis中的key，然后一个一个的处理
		Set<String> adStatKeys = client.keys(Constant.ADSTAT_KEYS_PATTERN);

		Set<String> materialStatKeys = client
				.keys(Constant.MATERIALSTAT_KEYS_PATTERN);

		String currentDayBeginDateTimeStr = TimerUtil
				.getCurrentDayBeginDateTimeStr();

		String currentHourBeginDateTimeStr = TimerUtil
				.getCurrentHourBeginDateTimeStr();

		
		List<AdStatBase> adStatBaseList = new ArrayList<AdStatBase>();

		for (String key : adStatKeys) {

			try {
				
				
				Map<String, String> adStatMap = client.hgetAll(key);
				// 把一个广告的所有统计信息，从redis中取出来，组成一个对象
				
				
				//JSONObject adStatJSON = JSONObject.fromObject(adStatMap);
				
				AdStatInfo adStatInfo = new AdStatInfo();
				
				adStatInfo.setAdId(adStatMap.get("adId"));
				adStatInfo.setMaterialId(adStatMap.get("materialId"));
				adStatInfo.setTotal_pv_num(adStatMap.get("total_pv_num"));
				adStatInfo.setTotal_click_num(adStatMap.get("total_click_num"));
				adStatInfo.setToday_pv_num(adStatMap.get("today_pv_num"));
				adStatInfo.setToday_click_num(adStatMap.get("today_click_num"));
				adStatInfo.setToday_uv_num(adStatMap.get("today_uv_num"));
				adStatInfo.setToday_ip_num(adStatMap.get("today_ip_num"));
				adStatInfo.setToday_mobile_pv_num(adStatMap.get("today_mobile_pv_num"));
				adStatInfo.setToday_mobile_click_num(adStatMap.get("today_mobile_click_num"));
			    adStatInfo.setCurrentHour_pv_num(adStatMap.get("currentHour_pv_num"));
			    adStatInfo.setCurrentHour_click_num(adStatMap.get("currentHour_click_num"));
				adStatInfo.setCurrentHour_mobile_pv_num(adStatMap.get("currentHour_mobile_pv_num"));
				adStatInfo.setCurrentHour_mobile_click_num(adStatMap.get("currentHour_mobile_click_num"));
				adStatInfo.setCurrentHour_uv_num(adStatMap.get("currentHour_uv_num"));
				adStatInfo.setCurrentHour_ip_num(adStatMap.get("currentHour_ip_num"));
				
				//点击关闭按钮
				adStatInfo.setCurrentHour_close_num(adStatMap.get("currentHour_close_num"));
				adStatInfo.setToday_close_num(adStatMap.get("today_close_num"));


				
				
				String[] strs = key.split("_");
				String adId = strs[strs.length - 1];

				if (StringUtils.isBlank(adId) || !StringUtils.isNumeric(adId)) {
					continue;
				}
				
				
				// 同步天的记录
				AdStatBase adStatBaseDay = new AdStatBase();
				adStatBaseDay.setAdId(adId);
				adStatBaseDay.setNumType(Constant.STAT_NUM_TYPE_DAY);
				adStatBaseDay.setPvNum(adStatInfo.getToday_pv_num());
				adStatBaseDay.setClickNum(adStatInfo.getToday_click_num());
				adStatBaseDay.setIpNum(adStatInfo.getToday_ip_num());
				adStatBaseDay.setMobileClickNum(adStatInfo
						.getToday_mobile_click_num());
				adStatBaseDay.setMobilePvNum(adStatInfo
						.getToday_mobile_pv_num());
				adStatBaseDay.setUvNum(adStatInfo.getToday_uv_num());
				adStatBaseDay.setTs(currentDayBeginDateTimeStr);
				
				
				adStatBaseDay.setCloseNum(adStatInfo.getToday_close_num());
				adStatBaseList.add(adStatBaseDay);
				//statService.processAdStatNum(adStatBaseDay, true);

				// 同步小时的记录
				AdStatBase adStatBaseHour = new AdStatBase();
				adStatBaseHour.setAdId(adId);
				adStatBaseHour.setNumType(Constant.STAT_NUM_TYPE_HOUR);
				adStatBaseHour.setPvNum(adStatInfo.getCurrentHour_pv_num());
				adStatBaseHour.setClickNum(adStatInfo
						.getCurrentHour_click_num());
				adStatBaseHour.setIpNum(adStatInfo.getCurrentHour_ip_num());
				adStatBaseHour.setMobileClickNum(adStatInfo
						.getCurrentHour_mobile_click_num());
				adStatBaseHour.setMobilePvNum(adStatInfo
						.getCurrentHour_mobile_pv_num());
				adStatBaseHour.setUvNum(adStatInfo.getCurrentHour_uv_num());
				adStatBaseHour.setTs(currentHourBeginDateTimeStr);
				
				adStatBaseHour.setCloseNum(adStatInfo.getCurrentHour_close_num());
				
				adStatBaseList.add(adStatBaseHour);
				//statService.processAdStatNum(adStatBaseHour, true);
				
				
				
				

			} catch (Exception e) {
				minlogger.error(
						"实时同步到广告统计信息到数据库其中一条出错: key=" + key + ","
								+ e.getMessage(), e);
			}

		}
		
		List<MaterialStatBase> materialStatBaseList = new ArrayList<MaterialStatBase>();

		for (String key : materialStatKeys) {

			try {
				
				
				Map<String, String> adStatMap = client.hgetAll(key);
				// 把一个广告的所有统计信息，从redis中取出来，组成一个对象
				//JSONObject adStatJSON = JSONObject.fromObject(adStatMap);
				
				AdStatInfo adStatInfo = new AdStatInfo();
				
				adStatInfo.setAdId(adStatMap.get("adId"));
				adStatInfo.setMaterialId(adStatMap.get("materialId"));
				adStatInfo.setTotal_pv_num(adStatMap.get("total_pv_num"));
				adStatInfo.setTotal_click_num(adStatMap.get("total_click_num"));
				adStatInfo.setToday_pv_num(adStatMap.get("today_pv_num"));
				adStatInfo.setToday_click_num(adStatMap.get("today_click_num"));
				adStatInfo.setToday_uv_num(adStatMap.get("today_uv_num"));
				adStatInfo.setToday_ip_num(adStatMap.get("today_ip_num"));
				adStatInfo.setToday_mobile_pv_num(adStatMap.get("today_mobile_pv_num"));
				adStatInfo.setToday_mobile_click_num(adStatMap.get("today_mobile_click_num"));
			    adStatInfo.setCurrentHour_pv_num(adStatMap.get("currentHour_pv_num"));
			    adStatInfo.setCurrentHour_click_num(adStatMap.get("currentHour_click_num"));
				adStatInfo.setCurrentHour_mobile_pv_num(adStatMap.get("currentHour_mobile_pv_num"));
				adStatInfo.setCurrentHour_mobile_click_num(adStatMap.get("currentHour_mobile_click_num"));
				adStatInfo.setCurrentHour_uv_num(adStatMap.get("currentHour_uv_num"));
				adStatInfo.setCurrentHour_ip_num(adStatMap.get("currentHour_ip_num"));
				

				String[] strs = key.split("_");
				String materialId = strs[strs.length - 1];

				if (StringUtils.isBlank(materialId) || !StringUtils.isNumeric(materialId)) {
					continue;
				}

				// 同步天的记录
				MaterialStatBase materialStatBaseDay = new MaterialStatBase();
				materialStatBaseDay.setMaterial_id(materialId);
				materialStatBaseDay.setNumType(Constant.STAT_NUM_TYPE_DAY);
				materialStatBaseDay.setPvNum(adStatInfo.getToday_pv_num());
				materialStatBaseDay
						.setClickNum(adStatInfo.getToday_click_num());
				materialStatBaseDay.setIpNum(adStatInfo.getToday_ip_num());
				materialStatBaseDay.setMobileClickNum(adStatInfo
						.getToday_mobile_click_num());
				materialStatBaseDay.setMobilePvNum(adStatInfo
						.getToday_mobile_pv_num());
				materialStatBaseDay.setUvNum(adStatInfo.getToday_uv_num());
				materialStatBaseDay.setTs(currentDayBeginDateTimeStr);

				
				materialStatBaseList.add(materialStatBaseDay);
				//statService.processMaterialStatNum(materialStatBaseDay, true);

				// 同步小时的记录
				MaterialStatBase materialStatBaseHour = new MaterialStatBase();
				materialStatBaseHour.setMaterial_id(materialId);
				materialStatBaseHour.setNumType(Constant.STAT_NUM_TYPE_HOUR);
				materialStatBaseHour.setPvNum(adStatInfo
						.getCurrentHour_pv_num());
				materialStatBaseHour.setClickNum(adStatInfo
						.getCurrentHour_click_num());
				materialStatBaseHour.setIpNum(adStatInfo
						.getCurrentHour_ip_num());
				materialStatBaseHour.setMobileClickNum(adStatInfo
						.getCurrentHour_mobile_click_num());
				materialStatBaseHour.setMobilePvNum(adStatInfo
						.getCurrentHour_mobile_pv_num());
				materialStatBaseHour.setUvNum(adStatInfo
						.getCurrentHour_uv_num());
				materialStatBaseHour.setTs(currentHourBeginDateTimeStr);

				
				materialStatBaseList.add(materialStatBaseHour);
				//statService.processMaterialStatNum(materialStatBaseHour, true);
				
				
			} catch (Exception e) {
				minlogger.error(
						"实时同步到物料统计信息到数据库其中一条出错: key=" + key + ","
								+ e.getMessage(), e);

			}
		}
		
		
		RedisUtil.releaseJedisClientFromPool(client);
		
		statService.processAdStatNumUpdateBatch(adStatBaseList);
		statService.processMaterialStatNumUpdateBatch(materialStatBaseList);
		
	}
	/**
	 * 广告点击 展现 域名 为维度的 统计信息
	 */
	private void adHostStatInfoToMysqlMin(){
		Jedis adHostclient = RedisUtil.getJedisClientFromPool();
		
		// 得到所有广告统计信息在redis中的key，然后一个一个的处理
		Set<String> adHostStatKeys = adHostclient.keys(Constant.Ad_HOST_KEY_PATTERN);

		List<AdHostStatBase> adHostStatBaseDayList = new ArrayList<AdHostStatBase>();
		//List<AdHostStatBase> adHostStatBaseTodayDayList = new ArrayList<AdHostStatBase>();

		
		
		for (String adHostkey : adHostStatKeys) {

			try {
				Map<String, String> adHostStatMap = adHostclient.hgetAll(adHostkey);
				
				String[] strs = adHostkey.split("_");
				String adId = strs[strs.length - 1];
				String host = strs[strs.length - 2];
				
				if (StringUtils.isBlank(adId) || !StringUtils.isNumeric(adId)) {
					continue;
				}
				
				if(StringUtils.isBlank(host)){
					continue;
				}
				AdHostStatBase adHostStatInfo = new AdHostStatBase();
				
				adHostStatInfo.setHost_key(adHostStatMap.get("host_key"));
				
				adHostStatInfo.setAdId_key(adHostStatMap.get("adId_key"));
				
				adHostStatInfo.setDayPv_host(adHostStatMap.get("dayPv_host"));
				
				adHostStatInfo.setDayUv_host(adHostStatMap.get("dayUv_host"));
				
				adHostStatInfo.setDayIp_host(adHostStatMap.get("dayIp_host"));
				
				adHostStatInfo.setDayClick_host(adHostStatMap.get("dayClick_host"));
				
				adHostStatInfo.setTs(TimerUtil.getCurrentDayBeginDateTimeStr());
				
				
				
				adHostStatBaseDayList.add(adHostStatInfo);
				
				
				
				

				// 删除太慢，直接改名字，然后一分钟后失效
				
				/*if(adHostclient.exists(Constant.AD_HOST_UV + host + "_" + adId)){
					adHostclient.rename(Constant.AD_HOST_UV + host + "_" + adId, Constant.AD_HOST_UV + host + "_" + adId +"_old");
					adHostclient.expire(Constant.AD_HOST_UV + host + "_" + adId +"_old", 60);


				}
				
				
				if(adHostclient.exists(Constant.AD_HOST_IP + host + "_" + adId)){
					adHostclient.rename(Constant.AD_HOST_IP + host + "_" + adId, Constant.AD_HOST_IP + host + "_" + adId +"_old");
					adHostclient.expire(Constant.AD_HOST_IP + host + "_" + adId +"_old", 60);
					
				}*/
				

				
				//删除当天key
				//adHostclient.del(adHostkey);

			} catch (Exception e) {
				minlogger.error("广告维度 ; 广告分钟同步其中一条出错：key=" + adHostkey + "," + e.getMessage(),e);

			}
			
		}
		
		RedisUtil.releaseJedisClientFromPool(adHostclient);
		
		//根据广告ID和时间删除所有数据,从新更新
		
		statService.deleteHostDBByadIdAndts(adHostStatBaseDayList);
		//更改昨天数据库数据;
		//statService.processAdHostStatNumUpdateBatch(adHostStatBaseDayList);
		
		//插叙今天数据库数据;
		
		statService.processAdHostStatNumSaveBatch(adHostStatBaseDayList);
		
	}
	
	public static void main(String[] args) throws Exception {
		/*minlogger.info("1分钟实时同步到广告统计信息到数据库：" + DateUtil.getCurrentDateTimeStr());

		RtAdStat2MysqlTimerTask ra = new RtAdStat2MysqlTimerTask();

		long start = System.currentTimeMillis();
		ra.AdStatInfoToMysqlRT();

		long end = System.currentTimeMillis();

		long t = end - start;
		minlogger.info("1分钟实时同步到广告统计信息到数据库,一共用时:" + t);*/
		System.out.println(TimerUtil.getCurrentDayBeginDateTimeStr());
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		RtAdStat2MysqlTimerTask rtAdStat2MysqlTimerTask = (RtAdStat2MysqlTimerTask) ctx
				.getBean("rtAdStat2MysqlTimerTask");
		rtAdStat2MysqlTimerTask.adHostStatInfoToMysqlMin();
		
	}

}
