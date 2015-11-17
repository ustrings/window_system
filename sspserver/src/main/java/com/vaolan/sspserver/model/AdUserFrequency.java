package com.vaolan.sspserver.model;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;
/**
 * ip 和 ua 控制 <br>
 * Description: <br>
 * Date: 2014年6月17日 <br>
 * Copyright (c) 2014 <br>
 * 
 * @author ztd
 */
@Table("ad_user_frequency")
public class AdUserFrequency {

	@Column("ad_uf_id")
	private String adUfId;
	
	@Column("uid_impress_num")
	private String uidImpressNum;
	
	@Column("sts")
	private String sts;
	
	@Column("ad_id")
	private int adId;
	
	@Column("uid_ip_num")
	private String uidIpNum;

	public String getAdUfId() {
		return adUfId;
	}

	public void setAdUfId(String adUfId) {
		this.adUfId = adUfId;
	}

	/**
	 * 当前广告的用户的访问量限制数
	 * @return
	 */
	

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public int getAdId() {
		return adId;
	}

	public void setAdId(int adId) {
		this.adId = adId;
	}

	public String getUidImpressNum() {
		return uidImpressNum;
	}

	public void setUidImpressNum(String uidImpressNum) {
		this.uidImpressNum = uidImpressNum;
	}

	public String getUidIpNum() {
		return uidIpNum;
	}

	public void setUidIpNum(String uidIpNum) {
		this.uidIpNum = uidIpNum;
	}

}
