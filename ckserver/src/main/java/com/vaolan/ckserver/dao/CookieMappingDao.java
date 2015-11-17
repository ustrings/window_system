package com.vaolan.ckserver.dao;

import java.util.List;

import com.vaolan.ckserver.model.CookieMapModel;
import com.vaolan.ckserver.model.CookieUser;


public interface CookieMappingDao {


	/**
	 * 保存cookie信息
	 * @param cookie
	 * @return
	 */
	public abstract void saveCookieInfo(CookieUser cookieUser);
	
	
	/**
	 * 保存cookiemapping信息
	 * @param cookieMapModel
	 */
	public abstract void saveCookieMappingInfo(CookieMapModel cookieMapModel);
	
	/**
	 * 通过adx的cookie去cookiemaping表里查询 vdsp(就是诏兰dsp)的cookie
	 * @param adxCid
	 * @return
	 */
	public abstract List<CookieMapModel> getVdspCookieInfobyAdxCid(CookieMapModel cookieMapModel);
	
	
	/**
	 * 通过vdsp的cookie去cookiemapping表里查询，adxd的cookie
	 * @param vdspCid
	 * @return
	 */
	public abstract List<CookieMapModel> getAdxCookieInfobyVdspCid(CookieMapModel cookieMapModel);
	
	
	public abstract void updateCookieMappingRelSts(CookieMapModel cookieMapModel);
	

}