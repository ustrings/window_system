package com.vaolan.sspserver.dao;

import java.util.List;
import java.util.Set;

public interface AdSiteDao {

	/**
	 * 查询广告计划 指定的定向站点<br>
	 * 此字段只要url字段
	 * 
	 * @param adId
	 * @return List<url>
	 * */
	public List<String> getSiteUrlByAdId(String adId);

	/**
	 * 查询广告计划 指定的定向Url<br>
	 * 此字段只要url字段
	 * 
	 * @param adId
	 * @return List<url>
	 * */
	public List<String> getUrlByAdId(String adId);
	
	/**
	 * 获取负向站点
	 * @param adId
	 * @return
	 */
	public List<String> getNegSiteUrlByAdId(String adId);

	/**
	 * 广告定投的IP地址
	 * 
	 * @param adId
	 * @return Set<ip>
	 * */
	public Set<String> getPosTargetIpByAdId(String adId);
	
	
	/**
	 * 广告不投的IP地址
	 * 
	 * @param adId
	 * @return Set<ip>
	 * */
	public Set<String> getNegTargetIpByAdId(String adId);
	
	
	/**
	 * 广告ad帐号定投
	 * @param adId
	 * @return
	 */
	Set<String> getTargetAdAcctByAdId(String adId);
}