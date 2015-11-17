package com.vaolan.adtimer.service;

import java.util.List;

import com.vaolan.adtimer.model.AdHostStatBase;
import com.vaolan.adtimer.model.AdStatBase;
import com.vaolan.adtimer.model.MaterialStatBase;

/**
 * 把redis中的统计信息同步到mysql的服务类
 * @author chenjinzhao
 *
 */
public interface StatService {
	
	/**
	 * 同步广告统计信息到数据库，isHave 为true 则update，如果false 则新插入
	 * @param adStatBase
	 * @param isHave
	 */
	public void processAdStatNum(AdStatBase adStatBase,boolean isHave);
	
	public void processAdStatNumUpdateBatch(List<AdStatBase> adStatBaseList);

	
	public void processAdStatNumSaveBatch(List<AdStatBase> adStatBaseList);

	/**
	 * 同步物料统计信息到数据库，isHave 为true 则update，如果false 则新插入
	 * @param adStatBase
	 * @param isHave
	 */
	public void processMaterialStatNum(MaterialStatBase materialStatBase,boolean isHave);
	
	public void processMaterialStatNumUpdateBatch(List<MaterialStatBase> materialStatBaseList);
	
	public void processMaterialStatNumSaveBatch(List<MaterialStatBase> MaterialStatBaseList);


	boolean isHaveTheTimeDataAd(String ts, String adId);
	
	boolean isHaveTheTimeDataMaterial(String ts, String materialId);
	
	
	/**
	 * 更新数据库和插入数据库; 以域名为维度的统计信息
	 */
	public void processAdHostStatNumUpdateBatch(List<AdHostStatBase> adHostStatBaseList);
	
	public void processAdHostStatNumSaveBatch(List<AdHostStatBase> adHostStatBaseList);
	
	/**
	 * 根据广告ID和时间删除 所有数据
	 */
	public void deleteHostDBByadIdAndts(List<AdHostStatBase> adHostStatBaseList);

}
