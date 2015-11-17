package com.hidata.ad.web.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.DBExclude;
import com.hidata.framework.annotation.db.Table;

@Table("ad_pre_throw_crowd")
public class AdPreThrowCrowd implements Serializable {

	/**
	 * 
	 */
	@DBExclude
	private static final long serialVersionUID = 2072824063427730137L;
	//任务状态
	@DBExclude
	public static final String TASK_STS_EXEC_NO="A";//待计算
	@DBExclude
	public static final String TASK_STS__EXEC_SUCCESS="B";//计算成功
    @DBExclude
    public static final String TASK_STS_EXEC_FAIL="C";//执行失败
    @DBExclude
    public static final String TASK_STS_EXEC_NOW="D";//执行中
    @DBExclude
    public static final String TASK_STS_EXEC_DEL="E";//已删除
    
	@Column("id")
	private String id;

	@Column("ad_id")
	private Integer adId;
	
	@Column("ads")
	private String ads;
	
	@Column("adnum")
	private Integer adnum;
	@Column("sts")
	private String sts;
	
	@Column("exec_start_time")
	private String execStartTime;
	@Column("exec_end_time")
	private String execEndTime;
	@Column("arrival_ads")
	private String arrivalAds;
	@Column("no_arrival_ads")
	private String noArrivalAds;
	
	public String getArrivalAds() {
		return arrivalAds;
	}

	public void setArrivalAds(String arrivalAds) {
		this.arrivalAds = arrivalAds;
	}

	public String getNoArrivalAds() {
		return noArrivalAds;
	}

	public void setNoArrivalAds(String noArrivalAds) {
		this.noArrivalAds = noArrivalAds;
	}

	public String getExecStartTime() {
		return execStartTime;
	}

	public void setExecStartTime(String execStartTime) {
		this.execStartTime = execStartTime;
	}

	public String getExecEndTime() {
		return execEndTime;
	}

	public void setExecEndTime(String execEndTime) {
		this.execEndTime = execEndTime;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getAdId() {
		return adId;
	}

	public void setAdId(Integer adId) {
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
