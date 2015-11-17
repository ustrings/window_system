package com.vaolan.ckserver.model;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

@Table("ad_instance")
public class AdInstance {

	@Column("ad_id")
	private String id;
	
	@Column("ad_desc")
	private String adDesc;
	
	@Column("ad_name")
	private String adName;
	
	@Column("start_time")
	private String startTime;
	
	@Column("end_time")
	private String endTime;
	
	@Column("all_budget")
	private Double allBudget;
	
	@Column("day_budget")
	private Double dayBudget;
	
	@Column("sts")
	private String sts;
	
	@Column("ad_url")
	private String adUrl;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Double getAllBudget() {
		return allBudget;
	}

	public void setAllBudget(Double allBudget) {
		this.allBudget = allBudget;
	}

	public Double getDayBudget() {
		return dayBudget;
	}

	public void setDayBudget(Double dayBudget) {
		this.dayBudget = dayBudget;
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
}
