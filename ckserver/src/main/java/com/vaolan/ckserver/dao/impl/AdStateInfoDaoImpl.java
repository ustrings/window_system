package com.vaolan.ckserver.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.hidata.framework.db.DBManager;
import com.vaolan.ckserver.dao.AdStateInfoDao;
import com.vaolan.ckserver.model.AdStatBase;
import com.vaolan.ckserver.model.MaterialStatBase;

@Component
public class AdStateInfoDaoImpl implements AdStateInfoDao{
	@Autowired
	private DBManager db;
	@Override
	public List<AdStatBase> findAdstatBaseByAdIdAndTime(String adId,
			String currentTime, String numType) {
		String sql = "SELECT * FROM ad_stat_info WHERE ad_id = ? AND ts = ? AND num_type = ?";
		try {
			Object[] args = new Object[]{
					adId,	currentTime, numType
			};
			List<AdStatBase> list = db.queryForListObject(sql, AdStatBase.class, args);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer saveAdStatBase(AdStatBase adStatBase) {
		try {
			Integer rows = db.insertObject(adStatBase);
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<MaterialStatBase> findMaterialStatBaseByIdAndTime(
			String materialId, String currentTime, String numType) {
		String sql = "SELECT * FROM material_stat_info WHERE material_id = ? AND  ts = ? AND num_type = ?";
		try {
			Object[] args = new Object[]{
					materialId, currentTime, numType
			};
			List<MaterialStatBase> list = db.queryForListObject(sql, MaterialStatBase.class, args);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer saveMaterialStatNase(MaterialStatBase materialStatBase) {
		try {
			Integer rows = db.insertObject(materialStatBase);
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
