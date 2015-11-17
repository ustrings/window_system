package com.hidata.ad.web.model;

import com.hidata.framework.annotation.db.Column;

public class ChartStatistics {
	
	@Column("material_name")
	private String name;
	@Column("ts")
	private String ts;
	@Column("num")
	private Integer number;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
}
