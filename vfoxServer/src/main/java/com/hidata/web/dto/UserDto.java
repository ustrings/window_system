package com.hidata.web.dto;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/**
 * 用户表对应的实体
 * @author xiaoming
 * @date 2014-12-22
 */
@Table("user")
public class UserDto {
	@Column("userid")
	private String userId;
	@Column("username")
	private String userName;
	@Column("password")
	private String passWord;
	@Column("company_name")
	private String companyName;
	@Column("company_leader")
	private String companyLeader;
	@Column("tel_nbr")
	private String telNbr;
	@Column("id_card_path")
	private String idCardPath;
	@Column("yingye_card_path")
	private String yingyeCardPath;
	@Column("tax_card_path")
	private String taxCardPath;
	@Column("org_card_path")
	private String orgCardPath;
	@Column("user_type")
	private String userType;
	@Column("sts")
	private String sts;
	@Column("sts_date")
	private String stsDate;
	@Column("test")
	private String test;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyLeader() {
		return companyLeader;
	}
	public void setCompanyLeader(String companyLeader) {
		this.companyLeader = companyLeader;
	}
	public String getTelNbr() {
		return telNbr;
	}
	public void setTelNbr(String telNbr) {
		this.telNbr = telNbr;
	}
	public String getIdCardPath() {
		return idCardPath;
	}
	public void setIdCardPath(String idCardPath) {
		this.idCardPath = idCardPath;
	}
	public String getYingyeCardPath() {
		return yingyeCardPath;
	}
	public void setYingyeCardPath(String yingyeCardPath) {
		this.yingyeCardPath = yingyeCardPath;
	}
	public String getTaxCardPath() {
		return taxCardPath;
	}
	public void setTaxCardPath(String taxCardPath) {
		this.taxCardPath = taxCardPath;
	}
	public String getOrgCardPath() {
		return orgCardPath;
	}
	public void setOrgCardPath(String orgCardPath) {
		this.orgCardPath = orgCardPath;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public String getSts() {
		return sts;
	}
	public void setSts(String sts) {
		this.sts = sts;
	}
	public String getStsDate() {
		return stsDate;
	}
	public void setStsDate(String stsDate) {
		this.stsDate = stsDate;
	}
}
