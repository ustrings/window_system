package com.vaolan.ckserver.model;

/**
 * 域名和广告ID维度实体类
 * @author xiaoming
 *
 */
public class AdHostStatInfo {
	
	//域名
	private String host_key;
	//广告ID
	private String adId_key;
	//每天PV量
	private String dayPv_host;
	//每天UV量
	private String dayUv_host;
	//每天IP量
	private String dayIp_host;
	//每天点击量
	private String dayClick_host;
	public String getHost_key() {
		return host_key;
	}
	public void setHost_key(String host_key) {
		this.host_key = host_key;
	}
	public String getAdId_key() {
		return adId_key;
	}
	public void setAdId_key(String adId_key) {
		this.adId_key = adId_key;
	}
	public String getDayPv_host() {
		return dayPv_host;
	}
	public void setDayPv_host(String dayPv_host) {
		this.dayPv_host = dayPv_host;
	}
	public String getDayUv_host() {
		return dayUv_host;
	}
	public void setDayUv_host(String dayUv_host) {
		this.dayUv_host = dayUv_host;
	}
	public String getDayIp_host() {
		return dayIp_host;
	}
	public void setDayIp_host(String dayIp_host) {
		this.dayIp_host = dayIp_host;
	}
	public String getDayClick_host() {
		return dayClick_host;
	}
	public void setDayClick_host(String dayClick_host) {
		this.dayClick_host = dayClick_host;
	}
	
	public String  toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("[adId_key=" + this.adId_key + ",");
		sb.append("host_key=" + this.host_key + ",");
		sb.append("dayPv_host=" + this.dayPv_host + ",");
		sb.append("dayUv_host=" + this.dayUv_host + ",");
		sb.append("dayIp_host=" + this.dayIp_host + ",");
		sb.append("dayClick_host=" + this.dayClick_host + "]");
		return  null;
	}
}
