package com.vaolan.sspserver.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;	

import net.sf.json.JSONObject;
import nl.bitwalker.useragentutils.DeviceType;
import nl.bitwalker.useragentutils.UserAgent;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hidata.framework.util.DateUtil;
import com.hidata.framework.util.http.RequestUtil;
import com.vaolan.sspserver.filter.AdplanKeywordFilter;
import com.vaolan.sspserver.ipsearch.IpSearch;
import com.vaolan.sspserver.model.AdFilterElement;
import com.vaolan.sspserver.model.AdShowPara;
import com.vaolan.sspserver.service.AdPlanService;
import com.vaolan.sspserver.service.DyAdService;
import com.vaolan.sspserver.util.JavaMd5Util;
import com.vaolan.sspserver.util.OnlineMonitorUtil;

@Controller
public class SSPCenterControllor {

	private static Logger Log = Logger.getLogger(SSPCenterControllor.class);

	@Autowired
	private AdPlanService adPlanService;

	@Autowired
	private DyAdService dyAdService;

	@Autowired
	private OnlineMonitorUtil olm;

	@Autowired
	private AdplanKeywordFilter adplanKeywordFilter;

	private static Logger logger = Logger.getLogger(SSPCenterControllor.class);

	/**
	 * 处理用户访问请求
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/c2", produces = "application/json")
	@ResponseBody
	public String sspCenter(HttpServletRequest request,
			HttpServletResponse response) {

		String content = "";
		try {
			// 获取用户的 User Agent 浏览器信息
			UserAgent uaInfo = new UserAgent(RequestUtil.getUserAgent(request));
			// 获取设备信息
			DeviceType device = uaInfo.getOperatingSystem().getDeviceType();
			// 获取区域信息
			String area = request.getParameter("area") == null ? "" : request
					.getParameter("area");
			String type = request.getParameter("ptey") == null ? "" : request
					.getParameter("ptey");

			// 所有流量速度，包括爬虫和正常的
			olm.alldSspPushOnlineMontor(area);

			// TODO *****重要，测试版本需要把 cookie 控制注销，生产版本需要把 cookie 控制打开*******
			// boolean userckNeed = olm.isdisplayadbyck(request, response);
			// if(!userckNeed){
			// String removeSelf =
			// "var sc = document.getElementsByTagName(\"script\");\n";
			// removeSelf += "var node =  sc[sc.length-1];\n";
			// removeSelf += "node.parentNode.removeChild(node);\n";
			//
			// return removeSelf;
			// }

			boolean ct = true;
			long centerStart = System.currentTimeMillis();
			// 如果是pc，mobile，tablet 才接受请求，否则不接受
			if (DeviceType.MOBILE.equals(device)
					|| DeviceType.COMPUTER.equals(device)
					|| DeviceType.TABLET.equals(device)) {
				// 更新在线统计信息
				olm.vaildSspPushOnlineMontor(area);
				AdFilterElement adFilterElement = new AdFilterElement();
				// 设置过滤元素的属性
				this.assemblyAdFilterElement(request, adFilterElement);
				// 检索广告
				AdShowPara adShowPara = adPlanService.adRetrieveForVaolanPush(
						adFilterElement, request, response);
				
				adShowPara.setArea(area);

			
				 String removeSelf = "var sc = document.getElementsByTagName(\"script\");\n";
					removeSelf += "var node =  sc[sc.length-1];\n"; 
					removeSelf +="node.parentNode.removeChild(node);\n";
				JSONObject adJson = JSONObject.fromObject(adShowPara);
				return removeSelf+"psda(" + adJson.toString() + ")";

			} else {
				ct = false;
				logger.warn("ua_invaild:请求的ua不正常,ua:"
						+ RequestUtil.getUserAgent(request));
				content = "";

			}

			long centerEnd = System.currentTimeMillis();

			long centerTime = centerEnd - centerStart;

			if (ct) {
				logger.info("timelog: centerTime, 有效请求选取广告完毕用时:" + centerTime);
			} else {
				logger.info("timelog: centerTime, 无效请求选取广告完毕用时:" + centerTime);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.error("广告位请求后台中心服务出错", e);
		} finally {

		}
		return content;
	}

	/**
	 * 处理用户访问请求
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/centerWH", produces = "application/json")
	@ResponseBody
	public String sspCenterByWH(HttpServletRequest request,
			HttpServletResponse response) {

		String content = "";
		try {
			// 获取用户的 User Agent 浏览器信息
			UserAgent uaInfo = new UserAgent(RequestUtil.getUserAgent(request));
			// 获取设备信息
			DeviceType device = uaInfo.getOperatingSystem().getDeviceType();
			// 获取区域信息
			String area = request.getParameter("area") == null ? "" : request
					.getParameter("area");
			String type = request.getParameter("ptey") == null ? "" : request
					.getParameter("ptey");

			// 所有流量速度，包括爬虫和正常的
			// olm.alldSspPushOnlineMontor(area);

			// TODO *****重要，测试版本需要把 cookie 控制注销，生产版本需要把 cookie 控制打开*******
			// boolean userckNeed = olm.isdisplayadbyck(request, response);
			// if(!userckNeed){
			// String removeSelf =
			// "var sc = document.getElementsByTagName(\"script\");\n";
			// removeSelf += "var node =  sc[sc.length-1];\n";
			// removeSelf += "node.parentNode.removeChild(node);\n";
			//
			// return removeSelf;
			// }

			boolean ct = true;
			long centerStart = System.currentTimeMillis();
			// 如果是pc，mobile，tablet 才接受请求，否则不接受
			if (DeviceType.MOBILE.equals(device)
					|| DeviceType.COMPUTER.equals(device)
					|| DeviceType.TABLET.equals(device)) {
				// 更新在线统计信息
				olm.vaildSspPushOnlineMontor(area);
				AdFilterElement adFilterElement = new AdFilterElement();
				// 设置过滤元素的属性
				this.assemblyAdFilterElement(request, adFilterElement);
				// 检索广告
				String adTag = adPlanService.adRetrieveForVaolanPushWH(
						adFilterElement, request, response);
				String areaTag = "var area='" + area + "';\n";
				String typeTag = "var type='" + type + "';\n";
				adTag = areaTag + adTag + typeTag;

				// String removeSelf =
				// "var sc = document.getElementsByTagName(\"script\");\n";
				// removeSelf += "var node =  sc[sc.length-1];\n";
				// removeSelf += "node.parentNode.removeChild(node);\n";
				//
				// adTag = removeSelf+adTag;
				content = adTag.toString();
			} else {
				ct = false;
				logger.warn("ua_invaild:请求的ua不正常,ua:"
						+ RequestUtil.getUserAgent(request));
				content = "";

			}

			long centerEnd = System.currentTimeMillis();

			long centerTime = centerEnd - centerStart;

			if (ct) {
				logger.info("timelog: centerTime, 有效请求选取广告完毕用时:" + centerTime);
			} else {
				logger.info("timelog: centerTime, 无效请求选取广告完毕用时:" + centerTime);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.error("广告位请求后台中心服务出错", e);
		} finally {

		}
		return content;
	}

	@RequestMapping(value = "/deadx")
	public String deadXRedirect(HttpServletRequest request,
			HttpServletResponse response) {
		String adId = request.getParameter("adId");
		String targetUrl = "";
		try {
			targetUrl = adPlanService.getAdTargetUrl(adId);
			if (StringUtils.isBlank(targetUrl)) {
				targetUrl = "http://www.vao365.com";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return "redirect:" + targetUrl;
	}

	/**
	 * 统计展示信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/showad", produces = "image/gif")
	@ResponseBody
	public void adshow(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String showType = request.getParameter("showType");
		String impId = request.getParameter("impId");// 唯一标识曝光id
		String ref = request.getParameter("ref");// 广告曝光的页面
		String clientIp = RequestUtil.getClientIp(request);
		String ua = RequestUtil.getUserAgent(request);
		String adId = request.getParameter("adId");
		String area = request.getParameter("area");
		String userid = JavaMd5Util.md5Encryp(clientIp, ua);
		boolean b = dyAdService.isNeedExecStatCode(userid, impId, adId, ref);
		if (b) {
			olm.adshowRecord(showType, area);
		}
		// 往客户端一个个 很小的图片。。。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control",
				"no-cache, max-age=0, must-revalidate");
		response.setDateHeader("Expires", 0);
		response.setHeader(
				"P3P",
				"CP=CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR");
		response.setContentType("image/gif");
		BufferedImage image = new BufferedImage(1, 1,
				BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "gif", baos);
		byte[] imgby = baos.toByteArray();

		OutputStream os = response.getOutputStream();
		os.write(imgby);
		os.flush();
		os.close();
	}

	/**
	 * 设置过滤器属性
	 * 
	 * @param request
	 * @param adFilterElement
	 * @throws UnsupportedEncodingException 
	 */
	private void assemblyAdFilterElement(HttpServletRequest request,
			AdFilterElement adFilterElement) throws UnsupportedEncodingException {

		// 广告类型(PC,MObile)
		String adType = request.getParameter("adType");

		// 获取前台传送过来的 reffer：首先获取要展示的页面传送过来的 reffer
		String ref = request.getParameter("adforcerReferer");
		// 即搜即投的时候会带上 advId 属性
		// String adId = request.getParameter("advId");
		String adId = request.getParameter("divda");
		// 用户账号信息
		// String adAcct = request.getParameter("radius");
		String adAcct = request.getParameter("rus");
		
		System.out.println("接受到的原始adacct："+adAcct);
		// 类型
		/**
		 * 盲投：0 离线即搜即投：1 定投策略1: 2 定投策略2: 3 即搜即投：4 百度即搜即投：5
		 */
		String type = request.getParameter("ptey");
		String width = request.getParameter("w");
		String height = request.getParameter("h");
		String excludeAdId = request.getParameter("excludeAdId");
		adFilterElement.getExcludeAdIds().add(excludeAdId);
		// 获取用户的 ip
		String ip = RequestUtil.getClientIp(request);
		// 获取用户的浏览器信息
		String userAgent = RequestUtil.getUserAgent(request);
		// 如果 ref 不存在则使用真正的 request 里面的reffer
		String referer = RequestUtil.getRefererUrl(request);
		String tfUrl = "";

		if (StringUtils.isNotBlank(ref)) {
			tfUrl = ref;
		} else {
			tfUrl = referer;
		}
		String host = "";
		try {
			URL url = new URL(tfUrl);
			host = url.getHost();
		} catch (Exception e) {
			Log.warn("背景url为空，或者解析host 出错！！");
			;
		}

		adFilterElement.setType(type);
		adFilterElement.setAdAcct(URLDecoder.decode(adAcct, "utf-8"));
		adFilterElement.setAdId(adId);
		adFilterElement.setHost(host);
		adFilterElement.setRef(tfUrl);
		adFilterElement.setUserAgent(userAgent);
		adFilterElement.setIp(ip);
		adFilterElement.setRegion(IpSearch.getAddrByIp(ip));
		adFilterElement.setWidth(width);
		adFilterElement.setHeight(height);
		adFilterElement.setAdType(adType);
	}

	public static void main(String[] args) {
		DateUtil.getNextDayBeginDateTimeStr();

	}
}
