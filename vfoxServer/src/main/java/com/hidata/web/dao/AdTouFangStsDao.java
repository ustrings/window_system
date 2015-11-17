package com.hidata.web.dao;

import java.util.List;

import com.hidata.web.dto.AdTouFangStsDto;

/**
 * 操作广告投放状态DAO
 * @author xiaoming
 * @date 2014-12-24
 */
public interface AdTouFangStsDao {
	
	/**
	 * 查询所有投放状态
	 * @return
	 */
	public List<AdTouFangStsDto> qryAllSts();
}
