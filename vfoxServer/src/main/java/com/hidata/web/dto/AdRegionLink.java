package com.hidata.web.dto;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/** 
 * 
 * @author sunliyong
 *
 */
@Table("ad_region_link")
public class AdRegionLink{
	@Column("id")
	private String id;
	
	@Column("ad_id")
	private String adId;
	
	@Column("region_code")
	private String regionCode;

	@Column("region_name")
	private String regionName;
	
	@Column("createtime")
	private String createTime;
	
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

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


}
