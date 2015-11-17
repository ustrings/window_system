package com.hidata.web.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.hidata.framework.db.DBManager;
import com.hidata.web.dao.WhiteUserDao;
import com.hidata.web.dto.WhiteUserDto;

@Component
public class WhiteUserDaoImpl implements WhiteUserDao{
	
	@Autowired
	private DBManager db;
	@Override
	public Integer save(WhiteUserDto whiteUser) {
		try {
			Integer id = db.insertObjectAndGetAutoIncreaseId(whiteUser);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public Integer delete(String id) {
		try {
			String sql = "DELETE FROM white_user WHERE id = " + id;
			Integer rows = db.update(sql);
			return rows;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Integer deleteAll(String ids) {
		String sql = "DELETE FROM white_user WHERE id IN ( "+ ids +" )";
		try {
			Integer rows = db.update(sql);
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
