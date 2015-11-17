package com.vaolan.ckserver.model;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;



@Table("ad_cookie_user")
public class CookieUser {
	
	@Column("id")
	private String id;
	
	@Column("cookie_id")
	private String cookieId;
	
	@Column("host_name")
	private String hostName;
	
	@Column("ip_address")
	private String ipAddress;
	
	@Column("request_protocal")
	private String requestProtocal;
	
	@Column("user_agent")
	private String userAgent;

	@Column("create_time")
	private String createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCookieId() {
		return cookieId;
	}

	public void setCookieId(String cookieId) {
		this.cookieId = cookieId;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
}
