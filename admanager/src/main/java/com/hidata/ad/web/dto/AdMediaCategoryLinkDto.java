package com.hidata.ad.web.dto;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/** 
 * 媒体分类
 * @author sunliyong
 *
 */
@Table("ad_media_category_link")
public class AdMediaCategoryLinkDto{
	@Column("id")
	private Integer id;
	
	@Column("ad_id")
	private String adId;
	
	@Column("media_category_code")
	private String media_category_code;
	
	@Column("createtime")
	private String createTime;
	
	@Column("sts")
	private String sts;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getMedia_category_code() {
		return media_category_code;
	}

	public void setMedia_category_code(String media_category_code) {
		this.media_category_code = media_category_code;
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
