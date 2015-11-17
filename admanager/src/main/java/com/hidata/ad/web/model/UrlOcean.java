package com.hidata.ad.web.model;

import com.hidata.framework.annotation.db.Column;

public class UrlOcean {

	
	@Column("uo_id")
	private String uoId;
	
	@Column("url")
	private String url;
	
	@Column("content")
	private String content;
	
	@Column("sts")
	private String sts;
	
	@Column("first_type")
	private String firstType;
	
	@Column("create_time")
	private String createTime;
	
	@Column("remark")
	private String remark;

	public String getUoId() {
		return uoId;
	}

	public void setUoId(String uoId) {
		this.uoId = uoId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getFirstType() {
		return firstType;
	}

	public void setFirstType(String firstType) {
		this.firstType = firstType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
