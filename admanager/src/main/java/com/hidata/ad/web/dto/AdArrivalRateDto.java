package com.hidata.ad.web.dto;

import java.io.Serializable;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.DBExclude;
import com.hidata.framework.annotation.db.Table;

@Table("ad_pre_throw_crowd")
public class AdArrivalRateDto implements Serializable {

	/**
	 * 
	 */
	@DBExclude
	private static final long serialVersionUID = 2072824063427730137L;
	
	@Column("id")
	private String id;
	
	@Column("ad_id")
	private String adId;
	
	@Column("ads")
	private String ads;
	
	@Column("adnum")
	private Integer adnum;
	
	@Column("ad_name")
	private String adName;
	
	private String impressNum;
	
	private String arrivalRate;
	private String sts;

	private String startTime;
	private String endTime;
	
	private String subStartArrivalnum;
	private String subEndArrivalNum;
	
	private String substartTime;
	private String subendTime;
	
	private String arriveSum;
	
	private String ad_acct;
	
	
	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}
	public String getAd_acct() {
		return ad_acct;
	}

	public void setAd_acct(String ad_acct) {
		this.ad_acct = ad_acct;
	}

	public String getArriveSum() {
		return arriveSum;
	}

	public void setArriveSum(String arriveSum) {
		this.arriveSum = arriveSum;
	}

	public String getSubStartArrivalnum() {
		return subStartArrivalnum;
	}

	public void setSubStartArrivalnum(String subStartArrivalnum) {
		this.subStartArrivalnum = subStartArrivalnum;
	}

	public String getSubEndArrivalNum() {
		return subEndArrivalNum;
	}

	public void setSubEndArrivalNum(String subEndArrivalNum) {
		this.subEndArrivalNum = subEndArrivalNum;
	}

	public String getSubstartTime() {
		return substartTime;
	}

	public void setSubstartTime(String substartTime) {
		this.substartTime = substartTime;
	}

	public String getSubendTime() {
		return subendTime;
	}

	public void setSubendTime(String subendTime) {
		this.subendTime = subendTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getImpressNum() {
		return impressNum;
	}

	public void setImpressNum(String impressNum) {
		this.impressNum = impressNum;
	}

	public String getArrivalRate() {
		return arrivalRate;
	}

	public void setArrivalRate(String arrivalRate) {
		this.arrivalRate = arrivalRate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getAds() {
		return ads;
	}

	public void setAds(String ads) {
		this.ads = ads;
	}

	public Integer getAdnum() {
		return adnum;
	}

	public void setAdnum(Integer adnum) {
		this.adnum = adnum;
	}

}
