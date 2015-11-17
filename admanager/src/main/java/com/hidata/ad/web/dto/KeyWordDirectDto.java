package com.hidata.ad.web.dto;


public class KeyWordDirectDto {
	
	// 关键词
	private String keyWds;
	
	private String urls;
	
	// 周期
	private String fetchCicle;
	
	private String isJisoujitou;//即搜即投
	
	private String searchType;
	
	private String keywordSearchDate;//关键词 小时内
	 
	private String keywordSearchNum; //匹配次数
	
	private String urlSearchDate;//url 搜索date
	
	private String urlSearchNum;//url 搜索数量
	
	private String keyWdMatchType;
	private String urlMatchType;
	

	public String getKeyWdMatchType() {
		return keyWdMatchType;
	}

	public void setKeyWdMatchType(String keyWdMatchType) {
		this.keyWdMatchType = keyWdMatchType;
	}

	public String getUrlMatchType() {
		return urlMatchType;
	}

	public void setUrlMatchType(String urlMatchType) {
		this.urlMatchType = urlMatchType;
	}

	public String getKeyWds() {
		return keyWds;
	}

	public void setKeyWds(String keyWds) {
		this.keyWds = keyWds;
	}

	public String getUrls() {
		return urls;
	}

	public void setUrls(String urls) {
		this.urls = urls;
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

	public String getKeywordSearchDate() {
		return keywordSearchDate;
	}

	public void setKeywordSearchDate(String keywordSearchDate) {
		this.keywordSearchDate = keywordSearchDate;
	}

	public String getKeywordSearchNum() {
		return keywordSearchNum;
	}

	public void setKeywordSearchNum(String keywordSearchNum) {
		this.keywordSearchNum = keywordSearchNum;
	}

	public String getUrlSearchDate() {
		return urlSearchDate;
	}

	public void setUrlSearchDate(String urlSearchDate) {
		this.urlSearchDate = urlSearchDate;
	}

	public String getUrlSearchNum() {
		return urlSearchNum;
	}

	public void setUrlSearchNum(String urlSearchNum) {
		this.urlSearchNum = urlSearchNum;
	}

}
