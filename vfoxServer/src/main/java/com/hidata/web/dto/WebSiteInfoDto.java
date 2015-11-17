package com.hidata.web.dto;

import java.io.Serializable;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.DBExclude;
import com.hidata.framework.annotation.db.Table;

/**
 * 广告主体信息 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月11日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
@Table("web_site_info")
public class WebSiteInfoDto implements Serializable {

	@DBExclude
    private static final long serialVersionUID = 3779913323709837175L;

	// 记录 id
    @Column("id")
    private String id;
    
    // 广告参数id
    @Column("page_config_id")
    private String pageConfigId;
    
    // 广告参数id
    @Column("web_site_name")
    private String webSiteName;
    
    // 广告参数id
    @Column("page_config_id_two")
    private String pageConfigIdTwo;
    
    // 广告参数id
    @Column("web_site_imge_url")
    private String webSiteImgeUrl;
    
    // 广告参数id
    @Column("priority")
    private String priority;
    
    // 广告参数id
    @Column("web_site_desc")
    private String webSiteDesc;
    
    // 广告参数id
    @Column("create_time")
    private String createTime;
    
    // 广告参数id
    @Column("update_time")
    private String updateTime;
    
    // 广告参数id
    @Column("sts")
    private String sts;
    
    // 广告参数id
    @Column("publish_time")
    private String publishTime;
    
    // 广告参数id
    @Column("host")
    private String host;
    
    // 广告参数id
    @Column("web_site_name2")
    private String webSiteName2;
    
    // 广告名称
    @Column("host2")
    private String host2;

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

	public String getWebSiteName() {
		return webSiteName;
	}

	public void setWebSiteName(String webSiteName) {
		this.webSiteName = webSiteName;
	}
	
	public String getPageConfigIdTwo() {
		return pageConfigIdTwo;
	}

	public void setPageConfigIdTwo(String pageConfigIdTwo) {
		this.pageConfigIdTwo = pageConfigIdTwo;
	}

	public String getWebSiteImgeUrl() {
		return webSiteImgeUrl;
	}

	public void setWebSiteImgeUrl(String webSiteImgeUrl) {
		this.webSiteImgeUrl = webSiteImgeUrl;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getWebSiteDesc() {
		return webSiteDesc;
	}

	public void setWebSiteDesc(String webSiteDesc) {
		this.webSiteDesc = webSiteDesc;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getPublishTime() {
		return publishTime;
	}


	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}

	public String getWebSiteName2() {
		return webSiteName2;
	}

	public void setWebSiteName2(String webSiteName2) {
		this.webSiteName2 = webSiteName2;
	}

	public String getHost2() {
		return host2;
	}

	public void setHost2(String host2) {
		this.host2 = host2;
	}

}
