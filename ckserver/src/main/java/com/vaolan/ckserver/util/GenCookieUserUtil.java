package com.vaolan.ckserver.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hidata.framework.util.CookieManager;
import com.hidata.framework.util.DateUtil;
import com.hidata.framework.util.http.RequestUtil;
import com.vaolan.ckserver.model.CookieUser;
import com.vaolan.ckserver.server.impl.CookieMappingServiceImpl;

/**
 * 
 * @author chenjinzhao
 * 
 */
public class GenCookieUserUtil {

	private static Logger logger = LoggerFactory
			.getLogger(CookieMappingServiceImpl.class);

	/**
	 * 装配cookie的全信息，如果cookie没有则种一个，并返回标志是否是新种的
	 * 
	 * @param cookieUser
	 * @param request
	 * @param response
	 * @return
	 */
	public static boolean assembleCookieUser(CookieUser cookieUser,
			HttpServletRequest request, HttpServletResponse response) {
		boolean isNewCookie = false;

		String createTime = DateUtil.getCurrentDateTimeStr();

		/**
		 * 得到我们dsp的cookie，如果存在读取，如果不存在种植
		 */
		String srcIP = RequestUtil.getClientIp(request);
		String userAgent = RequestUtil.getUserAgent(request);
		String hostName = request.getRemoteHost();

		String vdspCid = CookieManager.getValueByName(request,
				Constant.VAOLAN_COOKIE_NAME);
		if (StringUtils.isBlank(vdspCid)) {
			/**
			 * 生成唯一字符串，按srcip+ua+当前时间+随机数 md5生成
			 */
			vdspCid = CommonUtil.genCookieId(srcIP, userAgent);
			Cookie cookie = new Cookie(Constant.VAOLAN_COOKIE_NAME, vdspCid);
			cookie.setPath("/");
			cookie.setMaxAge(Integer.parseInt(Config
					.getProperty("cookie_expire_time")));
			response.addCookie(cookie);
			isNewCookie = true;
		}
		/**
		 * 一个cookie的全信息保存下来
		 */

		cookieUser.setCookieId(vdspCid);
		cookieUser.setHostName(hostName);
		cookieUser.setIpAddress(srcIP);
		cookieUser.setRequestProtocal(request.getProtocol());
		cookieUser.setUserAgent(userAgent);
		cookieUser.setCreateTime(createTime);

		return isNewCookie;
	}

	/**
	 * 记录cookie user信息
	 * 
	 * @param request
	 *            请求
	 * @param vdspCid
	 *            dsp的cookie Id
	 * @param isRecordDspckId
	 *            是否记录过该dsp的cookie id信息
	 */
	public static void logCookieUser(HttpServletRequest request,
			String vdspCid, boolean isRecordDspckId) {

		String vvdspCid = CookieManager.getValueByName(request,
				Constant.VAOLAN_COOKIE_NAME);

		// 如果受众没有dsp Id并且SSDB中没有dsp Id
		if (StringUtils.isBlank(vvdspCid) && !isRecordDspckId) {
			CookieUser cookieUser = new CookieUser();
			cookieUser.setCookieId(vdspCid);
			cookieUser.setHostName(request.getRemoteHost());
			cookieUser.setIpAddress(RequestUtil.getClientIp(request));
			cookieUser.setRequestProtocal(request.getProtocol());
			cookieUser.setUserAgent(RequestUtil.getUserAgent(request));
			cookieUser.setCreateTime(DateUtil.getCurrentDateTimeStr());

			AdStatLogUtil.saveCookieUser(cookieUser);
		}
	}

	/**
	 * 请求是否含有vaolan的cookie
	 * 
	 * @param cookieUser
	 * @param request
	 * @param response
	 * @return
	 */
	public static boolean isHaveDspCookieId(HttpServletRequest request) {
		String vdspCid = CookieManager.getValueByName(request,
				Constant.VAOLAN_COOKIE_NAME);
		if (StringUtils.isBlank(vdspCid)) {
			return false;
		}
		return true;
	}

	/**
	 * 通过dspid生成一个cookie信息
	 * 
	 * @param vdspCid
	 * @return
	 */
	public static Cookie getCookie(String vdspCid) {

		/**
		 * 生成唯一字符串，按srcip+ua+当前时间+随机数 md5生成
		 */
		if (StringUtils.isBlank(vdspCid)) {
			return null;
		}

		Cookie cookie = new Cookie(Constant.VAOLAN_COOKIE_NAME, vdspCid);
		cookie.setPath("/");
		cookie.setMaxAge(Integer.parseInt(Config
				.getProperty("cookie_expire_time")));

		return cookie;
	}

	/**
	 * 通过vdsp获取Cookie信息，如果vdspCid为空则生成一个cookie
	 * 
	 * @param request
	 * @return
	 */
	public static String composeDspCookieId(HttpServletRequest request) {
		String vdspCid = CookieManager.getValueByName(request,
				Constant.VAOLAN_COOKIE_NAME);
		if (StringUtils.isBlank(vdspCid)) {
			/**
			 * 生成唯一字符串，按srcip+ua+当前时间+随机数 md5生成
			 */
			String userAgent = RequestUtil.getUserAgent(request);
			String srcIP = RequestUtil.getClientIp(request);
			vdspCid = CommonUtil.genCookieId(srcIP, userAgent);
		}

		return vdspCid;
	}

	/**
	 * 判断SSDB中的dspId和传递过来的dspid是否相同
	 * 
	 * @param dspId
	 * @param request
	 * @return
	 */
	public static void judgeSSDBandDspEqual(String adxId, String ssdbDspId,
			String vdspCid) {

		if (!vdspCid.equals(ssdbDspId)) {
			logger.error("GenCookieUserUtil judgeSSDBandDspEqual is error,adxId="
					+ adxId
					+ ", ssdbdspId = "
					+ ssdbDspId
					+ ", pagedspid="
					+ vdspCid);
		}
	}
}
