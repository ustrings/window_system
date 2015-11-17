package com.hidata.ad.web.dto;

import java.io.Serializable;
import java.util.Date;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/**
 * 唯一访客投放频率 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月11日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */

@Table("ad_user_frequency")
public class AdUserFrequencyDto implements Serializable {

    private static final long serialVersionUID = 3779913323709837173L;

    @Column("ad_uf_id")
    private String adUfId;

    @Column("ad_id")
    private String adId;

    @Column("time_unit")
    private String timeUnit;

    @Column("uid_impress_num")
    private String uidImpressNum;

	@Column("create_time")
    private String createTime;

    @Column("sts")
	private String sts;
    
    @Column("uid_ip_num")
    private String uidIpNum;

	public String getUidIpNum() {
		return uidIpNum;
	}

	public void setUidIpNum(String uidIpNum) {
		this.uidIpNum = uidIpNum;
	}

	public String getAdUfId() {
		return adUfId;
	}

	public void setAdUfId(String adUfId) {
		this.adUfId = adUfId;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}

	public String getUidImpressNum() {
		return uidImpressNum;
	}

	public void setUidImpressNum(String uidImpressNum) {
		this.uidImpressNum = uidImpressNum;
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
