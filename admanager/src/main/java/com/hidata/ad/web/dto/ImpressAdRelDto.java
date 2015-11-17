package com.hidata.ad.web.dto;

import java.io.Serializable;

public class ImpressAdRelDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6901207918323624203L;
	// 主键信息
	private int id;
	//曝光id
	private Integer ad_ilog_id;
	//广告名称
	private String user_ad;
	
	//广告id
	private String ad_id;
	//原 ip
	private String src_ip;
		
	public String getAd_id() {
		return ad_id;
	}
	public void setAd_id(String ad_id) {
		this.ad_id = ad_id;
	}
	public String getSrc_ip() {
		return src_ip;
	}
	public void setSrc_ip(String src_ip) {
		this.src_ip = src_ip;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getAd_ilog_id() {
		return ad_ilog_id;
	}
	public void setAd_ilog_id(Integer ad_ilog_id) {
		this.ad_ilog_id = ad_ilog_id;
	}
	public String getUser_ad() {
		return user_ad;
	}
	public void setUser_ad(String user_ad) {
		this.user_ad = user_ad;
	}
}
