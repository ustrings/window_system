package com.hidata.web.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.hidata.framework.db.DBManager;
import com.hidata.web.dao.WhiteDomainDao;
import com.hidata.web.dto.WhiteDomainDto;

@Component
public class WhiteDomainDaoImpl implements WhiteDomainDao{
	private static Logger logger = LoggerFactory.getLogger(WhiteDomainDaoImpl.class);
	
	@Autowired
	private DBManager db;

	@Override
	public Integer save(WhiteDomainDto whiteDomain) {
		try {
			Integer id = db.insertObjectAndGetAutoIncreaseId(whiteDomain);
			return id;
		} catch (Exception e) {
			logger.error("WhiteDomainDaoImpl save error",e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<WhiteDomainDto> getObjectByPkId(String pkId) {
		String sql = "SELECT * FROM white_domain WHERE id = ?";
		try {
			Object[] args = new Object[]{
					pkId
			};
			List<WhiteDomainDto> list = db.queryForListObject(sql, WhiteDomainDto.class, args);
			return list;
		} catch (DataAccessException e) {
			logger.error("WhiteDomainDaoImpl getObjectByPkId error",e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer update(WhiteDomainDto whiteDomain) {
		String sql = "UPDATE white_domain SET domain_name = ?, domain_url = ?, sts_date = ? WHERE id = ?";
		try {
			Object[] args = new Object[]{
					whiteDomain.getDomainName(),whiteDomain.getDomainUrl(),
					whiteDomain.getStsDate(),whiteDomain.getId()
			};
			Integer rows = db.update(sql, args);
			return rows;
		} catch (Exception e) {
			logger.error("WhiteDomainDaoImpl update error",e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer delete(String pkId) {
		String sql = "DELETE FROM white_domain WHERE id = ?";
		try {
			Object[] args = new Object[]{
					pkId
			};
			Integer rows = db.update(sql, args);
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer deleteAll(String domainIds) {
		String sql = "DELETE FROM white_domain WHERE id IN ( "+ domainIds +" )";
		try {
			Integer rows = db.update(sql);
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
