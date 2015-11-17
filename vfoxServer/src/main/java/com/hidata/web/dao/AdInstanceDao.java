package com.hidata.web.dao;

import java.util.List;

import com.hidata.web.dto.AdInstanceDto;

/**
 * 广告计划的DAO
 * @author xiaoming
 *
 */
public interface AdInstanceDao {
	
	/**
	 * 查询广告计划
	 * @return
	 */
	public List<AdInstanceDto> getListBySts();
	
	/**
	 * 更改广告计划
	 * @param adIds
	 * @return
	 */
	public Integer updateSts(String adIds);
}
