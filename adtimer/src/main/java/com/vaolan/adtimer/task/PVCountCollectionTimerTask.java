package com.vaolan.adtimer.task;

import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.hidata.framework.cache.redis.JedisPoolWriper;
import com.hidata.framework.cache.redis.RedisUtil;
import com.hidata.framework.util.DateUtil;
import com.hidata.framework.util.JsonUtil;
import com.vaolan.adtimer.model.AdStatInfo;
import com.vaolan.adtimer.util.Constant;
import com.vaolan.adtimer.util.JedisPoolFactory;

/**
 * 同步每个ad的当天pv量
 * 
 * @author chenjinzhao
 * 
 */

@Component
public class PVCountCollectionTimerTask extends TimerTask {

	private static Logger logger = LoggerFactory
			.getLogger(PVCountCollectionTimerTask.class);

	
	
	
	@Resource(name = "pvCountJedisPool")
    private JedisPoolWriper pvCountJedisPool;
	
	
	
	@Override
	public void run() {

		logger.info("同步广告当天pv量：" + DateUtil.getCurrentDateTimeStr());
		Jedis client = RedisUtil.getJedisClientFromPool();

		//每分钟的广告量，清零
		client.del(Constant.AD_MIN_NUM);
		try {

			// 得到所有广告统计信息在redis中的key，然后一个一个的处理
			Set<String> adStatKeys = client.keys(Constant.ADSTAT_KEYS_PATTERN);

			JSONObject adPvJson = new JSONObject();
			for (String key : adStatKeys) {

				try {
					Map<String, String> adStatMap = client.hgetAll(key);
					// 把一个广告的所有统计信息，从redis中取出来，组成一个对象
					JSONObject adStatJSON = JSONObject.fromObject(adStatMap);
					AdStatInfo adStatInfo = JsonUtil.jsonToBean(
							adStatJSON.toString(), AdStatInfo.class);

					String[] strs = key.split("_");
					String adId = strs[strs.length - 1];

					if (StringUtils.isBlank(adId)) {
						continue;
					}

					String todayPvNum = adStatInfo.getToday_pv_num();

					adPvJson.put(adId, todayPvNum);
				} catch (Exception e) {
					logger.error("统计广告当天pv出错："+e.getMessage(),e);
				}

			}

			if (StringUtils.isNotBlank(adPvJson.toString())) {
				
				client.set("PVCount", adPvJson.toString());
				logger.debug("所有广告当天PVCount(" + DateUtil.getCurrentDateStr()
						+ ")：" + adPvJson.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("同步广告当天PV量出错" + e.getMessage(), e);
		} finally {
			RedisUtil.releaseJedisClientFromPool(client);
		}

	}
}
