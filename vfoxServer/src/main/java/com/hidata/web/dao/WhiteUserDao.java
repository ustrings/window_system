package com.hidata.web.dao;

import com.hidata.web.dto.WhiteUserDto;

/**
 * 用户白名单DAO
 * @author xiaoming
 *
 */
public interface WhiteUserDao {
	
	/**
	 * 新建
	 * @param whiteUser
	 * @return
	 */
	public Integer save(WhiteUserDto whiteUser);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public Integer delete(String id);
	
	/**
	 * 批量删除
	 * @param domainIds
	 * @return
	 */
	public Integer deleteAll(String ids);
}
