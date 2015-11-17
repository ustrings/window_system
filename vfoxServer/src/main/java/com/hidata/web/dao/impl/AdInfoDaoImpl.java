package com.hidata.web.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.framework.db.DBManager;
import com.hidata.web.dao.AdInfoDao;
import com.hidata.web.dto.AdInfoDto;

@Component
public class AdInfoDaoImpl implements AdInfoDao {
	@Autowired
	private DBManager db;

	String INSERT_SQL = "insert into page_config_ad_rel(page_config_id, sts, url, targeturl, title)"
			+ " values(?, ?, ?, ?, ?)";
	
	@Override
	public int insertAdInfo(AdInfoDto adInfoDto) {
		Object[] args = new Object[]{
				adInfoDto.getPageConfigId(), adInfoDto.getSts(),
				adInfoDto.getUrl(), adInfoDto.getTargeturl(), adInfoDto.getTitle()};
		return db.update(INSERT_SQL, args);
	}
	
	String UPDATE_SQL = "update page_config_ad_rel set page_config_id=?, sts=?, url=?, targeturl=?, title=? where id=?";
	@Override
	public int updateAdInfo(AdInfoDto adInfoDto) {
		Object[] args = new Object[]{
				adInfoDto.getPageConfigId(), adInfoDto.getSts(),adInfoDto.getUrl(), 
				adInfoDto.getTargeturl(), adInfoDto.getTitle(), adInfoDto.getId()};
		return db.update(UPDATE_SQL, args);
	}
	
	String CHANGE_STS_SQL = "update page_config_ad_rel set sts=? where id=?";
	@Override
	public int changeAdInfoSts(AdInfoDto adInfoDto) {
		Object[] args = new Object[]{adInfoDto.getSts(), adInfoDto.getId()};
		return db.update(CHANGE_STS_SQL, args);
	}
	@Override
	public List<AdInfoDto> findBySql(String sql) {
		return db.queryForListObject(sql, AdInfoDto.class);
	}
	
}
