package com.hidata.web.dto;

import com.hidata.framework.annotation.db.Column;

public class WebSiteInfoShowDto {
	// 记录 id
    @Column("id")
    private String id;
    
    @Column("web_site_name")
    private String webSiteName;
    
    @Column("sts")
    private String sts;
    
    @Column("page_width")
    private String width;
    
    @Column("page_height")
    private String height;
    
    @Column("name1")
    private String name1;
    
    @Column("name2")
    private String name2;
    
    @Column("web_site_imge_url")
    private String webSiteImgeUrl;
    
    @Column("priority")
    private String priority;
    
    @Column("web_site_desc")
    private String webSiteDesc;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWebSiteName() {
		return webSiteName;
	}

	public void setWebSiteName(String webSiteName) {
		this.webSiteName = webSiteName;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
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

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
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
    
}
