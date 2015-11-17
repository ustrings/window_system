package com.hidata.web.dto;

import java.io.Serializable;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.DBExclude;
import com.hidata.framework.annotation.db.Table;

@Table("page_config_ad_rel")
public class AdInfoDto implements Serializable {

	@DBExclude
    private static final long serialVersionUID = 3779913323709837175L;

	// 记录 id
    @Column("id")
    private String id;
    
    // 广告参数id
    @Column("page_config_id")
    private String pageConfigId;
    
    // 广告名称
    @Column("sts")
    private String sts;
    
    // 广告名称
    @Column("url")
    private String url;
    
    // 广告名称
    @Column("targeturl")
    private String targeturl;
    
    // 广告名称
    @Column("title")
    private String title;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPageConfigId() {
		return pageConfigId;
	}

	public void setPageConfigId(String pageConfigId) {
		this.pageConfigId = pageConfigId;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTargeturl() {
		return targeturl;
	}

	public void setTargeturl(String targeturl) {
		this.targeturl = targeturl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
