package com.hidata.ad.web.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hidata.ad.web.model.UserAccount;


public interface UserAccountDao {
	
	public UserAccount getUserAccountByUserid(int userid);

	public List<Map<String, Object>> userAdCostStatistics(String date);
	
	public void updateUserAccountBalance(UserAccount userAccount, BigDecimal cost);
	
}
