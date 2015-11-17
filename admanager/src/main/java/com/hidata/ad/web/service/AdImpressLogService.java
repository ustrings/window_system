package com.hidata.ad.web.service;

import java.util.List;

import com.hidata.ad.web.dto.AdCrowdBaseInfoDto;
import com.hidata.ad.web.dto.AdImpressLogDto;
import com.hidata.ad.web.dto.DatePressDto;


/**
 * 广告计划管理服务接口 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月13日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
public interface AdImpressLogService {

	
	public List<AdImpressLogDto> findAdImpressLogListByObj(
			AdImpressLogDto adImpressLogDto);
	
	
	public List<AdImpressLogDto> findAdImpressLogListByAdAcct(
			AdImpressLogDto adImpressLogDto);

	public List<AdCrowdBaseInfoDto> findAdCrowdBaseInfoDtoList(
			AdCrowdBaseInfoDto adCrowdBaseInfoDto, Integer limit, String orderby);
	
	public long getTotalExposureByAdId(String adId);
	
	public List<DatePressDto> getDatePressList(String adId);

}
