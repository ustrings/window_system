package com.microsoft.Test_struts_jpa.dao;


import java.util.List;

import javax.persistence.*;

import com.microsoft.Test_struts_jpa.entity.Dsp_Ad;
import com.microsoft.Test_struts_jpa.entity.Dsp_Domain;




public class DspAdDAO {
	//查询
	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	public List<Dsp_Ad> selectAd(){
		em = BaseDAO.getEm(); 
		List<Dsp_Ad> list = null;
		String sql = "from Dsp_Ad";
		Query query = em.createQuery(sql);
		list = query.getResultList();
		BaseDAO.getColse(em);
		return list;
	}
	
	//修改推送状态为 
	public boolean udpateStatus(int id,int push_status){
		em = BaseDAO.getEm();
		em.getTransaction().begin();
		String sql = null;
		if(push_status == 0){
			sql = "update Dsp_Ad set push_status = 1 where id = ?1 and push_status = ?2";
		}else if(push_status == 1){
			sql = "update Dsp_Ad set push_status = 0 where id = ?1 and push_status = ?2";
		}else{
			return false;
		}
		
		Query query = em.createQuery(sql);
		query.setParameter(1, id);
		query.setParameter(2, push_status);
		int i = query.executeUpdate();
		em.getTransaction().commit();
		BaseDAO.getColse(em);
		if(i > 0){
			return true;
		}
		return false;
	}
	
	//根据ID查询详情
	public Dsp_Ad selectDetail(int id){
		em = BaseDAO.getEm();
		Dsp_Ad ad = null;
		try{
			String sql = "from Dsp_Ad where  id =?1 ";
			Query query = em.createQuery(sql);
			query.setParameter(1,id);
			ad = (Dsp_Ad) query.getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			BaseDAO.getColse(em);
		}
		return ad;
	}
	//根据adId 查询Dsp_Domain表中的domain信息
	public List<Dsp_Domain> queryDomain(int adId){
		em = BaseDAO.getEm(); 
		List<Dsp_Domain> domain = null;
		String sql = "from Dsp_Domain where ad_id=?1";
		Query query = em.createQuery(sql);
		query.setParameter(1,adId);
		domain = query.getResultList();
		BaseDAO.getColse(em);
		return domain;
	}
	
	//删除ad表中信息（先删除domain表中的信息，再删除ad表中信息）
	public boolean deleteDomain(int adId){
		em = BaseDAO.getEm();
		em.getTransaction().begin();
		String sql = "delete from Dsp_Domain where ad_id = ?1";
		Query query = em.createQuery(sql);
		query.setParameter(1, adId);
		int i = query.executeUpdate();
		em.getTransaction().commit();
		BaseDAO.getColse(em);
		if(i > 0){
			return true;
		}
		
		return false;
	}
	public boolean delete(int id){
		deleteDomain(id);
		em = BaseDAO.getEm();
		em.getTransaction().begin();
		String sql = "delete from Dsp_Ad where id = ?1";
		Query query = em.createQuery(sql);
		query.setParameter(1, id);
		int i = query.executeUpdate();
		em.getTransaction().commit();
		BaseDAO.getColse(em);
		if(i > 0){
			return true;
		}
		
		return false;
	}
	//添加（先添加ad表，在添加domain表）
	public boolean insert(Dsp_Ad ad,String domain){
		em = BaseDAO.getEm();
		em.getTransaction().begin();
		String sql = "insert into Dsp_Ad(name,prio,push_status,push_method,begin_time,end_time,set_num,url,push_num) values(?,?,default,?,?,?,?,?,default)";
		Query query = em.createNativeQuery(sql);
		query.setParameter(1, ad.getName());
		query.setParameter(2, ad.getPrio());
		query.setParameter(3, ad.getPush_method());
		query.setParameter(4, ad.getBegin_time());
		query.setParameter(5, ad.getEnd_time());
		query.setParameter(6, ad.getSet_num());
		query.setParameter(7, ad.getUrl());
		int i = query.executeUpdate();
		
		em.getTransaction().commit();
		BaseDAO.getColse(em);
		
		Boolean b = insertDomain(ad,domain);
		if(i > 0  && b == true){
			return true;
		}
		return false;
	}
	public int queryAdId(Dsp_Ad ad){
		em = BaseDAO.getEm();
		Dsp_Ad dspad = null;
		String sql = "from Dsp_Ad where name = ?1 and prio = ?2 and push_method =?3 and begin_time = ?4 and end_time = ?5 and set_num = ?6 and url = ?7";
		Query query = em.createQuery(sql);
		query.setParameter(1,ad.getName());
		query.setParameter(2, ad.getPrio());
		query.setParameter(3, ad.getPush_method());
		query.setParameter(4, ad.getBegin_time());
		query.setParameter(5, ad.getEnd_time());
		query.setParameter(6, ad.getSet_num());
		query.setParameter(7, ad.getUrl());
		dspad = (Dsp_Ad) query.getSingleResult();
		BaseDAO.getColse(em);
		
		return dspad.getId();
	}
	public boolean insertDomain(Dsp_Ad ad,String domain){
		String[]arr=domain.split("\r\n"); 
		
		int adId = queryAdId(ad);
		em = BaseDAO.getEm();
		em.getTransaction().begin();
		int num = 0;
		for(int a =0;a<arr.length;a++){
			String sqlDomain = "insert into Dsp_Domain(ad_id,domain,push_num) values(?,?,default)";
			Query queryDomain = em.createNativeQuery(sqlDomain);
			queryDomain.setParameter(1, adId);
			queryDomain.setParameter(2, arr[a]);
			int i = queryDomain.executeUpdate();
			if(i > 0 ){
				num++;
			}
		}
		
		em.getTransaction().commit();
		BaseDAO.getColse(em);
		if(num == arr.length ){
			return true;
		}
		return false;
	}
	//查询ad表中优先级 是否存在
	public List<Dsp_Ad> selectPrio(int prio){
		em = BaseDAO.getEm(); 
		List<Dsp_Ad> list = null;
		String sql = "from Dsp_Ad where prio = ?1";
		Query query = em.createQuery(sql);
		query.setParameter(1,prio);
		list = query.getResultList();
		BaseDAO.getColse(em);
		return list;
	}
	//修改投放次数
	public boolean udpateAdTimes(int AdId){
		em = BaseDAO.getEm();
		em.getTransaction().begin();
		String sql = "update Dsp_Ad set push_num = push_num + 1 where id = ?";
		Query query = em.createQuery(sql);
		query.setParameter(1, AdId);
		int i = query.executeUpdate();
		em.getTransaction().commit();
		BaseDAO.getColse(em);
		if(i > 0){
			return true;
		}
		return true;
	}
	
}
