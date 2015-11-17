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
public class RtIpUv2HostMainKeyTimerTask extends TimerTask {

	@Autowired
	StatService statService;

	private static Logger logger = LoggerFactory
			.getLogger(RtIpUv2HostMainKeyTimerTask.class);

	@Override
	public void run() {

		try {
			logger.debug("实时计算每个域名下的ip，uv数量：" + DateUtil.getCurrentDateTimeStr());
			
			long start = System.currentTimeMillis();
			//域名为度
			this.HostIpUvCalc();
			long end = System.currentTimeMillis();
			long t = end-start;
			logger.debug("实时计算每个域名ip,uv数量，一共用时:"+t);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("实时计算ip，uv数量出错:" + e.getMessage(), e);

		}

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

	public static void main(String[] args) throws Exception {
		/*Set<String> keys = RedisUtil.keys("adstat_*");

		System.out.println(keys);*/
		
		RtIpUv2HostMainKeyTimerTask rtIpUv2MainKeyTimerTask = new RtIpUv2HostMainKeyTimerTask();
		rtIpUv2MainKeyTimerTask.HostIpUvCalc();
	}

}
