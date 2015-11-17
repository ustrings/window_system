package com.vaolan.sspserver.dao;

import java.util.Set;

public interface AdRegionDao {

	
	/**
	 * 根据adid 查询投放的区域
	 * @param adId
	 * @return
	 */
	public Set<String> getRegionTargetByAdId(String adId);
	
	}