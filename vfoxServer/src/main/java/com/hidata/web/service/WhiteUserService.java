package com.hidata.web.service;

import java.util.Map;

import com.hidata.web.util.Pager;

/**
 * 用户白名单的SERVICE
 * @author xiaoming
 *
 */
public interface WhiteUserService {
	
	/**
	 * 获取表格信息
	 * @param map
	 * @param curPage
	 * @return
	 */
	public Pager getPager(Map<String,String> map, String curPage);
	
	/**
	 * 添加
	 * @param userMd5Id
	 * @return
	 */
	public Boolean add(String userMd5Id);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public Boolean delete(String id);
	/**
	 * 批量删除
	 * @param domainIds
	 * @return
	 */
	public Boolean deleteAll(String ids);
	
}
