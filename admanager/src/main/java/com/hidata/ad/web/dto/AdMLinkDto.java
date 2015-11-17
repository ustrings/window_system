package com.hidata.ad.web.dto;



/**
 * 广告审核展现实体
 */

public class AdMLinkDto  {

	private String id;
    
    //广告ID
    private String adId;
    
	//广告物料ID
    private String adMId;

	//状态
    private String sts;
    
    private String checkStatus;
    
    private String comment;
    
    private String materialName;
    
    private String MType;
    
    private String linkUrl;
    
    private String richText;


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

	public String getAdMId() {
		return adMId;
	}

	public void setAdMId(String adMId) {
		this.adMId = adMId;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMType() {
		return MType;
	}

	public void setMType(String mType) {
		MType = mType;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getRichText() {
		return richText;
	}

	public void setRichText(String richText) {
		this.richText = richText;
	}
}
