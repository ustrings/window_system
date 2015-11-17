package com.vaolan.adtimer.model;

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
 * @author chenjinzhao
 */
@Table("ad_instance")
public class AdInstance implements Serializable {

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
    
    //付费方式
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
    
    
    @Column("ad_tanx_url")
    private String adTanxUrl;
    
    
    @Column("ad_3stat_code")
    private String ad3statCode;
    
    @Column("ad_3stat_code_sub")
    private String ad3statCodeSub;
    
    @Column("ad_3stat_code_temp")
    private String ad3statCodeTemp;

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
    
	
	
   
}
