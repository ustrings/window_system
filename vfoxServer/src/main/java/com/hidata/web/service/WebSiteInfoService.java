package com.hidata.web.service;

import java.util.List;
import java.util.Map;

import com.hidata.web.dto.WebSiteInfoDto;

public interface WebSiteInfoService {
	public List<WebSiteInfoDto> findAll();
	public List<WebSiteInfoDto> findBySqlWithParam(String sql, Object[] params);
	public int insertPageInfo(WebSiteInfoDto webSiteInfoDto);
	public int updatePageInfo(WebSiteInfoDto webSiteInfoDto);
	public int changePageInfoSts(WebSiteInfoDto webSiteInfoDto);
	public <T> List<T> findBySql(String string, Class<T> classType);
}
