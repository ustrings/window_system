package com.hidata.ad.web.dto;

import java.io.Serializable;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

@Table("ad_vc_link")
public class AdVisitorLinkDto implements Serializable {
	private static final long serialVersionUID = 3779913323709837175L;
	@Column("id")
	private String id;
	@Column("ad_id")
	private String adId;
	@Column("vc_id")
	private String vcId;
	@Column("create_time")
	private String createTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAdId() {
		return adId;
	}
	public void setAdId(String adId) {
		this.adId = adId;
	}
	public String getVcId() {
		return vcId;
	}
	public void setVcId(String vcId) {
		this.vcId = vcId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
