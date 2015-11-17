package com.vaolan.adtimer.dao;

import java.util.List;

import com.vaolan.adtimer.model.AdHostStatBase;
import com.vaolan.adtimer.model.AdStatBase;
import com.vaolan.adtimer.model.MaterialStatBase;

public interface StatDao {

	
	/**
	 * 
	 * @param adStatBase
	 */
	public void saveAdStatBase(AdStatBase adStatBase);
	
	
	/**
	 * 批量新增
	 * @param adStatBaseList
	 */
	public void saveAdStatBaseBatch(List<AdStatBase> adStatBaseList);


	public AdStatBase getAdStatBase(AdStatBase adStatBase);

	
	
	public void updateAdStatBase(AdStatBase adStatBase);
	
	/**
	 * 批量更新广告统计
	 * @param adStatBaseList
	 */
	public void updateAdStatBaseBatch(List<AdStatBase> adStatBaseList);

	
	
	public void saveMaterialStatBase(MaterialStatBase materialStatBase);
	
	/**
	 * 批量新增物料统计
	 * @param materialStatBaseList
	 */
	public void saveMaterialStatBaseBatch(List<MaterialStatBase> materialStatBaseList);


	public MaterialStatBase getMaterialStatBase(MaterialStatBase materialStatBase);

	public void updateMaterialStatBase(MaterialStatBase materialStatBase);
	
	
	/**
	 * 批量更新物料统计
	 * @param materialStatBaseList
	 */
	public void updateMaterialStatBaseBatch(List<MaterialStatBase> materialStatBaseList );
	
	public AdStatBase getAdStatBaseByTs(AdStatBase adStatBase);
	
	public MaterialStatBase getMaterialStatBaseByTs(MaterialStatBase maStatBase);
	
	/**
	 * 更新 广告信息 维度 域名
	 * @param list
	 * @author xiaoming
	 */
	public void processAdHostStatNumUpdateBatch(List<AdHostStatBase> list);
	
	/**
	 * 插入 广告信息 维度 域名
	 * @param list
	 */
	public void processAdHostStatNumSaveBatch(List<AdHostStatBase> list);
	
	/**
	 * 根据广告ID和时间删除数据
	 * @param list
	 */
	public void deleteHostDBByadIdAndts(List<AdHostStatBase> list);


}
