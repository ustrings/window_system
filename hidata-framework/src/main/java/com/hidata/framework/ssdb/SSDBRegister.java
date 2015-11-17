package com.hidata.framework.ssdb;

import org.apache.commons.pool.impl.GenericObjectPool.Config;
import org.nutz.ssdb4j.SSDBs;
import org.nutz.ssdb4j.spi.SSDB;

/**
 * SSDB用于存储人群信息
 * @author fushengli
 *
 */
public class SSDBRegister
{
	private String ipAddress;
	
	private int port;
	
	private int timeout; 
	
	private int maxActive;
	
	private int maxIdle;
	
	private int maxWait;
	
	public SSDBRegister()
	{
	}
	
	/**
	 * 初始化SSDB
	 * @return
	 */
	public SSDB init()
	{
		Config config = new Config();
		config.maxActive = this.maxActive;
		config.maxIdle = this.maxIdle;
		config.maxWait = this.maxWait;
		return SSDBs.pool(this.ipAddress, this.port, this.timeout, config);
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress()
	{
		return ipAddress;
	}

	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress)
	{
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the port
	 */
	public int getPort()
	{
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port)
	{
		this.port = port;
	}

	/**
	 * @return the timeout
	 */
	public int getTimeout()
	{
		return timeout;
	}

	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(int timeout)
	{
		this.timeout = timeout;
	}

	/**
	 * @return the maxActive
	 */
	public int getMaxActive()
	{
		return maxActive;
	}

	/**
	 * @param maxActive the maxActive to set
	 */
	public void setMaxActive(int maxActive)
	{
		this.maxActive = maxActive;
	}

	/**
	 * @return the maxIdle
	 */
	public int getMaxIdle()
	{
		return maxIdle;
	}

	/**
	 * @param maxIdle the maxIdle to set
	 */
	public void setMaxIdle(int maxIdle)
	{
		this.maxIdle = maxIdle;
	}

	/**
	 * @return the maxWait
	 */
	public int getMaxWait()
	{
		return maxWait;
	}

	/**
	 * @param maxWait the maxWait to set
	 */
	public void setMaxWait(int maxWait)
	{
		this.maxWait = maxWait;
	}
}
