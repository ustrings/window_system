package com.microsoft.Test_struts_jpa.entity;

import javax.persistence.*;

@Entity
public class Dsp_Ad {
	private int id;
	private String name;
	private int prio;
	private int push_status;
	private int push_method;
	private Long begin_time;
	private Long end_time;
	private int set_num;
	private String url;
	private int push_num;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column
	public int getPrio() {
		return prio;
	}
	public void setPrio(int prio) {
		this.prio = prio;
	}
	
	@Column
	public int getPush_status() {
		return push_status;
	}
	public void setPush_status(int pushStatus) {
		push_status = pushStatus;
	}
	
	@Column
	public int getPush_method() {
		return push_method;
	}
	public void setPush_method(int pushMethod) {
		push_method = pushMethod;
	}
	
	@Column
	public Long getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(Long beginTime) {
		begin_time = beginTime;
	}
	
	@Column
	public Long getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Long endTime) {
		end_time = endTime;
	}
	
	@Column
	public int getSet_num() {
		return set_num;
	}
	public void setSet_num(int setNum) {
		set_num = setNum;
	}
	
	@Column
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column
	public int getPush_num() {
		return push_num;
	}
	public void setPush_num(int pushNum) {
		push_num = pushNum;
	}
	
}
