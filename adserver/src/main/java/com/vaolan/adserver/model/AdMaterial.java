package com.vaolan.adserver.model;

import java.util.Date;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.DBExclude;

public class AdMaterial {
	
	@Column("ad_m_id")
	private String id;
	
	@Column("m_type")
	private String mType;
	
	@Column("material_name")
	private String materialName;
	
	@Column("material_size")
	private int materialSize;
	
	@Column("link_url")
	private String linkUrl;
	
	@Column("target_url")
	private String targetUrl;
	
	@Column("create_time")
	private Date createTime;
	
	@Column("material_desc")
	private String materialDesc;
	
	@Column("third_monitor")
	private int thirdMonitor;
	
	@Column("monitor_link")
	private String monitorLink;
	
	@Column("rich_text")
	private String richText;
	
	@Column("userid")
	private int userid;
	
	@Column("status")
	private int status;
	
	@Column("material_type")
	private Integer materialType;
	@DBExclude
    private int logId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getmType() {
		return mType;
	}

	public void setmType(String mType) {
		this.mType = mType;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public int getMaterialSize() {
		return materialSize;
	}

	public void setMaterialSize(int materialSize) {
		this.materialSize = materialSize;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public int getThirdMonitor() {
		return thirdMonitor;
	}

	public void setThirdMonitor(int thirdMonitor) {
		this.thirdMonitor = thirdMonitor;
	}

	public String getMonitorLink() {
		return monitorLink;
	}

	public void setMonitorLink(String monitorLink) {
		this.monitorLink = monitorLink;
	}

	public String getRichText() {
		return richText;
	}

	public void setRichText(String richText) {
		this.richText = richText;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getMaterialType() {
		return materialType;
	}

	public void setMaterialType(Integer materialType) {
		this.materialType = materialType;
	}

	public int getLogId() {
		return logId;
	}

	public void setLogId(int logId) {
		this.logId = logId;
	}

	
}
