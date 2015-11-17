package com.hidata.ad.web.dto;

import java.io.Serializable;

import com.hidata.framework.annotation.db.Column;

public class AdMaterialReportViewDto implements Serializable {

	private static final long serialVersionUID = -1440304808154213399L;
	
	@Column("material_name")
	private String materialName;
	
	@Column("view_num")
	private Long viewNum;
	
	@Column("uv")
	private Long uv;
	
	@Column("unique_ip")
	private Long uniqueIP;
	
	@Column("click_num")
	private Long clickNum;
	
	@Column("click_rate")
	private double clickRate;

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public Long getViewNum() {
		return viewNum;
	}

	public void setViewNum(Long viewNum) {
		this.viewNum = viewNum;
	}

	public Long getUv() {
		return uv;
	}

	public void setUv(Long uv) {
		this.uv = uv;
	}

	public Long getUniqueIP() {
		return uniqueIP;
	}

	public void setUniqueIP(Long uniqueIP) {
		this.uniqueIP = uniqueIP;
	}

	public Long getClickNum() {
		return clickNum;
	}

	public void setClickNum(Long clickNum) {
		this.clickNum = clickNum;
	}

	public double getClickRate() {
		return clickRate;
	}

	public void setClickRate(double clickRate) {
		this.clickRate = clickRate;
	}
}
