package com.vaolan.ckserver.model;


/**
 * 重定向用户信息
 * @author fushengli
 *
 */
public class RedirectUserInfo
{
	//用户是否激活的
	private boolean isActive;
	
	//用户映射时间
	private String mappingTime;
	
	//人群Id
	private String crowId;

	/**
	 * 构造函数
	 */
	public RedirectUserInfo()
	{
	}
	
	/**
	 * 构造函数
	 */
	public RedirectUserInfo(String crowId)
	{
		//this.mappingTime = DateUtil.getCurrentDateTimeStr();
		this.mappingTime = String.valueOf(System.currentTimeMillis());
		this.isActive = true;
		this.crowId = crowId;
	}
	
	/**
	 * @return the isActive
	 */
	public boolean isActive()
	{
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive)
	{
		this.isActive = isActive;
	}

	/**
	 * @return the mappingTime
	 */
	public String getMappingTime()
	{
		return mappingTime;
	}

	/**
	 * @param mappingTime the mappingTime to set
	 */
	public void setMappingTime(String mappingTime)
	{
		this.mappingTime = mappingTime;
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
}
