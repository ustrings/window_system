package com.vaolan.adserver.dao;

import com.vaolan.adserver.model.CookieUser;

public interface CookieUserDao {

	/* 
	 * 保存cookie信息
	 */
	public abstract int save(CookieUser cookie);

}