package com.hidata.web.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.hidata.framework.db.DBManager;
import com.hidata.web.dao.AgentManagerDao;
import com.hidata.web.dto.UserDto;

@Component
public class AgentManagerDaoImpl implements AgentManagerDao{
	
	@Autowired
	private DBManager db;
	
	private Logger logger = LoggerFactory.getLogger(AgentManagerDaoImpl.class);

	@Override
	public Integer saveAgentUser(UserDto userDto) {
		try {
			Integer id = db.insertObjectAndGetAutoIncreaseId(userDto);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AgentManagerDaoImpl saveAgentUser error",e);
		}
		return null;
	}

	@Override
	public List<UserDto> qryAgentUserDtoById(String userId) {
		String sql = "SELECT * FROM adbase.user WHERE userid = ? "
				+ "AND user_type = '2'  "
				;
		try {
			Object[] args = new Object[]{
				userId	
			};
			List<UserDto> list = db.queryForListObject(sql, args, UserDto.class);
			return list;
		} catch (DataAccessException e) {
			logger.error("AgentManagerDaoImpl qryAgentUserDtoById error",e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer editUserDto(UserDto userDto) {
		String sql = "UPDATE adbase.user SET user.username = ? , "
				+ "user.password = ?, user.company_name = ?, "
				+ "user.company_leader = ? , user.tel_nbr = ? , "
				+ "user.id_card_path = ?, user.yingye_card_path = ?,"
				+ " user.tax_card_path = ? , user.org_card_path = ?, "
				+ "user.sts_date = ? WHERE user.userid = ?";
		try {
			Object[] args = new Object[]{
					userDto.getUserName(),userDto.getPassWord(),
					userDto.getCompanyName(), userDto.getCompanyLeader(),
					userDto.getTelNbr(),userDto.getIdCardPath(),
					userDto.getYingyeCardPath(), userDto.getTaxCardPath(),
					userDto.getOrgCardPath(), userDto.getStsDate(),
					userDto.getUserId()
			};
			Integer rows = db.update(sql, args);
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AgentManagerDaoImpl editUserDto error",e);
		}
		return null;
	}

	@Override
	public Integer deleteUserDto(String userId, String date) {
		String sql = "UPDATE adbase.user SET user.sts = 'D', user.sts_date = ? WHERE user.userid = ?";
		try {
			Object[] args = new Object[]{
					date,userId
			};
			Integer rows = db.update(sql, args);
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
