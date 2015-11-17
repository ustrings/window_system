package com.vaolan.sspserver.dao;

import com.vaolan.sspserver.model.AdUserFrequency;


public interface AdUserFrequencyDao {

	/**
	 * 查询adId的投放时间
	 * @param adId
	 * @return List<AdTimeFilter>
	 * */
	public abstract AdUserFrequency getUserFrequencyByAdId(String adId);

}