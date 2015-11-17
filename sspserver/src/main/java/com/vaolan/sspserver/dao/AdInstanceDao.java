package com.vaolan.sspserver.dao;

import java.util.List;

import com.vaolan.sspserver.model.AdInstance;



public interface AdInstanceDao {

	/**
	 * 查询指定渠道下 所有可投放的adInstance
	 * @param channel 渠道  ex:tanx
	 * @param List<AdInstance>
	 * */
	public abstract List<AdInstance> getInstanceByChannel(String channel);

}