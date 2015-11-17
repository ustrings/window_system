package com.vaolan.adserver.dao;

import com.vaolan.adserver.model.ImpressLog;

public interface ImpressLogDao {

	/* 
	 * 
	 */
	public abstract int save(ImpressLog log);

	public abstract int saveReturnId(ImpressLog log);
	/**
	 * 通过主键更新‘用户ID’
	 * */
	public abstract int updateUidById(ImpressLog log);

}