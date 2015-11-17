package com.vaolan.sspserver.model;
import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/**
 * 广告主体信息 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月18日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * @author pj
 */
@Table("ad_media_category_link")
public class MediaCategory
{
	
	@Column("media_category_code")
	private int mediaCategoryCode;

	public int getMediaCategoryCode() {
		return mediaCategoryCode;
	}

	public void setMediaCategoryCode(int mediaCategoryCode) {
		this.mediaCategoryCode = mediaCategoryCode;
	}
}
