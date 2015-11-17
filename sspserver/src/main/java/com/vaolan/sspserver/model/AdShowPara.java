package com.vaolan.sspserver.model;

public class AdShowPara {

	private String adId;

	private String userId;

	private String adAcct;

	private String ref;

	private String width;

	private String height;

	//标识是精准还是盲投，
	private String adThrowType;
	
	//标识是pc弹窗，还是mobile弹窗
	private String adType;
	
	
	//是否有广告投放，最高的开关(0:NO,1:YES)
	private String showFlag;
	
	
	private String impId;
	
	private String area;
	
	

	

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getImpId() {
		return impId;
	}

	public void setImpId(String impId) {
		this.impId = impId;
	}



	public String getShowFlag() {
		return showFlag;
	}

	public void setShowFlag(String showFlag) {
		this.showFlag = showFlag;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAdAcct() {
		return adAcct;
	}

	public void setAdAcct(String adAcct) {
		this.adAcct = adAcct;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getAdThrowType() {
		return adThrowType;
	}

	public void setAdThrowType(String adThrowType) {
		this.adThrowType = adThrowType;
	}

	public String getAdType() {
		return adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public String getExtLink() {
		return extLink;
	}

	public void setExtLink(String extLink) {
		this.extLink = extLink;
	}

	public String getCloseType() {
		return closeType;
	}

	public void setCloseType(String closeType) {
		this.closeType = closeType;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	// 用来标识是用自有物料还是外部连接
	private String linkType;

	// 投放连接 或者 第三方js
	private String extLink;

	private String closeType;

	private String adName;

}
