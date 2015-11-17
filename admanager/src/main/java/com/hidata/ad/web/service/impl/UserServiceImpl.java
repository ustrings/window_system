package com.hidata.ad.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.ad.web.dao.UserDao;
import com.hidata.ad.web.model.User;
import com.hidata.ad.web.service.UserService;

@Component
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	public boolean userLogin(User user) {
		return userDao.userLogin(user);
	}

	public int addUser(User user) {
		return userDao.addUser(user);
	}

	public int delUser(User user) {
		return userDao.delUser(user);
	}
	@Override
	public int editUser(User user) {
		return userDao.editUser(user);
	}
	public User getUser(User user) {
		return userDao.getUser(user);
	}

	public boolean checkUser(String username, String password) {
		User user = new User();
		user.setUserName(username);
		user.setPassWord(password);
		return userDao.userLogin(user);
	}

	public User getUser(String username) {
		return userDao.getUser(username);
	}

}
