package com.microsoft.Test_struts_jpa.entity;

import javax.persistence.*;

@Entity
public class Dsp_Users {
	private int userid;
	private int companyid;
	private int pid;
	private String username;
	private String password;
	private String nickname;
	private long regdate;
	private long lastdate;
	private String regip;
	private String lastip;
	private int loginnum;
	private String email;
	private String mobile;
	private int islock;
	private int vip;
	private int overduedate;
	private int status;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	@Column
	public int getCompanyid() {
		return companyid;
	}
	public void setCompanyid(int companyid) {
		this.companyid = companyid;
	}
	
	@Column
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	
	@Column
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Column
	public long getRegdate() {
		return regdate;
	}
	public void setRegdate(long regdate) {
		this.regdate = regdate;
	}
	
	@Column
	public long getLastdate() {
		return lastdate;
	}
	public void setLastdate(long lastdate) {
		this.lastdate = lastdate;
	}
	
	@Column
	public String getRegip() {
		return regip;
	}
	public void setRegip(String regip) {
		this.regip = regip;
	}
	
	@Column
	public String getLastip() {
		return lastip;
	}
	public void setLastip(String lastip) {
		this.lastip = lastip;
	}
	
	@Column
	public int getLoginnum() {
		return loginnum;
	}
	public void setLoginnum(int loginnum) {
		this.loginnum = loginnum;
	}
	
	@Column
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Column
	public int getIslock() {
		return islock;
	}
	public void setIslock(int islock) {
		this.islock = islock;
	}
	
	@Column
	public int getVip() {
		return vip;
	}
	public void setVip(int vip) {
		this.vip = vip;
	}
	
	@Column
	public int getOverduedate() {
		return overduedate;
	}
	public void setOverduedate(int overduedate) {
		this.overduedate = overduedate;
	}
	
	@Column
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Column
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
