package com.hidata.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.web.dao.WebSiteInfoDao;
import com.hidata.web.dto.WebSiteInfoDto;
import com.hidata.web.service.WebSiteInfoService;

@Component
public class WebSiteInfoServiceImpl implements WebSiteInfoService {
	@Autowired
	private WebSiteInfoDao webSiteInfoDao;

	@Override
	public List<WebSiteInfoDto> findAll() {
		return webSiteInfoDao.findAll();
	}

	@Override
	public List<WebSiteInfoDto> findBySqlWithParam(String sql, Object[] params) {
		return webSiteInfoDao.findBySqlWithParam(sql, params);
	}

	@Override
	public int insertPageInfo(WebSiteInfoDto webSiteInfoDto) {
		return webSiteInfoDao.insertPageInfo(webSiteInfoDto);
	}

	@Override
	public int updatePageInfo(WebSiteInfoDto webSiteInfoDto) {
		return webSiteInfoDao.updatePageInfo(webSiteInfoDto);
	}

	@Override
	public int changePageInfoSts(WebSiteInfoDto webSiteInfoDto) {
		return webSiteInfoDao.changePageInfoSts(webSiteInfoDto);
	}

	@Override
	public <T> List<T> findBySql(String sql, Class<T> classType) {
		return webSiteInfoDao.findBySql(sql, classType);
	}
}
