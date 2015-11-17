package com.hidata.ad.web.dto;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

@Table("visitor_crowd")
public class VisitorCrowdDto {
	@Column("vc_id")
	private String vcId;
	@Column("vc_name")
	private String name;
	@Column("vc_site_type")
	private String vcSiteType;
	@Column("vc_site_desc")
	private String vcSiteDesc;
	@Column("vc_site_host")
	private String vcSiteHost;
	@Column("vc_code")
	private String vcCode;
	@Column("vc_userid")
	private String vcUserid;
	@Column("vc_sts")
	private String vcSts;
	public String getVcId() {
		return vcId;
	}
	public void setVcId(String vcId) {
		this.vcId = vcId;
	}
	public String getVcSiteType() {
		return vcSiteType;
	}
	public void setVcSiteType(String vcSiteType) {
		this.vcSiteType = vcSiteType;
	}
	public String getVcSiteDesc() {
		return vcSiteDesc;
	}
	public void setVcSiteDesc(String vcSiteDesc) {
		this.vcSiteDesc = vcSiteDesc;
	}
	public String getVcSiteHost() {
		return vcSiteHost;
	}
	public void setVcSiteHost(String vcSiteHost) {
		this.vcSiteHost = vcSiteHost;
	}
	public String getVcCode() {
		return vcCode;
	}
	public void setVcCode(String vcCode) {
		this.vcCode = vcCode;
	}
	public String getVcUserid() {
		return vcUserid;
	}
	public void setVcUserid(String vcUserid) {
		this.vcUserid = vcUserid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVcSts() {
		return vcSts;
	}
	public void setVcSts(String vcSts) {
		this.vcSts = vcSts;
	}
}
