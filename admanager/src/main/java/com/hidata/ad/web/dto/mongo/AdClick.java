package com.hidata.ad.web.dto.mongo;

import java.io.Serializable;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.DBExclude;
import com.hidata.framework.annotation.db.Table;

@Table("ad_mongo_click")
public class AdClick implements Serializable {
	
	@DBExclude
	private static final long serialVersionUID = 2072824063427730137L;
	
	@Column("id")
	private String id;
	
	@Column("type")
	private String type;
	
	@Column("target")
	private String target;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
}
