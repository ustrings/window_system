package com.vaolan.ckserver.model;

public class BidResult {
	
	private String bid;//竞价id
	private String encryption;//tanx密文
	private String resultPrice;//成交价格
	private String crcVerify;//crc校验
	private String channel;
	private String domain;
	private String adSize;
	private String viewScreen;
	private String adId;
	
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getEncryption() {
		return encryption;
	}
	public void setEncryption(String encryption) {
		this.encryption = encryption;
	}
	public String getResultPrice() {
		return resultPrice;
	}
	public void setResultPrice(String resultPrice) {
		this.resultPrice = resultPrice;
	}
	public String getCrcVerify() {
		return crcVerify;
	}
	public void setCrcVerify(String crcVerify) {
		this.crcVerify = crcVerify;
	}
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
	public String getAdSize() {
		return adSize;
	}
	public void setAdSize(String adSize) {
		this.adSize = adSize;
	}
	public String getViewScreen() {
		return viewScreen;
	}
	public void setViewScreen(String viewScreen) {
		this.viewScreen = viewScreen;
	}
	public String getAdId() {
		return adId;
	}
	public void setAdId(String adId) {
		this.adId = adId;
	}
	
}
