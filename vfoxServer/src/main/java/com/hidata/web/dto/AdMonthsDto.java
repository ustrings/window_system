package com.hidata.web.dto;

import com.hidata.framework.annotation.db.Column;


/**
 * 每个广告计划统计的月份
 * @author xiaoming
 * @date 2014-12-27
 */
public class AdMonthsDto {
	@Column("ad_id")
	private String adId;
	@Column("yuefen")
	private String yuefen;
	public String getAdId() {
		return adId;
	}
	public void setAdId(String adId) {
		this.adId = adId;
	}
	public String getYuefen() {
		return yuefen;
	}
	public void setYuefen(String yuefen) {
		this.yuefen = yuefen;
	}
}
