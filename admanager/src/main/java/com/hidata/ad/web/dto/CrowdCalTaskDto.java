package com.hidata.ad.web.dto;

import java.io.Serializable;

public class CrowdCalTaskDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5129400370666398262L;


	private TaskDto taskDto;
	
	
	private String  crowdIds;
	
	
	private String start_time;
	private String end_time;
	
	private Integer userId;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public TaskDto getTaskDto() {
		return taskDto;
	}
	public void setTaskDto(TaskDto taskDto) {
		this.taskDto = taskDto;
	}
	public String getCrowdIds() {
		return crowdIds;
	}
	public void setCrowdIds(String crowdIds) {
		this.crowdIds = crowdIds;
	}

}
