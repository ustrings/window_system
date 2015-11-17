package com.hidata.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.web.dao.PageInfoDao;
import com.hidata.web.dto.PageInfoDto;
import com.hidata.web.service.PageInfoService;

@Component
public class PageInfoServiceImpl implements PageInfoService {
	@Autowired
	private PageInfoDao pageInfoDao;
	
	@Override
	public List<PageInfoDto> findAll(String type) {
		return pageInfoDao.findAll(type);
	}
	
	@Override
	public List<PageInfoDto> findBySqlWithParam(String sql, Object[] params) {
		return pageInfoDao.findBySqlWithParam(sql, params);
	}

	@Override
	public int insertPageInfo(PageInfoDto pageInfoDto) {
		return pageInfoDao.insertPageInfo(pageInfoDto);
	}
	
	@Override
	public int updatePageInfo(PageInfoDto pageInfoDto) {
		return pageInfoDao.updatePageInfo(pageInfoDto);
	}
	
	@Override
	public int changePageInfoSts(PageInfoDto pageInfoDto) {
		return pageInfoDao.changePageInfoSts(pageInfoDto);
	}

	@Override
	public long queryForLong(String sql) {
		return pageInfoDao.queryForLong(sql);
	}
	
	@Override
	public List<PageInfoDto> findBySql(String sql) {
		return pageInfoDao.findBySql(sql);
	}

	@Override
	public List<PageInfoDto> getFirstLevel() {
		return pageInfoDao.getFirstLevel();
	}

	@Override
	public List<PageInfoDto> getSecondLevelByParentId(String parentConfigId) {
		return pageInfoDao.getSecondLevelByParentId(parentConfigId);
	}
}
