package com.hidata.web.dao;

import java.util.List;

import com.hidata.web.dto.UserDto;

/**
 * 账号管理DAO
 * @author xiaoming
 * @date2014-12-29
 */
public interface AgentManagerDao {
	
	/**
	 * 保存代理商用户
	 * @param userDto
	 * @return
	 */
	public Integer saveAgentUser(UserDto userDto);
	
	/**
	 * 根据主键ID查询代理商用户
	 * @param userId
	 * @return
	 */
	public List<UserDto> qryAgentUserDtoById(String userId);
	
	/**
	 * 编辑代理商用户
	 * @param userDto
	 * @return
	 */
	public Integer editUserDto(UserDto userDto);
	
	/**
	 * 删除用户
	 * @param userId
	 * @return
	 */
	public Integer deleteUserDto(String userId, String date);
}
