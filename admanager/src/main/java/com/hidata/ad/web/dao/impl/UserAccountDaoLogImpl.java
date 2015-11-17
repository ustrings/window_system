package com.hidata.ad.web.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.ad.web.dao.UserAccountLogDao;
import com.hidata.ad.web.model.UserAccountLog;
import com.hidata.framework.db.DBManager;

@Component
public class UserAccountDaoLogImpl implements UserAccountLogDao{

	@Autowired
	private DBManager db;
	
	private static final String FIND_USER_ACCOUNT_LOG_LIST_BY_USERID = "select id, userid, account_type, account_amount, account_date, create_time, remark, account_balance from user_account_log where userid = ? order by account_date desc";
	@Override
	public List<UserAccountLog> findUserAccountLogListByUserid(int userid) {
		Object[] args = new Object[]{userid};
		return db.queryForListObject(FIND_USER_ACCOUNT_LOG_LIST_BY_USERID, args, UserAccountLog.class);
	}
	
	@Override
	public void insertUserAccountLog(UserAccountLog log) {
		db.insertObject(log);
	}

}
