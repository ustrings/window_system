package com.hidata.ad.web.dto;

import com.hidata.framework.annotation.db.Column;

public class AdStatisticsViewDto {

	@Column("total_impress_num")
	private String totalImpressNum;
	@Column("total_click_num")
	private String totalClickNum;
	@Column("click_rate")
	private String clickRate;
	@Column("cpm_price")
	private String cpm_price;
	@Column("total_amount")
	private String totalAmount;
	
	public String getTotalImpressNum() {
		return totalImpressNum;
	}
	public void setTotalImpressNum(String totalImpressNum) {
		this.totalImpressNum = totalImpressNum;
	}
	public String getTotalClickNum() {
		return totalClickNum;
	}
	public void setTotalClickNum(String totalClickNum) {
		this.totalClickNum = totalClickNum;
	}
	public String getClickRate() {
		return clickRate;
	}
	public void setClickRate(String clickRate) {
		this.clickRate = clickRate;
	}
	public String getCpm_price() {
		return cpm_price;
	}
	public void setCpm_price(String cpm_price) {
		this.cpm_price = cpm_price;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
}
