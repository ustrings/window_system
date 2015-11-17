package com.hidata.ad.web.dto;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;


@Table("crowd_find_sum")
public class AdCrowdFindSum {
	@Column("id")
	private String id;
	
	@Column("crowd_id")
	private String crowdId;
	
	@Column("crowd_num")
	private String crowdNum;
	
	@Column("dt")
	private String dt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCrowdId() {
		return crowdId;
	}

	public void setCrowdId(String crowdId) {
		this.crowdId = crowdId;
	}

	public String getCrowdNum() {
		return crowdNum;
	}

	public void setCrowdNum(String crowdNum) {
		this.crowdNum = crowdNum;
	}

	public String getDt() {
		return dt;
	}

	public void setDt(String dt) {
		this.dt = dt;
	}
	
}
