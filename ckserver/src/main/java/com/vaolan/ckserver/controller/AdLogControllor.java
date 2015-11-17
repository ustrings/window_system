package com.vaolan.ckserver.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
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
import com.vaolan.ckserver.model.CookieUser;
import com.vaolan.ckserver.model.ImpressInfo;
import com.vaolan.ckserver.server.AdLogService;
import com.vaolan.ckserver.util.GenCookieUserUtil;

/**
 * 
 * 
 * @author chenjinzhao
 * 
 */
@Controller
public class AdLogControllor {

	private static Logger logger = Logger.getLogger(AdLogControllor.class);

	@Autowired
	private AdLogService adLogServer;

	/**
	 * 收集客户端信息
	 * 
	 * @return
	 */
	// 广告展现需要统计的信息
	@RequestMapping(value = "/statpv", produces = "image/gif")
	@ResponseBody
	public void pvstat(HttpServletRequest request, HttpServletResponse response) {
		/**
		 * 返回一个空1x1像素的gif图片
		 */

		/**
		 * 异常请求，直接过滤
		 */
		String exceptionUa = "Mozilla/4.0"; //表示 不正常的UA , 可能是爬虫等造成的
		String ua = RequestUtil.getUserAgent(request);
		if (exceptionUa.equals(ua)) {
			return;
		}

		try {

			String adId = request.getParameter("adId"); // 广告ID
			String materialId = request.getParameter("materialId"); // 物料ID
			String referrer = request.getParameter("referrer"); // url来源

			String bid = request.getParameter("bid"); // 广告ID


			String impuuid = request.getParameter("impuuid");

			if (null == bid) {
				bid = "";
			}

			/*
			String channel = request.getParameter("channel"); // 频道
			String domain = request.getParameter("domain"); // 域名
			String size = request.getParameter("size"); // 尺寸
			String viewscreen = request.getParameter("viewscreen");
            */
			
			String userId = request.getParameter("userId");// 用户标识
			String userIdType = request.getParameter("userIdType"); // 帐号类型，1：tanx tid，2，ad帐号  3，md5(ad)16位+ md5(ua)16位

			if (StringUtils.isBlank(adId)) {
				logger.warn("收集pv信息，adId 或者 materialId 为空: adId=" + adId
						+ ",materialId=" + materialId);
			} else {
				CookieUser cookieUser = new CookieUser();
				boolean isNewCookie = GenCookieUserUtil.assembleCookieUser(
						cookieUser, request, response);

				ImpressInfo impressInfo = new ImpressInfo();
				impressInfo.setAdId(adId);
				impressInfo.setMaterialId(materialId);
				impressInfo.setCreateTime(cookieUser.getCreateTime());
				impressInfo.setImpressUrl(referrer);
				impressInfo.setSrcIP(cookieUser.getIpAddress());
				impressInfo.setUid(cookieUser.getCookieId());
				impressInfo.setChannel("");
				impressInfo.setDomain("");
				impressInfo.setSize("");
				impressInfo.setViewscreen("");
				impressInfo.setAdAcct(userId);
				impressInfo.setBid(bid);
				impressInfo.setImpuuid(impuuid);
				impressInfo.setUserAgent(cookieUser.getUserAgent());
				// pv信息保存
				
				
				adLogServer.savePvInfo(impressInfo, cookieUser, isNewCookie);
			}
			// 往客户端一个个 很小的图片。。。
			this.returnGif(response);
		} catch (Exception e) {
			logger.error("记录PV信息出错:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 广告被点击所统计的信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/statclick", produces = "image/gif")
	@ResponseBody
	public void clickstat(HttpServletRequest request,
			HttpServletResponse response) {
		/**
		 * 返回一个空1x1像素的gif图片
		 */

		/**
		 * 异常请求，直接过滤
		 */
		String exceptionUa = "Mozilla/4.0";
		String ua = RequestUtil.getUserAgent(request);
		if (exceptionUa.equals(ua)) {
			return;
		}

		try {

			String adId = request.getParameter("adId");
			String materialId = request.getParameter("materialId");
			String referrer = request.getParameter("referrer");

			String bid = request.getParameter("bid");

			String impuuid = request.getParameter("impuuid");

			String pos = request.getParameter("pos");

			// 点击坐标，如果点击坐标为空，说明是非人类点击，就不记录数量
			if (StringUtils.isBlank(pos)) {
				return;
			}

			if (null == bid) {
				bid = "";
			}

			/*
			String channel = request.getParameter("channel");
			String domain = request.getParameter("domain");
			String size = request.getParameter("size");
			String viewscreen = request.getParameter("viewscreen");
			*/
			
			
			String userId = request.getParameter("userId");// 用户标识

			String userIdType = request.getParameter("userIdType"); // 帐号类型，1：tanx tid，2，ad帐号  3，md5(ad)16位+ md5(ua)16位

			if (StringUtils.isBlank(adId)) {
				logger.warn("收集click信息，adId 或者 materialId 为空: adId=" + adId
						+ ",materialId=" + materialId);
			} else {
				CookieUser cookieUser = new CookieUser();
				GenCookieUserUtil.assembleCookieUser(cookieUser, request,
						response);

				ImpressInfo impressInfo = new ImpressInfo();
				impressInfo.setAdId(adId);
				impressInfo.setMaterialId(materialId);
				impressInfo.setCreateTime(cookieUser.getCreateTime());
				impressInfo.setImpressUrl(referrer);
				impressInfo.setSrcIP(cookieUser.getIpAddress());
				impressInfo.setUid(cookieUser.getCookieId());
				impressInfo.setChannel("");
				impressInfo.setDomain("");
				impressInfo.setSize("");
				impressInfo.setViewscreen("");
				impressInfo.setAdAcct(userId);
				impressInfo.setBid(bid);
				impressInfo.setImpuuid(impuuid);
				impressInfo.setUserAgent(cookieUser.getUserAgent());
				
				adLogServer.saveClickInfo(impressInfo, cookieUser);
			}
			this.returnGif(response);
		} catch (Exception e) {
			logger.error("记录Click信息出错:" + e.getMessage(), e);
		}
	}
	
	
	/**
	 * 关闭按钮被点击所统计的 信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/statclose", produces = "image/gif")
	@ResponseBody
	public void closestat(HttpServletRequest request,HttpServletResponse response) {

		/**
		 * 异常请求，直接过滤
		 */
		String exceptionUa = "Mozilla/4.0";
		String ua = RequestUtil.getUserAgent(request);
		if (exceptionUa.equals(ua)) {
			return;
		}

		try {

			String adId = request.getParameter("adId");
			String materialId = request.getParameter("materialId");
			String referrer = request.getParameter("referrer");
			
			String bid = request.getParameter("bid");
			String impuuid = request.getParameter("impuuid");
			
			
//			String pos = request.getParameter("pos");

			// 点击坐标，如果点击坐标为空，说明是非人类点击，就不记录数量
//			if (StringUtils.isBlank(pos)) {
//				return;
//			}

			if (null == bid) {
				bid = "";
			}

			/*
			String channel = request.getParameter("channel");
			String domain = request.getParameter("domain");
			String size = request.getParameter("size");
			String viewscreen = request.getParameter("viewscreen");
			*/
			
			String userId = request.getParameter("userId");// 用户标识

			String userIdType = request.getParameter("userIdType"); // 帐号类型，1：tanx tid，2，ad帐号  3，md5(ad)16位+ md5(ua)16位

			if (StringUtils.isBlank(adId)) {
				logger.warn("收集click信息，adId 或者 materialId 为空: adId=" + adId
						+ ",materialId=" + materialId);
			} else {
				CookieUser cookieUser = new CookieUser();
				GenCookieUserUtil.assembleCookieUser(cookieUser, request,
						response);

				ImpressInfo impressInfo = new ImpressInfo();
				impressInfo.setAdId(adId);
				impressInfo.setMaterialId(materialId);
				impressInfo.setCreateTime(cookieUser.getCreateTime());
				impressInfo.setImpressUrl(referrer);
				impressInfo.setSrcIP(cookieUser.getIpAddress());
				impressInfo.setUid(cookieUser.getCookieId());
				impressInfo.setChannel("");
				impressInfo.setDomain("");
				impressInfo.setSize("");
				impressInfo.setViewscreen("");
				impressInfo.setAdAcct(userId);
				impressInfo.setBid(bid);
				impressInfo.setImpuuid(impuuid);
				impressInfo.setUserAgent(cookieUser.getUserAgent());
				
				//adLogServer.saveClickInfo(impressInfo, cookieUser);
				adLogServer.saveCloseClickInfo(impressInfo);
			}
			this.returnGif(response);
		} catch (Exception e) {
			logger.error("记录点击关闭按钮量信息出错:" + e.getMessage(), e);
		}
	}
	
	
	
	/**
	 * 统计完数据后,返回一个空的1x1的像素图片
	 * @author xiaoming
	 */
	private void returnGif(HttpServletResponse response){
		try {
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
		} catch (IOException e) {
			logger.error("在返回客户端空的小图片时出错。",e);
		}
	}
	public static void main(String[] args) {
		String dd = SelfBase64Test
				.getAd("Y1QOMBoLYZSGdBFpMBmlMBsGM1mvMZM=M3gO");
		System.out.println(dd);
	}
}
