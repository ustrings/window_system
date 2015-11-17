package com.vaolan.sspserver.model;

import java.io.Serializable;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/**
 * 广告投放url信息 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月11日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author chenjizhao
 */

@Table("ad_url")
public class AdUrl implements Serializable
{

	private static final long serialVersionUID = 3779913323709837175L;

	
	@Column("ad_u_id")
	private String adUid;
	
	@Column("ad_id")
	private String adId;
	
	@Column("url")
	private String url;
	
	@Column("create_time")
	private String createTime;
	
	@Column("sts") 
	private String sts;
	
	

	public String getAdUid() {
		return adUid;
	}

	public void setAdUid(String adUid) {
		this.adUid = adUid;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
