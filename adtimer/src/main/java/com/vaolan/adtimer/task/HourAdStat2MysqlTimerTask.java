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
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import net.sf.json.JSONObject;

import com.hidata.framework.cache.redis.RedisUtil;
import com.hidata.framework.util.DateUtil;
import com.hidata.framework.util.JsonUtil;
import com.mysql.jdbc.TimeUtil;
import com.vaolan.adtimer.model.AdStatBase;
import com.vaolan.adtimer.model.AdStatInfo;
import com.vaolan.adtimer.model.MaterialStatBase;
import com.vaolan.adtimer.service.StatService;
import com.vaolan.adtimer.util.Constant;
import com.vaolan.adtimer.util.TimerUtil;

/**
 * 每隔一个小时，把redis中的hour数据清零，并同步到数据库，进行下一个hour的计数
 * 
 * @author chenjinzhao
 * 
 */
@Component
public class HourAdStat2MysqlTimerTask extends TimerTask {

	@Autowired
	StatService statService;

	private static Logger hourlogger = LoggerFactory
			.getLogger("adtimer_hour");

	@Override
	public void run() {
		try {
			hourlogger.info("小时同步：" + DateUtil.getCurrentDateTimeStr());

			long start = System.currentTimeMillis();
			AdStatInfoToMysqlHour();
			MaStatInfoToMysqlHour();
			long end = System.currentTimeMillis();

			long t = end - start;

			hourlogger.info("小时同步，一共用时:" + t);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			hourlogger.error("每小时同步到广告统计信息到数据库出错:" + e.getMessage(), e);
		}
	}

	public void AdStatInfoToMysqlHour() throws Exception {

		Jedis adclient = RedisUtil.getJedisClientFromPool();
		// 得到所有广告统计信息在redis中的key，然后一个一个的处理
		Set<String> adStatKeys = adclient.keys(Constant.ADSTAT_KEYS_PATTERN);

		/**
		 * 新的一个小时开始，把上一个小时的数据同步到数据库，同时hour数据清零
		 */
		String lastHourBeginDateTimeStr = TimerUtil
				.getPreviousHourBeginDateTimeStr();
		hourlogger.info("previous_hour:" + lastHourBeginDateTimeStr);
		
		
		List<AdStatBase> adStatBaseLastHourList = new ArrayList<AdStatBase>();
		List<AdStatBase> adStatBaseCurrentHourList = new ArrayList<AdStatBase>();
		

		
		
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
				adStatBaseHour.setTs(lastHourBeginDateTimeStr);
				
				//关闭按钮
				adStatBaseHour.setCloseNum(adStatInfo.getCurrentHour_close_num());
				
				adStatBaseLastHourList.add(adStatBaseHour);
				//statService.processAdStatNum(adStatBaseHour,true);
				
				
				
				//新插入一条当前小时的 的记录
				AdStatBase adStatBaseHourNew = new AdStatBase();
				adStatBaseHourNew.setAdId(adId);
				adStatBaseHourNew.setNumType(Constant.STAT_NUM_TYPE_HOUR);
				adStatBaseHourNew.setPvNum("0");
				adStatBaseHourNew.setClickNum("0");
				adStatBaseHourNew.setIpNum("0");
				adStatBaseHourNew.setMobileClickNum("0");
				adStatBaseHourNew.setMobilePvNum("0");
				adStatBaseHourNew.setUvNum("0");
				adStatBaseHourNew.setTs(TimerUtil.getCurrentHourBeginDateTimeStr());
				
				
				//关闭按钮
				adStatBaseHourNew.setCloseNum("0");
				
				adStatBaseCurrentHourList.add(adStatBaseHourNew);
				//statService.processAdStatNum(adStatBaseHourNew,false);
				
				

				// 删除太慢，直接改名字，然后改后的名字一分钟失效
				
				if(adclient.exists(Constant.AD_STAT_HOUR_UV_KEY_PREFIX + adId)){
					adclient.rename(Constant.AD_STAT_HOUR_UV_KEY_PREFIX + adId, Constant.AD_STAT_HOUR_UV_KEY_PREFIX + adId+"_old");
					adclient.expire(Constant.AD_STAT_HOUR_UV_KEY_PREFIX + adId+"_old", 60);
				}
				
				if(adclient.exists(Constant.AD_STAT_HOUR_IP_KEY_PREFIX + adId)){
					adclient.rename(Constant.AD_STAT_HOUR_IP_KEY_PREFIX + adId, Constant.AD_STAT_HOUR_IP_KEY_PREFIX + adId+"_old");
					adclient.expire(Constant.AD_STAT_HOUR_IP_KEY_PREFIX + adId+"_old", 60);
				}

				adclient.hset(adkey, "currentHour_click_num", "0");
				adclient.hset(adkey, "currentHour_ip_num", "0");
				adclient.hset(adkey, "currentHour_mobile_click_num", "0");
				adclient.hset(adkey, "currentHour_mobile_pv_num", "0");
				adclient.hset(adkey, "currentHour_pv_num", "0");
				adclient.hset(adkey, "currentHour_uv_num", "0");
				adclient.hset(adkey, "currentHour_close_num", "0");

			} catch (Exception e) {
				hourlogger.error(
						"广告小时同步其中一条出错：key=" + adkey + "," + e.getMessage(), e);

			}

		}
		

		
		RedisUtil.releaseJedisClientFromPool(adclient);
		
		statService.processAdStatNumUpdateBatch(adStatBaseLastHourList);
		
		statService.processAdStatNumSaveBatch(adStatBaseCurrentHourList);
	}
	
	
	
	
	
	public void MaStatInfoToMysqlHour() throws Exception {
		
		Jedis maclient = RedisUtil.getJedisClientFromPool();
		
		String lastHourBeginDateTimeStr = TimerUtil
				.getPreviousHourBeginDateTimeStr();
		
		Set<String> materialStatKeys = maclient.keys(Constant.MATERIALSTAT_KEYS_PATTERN);

		List<MaterialStatBase> materialStatBaseLastHourList = new ArrayList<MaterialStatBase>();
		List<MaterialStatBase> materialStatBaseCurrentHourList = new ArrayList<MaterialStatBase>();
		
		
		for (String makey : materialStatKeys) {

			try {
				Map<String, String> adStatMap = maclient.hgetAll(makey);
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
				

				String[] strs = makey.split("_");
				String materialId = strs[strs.length - 1];

				if (StringUtils.isBlank(materialId) || !StringUtils.isNumeric(materialId)) {
					continue;
				}
				
				
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
				materialStatBaseHour.setTs(lastHourBeginDateTimeStr);

				
				materialStatBaseLastHourList.add(materialStatBaseHour);
				//statService.processMaterialStatNum(materialStatBaseHour,true);
				
				
				
				
				// 新插入一条记录  小时的记录
				MaterialStatBase materialStatBaseHourNew = new MaterialStatBase();
				materialStatBaseHourNew.setMaterial_id(materialId);
				materialStatBaseHourNew.setNumType(Constant.STAT_NUM_TYPE_HOUR);
				materialStatBaseHourNew.setPvNum("0");
				materialStatBaseHourNew.setClickNum("0");
				materialStatBaseHourNew.setIpNum("0");
				materialStatBaseHourNew.setMobileClickNum("0");
				materialStatBaseHourNew.setMobilePvNum("0");
				materialStatBaseHourNew.setUvNum("0");
				materialStatBaseHourNew.setTs(TimerUtil.getCurrentHourBeginDateTimeStr());

				
				materialStatBaseCurrentHourList.add(materialStatBaseHourNew);
				//statService.processMaterialStatNum(materialStatBaseHourNew,false);
				
				

				//删除太慢，直接改名字，然后把新名字一分钟失效
				
				if(maclient.exists(Constant.MATERIAL_STAT_HOUR_UV_KEY_PREFIX+ materialId)){
					maclient.rename(Constant.MATERIAL_STAT_HOUR_UV_KEY_PREFIX+ materialId, Constant.MATERIAL_STAT_HOUR_UV_KEY_PREFIX+ materialId+"_old");
					maclient.expire(Constant.MATERIAL_STAT_HOUR_UV_KEY_PREFIX+ materialId+"_old", 60);

				}
				
				if(maclient.exists(Constant.MATERIAL_STAT_HOUR_IP_KEY_PREFIX+ materialId)){
					maclient.rename(Constant.MATERIAL_STAT_HOUR_IP_KEY_PREFIX+ materialId, Constant.MATERIAL_STAT_HOUR_IP_KEY_PREFIX+ materialId+"_old");
					maclient.expire(Constant.MATERIAL_STAT_HOUR_IP_KEY_PREFIX+ materialId+"_old", 60);
				}
				
				
				maclient.hset(makey, "currentHour_click_num", "0");
				maclient.hset(makey, "currentHour_ip_num", "0");
				maclient.hset(makey, "currentHour_mobile_click_num", "0");
				maclient.hset(makey, "currentHour_mobile_pv_num", "0");
				maclient.hset(makey, "currentHour_pv_num", "0");
				maclient.hset(makey, "currentHour_uv_num", "0");

			} catch (Exception e) {
				hourlogger.error(
						"物料小时同步其中一条出错：key=" + makey + "," + e.getMessage(), e);

			}
		}
		
		RedisUtil.releaseJedisClientFromPool(maclient);
		
		statService.processMaterialStatNumUpdateBatch(materialStatBaseLastHourList);
		statService.processMaterialStatNumSaveBatch(materialStatBaseCurrentHourList);
	}

	
	
	
	
	public static void main(String[] args) {

		while (true) {
			System.out.println(TimerUtil.getPreviousHourBeginDateTimeStr());
		}

	}
}
