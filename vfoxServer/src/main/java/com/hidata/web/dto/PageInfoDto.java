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
@Table("page_config")
public class PageInfoDto implements Serializable {

	@DBExclude
    private static final long serialVersionUID = 3779913323709837175L;

    @Column("id")
    private String pageId;
    
    // 广告名称
    @Column("page_name")
    private String pageName;
    
    // 广告名称
    @Column("page_width")
    private String pageWidth;
    
    // 广告名称
    @Column("page_height")
    private String pageHeight;
    
    // 广告名称
    @Column("jsp_path")
    private String jspPath;
    
    // 广告名称
    @Column("sts")
    private String sts;


	// 开始时间
    @Column("create_time")
    private String createTime;

    // 结束时间
    @Column("update_time")
    private String updateTime;
    
    // 结束时间
    @Column("publish_time")
    private String publishTime;
    
    // 结束时间
    @Column("site_type_code")
    private String siteTypeCode;

    // 预算
    @Column("parent_config_id")
    private String parentConfigId;
    
    // 预算
    @Column("type")
    private String type;
    
    // 预算
    @Column("level")
    private String level;

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getPageWidth() {
		return pageWidth;
	}

	public void setPageWidth(String pageWidth) {
		this.pageWidth = pageWidth;
	}

	public String getPageHeight() {
		return pageHeight;
	}

	public void setPageHeight(String pageHeight) {
		this.pageHeight = pageHeight;
	}

	public String getJspPath() {
		return jspPath;
	}

	public void setJspPath(String jspPath) {
		this.jspPath = jspPath;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
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

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getSiteTypeCode() {
		return siteTypeCode;
	}

	public void setSiteTypeCode(String siteTypeCode) {
		this.siteTypeCode = siteTypeCode;
	}

	public String getParentConfigId() {
		return parentConfigId;
	}

	public void setParentConfigId(String parentConfigId) {
		this.parentConfigId = parentConfigId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
   
}
