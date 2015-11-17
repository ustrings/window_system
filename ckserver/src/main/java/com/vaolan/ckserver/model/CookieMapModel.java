package com.vaolan.ckserver.model;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;


@Table("ad_cookie_mapping")
public class CookieMapModel {
	
	@Column("id")
	private String id;
	
	@Column("adx_cid")
	private String adxCid;
	
	@Column("vdsp_cid")
	private String vdspCid;
	
	@Column("adx_vendor")
	private String adxVendor;
	
	@Column("create_time")
	private String createTime;

	@Column("sts")
	private String sts;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdxCid() {
		return adxCid;
	}

	public void setAdxCid(String adxCid) {
		this.adxCid = adxCid;
	}

	public String getVdspCid() {
		return vdspCid;
	}

	public void setVdspCid(String vdspCid) {
		this.vdspCid = vdspCid;
	}

	public String getAdxVendor() {
		return adxVendor;
	}

	public void setAdxVendor(String adxVendor) {
		this.adxVendor = adxVendor;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}
	
	

}
