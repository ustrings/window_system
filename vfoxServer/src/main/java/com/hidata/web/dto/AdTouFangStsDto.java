package com.hidata.web.dto;


import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/**
 * 广告状态表 对应的表
 * @author xiaoming
 *
 */
@Table("ad_toufang_sts")
public class AdTouFangStsDto {
	
	@Column("id")
	private String id;
	
	@Column("sts_name")
	private String stsName;
	
	@Column("sts_index")
	private String stsIndex;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStsName() {
		return stsName;
	}

	public void setStsName(String stsName) {
		this.stsName = stsName;
	}

	public String getStsIndex() {
		return stsIndex;
	}

	public void setStsIndex(String stsIndex) {
		this.stsIndex = stsIndex;
	}
}
