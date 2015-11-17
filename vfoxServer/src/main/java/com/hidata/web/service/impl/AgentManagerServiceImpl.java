package com.hidata.web.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hidata.framework.util.StringUtil;
import com.hidata.web.dao.AgentManagerDao;
import com.hidata.web.dao.PagerDao;
import com.hidata.web.dto.UserDto;
import com.hidata.web.service.AgentManagerService;
import com.hidata.web.util.Pager;
import com.hidata.web.util.TimeUtil;


@Service
public class AgentManagerServiceImpl implements AgentManagerService{
	
	@Autowired
	private AgentManagerDao agentManagerdao;
	
	@Autowired
	private PagerDao pagerDao;

	@Override
	public Pager getPager(Map<String, String> map, String curPage) {
		Pager pager = null;
		StringBuffer sb_sql = new StringBuffer();
		sb_sql.append(
				"SELECT * FROM adbase.user WHERE sts = 'A' "
				+ " AND user.user_type = '2'" //用户类型，代理商管理用户
				);
		if(map != null && map.size() > 0){
			String companyName = map.get("companyName");
			if(StringUtil.isNotEmpty(companyName)){
				sb_sql.append(" AND user.company_name  LIKE '%" + companyName +"%'");
			}
			
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
			
			String telNbr = map.get("telNbr");
			if(StringUtil.isNotEmpty(telNbr)){
				sb_sql.append(" AND user.tel_nbr LIKE  '%" + telNbr + "%'");
			}
		}
		sb_sql.append(" ORDER BY user.userid DESC");
		pager = pagerDao.getPagerBySql(sb_sql.toString(), Integer.parseInt(curPage), 10, UserDto.class);
		return pager;
	}

	@Override
	public Integer addAgentUser(UserDto userDto) {
		if(userDto != null){
			userDto.setUserType("2");//代理商用户
			String date = TimeUtil.dateLongToMMHHssString(new Date().getTime());
			userDto.setStsDate(date);
			Integer id = agentManagerdao.saveAgentUser(userDto);
			if(id != null && id > 0){
				return id;
			}
		}
		return null;
	}

	@Override
	public UserDto getUserDtoById(String userId) {
		if(StringUtil.isNotEmpty(userId)){
			List<UserDto> list = agentManagerdao.qryAgentUserDtoById(userId);
			if(list != null && list.size() == 1){
				return list.get(0);
			}
		}
		return null;
	}

	@Override
	public Boolean updateAgentUser(UserDto userDto) {
		if(userDto != null){
			userDto.setUserType("2");//代理商用户
			String date = TimeUtil.dateLongToMMHHssString(new Date().getTime());
			userDto.setStsDate(date);
			Integer rows = agentManagerdao.editUserDto(userDto);
			if(rows != null){
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean deleteUserDto(String userId) {
		if(StringUtil.isNotEmpty(userId)){
			String date = TimeUtil.dateLongToMMHHssString(new Date().getTime());
			Integer rows = agentManagerdao.deleteUserDto(userId,date);
			if(rows != null && rows > 0){
				return true;
			}
		}
		return false;
	}
	
}
