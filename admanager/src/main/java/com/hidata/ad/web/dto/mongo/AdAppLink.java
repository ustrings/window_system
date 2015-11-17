package com.hidata.ad.web.dto.mongo;

import java.io.Serializable;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.DBExclude;
import com.hidata.framework.annotation.db.Table;

@Table("ad_mongo_app_link")
public class AdAppLink implements Serializable {
	
	@DBExclude
	private static final long serialVersionUID = 2072824063427730137L;
	
	@Column("id")
	private String id;
	
	@Column("ad_id")
	private String adId;
	
	@Column("app_id")
	private String appId;

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

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
}
