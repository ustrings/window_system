package com.vaolan.ckserver.dao;

import java.util.List;

import com.vaolan.ckserver.model.AdStatBase;
import com.vaolan.ckserver.model.MaterialStatBase;

/**
 * 操作统计信息表的DAO
 * @author xiaoming
 *
 */
public interface AdStateInfoDao {
	/**
	 * 根据广告ID，当前时间 ，类型 查找 统计信息
	 * @param adId
	 * @param currentDay
	 * @return
	 */
	public List<AdStatBase> findAdstatBaseByAdIdAndTime(String adId, String currentTime, String numType);
	
	/**
	 * 保存数据
	 * @param adStatBase
	 * @return
	 */
	public Integer saveAdStatBase(AdStatBase adStatBase);
	
	
	/**
	 * 物料统计
	 * @param materialId
	 * @param currentTime
	 * @param numType
	 * @return
	 */
	public List<MaterialStatBase> findMaterialStatBaseByIdAndTime(String materialId, String currentTime, String numType);
	
	/**
	 * 保存物料统计
	 * @param materialStatBase
	 * @return
	 */
	public Integer saveMaterialStatNase(MaterialStatBase materialStatBase);
	
}
