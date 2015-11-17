package com.hidata.web.dto;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

@Table("ad_ext_link")
public class AdExtLinkDto {
	@Column("id")
	private String id;
	@Column("ad_instance_id")
	private String adInstanceId;
	@Column("throw_url")
	private String throwUrl;
	@Column("pic_size")
	private String picSize;
	@Column("width")
	private String width;
	@Column("height")
	private String height;
	@Column("sts")
	private String sts;
	@Column("sts_date")
	private String stsDate;
	@Column("remark")
	private String remark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAdInstanceId() {
		return adInstanceId;
	}
	public void setAdInstanceId(String adInstanceId) {
		this.adInstanceId = adInstanceId;
	}
	public String getThrowUrl() {
		return throwUrl;
	}
	public void setThrowUrl(String throwUrl) {
		this.throwUrl = throwUrl;
	}
	public String getPicSize() {
		return picSize;
	}
	public void setPicSize(String picSize) {
		this.picSize = picSize;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getSts() {
		return sts;
	}
	public void setSts(String sts) {
		this.sts = sts;
	}
	public String getStsDate() {
		return stsDate;
	}
	public void setStsDate(String stsDate) {
		this.stsDate = stsDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
