package com.hidata.ad.web.model;

import java.util.Date;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

@Table("ad_material")
public class AdMaterial {
	
	@Column("ad_m_id")
	private int id;
	
	@Column("m_type")
	private int MType;
	
	@Column("material_name")
	private String materialName;
	
	@Column("material_size")
	private int materialSize;
	
	@Column("link_url")
	private String linkUrl;
	
	@Column("target_url")
	private String targetUrl;
	
	@Column("create_time")
	private Date createTime;
	
	@Column("material_desc")
	private String materialDesc;
	
	@Column("third_monitor")
	private int thirdMonitor;
	
	@Column("monitor_link")
	private String monitorLink;
	
	@Column("rich_text")
	private String richText;
	
	@Column("userid")
	private int userid;
	
	@Column("sts")
	private String sts;
	
	//字段添加： 周晓明 (图片规格值)
	@Column("material_size_value")
	private String materialValue;
	
	@Column("check_status")
	private String checkStatus;
	
	@Column("cover_flag")
	private String coverFlag;
	
	@Column("useful_type")
	private String usefulType;
	
	public String getCoverFlag() {
		return coverFlag;
	}

	public void setCoverFlag(String coverFlag) {
		this.coverFlag = coverFlag;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	@Column("material_type")
	private Integer materialType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMType() {
		return MType;
	}

	public void setMType(int mType) {
		MType = mType;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public int getMaterialSize() {
		return materialSize;
	}

	public void setMaterialSize(int materialSize) {
		this.materialSize = materialSize;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public int getThirdMonitor() {
		return thirdMonitor;
	}

	public void setThirdMonitor(int thirdMonitor) {
		this.thirdMonitor = thirdMonitor;
	}

	public String getMonitorLink() {
		return monitorLink;
	}

	public void setMonitorLink(String monitorLink) {
		this.monitorLink = monitorLink;
	}

	public String getRichText() {
		return richText;
	}

	public void setRichText(String richText) {
		this.richText = richText;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}



	public Integer getMaterialType() {
		return materialType;
	}

	public void setMaterialType(Integer materialType) {
		this.materialType = materialType;
	}

	public String getMaterialValue() {
		return materialValue;
	}

	public void setMaterialValue(String materialValue) {
		this.materialValue = materialValue;
	}

	public String getUsefulType() {
		return usefulType;
	}

	public void setUsefulType(String usefulType) {
		this.usefulType = usefulType;
	}
	
	
}
