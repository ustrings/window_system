package com.hidata.web.dto;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/**
 * 域名白名单表 对应 实体 类
 * @author xiaoming
 * @date 2014-12-31
 */
@Table("white_domain")
public class WhiteDomainDto {
	@Column("id")
	private String id;
	@Column("domain_name")
	private String domainName;
	@Column("domain_url")
	private String domainUrl;
	@Column("sts")
	private String sts;
	@Column("sts_date")
	private String stsDate;
	@Column("remark")
	private String remark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getDomainUrl() {
		return domainUrl;
	}
	public void setDomainUrl(String domainUrl) {
		this.domainUrl = domainUrl;
	}
	public String getSts() {
		return sts;
	}
	public void setSts(String sts) {
		this.sts = sts;
	}
	public String getStsDate() {
		return stsDate;
	}
	public void setStsDate(String stsDate) {
		this.stsDate = stsDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
