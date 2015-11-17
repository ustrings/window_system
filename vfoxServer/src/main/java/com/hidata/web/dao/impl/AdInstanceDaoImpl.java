package com.hidata.web.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.hidata.framework.db.DBManager;
import com.hidata.web.dao.AdInstanceDao;
import com.hidata.web.dto.AdInstanceDto;
import com.hidata.web.util.TimeUtil;

@Component
public class AdInstanceDaoImpl implements AdInstanceDao{
	
	@Autowired
	private DBManager db;
	
	@Override
	public List<AdInstanceDto> getListBySts() {
		
		try {
			String date = TimeUtil.dateLongToMMHHssString(new Date().getTime());
			String sql = "SELECT * FROM ad_instance WHERE sts = 'A' AND ad_useful_type = 'N' AND end_time < '" + date + "' AND (ad_toufang_sts  = 2 OR ad_toufang_sts = 3) ";
			List<AdInstanceDto> list = db.queryForListObject(sql, AdInstanceDto.class);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer updateSts(String adIds) {
		try {
			String sql = "UPDATE ad_instance SET ad_toufang_sts = '4' WHERE ad_id IN ( " + adIds + ")";
			Integer rows = db.update(sql);
			return rows;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}
