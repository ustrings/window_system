package com.hidata.web.service;

import java.util.Map;

import com.hidata.web.dto.UserDto;
import com.hidata.web.util.Pager;

/**
 * 操作账号管理的Service
 * @author xiaoming
 * @date 2014-12-29
 */
public interface AgentManagerService {
	
	/**
	 * 获取前台列表
	 * @param map
	 * @param curPage
	 * @return
	 */
	public Pager getPager(Map<String,String> map, String curPage);
	
	/**
	 * 添加代理商用户
	 * @param userDto
	 * @return
	 */
	public Integer addAgentUser(UserDto userDto);
	
	/**
	 * 根据代理商用户ID查找用户
	 * @param userId
	 * @return
	 */
	public UserDto getUserDtoById(String userId);
	
	/**
	 * 编辑用户
	 * @param userDto
	 * @return
	 */
	public Boolean updateAgentUser(UserDto userDto);
	
	/**
	 * 删除用户
	 * @param userId
	 * @return
	 */
	public Boolean deleteUserDto(String userId);
}
