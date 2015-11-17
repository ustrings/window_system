package com.hidata.framework.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.hidata.framework.log.LogManager;

/**
 * cookie 管理员
 * @author houzhaowei
 *
 */
public class CookieManager {
	
	private static LogManager logger = LogManager.getLogger(CookieManager.class);
	
	private static String LOG_KEY = "CookieManager";
	
	/**
	 * 根据cookie 的key 获取value
	 * @param request HttpServletRequest
	 * @param name cookie 的key
	 * @return value
	 */
	public static String getValueByName(HttpServletRequest request ,String name){
		String value = null;
		try{
			Cookie[] cookies = request.getCookies();
			if(cookies != null){
				for(int i = 0 ; i < cookies.length ; i++){
					Cookie cookie = cookies[i];
					if(name.equals(cookie.getName())){
						value = cookie.getValue();
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(LOG_KEY, "get cookie exception:" + e.getMessage());
			return null;
		}
		return value;
	}
}
