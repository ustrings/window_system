package com.vaolan.sspserver.model;

public enum AdvExchangeEnum
{
	TANX(1, "tanx");
	
	//类型
	private int type;
	
	//名称
	private String name;
	
	private AdvExchangeEnum(int type, String name)
	{
		this.type = type;
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public int getType()
	{
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type)
	{
		this.type = type;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}
