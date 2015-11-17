package com.vaolan.sspserver.dao;

import com.vaolan.sspserver.model.AdTimeFrequency;



public interface AdTimeFrequencyDao {

	/**
	 * 查询广告计划频度控制
	 * @param adId
	 * @return List<adTimeFrequency>
	 * */
	public abstract AdTimeFrequency getFrequencyByAdId(String adId);

}