package com.microsoft.Test_struts_jpa.biz;



import com.microsoft.Test_struts_jpa.dao.UserInfoDAO;
import com.microsoft.Test_struts_jpa.entity.Dsp_Users;

public class UserInfoBiz {
	//查询
	private UserInfoDAO userinfo = new UserInfoDAO();
	public Dsp_Users getSelectUserInfo(String username,String password){
		userinfo = new UserInfoDAO();
		Dsp_Users user = userinfo.select(username,password);
		
		return user;
	}
	//注册
	public boolean getRegitster(Dsp_Users user){
		return userinfo.regitster(user);
	}
	
}
