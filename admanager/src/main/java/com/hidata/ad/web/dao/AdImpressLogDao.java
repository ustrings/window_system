package com.hidata.ad.web.dao;

import java.util.List;

import com.hidata.ad.web.dto.AdCrowdBaseInfoDto;
import com.hidata.ad.web.dto.AdImpressLogDto;
import com.hidata.ad.web.dto.DatePressDto;


/**
 * 广告计划维护操作DAO
 * Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月13日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author sunly
 */
public interface AdImpressLogDao {

	/**
	 * 查询曝光记录
	 * @param adImpressLogDto
	 * @return
	 */
	public List<AdImpressLogDto> findAdImpressLogListByObj(
			AdImpressLogDto adImpressLogDto);
	
	

	/**
	 * 查询历史浏览轨迹
	 * @param adCrowdBaseInfoDto
	 * @param limit
	 * @param orderby
	 * @return
	 */
	public List<AdCrowdBaseInfoDto> findAdCrowdBaseInfoDtoList(
			AdCrowdBaseInfoDto adCrowdBaseInfoDto, Integer limit, String orderby);

	/**
	 * 查询历史浏览轨迹
	 * @param adCrowdBaseInfoDto
	 * @param limit
	 * @param orderby
	 * @return
	 */
	@Deprecated
	public List<AdCrowdBaseInfoDto> findAdCrowdBaseInfoDtoList_old(
			AdCrowdBaseInfoDto adCrowdBaseInfoDto, Integer limit, String orderby);

	public long getTotalExposureByAdId(String adId);

	public List<DatePressDto> adImpressLogDao(String adId);


	public List<AdImpressLogDto> findAdImpressLogListByAdAcct(
			AdImpressLogDto adImpressLogDto);
	
	
}
