package com.hidata.ad.web.dto;

import java.io.Serializable;
import java.util.Date;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/**
 * 广告时间投放频率 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月11日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
@Table("ad_time_frequency")
public class AdTimeFrequencyDto implements Serializable {

    private static final long serialVersionUID = 3779913323709837173L;
    
    @Column("ad_tf_id")
    private String adTfId;
    
    @Column("ad_id")
    private String adId;
    
    @Column("put_interval_unit")
    private String putIntervalUnit;
    
    @Column("put_interval_num")
	private String putIntervalNum;
    
    @Column("day_limit")
    private String dayLimit;
    
    @Column("minute_limit")
    private String minLimit;
    
	@Column("create_time")
    private String createTime;

    @Column("sts")
	private String sts;
    
    @Column("is_uniform")
    private String isUniform;
    
    public String getIsUniform() {
    	if(isUniform == null){
			isUniform="0";
			}
		return isUniform;
	}

	public void setIsUniform(String isUniform) {
//		if(isUniform == null){
//			isUniform="0";
//			}
		this.isUniform = isUniform;
	}

	public String getMinLimit() {
 		return minLimit;
 	}

 	public void setMinLimit(String minLimit) {
 		this.minLimit = minLimit;
 	}
	public String getAdTfId() {
		return adTfId;
	}

	public void setAdTfId(String adTfId) {
		this.adTfId = adTfId;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getPutIntervalUnit() {
		return putIntervalUnit;
	}

	public void setPutIntervalUnit(String putIntervalUnit) {
		this.putIntervalUnit = putIntervalUnit;
	}

	public String getPutIntervalNum() {
		return putIntervalNum;
	}

	public void setPutIntervalNum(String putIntervalNum) {
		this.putIntervalNum = putIntervalNum;
	}

	public String getDayLimit() {
		return dayLimit;
	}

	public void setDayLimit(String dayLimit) {
		this.dayLimit = dayLimit;
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
    
    
    
}
