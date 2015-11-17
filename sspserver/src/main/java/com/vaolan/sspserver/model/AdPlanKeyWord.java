package com.vaolan.sspserver.model;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/**
 * 广告对应标签信息
 * Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年12月15日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
@Table("adplan_keyword")
public class AdPlanKeyWord {
    // 主键
    @Column("id")
    private String id;
    
    @Column("key_wd")
    private String keyWd;
    
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
    
    @Column("ad_id")
    private String adId;
    
    @Column("sts")
    private String sts;
    
    @Column("sts_date")
    private String stsDate;
    
    @Column("match_type")
    private String matchType;
    
    @Column("kw_type")
    private String kwType;
    
    @Column("kw_file_path")
    private String kwFilePath;

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

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getStsDate() {
		return stsDate;
	}

	public void setStsDate(String stsDate) {
		this.stsDate = stsDate;
	}

	public String getMatchType() {
		return matchType;
	}

	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}

	public String getKwType() {
		return kwType;
	}

	public void setKwType(String kwType) {
		this.kwType = kwType;
	}

	public String getKwFilePath() {
		return kwFilePath;
	}

	public void setKwFilePath(String kwFilePath) {
		this.kwFilePath = kwFilePath;
	}
	
    
//    //全路径名称MD5值
//    private String  fullPathNameMDVal;
//    
//    //
//    private String labelName;

    
}
