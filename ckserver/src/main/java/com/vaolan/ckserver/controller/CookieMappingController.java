package com.vaolan.ckserver.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.Jedis;

import com.hidata.framework.util.http.RequestUtil;
import com.vaolan.ckserver.model.CookieMapModel;
import com.vaolan.ckserver.model.CookieUser;
import com.vaolan.ckserver.server.CookieMappingService;
import com.vaolan.ckserver.util.Config;
import com.vaolan.ckserver.util.Constant;
import com.vaolan.ckserver.util.GenCookieUserUtil;

@Controller
public class CookieMappingController {

	private static Logger logger = Logger
			.getLogger(CookieMappingController.class);

	@Autowired
	private CookieMappingService cookieMappingServer;

	// @RequestMapping(value = "/cookiemapping", produces = "image/gif")
	@ResponseBody
	@Deprecated
	public void ckm(HttpServletRequest request, HttpServletResponse response,
			Model model) throws IOException {

		/**
		 * 得到的Tanx的cookie
		 */
		String adxCid = request.getParameter(Config
				.getProperty("tanx_cookie_name"));

		if (StringUtils.isBlank(adxCid)) {
			logger.warn("adxcookie获取失败，失败原因: 获取adx的cookie为空");
			return;
		}

		if (Constant.ADEXCHANGE_TANX_COOKIEMPPING_ERROR.equals(adxCid)) {
			logger.warn("adxcookie获取失败，失败原因: 获取adx的cookie为E0，tanx内部错误");
			return;
		}

		adxCid = URLDecoder.decode(adxCid, "utf-8");
		CookieUser cookieUser = new CookieUser();
		boolean isNewCookie = GenCookieUserUtil.assembleCookieUser(cookieUser,
				request, response);
		logger.info("cookiemapping for tanx: tanxId:" + adxCid
				+ "----zhaolanId:" + cookieUser.getCookieId());

		/**
		 * 记录cookiemapping
		 */
		CookieMapModel cookieMapModel = new CookieMapModel();
		cookieMapModel.setAdxCid(adxCid);
		cookieMapModel.setVdspCid(cookieUser.getCookieId());
		cookieMapModel.setCreateTime(cookieUser.getCreateTime());
		cookieMapModel.setAdxVendor(Constant.ADEXCHANGE_TANX);
		cookieMapModel.setSts(Constant.COOKIEMAPPING_STS_A);

		/**
		 * 保存cookie以及 cookiemapping信息
		 */
		cookieMappingServer.saveCookieAndMappingInfo(cookieMapModel,
				cookieUser, isNewCookie);

		/**
		 * 返回一个空1x1像素的gif图片
		 */
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

	@RequestMapping(value = "/cookiemapping", produces = "image/gif")
	@ResponseBody
	public void cookieMapping(HttpServletRequest request,
			HttpServletResponse response, Model model) throws IOException {

		/**
		 * 保存cookie以及 cookiemapping信息
		 */
		
		
		/**
		 * 异常请求，直接过滤
		 */
		String exceptionUa ="Mozilla/4.0";
		String ua = RequestUtil.getUserAgent(request);
		if(exceptionUa.equals(ua)){
			return ;
		}
		
		boolean result = false;
		try {
			//目前用不着ck
		   //result = cookieMappingServer.cookieMapping(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存cookiemapping信息出错:"+e.getMessage(),e);
		}

		if (result) {
			/**
			 * 返回一个空1x1像素的gif图片
			 */
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
	}

	@RequestMapping(value = "/viewck")
	public String viewck(HttpServletRequest request,
			HttpServletResponse response, Model model) throws IOException {

		return "ck";

	}

	@RequestMapping(value = "/")
	public void index(HttpServletRequest request, HttpServletResponse response,
			Model model) throws IOException {

		logger.info("我启动了啊");

	}
	
	public static void main(String[] args) {
		Jedis jedis = new Jedis("114.111.162.219");
		
		Set<String> keys = jedis.keys("*305*");
		
		for(String key : keys){
			System.out.println(key);
		}
	}

}
