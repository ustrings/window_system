package com.hidata.ad.web.model;


import com.hidata.framework.annotation.db.Column;

public class AdStatInfo
{	
	@Column("link_type")
	private String adLinkType;
	@Column("ad_name")
	private String adName;
	@Column("asi_id")
	private String asiId;
	@Column("ad_id")
	private String adId;
	@Column("pv_num")
	private String pvNum;
	@Column("click_num")
	private String clickNum;
	@Column("uv_num")
	private String uvNum;
	@Column("ip_num")
	private String ipNum;
	@Column("mobile_pv_num")
	private String mobilePvNum;
	@Column("mobile_click_num")
	private String mobileClickNum;
	@Column("click_rate")
	private double clickRate;
	@Column("close_num")
	private String closeNum;
	@Column("unit_price")
	private String unitPrice;
	@Column("total_amount")
	private String totalAmount;
	@Column("ts")
	private String ts;
	
	public AdStatInfo(String asiId, String adId, String pvNum, String clickNum,
			String uvNum, String ipNum, String mobilePvNum,
			String mobileClickNum,String closeNum) {
		super();
		this.asiId = asiId;
		this.adId = adId;
		this.pvNum = pvNum;
		this.clickNum = clickNum;
		this.uvNum = uvNum;
		this.ipNum = ipNum;
		this.mobilePvNum = mobilePvNum;
		this.mobileClickNum = mobileClickNum;
		this.closeNum = closeNum;
	}

	public AdStatInfo() {
		super();
	}

	public String getAsiId() {
		return asiId;
	}

	public void setAsiId(String asiId) {
		this.asiId = asiId;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
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

	public String getUvNum() {
		return uvNum;
	}

	public void setUvNum(String uvNum) {
		this.uvNum = uvNum;
	}

	public String getIpNum() {
		return ipNum;
	}

	public void setIpNum(String ipNum) {
		this.ipNum = ipNum;
	}

	public String getMobilePvNum() {
		return mobilePvNum;
	}

	public void setMobilePvNum(String mobilePvNum) {
		this.mobilePvNum = mobilePvNum;
	}

	public String getMobileClickNum() {
		return mobileClickNum;
	}

	public void setMobileClickNum(String mobileClickNum) {
		this.mobileClickNum = mobileClickNum;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public double getClickRate() {
		return clickRate;
	}

	public void setClickRate(double clickRate) {
		this.clickRate = clickRate;
	}
	
	public String getAdLinkType() {
		return adLinkType;
	}

	public void setAdLinkType(String adLinkType) {
		this.adLinkType = adLinkType;
	}

	public String getCloseNum() {
		return closeNum;
	}

	public void setCloseNum(String closeNum) {
		this.closeNum = closeNum;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
}
