package com.vaolan.sspserver.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.hidata.framework.cache.redis.JedisPoolWriper;
import com.vaolan.sspserver.model.IPInfoForRedis;
import com.vaolan.sspserver.service.IpUaService;

@Service
public class IpUaServiceImpl implements IpUaService {
	@Resource(name = "jedisPool20_6401")
	private JedisPoolWriper jedisPool20_6401;
	
	@Override
	public IPInfoForRedis getIPInfoForRedis(String key) {
		String value = null;
		Jedis client = jedisPool20_6401.getJedis();
		value = client.get(key);
		jedisPool20_6401.releaseJedis(client);
		IPInfoForRedis info = null ;
		
		if(!StringUtils.isEmpty(value)) {
			info = new IPInfoForRedis();
			info.setKey(key);
			info.setVisitNum(value);
		}
		
		return info;
	}

	@Override
	public void saveIPInfoForRedis(IPInfoForRedis info, int expireSecond) {
		if(info != null) {
//			key
//			ip_1_192.168.1.1
//			visit_num : 123
			
			Jedis client = jedisPool20_6401.getJedis();
			client.setex(info.getKey(), expireSecond, info.getVisitNum());
			
			jedisPool20_6401.releaseJedis(client);
		}
	}

	@Override
	public long hincrByOne(String key) {
		Jedis client = jedisPool20_6401.getJedis();
		long incr = client.incrBy(key, 1);
		jedisPool20_6401.releaseJedis(client);
		return incr;
	}

}
