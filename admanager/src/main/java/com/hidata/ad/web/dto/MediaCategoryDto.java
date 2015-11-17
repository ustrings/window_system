package com.hidata.ad.web.dto;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/** 
 * 媒体分类
 * @author sunliyong
 *
 */
@Table("media_category")
public class MediaCategoryDto{
	
	@Column("code")
	private String code;
	
	@Column("name")
	private String name;
	
	@Column("parent_media_category_code")
	private String parentCode;
	
	@Column("sts")
	private String sts;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

}
