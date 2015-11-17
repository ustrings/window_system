package com.vaolan.adserver.util;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import com.hidata.framework.cache.redis.RedisUtil;
import com.hidata.framework.util.DateUtil;

public class RTest {
	
	public static void main(String[] args) {
		Jedis client = RedisUtil.getJedisClientFromPool();
		
		Set<String> imps = client.keys("imp_*");
		
		for(String imp : imps){
		    //System.out.println(imp);
			
			Map<String,String> imp_map =  client.hgetAll(imp);
			
			//System.out.println(imp_map.get("ts"));
			
			Date impDate = DateUtil.parseDateTime(imp_map.get("ts"));	
			
			Date date3 = DateUtil.parseDateTime("2014-05-26 15:00:00");
			
			long impDateLong = impDate.getTime();
			long date3Long = date3.getTime();
			
			if(impDateLong < date3Long){
				client.del(imp);
			}
		}
		RedisUtil.releaseJedisClientFromPool(client);
	}

}
