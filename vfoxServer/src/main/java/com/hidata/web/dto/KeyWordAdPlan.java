package com.hidata.web.dto;

import java.io.Serializable;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.DBExclude;
import com.hidata.framework.annotation.db.Table;

/**
 * 广告计划下--关键词定向表
 * @author ssq
 *
 */
@Table("adplan_keyword")
public class KeyWordAdPlan implements Serializable{
		@DBExclude
	    private static final long serialVersionUID = 3779913323709837175L;
		// 主键
		@Column("id")
		private String id;	
		// 关键词
		@Column("key_wd")
		private String keyWd;	
		// 周期
		@Column("fetch_cicle")
		private String fetchCicle;
		//是否及搜集头
		@Column("is_jisoujitou")
		private String isJisoujitou;
		//搜索类型
		@Column("search_type")
		private String searchType;
		//搜索时间段
		@Column("search_date")
		private String searchDate;
		//搜索次数
		@Column("search_num")
		private String searchNum;
		
		@Column("ad_id")
		private String adId;
		
		@Column("sts_date")
		private String stsDate;
		
		@Column("sts")
		private String sts;
		//匹配关系
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



		public String getAdId() {
			return adId;
		}

		public void setAdId(String adId) {
			this.adId = adId;
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
