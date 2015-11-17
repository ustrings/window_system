package com.hidata.web.dto;

import java.io.Serializable;
import java.util.Date;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

@Table("ad_crowd_link")
public class AdCrowdLinkDto implements Serializable {

	private static final long serialVersionUID = 3779913323709837175L;

	// 主键信息
	@Column("id")
	private String adCrowdLinkId;

	// 广告ID
	@Column("ad_id")
	private String adId;

	// 人群ID
	@Column("crowd_id")
	private String crowdId;

	// 创建时间
	@Column("create_time")
	private String createTime;

	// 状态
	@Column("sts")
	private String sts;

	public String getAdCrowdLinkId() {
		return adCrowdLinkId;
	}

	public void setAdCrowdLinkId(String adCrowdLinkId) {
		this.adCrowdLinkId = adCrowdLinkId;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getCrowdId() {
		return crowdId;
	}

	public void setCrowdId(String crowdId) {
		this.crowdId = crowdId;
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
