package com.vaolan.adtimer.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hidata.framework.db.DBManager;
import com.vaolan.adtimer.dao.StatDao;
import com.vaolan.adtimer.model.AdHostStatBase;
import com.vaolan.adtimer.model.AdStatBase;
import com.vaolan.adtimer.model.MaterialStatBase;

@Repository
public class StatDaoImpl implements StatDao {

	@Autowired
	DBManager db;

	@Override
	public void saveAdStatBase(AdStatBase adStatBase) {
		db.insertObject(adStatBase);
	}

	@Override
	public AdStatBase getAdStatBase(AdStatBase adStatBase) {
		AdStatBase adStatRet = null;

		List<AdStatBase> adStatList;

		String sql = "select asi_id, ad_id,pv_num,click_num,uv_num,"
				+ "ip_num,mobile_pv_num,mobile_click_num,ts,num_type from ad_stat_info where ad_id=? and ts=? and num_type =? ";
		Object[] args = new Object[] { adStatBase.getAdId(),
				adStatBase.getTs(), adStatBase.getNumType() };

		adStatList = db.queryForListObject(sql, args, AdStatBase.class);
		if (adStatList.size() == 0) {
			adStatRet = null;
		} else {
			adStatRet = adStatList.get(0);
		}

		return adStatRet;
	}

	@Override
	public void updateAdStatBase(AdStatBase adStatBase) {

		String sql = "update ad_stat_info set pv_num = ?,click_num = ?,uv_num = ?,"
				+ "ip_num = ?,mobile_pv_num = ?,mobile_click_num = ?  where  ad_id = ? and ts = ? and num_type = ?";
		Object[] args = new Object[] { adStatBase.getPvNum(),
				adStatBase.getClickNum(), adStatBase.getUvNum(),
				adStatBase.getIpNum(), adStatBase.getMobilePvNum(),
				adStatBase.getMobileClickNum(), adStatBase.getAdId(),
				adStatBase.getTs(), adStatBase.getNumType() };

		db.update(sql, args);

	}

	@Override
	public void saveMaterialStatBase(MaterialStatBase materialStatBase) {
		db.insertObject(materialStatBase);
	}

	@Override
	public MaterialStatBase getMaterialStatBase(
			MaterialStatBase materialStatBase) {
		MaterialStatBase materialStatRet = null;

		List<MaterialStatBase> materialStatList;

		String sql = "select msi_id, material_id,pv_num,click_num,uv_num,"
				+ "ip_num,mobile_pv_num,mobile_click_num,ts,num_type from material_stat_info where material_id=? and ts=? and num_type =? ";
		Object[] args = new Object[] { materialStatBase.getMaterial_id(),
				materialStatBase.getTs(), materialStatBase.getNumType() };

		materialStatList = db.queryForListObject(sql, args,
				MaterialStatBase.class);
		if (materialStatList.size() == 0) {
			materialStatRet = null;
		} else {
			materialStatRet = materialStatList.get(0);
		}

		return materialStatRet;
	}

	@Override
	public void updateMaterialStatBase(MaterialStatBase materialStatBase) {
		String sql = "update material_stat_info set pv_num = ?,click_num = ?,uv_num = ?,"
				+ "ip_num = ?,mobile_pv_num = ?,mobile_click_num = ?  where  material_id = ? and ts=? and num_type =?";
		Object[] args = new Object[] { materialStatBase.getPvNum(),
				materialStatBase.getClickNum(), materialStatBase.getUvNum(),
				materialStatBase.getIpNum(), materialStatBase.getMobilePvNum(),
				materialStatBase.getMobileClickNum(),
				materialStatBase.getMaterial_id(), materialStatBase.getTs(),
				materialStatBase.getNumType() };

		db.update(sql, args);

	}

	@Override
	public AdStatBase getAdStatBaseByTs(AdStatBase adStatBase) {
		AdStatBase adStatRet = null;

		List<AdStatBase> adStatList;

		String sql = "select asi_id, ad_id,pv_num,click_num,uv_num,"
				+ "ip_num,mobile_pv_num,mobile_click_num,ts,num_type from ad_stat_info where ad_id=? and ts=? limit 0 ,1 ";
		Object[] args = new Object[] { adStatBase.getAdId(), adStatBase.getTs() };

		adStatList = db.queryForListObject(sql, args, AdStatBase.class);
		if (adStatList.size() == 0) {
			adStatRet = null;
		} else {
			adStatRet = adStatList.get(0);
		}

		return adStatRet;
	}

	@Override
	public MaterialStatBase getMaterialStatBaseByTs(MaterialStatBase maStatBase) {

		MaterialStatBase maStatRet = null;

		List<MaterialStatBase> maStatList;

		String sql = "select msi_id, material_id,pv_num,click_num,uv_num,"
				+ "ip_num,mobile_pv_num,mobile_click_num,ts,num_type from material_stat_info where material_id=? and ts=? limit 0,1 ";

		Object[] args = new Object[] { maStatBase.getMaterial_id(),
				maStatBase.getTs() };

		maStatList = db.queryForListObject(sql, args, MaterialStatBase.class);
		if (maStatList.size() == 0) {
			maStatRet = null;
		} else {
			maStatRet = maStatList.get(0);
		}

		return maStatRet;

	}

	@Override
	public void updateAdStatBaseBatch(List<AdStatBase> adStatBaseList) {
		/*String sql = "update ad_stat_info set pv_num = ?,click_num = ?,uv_num = ?,"
				+ "ip_num = ?,mobile_pv_num = ?,mobile_click_num = ? where  ad_id = ? and ts = ? and num_type = ?";*/
		
		String sql = "update ad_stat_info set pv_num = ?,click_num = ?,uv_num = ?,"
				+ "ip_num = ?,mobile_pv_num = ?,mobile_click_num = ?, close_num = ? where  ad_id = ? and ts = ? and num_type = ?";

		List<Object[]> argsList = new ArrayList<Object[]>();

		for (AdStatBase adStatBase : adStatBaseList) {
			Object[] args = new Object[] { adStatBase.getPvNum(),
					adStatBase.getClickNum(), adStatBase.getUvNum(),
					adStatBase.getIpNum(), adStatBase.getMobilePvNum(),
					adStatBase.getMobileClickNum(), 
					adStatBase.getCloseNum(),
					adStatBase.getAdId(),
					adStatBase.getTs(), adStatBase.getNumType() };

			argsList.add(args);
		}
		db.batchUpdate(sql, argsList);
	}

	@Override
	public void updateMaterialStatBaseBatch(
			List<MaterialStatBase> materialStatBaseList) {

		String sql = "update material_stat_info set pv_num = ?,click_num = ?,uv_num = ?,"
				+ "ip_num = ?,mobile_pv_num = ?,mobile_click_num = ?  where  material_id = ? and ts=? and num_type =?";

		List<Object[]> argsList = new ArrayList<Object[]>();

		for (MaterialStatBase materialStatBase : materialStatBaseList) {

			Object[] args = new Object[] { materialStatBase.getPvNum(),
					materialStatBase.getClickNum(),
					materialStatBase.getUvNum(), materialStatBase.getIpNum(),
					materialStatBase.getMobilePvNum(),
					materialStatBase.getMobileClickNum(),
					materialStatBase.getMaterial_id(),
					materialStatBase.getTs(), materialStatBase.getNumType() };

			argsList.add(args);
		}

		db.batchUpdate(sql, argsList);

	}

	@Override
	public void saveAdStatBaseBatch(List<AdStatBase> adStatBaseList) {
		String sql = "insert into ad_stat_info (ad_id,pv_num,click_num,uv_num,ip_num,mobile_pv_num,mobile_click_num,ts,num_type,close_num) values(?,?,?,?,?,?,?,?,?,?) ";

		List<Object[]> argsList = new ArrayList<Object[]>();

		for (AdStatBase adStatBase : adStatBaseList) {
			Object[] args = new Object[] { adStatBase.getAdId(),
					adStatBase.getPvNum(), adStatBase.getClickNum(),
					adStatBase.getUvNum(), adStatBase.getIpNum(),
					adStatBase.getMobilePvNum(),
					adStatBase.getMobileClickNum(), adStatBase.getTs(),
					adStatBase.getNumType(), adStatBase.getCloseNum() };

			argsList.add(args);
		}
		db.batchUpdate(sql, argsList);
	}

	@Override
	public void saveMaterialStatBaseBatch(
			List<MaterialStatBase> materialStatBaseList) {

		String sql = "insert into material_stat_info (material_id,pv_num,click_num,uv_num,ip_num,mobile_pv_num,mobile_click_num,ts,num_type) values(?,?,?,?,?,?,?,?,?) ";

		List<Object[]> argsList = new ArrayList<Object[]>();

		for (MaterialStatBase materialStatBase : materialStatBaseList) {

			Object[] args = new Object[] { materialStatBase.getMaterial_id(),
					materialStatBase.getPvNum(),
					materialStatBase.getClickNum(),
					materialStatBase.getUvNum(), materialStatBase.getIpNum(),
					materialStatBase.getMobilePvNum(),
					materialStatBase.getMobileClickNum(),
					materialStatBase.getTs(), materialStatBase.getNumType() };

			argsList.add(args);
		}

		db.batchUpdate(sql, argsList);

	}

	@Override
	public void processAdHostStatNumUpdateBatch(List<AdHostStatBase> list) {
		try {
			String sql = "update ad_host set pv_num = ?,click_num = ?,uv_num = ?, ip_num = ? where  ad_id = ? and ts = ? and url_host = ?";

			List<Object[]> argsList = new ArrayList<Object[]>();

			for (AdHostStatBase adHostStatBase : list) {
				Object[] args = new Object[] {
						adHostStatBase.getDayPv_host(),
						adHostStatBase.getDayClick_host(),
						adHostStatBase.getDayUv_host(),
						adHostStatBase.getDayIp_host(),
						adHostStatBase.getAdId_key(),
						adHostStatBase.getTs(),
						adHostStatBase.getHost_key()
				};

				argsList.add(args);
			}
			db.batchUpdate(sql, argsList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void processAdHostStatNumSaveBatch(List<AdHostStatBase> list) {
		String sql = "insert into ad_host (ad_id,url_host,pv_num,click_num,uv_num,ip_num,ts) values(?,?,?,?,?,?,?) ";

		List<Object[]> argsList = new ArrayList<Object[]>();

		for (AdHostStatBase adHostStatBase : list) {
			Object[] args = new Object[] { 
					adHostStatBase.getAdId_key(),
					adHostStatBase.getHost_key(),
					adHostStatBase.getDayPv_host(),
					adHostStatBase.getDayClick_host(),
					adHostStatBase.getDayUv_host(),
					adHostStatBase.getDayIp_host(),
					adHostStatBase.getTs()
			};

			argsList.add(args);
		}
		db.batchUpdate(sql, argsList);
	}

	@Override
	public void deleteHostDBByadIdAndts(List<AdHostStatBase> list) {
		String sql = "DELETE FROM ad_host WHERE ad_id = ? AND ts = ?";
		List<Object[]> argsList = new ArrayList<Object[]>();
		for(AdHostStatBase adHostStatBase : list){
			Object[] args = new Object[] { 
					adHostStatBase.getAdId_key(),
					adHostStatBase.getTs()
			};
			argsList.add(args);
		}
		db.batchUpdate(sql, argsList);
	}

}
