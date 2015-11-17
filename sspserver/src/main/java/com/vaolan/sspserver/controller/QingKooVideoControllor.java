package com.vaolan.sspserver.controller;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hidata.framework.util.http.RequestUtil;
import com.vaolan.sspserver.ipsearch.IpSearch;
import com.vaolan.sspserver.model.AdFilterElement;
import com.vaolan.sspserver.model.VarSizeBean;
import com.vaolan.sspserver.service.AdPlanService;
import com.vaolan.sspserver.util.JavaMd5Util;

@Controller
public class QingKooVideoControllor {

	private static Logger Log = Logger.getLogger(QingKooVideoControllor.class);

	@Autowired
	private AdPlanService adPlanService;

	@RequestMapping(value = "/qk_video", produces = "application/json")
	@ResponseBody
	public String shdxAdCode(HttpServletRequest request,
			HttpServletResponse response) {

		try {

			String ref = "";
			String adforcerReferer = request.getParameter("adforcerReferer");
			String adId = request.getParameter("advId");
			String ctype = "0";
			String adAcct = request.getParameter("radius");
			String ip = RequestUtil.getClientIp(request);
			String userAgent = RequestUtil.getUserAgent(request);
			String referrer = RequestUtil.getRefererUrl(request);
			String template = request.getParameter("template");

			String sizeBw = request.getParameter("size_bw");
			String sizeBh = request.getParameter("size_bh");

			String sizeSw = request.getParameter("size_sw");
			String sizeSh = request.getParameter("size_sh");

			VarSizeBean vsb = null;

			if (StringUtils.isNotBlank(sizeSw)
					&& StringUtils.isNotBlank(sizeSh)
					&& StringUtils.isNotBlank(sizeBw)
					&& StringUtils.isNotBlank(sizeBh)) {
				
				vsb = new VarSizeBean();
				
				vsb.setSizeBigWidth(sizeBw);
				vsb.setSizeBigHeight(sizeBh);
				vsb.setSizeSmallWidth(sizeSw);
				vsb.setSizeSmallHeight(sizeSh);
				

			}

			

			ref = adforcerReferer;

			String host = "";
			try {
				URL url = new URL(ref);
				host = url.getHost();
			} catch (Exception e) {
				Log.warn("背景url为空，或者解析host 出错！！");
				;
			}

			AdFilterElement adFilterElement = new AdFilterElement();
			adFilterElement.setAdAcct(adAcct);
			adFilterElement.setAdId(adId);
			adFilterElement.setHost(host);
			adFilterElement.setRef(ref);
			adFilterElement.setUserAgent(userAgent);
			adFilterElement.setIp(ip);
			adFilterElement.setIp(IpSearch.getAddrByIp(ip));

			String adTag = adPlanService.adRetrieveForQingKooVideo(
					adFilterElement, template,vsb);

			return adTag.toString();
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("广告位请求后台中心服务出错", e);
		} finally {

		}
		return null;
	}

	@RequestMapping(value = "/shdxDemo", produces = "application/json")
	@ResponseBody
	public String shdxAdCodeDemo(HttpServletRequest request,
			HttpServletResponse response) {

		try {

			String ref = "";
			String adforcerReferer = request.getParameter("adforcerReferer");
			String adId = request.getParameter("advId");
			String ctype = "0";
			String adAcct = request.getParameter("radius");
			String ip = RequestUtil.getClientIp(request);
			String userAgent = RequestUtil.getUserAgent(request);
			String referrer = RequestUtil.getRefererUrl(request);
			String template = request.getParameter("template");
			ref = adforcerReferer;
			String host = "";
			try {
				URL url = new URL(ref);
				host = url.getHost();
			} catch (Exception e) {
				Log.warn("背景url为空，或者解析host 出错！！");
				;
			}

			AdFilterElement adFilterElement = new AdFilterElement();
			adFilterElement.setAdAcct(adAcct);
			adFilterElement.setAdId(adId);
			adFilterElement.setHost(host);
			adFilterElement.setRef(ref);
			adFilterElement.setUserAgent(userAgent);
			adFilterElement.setIp(ip);

			String adTag = adPlanService.adRetrieveForQingKooVideoDemo(
					adFilterElement, template);

			return adTag.toString();
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("广告位请求后台中心服务出错", e);
		} finally {

		}
		return null;
	}

	public static void main(String[] args) {
		String ad = "a3b22964c9900469903e8ae7cccc206bd31f28ff";
		String ua = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36";

		String ad16 = JavaMd5Util.md5EncrypFirst16(ad);

		String ua16 = JavaMd5Util.md5EncrypFirst16(ua);

		System.out.println(ad16.toLowerCase() + ua16.toLowerCase());
	}

}
