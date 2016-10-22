package com.microsoft.Test_struts_jpa.dao;


import java.util.List;

import javax.persistence.*;

import com.microsoft.Test_struts_jpa.entity.Dsp_Ad;
import com.microsoft.Test_struts_jpa.entity.Dsp_Domain;




public class DspDomainDAO {
	//域名统计查询
	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	public List<Dsp_Domain> selectDomain(){
		em = BaseDAO.getEm(); 
		List<Dsp_Domain> list = null;
		String sql = "from Dsp_Domain";
		Query query = em.createQuery(sql);
		list = query.getResultList();
		BaseDAO.getColse(em);
		return list;
	}
	//修改投放次数
	public boolean udpateDomainTimes(int DomainId){
		em = BaseDAO.getEm();
		em.getTransaction().begin();
		String sql = "update Dsp_Domain set push_num = push_num + 1 where id = ?";
		Query query = em.createQuery(sql);
		query.setParameter(1, DomainId);
		int i = query.executeUpdate();
		em.getTransaction().commit();
		BaseDAO.getColse(em);
		if(i > 0){
			return true;
		}
		return true;
	}
	
	
}
