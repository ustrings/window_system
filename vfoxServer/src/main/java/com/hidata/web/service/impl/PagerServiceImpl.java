package com.hidata.web.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.web.dao.PagerDao;
import com.hidata.web.service.PagerService;
import com.hidata.web.util.Pager;

@Component
public class PagerServiceImpl implements PagerService {
	@Autowired
	private PagerDao pagerDao;

	/**
	 * 根据当前页，每页记录，查询结果类型，表名称，查询参数获取查询结果，参数使用 = 匹配
	 * @param curPage 当前页
	 * @param pageRecord 每页的记录
	 * @param dtoType 查询结果的类型
	 * @param tableName 查询表名称
	 * @param params 查询参数
	 * @return
	 */
	@Override
	public <T> Pager getPagerByEqual(int curPage, int pageRecord, 
			Class<T> dtoType, String tableName, Map<Object, Object> params) {
		return pagerDao.getPagerByEqual(curPage, pageRecord, dtoType, tableName, params);
	}
	
	
	/**
	 * 根据当前页，每页记录，查询结果类型，表名称，查询参数获取查询结果，sql 自动生成，使用的是 like 匹配模式
	 */
	@Override
	public <T> Pager getPagerByLike(int curPage, int pageRecord,
			Class<T> dtoType, String tableName, Map<Object, Object> params) {
		return pagerDao.getPagerByLike(curPage, pageRecord, dtoType, tableName, params);
	}
	
	/**
	 * 根据当前页，每页记录，查询结果类型，表名称，查询参数获取查询结果，sql 自己传入
	 */
	@Override
	public <T> Pager getPagerBySql(String sql, int curPage, int pageRecord,
			Class<T> dtoType) {
		return pagerDao.getPagerBySql(sql, curPage, pageRecord, dtoType);
	}
	
	/**
	 * 根据当前页，每页记录，查询结果类型，表名称，查询参数获取查询结果，sql 自己传入，联合查询
	 */
	@Override
	public <T> Pager getPagerBySqlForBeanPropertyRowMapper(String sql,
			int curPage, int pageRecord, Class<T> dtoType) {
		return pagerDao.getPagerBySqlForBeanPropertyRowMapper(sql, curPage, pageRecord, dtoType);
	}
}
