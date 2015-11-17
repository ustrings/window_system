package com.hidata.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.web.dao.UserDao;
import com.hidata.web.dto.UserDto;
import com.hidata.web.model.User;
import com.hidata.web.service.UserService;

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
	
	/**
	 * @author xiaoming
	 * date 2014-12-23
	 */
	@Override
	public List<UserDto> getUsersByType(String userType) {
		
		return userDao.qryUserByUserType(userType);
	}

}
