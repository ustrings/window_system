package com.vaolan.ckserver.server;

import com.vaolan.ckserver.model.CookieUser;
import com.vaolan.ckserver.model.ImpressInfo;



public interface AdLogService {

    
	/**
	 * 保存广告的pv信息
	 * @param cookieUser
	 */
	public abstract void savePvInfo(ImpressInfo impressInfo,CookieUser cookieUser,boolean isNewCookie);
	
	/**
	 * 保存广告的点击信息
	 * @throws Exception 
	 */
	public abstract void saveClickInfo(ImpressInfo impressInfo,CookieUser cookieUser);
	
	
	/**
	 * 保存点击关闭按钮信息
	 * @param impressInfo
	 * @param cookieUser
	 */
	public abstract void saveCloseClickInfo(ImpressInfo impressInfo);
	
	


}