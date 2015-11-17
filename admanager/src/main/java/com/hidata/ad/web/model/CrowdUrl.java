package com.hidata.ad.web.model;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;


@Table("crowd_url")
public class CrowdUrl {

	@Column("cu_id")
	private String cuId;

	@Column("url")
	private String url;

	@Column("crowd_id")
	private String crowdId;

	@Column("source_type")
	private String sourceType;

	@Column("create_time")
	private String createTime;

	@Column("sts")
	private String sts;

	public String getCuId() {
		return cuId;
	}

	public void setCuId(String cuId) {
		this.cuId = cuId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
