package com.hidata.ad.web.model;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

@Table("ad_m_link")
public class AdMLink {
	@Column("id")
	private int id;
	
	@Column("ad_id")
	private int adId;
	
	@Column("ad_m_id")
	private int adMId;
	
	@Column("create_time")
	private String createTime;
	
	@Column("sts")
	private String sts;
	
	@Column("check_status")
	private String checkStatus;
	
	@Column("comment")
	private String comment;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAdId() {
		return adId;
	}

	public void setAdId(int adId) {
		this.adId = adId;
	}

	public int getAdMId() {
		return adMId;
	}

	public void setAdMId(int adMId) {
		this.adMId = adMId;
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

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
