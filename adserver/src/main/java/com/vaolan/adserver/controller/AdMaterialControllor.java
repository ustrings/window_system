package com.vaolan.adserver.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hidata.framework.util.StringUtil;
import com.hidata.framework.util.http.RequestUtil;
import com.ideal.encode.SelfBase64Test;
import com.vaolan.adserver.model.AdMaterialCache;
import com.vaolan.adserver.service.AdMaterialService;
import com.vaolan.adserver.service.CookieUserService;
import com.vaolan.adserver.service.impressLogService;
import com.vaolan.adserver.util.Config;
import com.vaolan.adserver.util.Constant;
import com.vaolan.adserver.util.CookieUtil;
import com.vaolan.adserver.util.JavaMd5Util;

/**
 * 
 * 2013-04-03
 */
@Controller
@RequestMapping("/ad")
public class AdMaterialControllor {

	private static Logger log = Logger.getLogger(AdMaterialControllor.class);
	@Autowired
	private AdMaterialService service;
	@Autowired
	private CookieUserService cookieService;
	@Autowired
	private impressLogService logService;

	/**
	 * 允许爬取页面的时候，带统计代码的白名单ip
	 */
	private Set<String> whithIpsMap = new HashSet<String>();

	public AdMaterialControllor() {
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

	/**
	 * @param request
	 * @param corderid
	 * @param model
	 * @return
	 */
	@RequestMapping("/{ad_acctid}/{ad_id}")
	public String index(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String ad_id,
			@PathVariable String ad_acctid, Model model) {
		

		String pk = request.getParameter("pk");

		String w=request.getParameter("w");
		String h = request.getParameter("h");
		String d = request.getParameter("d");

		try {

			// boolean isNum = ad_id.matches("[0-9]+");
			
			
			String adsize= "";
			if(StringUtils.isNotBlank(w) || StringUtils.isNotBlank(h)){
				adsize = w+"x"+h;
			}
			
			// 用于处理弹窗时无法获取referer的情况
			String adforcerReferer = request.getParameter("adforcerReferer");
			adforcerReferer = (StringUtil.isEmpty(adforcerReferer) || adforcerReferer
					.equals("null")) ? Constant.BLANK : adforcerReferer;

			String type = request.getParameter("type"); // 以此参数来做特殊处理的判断
			// 判断是否收集客户端信息，如果是广告主自己浏览，不收集客户端信息
			String isCollect = Constant.USER_TYPE_VIEW.equals(type) ? Constant.COLLECT_USER_INFOR_N
					: Constant.COLLECT_USER_INFOR_Y;
			// 根据广告id获取物料集合
			if (StringUtils.isNotBlank(ad_id)) {
				List<AdMaterialCache> list = null;
				AdMaterialCache material = null;

				list = service.findMaterialByAdId(ad_id);
				if (null != list && list.size() > 0) {
					
					if(StringUtils.isBlank(adsize)){
						Random r = new Random();
						material = list.get(r.nextInt(list.size()));
					}else{
						for(AdMaterialCache ac :list){
							String width = ac.getWidth();
							String height = ac.getHeight();
							
							String msize = width+"x"+height;
							if(adsize.equals(msize)){
								material = ac;
							}
						}
					}
					
					
					
				} else {
					
					material = new AdMaterialCache();
					
					material.setWidth("0");
					material.setHeight("0");
					material.setM_type("3");
				}

				String clientIp = RequestUtil.getClientIp(request);

				String userAgent = RequestUtil.getUserAgent(request);

				String referrer = RequestUtil.getRefererUrl(request);

				StringBuffer url = request.getRequestURL();

				// 加密 ad 帐号
				String _us = request.getParameter("_us");
				
				String ad_acct = request.getParameter("ad_acct");

				String userId = "";
				String userIdType = "";

				if (StringUtils.isNotBlank(_us)) {

					try {
						String ad_acct_src = SelfBase64Test.getAd(_us);
						userId = ad_acct_src;
						userIdType = "2";
					} catch (Exception e) {
						log.error("电信回传帐号解析出错：_us=" + _us);
					}

				} else if (StringUtils.isNotBlank(ad_acct)) {

					userId = JavaMd5Util.md5EncrypFirst16(ad_acct)
							+ JavaMd5Util.md5EncrypFirst16(userAgent);
					userId = userId.toLowerCase();
					userIdType = "3";
				}

				String jsVersion = service.getJsVersion();

				String impuuid = service.genImpressionUUID(clientIp,
						url.toString(), referrer, userAgent);

				String isPutStatCode = "0";
				if (whithIpsMap.contains(clientIp)) {// 白名单IP 中若存在
					isPutStatCode = "1";
				}

				String isHaveNHTStat = "0";

				// 有NHT统计模式
				if (StringUtils.isNotBlank(material.getAd_3stat_code_sub())) {// 判断是否有NHT请求形式
					isHaveNHTStat = "1";
				}

				model.addAttribute("isHaveNHTStat", isHaveNHTStat);


				model.addAttribute("isPutStatCode", isPutStatCode);

				model.addAttribute("ver", jsVersion);

				model.addAttribute("impuuid", impuuid);
				model.addAttribute("userId", userId);
				model.addAttribute("userIdType", userIdType);


				// 多张图片，随机
				model.addAttribute("material", material);
				model.addAttribute("adId", ad_id);
				model.addAttribute("isCollect", isCollect);
				model.addAttribute("pv_collect_url",
						Config.getProperty("pv_collect_url"));
				model.addAttribute("click_collect_url",
						Config.getProperty("click_collect_url"));

				model.addAttribute("adforcerReferer", adforcerReferer);
				model.addAttribute("d", d);

			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		if(StringUtils.isNotBlank(pk)){
			return "pkPage";
		}else{
			return "adPage";
		}
		
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
		String adId = request.getParameter("adId");
		String isHaveNHTStat = request.getParameter("isHaveNHTStat");
		String referer = request.getParameter("referer");
		String userId = request.getParameter("userId");

		String statCode="";
		
		boolean b = service.isNeedExecStatCode(impuuid);

		if (!b) {
			service.processNHTstat(adId, isHaveNHTStat);
		} else {

			boolean flag = true;
			if (StringUtils.isNotBlank(referer)) {// 若referer为空则保存。

				// 如果ad_acct不为空，则按ad_acct频次控制
				if (StringUtil.isNotBlank(userId)) {

					String key = userId + "_" + adId + "_"
							+ JavaMd5Util.md5Encryp(referer); // cache中的key值

					if (!service.getValueByKey(key)) {
						service.add(key, Constant.FREQUENCY, "1");
					} else {
						flag = false; // 说明在1分钟内同一个pv多次请求
						b = false; // 不在执行统计代码
					}

					// 如果ad_acct为空，则按cookie控制频次
				} else {
					String prequency_cookie = CookieUtil
							.composeDspCookieId(request); // 返回访问频率的cookie信息
					String key = prequency_cookie + "_" + adId + "_"
							+ JavaMd5Util.md5Encryp(referer); // cache中的key值

					if (CookieUtil.isHaveDspCookieId(request)) { // 存在
						// 根据key 看cache中是否含有， 若有则不处理，若没有则处理并保存到cache
						if (!service.getValueByKey(key)) {
							service.add(key, Constant.FREQUENCY, "1");
						} else {
							flag = false; // 说明在1分钟内同一个pv多次请求

							b = false; // 不在执行统计代码
						}

					} else {// 不存在
						Cookie cookie = CookieUtil.getCookie(prequency_cookie);
						response.addCookie(cookie); // 种cookie并 保存cache中
						service.add(key, Constant.FREQUENCY, "1");
					}
				}
			}
			
			/**
			if (flag) {
				Map<String, String> terminal_map = service
						.findValueByAdIdAndType(adId);
				if (terminal_map != null && terminal_map.size() > 0) {
					Map<String, String> map_useragent = service
							.getUserAgentMassage(request);
					String browser = map_useragent.get("browser");
					String system = map_useragent.get("system");
					String device = map_useragent.get("device");
					if (terminal_map.get("3") != null
							&& terminal_map.get("3").length() > 0) {
						String[] browsers = terminal_map.get("3").split(",");
						if (Arrays.asList(browsers).contains(browser)) {
							b = false;
						}
					}
					if (terminal_map.get("1") != null
							&& terminal_map.get("1").length() > 0) {
						String[] devices = terminal_map.get("1").split(",");
						if (Arrays.asList(devices).contains(device)) {
							b = false;
						}
					}
					if (terminal_map.get("2") != null
							&& terminal_map.get("2").length() > 0) {
						String[] systems = terminal_map.get("2").split(",");
						if (Arrays.asList(systems).contains(system)) {
							b = false;
						}
					}
				}
			}**/
		}
		
		
		//如果需要统计，则把统计代码返回
		if(b){
			
			try {
				if (StringUtils.isNotBlank(adId)) {
					List<AdMaterialCache> list = null;
					AdMaterialCache material = null;

					list = service.findMaterialByAdId(adId);
					if (null != list && list.size() > 0) {
						material = list.get(0);
					} else {
						//没有物料的
						return "";
					}

					// getAd_3stat_code_temp 里面存放了从 NHT统计模式 移动过来的统计代码，因为还要移走，
					// 所以临时放入这字段，但是也要参与统计，所以一并发到前段执行
					StringBuffer bf = new StringBuffer("");
					bf.append(material.getAd_3stat_code() == null ? "" : material
							.getAd_3stat_code());
					bf.append(material.getAd_3stat_code_temp() == null ? ""
							: material.getAd_3stat_code_temp());

					statCode =  bf.toString();
				}

			} catch (Exception e) {
				e.printStackTrace();
				log.error("getstatcode  failure", e);
			}
		}
		
		//如果需要统计,则返回统计代码，如果不需要返回-1
		String ret = b ? statCode : "-1";
		
		return ret;
	}
}
