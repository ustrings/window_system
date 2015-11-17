package com.hidata.web.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import com.hidata.framework.db.DBManager;
import com.hidata.web.dao.PagerDao;
import com.hidata.web.util.Pager;
import com.hidata.web.util.SqlUtils;

@Component
public class PagerDaoImpl implements PagerDao {
	@Autowired
	private DBManager db;
	
	@Override
	public <T> Pager getPagerByEqual(int curPage, int pageRecord, Class<T> dtoType,
			String tableName, Map<Object, Object> params) {
		Pager pager = new Pager();
		if (curPage <=0) {
			curPage = 1;
		}
		pager.setCurPage(curPage);
		if (pageRecord <= 0) {
			pageRecord = pager.getPageRecord();
		}
		pager.setPageRecord(pageRecord);
		String sqlCount = SqlUtils.assembleCountSqlEqual(params, tableName);
		int allRecord = db.queryForInt(sqlCount);
		int  allPage = (allRecord % pageRecord) > 0 ? 
			   (allRecord / pageRecord) + 1 : allRecord / pageRecord;
		pager.setAllPage(allPage);
		pager.setAllRecord(allRecord);
		
		String sqlPger = SqlUtils.assembleQuerySqlEqual(params, tableName, curPage, pageRecord);
		List<T> queryResult = db.queryForListObject(sqlPger, dtoType);
		pager.setResult(queryResult);
		
		return pager;
	}

	@Override
	public <T> Pager getPagerByLike(int curPage, int pageRecord,
			Class<T> dtoType, String tableName, Map<Object, Object> params) {
		Pager pager = new Pager();
		if (curPage <=0) {
			curPage = 1;
		}
		pager.setCurPage(curPage);
		if (pageRecord <= 0) {
			pageRecord = pager.getPageRecord();
		}
		pager.setPageRecord(pageRecord);
		String sqlCount = SqlUtils.assembleCountSqlLike(params, tableName);
		int allRecord = db.queryForInt(sqlCount);
		int  allPage = (allRecord % pageRecord) > 0 ? 
			   (allRecord / pageRecord) + 1 : allRecord / pageRecord;
		pager.setAllPage(allPage);
		pager.setAllRecord(allRecord);
		
		String sqlPger = SqlUtils.assembleQuerySqlLike(params, tableName, curPage, pageRecord);
		List<T> queryResult = db.queryForListObject(sqlPger, dtoType);
		pager.setResult(queryResult);
		
		return pager;
	}

	@Override
	public <T> Pager getPagerBySql(String sql, int curPage, int pageRecord,
			Class<T> dtoType) {
		Pager pager = new Pager();
		if (curPage <=0) {
			curPage = 1;
		}
		pager.setCurPage(curPage);
		if (pageRecord <= 0) {
			pageRecord = pager.getPageRecord();
		}
		pager.setPageRecord(pageRecord);
		String sqlCount = SqlUtils.getCountSqlFromSql(sql);
		int allRecord = db.queryForInt(sqlCount);
		int  allPage = (allRecord % pageRecord) > 0 ? 
			   (allRecord / pageRecord) + 1 : allRecord / pageRecord;
		pager.setAllPage(allPage);
		pager.setAllRecord(allRecord);
		
		String sqlQuery= SqlUtils.getQuerySqlFromSql(sql, curPage, pageRecord);
		List<T> queryResult = db.queryForListObject(sqlQuery, dtoType);
		pager.setResult(queryResult);
		
		return pager;
	}

	@Override
	public <T> Pager getPagerBySqlForBeanPropertyRowMapper(String sql,
			int curPage, int pageRecord, Class<T> dtoType) {
		Pager pager = new Pager();
		if (curPage <=0) {
			curPage = 1;
		}
		pager.setCurPage(curPage);
		if (pageRecord <= 0) {
			pageRecord = pager.getPageRecord();
		}
		pager.setPageRecord(pageRecord);
		String sqlCount = SqlUtils.getCountSqlFromSql(sql);
		int allRecord = db.queryForInt(sqlCount);
		int  allPage = (allRecord % pageRecord) > 0 ? 
			   (allRecord / pageRecord) + 1 : allRecord / pageRecord;
		pager.setAllPage(allPage);
		pager.setAllRecord(allRecord);
		
		String sqlQuery= SqlUtils.getQuerySqlFromSql(sql, curPage, pageRecord);
		List<T> queryResult = db.getTemplate().query(sqlQuery, new BeanPropertyRowMapper(dtoType));
		pager.setResult(queryResult);
		
		return pager;
	}
	

	

}
