package com.hidata.web.service;

import java.util.List;

import com.hidata.web.dto.AdInfoDto;

public interface AdInfoService {
	public int insertAdInfo(AdInfoDto adInfoDto);
	public int updateAdInfo(AdInfoDto adInfoDto);
	public int changeAdInfoSts(AdInfoDto adInfoDto);
	public List<AdInfoDto> findBySql(String sql);
}
