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
 * 每隔一个小时，把redis中的today数据清零，并同步到数据库，进行下一个today的计数
 * 
 * @author chenjinzhao
 * 
 */
@Component
public class DayAdStat2MysqlTimerTask extends TimerTask {

	@Autowired
	StatService statService;

	private static Logger daylogger = LoggerFactory
			.getLogger("adtimer_day");

	@Override
	public void run() {
		try {
			daylogger.info("天同步：" + DateUtil.getCurrentDateTimeStr());
			
			long start = System.currentTimeMillis();
			
			AdStatInfoToMysqlDay();
			
			this.adHostStatInfoToMysqlDay();
			long end = System.currentTimeMillis();

			long t = end - start;

			daylogger.info("天同步，一共用时:" + t);

		} catch (Exception e) {
			e.printStackTrace();
			daylogger.error("每天同步到广告统计信息到数据库出错:" + e.getMessage(), e);

		}
	}

	private void AdStatInfoToMysqlDay() throws Exception {

		
		Jedis adclient = RedisUtil.getJedisClientFromPool();
		
		// 得到所有广告统计信息在redis中的key，然后一个一个的处理
		Set<String> adStatKeys = adclient.keys(Constant.ADSTAT_KEYS_PATTERN);


		String lastDayBeginDateTimeStr = TimerUtil
				.getPreviousDayBeginDateTimeStr();

		
		List<AdStatBase> adStatBaseLastDayList = new ArrayList<AdStatBase>();
		List<AdStatBase> adStatBaseTodayDayList = new ArrayList<AdStatBase>();

		
		
		for (String adkey : adStatKeys) {

			try {
				Map<String, String> adStatMap = adclient.hgetAll(adkey);
				// 把一个广告的所有统计信息，从redis中取出来，组成一个对象
				
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
				
				String[] strs = adkey.split("_");
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
				adStatBaseDay.setMobileClickNum(adStatInfo.getToday_mobile_click_num());
				adStatBaseDay.setMobilePvNum(adStatInfo.getToday_mobile_pv_num());
				adStatBaseDay.setUvNum(adStatInfo.getToday_uv_num());
				adStatBaseDay.setTs(lastDayBeginDateTimeStr);
				
				//点击关闭按钮
				adStatBaseDay.setCloseNum(adStatInfo.getToday_close_num());
			

				
				adStatBaseLastDayList.add(adStatBaseDay);
				//statService.processAdStatNum(adStatBaseDay,true);
				
				
				
				
				// 新插入一条新一天的天记录数据
				AdStatBase adStatBaseDayNew = new AdStatBase();
				adStatBaseDayNew.setAdId(adId);
				adStatBaseDayNew.setNumType(Constant.STAT_NUM_TYPE_DAY);
				adStatBaseDayNew.setPvNum("0");
				adStatBaseDayNew.setClickNum("0");
				adStatBaseDayNew.setIpNum("0");
				adStatBaseDayNew.setMobileClickNum("0");
				adStatBaseDayNew.setMobilePvNum("0");
				adStatBaseDayNew.setUvNum("0");
				adStatBaseDayNew.setTs(TimerUtil.getCurrentDayBeginDateTimeStr());
				
				//点击关闭按钮
				adStatBaseDayNew.setCloseNum("0");
				
				
				adStatBaseTodayDayList.add(adStatBaseDayNew);

				// 删除太慢，直接改名字，然后一分钟后失效
				
				if(adclient.exists(Constant.AD_STAT_DAY_UV_KEY_PREFIX + adId)){
					adclient.rename(Constant.AD_STAT_DAY_UV_KEY_PREFIX + adId, Constant.AD_STAT_DAY_UV_KEY_PREFIX + adId+"_old");
					adclient.expire(Constant.AD_STAT_DAY_UV_KEY_PREFIX + adId+"_old", 60);

				}
				
				
				if(adclient.exists(Constant.AD_STAT_DAY_IP_KEY_PREFIX + adId)){
					adclient.rename(Constant.AD_STAT_DAY_IP_KEY_PREFIX + adId, Constant.AD_STAT_DAY_IP_KEY_PREFIX + adId+"_old");
					adclient.expire(Constant.AD_STAT_DAY_IP_KEY_PREFIX + adId+"_old", 60);
				}
				

				/*adclient.hset(adkey, "today_click_num", "0");
				adclient.hset(adkey, "today_ip_num", "0");
				adclient.hset(adkey, "today_mobile_click_num", "0");
				adclient.hset(adkey, "today_mobile_pv_num", "0");
				adclient.hset(adkey, "today_pv_num", "0");
				adclient.hset(adkey, "today_uv_num", "0");*/
				
				//删除当天key
				adclient.del(adkey);

			} catch (Exception e) {
				daylogger.error("广告天同步其中一条出错：key=" + adkey + "," + e.getMessage(),
						e);

			}
			
		}
		
		RedisUtil.releaseJedisClientFromPool(adclient);
		
		Jedis maclient = RedisUtil.getJedisClientFromPool();
		Set<String> materialStatKeys = maclient
				.keys(Constant.MATERIALSTAT_KEYS_PATTERN);
		
		
		List<MaterialStatBase> materialStatBaseLastDayList = new ArrayList<MaterialStatBase>();
		List<MaterialStatBase> materialStatBaseTodayDayList = new ArrayList<MaterialStatBase>();

		
		
		
		for (String makey : materialStatKeys) {
			
			try {
				
				Map<String, String> adStatMap = maclient.hgetAll(makey);
				// 把一个广告的所有统计信息，从redis中取出来，组成一个对象
				
				//System.out.println(makey);
				
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

				String[] strs = makey.split("_");
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
				materialStatBaseDay.setTs(lastDayBeginDateTimeStr);

				
				materialStatBaseLastDayList.add(materialStatBaseDay);
				//statService.processMaterialStatNum(materialStatBaseDay,true);
				
				
				
				
				//新插入一条新的天的记录
				MaterialStatBase materialStatBaseDayNew = new MaterialStatBase();
				materialStatBaseDayNew.setMaterial_id(materialId);
				materialStatBaseDayNew.setNumType(Constant.STAT_NUM_TYPE_DAY);
				materialStatBaseDayNew.setPvNum("0");
				materialStatBaseDayNew.setClickNum("0");
				materialStatBaseDayNew.setIpNum("0");
				materialStatBaseDayNew.setMobileClickNum("0");
				materialStatBaseDayNew.setMobilePvNum("0");
				materialStatBaseDayNew.setUvNum("0");
				materialStatBaseDayNew.setTs(TimerUtil.getCurrentDayBeginDateTimeStr());

				
				
				materialStatBaseTodayDayList.add(materialStatBaseDayNew);
				//statService.processMaterialStatNum(materialStatBaseDayNew,false);
				
				

				//删除太慢，直接改名字，然后一分钟后
				
				
				if(maclient.exists(Constant.MATERIAL_STAT_DAY_UV_KEY_PREFIX+ materialId)){
					maclient.rename(Constant.MATERIAL_STAT_DAY_UV_KEY_PREFIX+ materialId, Constant.MATERIAL_STAT_DAY_UV_KEY_PREFIX+ materialId+"_old");
					maclient.expire(Constant.MATERIAL_STAT_DAY_UV_KEY_PREFIX+ materialId+"_old", 60);

				}
				
				
				if(maclient.exists(Constant.MATERIAL_STAT_DAY_IP_KEY_PREFIX+ materialId)){
					maclient.rename(Constant.MATERIAL_STAT_DAY_IP_KEY_PREFIX+ materialId, Constant.MATERIAL_STAT_DAY_IP_KEY_PREFIX+ materialId+"_old");
					maclient.expire(Constant.MATERIAL_STAT_DAY_IP_KEY_PREFIX+ materialId+"_old", 60);
				}
				
				/*maclient.hset(makey, "today_click_num", "0");
				maclient.hset(makey, "today_ip_num", "0");
				maclient.hset(makey, "today_mobile_click_num", "0");
				maclient.hset(makey, "today_mobile_pv_num", "0");
				maclient.hset(makey, "today_pv_num", "0");
				maclient.hset(makey, "today_uv_num", "0");*/
				
				maclient.del(makey);

			} catch (Exception e) {
				daylogger.error(
						"物料天同步其中一条出错：key=" + makey + "," + e.getMessage(), e); 

			}
			
		}
		
		

		RedisUtil.releaseJedisClientFromPool(maclient);
		
		
		statService.processAdStatNumUpdateBatch(adStatBaseLastDayList);
		statService.processMaterialStatNumUpdateBatch(materialStatBaseLastDayList);
		
		//新的一天，不用再插入一条新的记录了，删除了key，新的一天如果再同投放的话，会自动插入当天的一天的数据，不然会偶尔冲突，ckserver插入一条，这里又插入一条
		//statService.processAdStatNumSaveBatch(adStatBaseTodayDayList);
		//statService.processMaterialStatNumSaveBatch(materialStatBaseTodayDayList);
		

	}
	
	/**
	 * 广告点击 展现 域名 为维度的 统计信息
	 */
	private void adHostStatInfoToMysqlDay(){
		Jedis adHostclient = RedisUtil.getJedisClientFromPool();
		
		// 得到所有广告统计信息在redis中的key，然后一个一个的处理
		Set<String> adHostStatKeys = adHostclient.keys(Constant.Ad_HOST_KEY_PATTERN);


		String lastDayBeginDateTimeStr = TimerUtil.getPreviousDayBeginDateTimeStr();

		
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
				
				adHostStatInfo.setTs(lastDayBeginDateTimeStr);
				
				
				
				adHostStatBaseDayList.add(adHostStatInfo);
				
				
				
				
				// 新插入一条新一天的天记录数据
				/*AdHostStatBase adHostStatInfotoDay = new AdHostStatBase();
				
				adHostStatInfotoDay.setHost_key(host);
				
				adHostStatInfotoDay.setAdId_key(adId);
				
				adHostStatInfotoDay.setDayPv_host("0");
				
				adHostStatInfotoDay.setDayUv_host("0");
				
				adHostStatInfotoDay.setDayIp_host("0");
				
				adHostStatInfotoDay.setDayClick_host("0");
				
				adHostStatInfotoDay.setTs(TimerUtil.getCurrentDayBeginDateTimeStr());*/
				
				
				
				//adHostStatBaseTodayDayList.add(adHostStatInfotoDay);

				// 删除太慢，直接改名字，然后一分钟后失效
				
				if(adHostclient.exists(Constant.AD_HOST_UV + host + "_" + adId)){
					adHostclient.rename(Constant.AD_HOST_UV + host + "_" + adId, Constant.AD_HOST_UV + host + "_" + adId +"_old");
					adHostclient.expire(Constant.AD_HOST_UV + host + "_" + adId +"_old", 60);


				}
				
				
				if(adHostclient.exists(Constant.AD_HOST_IP + host + "_" + adId)){
					adHostclient.rename(Constant.AD_HOST_IP + host + "_" + adId, Constant.AD_HOST_IP + host + "_" + adId +"_old");
					adHostclient.expire(Constant.AD_HOST_IP + host + "_" + adId +"_old", 60);
					
				}
				

				
				//删除当天key
				adHostclient.del(adHostkey);

			} catch (Exception e) {
				daylogger.error("广告维度 ; 广告天同步其中一条出错：key=" + adHostkey + "," + e.getMessage(),
						e);

			}
			
		}
		
		RedisUtil.releaseJedisClientFromPool(adHostclient);
		
		//更改昨天数据库数据;
		statService.processAdHostStatNumUpdateBatch(adHostStatBaseDayList);
		
		//插叙今天数据库数据;
		
		//statService.processAdHostStatNumSaveBatch(adHostStatBaseTodayDayList);
		
	}
	
	public static void main(String[] args) {
		
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		
		DayAdStat2MysqlTimerTask dayAdStat2MysqlTimerTask = (DayAdStat2MysqlTimerTask) ctx
				.getBean("dayAdStat2MysqlTimerTask");
		
		
		dayAdStat2MysqlTimerTask.adHostStatInfoToMysqlDay();
	}

}
