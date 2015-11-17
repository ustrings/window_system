package com.vaolan.ckserver.dao;

import java.util.List;

import com.vaolan.ckserver.model.AdInstance;


public interface AdInstanceDao {

	/**
	 * 查询在投的广告列表
	 * @return
	 * @throws Exception
	 */
	public List<AdInstance> queryAdInstanceListUsing() throws Exception;

	
}