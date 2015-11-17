package com.hidata.ad.web.dto;

import java.io.Serializable;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

@Table("ad_category")
public class AdCategoryDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6031459434539845383L;

	@Column("ad_category_id")
	private String id;
	
	@Column("name")
	private String name;
	
	@Column("parent_code")
	private String parentCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
    
}
