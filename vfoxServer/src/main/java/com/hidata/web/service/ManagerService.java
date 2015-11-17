package com.hidata.web.service;

import java.util.Map;

import com.hidata.web.dto.UserDto;
import com.hidata.web.util.Pager;

/**
 * 管理员账户Service
 * @author xiaoming
 * @date 2014-12-29
 */
public interface ManagerService {
	
	/**
	 * 获取表格信息
	 * @param map
	 * @param curPage
	 * @return
	 */
	public Pager getPager(Map<String,String> map, String curPage);
	
	/**
	 * 添加账户管理员
	 * @param userDto
	 * @return
	 */
	public Boolean addManagerUserDto(UserDto userDto);
	
	/**
	 * 删除管理员用户
	 * @param userId
	 * @return
	 */
	public Boolean deleteManagerUserDto(String userId);
}
