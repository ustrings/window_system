package com.hidata.web.dto;

import java.io.Serializable;
import java.util.Date;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/**
 * 广告投放站点信息 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月11日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */

@Table("ad_site")
public class AdSiteDto implements Serializable {

    private static final long serialVersionUID = 3779913323709837175L;

    @Column("ad_s_id")
    private String adSiteId;

    @Column("ad_id")
    private String adId;

    @Column("url")
    private String url;

    @Column("match_type")
    private String matchType;

    @Column("create_time")
    private String createTime;

    @Column("sts")
    private String sts;
    
    @Column("channel_id")
    private String channelId;
    
    @Column("site_desc")
    private String siteDesc;
    
    @Column("channel_name")
    private String channelName;

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getSiteDesc() {
		return siteDesc;
	}

	public void setSiteDesc(String siteDesc) {
		this.siteDesc = siteDesc;
	}

	public String getAdSiteId() {
		return adSiteId;
	}

	public void setAdSiteId(String adSiteId) {
		this.adSiteId = adSiteId;
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

	public String getMatchType() {
		return matchType;
	}

	public void setMatchType(String matchType) {
		this.matchType = matchType;
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
