package com.hidata.ad.web.dto;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

@Table("integer_model_crowd")
public class IntegerModelCrowdDto {
	// 主键
	@Column("id")
	private String id;
	
	// 模型类型
	@Column("model_type")
	private String modelType;
	
	// 周期
	@Column("fetch_cicle")
	private String fetchCicle;
	
	@Column("match_url")
	private String matchUrl;
	
	@Column("crowd_id")
	private String crowdId;
	
	@Column("sts_date")
	private String stsDate;
	
	@Column("sts")
	private String sts;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getFetchCicle() {
		return fetchCicle;
	}

	public void setFetchCicle(String fetchCicle) {
		this.fetchCicle = fetchCicle;
	}

	public String getMatchUrl() {
		return matchUrl;
	}

	public void setMatchUrl(String matchUrl) {
		this.matchUrl = matchUrl;
	}

	public String getCrowdId() {
		return crowdId;
	}

	public void setCrowdId(String crowdId) {
		this.crowdId = crowdId;
	}

	public String getStsDate() {
		return stsDate;
	}

	public void setStsDate(String stsDate) {
		this.stsDate = stsDate;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

}
