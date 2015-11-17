package com.hidata.ad.web.model;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;


@Table("crowd_portrait")
public class CrowdPortrait {

	private Integer id;
	
	@Column("crowd_id")
	private String crowdId;
	
	@Column("sex")
	private String sex;
	
	@Column("age_range")
	private String ageRange;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCrowdId() {
		return crowdId;
	}

	public void setCrowdId(String crowdId) {
		this.crowdId = crowdId;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAgeRange() {
		return ageRange;
	}

	public void setAgeRange(String ageRange) {
		this.ageRange = ageRange;
	}

}
