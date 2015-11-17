package com.hidata.ad.web.dto;

import com.hidata.framework.annotation.db.Column;

public class DatePressDto {
	@Column("press")
	private String press;
	
	@Column("date")
	private String date;
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
