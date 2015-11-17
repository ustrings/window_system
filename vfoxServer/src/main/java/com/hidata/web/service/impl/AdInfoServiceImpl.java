package com.hidata.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.web.dao.AdInfoDao;
import com.hidata.web.dto.AdInfoDto;
import com.hidata.web.service.AdInfoService;

@Component
public class AdInfoServiceImpl implements AdInfoService {
	@Autowired
	private AdInfoDao adInfoDao;

	@Override
	public int insertAdInfo(AdInfoDto adInfoDto) {
		return adInfoDao.insertAdInfo(adInfoDto);
	}
	
	@Override
	public int updateAdInfo(AdInfoDto adInfoDto) {
		return adInfoDao.updateAdInfo(adInfoDto);
	}
	
	@Override
	public int changeAdInfoSts(AdInfoDto adInfoDto) {
		return adInfoDao.changeAdInfoSts(adInfoDto);
	}

	@Override
	public List<AdInfoDto> findBySql(String sql) {
		return adInfoDao.findBySql(sql);
	}
	
}
