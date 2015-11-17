package com.hidata.ad.web.dao;

import java.util.List;

import com.hidata.ad.web.model.UserAccountLog;

public interface UserAccountLogDao {

	public List<UserAccountLog> findUserAccountLogListByUserid(int userid);
	
	public void insertUserAccountLog(UserAccountLog log);
}
