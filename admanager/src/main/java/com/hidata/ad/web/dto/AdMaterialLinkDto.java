package com.hidata.ad.web.dto;

import java.io.Serializable;
import java.util.Date;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/**
 * 广告与广告物料关联信息 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月11日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */

@Table("ad_m_link")
public class AdMaterialLinkDto implements Serializable {

    private static final long serialVersionUID = 3779913323709837173L;
    
    @Column("id")
    private String adMLinkId;
    
    //广告ID
    @Column("ad_id")
    private String adId;
    
	//广告物料ID
    @Column("ad_m_id")
    private String adMaterId;

	//创建时间
    @Column("create_time")
    private String createTime;

	//状态
    @Column("sts")
    private String sts;
    
	@Column("check_status")
	private String checkStatus;
	
	@Column("comment")
	private String comment;

	public String getAdMLinkId() {
		return adMLinkId;
	}

	public void setAdMLinkId(String adMLinkId) {
		this.adMLinkId = adMLinkId;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getAdMaterId() {
		return adMaterId;
	}

	public void setAdMaterId(String adMaterId) {
		this.adMaterId = adMaterId;
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
