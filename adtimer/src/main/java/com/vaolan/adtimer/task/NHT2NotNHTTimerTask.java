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
 * 检测启用NHT模式统计的 广告，是否达到了一定量，如果达到了，则变回非NHT统计模式
 * 
 * @author chenjinzhao
 * 
 */
@Component
public class NHT2NotNHTTimerTask extends TimerTask {

	@Autowired
	NHTStatService nhtService;

	private static Logger logger = LoggerFactory
			.getLogger(NHT2NotNHTTimerTask.class);

	private String redisHost = "192.168.21.5";

	private int redisPort = 6379;

	private int redisMaxActive = 100;

	private JedisPool pool = JedisPoolFactory.getPool(redisHost, redisPort,
			redisMaxActive);

	@Override
	public void run() {

		try {
			logger.debug("检测NHT的量是否达到设定值：" + DateUtil.getCurrentDateTimeStr());

			if (this.pool != null) {

				Jedis client = this.pool.getResource();

				Set<String> nhtAds = client.keys("NHT_*");

				for (String nhtAd : nhtAds) {

					try {

						Map<String, String> nhtAdMap = client.hgetAll(nhtAd);

						logger.debug(nhtAd+"=====================start!");
						for(String key : nhtAdMap.keySet()){
							logger.debug("****"+key+"::::"+nhtAdMap.get(key));
							
						}
						logger.debug(nhtAd+"=====================end!");
						
						int adSetPvNum = Integer.parseInt(nhtAdMap
								.get("adSetPvNum"));

						int pvNum = nhtAdMap.get("pvNum") == null ? 0 : Integer
								.parseInt(nhtAdMap.get("pvNum"));

						String sts = nhtAdMap.get("sts") == null ? ""
								: nhtAdMap.get("sts");

						// 如果当前广告的NHT量 达到设定的值，则NHT统计模式 切换到 非NHT统计模式
						if (pvNum >= adSetPvNum && !"F".equals(sts)) {
							
							logger.debug("key:" + nhtAd
									+ ",NHT量已经达到预设值:  adSetPvNum:" + adSetPvNum
									+ ", pvNum:" + pvNum);
							
							// 状态，F：表示NHT的量已经达到预设值。到量要停止
							client.hset(nhtAd, "sts", "F");

							String[] args = nhtAd.split("_");
							String adId = args[1];
							nhtService.nhtStat2NnhtProcess(adId, client);
						}

					} catch (Exception e) {
						logger.error(
								"检测NHT的量是否达到设定值出错:" + nhtAd + e.getMessage(), e);
					}

				}
				
				//归还redis客户端到它所属的连接池
				JedisPoolFactory.returnResource(this.pool, client);
				
			} else {
				logger.error("redis连接池为null!!!");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("检测NHT的量是否达到设定值出错" + e.getMessage(), e);

		}

	}

	public static void main(String[] args) {
		

	}

}
