package com.hidata.web.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hidata.framework.util.StringUtil;
import com.hidata.web.dao.AgentManagerDao;
import com.hidata.web.dao.PagerDao;
import com.hidata.web.dto.UserDto;
import com.hidata.web.service.ManagerService;
import com.hidata.web.util.Pager;
import com.hidata.web.util.TimeUtil;

@Service
public class ManagerServiceImpl implements ManagerService{
	
	@Autowired
	private PagerDao pagerDao;
	
	@Autowired
	private AgentManagerDao agentManagerDao;

	@Override
	public Pager getPager(Map<String, String> map, String curPage) {
		Pager pager = null;
		StringBuffer sb_sql = new StringBuffer();
		sb_sql.append(
				"SELECT * FROM adbase.user WHERE sts = 'A' "
				+ " AND user.user_type = '1'" //用户类型，代理商管理用户
				);
		if(map != null && map.size() > 0){
			
			String userName = map.get("userName");
			if(StringUtil.isNotEmpty(userName)){
				sb_sql.append(" AND user.username LIKE '%" + userName +"%'");
			}
			
			String startTime = map.get("startTime");
			if(StringUtil.isNotEmpty(startTime)){
				sb_sql.append("  AND user.sts_date  >= '" + startTime + "' ");
			}
			
			String endTime = map.get("endTime");
			if(StringUtil.isNotEmpty(endTime)){
				sb_sql.append(" AND user.sts_date <= '" + endTime + "' ");
			}
			
		}
		sb_sql.append(" ORDER BY user.userid DESC");
		pager = pagerDao.getPagerBySql(sb_sql.toString(), Integer.parseInt(curPage), 10, UserDto.class);
		return pager;
	}

	@Override
	public Boolean addManagerUserDto(UserDto userDto) {
		if(userDto != null){
			userDto.setUserType("1");//管理员账户
			String date = TimeUtil.dateLongToMMHHssString(new Date().getTime());
			userDto.setStsDate(date);
			Integer rows = agentManagerDao.saveAgentUser(userDto);
			if(rows != null){
				return true;
			}
		}
		return false;

	}

	@Override
	public Boolean deleteManagerUserDto(String userId) {
		if(StringUtil.isNotEmpty(userId)){
			String date = TimeUtil.dateLongToMMHHssString(new Date().getTime());
			Integer rows = agentManagerDao.deleteUserDto(userId, date);
			if(rows != null && rows > 0){
				return true;
			}
		}
		return false;
	}
}
