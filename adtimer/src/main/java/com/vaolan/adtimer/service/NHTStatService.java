package com.vaolan.adtimer.service;

import java.io.IOException;

import redis.clients.jedis.Jedis;

public interface NHTStatService {
	
	/**
	 * 把一个广告的有NHT统计模式的统计代码，转成非NHT统计模式
	 * @param adId
	 * @throws IOException 
	 */
	public void nhtStat2NnhtProcess(String adId,Jedis client) throws IOException;
	
	/**
	 * 重新启动一个广告的NHT模式的统计代码
	 * @param adId
	 * @throws IOException 
	 */
	public void nNht2nhtStatProcess(String adId,Jedis client) throws IOException;


}
