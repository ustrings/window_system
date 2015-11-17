package com.vaolan.sspserver.controller;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hidata.framework.util.http.RequestUtil;
import com.ideal.encode.SelfBase64Test;
import com.vaolan.sspserver.ipsearch.IpSearch;
import com.vaolan.sspserver.model.AdFilterElement;
import com.vaolan.sspserver.model.AdvPlan;
import com.vaolan.sspserver.service.DyAdService;
import com.vaolan.sspserver.util.Config;
import com.vaolan.sspserver.util.CookieUtil;

@Controller
public class ShPortalControllor {

	private static Logger Log = Logger.getLogger(ShPortalControllor.class);

	@Autowired
	private DyAdService dyAdService;

	private Set<String> whithIpsMap = new HashSet<String>();

	public ShPortalControllor() {
		String whithNHTips = Config.getProperty("white_nht_ips");

		if (StringUtils.isNotBlank(whithNHTips)) {
			String[] ips = whithNHTips.split(",");

			if (ips != null && ips.length > 0) {
				for (int i = 0; i < ips.length; i++) {
					whithIpsMap.add(ips[i]);
				}
			}
		}
	}

	@RequestMapping(value = "/dyad")
	public String dyAd(HttpServletRequest request, HttpServletResponse response) {

		try {
			String width = request.getParameter("w");
			String height = request.getParameter("h");
			String ad = request.getParameter("ad");
			String referrer = RequestUtil.getRefererUrl(request);
			String clientIp = RequestUtil.getClientIp(request);
			String userAgent = RequestUtil.getUserAgent(request);
			StringBuffer url = request.getRequestURL();
			String ad_acct = "";
			String _us = request.getParameter("_us");
			try {
				ad_acct = SelfBase64Test.getAd(_us);
			} catch (Exception e) {
				Log.error("电信传过来的加密帐号解密出错,_us" + _us);
			}

			AdFilterElement adFilterElement = new AdFilterElement();

			adFilterElement.setIp(clientIp);
			adFilterElement.setAdAcct(ad_acct);
			adFilterElement.setUserAgent(userAgent);
			adFilterElement.setRef(referrer);
			adFilterElement.setHeight(height);
			adFilterElement.setWidth(width);
			adFilterElement.setAdId("0");
			adFilterElement.setRegion(IpSearch.getAddrByIp(clientIp));

			String impuuid = dyAdService.genImpressionUUID(clientIp,
					url.toString(), referrer, userAgent);

			AdvPlan displayAd = dyAdService.dyAdRetrieveForShPortal(width,
					height, adFilterElement);

			if (displayAd == null) {
				PrintWriter out = response.getWriter();
				out.write("");
			} else {

				request.setAttribute("width", width);
				request.setAttribute("height", height);

				request.setAttribute("displayAdId", displayAd.getAdInstance()
						.getAdId());
				request.setAttribute("displayUserId", displayAd.getAdInstance()
						.getUserId());
				request.setAttribute("ad_acct", ad_acct);
				request.setAttribute("referrer", referrer);
				request.setAttribute("impuuid", impuuid);
				request.setAttribute("pageId", ad + "_" + width + "x" + height);

				if (whithIpsMap.contains(clientIp)) {
					String pageId = ad + "_" + width + "x" + height;

					String statCode = dyAdService.getStatCode(pageId);

					request.setAttribute("statCode", statCode);
				} else {
					request.setAttribute("statCode", "");
				}

				return "dyad";
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.error("选择动态广告出错", e);
		} finally {

		}
		return null;
	}

	/**
	 * 判断是否需要执行pv信息代码，判断impuuid 是否在存在，如果存在就标识需要记录pv信息并删除impuuid
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pvneed", produces = "application/json")
	@ResponseBody
	public String isPvNeed(HttpServletRequest request,
			HttpServletResponse response) {

		String impuuid = request.getParameter("impuuid");
		String pageId = request.getParameter("pageId");

		String adId = request.getParameter("adId");
		String referrer = request.getParameter("referrer");

		/**
		 * 得到当前用户的cookie，如果没有种植一个
		 */
		String cookieName = "vlsspid";

		String cookieValue = CookieUtil.composeDspCookieId(request, cookieName);

		if (!CookieUtil.isHaveDspCookieId(request, cookieName)) {
			Cookie cookie = CookieUtil.getCookie(cookieName, cookieValue,
					Integer.parseInt(Config.getProperty("cookie_expire_time")));

			response.addCookie(cookie);
		}

		boolean b = dyAdService.isNeedExecStatCode(cookieValue, impuuid, adId,
				referrer);

		String statCode = "";

		if (b) {
			statCode = dyAdService.getStatCode(pageId);
		} else {
			statCode = "0";
		}

		return statCode;
	}

}
