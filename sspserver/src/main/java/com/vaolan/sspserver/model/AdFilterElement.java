package com.vaolan.sspserver.model;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AdFilterElement {
	
	private String ref;
	private Set<String> excludeAdIds = new HashSet<String>();
	
	private String userAgent;
	private String adId;
	
	//标志投放类型，盲投：0 离线即搜即投：1 定投策略1: 2 定投策略2: 3 即搜即投：4 百度即搜即投：5
	private String type;
	
	private String adAcct;
	
	private String host;

	private String ip;
	
	private String width;
	
	private String height;
	
	private String region;
	
	
	//标识PC弹窗和移动弹窗
	private String adType;
	
	
	public String getAdType() {
		return adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}

	// 每分钟的投放量
	private Map<String, String> adMinNumMap;
	
	
	public Map<String, String> getAdMinNumMap() {
		return adMinNumMap;
	}

	public void setAdMinNumMap(Map<String, String> adMinNumMap) {
		this.adMinNumMap = adMinNumMap;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getAdAcct() {
		return adAcct;
	}

	public void setAdAcct(String adAcct) {
		this.adAcct = adAcct;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Set<String> getExcludeAdIds() {
		return excludeAdIds;
	}

	public void setExcludeAdIds(Set<String> excludeAdIds) {
		this.excludeAdIds = excludeAdIds;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
