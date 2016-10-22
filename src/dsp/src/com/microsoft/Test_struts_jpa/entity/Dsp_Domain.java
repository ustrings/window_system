package com.microsoft.Test_struts_jpa.entity;

import javax.persistence.*;

@Entity
public class Dsp_Domain {
	private int id;
	private int ad_id;
	private String domain;
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
	public int getAd_id() {
		return ad_id;
	}
	public void setAd_id(int adId) {
		ad_id = adId;
	}
	
	@Column
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	@Column
	public int getPush_num() {
		return push_num;
	}
	public void setPush_num(int pushNum) {
		push_num = pushNum;
	}
	
}
