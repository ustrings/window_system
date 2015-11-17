package com.vaolan.sspserver.model;

import java.io.Serializable;
import java.util.Date;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

@Table("ad_material")
public class AdMaterial implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9086328762686251184L;

	@Column("ad_m_id")
	private String id;

	@Column("m_type")
	private int MType;

	@Column("material_name")
	private String materialName;

	@Column("material_size_value")
	private String materialSizeValue;

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

	@Column("check_status")
	private String checkStatus;

	@Column("rich_text")
	private String richText;

	@Column("userid")
	private int userid;

	@Column("sts")
	private String sts;

	public String getSts()
	{
		return sts;
	}

	public void setSts(String sts)
	{
		this.sts = sts;
	}

	@Column("material_type")
	private Integer materialType;

	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	public int getMType()
	{
		return MType;
	}

	public void setMType(int mType)
	{
		MType = mType;
	}

	public String getMaterialName()
	{
		return materialName;
	}

	public void setMaterialName(String materialName)
	{
		this.materialName = materialName;
	}

	/**
	 * @return the materialSizeValue
	 */
	public String getMaterialSizeValue()
	{
		return materialSizeValue;
	}

	/**
	 * @param materialSizeValue the materialSizeValue to set
	 */
	public void setMaterialSizeValue(String materialSizeValue)
	{
		this.materialSizeValue = materialSizeValue;
	}

	public String getLinkUrl()
	{
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl)
	{
		this.linkUrl = linkUrl;
	}

	public String getTargetUrl()
	{
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl)
	{
		this.targetUrl = targetUrl;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public String getMaterialDesc()
	{
		return materialDesc;
	}

	public void setMaterialDesc(String materialDesc)
	{
		this.materialDesc = materialDesc;
	}

	public int getThirdMonitor()
	{
		return thirdMonitor;
	}

	public void setThirdMonitor(int thirdMonitor)
	{
		this.thirdMonitor = thirdMonitor;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getRichText()
	{
		return richText;
	}

	public void setRichText(String richText)
	{
		this.richText = richText;
	}

	public int getUserid()
	{
		return userid;
	}

	public void setUserid(int userid)
	{
		this.userid = userid;
	}

	public Integer getMaterialType()
	{
		return materialType;
	}

	public void setMaterialType(Integer materialType)
	{
		this.materialType = materialType;
	}
}
