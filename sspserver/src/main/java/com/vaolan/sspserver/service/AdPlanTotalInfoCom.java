package com.vaolan.sspserver.service;

import java.util.Map;
import java.util.Set;

import com.vaolan.sspserver.model.AdvPlan;


public interface AdPlanTotalInfoCom {

	/**
	 * 获取系统所有广告相关信息
	 * */
	public void getAdvPlans(Map<String,AdvPlan> advPlanList,String channel);
	
	/**
	 * 获取系统所有广告相关关键词信息
	 * */
	public void getAdKeywordsvPlans(Map<String, Set<String>> keywordsMap,String channel);

}