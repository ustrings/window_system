package com.hidata.ad.web.dto;

import java.io.Serializable;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.DBExclude;
import com.hidata.framework.annotation.db.Table;
@Table("task_ad_rel")
public class TaskAdRelDto implements Serializable {


	/**
	 * 
	 */
	@DBExclude
	private static final long serialVersionUID = 8251063744189415356L;


	@Column("task_id")
	private String task_id;
	
	@Column("ad")
	private String ad;
	
	
	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}
	
	
}
