package com.hidata.web.dao;

import java.util.List;

import com.hidata.web.dto.AdInfoDto;

public interface AdInfoDao {
	public int insertAdInfo(AdInfoDto adInfoDto);
	public int updateAdInfo(AdInfoDto adInfoDto);
	public int changeAdInfoSts(AdInfoDto adInfoDto);
	public List<AdInfoDto> findBySql(String sql);
}
