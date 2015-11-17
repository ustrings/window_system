package com.hidata.ad.web.model;

import com.hidata.framework.annotation.db.Column;




public class KeywordOcean {
	
	@Column("kwo_id")
	private String kwoId;
	
	@Column("keyword")
	private String keyword;
	
	@Column("sts")
	private String sts;
	
	@Column("first_type")
	private String fristType;
	
	
	@Column("create_time")
	private String createTime;
	
	@Column("remark")
	private String remark;

	public String getKwoId() {
		return kwoId;
	}

	public void setKwoId(String kwoId) {
		this.kwoId = kwoId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getFristType() {
		return fristType;
	}

	public void setFristType(String fristType) {
		this.fristType = fristType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
