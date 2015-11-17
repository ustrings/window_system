package com.vaolan.sspserver.model;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;
/**
 * 广告投放时间 <br>
 * Description: <br>
 * Date: 2014年6月17日 <br>
 * Copyright (c) 2014 <br>
 * 
 * @author pj
 */
@Table("ad_time_filter")
public class AdTimeFilter {

	@Column("id")
	private String id;
	@Column("ad_id")
	private String adId;
	@Column("days_of_week")
	private String daysOfWeek;
	@Column("start_hour")
	private int startHour;
	@Column("end_hour")
	private int endHour;
	
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
	
}
