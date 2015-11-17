package com.vaolan.ckserver.model;

public class ClickInfo {
	
	private String adId;
	
	private String materialId;
	
    private String impressUrl;
	
	private String createTime;
	
	private String uid;
	
	private String srcIP;
	
	private String impressChannel;

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getImpressUrl() {
		return impressUrl;
	}

	public void setImpressUrl(String impressUrl) {
		this.impressUrl = impressUrl;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getSrcIP() {
		return srcIP;
	}

	public void setSrcIP(String srcIP) {
		this.srcIP = srcIP;
	}

	public String getImpressChannel() {
		return impressChannel;
	}

	public void setImpressChannel(String impressChannel) {
		this.impressChannel = impressChannel;
	}

}
