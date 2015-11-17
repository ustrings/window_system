package com.hidata.web.dto;

import com.hidata.framework.annotation.db.Column;

/**
 * 广告ad_instance和ad_check_process关联 待审核查询展示信息
 * @author ssq
 *
 */
public class AdInstanceCheckProcessDto {
    
	//广告Id
	@Column("ad_id")
	private String adId;
	//广告名称
	@Column("ad_name")
	private String adName;
	//广告投放开始时间
	@Column("start_time")
	private String startTime;
	//付费方式
	@Column("charge_type")
	private String chargeType;
	//单价
	@Column("unit_price")
	private String unitPrice;
	//广告投放结束时间
	@Column("end_time")
	private String endTime;
	//投放链接
	@Column("ad_url")
	private String throwUrl;
	//审核状态
	@Column("check_sts")
	private String checkSts;
	//审核人Id
	@Column("check_user_id")
	private String checkUserId;
	//ad_check_process的Id
	@Column("check_process_id")
	private String checkProcessId;
	//更新时间
	@Column("sts_date")
	private String updateDate;
    //广告的投放链接的链接类型
	@Column("link_type")
	private String linkType;
	
	@Column("day_limit")
	private String dayLimit;
	
	public String getDayLimit() {
		return dayLimit;
	}

	public void setDayLimit(String dayLimit) {
		this.dayLimit = dayLimit;
	}

	private String adStrategy;
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
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

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getThrowUrl() {
		return throwUrl;
	}

	public void setThrowUrl(String throwUrl) {
		this.throwUrl = throwUrl;
	}

	public String getCheckSts() {
		return checkSts;
	}

	public void setCheckSts(String checkSts) {
		this.checkSts = checkSts;
	}

	public String getCheckUserId() {
		return checkUserId;
	}

	public void setCheckUserId(String checkUserId) {
		this.checkUserId = checkUserId;
	}

	public String getCheckProcessId() {
		return checkProcessId;
	}

	public void setCheckProcessId(String checkProcessId) {
		this.checkProcessId = checkProcessId;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
}
