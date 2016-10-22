package com.microsoft.Test_struts_jpa.biz;

import java.util.List;

import com.microsoft.Test_struts_jpa.dao.DspAdDAO;
import com.microsoft.Test_struts_jpa.entity.Dsp_Ad;
import com.microsoft.Test_struts_jpa.entity.Dsp_Domain;

public class DspAdBiz {
	
	
	//查询
	private DspAdDAO adDao = new DspAdDAO();
	public List<Dsp_Ad> getSelectAd(){
		List<Dsp_Ad> list = adDao.selectAd();
		return list;
	}
	
	//修改状态
	public boolean getUpdateStatus(int id,int push_status){
		return adDao.udpateStatus(id,push_status);
	}
	
	//查询详情
	public Dsp_Ad getSelectDetailAd(int id){
		Dsp_Ad ad = adDao.selectDetail(id);
		return ad;
	}
	//查询domain信息
	public List<Dsp_Domain> getQueryDomain(int adId){
		List<Dsp_Domain> domain = adDao.queryDomain(adId);
		return domain;
	}
	//删除
	public boolean getDeleteAd(int id){
		return adDao.delete(id);
	}
	//添加
	public boolean getInsterAd(Dsp_Ad ad,String domain){
		return adDao.insert(ad,domain);
	}
	//查询优先级 是否存在
	public List<Dsp_Ad> getSelectPrio(int prio){
		return adDao.selectPrio(prio);
	}
	//修改投放次数
	public boolean getUdpateAdTimes(int AdId){
		return adDao.udpateAdTimes(AdId);
	}
	
	
}
