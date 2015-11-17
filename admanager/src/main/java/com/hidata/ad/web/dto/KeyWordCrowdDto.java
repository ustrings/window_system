package com.hidata.ad.web.dto;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

@Table("keyword_crowd")
public class KeyWordCrowdDto {
	// 主键
	@Column("id")
	private String id;
	
	// 关键词
	@Column("key_wd")
	private String keyWd;
	
	// 周期
	@Column("fetch_cicle")
	private String fetchCicle;
	
	@Column("is_jisoujitou")
	private String isJisoujitou;
	
	@Column("search_type")
	private String searchType;
	
	@Column("search_date")
	private String searchDate;
	
	@Column("search_num")
	private String searchNum;
	
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

	public String getKeyWd() {
		return keyWd;
	}

	public void setKeyWd(String keyWd) {
		this.keyWd = keyWd;
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

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(String searchDate) {
		this.searchDate = searchDate;
	}

	public String getSearchNum() {
		return searchNum;
	}

	public void setSearchNum(String searchNum) {
		this.searchNum = searchNum;
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
