package com.vaolan.adtimer.model;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;



/**
 * 一个广告的所有统计信息
 * @author chenjinzhao
 *
 */
@Table("material_stat_info")
public class MaterialStatBase {
	
	
	@Column("msi_id")
	private String msiId;
	//广告id
	@Column("material_id")
	private String Material_id;
	
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


	public String getMsiId() {
		return msiId;
	}

	public void setMsiId(String msiId) {
		this.msiId = msiId;
	}

	public String getMaterial_id() {
		return Material_id;
	}

	public void setMaterial_id(String material_id) {
		Material_id = material_id;
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
	
	
}
