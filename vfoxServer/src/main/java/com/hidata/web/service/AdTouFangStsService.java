package com.hidata.web.service;

import java.util.List;

import com.hidata.web.dto.AdTouFangStsDto;

/**
 * 操作广告投放状态Service
 * @author xiaoming
 * @date 2014-12-24
 */
public interface AdTouFangStsService {
	/**
	 * 获取所有广告状态
	 * @return
	 */
	public List<AdTouFangStsDto> getAllLists();
}
