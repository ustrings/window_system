package com.vaolan.sspserver.service;

import com.vaolan.sspserver.model.AdFilterElement;
import com.vaolan.sspserver.model.AdvPlan;

public interface DyAdService {

	/**
	 * 根据广告尺寸，动态检索广告，随机选择一只满足广告尺寸的广告
	 * 
	 * @param width
	 * @param height
	 * @return
	 */
	public AdvPlan dyAdRetrieveForShPortal(String width, String height,AdFilterElement adFilterElement);

	/**
	 * 生成展现的 UUID
	 * @param clientIp
	 * @param url
	 * @param referrer
	 * @param userAgent
	 * @return
	 */
	public String genImpressionUUID(String clientIp, String url,
			String referrer, String userAgent);
	/**
	 * 判断当前的访问是不是有效访问
	 * @param cookieValue
	 * @param impuuid
	 * @param adId
	 * @param referrer
	 * @return
	 */
	public boolean isNeedExecStatCode(String cookieValue ,String impuuid,String adId,String referrer);
	
	/**
	 * 获取页面状态码
	 * @param pageId
	 * @return
	 */
	public String getStatCode(String pageId);
}
