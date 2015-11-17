package com.hidata.ad.web.dto;

import java.io.Serializable;
import java.util.Date;

public class AdMaterialDto implements Serializable {
	
	private static final long serialVersionUID = 3779913323709837173L;
	
	private int id;

	private Integer mType;
	
	private String materialName;
	
	private Integer materialSize;
	
	private String linkUrl;
	
	private String targetUrl;
	
	private Date createTime;
	
	private String materialDesc;
	
	private Integer thirdMonitor;
	
	private String monitorLink;
	
	private String richText;
	
	private Integer materialType;
	
	private String materialValue;
	
	private String coverFlag;
	
	private String usefulType;
	
	

	public String getUsefulType() {
		return usefulType;
	}

	public void setUsefulType(String usefulType) {
		this.usefulType = usefulType;
	}

	public String getCoverFlag() {
		return coverFlag;
	}

	public void setCoverFlag(String coverFlag) {
		this.coverFlag = coverFlag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getmType() {
		return mType;
	}

	public void setmType(Integer mType) {
		this.mType = mType;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public Integer getMaterialSize() {
		return materialSize;
	}

	public void setMaterialSize(Integer materialSize) {
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

	public Integer getThirdMonitor() {
		return thirdMonitor;
	}

	public void setThirdMonitor(Integer thirdMonitor) {
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

	public Integer getMaterialType() {
		return materialType;
	}

	public void setMaterialType(Integer materialType) {
		this.materialType = materialType;
	}

	public String getMaterialValue() {
		return materialValue;
	}

	public void setMaterialValue(String materialValue) {
		this.materialValue = materialValue;
	}
	
}
