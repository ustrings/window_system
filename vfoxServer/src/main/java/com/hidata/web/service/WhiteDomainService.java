package com.hidata.web.service;

import java.util.Map;

import com.hidata.web.dto.WhiteDomainDto;
import com.hidata.web.util.Pager;

/**
 * 域名白名单 Service
 * @author xiaoming
 * @date 2014-12-31
 */
public interface WhiteDomainService {
	
	/**
	 * 获取Pager 对象
	 * @param map
	 * @param curPage
	 * @return
	 */
	public Pager getPager(Map<String,String> map, String curPage); 
	
	/**
	 * 新增
	 * @param whiteDomain
	 * @return
	 */
	public Boolean addWhiteDomain(WhiteDomainDto whiteDomain);
	
	/**
	 * 根据主键ID查询实体
	 * @param id
	 * @return
	 */
	public WhiteDomainDto getWhiteDomainById(String id);
	
	/**
	 * 编辑
	 * @param whiteDomain
	 * @return
	 */
	public Boolean edit(WhiteDomainDto whiteDomain);
	
	/**
	 * 删除
	 * @param domainId
	 * @return
	 */
	public Boolean delete(String domainId);
	
	/**
	 * 批量删除
	 * @param domainIds
	 * @return
	 */
	public Boolean deleteAll(String domainIds);
}
