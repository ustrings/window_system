package com.hidata.ad.web.dto;

import java.io.Serializable;

public class AdCrowdBaseInfoDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6901207918323624203L;
	// 主键信息
	private String ad_acct;
	//广告id
	private String ad_id;
	//广告名称
	private String match_value;
	
	private String match_type;
	
	private String ts;
	
	private String crowd_id;
	private String crowdName;
	public String getCrowdName() {
		return crowdName;
	}
	public void setCrowdName(String crowdName) {
		this.crowdName = crowdName;
	}
	private String host_type;
	
	private String host;
	private String src_ip;
	private String dt;
	
	//扩展字段
	
	private String historyStartTime;
	private String historyEndTime;
	
	private int adilogId;
	private String defaultTime;
	
	private String uidAndUaEnconding_crowdid;
	private String adEncoding_crowdid;

	public String getUidAndUaEnconding_crowdid() {
		return uidAndUaEnconding_crowdid;
	}
	public void setUidAndUaEnconding_crowdid(String uidAndUaEnconding_crowdid) {
		this.uidAndUaEnconding_crowdid = uidAndUaEnconding_crowdid;
	}
	public String getAdEncoding_crowdid() {
		return adEncoding_crowdid;
	}
	public void setAdEncoding_crowdid(String adEncoding_crowdid) {
		this.adEncoding_crowdid = adEncoding_crowdid;
	}
	public String getSrc_ip() {
		return src_ip;
	}
	public void setSrc_ip(String src_ip) {
		this.src_ip = src_ip;
	}
	
	public String getDefaultTime() {
		return defaultTime;
	}
	public void setDefaultTime(String defaultTime) {
		this.defaultTime = defaultTime;
	}
	public int getAdilogId() {
		return adilogId;
	}
	public void setAdilogId(int adilogId) {
		this.adilogId = adilogId;
	}
	public String getHistoryStartTime() {
		return historyStartTime;
	}
	public void setHistoryStartTime(String historyStartTime) {
		this.historyStartTime = historyStartTime;
	}
	public String getHistoryEndTime() {
		return historyEndTime;
	}
	public void setHistoryEndTime(String historyEndTime) {
		this.historyEndTime = historyEndTime;
	}

	public String getAd_acct() {
		return ad_acct;
	}
	public void setAd_acct(String ad_acct) {
		this.ad_acct = ad_acct;
	}
	public String getAd_id() {
		return ad_id;
	}
	public void setAd_id(String ad_id) {
		this.ad_id = ad_id;
	}

	public String getMatch_value() {
		return match_value;
	}
	public void setMatch_value(String match_value) {
		this.match_value = match_value;
	}
	public String getMatch_type() {
		return match_type;
	}
	public void setMatch_type(String match_type) {
		this.match_type = match_type;
	}
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
	public String getCrowd_id() {
		return crowd_id;
	}
	public void setCrowd_id(String crowd_id) {
		this.crowd_id = crowd_id;
	}
	public String getHost_type() {
		return host_type;
	}
	public void setHost_type(String host_type) {
		this.host_type = host_type;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getDt() {
		return dt;
	}
	public void setDt(String dt) {
		this.dt = dt;
	}
		
}
