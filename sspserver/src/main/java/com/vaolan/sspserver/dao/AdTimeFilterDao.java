package com.vaolan.sspserver.dao;

import java.util.List;

import com.vaolan.sspserver.model.AdTimeFilter;


public interface AdTimeFilterDao {

	/**
	 * 查询adId的投放时间
	 * @param adId
	 * @return List<AdTimeFilter>
	 * */
	public abstract List<AdTimeFilter> getTimeFilterByAdId(String adId);

}