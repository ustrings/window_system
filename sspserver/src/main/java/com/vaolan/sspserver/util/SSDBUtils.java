package com.vaolan.sspserver.util;

import java.util.Map;

import org.apache.log4j.Logger;
import org.nutz.ssdb4j.spi.SSDB;

import com.vaolan.sspserver.filter.AdplanKeywordFilter;

public class SSDBUtils {
	private static Logger logger = Logger.getLogger(AdplanKeywordFilter.class);
	
	 /**
	  *  得到某个zset的所有值
	  * @param ssdb
	  * @param key
	  * @return
	  */
 	public static Map<String, String> getAllZset(SSDB ssdb, String key) {
 		try {
 			return ssdb.zrange(key, 0, ssdb.zsize(key).asInt()).mapString();
 		} catch (Exception e) {
 			logger.info("ssdb get error " + e);
 			// 发现异常后再尝试放入一次
 			return ssdb.zrange(key, 0, ssdb.zsize(key).asInt()).mapString();
 		}
 	}
 	
 	 /**
	  *  得到某个hset的所有值
	  * @param ssdb
	  * @param key
	  * @return
	  */
	public static Map<String, String> getAllHset(SSDB ssdb, String key) {
		try {
			return ssdb.hrscan(key, "", "", 1000).mapString();
		} catch (Exception e) {
			logger.info("ssdb get error " + e);
			// 发现异常后再尝试放入一次
			return ssdb.hrscan(key, "", "", 1000).mapString();
		}
	}
 	
	 /**
	  *  得到某个key的值
	  * @param ssdb
	  * @param key
	  * @return
	  */
	public static String get(SSDB ssdb, String key) {
		return ssdb.get(key).asString();
	}
}
