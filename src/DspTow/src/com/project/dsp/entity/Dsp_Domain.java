package com.project.dsp.entity;


public class Dsp_Domain {
	public Dsp_Domain(int ad_id, String domain) {
		this.ad_id = ad_id;
		this.domain = domain;
	}
	private int id;
	private int ad_id;
	private String domain;
	private int push_num;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Dsp_Domain() {
		// TODO Auto-generated constructor stub
	}
	public int getAd_id() {
		return ad_id;
	}
	public void setAd_id(int adId) {
		ad_id = adId;
	}
	
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public int getPush_num() {
		return push_num;
	}
	public void setPush_num(int pushNum) {
		push_num = pushNum;
	}
	
}
