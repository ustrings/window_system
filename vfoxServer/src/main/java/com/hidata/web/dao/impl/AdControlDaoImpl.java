package com.hidata.web.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.hidata.framework.db.DBManager;
import com.hidata.web.dao.AdControlDao;
import com.hidata.web.dto.AdControlDto;

@Component
public class AdControlDaoImpl implements AdControlDao{
	
	@Autowired
	private DBManager db;
	
	@Override
	public List<AdControlDto> qryAdControl() {
		String sql = "SELECT * FROM put_control WHERE id = 1";
		try {
			List<AdControlDto> list = db.queryForListObject(sql, AdControlDto.class);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer updateAdControl(AdControlDto adControlDto) {
		String sql = "UPDATE put_control SET frequency_day = ? , spacing_min = ? , pv_total = ? , sts_date = ? WHERE id = 1";
		try {
			Object[] args = new Object[]{
					adControlDto.getFrequencyDay(), adControlDto.getSpacingMin(),
					adControlDto.getPvTotal(), adControlDto.getStsDate()
			};
			Integer rows = db.update(sql, args);
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
