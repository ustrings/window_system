package com.hidata.ad.web.dto;

import com.hidata.framework.annotation.db.Column;

public class AdInstanceShowDto {
	@Column("ad_id")
	private String adId;
	
	@Column("ad_name")
	private String adName;
	
	@Column("ad_desc")
	private String adDesc;
	
	@Column("start_time")
	private String startTime;
	
	@Column("end_time")
	private String endTime;
	
	@Column("sts")
	private String sts;
	
	@Column("ad_url")
	private String adUrl;
	
	@Column("link_type")
	private String linkType;
	
	@Column("day_limit")
	private String dayLimit;
	
	@Column("throw_url")
	private String throwUrl;
	
	@Column("remark")
	private String statue;
	
	@Column("ad_toufang_sts")
	private String adToufangSts;
	
	public String getAdToufangSts() {
		return adToufangSts;
	}

	public void setAdToufangSts(String adToufangSts) {
		this.adToufangSts = adToufangSts;
	}

	public String getStatue() {
		return statue;
	}

	public void setStatue(String statue) {
		this.statue = statue;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getAdDesc() {
		return adDesc;
	}

	public void setAdDesc(String adDesc) {
		this.adDesc = adDesc;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getAdUrl() {
		return adUrl;
	}

	public void setAdUrl(String adUrl) {
		this.adUrl = adUrl;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public String getDayLimit() {
		return dayLimit;
	}

	public void setDayLimit(String dayLimit) {
		this.dayLimit = dayLimit;
	}

	public String getThrowUrl() {
		return throwUrl;
	}

	public void setThrowUrl(String throwUrl) {
		this.throwUrl = throwUrl;
	}
	
	
}
