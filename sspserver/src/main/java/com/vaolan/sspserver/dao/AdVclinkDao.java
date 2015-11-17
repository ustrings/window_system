package com.vaolan.sspserver.dao;
import java.util.List;

public interface AdVclinkDao {

	/**
	 * 查询广告所属人群
	 * @param adId
	 * @return List<String>
	 * */
	public abstract List<String> getVcIdByAdId(String adId);

}