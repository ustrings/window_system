package com.hidata.web.dao;

import java.util.Map;

import com.hidata.web.util.Pager;

public interface PagerDao {
	/**
	 * 根据当前页，每页记录，查询结果类型，表名称，查询参数获取查询结果
	 * @param curPage 当前页
	 * @param pageRecord 每页的记录
	 * @param dtoType 查询结果的类型
	 * @param tableName 查询表名称
	 * @param params 查询参数
	 * @return
	 */
	public <T> Pager getPagerByEqual(int curPage,int pageRecord, Class<T> dtoType, String tableName, Map<Object, Object> params);
	
	public <T> Pager getPagerByLike(int curPage,int pageRecord, Class<T> dtoType, String tableName, Map<Object, Object> params);
	
	public <T> Pager getPagerBySql(String sql,int curPage,int pageRecord, Class<T> dtoType);
	
	public <T> Pager getPagerBySqlForBeanPropertyRowMapper(String sql,int curPage,int pageRecord, Class<T> dtoType);
	
}
