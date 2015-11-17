package com.hidata.ad.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.ad.web.dao.AdCrowdDetailDao;
import com.hidata.ad.web.dao.AdPlanManageDao;
import com.hidata.ad.web.dao.CrowdDao;
import com.hidata.ad.web.dto.AdCrowdBaseInfoDto;
import com.hidata.ad.web.dto.AdCrowdDetailDto;
import com.hidata.ad.web.dto.AdInstanceDto;
import com.hidata.ad.web.model.Crowd;
import com.hidata.ad.web.service.AdCrowdDetailService;

/**
 * 广告计划维护服务 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月13日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author chenjinzhao
 */
@Component
public class AdCrowdDetailServiceImpl implements AdCrowdDetailService {

	@Autowired
	private AdCrowdDetailDao adCrowdDetailDao;
	
	@Autowired
	private AdPlanManageDao adPlanManageDao;
	
	@Autowired
	private CrowdDao crowdDao;
	
	@Override
	public List<Crowd> getCrowdByUserId(Integer userId){
		Crowd crowd = new Crowd();
		crowd.setUserId(String.valueOf(userId));
		return crowdDao.getCrowdList(crowd);
	}
	@Override
	public List<Crowd> getAdCrowdsByAdId(String adId){
		AdInstanceDto adInstanceDto = new AdInstanceDto();
		adInstanceDto.setAdId(adId);
		return adPlanManageDao.getAdCrowdsByAdId(adInstanceDto);
	}
	
	@Override
	public List<AdCrowdDetailDto> queryAdCrowdDetailListByCondition(AdCrowdDetailDto adCrowdDetailDto,List<Crowd> crowdList,Integer limit,String orderby){
		return adCrowdDetailDao.queryAdCrowdDetailListByCondition(adCrowdDetailDto,crowdList, limit, orderby);
	}
	
	@Override
	public List<AdCrowdBaseInfoDto> queryAdCrowdBaseInfoDtoListByCrowId(AdCrowdBaseInfoDto adCrowdBaseInfoDto,Integer limit,String orderby){
		return adCrowdDetailDao.queryAdCrowdBaseInfoDtoListByCrowId(adCrowdBaseInfoDto, limit, orderby);
	}
	
	@Override
	public List<AdCrowdBaseInfoDto> queryAdCrowdBaseInfoDtoListByCondition(AdCrowdBaseInfoDto adCrowdBaseInfoDto,Integer limit,String orderby){
		return adCrowdDetailDao.queryAdCrowdBaseInfoDtoListByCondition(adCrowdBaseInfoDto, limit, orderby);
	}
}
	
