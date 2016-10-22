package com.microsoft.Test_struts_jpa.dao;


import javax.persistence.*;


import com.microsoft.Test_struts_jpa.entity.Dsp_Users;


public class UserInfoDAO {
	//查询
	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	
	public Dsp_Users select(String username,String password){
//System.out.println("账号："+username +"密码："+password);
		em = BaseDAO.getEm();
		Dsp_Users user = null;
		try{
			String sql = "from Dsp_Users where  username =?1 and password = ?2";
			Query query = em.createQuery(sql);
			query.setParameter(1,username);
			query.setParameter(2, password);
			user = (Dsp_Users) query.getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			BaseDAO.getColse(em);
		}
		return user;
	}
	
	public boolean regitster(Dsp_Users user){
		//System.out.println("账号："+username +"密码："+password);
		
		em = BaseDAO.getEm();
		em.getTransaction().begin();
		String sql = "insert into Dsp_Users(companyid,pid,username,password,nickname,regdate,lastdate,regip,lastip,loginnum,email,mobile,islock,vip,overduedate,status) values(default,default,?,?,?,?,default,?,?,default,?,?,default,default,default,default)";
		
		Query query = em.createNativeQuery(sql);
		query.setParameter(1, user.getUsername());
		query.setParameter(2, user.getPassword());
		query.setParameter(3, user.getNickname());
		query.setParameter(4, user.getRegdate());
		query.setParameter(5, user.getRegip());
		query.setParameter(6, user.getLastip());
		query.setParameter(7, user.getEmail());
		query.setParameter(8, user.getMobile());
		
		int i = query.executeUpdate();
		
		em.getTransaction().commit();
		BaseDAO.getColse(em);
		
		if(i > 0){
			return true;
		}
		return false;
		
	}
	
}
