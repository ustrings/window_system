package com.hidata.web.service;

import com.hidata.web.dto.AdControlDto;

/**
 * 投放控制Service
 * @author xiaoming
 * @date 2015-1-5
 */
public interface AdControlService {
	
	/**
	 * 编辑
	 * @param adControlDto
	 * @return
	 */
	public Boolean editAdControl(AdControlDto adControlDto);
	
	/**
	 * 查询
	 * @return
	 */
	public AdControlDto getAdControlDto();
}
