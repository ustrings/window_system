package com.hidata.ad.web.dto;

import java.io.Serializable;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.DBExclude;
import com.hidata.framework.annotation.db.Table;

@Table("task_crowd_rel")
public class TaskCrowdRelDto implements Serializable {


	/**
	 * 
	 */
	@DBExclude
	private static final long serialVersionUID = -5623844824477989268L;

	@Column("task_id")
	private int task_id;
	
	@Column("crowd_id")
	private String crowd_id;
	
	@Column("sts")
	private String sts;

	public int getTask_id() {
		return task_id;
	}

	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}

	public String getCrowd_id() {
		return crowd_id;
	}

	public void setCrowd_id(String crowd_id) {
		this.crowd_id = crowd_id;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}
	
}
