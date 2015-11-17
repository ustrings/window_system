package com.hidata.ad.web.dao;

import java.util.List;

import com.hidata.ad.web.model.CrowdPortrait;


public interface CrowdPortraitDao {

	/**
	 * 插入
	 * @param crowdPortrait
	 * @return
	 * @throws Exception 
	 */
	public int insertObject(CrowdPortrait crowdPortrait) throws Exception;

	public int delCrowdPortraitByCrowdId(String crowdId) throws Exception;

	public List<CrowdPortrait> queryCrowdPortraitListByCodition(
			CrowdPortrait crowdPortrait) throws Exception;


}
