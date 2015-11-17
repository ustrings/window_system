package com.hidata.web.dao;

import java.util.List;

import com.hidata.web.dto.PageInfoDto;

public interface PageInfoDao {
	public List<PageInfoDto> findAll(String type);
	public List<PageInfoDto> findBySqlWithParam(String sql, Object[] params);
	public int insertPageInfo(PageInfoDto pageInfoDto);
	public int updatePageInfo(PageInfoDto pageInfoDto);
	public int changePageInfoSts(PageInfoDto pageInfoDto);
	public long queryForLong(String sql);
	public  List<PageInfoDto> findBySql(String sql);
	public  List<PageInfoDto> getFirstLevel();
	public List<PageInfoDto> getSecondLevelByParentId(String parentConfigId);
	public int enablePageInfo(PageInfoDto pageInfoDto);
}
