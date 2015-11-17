package com.hidata.web.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.hidata.framework.db.DBManager;
import com.hidata.web.dao.AdTouFangStsDao;
import com.hidata.web.dto.AdTouFangStsDto;

@Component
public class AdTouFangStsDaoImpl implements AdTouFangStsDao{
	private static Logger logger = LoggerFactory.getLogger(AdTouFangStsDaoImpl.class);
	
	@Autowired
	private DBManager db;
	@Override
	public List<AdTouFangStsDto> qryAllSts() {
		String sql = "SELECT * FROM ad_toufang_sts ";
		try {
			List<AdTouFangStsDto> list = db.queryForListObject(sql, AdTouFangStsDto.class);
			return list;
		} catch (DataAccessException e) {
			logger.error("AdTouFangStsDaoImpl qryAllSts error",e);
			e.printStackTrace();
		}
		return null;
	}
	
}
