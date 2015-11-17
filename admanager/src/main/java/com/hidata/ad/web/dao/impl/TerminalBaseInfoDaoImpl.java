package com.hidata.ad.web.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.hidata.ad.web.dao.TerminalBaseInfoDao;
import com.hidata.ad.web.dto.TerminalBaseInfo;
import com.hidata.framework.db.DBManager;
/**
 * 操作终端定向的Dao实现类
 * @author xiaoming
 *
 */
@Repository
public class TerminalBaseInfoDaoImpl implements TerminalBaseInfoDao {
	@Autowired
	private DBManager db;
	
	@Override
	public List<TerminalBaseInfo> getList() {
		String sql = "SELECT tbi_id, t_type, t_name, t_value FROM terminal_base_info";
		return db.queryForListObject(sql, null, TerminalBaseInfo.class);
	}

	@Override
	public List<TerminalBaseInfo> getListByType(String type) {
		
		try {
			String sql = "SELECT tbi_id, t_type, t_name, t_value FROM terminal_base_info WHERE t_type = ?";
			Object[] args = new Object[]{
				type
			};
			return db.queryForListObject(sql, args,TerminalBaseInfo.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据ID 查询value值
	 */
	@Override
	public List<TerminalBaseInfo> getValueByTid(String tId) {
		try {
			String sql = "SELECT tbi_id, t_value FROM terminal_base_info WHERE tbi_id = ? ";
			Object[] args = new Object[]{
				tId	
			};
			return db.queryForListObject(sql, args,TerminalBaseInfo.class);
					} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
