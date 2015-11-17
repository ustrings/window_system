package com.hidata.ad.web.dto;

import java.io.Serializable;
import java.util.Date;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

@Table("ad_impress_log")
public class AdImpressLogDto implements Serializable {
	private static final long serialVersionUID = 2457298986207523150L;
	
	// 主键信息
	@Column("ad_ilog_id")
	private int id;
	//广告id
	@Column("ad_id")
	private Integer adId;
	//广告名称
	@Column("ad_name")
	private String adName;
	//曝光url
	@Column("impress_url")
	private String impressUrl;
	//曝光时间
	@Column("ts")
	private String ts;
	//曝光ip
	@Column("visitor_ip")
	private String visitor_ip;
	
	//用户ad
	@Column("ad_acct")
	private String ad_acct;
	
	//用户ad
	@Column("is_clicked")
	private String isClicked;
	
	private int userId;
	
	//操作flag
	private String op;
	private String startTime;
	
	private String endTime;
	
	public String getAdName() {
		return adName;
	}
	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getAd_acct() {
		return ad_acct;
	}
	public void setAd_acct(String ad_acct) {
		this.ad_acct = ad_acct;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getAdId() {
		return adId;
	}
	public void setAdId(Integer adId) {
		this.adId = adId;
	}
	
	public String getImpressUrl() {
		return impressUrl;
	}
	public void setImpressUrl(String impressUrl) {
		this.impressUrl = impressUrl;
	}
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
	public String getVisitor_ip() {
		return visitor_ip;
	}
	public void setVisitor_ip(String visitor_ip) {
		this.visitor_ip = visitor_ip;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getIsClicked() {
		return isClicked;
	}
	public void setIsClicked(String isClicked) {
		this.isClicked = isClicked;
	}
	
}
