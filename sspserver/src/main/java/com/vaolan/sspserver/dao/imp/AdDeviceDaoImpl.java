package com.vaolan.sspserver.dao.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hidata.framework.db.DBManager;
import com.vaolan.sspserver.dao.AdDeviceDao;
import com.vaolan.sspserver.model.AdDeviceLinkDto;

@Repository
public class AdDeviceDaoImpl implements AdDeviceDao {

	@Autowired
	private DBManager db;

	@Override
	public List<AdDeviceLinkDto> getAdDeviceLinks(String adId) {
		String sql = "select ad_id,ad_device_id  from ad_device_link where ad_id = ?";
		Object[] args = new Object[] { adId };
		return db.queryForListObject(sql, args, AdDeviceLinkDto.class);
	}
}
