package com.hidata.ad.web.dto;

import java.io.Serializable;
import java.util.Date;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;


/**
 * 广告投放时间信息
 * Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月11日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */

@Table("ad_time")
public class AdTimeDto implements Serializable {

    private static final long serialVersionUID = 3779913323709837175L;
    
    //广告时间主键
    @Column("ad_t_id")
    private String adTimeid;
    
    //广告ID
    @Column("ad_id")
    private String adId;
    
    //星期信息
    @Column("week")
    private  String week;
    

	//时间信息
    @Column("hour")
    private String hour;
    
    //创建时间
    @Column("create_time")
    private String createTime;
    
	//状态信息
    @Column("sts")
    private String sts;

	public String getAdTimeid() {
		return adTimeid;
	}

	public void setAdTimeid(String adTimeid) {
		this.adTimeid = adTimeid;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
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
