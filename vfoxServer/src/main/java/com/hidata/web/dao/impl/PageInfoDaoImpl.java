package com.hidata.web.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.framework.db.DBManager;
import com.hidata.web.dao.PageInfoDao;
import com.hidata.web.dto.PageInfoDto;

@Component
public class PageInfoDaoImpl implements PageInfoDao {
	@Autowired
	private DBManager db;
	@Override
	public long queryForLong(String sql) {
		return db.queryForLong(sql);
	}
	
	@Override
	public List<PageInfoDto> findBySql(String sql) {
		return db.queryForListObject(sql, PageInfoDto.class);
	}
	
	@Override
	public List<PageInfoDto> findAll(String type) {
		String sql = "select * from page_config where type=? and level =1";
		return db.queryForListObject(sql, new Object[]{type},PageInfoDto.class);
	}

	public List<PageInfoDto> findBySqlWithParam(String sql, Object[] params) {
		return db.queryForListObject(sql, params, PageInfoDto.class);
	}

	String INSERT_SQL = "insert into page_config(page_name, page_width, page_height, sts, create_time, update_time, publish_time, parent_config_id, type, level)"
			+ " values(?,?,?,?,?,?,?,?,?,?)";
	@Override
	public int insertPageInfo(PageInfoDto pageInfoDto) {
		Object[] args = new Object[]{pageInfoDto.getPageName(), pageInfoDto.getPageWidth(),
				pageInfoDto.getPageHeight(), pageInfoDto.getSts(), pageInfoDto.getCreateTime(),
				 pageInfoDto.getUpdateTime(),  pageInfoDto.getPublishTime(),
				pageInfoDto.getParentConfigId(), pageInfoDto.getType(), pageInfoDto.getLevel()};
		return db.update(INSERT_SQL, args);
	}
	
	String UPDATE_SQL = "update page_config set page_name=?, page_width=?, page_height=?, sts=?, "
			+ " update_time=?, parent_config_id=?, type=?, level=? where id=?";
	
	@Override
	public int updatePageInfo(PageInfoDto pageInfoDto) {
		Object[] args = new Object[]{pageInfoDto.getPageName(), pageInfoDto.getPageWidth(),
				pageInfoDto.getPageHeight(), pageInfoDto.getSts(), pageInfoDto.getUpdateTime(),
				 pageInfoDto.getParentConfigId(),  pageInfoDto.getType(),
				pageInfoDto.getLevel(), pageInfoDto.getPageId()};
		return db.update(UPDATE_SQL, args);
	}
	
	String CHANGE_STAT_SQL = "update page_config set sts=?, update_time=? where id=?";
	@Override
	public int changePageInfoSts(PageInfoDto pageInfoDto) {
		Object[] args = new Object[]{pageInfoDto.getSts(), pageInfoDto.getUpdateTime(), pageInfoDto.getPageId()};
		return db.update(CHANGE_STAT_SQL, args);
	}
	
	String ENABLE_SQL = "update page_config set sts=?, update_time=? where id=?";
	@Override
	public int enablePageInfo(PageInfoDto pageInfoDto) {
		Object[] args = new Object[]{pageInfoDto.getSts(), pageInfoDto.getUpdateTime(), pageInfoDto.getPageId()};
		return db.update(ENABLE_SQL, args);
	}

	String FIRST_LEVEL_SQL = "select * from page_config where level = 1 and sts = 'A'";
	
	@Override
	public List<PageInfoDto> getFirstLevel() {
		return db.queryForListObject(FIRST_LEVEL_SQL, PageInfoDto.class);
	}

	
	String FIND_SECOND_LEVEL_SQL = "select * from page_config where sts = 'A' and parent_config_id=?";
	@Override
	public List<PageInfoDto> getSecondLevelByParentId(String parentConfigId) {
		return db.queryForListObject(FIND_SECOND_LEVEL_SQL, new Object[]{parentConfigId}, PageInfoDto.class);
	}
	
}
