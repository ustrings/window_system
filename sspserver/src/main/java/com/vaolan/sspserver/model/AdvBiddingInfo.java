package com.vaolan.sspserver.model;

/**
 * 用来存储广告竞价请求所需信息
 * @author fushengli
 *
 */
public class AdvBiddingInfo
{
	//广告ID
	private String advId;
	
	//人群定向
	private String crowId;
	
	//是否盲投
	private boolean isBlideBid = true;
	
	//物料标识
	private int materialId;

	/**
	 * @return the advId
	 */
	public String getAdvId()
	{
		return advId;
	}

	/**
	 * @param advId the advId to set
	 */
	public void setAdvId(String advId)
	{
		this.advId = advId;
	}

	/**
	 * @return the crowId
	 */
	public String getCrowId()
	{
		return crowId;
	}

	/**
	 * @param crowId the crowId to set
	 */
	public void setCrowId(String crowId)
	{
		this.crowId = crowId;
	}

	/**
	 * @return the isBlideBid
	 */
	public boolean isBlideBid()
	{
		return isBlideBid;
	}

	/**
	 * @param isBlideBid the isBlideBid to set
	 */
	public void setBlideBid(boolean isBlideBid)
	{
		this.isBlideBid = isBlideBid;
	}

	/**
	 * @return the materialId
	 */
	public int getMaterialId()
	{
		return materialId;
	}

	/**
	 * @param materialId the materialId to set
	 */
	public void setMaterialId(int materialId)
	{
		this.materialId = materialId;
	}
}
