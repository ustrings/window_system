package com.microsoft.Test_struts_jpa.dao;

import javax.persistence.*;


public class BaseDAO {
	public static EntityManagerFactory emf = null;
	static{
		emf = Persistence.createEntityManagerFactory("mdos");
	}
	public static EntityManager getEm(){
		EntityManager em = emf.createEntityManager();
		return em;
	}
	public static void getColse(EntityManager em){
		em.close();
	}
}
