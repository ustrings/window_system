package com.hidata.ad.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.ad.web.dao.AdImpressLogDao;
import com.hidata.ad.web.dto.AdCrowdBaseInfoDto;
import com.hidata.ad.web.dto.AdImpressLogDto;
import com.hidata.ad.web.dto.DatePressDto;
import com.hidata.ad.web.service.AdImpressLogService;
import com.hidata.framework.util.StringUtil;

/**
 * 广告计划维护服务 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月13日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author chenjinzhao
 */
@Component
public class AdImpressLogServiceImpl implements AdImpressLogService {

	@Autowired
	private AdImpressLogDao adImpressLogDao;
	
	/**
	 * 查询曝光记录
	 * @param adImpressLogDto
	 * @return
	 */
	@Override
	public List<AdImpressLogDto> findAdImpressLogListByObj(
			AdImpressLogDto adImpressLogDto){
		return adImpressLogDao.findAdImpressLogListByObj(adImpressLogDto);
	}
	
	@Override
	public List<AdCrowdBaseInfoDto> findAdCrowdBaseInfoDtoList(AdCrowdBaseInfoDto adCrowdBaseInfoDto,Integer limit,String orderby){
		return adImpressLogDao.findAdCrowdBaseInfoDtoList(adCrowdBaseInfoDto, limit, orderby);
	}

	@Override
	public long getTotalExposureByAdId(String adId) {
		return adImpressLogDao.getTotalExposureByAdId(adId);
	}

	@Override
	public List<DatePressDto> getDatePressList(String adId) {
		return adImpressLogDao.adImpressLogDao(adId);
	}

	@Override
	public List<AdImpressLogDto> findAdImpressLogListByAdAcct(
			AdImpressLogDto adImpressLogDto) {
		
		
		//如果开始，结束时间为空，则默认填上，开始时间(2010年)，结束时间(2050年)，这样能用上索引
		if(StringUtil.isBlank(adImpressLogDto.getStartTime())){
			adImpressLogDto.setStartTime("2010-01-01");
		}
		
		if(StringUtil.isBlank(adImpressLogDto.getEndTime())){
			adImpressLogDto.setEndTime("2050-01-01");
		}
		return adImpressLogDao.findAdImpressLogListByAdAcct(adImpressLogDto);
	}
}
	
