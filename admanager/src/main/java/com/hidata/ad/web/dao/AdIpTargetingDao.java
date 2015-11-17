package com.hidata.ad.web.dao;

import java.util.List;

import com.hidata.ad.web.model.AdIpTargeting;


public interface AdIpTargetingDao {

	/**
	 * 添加ip定向
	 * @param adIpTargeting
	 * @return
	 * @throws Exception
	 */
	public int addAdIpTargeting(AdIpTargeting adIpTargeting) throws Exception;

	/**
	 * 删除ip定向
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int delAdIpTargetingByAdId(Integer adId) throws Exception;

	/**
	 * 查询ip定向list
	 * @param adId
	 * @return
	 */
	public List<AdIpTargeting> queryAdIpTargetingListByAdId(Integer adId);
	

}
