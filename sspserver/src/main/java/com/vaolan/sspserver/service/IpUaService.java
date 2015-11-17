package com.vaolan.sspserver.service;

import com.vaolan.sspserver.model.IPInfoForRedis;

public interface IpUaService {
	/**
	 * 获取指定 key 的IpInfoForRedis
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public IPInfoForRedis getIPInfoForRedis(String key);
	/**
	 * 保存 IPInfo 数据到 redis 中
	 * @param info
	 * @param expireSecond 多少秒过期
	 * @throws Exception
	 */
	public  void saveIPInfoForRedis(IPInfoForRedis info, int expireSecond);
	/**
	 * 在hash结构指定key和field自增1
	 * @param key
	 * @param field
	 * @return value
	 * @throws Exception 
	 * */
	public long hincrByOne(String key);
}
