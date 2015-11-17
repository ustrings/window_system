package com.vaolan.sspserver.model;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;
import com.hidata.framework.db.JDBCTemplateManager;

@Table("crowd")
public class Crowd
{
	@Column("crowd_id")
	private String crowdId;

	@Column("crowd_name")
	private String crowdName;

	@Column("crowd_desc")
	private String crowdDesc;

	@Column("create_time")
	private String createTime;

	@Column("sts")
	private String sts;

	@Column("user_id")
	private String userId;

	public String getCrowdId()
	{
		return crowdId;
	}

	public void setCrowdId(String crowdId)
	{
		this.crowdId = crowdId;
	}

	public String getCrowdName()
	{
		return crowdName;
	}

	public void setCrowdName(String crowdName)
	{
		this.crowdName = crowdName;
	}

	public String getCrowdDesc()
	{
		return crowdDesc;
	}

	public void setCrowdDesc(String crowdDesc)
	{
		this.crowdDesc = crowdDesc;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public String getSts()
	{
		return sts;
	}

	public void setSts(String sts)
	{
		this.sts = sts;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public static void main(String[] args)
	{
		Crowd cr = new Crowd();
		cr.setCrowdName("哇哈哈");
		cr.setCrowdDesc("我知道了");
		cr.setSts("A");
		cr.setUserId("12345");
		cr.setCreateTime("20130704");

		JDBCTemplateManager jm = new JDBCTemplateManager();

		jm.insertObject(cr);
	}

}
