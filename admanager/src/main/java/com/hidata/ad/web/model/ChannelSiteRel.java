package com.hidata.ad.web.model;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/**
 * 频道域名表
 * @author ssq
 *
 */
@Table("channel_site_rel")
public class ChannelSiteRel {
	
	@Column("id")
	private Integer id;
	
	@Column("channel_id")
	private Integer channelId;
	
	@Column("site_url")
	private String siteUrl;
	
	@Column("site_desc")
	private String siteDesc;
	
	@Column("sts")
	private String sts;
	
	@Column("sts_date")
	private String stsDate;
	
	@Column("channel_name")
	private String channelName;
	
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	public String getSiteDesc() {
		return siteDesc;
	}

	public void setSiteDesc(String siteDesc) {
		this.siteDesc = siteDesc;
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


}
