package com.hidata.ad.web.dto;

import java.io.Serializable;

import com.hidata.framework.annotation.db.Column;

public class AdReportViewDto implements Serializable {

	private static final long serialVersionUID = -6389054605381009963L;
	
	@Column("ad_id")
	private String adId;
	
	@Column("ad_name")
	private String adName;
	
	@Column("view_num")
	private Long viewNum;
	
	@Column("uv")
	private Long uv;
	
	@Column("unique_ip")
	private Long uniqueIP;
	
	@Column("click_num")
	private Long clickNum;
	
	@Column("click_rate")
	private double clickRate;

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public Long getViewNum() {
		return viewNum;
	}

	public void setViewNum(Long viewNum) {
		this.viewNum = viewNum;
	}

	public Long getUv() {
		return uv;
	}

	public void setUv(Long uv) {
		this.uv = uv;
	}

	public Long getUniqueIP() {
		return uniqueIP;
	}

	public void setUniqueIP(Long uniqueIP) {
		this.uniqueIP = uniqueIP;
	}

	public Long getClickNum() {
		return clickNum;
	}

	public void setClickNum(Long clickNum) {
		this.clickNum = clickNum;
	}

	public double getClickRate() {
		return clickRate;
	}

	public void setClickRate(double clickRate) {
		this.clickRate = clickRate;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}
	
}
