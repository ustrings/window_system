package com.hidata.web.dao;

import java.util.List;

import com.hidata.web.dto.AdControlDto;

/**
 * 投放控制DAO
 * @author xiaoming
 * @date 2015-1-5
 */
public interface AdControlDao {
	
	/**
	 * 查询投放控制
	 * @return
	 */
	public List<AdControlDto> qryAdControl();
	
	
	/**
	 * 跟新投放设置
	 * @return
	 */
	public Integer updateAdControl(AdControlDto adControlDto);
}
