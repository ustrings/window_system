package com.hidata.ad.web.dto;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

@Table("url_crowd")
public class UrlCrowdDto {
	// 主键
	@Column("id")
	private String id;
	
	// 关键词
	@Column("url")
	private String url;
	
	// 周期
	@Column("fetch_cicle")
	private String fetchCicle;
	
	// 周期
	@Column("is_jisoujitou")
	private String isJisoujitou;
	
	@Column("match_date")
	private String matchDate;
	
	@Column("match_num")
	private String matchNum;
	
	@Column("crowd_id")
	private String crowdId;
	
	@Column("sts_date")
	private String stsDate;
	
	@Column("sts")
	private String sts;
	
	@Column("match_type")
	private String matchType;

	public String getMatchType() {
		return matchType;
	}

	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFetchCicle() {
		return fetchCicle;
	}

	public void setFetchCicle(String fetchCicle) {
		this.fetchCicle = fetchCicle;
	}
	
	public String getIsJisoujitou() {
		return isJisoujitou;
	}

	public void setIsJisoujitou(String isJisoujitou) {
		this.isJisoujitou = isJisoujitou;
	}

	public String getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}

	public String getMatchNum() {
		return matchNum;
	}

	public void setMatchNum(String matchNum) {
		this.matchNum = matchNum;
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
