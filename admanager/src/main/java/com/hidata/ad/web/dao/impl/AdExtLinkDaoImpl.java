package com.hidata.ad.web.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.hidata.ad.web.dao.AdExtLinkDao;
import com.hidata.ad.web.dto.AdExtLinkDto;
import com.hidata.framework.db.DBManager;

@Component
public class AdExtLinkDaoImpl implements AdExtLinkDao {
	
	@Autowired
	private DBManager db;
	@Override
	public List<AdExtLinkDto> qryAdExtLinkByAdId(String adId) {
		String sql = "SELECT * FROM ad_ext_link WHERE ad_instance_id = " + adId;
		try {
			List<AdExtLinkDto> list = db.queryForListObject(sql, AdExtLinkDto.class);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public Integer deleteAdExtLinkByAdId(String adId) {
		String sql = "DELETE FROM ad_ext_link WHERE ad_instance_id = " + adId;
		try {
			Integer rows = db.update(sql);
			return rows;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}
