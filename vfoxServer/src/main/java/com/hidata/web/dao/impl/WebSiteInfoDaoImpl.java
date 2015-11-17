package com.hidata.web.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.framework.db.DBManager;
import com.hidata.web.dao.WebSiteInfoDao;
import com.hidata.web.dto.WebSiteInfoDto;

@Component
public class WebSiteInfoDaoImpl implements WebSiteInfoDao {
	@Autowired
	private DBManager db;
	@Override
	public long queryForLong(String sql) {
		return db.queryForLong(sql);
	}
	
	@Override
	public <T> List<T> findBySql(String sql,Class<T> classType) {
		return db.queryForListObject(sql, classType);
	}
	
	@Override
	public List<WebSiteInfoDto> findAll() {
		String sql = "select * from web_site_info";
		return db.queryForListObject(sql, WebSiteInfoDto.class);
	}

	public List<WebSiteInfoDto> findBySqlWithParam(String sql, Object[] params) {
		return db.queryForListObject(sql, params, WebSiteInfoDto.class);
	}

	String INSERT_SQL = "insert into web_site_info("
			+ "page_config_id, web_site_name, page_config_id_two, web_site_imge_url, priority, "
			+ "web_site_desc, create_time, update_time, sts, publish_time, host, web_site_name2, host2"
			+ ")"
			+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
	@Override
	public int insertPageInfo(WebSiteInfoDto webSiteInfoDto) {
		Object[] args = new Object[]{
				webSiteInfoDto.getPageConfigId(), webSiteInfoDto.getWebSiteName(), webSiteInfoDto.getPageConfigIdTwo(),
				webSiteInfoDto.getWebSiteImgeUrl(), webSiteInfoDto.getPriority(), webSiteInfoDto.getWebSiteDesc(),
				 webSiteInfoDto.getCreateTime(), webSiteInfoDto.getUpdateTime(), webSiteInfoDto.getSts(),
				webSiteInfoDto.getPublishTime(), webSiteInfoDto.getHost(), webSiteInfoDto.getWebSiteName2(),
				webSiteInfoDto.getHost2()};
		return db.update(INSERT_SQL, args);
	}
	
	String UPDATE_SQL = "update web_site_info set"
			+ " page_config_id=?, web_site_name=?, page_config_id_two=?, web_site_imge_url=?, priority=?, "
			+ " web_site_desc=?, update_time=?, sts=?, host=?, web_site_name2=?, host2=?"
			+ " where id=?";
	
	@Override
	public int updatePageInfo(WebSiteInfoDto webSiteInfoDto) {
		Object[] args = new Object[]{
				webSiteInfoDto.getPageConfigId(), webSiteInfoDto.getWebSiteName(), webSiteInfoDto.getPageConfigIdTwo(),
				webSiteInfoDto.getWebSiteImgeUrl(), webSiteInfoDto.getPriority(), webSiteInfoDto.getWebSiteDesc(),
				webSiteInfoDto.getUpdateTime(), webSiteInfoDto.getSts(),
				webSiteInfoDto.getHost(), webSiteInfoDto.getWebSiteName2(),
				webSiteInfoDto.getHost2(), webSiteInfoDto.getId()};
		return db.update(UPDATE_SQL, args);
	}
	
	String CHANGE_STATE_SQL = "update web_site_info set sts=?, update_time=? where id=?";
	@Override
	public int changePageInfoSts(WebSiteInfoDto webSiteInfoDto) {
		Object[] args = new Object[]{webSiteInfoDto.getSts(), webSiteInfoDto.getUpdateTime(), webSiteInfoDto.getId()};
		return db.update(CHANGE_STATE_SQL, args);
	}
	
}
