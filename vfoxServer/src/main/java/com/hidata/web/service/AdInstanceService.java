package com.hidata.web.service;

import java.util.List;

import com.hidata.web.dto.AdInstanceDto;

/**
 * 广告计划的Service
 * @author xiaoming
 *
 */
public interface AdInstanceService {
	/**
	 * 广告计划，查询投放结束的广告计划
	 * @return
	 */
	public List<AdInstanceDto> getListsBysts();
	
	/**
	 * 更改广告计划
	 * @param adIds
	 * @return
	 */
	public Boolean updateSts(String adIds);
}
