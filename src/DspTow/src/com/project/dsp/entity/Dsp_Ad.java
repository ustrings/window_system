package com.project.dsp.entity;

import com.project.dsp.utils.StrUtil;

public class Dsp_Ad{
	private int id;
	private String name;
	private int prio;
	private int push_status;
	private int push_method;
	private String begin_time;
	private String end_time;
	private int set_num;
	private String url;
	private int push_num;
	
	private String domain;
	private String time;
	
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPrio() {
		return prio;
	}
	public void setPrio(int prio) {
		this.prio = prio;
	}
	
	public int getPush_status() {
		return push_status;
	}
	public void setPush_status(int pushStatus) {
		push_status = pushStatus;
	}
	
	public int getPush_method() {
		return push_method;
	}
	public void setPush_method(int pushMethod) {
		push_method = pushMethod;
	}
	
	public String getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(String beginTime) {
		begin_time = beginTime;
	}
	
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String endTime) {
		end_time = endTime;
	}
	
	public int getSet_num() {
		return set_num;
	}
	public void setSet_num(int setNum) {
		set_num = setNum;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public int getPush_num() {
		return push_num;
	}
	public void setPush_num(int pushNum) {
		push_num = pushNum;
	}
	
}
