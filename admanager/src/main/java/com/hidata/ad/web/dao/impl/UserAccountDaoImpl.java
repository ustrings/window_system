package com.hidata.ad.web.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.ad.web.dao.UserAccountDao;
import com.hidata.ad.web.model.UserAccount;
import com.hidata.framework.db.DBManager;

@Component
public class UserAccountDaoImpl implements UserAccountDao{

	@Autowired
	private DBManager db;

	private static final String GET_USER_ACCOUNT_BY_USER = "select userid,create_time,account_balance,total_amount from user_account where userid = ?";
	@Override
	public UserAccount getUserAccountByUserid(int userid) {
		Object[] args = new Object[]{userid};
		return db.queryForObject(GET_USER_ACCOUNT_BY_USER, args, UserAccount.class);
	}
	
	private static final String USER_AD_COST_STATISTICS = "select b.userid, sum(a.pv_num * b.unit_price / 1000) total_amount from ad_stat_info a, ad_instance b where a.ad_id = b.ad_id and a.num_type = 'D' and b.ad_useful_type = 'N' and b.sts = 'A' and a.ts = ? group by b.userid";
	@Override
	public List<Map<String, Object>> userAdCostStatistics(String date) {
		Object[] args = new Object[]{date};
		return db.queryForList(USER_AD_COST_STATISTICS, args);
	}
	
	private static final String UPDATE_USER_ACCOUNT_BALANCE = "update user_account set account_balance = account_balance - ?, update_time = sysdate(), updator = ? where userid = ?";
	@Override
	public void updateUserAccountBalance(UserAccount userAccount, BigDecimal cost) {
		Object[] args = new Object[]{cost, "timer", userAccount.getUserid()};
		db.update(UPDATE_USER_ACCOUNT_BALANCE, args);
	}
}
