package com.vaolan.ckserver.dao;

import java.util.List;

import com.vaolan.ckserver.model.AdCrowdLink;


public interface AdCrowdLinkDao {

	/**
	 * 查询广告对应的人群
	 * @return
	 * @throws Exception
	 */
	public List<AdCrowdLink> queryAdCrowdLinkByAdId(String  adId) throws Exception;

	
}