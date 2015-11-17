package com.hidata.ad.web.dto.mongo;

import java.io.Serializable;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.DBExclude;
import com.hidata.framework.annotation.db.Table;

@Table("ad_mongo_click_link")
public class AdClickLink implements Serializable {
	
	@DBExclude
	private static final long serialVersionUID = 2072824063427730137L;
	
	@Column("id")
	private String id;
	
	@Column("ad_id")
	private String adId;
	
	@Column("click_id")
	private String clickId;
	
	@Column("target")
	private String target;

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

	public String getClickId() {
		return clickId;
	}

	public void setClickId(String clickId) {
		this.clickId = clickId;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	
}
