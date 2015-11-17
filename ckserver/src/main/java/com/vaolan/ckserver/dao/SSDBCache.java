package com.vaolan.ckserver.dao;

import org.nutz.ssdb4j.spi.SSDB;

public interface SSDBCache
{
	/**
	 * 获取主SSDB
	 * @return
	 */
	public SSDB getMasterSSDB();
}
