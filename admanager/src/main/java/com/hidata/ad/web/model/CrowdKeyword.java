package com.hidata.ad.web.model;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;


@Table("crowd_keyword")
public class CrowdKeyword {

	
	@Column("ck_id")
	private String ckId;
	
	@Column("keyword")
	private String keyword;
	
	@Column("crowd_id")
	private String crowdId;
	
	@Column("source_type")
	
	private String sourceType;
	
	@Column("create_time")
	private String createTime;
	
	@Column("sts")
	private String sts;

	
	
	public String getCkId() {
		return ckId;
	}

	public void setCkId(String ckId) {
		this.ckId = ckId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getCrowdId() {
		return crowdId;
	}

	public void setCrowdId(String crowdId) {
		this.crowdId = crowdId;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}
}
