package com.hidata.ad.web.service;

import java.util.List;

import com.hidata.ad.web.model.UserAccount;
import com.hidata.ad.web.model.UserAccountLog;

public interface UserAccountService {

	public List<UserAccountLog> findUserAccountLogListByUserid(int userid);
	
	public UserAccount getUserAccountByUserid(int userid);
	
	public void userAccountAmountCalculateTimer();
}
