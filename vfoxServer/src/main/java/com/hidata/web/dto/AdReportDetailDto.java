package com.hidata.web.dto;

import com.hidata.framework.annotation.db.Column;

/**
 * 广告报告详情实体类
 * @author xiaoming
 *
 */
public class AdReportDetailDto {
	
	@Column("ad_id")
	private String adId;
	
	@Column("ad_name")
	private String adName;
	
	@Column("ad_url")
	private String adUrl;
	
	@Column("ad_tf_sts")
	private String adTFsts;
	
	@Column("start_time")
	private String startTime;
	
	@Column("user_id")
	private String userId;
	
	@Column("end_time")
	private String endTime;
	
	@Column("pv_nums")
	private String pvNums;
	
	@Column("click_nums")
	private String clickNums;
	
	@Column("click_rates")
	private String rateNums;
	
	@Column("uv_nums")
	private String uvNums;
	
	@Column("ip_nums")
	private String ipNums;
	
	@Column("mobile_pv_nums")
	private String mobilePvNums;
	
	@Column("mobile_click_nums")
	private String mobileClickNums;
	
	@Column("total_money")
	private String totalMoney;
	
	@Column("charge_type")
	private String chargeType;
	
	@Column("unit_price")
	private String unitPrice;
	
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

	public String getAdTFsts() {
		return adTFsts;
	}

	public void setAdTFsts(String adTFsts) {
		this.adTFsts = adTFsts;
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

	public String getPvNums() {
		return pvNums;
	}

	public void setPvNums(String pvNums) {
		this.pvNums = pvNums;
	}

	public String getClickNums() {
		return clickNums;
	}

	public void setClickNums(String clickNums) {
		this.clickNums = clickNums;
	}

	public String getRateNums() {
		return rateNums;
	}

	public void setRateNums(String rateNums) {
		this.rateNums = rateNums;
	}

	public String getUvNums() {
		return uvNums;
	}

	public void setUvNums(String uvNums) {
		this.uvNums = uvNums;
	}

	public String getIpNums() {
		return ipNums;
	}

	public void setIpNums(String ipNums) {
		this.ipNums = ipNums;
	}

	public String getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getMobilePvNums() {
		return mobilePvNums;
	}

	public void setMobilePvNums(String mobilePvNums) {
		this.mobilePvNums = mobilePvNums;
	}

	public String getMobileClickNums() {
		return mobileClickNums;
	}

	public void setMobileClickNums(String mobileClickNums) {
		this.mobileClickNums = mobileClickNums;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
}
