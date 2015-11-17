package com.hidata.ad.web.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.ad.web.dao.TestDao;
import com.hidata.framework.db.DBManager;

@Component
public class TestDaoImpl implements TestDao {
	
	@Autowired
	private DBManager db;
	
	public 	List<Map<String,Object>> queryKeyword(){
		String sql = "select keyword from keyword_ocean";
		return db.queryForList(sql);
	}

}
