package com.vaolan.ckserver.util;



import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.hidata.framework.util.DateUtil;
import com.hidata.framework.util.http.RequestUtil;
import com.vaolan.ckserver.model.BidResult;
import com.vaolan.ckserver.model.CookieUser;
import com.vaolan.ckserver.model.ImpressInfo;

/**
 * 记录广告曝光，点击，cookie信息的工具类，写到磁盘
 * 
 * @author chenjinzhao
 * 
 */
public class AdStatLogUtil {
	
	public static String LOG_SEPARATOR = "\t";
	private static Logger cookieUserlogger = Logger.getLogger("cookieUser");
	private static Logger impressLogLogger = Logger.getLogger("impressLog");
	
	//private static Logger bidRes = Logger.getLogger("bidRes");

	private static Logger clickLogLogger = Logger.getLogger("clickLog");
	
	//重定向人群日志
	//private static Logger rtCrowdLogger = Logger.getLogger("retargetCrowd");
	
	//记录cookiemapping所有信息
	//private static Logger ckLogger = Logger.getLogger("cookieMapping");
	
	//记录人群信息
	//private static Logger crowdInfoLogger = Logger.getLogger("crowdInfoRefresher");
	
	
	private static Logger logger = Logger.getLogger(AdStatLogUtil.class);

	/**
	 * 保存竞价反馈结果
	 * */
	
	/**
	public static void saveBidRes(BidResult bidResult){
		StringBuilder sb = new StringBuilder();
		sb.append(bidResult.getBid()).append(LOG_SEPARATOR)
		.append(bidResult.getEncryption()).append(LOG_SEPARATOR)
		.append(bidResult.getResultPrice()).append(LOG_SEPARATOR)
		.append(bidResult.getCrcVerify());
		bidRes.info(sb.toString());
	}**/
	
	public static void saveCookieUser(CookieUser cookieUser) {
		StringBuffer sblog = new StringBuffer("");
		sblog.append(cookieUser.getCookieId());
		sblog.append("\t");

		sblog.append(cookieUser.getHostName());
		sblog.append("\t");

		sblog.append(cookieUser.getIpAddress());
		sblog.append("\t");

		sblog.append(cookieUser.getRequestProtocal());
		sblog.append("\t");

		sblog.append(cookieUser.getUserAgent());
		sblog.append("\t");

		sblog.append(cookieUser.getCreateTime());

		cookieUserlogger.info(sblog.toString());
	}

	public static void saveImpressLog(ImpressInfo impressLog) {
		StringBuffer sblog = new StringBuffer("");

		
		sblog.append(impressLog.getAdId());
		sblog.append("\t");
		
		sblog.append(impressLog.getMaterialId());
		sblog.append("\t");

		sblog.append(impressLog.getImpressUrl());
		sblog.append("\t");

		sblog.append(impressLog.getCreateTime());
		sblog.append("\t");

		sblog.append(impressLog.getUid());
		sblog.append("\t");
		
		sblog.append(impressLog.getSrcIP());
		sblog.append("\t");
		
		sblog.append(impressLog.getChannel());
		sblog.append("\t");

		sblog.append(impressLog.getAdAcct());
		sblog.append("\t");
		
		sblog.append(impressLog.getImpuuid());
		sblog.append("\t");
		
		sblog.append(impressLog.getCrowdIds());
		sblog.append("\t");
		
		sblog.append(impressLog.getUserAgent());
		
		

		impressLogLogger.info(sblog.toString());
	}

	public static void saveClickLog(ImpressInfo impressLog) {
		StringBuffer sblog = new StringBuffer("");

		sblog.append(impressLog.getAdId());
		sblog.append("\t");

		sblog.append(impressLog.getMaterialId());
		sblog.append("\t");

		sblog.append(impressLog.getImpressUrl());
		sblog.append("\t");

		sblog.append(impressLog.getCreateTime());
		sblog.append("\t");

		sblog.append(impressLog.getUid());
		sblog.append("\t");

		sblog.append(impressLog.getSrcIP());
		sblog.append("\t");
		
		sblog.append(impressLog.getChannel());
		sblog.append("\t");

		sblog.append(impressLog.getAdAcct());
		sblog.append("\t");
		
		sblog.append(impressLog.getImpuuid());
		sblog.append("\t");
		
		sblog.append(impressLog.getCrowdIds());
		sblog.append("\t");
		
		sblog.append(impressLog.getUserAgent());
		
		
		
		clickLogLogger.info(sblog.toString());

	}
	
	/**
	 * 记录重定向人群日志
	 * @param request
	 */
    /**
	public static void saveRtCrowd(HttpServletRequest request) {
		StringBuffer sblog = new StringBuffer("");

		sblog.append(request.getParameter(Config.getProperty("tanx_cookie_name")));
		sblog.append("\t");
		
		sblog.append(request.getParameter("vl_type"));
		sblog.append("\t");
		
		sblog.append(request.getParameter("vl_vcid"));
		sblog.append("\t");

		sblog.append(DateUtil.getCurrentDateTimeStr());
		sblog.append("\t");

		sblog.append(request.getRemoteHost());
		sblog.append("\t");

		sblog.append(RequestUtil.getClientIp(request));
		sblog.append("\t");

		sblog.append(RequestUtil.getUserAgent(request));
		sblog.append("\t");
		
		sblog.append(request.getProtocol());
		
		rtCrowdLogger.info(sblog.toString());
	}
	**/
	
	/**
	 * 记录cookie mapping的相关信息
	 * @param request
	 */
	
	/**
	public static void saveCkMapping(HttpServletRequest request, String vdspCid) {
		StringBuffer sblog = new StringBuffer("");

		sblog.append(request.getParameter(Config.getProperty("tanx_cookie_name")));
		sblog.append("\t");
		
		sblog.append(vdspCid);
		sblog.append("\t");
		
		sblog.append(DateUtil.getCurrentDateTimeStr());
		sblog.append("\t");

		sblog.append(request.getRemoteHost());
		sblog.append("\t");

		sblog.append(RequestUtil.getClientIp(request));
		sblog.append("\t");

		sblog.append(RequestUtil.getUserAgent(request));
		sblog.append("\t");
		
		sblog.append(request.getProtocol());
		
		ckLogger.info(sblog.toString());
	}
	*/
	
	/**
	 * 记录cookie mapping的相关信息
	 * @param request
	 */
	
	/**
	public static void saveCrowdInfo(HttpServletRequest request, String vdspCid) {
		StringBuffer sblog = new StringBuffer("");

		sblog.append(vdspCid);
		sblog.append("\t");
		
		sblog.append(request.getParameter("vl_vcid"));
		sblog.append("\t");
		
		sblog.append(DateUtil.getCurrentDateTimeStr());
		sblog.append("\t");

		sblog.append(request.getRemoteHost());
		sblog.append("\t");

		sblog.append(RequestUtil.getClientIp(request));
		sblog.append("\t");

		sblog.append(RequestUtil.getUserAgent(request));
		sblog.append("\t");
		
		sblog.append(request.getProtocol());
		
		crowdInfoLogger.info(sblog.toString());
	} **/
	
	public static void main(String[] args){
		cookieUserlogger.info("哈哈哈哈:cookieUser");
		impressLogLogger.info("哈哈哈哈:impressLog");
		clickLogLogger.info("哈哈哈哈:clickLog");
		logger.info("阿拉啦啦啦啦啦");
	}

}
