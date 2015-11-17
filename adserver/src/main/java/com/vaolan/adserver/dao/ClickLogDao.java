package com.vaolan.adserver.dao;

import com.vaolan.adserver.model.ImpressLog;

public interface ClickLogDao {

	public abstract int save(ImpressLog log);

}