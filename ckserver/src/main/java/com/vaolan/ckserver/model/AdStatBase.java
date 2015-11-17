package com.vaolan.ckserver.model;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;



/**
 * 一个广告的所有统计信息
 * @author chenjinzhao
 *
 */
@Table("ad_stat_info")
public class AdStatBase {
	
	
	@Column("asi_id")
	private String asiId;
	//广告id
	@Column("ad_id")
	private String adId;
	
	@Column("pv_num")
	private String pvNum;
	
	@Column("click_num")
	private String clickNum;
	
	@Column("uv_num")
	private String uvNum;
	
	@Column("ip_num")
	private String ipNum;
	
	@Column("mobile_pv_num")
	private String mobilePvNum;
	
	@Column("mobile_click_num")
	private String mobileClickNum;
	
	@Column("ts")
	private String ts;
	
	@Column("num_type")
	private String numType;
	
	@Column("close_num")
	private String closeNum;
	

	public String getCloseNum() {
		return closeNum;
	}

	public void setCloseNum(String closeNum) {
		this.closeNum = closeNum;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getPvNum() {
		return pvNum;
	}

	public void setPvNum(String pvNum) {
		this.pvNum = pvNum;
	}

	public String getClickNum() {
		return clickNum;
	}

	public void setClickNum(String clickNum) {
		this.clickNum = clickNum;
	}

	public String getUvNum() {
		return uvNum;
	}

	public void setUvNum(String uvNum) {
		this.uvNum = uvNum;
	}

	public String getIpNum() {
		return ipNum;
	}

	public void setIpNum(String ipNum) {
		this.ipNum = ipNum;
	}

	public String getMobilePvNum() {
		return mobilePvNum;
	}

	public void setMobilePvNum(String mobilePvNum) {
		this.mobilePvNum = mobilePvNum;
	}

	public String getMobileClickNum() {
		return mobileClickNum;
	}

	public void setMobileClickNum(String mobileClickNum) {
		this.mobileClickNum = mobileClickNum;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getNumType() {
		return numType;
	}

	public void setNumType(String numType) {
		this.numType = numType;
	}
	
	public String getAsiId() {
		return asiId;
	}

	public void setAsiId(String asiId) {
		this.asiId = asiId;
	}

	
	
}
