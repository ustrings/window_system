package com.vaolan.adserver.util;

import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.hidata.framework.util.CookieManager;
import com.hidata.framework.util.http.RequestUtil;

public class CookieUtil {
	/**
	 * 获取用户访问频率的cookie， 若存在则返回。若不存在，则生成一个
	 * @param request
	 * @return
	 */
	public static String composeDspCookieId(HttpServletRequest request){
	    String vdspCid = CookieManager.getValueByName(request, Constant.USER_FREQUENCY_COOKIE);
		if(StringUtils.isBlank(vdspCid)){
		    /**
		     * 生成唯一字符串，按srcip+ua+当前时间+随机数 md5生成
		     */
			String userAgent = RequestUtil.getUserAgent(request);
			String srcIP = RequestUtil.getClientIp(request);
			vdspCid = genCookieId(srcIP, userAgent);
		}
		
		return vdspCid;
	}
	public static String genCookieId(String srcIP,String userAgent){
		String cookieId = "";
		String sysTime = System.currentTimeMillis()+"";
		Random r = new Random();
		String rStr = r.nextInt()+"";
		
	    cookieId = Constant.FREQUENCY + JavaMd5Util.md5Encryp(srcIP,userAgent,sysTime,rStr);
		return cookieId;
	}
	/**
	 * 判断cookie中是否含有 用户请求频率的 cookie
	 * @param request
	 * @return
	 */
	public static boolean isHaveDspCookieId(HttpServletRequest request){
	    String vdspCid = CookieManager.getValueByName(request, Constant.USER_FREQUENCY_COOKIE);
		if(StringUtils.isBlank(vdspCid)){
			return false;
		}
		return true;
	}
	
	//生成用户访问频率的cookie
	public static Cookie getCookie(String vdspCid){
		
	    /**
	     * 生成唯一字符串，按srcip+ua+当前时间+随机数 md5生成
	     */
		if (StringUtils.isBlank(vdspCid))
		{
			return null;
		}
		
		Cookie cookie = new Cookie(Constant.USER_FREQUENCY_COOKIE, vdspCid);
		cookie.setPath("/");
		cookie.setMaxAge(Integer.parseInt(Config.getProperty("cookie_expire_time")));
		
		return cookie;
	}
}
