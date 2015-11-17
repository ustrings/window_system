package com.hidata.ad.web.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hidata.ad.web.dao.UserAccountDao;
import com.hidata.ad.web.dao.UserAccountLogDao;
import com.hidata.ad.web.model.UserAccount;
import com.hidata.ad.web.model.UserAccountLog;
import com.hidata.ad.web.service.UserAccountService;
import com.hidata.framework.util.DateUtil;

@Component
public class UserAccountServiceImpl implements UserAccountService {

	@Autowired
	private UserAccountLogDao userAccountLogDao;
	
	@Autowired
	private UserAccountDao userAccountDao;

	@Override
	public List<UserAccountLog> findUserAccountLogListByUserid(int userid) {
		return userAccountLogDao.findUserAccountLogListByUserid(userid);
	}

	@Override
	public UserAccount getUserAccountByUserid(int userid) {
		return userAccountDao.getUserAccountByUserid(userid);
	}

	@Scheduled(cron="0 30 2 * * ?")
	@Override
	@Transactional
	public void userAccountAmountCalculateTimer() {
		String lastDate = DateUtil.format(DateUtil.addDays(DateUtil.getCurrentDate(), -1), DateUtil.C_DATE_PATTON_DEFAULT);
		List<Map<String, Object>> costs = userAccountDao.userAdCostStatistics(lastDate + " 00:00:00");
		
		UserAccountLog userAccountLog = null;
		for (Map<String, Object> m : costs) {
			String userid = m.get("userid").toString();
			String totalAmount = m.get("total_amount").toString();
			
			UserAccount acct = userAccountDao.getUserAccountByUserid(Integer.valueOf(userid));
			
			BigDecimal balance = new BigDecimal(acct.getAccountBalance());
			BigDecimal costAmount = new BigDecimal(totalAmount);
			BigDecimal lastBalance = balance.subtract(costAmount);
			
			if (costAmount.compareTo(BigDecimal.ZERO) <= 0)
				continue;
			
			//acct.setAccountBalance(lastBalance.toString());
			userAccountDao.updateUserAccountBalance(acct, costAmount);
			
			userAccountLog = new UserAccountLog();
			userAccountLog.setAccountAmount(totalAmount);
			userAccountLog.setUserid(userid);
			userAccountLog.setAccountDate(lastDate);
			userAccountLog.setAccountType("1");
			userAccountLog.setRemark("广告消耗");
			userAccountLog.setCreateTime(DateUtil.getCurrentDateTimeStr());
			userAccountLog.setAccountBalance(lastBalance.toString());
			
			userAccountLogDao.insertUserAccountLog(userAccountLog);
		}
	}
}
