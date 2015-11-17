package com.vaolan.ckserver.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hidata.framework.db.DBManager;
import com.vaolan.ckserver.dao.CookieMappingDao;
import com.vaolan.ckserver.model.CookieMapModel;
import com.vaolan.ckserver.model.CookieUser;

@Repository
public class CookieMappingDaoImpl implements CookieMappingDao {

	@Autowired
	private DBManager db;

	@Override
	public void saveCookieInfo(CookieUser cookieUser) {
		db.insertObject(cookieUser);

	}

	@Override
	public void saveCookieMappingInfo(CookieMapModel cookieMapModel) {
		db.insertObject(cookieMapModel);
	}

	@Override
	public List<CookieMapModel> getVdspCookieInfobyAdxCid(
			CookieMapModel cookieMapModel) {

		String sql = "select id,adx_cid,vdsp_cid,adx_vendor,create_time,sts from ad_cookie_mapping where adx_cid = ?";
		Object[] args = new Object[] { cookieMapModel.getAdxCid() };

		List<CookieMapModel> cmmList = db.queryForListObject(sql, args,
				CookieMapModel.class);
		return cmmList;
	}

	@Override
	public List<CookieMapModel> getAdxCookieInfobyVdspCid(
			CookieMapModel cookieMapModel) {

		String sql = "select id,adx_cid,vdsp_cid,adx_vendor,create_time,sts from ad_cookie_mapping where vdsp_cid = ?";
		Object[] args = new Object[] { cookieMapModel.getVdspCid() };

		List<CookieMapModel> cmmList = db.queryForListObject(sql, args,
				CookieMapModel.class);
		return cmmList;

	}

	@Override
	public void updateCookieMappingRelSts(CookieMapModel cookieMapModel) {

		String sql = "update ad_cookie_mapping set sts =? where id = ? ";
		Object[] args = new Object[] { cookieMapModel.getSts(),cookieMapModel.getId() };

		db.update(sql, args);
	}

}
