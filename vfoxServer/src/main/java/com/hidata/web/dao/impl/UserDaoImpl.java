package com.hidata.web.dao.impl;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.hidata.framework.db.DBManager;
import com.hidata.web.dao.UserDao;
import com.hidata.web.dto.UserDto;
import com.hidata.web.model.User;

@Component
public class UserDaoImpl implements UserDao{
	private static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	@Autowired
	private DBManager db;
	
	public boolean userLogin(User user) {
		String sql = "select userid from user where username=? and password=?";
		Object[] args = new Object[]{user.getUserName(),user.getPassWord()};
		List<Map<String,Object>> result = db.queryForList(sql, args);
		if(null != result && result.size()>0){
			return true;
		}
		return false;
	}

	public int addUser(User user) {
		String sql = "insert into user (username,password) values (?,?,?)";
		Object[] args = new Object[]{user.getUserName(), user.getPassWord()};
		return db.update(sql,args);
	}

	public int delUser(User user) {
		String sql = "delete from user where userid=?";
		Object[] args = new Object[]{user.getUserId()};
		return db.update(sql, args);
	}

	public User getUser(User user) {
		User returnUser = new User();
		String sql = "select userid,username password from user where userid=?";
		Object[] args = new Object[]{user.getUserId()};
		List<Map<String,Object>> result = db.queryForList(sql, args);
		if(null != result && result.size()>0){
			Map<String,Object> oneResult = result.get(0);
			user.setUserName(oneResult.get("username") == null ? "" :oneResult.get("username").toString() );
			user.setPassWord(oneResult.get("password") == null ? "" : oneResult.get("password").toString());
		}
		return returnUser;
	}

	public User getUser(String username) {
		User user = new User();
		String sql = "select userid,username,user_type,sts from user where username=?";
		Object[] args = new Object[]{username};
		List<Map<String,Object>> result = db.queryForList(sql, args);
		if(null != result && result.size()>0){
			Map<String,Object> oneResult = result.get(0);
			user.setUserName(oneResult.get("username") == null ? "" :oneResult.get("username").toString() );
			user.setUserId(oneResult.get("userid") == null ? 0 : Integer.valueOf(oneResult.get("userid").toString()));
		    user.setType(oneResult.get("user_type") == null ? "" :oneResult.get("user_type").toString() );
		    user.setSts(oneResult.get("sts") == null ? "" : oneResult.get("sts").toString() );
		}
		return user;
	}

	@Override
	public List<UserDto> qryUserByUserType(String userType) {
		String sql = "SELECT * FROM adbase.user WHERE user_type = ? AND sts = 'A' ";
		try {
			Object[] args = new Object[]{
					userType
			};
			List<UserDto> list = db.queryForListObject(sql, UserDto.class,args);
			return list;
		} catch (DataAccessException e) {
			logger.error("UserDaoImpl,qryUserByUserType error", e);
			e.printStackTrace();
		}
		return null;
	}

}
