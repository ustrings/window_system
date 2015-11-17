package com.hidata.web.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class SqlUtils {
	
	public static void main(String[] args) {
		String sql  = "select * from test";
		System.out.println(getCountSqlFromSql(sql));
	}
	
	public static String getCountSqlFromSql(String sql) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from (").append(sql).append(") tt");
		return sb.toString();
	}
	
	public static String getQuerySqlFromSql(String sql, int curPage, int pageRecord) {
		StringBuffer sb = new StringBuffer(sql);
		sb.append(" limit ").append(((curPage -1) * pageRecord))
		.append(" , ").append(pageRecord);
		return sb.toString();
	}
			
	/**
	 * 组装 count 查询语句 使用 = 作为匹配条件
	 * @param params
	 * @param tableName
	 * @return
	 */
	public static String assembleCountSqlEqual(Map<Object, Object> params, String tableName) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from ").append(tableName);
		if (params != null) {
			Set<Entry<Object, Object>> entrys = params.entrySet();
			sql.append(" where 1=1 " );
			for (Entry<Object, Object> entry : entrys) {
				if(entry.getKey() != null && !entry.getKey().equals("")) {
					if (entry.getValue() != null && !entry.getValue().equals("")) {
						sql.append(" and " ).append(entry.getKey()).append(" = '").append(entry.getValue()).append("'");
					}
				}
			}
		}
		return sql.toString();
	}
	
	/**
	 * 组装 count 查询语句 使用 like 作为匹配条件
	 * @param params
	 * @param tableName
	 * @return
	 */
	public static String assembleCountSqlLike(Map<Object, Object> params, String tableName) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from ").append(tableName);
		if (params != null) {
			Set<Entry<Object, Object>> entrys = params.entrySet();
			sql.append(" where 1=1 " );
			for (Entry<Object, Object> entry : entrys) {
				if(entry.getKey() != null && !entry.getKey().equals("")) {
					if (entry.getValue() != null && !entry.getValue().equals("")) {
						sql.append(" and " ).append(entry.getKey()).append(" like '%").append(entry.getValue()).append("%'");
					}
				}
			}
		}
		return sql.toString();
	}
	
	public static String assembleQuerySqlEqual(Map<Object, Object> params, String tableName, int curPage, int pageRecord) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(tableName);
		if (params != null) {
			Set<Entry<Object, Object>> entrys = params.entrySet();
			sql.append(" where 1=1 " );
			for (Entry<Object, Object> entry : entrys) {
				if(entry.getKey() != null && !entry.getKey().equals("")) {
					if (entry.getValue() != null && !entry.getValue().equals("")) {
						sql.append(" and " ).append(entry.getKey()).append(" = '").append(entry.getValue()).append("'");
					}
				}
			}
		}
		sql.append(" limit ").append(((curPage -1) * pageRecord))
		.append(" , ").append(pageRecord);
		return sql.toString();
	}
	
	public static String assembleQuerySqlLike(Map<Object, Object> params, String tableName, int curPage, int pageRecord) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(tableName);
		if (params != null) {
			Set<Entry<Object, Object>> entrys = params.entrySet();
			sql.append(" where 1=1 " );
			for (Entry<Object, Object> entry : entrys) {
				if(entry.getKey() != null && !entry.getKey().equals("")) {
					if (entry.getValue() != null && !entry.getValue().equals("")) {
						sql.append(" and " ).append(entry.getKey()).append(" like '%").append(entry.getValue()).append("%'");
					}
				}
			}
		}
		sql.append(" limit ").append(((curPage -1) * pageRecord))
		.append(" , ").append(pageRecord);
		return sql.toString();
	}
}
 