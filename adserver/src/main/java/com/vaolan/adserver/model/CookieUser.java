package com.vaolan.adserver.model;


public class CookieUser {
	
	private String id;
	private String hostName;
	private String ipAddress;
	private String requestProtocal;
	private String userAgent;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getRequestProtocal() {
		return requestProtocal;
	}
	public void setRequestProtocal(String requestProtocal) {
		this.requestProtocal = requestProtocal;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

}
