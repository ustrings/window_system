package com.hidata.ad.web.model;

import com.hidata.framework.annotation.db.Column;

public class MaterialStatInfo {
	@Column("material_name")
	private String materialName;
	@Column("msi_id")
	private String msiId;
	@Column("material_id")
	private String materialId;
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
	@Column("click_rate")
	private double clickRate;
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getMsiId() {
		return msiId;
	}
	public void setMsiId(String msiId) {
		this.msiId = msiId;
	}
	public String getMaterialId() {
		return materialId;
	}
	public void setMaterialId(String materialId) {
		this.materialId = materialId;
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
	public double getClickRate() {
		return clickRate;
	}
	public void setClickRate(double clickRate) {
		this.clickRate = clickRate;
	}
	public MaterialStatInfo(String materialName, String msiId,
			String materialId, String pvNum, String clickNum, String uvNum,
			String ipNum, String mobilePvNum, String mobileClickNum,
			double clickRate) {
		super();
		this.materialName = materialName;
		this.msiId = msiId;
		this.materialId = materialId;
		this.pvNum = pvNum;
		this.clickNum = clickNum;
		this.uvNum = uvNum;
		this.ipNum = ipNum;
		this.mobilePvNum = mobilePvNum;
		this.mobileClickNum = mobileClickNum;
		this.clickRate = clickRate;
	}
	public MaterialStatInfo() {
		super();
	}
}
