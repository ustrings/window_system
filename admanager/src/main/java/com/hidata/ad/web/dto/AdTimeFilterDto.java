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

@Table("ad_time_filter")
public class AdTimeFilterDto implements Serializable {

    private static final long serialVersionUID = 3779913323709837175L;
    
    //广告时间主键
    @Column("id")
    private String id;
    
    //广告ID
    @Column("ad_id")
    private String adId;
    
    //星期信息
    @Column("days_of_week")
    private  String daysOfWeek;
    

	//时间信息
    @Column("start_hour")
    private int startHour;
    
    //创建时间
    @Column("end_hour")
    private int endHour;
    
    //创建时间
    @Column("update_time")
    private String updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getDaysOfWeek() {
		return daysOfWeek;
	}

	public void setDaysOfWeek(String daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}

	public int getStartHour() {
		return startHour;
	}

	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	public int getEndHour() {
		return endHour;
	}

	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}
