package com.vaolan.adtimer.task;

import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.hidata.framework.util.DateUtil;
import com.vaolan.adtimer.service.NHTStatService;
import com.vaolan.adtimer.util.JedisPoolFactory;


/**
 * 回复NHT统计模式
 * 
 * @author chenjinzhao
 * 
 */
@Component
public class NotNHT2NHTTimerTask extends TimerTask {

	@Autowired
	NHTStatService nhtService;

	private static Logger logger = LoggerFactory
			.getLogger(NotNHT2NHTTimerTask.class);

	private String redisHost = "192.168.21.5";

	private int redisPort = 6379;

	private int redisMaxActive = 100;

	private JedisPool pool = JedisPoolFactory.getPool(redisHost, redisPort,
			redisMaxActive);

	@Override
	public void run() {

		try {
			logger.debug("第二天回复NHT统计模式：" + DateUtil.getCurrentDateTimeStr());

			if (this.pool != null) {
				
				Jedis client = this.pool.getResource();

				Set<String> nhtAds = client.keys("NHT_*");

				for (String nhtAd : nhtAds) {

					try {
						Map<String, String> nhtAdMap = client.hgetAll(nhtAd);
						

						String sts = nhtAdMap.get("sts") == null ? ""
								: nhtAdMap.get("sts");
						
						// 如果当前广告的NHT量 还没达到预设值，则不清零，继续让他跑着
						if (!"F".equals(sts)) {
							continue;
						}
						// 从新计算NHT流量
						client.hset(nhtAd, "pvNum", "0");
						// 状态，从新计算NHT流量，状态给为空：表示未达到预设的NHT量
						client.hset(nhtAd, "sts", "");
						// 回复NHT统计模式
						String[] args = nhtAd.split("_");
						String adId = args[1];
						nhtService.nNht2nhtStatProcess(adId, client);

					} catch (Exception e) {
						logger.error(
								"第二天回复NHT统计模式出错:" + nhtAd + e.getMessage(), e);
					}

				}
				
				JedisPoolFactory.returnResource(this.pool, client);
			} else {
				logger.error("redis连接池为null!!!");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("第二天回复NHT统计模式出错" + e.getMessage(), e);

		}

	}

	public static void main(String[] args) {

	}

}
