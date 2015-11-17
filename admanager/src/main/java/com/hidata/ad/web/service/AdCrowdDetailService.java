package com.hidata.ad.web.service;

import java.util.List;

import com.hidata.ad.web.dto.AdCrowdBaseInfoDto;
import com.hidata.ad.web.dto.AdCrowdDetailDto;
import com.hidata.ad.web.model.Crowd;


/**
 * 广告人群明细接口 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月13日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
public interface AdCrowdDetailService {

	public List<Crowd> getAdCrowdsByAdId(String adId);

	public List<AdCrowdDetailDto> queryAdCrowdDetailListByCondition(
			AdCrowdDetailDto adCrowdDetailDto, List<Crowd> crowdList,Integer limit, String orderby);

	public List<AdCrowdBaseInfoDto> queryAdCrowdBaseInfoDtoListByCrowId(
			AdCrowdBaseInfoDto adCrowdBaseInfoDto, Integer limit, String orderby);

	/**
	 * 查询人群明细
	 * @param adCrowdBaseInfoDto
	 * @param limit
	 * @param orderby
	 * @return
	 */
	public List<AdCrowdBaseInfoDto> queryAdCrowdBaseInfoDtoListByCondition(
			AdCrowdBaseInfoDto adCrowdBaseInfoDto, Integer limit, String orderby);

	/**
	 * 根据用户id获取人群
	 * @param userId
	 * @return
	 */
	public List<Crowd> getCrowdByUserId(Integer userId);
	
}
