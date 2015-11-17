package com.vaolan.adtimer.task;

import java.util.Set;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

import com.hidata.framework.cache.redis.RedisUtil;
import com.hidata.framework.util.DateUtil;
import com.hidata.framework.util.StringUtil;
import com.vaolan.adtimer.service.StatService;
import com.vaolan.adtimer.util.Constant;

/**
 * 实时计算广告的ip数量，uv数量，到广告信息统计中
 * 
 * @author chenjinzhao
 * 
 */
@Component
public class RtIpUv2MainKeyTimerTask extends TimerTask {

	@Autowired
	StatService statService;

	private static Logger logger = LoggerFactory
			.getLogger(RtIpUv2MainKeyTimerTask.class);

	@Override
	public void run() {

		try {
			logger.debug("实时计算ip，uv数量：" + DateUtil.getCurrentDateTimeStr());
			
			long start = System.currentTimeMillis();
			
			//广告维度
			this.AdIpUvCalc();
			//物料维度
			this.MaterialIpUvCalc();
			long end = System.currentTimeMillis();
			long t = end-start;
			logger.debug("实时计算ip,uv数量，一共用时:"+t);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("实时计算ip，uv数量出错:" + e.getMessage(), e);

		}

	}

	/**
	 * 把广告当天的uv数量，ip数量，当前小时的uv数量，ip数量，算出来写入ADSTAT_MAIN_adId中
	 * 
	 * @author chenjinzhao
	 * @throws Exception
	 */
	private void AdIpUvCalc() throws Exception {

		// 得到所有广告统计信息在redis中的key，然后一个一个的处理

		Jedis client = RedisUtil.getJedisClientFromPool();
		Set<String> adStatKeys = client.keys(Constant.ADSTAT_KEYS_PATTERN);

		for (String adStatKey : adStatKeys) {

			try {
				String[] strs = adStatKey.split("_");
				String adId = strs[strs.length - 1];

				if (StringUtils.isBlank(adId)) {
					continue;
				}

				if (client.exists(Constant.AD_STAT_DAY_UV_KEY_PREFIX + adId)) {
					Long adDayUvNum = client
							.scard(Constant.AD_STAT_DAY_UV_KEY_PREFIX + adId);
					client.hset(Constant.AD_STAT_KEY_PREFIX + adId,
							"today_uv_num", adDayUvNum.toString());
				}

				if (client.exists(Constant.AD_STAT_DAY_IP_KEY_PREFIX + adId)) {
					Long adDayIpNum = client
							.scard(Constant.AD_STAT_DAY_IP_KEY_PREFIX + adId);
					client.hset(Constant.AD_STAT_KEY_PREFIX + adId,
							"today_ip_num", adDayIpNum.toString());
				}

				if (client.exists(Constant.AD_STAT_HOUR_UV_KEY_PREFIX + adId)) {
					Long adHourUvNum = client
							.scard(Constant.AD_STAT_HOUR_UV_KEY_PREFIX + adId);
					client.hset(Constant.AD_STAT_KEY_PREFIX + adId,
							"currentHour_uv_num", adHourUvNum.toString());
				}

				if (client.exists(Constant.AD_STAT_HOUR_IP_KEY_PREFIX + adId)) {
					Long adHourIpNum = client
							.scard(Constant.AD_STAT_HOUR_IP_KEY_PREFIX + adId);
					client.hset(Constant.AD_STAT_KEY_PREFIX + adId,
							"currentHour_ip_num", adHourIpNum.toString());
				}
			} catch (Exception e) {
				logger.error(
						"实时计算广告ip，uv数量其中一条出错：" + adStatKey + "," + e.getMessage(),
						e);
			}

		}
		RedisUtil.releaseJedisClientFromPool(client);
	}
	
	/**
	 * 把域名 每天访问的 IP  和 UV 数量算出来, 写入到 ad_host_key_host_adId为key里面
	 * @throws Exception
	 */
	private void HostIpUvCalc() throws Exception{
		//获取redis客户端
		Jedis client = RedisUtil.getJedisClientFromPool();
		Set<String> adHostKeys = client.keys(Constant.Ad_HOST_KEY_PATTERN);
		try {
			
			if(adHostKeys != null && adHostKeys.size() > 0){
				for(String adHostKey : adHostKeys){
					String[] strs = adHostKey.split("_");
					String adId = strs[strs.length - 1];
					String host = strs[strs.length - 2];
					
					if(StringUtils.isBlank(adId)){
						continue;
					}
					if(StringUtils.isBlank(host)){
						continue;
					}
					
					if (client.exists(Constant.AD_HOST_UV + host + "_" + adId)) {
						Long adDayUvNum = client.scard(Constant.AD_HOST_UV + host + "_" + adId);
						client.hset(Constant.AD_HOST_KEY + host + "_" + adId,"dayUv_host", adDayUvNum.toString());
					}
					
					if(client.exists(Constant.AD_HOST_IP + host + "_" + adId)) {
						Long adDayIpNum = client.scard(Constant.AD_HOST_IP + host + "_" + adId);
						client.hset(Constant.AD_HOST_KEY + host + "_" + adId,"dayIp_host", adDayIpNum.toString());
					}
				}
			}
		} catch (Exception e) {
			logger.error(
					"一域名为维度;实时计算广告ip，uv数量其中一条出错：" + adHostKeys + "," + e.getMessage(),
					e);
		} finally{
			RedisUtil.releaseJedisClientFromPool(client);
		}
		
		
	}

	/**
	 * 物料当天的uv数量，ip数量，当前小时的uv数量，ip数量，算出来写入ADSTAT_adId中
	 * 
	 * @author chenjinzhao
	 * @throws Exception
	 */
	private void MaterialIpUvCalc() throws Exception {

		// 得到所有物料统计信息在redis中的key，然后一个一个的处理

		Jedis client = RedisUtil.getJedisClientFromPool();
		Set<String> maStatKeys = client
				.keys(Constant.MATERIALSTAT_KEYS_PATTERN);

		for (String maStatKey : maStatKeys) {

			try {

				String[] strs = maStatKey.split("_");
				String maId = strs[strs.length - 1];

				if (StringUtils.isBlank(maId)) {
					continue;
				}

				if (client.exists(Constant.MATERIAL_STAT_DAY_UV_KEY_PREFIX
						+ maId)) {
					Long maDayUvNum = client
							.scard(Constant.MATERIAL_STAT_DAY_UV_KEY_PREFIX
									+ maId);
					client.hset(Constant.MATERIAL_STAT_KEY_PREFIX + maId,
							"today_uv_num", maDayUvNum.toString());
				}

				if (client.exists(Constant.MATERIAL_STAT_DAY_IP_KEY_PREFIX
						+ maId)) {
					Long maDayIpNum = client
							.scard(Constant.MATERIAL_STAT_DAY_IP_KEY_PREFIX
									+ maId);
					client.hset(Constant.MATERIAL_STAT_KEY_PREFIX + maId,
							"today_ip_num", maDayIpNum.toString());
				}

				if (client.exists(Constant.MATERIAL_STAT_HOUR_UV_KEY_PREFIX
						+ maId)) {
					Long maHourUvNum = client
							.scard(Constant.MATERIAL_STAT_HOUR_UV_KEY_PREFIX
									+ maId);
					client.hset(Constant.MATERIAL_STAT_KEY_PREFIX + maId,
							"currentHour_uv_num", maHourUvNum.toString());
				}

				if (client.exists(Constant.MATERIAL_STAT_HOUR_IP_KEY_PREFIX
						+ maId)) {
					Long maHourIpNum = client
							.scard(Constant.MATERIAL_STAT_HOUR_IP_KEY_PREFIX
									+ maId);
					client.hset(Constant.MATERIAL_STAT_KEY_PREFIX + maId,
							"currentHour_ip_num", maHourIpNum.toString());
				}

			} catch (Exception e) {
				logger.error(
						"实时计算物料ip，uv数量其中一条出错：" + maStatKey + ","
								+ e.getMessage(), e);

			}
		}
		RedisUtil.releaseJedisClientFromPool(client);

	}

	public static void main(String[] args) throws Exception {
		/*Set<String> keys = RedisUtil.keys("adstat_*");

		System.out.println(keys);*/
		
		RtIpUv2MainKeyTimerTask rtIpUv2MainKeyTimerTask = new RtIpUv2MainKeyTimerTask();
		rtIpUv2MainKeyTimerTask.HostIpUvCalc();
	}

}
