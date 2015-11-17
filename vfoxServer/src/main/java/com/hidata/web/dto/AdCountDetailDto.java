package com.hidata.web.dto;

import org.springframework.cache.annotation.Cacheable;

import com.hidata.framework.annotation.db.Column;

/**
 * 广告统计列表的表格详情
 * @author xiaoming
 * @date 2014-12-25
 */
public class AdCountDetailDto {
	
	@Column("ad_id")
	private String adId;//广告计划ID
	
	@Column("ad_name")
	private String adName;//广告计划名称
	
	@Column("ad_url")
	private String adUrl;//广告链接
	
	@Column("start_time")
	private String startTime;//广告开始时间
	
	@Column("end_time")
	private String endTime;//广告结束时间
	
	@Column("ad_strategy")
	private String adStrategy;//投放策略
	
	@Column("ad_toufang_sts")
	private	String adTFsts;//投放状态
	
	@Column("day_limit")
	private String dayLimit; //每日期望PV
	
	@Column("pv_num")
	private String pvNum;//展现量（PV）
	
	@Column("click_num")
	private String clickNum;   //点击量（click）
	
	@Column("click_rate")
	private String ctrNum;     //点击率(CTR)
	
	@Column("link_type")
	private String linkType;
	
	@Column("throw_url")
	private String throwUrl;
	
	
	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public String getThrowUrl() {
		return throwUrl;
	}

	public void setThrowUrl(String throwUrl) {
		this.throwUrl = throwUrl;
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

	public String getAdUrl() {
		return adUrl;
	}

	public void setAdUrl(String adUrl) {
		this.adUrl = adUrl;
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

	public String getAdStrategy() {
		return adStrategy;
	}

	public void setAdStrategy(String adStrategy) {
		this.adStrategy = adStrategy;
	}

	public String getAdTFsts() {
		return adTFsts;
	}

	public void setAdTFsts(String adTFsts) {
		this.adTFsts = adTFsts;
	}

	public String getDayLimit() {
		return dayLimit;
	}

	public void setDayLimit(String dayLimit) {
		this.dayLimit = dayLimit;
	}

	public String getPvNum() {
		return pvNum;
	}

	public void setPvNum(String pvNum) {
		this.pvNum = pvNum;
	}

	public String getClickNum() {
		return clickNum;
	}

	public void setClickNum(String clickNum) {
		this.clickNum = clickNum;
	}

	public String getCtrNum() {
		return ctrNum;
	}

	public void setCtrNum(String ctrNum) {
		this.ctrNum = ctrNum;
	}
	
}                             

