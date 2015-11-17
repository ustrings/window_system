package com.hidata.ad.web.dao;

import java.util.List;

import com.hidata.ad.web.dto.AdCrowdBaseInfoDto;
import com.hidata.ad.web.dto.AdCrowdDetailDto;
import com.hidata.ad.web.model.Crowd;

/**
 *  Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月13日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
public interface AdCrowdDetailDao {

	public List<AdCrowdDetailDto> queryAdCrowdDetailListByCondition(
			AdCrowdDetailDto adCrowdDetailDto,List<Crowd> crowdList, Integer limit, String orderby);

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

	@Deprecated
	public List<AdCrowdBaseInfoDto> queryAdCrowdBaseInfoDtoListByCondition_old(
			AdCrowdBaseInfoDto adCrowdBaseInfoDto, Integer limit, String orderby);

}
