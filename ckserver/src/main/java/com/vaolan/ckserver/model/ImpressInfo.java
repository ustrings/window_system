package com.vaolan.ckserver.model;

public class ImpressInfo {
	
	private String adId; //广告ID
	
	private String materialId; // 物料ID
	
	
	private String impressUrl;
	
	private String createTime;
	
	private String uid;
	
	private String srcIP;
	
	private String impressChannel;
	
	private String channel;
	
	private String domain;
	
	private String size;
	
	private String viewscreen;
	
	private String bid;
	
	
	private String impuuid;
	
	private String crowdIds;
	
	private String userAgent;
	
	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getCrowdIds() {
		return crowdIds;
	}

	public void setCrowdIds(String crowdIds) {
		this.crowdIds = crowdIds;
	}

	public String getImpuuid() {
		return impuuid;
	}

	public void setImpuuid(String impuuid) {
		this.impuuid = impuuid;
	}

	public String getAdAcct() {
		return adAcct;
	}

	public void setAdAcct(String adAcct) {
		this.adAcct = adAcct;
	}

	private String adAcct;

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getViewscreen() {
		return viewscreen;
	}

	public void setViewscreen(String viewscreen) {
		this.viewscreen = viewscreen;
	}

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

	/**
	 * @return the bid
	 */
	public String getBid()
	{
		return bid;
	}

	/**
	 * @param bid the bid to set
	 */
	public void setBid(String bid)
	{
		this.bid = bid;
	}
	

}
