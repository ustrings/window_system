package com.hidata.ad.web.dto;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;


@Table("crowd_find_info")
public class AdCrowdFindInfo {
	@Column("crowd_id")
	private String crowdId;
	
	@Column("crowd_name")
	private String crowdName;
	
	@Column("crowd_sts")
	private String crowdSts;
	
	@Column("create_date")
	private String createDate;
	
	@Column("express_date")
	private String expressDate;
	
	@Column("user_id")
	private String userId;
	
	@Column("direct_type")
	private String directType;
	
	public String getDirectType() {
		return directType;
	}

	public void setDirectType(String directType) {
		this.directType = directType;
	}


	public String getCrowdId() {
		return crowdId;
	}

	public void setCrowdId(String crowdId) {
		this.crowdId = crowdId;
	}

	public String getCrowdName() {
		return crowdName;
	}

	public void setCrowdName(String crowdName) {
		this.crowdName = crowdName;
	}

	public String getCrowdSts() {
		return crowdSts;
	}

	public void setCrowdSts(String crowdSts) {
		this.crowdSts = crowdSts;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getExpressDate() {
		return expressDate;
	}

	public void setExpressDate(String expressDate) {
		this.expressDate = expressDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
