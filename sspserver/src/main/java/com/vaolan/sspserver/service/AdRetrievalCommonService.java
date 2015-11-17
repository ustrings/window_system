package com.vaolan.sspserver.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaolan.sspserver.model.AdFilterElement;
import com.vaolan.sspserver.model.AdProperty;
import com.vaolan.sspserver.model.AdvPlan;
import com.vaolan.sspserver.model.VarSizeBean;

public interface AdRetrievalCommonService {

	 /**
     * 给诏兰自己弹窗的流量选取广告
     */
	public AdProperty adRetrieveCommonForVaolanPush(AdFilterElement adFilterElement,HttpServletRequest request,
			HttpServletResponse response);
	
	/**
	 
	public AdvPlan adRetrieveForSHDX(AdFilterElement adFilterElement, String channel,VarSizeBean vsb);
*/
	

}
