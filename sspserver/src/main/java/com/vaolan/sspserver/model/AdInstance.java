package com.vaolan.sspserver.model;

import java.io.Serializable;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.DBExclude;
import com.hidata.framework.annotation.db.Table;

/**
 * 广告主体信息 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月11日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
@Table("ad_instance")
public class AdInstance implements Serializable
{
	@DBExclude
	private static final long serialVersionUID = 3779913323709837175L;

	@Column("ad_id")
	private String adId;

	// 广告名称
	@Column("ad_name")
	private String adName;

	// 广告描述
	@Column("ad_desc")
	private String adDesc;

	// 广告账户ID
	@Column("userid")
	private String userId;

	@Column("charge_type")
	private String chargeType;

	// 开始时间
	@Column("start_time")
	private String startTime;

	// 结束时间
	@Column("end_time")
	private String endTime;

	// 预算
	@Column("all_budget")
	private String allBudget;

	// 每日预算
	@Column("day_budget")
	private String dayBudget;

	// 创建时间
	@Column("create_time")
	private String createTime;

	// 状态
	@Column("sts")
	private String sts;

	// 广告链接
	@Column("ad_url")
	private String adUrl;

	// 广告链接
	@Column("ad_tanx_url")
	private String adTanxUrl;
	
	// 广告链接
	@Column("channel")
	private String channel;
	
	@Column("ad_3stat_code")
	private String ad3StatCode;
	
	

	@Column("ad_3stat_code_temp")
	private String ad3StatcodeTemp;
	
	// 广告是否盲投
	@Column("isBlindBid")
	private boolean isBlindBid = true;
	
	// 广告是否盲投
	@Column("ad_useful_type")
	private String adUsefulType;
	
	@Column("close_type")
	private String closeType;
	
	@Column("link_type")
	private String linkType;
	
	@Column("ad_type")
	private String adType;
	
	
	public String getAdType() {
		return adType;
	}


	public void setAdType(String adType) {
		this.adType = adType;
	}


	public String getLinkType() {
		return linkType;
	}


	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}


	public String getCloseType() {
		return closeType;
	}


	public void setCloseType(String closeType) {
		this.closeType = closeType;
	}


	public String getAdId()
	{
		return adId;
	}
	
	
	public String getAd3StatCode() {
		return ad3StatCode;
	}

	public void setAd3StatCode(String ad3StatCode) {
		this.ad3StatCode = ad3StatCode;
	}

	public String getAd3StatcodeTemp() {
		return ad3StatcodeTemp;
	}

	public void setAd3StatcodeTemp(String ad3StatcodeTemp) {
		this.ad3StatcodeTemp = ad3StatcodeTemp;
	}

	public void setAdId(String adId)
	{
		this.adId = adId;
	}

	public String getAdName()
	{
		return adName;
	}

	public void setAdName(String adName)
	{
		this.adName = adName;
	}

	public String getAdDesc()
	{
		return adDesc;
	}

	public void setAdDesc(String adDesc)
	{
		this.adDesc = adDesc;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getStartTime()
	{
		return startTime;
	}

	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}

	public String getEndTime()
	{
		return endTime;
	}

	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}

	public String getAllBudget()
	{
		return allBudget;
	}

	public void setAllBudget(String allBudget)
	{
		this.allBudget = allBudget;
	}

	public String getDayBudget()
	{
		return dayBudget;
	}

	public void setDayBudget(String dayBudget)
	{
		this.dayBudget = dayBudget;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public String getSts()
	{
		return sts;
	}

	public void setSts(String sts)
	{
		this.sts = sts;
	}

	public String getAdUrl()
	{
		return adUrl;
	}

	public void setAdUrl(String adUrl)
	{
		this.adUrl = adUrl;
	}

	/**
	 * @return the adTanxUrl
	 */
	public String getAdTanxUrl()
	{
		return adTanxUrl;
	}

	/**
	 * @param adTanxUrl the adTanxUrl to set
	 */
	public void setAdTanxUrl(String adTanxUrl)
	{
		this.adTanxUrl = adTanxUrl;
	}

	public String getChargeType()
	{
		return chargeType;
	}

	public void setChargeType(String chargeType)
	{
		this.chargeType = chargeType;
	}

	/**
	 * @return the channel
	 */
	public String getChannel()
	{
		return channel;
	}

	/**
	 * @param channel the channel to set
	 */
	public void setChannel(String channel)
	{
		this.channel = channel;
	}

	/**
	 * @return the isBlindBid
	 */
	public boolean isBlindBid()
	{
		return isBlindBid;
	}

	/**
	 * @param isBlindBid the isBlindBid to set
	 */
	public void setBlindBid(boolean isBlindBid)
	{
		this.isBlindBid = isBlindBid;
	}


	public String getAdUsefulType() {
		return adUsefulType;
	}


	public void setAdUsefulType(String adUsefulType) {
		this.adUsefulType = adUsefulType;
	}
	
	
}
