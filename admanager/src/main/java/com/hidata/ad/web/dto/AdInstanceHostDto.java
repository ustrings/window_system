package com.hidata.ad.web.dto;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

public class AdInstanceHostDto {

	@Column("ad_id")
	private String adId;
	@Column("ad_name")
	private String adName;
	@Column("url_host")
	private String urlHost;
	@Column("pv_num")
	private String pvNum;
	@Column("click_num")
	private String clickNum;
	@Column("ts")
	private String ts;
    @Column("click_rate")
    private String clickRate;
    @Column("uv_num")
    private String uvNum;
    @Column("ip_num")
    private String ipNum;

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getUrlHost() {
		return urlHost;
	}

	public void setUrlHost(String urlHost) {
		this.urlHost = urlHost;
	}

	public String getPvNum() {
		return pvNum;
	}

	public void setPvNum(String pvNum) {
		this.pvNum = pvNum;
	}

	public String getClickNum() {
		return clickNum;
	}

	public void setClickNum(String clickNum) {
		this.clickNum = clickNum;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getClickRate() {
		return clickRate;
	}

	public void setClickRate(String clickRate) {
		this.clickRate = clickRate;
	}

	public String getUvNum() {
		return uvNum;
	}

	public void setUvNum(String uvNum) {
		this.uvNum = uvNum;
	}

	public String getIpNum() {
		return ipNum;
	}

	public void setIpNum(String ipNum) {
		this.ipNum = ipNum;
	}
}
