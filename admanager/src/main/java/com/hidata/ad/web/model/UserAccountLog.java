package com.hidata.ad.web.model;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

@Table("user_account_log")
public class UserAccountLog {

	@Column("id")
	private String id;
	
	@Column("userid")
	private String userid;
	
	@Column("account_type")
	private String accountType;
	
	@Column("account_amount")
	private String accountAmount;
	
	@Column("account_date")
	private String accountDate;
	
	@Column("remark")
	private String remark;
	
	@Column("create_time")
	private String createTime;
	
	@Column("account_balance")
	private String accountBalance;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountAmount() {
		return accountAmount;
	}

	public void setAccountAmount(String accountAmount) {
		this.accountAmount = accountAmount;
	}

	public String getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}
}
