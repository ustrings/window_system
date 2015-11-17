package com.vaolan.sspserver.dao;

import java.util.List;

import com.vaolan.sspserver.model.AdDeviceLinkDto;
import com.vaolan.sspserver.model.AdInstance;



public interface AdDeviceDao {

	/**
	 * 查询指定渠道下 所有可投放的adInstance
	 * @param channel 渠道  ex:tanx
	 * @param List<AdInstance>
	 * */
	public  List<AdDeviceLinkDto> getAdDeviceLinks(String adId);

}