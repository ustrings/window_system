package com.vaolan.sspserver.model;

public class AdFreshInfo {
	
	private String channel;
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getCrowdOrMt() {
		return crowdOrMt;
	}

	public void setCrowdOrMt(String crowdOrMt) {
		this.crowdOrMt = crowdOrMt;
	}

	private String adId;
	
	private String adName;
	
	private String crowdOrMt;
	
	private String adCount;
	
	private String adStatus;
	
	private String size;
	
	private String dayLimitCount;

	private String stopTime;
	
	private String clickCount;
	
	private String clickRate;
	
	private String closeType;
	
	private String finishRate;
	
	
	
	

	public String getFinishRate() {
		return finishRate;
	}

	public void setFinishRate(String finishRate) {
		this.finishRate = finishRate;
	}

	public String getCloseType() {
		return closeType;
	}

	public void setCloseType(String closeType) {
		this.closeType = closeType;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getAdCount() {
		return adCount;
	}

	public void setAdCount(String adCount) {
		this.adCount = adCount;
	}

	public String getAdStatus() {
		return adStatus;
	}

	public void setAdStatus(String adStatus) {
		this.adStatus = adStatus;
	}
	
	public String getDayLimitCount() {
		return dayLimitCount;
	}

	public void setDayLimitCount(String dayLimitCount) {
		this.dayLimitCount = dayLimitCount;
	}

	public String getStopTime() {
		return stopTime;
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}

	public String getClickCount() {
		return clickCount;
	}

	public void setClickCount(String clickCount) {
		this.clickCount = clickCount;
	}

	public String getClickRate() {
		return clickRate;
	}

	public void setClickRate(String clickRate) {
		this.clickRate = clickRate;
	}

}
