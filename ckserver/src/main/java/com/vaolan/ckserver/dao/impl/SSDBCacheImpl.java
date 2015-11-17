package com.vaolan.ckserver.dao.impl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.nutz.ssdb4j.spi.SSDB;
import org.springframework.stereotype.Component;

import com.hidata.framework.ssdb.SSDBRegister;
import com.vaolan.ckserver.dao.SSDBCache;

@Component
public class SSDBCacheImpl implements SSDBCache
{
	@Resource(name = "SSDBRegister")
	private SSDBRegister ssdbRegister;
	
	//ssdb
	private SSDB ssdb;
	
	@PostConstruct
	public void initMasterSSDB()
	{
		ssdb = ssdbRegister.init();
	}
	
	@Override
	public SSDB getMasterSSDB()
	{
		if (null == ssdb)
		{
			ssdb = ssdbRegister.init();
		}
		
		return ssdb;
	}
}
