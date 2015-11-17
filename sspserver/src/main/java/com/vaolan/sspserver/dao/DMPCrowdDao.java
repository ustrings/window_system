package com.vaolan.sspserver.dao;

import java.util.List;

public interface DMPCrowdDao {

	/**
	 * 查询ad对应的dmp人群
	 * @param adId
	 * @return List<string>
	 * */
	public abstract List<String> getCrowdIdByAdId(String adId);

}