package com.hidata.web.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hidata.web.dto.UserDto;
import com.hidata.web.model.User;

@Component
public interface UserDao {

	/**
	 * 用户登录
	 * @param user
	 * @return 是否校验成功
	 */
	public boolean userLogin(User user);
	
	/**
	 * 添加用户
	 * @param user
	 * @return 状态
	 */
	public int addUser(User user);
	
	/**
	 * 删除用户
	 * @param user
	 * @return 状态
	 */
	public int delUser(User user);
	
	/**
	 * 获取user
	 * @param user
	 * @return user 对象
	 */
	public User getUser(User user);
	
	public User getUser(String username);
	
	/**
	 * 查询所有   代理商账户  user_type = 2
	 * @author xiaoming
	 * @return
	 */
	public List<UserDto> qryUserByUserType(String userType);

}

