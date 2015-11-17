package com.hidata.web.dao;

import java.util.List;

import com.hidata.web.dto.WebSiteInfoDto;

public interface WebSiteInfoDao {
	public List<WebSiteInfoDto> findAll();
	public List<WebSiteInfoDto> findBySqlWithParam(String sql, Object[] params);
	public int insertPageInfo(WebSiteInfoDto webSiteInfoDto);
	public int updatePageInfo(WebSiteInfoDto webSiteInfoDto);
	public int changePageInfoSts(WebSiteInfoDto webSiteInfoDto);
	public long queryForLong(String sql);
	public <T> List<T> findBySql(String sql,Class<T> classType);
}
