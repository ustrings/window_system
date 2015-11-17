package com.vaolan.ckserver.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaolan.ckserver.model.CookieMapModel;
import com.vaolan.ckserver.model.CookieUser;



public interface CookieMappingService {

	/**
	 * 保存种cookie的信息以及，cookiemapping信息
	 * @param cookieMapModel
	 * @param cookieUser
	 * @param isNewCookie
	 */
	@Deprecated
	public abstract void saveCookieAndMappingInfo(CookieMapModel cookieMapModel,CookieUser cookieUser,boolean isNewCookie);

	/**
	 * 进行cookie mapping，并记录相关信息
	 * @param request
	 * @param response
	 * @return
	 */
	public abstract boolean cookieMapping(final HttpServletRequest request, HttpServletResponse response);
}