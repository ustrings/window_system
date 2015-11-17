package com.hidata.ad.web.dto.mongo;

import java.io.Serializable;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.DBExclude;
import com.hidata.framework.annotation.db.Table;

@Table("ad_mongo_industry")
public class AdIndustry implements Serializable {
	
	@DBExclude
	private static final long serialVersionUID = 2072824063427730137L;
	
	@Column("id")
	private String id;
	
	@Column("description")
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
