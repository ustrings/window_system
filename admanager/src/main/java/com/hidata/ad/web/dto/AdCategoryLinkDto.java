package com.hidata.ad.web.dto;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/** 
 * 媒体分类
 * @author sunliyong
 *
 */
@Table("ad_category_link")
public class AdCategoryLinkDto{
	@Column("ad_id")
	private String ad_id;
	
	@Column("ad_category_id")
	private String ad_category_id;

	public String getAd_id() {
		return ad_id;
	}

	public void setAd_id(String ad_id) {
		this.ad_id = ad_id;
	}

	public String getAd_category_id() {
		return ad_category_id;
	}

	public void setAd_category_id(String ad_category_id) {
		this.ad_category_id = ad_category_id;
	}
	
}
