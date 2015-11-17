package com.hidata.web.dto;

import com.hidata.framework.annotation.db.Column;

/**
 *  广告的审核历史 信息 进度
 * @author lenovo
 *
 */
public class CheckHistoryDto {

	@Column("ad_id")
	private String adId;
	
	@Column("check_userid")
	private String checkUserid;
	
	@Column("check_username")
	private String checkUsername;
	
	@Column("check_deptname")
	private String checkDeptname;
	
	@Column("check_sts")
	private String checkSts;
	
	@Column("check_desc")
	private String checkDesc;
	
	@Column("check_date")
	private String checkDate;

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getCheckUserid() {
		return checkUserid;
	}

	public void setCheckUserid(String checkUserid) {
		this.checkUserid = checkUserid;
	}

	public String getCheckUsername() {
		return checkUsername;
	}

	public void setCheckUsername(String checkUsername) {
		this.checkUsername = checkUsername;
	}

	public String getCheckDeptname() {
		return checkDeptname;
	}

	public void setCheckDeptname(String checkDeptname) {
		this.checkDeptname = checkDeptname;
	}

	public String getCheckSts() {
		return checkSts;
	}

	public void setCheckSts(String checkSts) {
		this.checkSts = checkSts;
	}

	public String getCheckDesc() {
		return checkDesc;
	}

	public void setCheckDesc(String checkDesc) {
		this.checkDesc = checkDesc;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
}
