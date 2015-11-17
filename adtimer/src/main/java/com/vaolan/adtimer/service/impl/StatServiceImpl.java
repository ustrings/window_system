package com.vaolan.adtimer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaolan.adtimer.dao.StatDao;
import com.vaolan.adtimer.model.AdHostStatBase;
import com.vaolan.adtimer.model.AdStatBase;
import com.vaolan.adtimer.model.MaterialStatBase;
import com.vaolan.adtimer.service.StatService;

/**
 * 
 * @author chenjinzhao
 * 
 */
@Service
public class StatServiceImpl implements StatService {

	@Autowired
	StatDao statDao;

	/**
	 * 某个广告当天的pv，点击，uv，ip量等信息同步到数据库， 如果数据库没有记录，则插入一条，如果有了，则更新数量 记录有两种类型：
	 * 1、天的记录，记录当天的每个广告的pv量，点击量，uv量，ip量等信息。第二天到了则重新插入一条记录（由timer控制）
	 * 2、时辰的记录，记录当前小时的每个广告的pv量，点击量，uv量，ip量等信息。第二个时辰到了，则重新插入一条记录(由timer控制)
	 * 
	 * 记录类型是在timer里面控制的，频次也是由timer控制的。
	 * 
	 * @author chenjinzhao
	 */
	@Override
	public void processAdStatNum(AdStatBase adStatBase, boolean isHave) {

		if (isHave) {
			statDao.updateAdStatBase(adStatBase);
		} else {
			statDao.saveAdStatBase(adStatBase);
		}

	}

	@Override
	public void processMaterialStatNum(MaterialStatBase materialStatBase,
			boolean isHave) {

		if (isHave) {
			statDao.updateMaterialStatBase(materialStatBase);
		} else {
			statDao.saveMaterialStatBase(materialStatBase);

		}

	}

	@Override
	public boolean isHaveTheTimeDataAd(String ts, String adId) {
		AdStatBase adStatBase = new AdStatBase();
		adStatBase.setTs(ts);
		adStatBase.setAdId(adId);
		AdStatBase ab = statDao.getAdStatBaseByTs(adStatBase);
		if (null == ab) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean isHaveTheTimeDataMaterial(String ts, String materialId) {
		MaterialStatBase maStatBase = new MaterialStatBase();
		maStatBase.setTs(ts);
		maStatBase.setMaterial_id(materialId);
		MaterialStatBase ab = statDao.getMaterialStatBaseByTs(maStatBase);
		if (null == ab) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void processAdStatNumUpdateBatch(List<AdStatBase> adStatBaseList) {

		statDao.updateAdStatBaseBatch(adStatBaseList);
	}

	@Override
	public void processAdStatNumSaveBatch(List<AdStatBase> adStatBaseList) {
		statDao.saveAdStatBaseBatch(adStatBaseList);
	}

	@Override
	public void processMaterialStatNumUpdateBatch(
			List<MaterialStatBase> materialStatBaseList) {
		statDao.updateMaterialStatBaseBatch(materialStatBaseList);
	}

	@Override
	public void processMaterialStatNumSaveBatch(
			List<MaterialStatBase> materialStatBaseList) {
		statDao.saveMaterialStatBaseBatch(materialStatBaseList);

	}

	@Override
	public void processAdHostStatNumUpdateBatch(List<AdHostStatBase> adHostStatBaseList) {
		statDao.processAdHostStatNumUpdateBatch(adHostStatBaseList);
	}

	@Override
	public void processAdHostStatNumSaveBatch(List<AdHostStatBase> adHostStatBaseTodayList) {
		statDao.processAdHostStatNumSaveBatch(adHostStatBaseTodayList);
	}
	
	@Override
	public void deleteHostDBByadIdAndts(List<AdHostStatBase> adHostStatBaseList){
		statDao.deleteHostDBByadIdAndts(adHostStatBaseList);
	}

}
