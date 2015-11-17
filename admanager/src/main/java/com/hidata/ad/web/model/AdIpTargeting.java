package com.hidata.ad.web.model;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.DBExclude;
import com.hidata.framework.annotation.db.Table;

@Table("ad_ip_targeting")
public class AdIpTargeting {

	@DBExclude
	public static String TYPE_WHITE="+";
	@DBExclude
	public static String TYPE_BLACK="-";
	
	@Column("id")
	private Integer id;
	@Column("ad_id")
	private Integer adId;
	
	@Column("ip")
	private String ip;
	
	@Column("type")
	private String type;
	@Column("create_time")
	private String createTime;
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAdId() {
		return adId;
	}

	public void setAdId(Integer adId) {
		this.adId = adId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
