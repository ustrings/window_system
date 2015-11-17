package com.hidata.ad.web.dto;

import java.io.Serializable;

import com.hidata.framework.annotation.db.Column;

public class AdCrowdDetailDto implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2457298986207523150L;

	private String ad_acct;
	public String getAd_acct() {
		return ad_acct;
	}
	public void setAd_acct(String ad_acct) {
		this.ad_acct = ad_acct;
	}
	public String getCrowdNums() {
		return crowdNums;
	}
	public void setCrowdNums(String crowdNums) {
		this.crowdNums = crowdNums;
	}
	//广告id
	@Column("ad_id")
	private Integer adId;
	//广告名称
	@Column("ad_name")
	private String adName; 
	@Column("crowd_id")
	private String crowdId;

	//人群名称
	private String crowdName;

	@Column("dt")
	private String dt;
	
	private String startTime;
	
	private String endTime;
	
	@Column("crownum")
	private String crowdNums;
	
	private int userId;
	
	public String getCrowdId() {
		return crowdId;
	}
	public void setCrowdId(String crowdId) {
		this.crowdId = crowdId;
	}
	
	public String getCrowdName() {
		return crowdName;
	}
	public void setCrowdName(String crowdName) {
		this.crowdName = crowdName;
	}
	
	public String getAdName() {
		return adName;
	}
	public void setAdName(String adName) {
		this.adName = adName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
		
	public Integer getAdId() {
		return adId;
	}
	public void setAdId(Integer adId) {
		this.adId = adId;
	}
	public String getDt() {
		return dt;
	}
	public void setDt(String dt) {
		this.dt = dt;
	}
}
