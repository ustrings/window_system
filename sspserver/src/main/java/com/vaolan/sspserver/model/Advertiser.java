package com.vaolan.sspserver.model;

import java.util.List;

/**
 * 广告主信息
 * @author fushengli
 *
 */
public class Advertiser
{
	//广告
	private List<AdvPlan> advs;
	
	//账户额度
	private int count;
	
	//用户权重
	private int priority;

	public List<AdvPlan> getAdvs()
	{
		return advs;
	}

	public void setAdvs(List<AdvPlan> advs)
	{
		this.advs = advs;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public int getPriority()
	{
		return priority;
	}

	public void setPriority(int priority)
	{
		this.priority = priority;
	}
	
}
