package com.vaolan.sspserver.model;

/**
 * 每个 ip 被访问的次数信息
 * @author ZTD
 *
 */
public class IPInfoForRedis {
	// key
	private String key;
	// 已访问数量
	private String visitNum;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getVisitNum() {
		return visitNum;
	}
	public void setVisitNum(String visitNum) {
		this.visitNum = visitNum;
	}
	
	
}
