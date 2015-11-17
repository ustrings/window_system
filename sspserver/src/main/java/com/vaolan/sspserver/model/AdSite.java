package com.vaolan.sspserver.model;

import java.io.Serializable;

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
public class AdSite implements Serializable
{

	private static final long serialVersionUID = 3779913323709837175L;


	@Column("url")
	private String url;

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}
}
