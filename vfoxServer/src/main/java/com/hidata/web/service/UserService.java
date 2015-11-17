package com.hidata.web.service;

import java.util.List;

import com.hidata.web.dto.UserDto;
import com.hidata.web.model.User;

public interface UserService {
	
	public boolean userLogin(User user);
	
	public int addUser(User user);
	
	public int delUser(User user);
	
	public User getUser(User user);
	
	public boolean checkUser(String username, String password);
	
	public User getUser(String username);
	
	/**
	 * @author xiaoming
	 * @param userType --- 用户类型（1，管理员用户； 2，代理商账户）
	 * @date 2014-12-23
	 * @return
	 */
	public List<UserDto> getUsersByType(String userType);
}
