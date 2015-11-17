package com.hidata.web.dao;

import java.util.List;

import com.hidata.web.dto.WhiteDomainDto;

/**
 * 域名白名单   操作数据库
 * @author xiaoming
 * @date 2014-12-31
 */
public interface WhiteDomainDao {
	
	/**
	 * 新增
	 * @param whiteDomain
	 * @return
	 */
	public Integer save(WhiteDomainDto whiteDomain);
	
	/**
	 * 根据主键查询实体
	 * @param pkId
	 * @return
	 */
	public List<WhiteDomainDto> getObjectByPkId(String pkId);
	
	/**
	 * 编辑
	 * @param whiteDomain
	 * @return
	 */
	public Integer update(WhiteDomainDto whiteDomain);
	
	/**
	 * 删除
	 * @param pkId
	 * @return
	 */
	public Integer delete(String pkId);
	
	/**
	 * 批量删除
	 * @param domainIds
	 * @return
	 */
	public Integer deleteAll(String domainIds);
	
}
