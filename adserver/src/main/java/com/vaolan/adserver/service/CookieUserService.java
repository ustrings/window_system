package com.vaolan.adserver.service;


import com.vaolan.adserver.model.CookieUser;
import com.vaolan.adserver.model.ImpressLog;

public interface CookieUserService {

	/**
	 * 保存cookie的同时，记录访问,ImpressLog log日志
	 *  if 新cookie 则保存cookie信息，否则只保存日志
	 * */
	public abstract int saveCookieInfo(CookieUser cookie,ImpressLog log,boolean isNew);

}