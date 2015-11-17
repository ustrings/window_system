package com.hidata.ad.web.service;

import com.hidata.ad.web.model.User;

public interface UserService {
	
	public boolean userLogin(User user);
	
	public int addUser(User user);
	
	public int delUser(User user);
	
	public User getUser(User user);
	
	public boolean checkUser(String username, String password);
	
	public User getUser(String username);

	public int editUser(User user);
}
