package com.vaolan.sspserver.model;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/**
 * 广告主体信息 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月11日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author fsl
 */
@Table("ad_category")
public class AdCategory
{
	@Column("ad_id")
	private String adId;
	
	@Column("ad_category_id")
	private int categoryId;

	/**
	 * @return the adId
	 */
	public String getAdId()
	{
		return adId;
	}

	/**
	 * @param adId the adId to set
	 */
	public void setAdId(String adId)
	{
		this.adId = adId;
	}

	/**
	 * @return the categoryId
	 */
	public int getCategoryId()
	{
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(int categoryId)
	{
		this.categoryId = categoryId;
	}
}
