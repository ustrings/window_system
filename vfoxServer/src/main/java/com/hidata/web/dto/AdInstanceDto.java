package com.hidata.web.dto;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;
/**
 * 广告计划表对应的实体类
 * @author xiaoming
 * @date 2014-12-22
 */
@Table("ad_instance")
public class AdInstanceDto {
	@Column("ad_id")
	private String adId;
	
	@Column("ad_desc")
	private String adDesc;
	
	@Column("ad_name")
	private String adName;
	
	@Column("charge_type")
	private String chargeType;
	
	@Column("unit_price")
	private String unitPrice;

	@Column("channel")
	private String channel;
	
	@Column("immediately_crowd")
	private String immediatelyCrowd;
	
	@Column("start_time")
	private String startTime;
	
	@Column("end_time")
	private String endTime;
	
	@Column("all_budget")
	private String allBudget;
	
	@Column("day_budget")
	private String dayBudget;
	
	@Column("create_time")
	private String createTime;
	
	@Column("sts")
	private String sts;
	
	@Column("userid")
	private String userId;
	
	@Column("ad_url")
	private String adUrl;
	
	@Column("ad_tanx_url")
	private String adTanxUrl;
	
	@Column("ad_3stat_code")
	private String ad3statCode;
	
	@Column("ad_3stat_code_sub")
	private String ad3statCodeSub;
	
	@Column("ad_3stat_code_temp")
	private String ad3statCodeTemp;
	
	@Column("total")
	private String total;
	
	@Column("close_type")
	private String closeType;
	
	@Column("ad_type")
	private String adType;
	
	@Column("max_cpm_bidprice")
	private String maxCpmBidprice;
	
	@Column("put_type")
	private String putType;
	
	@Column("link_type")
	private String linkType;
	
	@Column("ad_toufang_sts")
	private String adToufangSts;
	
	@Column("ad_useful_type")
	private String adUsefulType;	
	
	//策略总结
	private String adStrategy;
	
	public String getAdStrategy() {
		return adStrategy;
	}

	public void setAdStrategy(String adStrategy) {
		this.adStrategy = adStrategy;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getAdDesc() {
		return adDesc;
	}

	public void setAdDesc(String adDesc) {
		this.adDesc = adDesc;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getImmediatelyCrowd() {
		return immediatelyCrowd;
	}

	public void setImmediatelyCrowd(String immediatelyCrowd) {
		this.immediatelyCrowd = immediatelyCrowd;
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

	public String getAllBudget() {
		return allBudget;
	}

	public void setAllBudget(String allBudget) {
		this.allBudget = allBudget;
	}

	public String getDayBudget() {
		return dayBudget;
	}

	public void setDayBudget(String dayBudget) {
		this.dayBudget = dayBudget;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAdUrl() {
		return adUrl;
	}

	public void setAdUrl(String adUrl) {
		this.adUrl = adUrl;
	}

	public String getAdTanxUrl() {
		return adTanxUrl;
	}

	public void setAdTanxUrl(String adTanxUrl) {
		this.adTanxUrl = adTanxUrl;
	}

	public String getAd3statCode() {
		return ad3statCode;
	}

	public void setAd3statCode(String ad3statCode) {
		this.ad3statCode = ad3statCode;
	}

	public String getAd3statCodeSub() {
		return ad3statCodeSub;
	}

	public void setAd3statCodeSub(String ad3statCodeSub) {
		this.ad3statCodeSub = ad3statCodeSub;
	}

	public String getAd3statCodeTemp() {
		return ad3statCodeTemp;
	}

	public void setAd3statCodeTemp(String ad3statCodeTemp) {
		this.ad3statCodeTemp = ad3statCodeTemp;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getCloseType() {
		return closeType;
	}

	public void setCloseType(String closeType) {
		this.closeType = closeType;
	}

	public String getAdType() {
		return adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}

	public String getMaxCpmBidprice() {
		return maxCpmBidprice;
	}

	public void setMaxCpmBidprice(String maxCpmBidprice) {
		this.maxCpmBidprice = maxCpmBidprice;
	}

	public String getPutType() {
		return putType;
	}

	public void setPutType(String putType) {
		this.putType = putType;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public String getAdToufangSts() {
		return adToufangSts;
	}

	public void setAdToufangSts(String adToufangSts) {
		this.adToufangSts = adToufangSts;
	}

	public String getAdUsefulType() {
		return adUsefulType;
	}

	public void setAdUsefulType(String adUsefulType) {
		this.adUsefulType = adUsefulType;
	}
	
	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	
}
