package com.vaolan.sspserver.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.vaolan.sspserver.model.AdFilterElement;
import com.vaolan.sspserver.model.AdShowPara;
import com.vaolan.sspserver.model.VarSizeBean;

public interface AdPlanService {
	
	
	/**
	 * 广告检索，
	 * @param adFilterElement
	 * @return 广告代码
	 */
	public AdShowPara adRetrieveForVaolanPush(AdFilterElement adFilterElement, HttpServletRequest request,
			HttpServletResponse response);
	
	
	public String adRetrieveForVaolanPushWH(AdFilterElement adFilterElement,HttpServletRequest request,
			HttpServletResponse response);
	

	
	public String adRetrieveForQingKooVideo(AdFilterElement adFilterElement,String template,VarSizeBean vsb);
	
	public String adRetrieveForShProtal(AdFilterElement adFilterElement);
	
	public String adRetrieveForQingKooVideoDemo(AdFilterElement adFilterElement,String template);
	
	
	public String getAdTargetUrl(String adId);

	
}
