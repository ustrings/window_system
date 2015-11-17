package com.hidata.ad.web.dao;

import org.springframework.stereotype.Component;

import com.hidata.ad.web.model.User;

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
	 * 查询用户
	 * @param userId
	 * @return
	 */
	public User getUserById(int userId);

	public int editUser(User user);

}

