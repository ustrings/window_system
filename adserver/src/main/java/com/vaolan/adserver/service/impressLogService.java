package com.vaolan.adserver.service;


import com.vaolan.adserver.model.ImpressLog;

public interface impressLogService {

	/**
	 * 记录点击日志
	 * */
	public abstract int saveClickLog(ImpressLog log);

}