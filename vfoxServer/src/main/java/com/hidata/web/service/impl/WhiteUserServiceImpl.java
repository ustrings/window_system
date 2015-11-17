package com.hidata.web.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hidata.framework.util.StringUtil;
import com.hidata.web.dao.PagerDao;
import com.hidata.web.dao.WhiteUserDao;
import com.hidata.web.dto.WhiteUserDto;
import com.hidata.web.service.WhiteUserService;
import com.hidata.web.util.Pager;
import com.hidata.web.util.TimeUtil;

@Service
public class WhiteUserServiceImpl implements WhiteUserService{
	
	@Autowired
	private WhiteUserDao whiteUserDao;
	@Autowired
	private PagerDao pagerDao;
	@Override
	public Pager getPager(Map<String, String> map, String curPage) {
		Pager pager = null;
		StringBuffer sb_sql = new StringBuffer();
		sb_sql.append("SELECT * FROM white_user  WHERE 1=1 ");
		if(map != null && map.size() > 0){
			String peopleId = map.get("peopleId");
			if(StringUtil.isNotEmpty(peopleId)){
				sb_sql.append(" AND user_md5_id LIKE '%" + peopleId +"%'");
			}
			
			String startTime = map.get("startTime");
			if(StringUtil.isNotEmpty(startTime)){
				sb_sql.append(" AND sts_date  >= '" + startTime + "' ");
			}
			
			String endTime = map.get("endTime");
			if(StringUtil.isNotEmpty(endTime)){
				sb_sql.append(" AND sts_date <= '" + endTime + "' ");
			}
			
		}
		sb_sql.append(" ORDER BY id DESC");
		pager = pagerDao.getPagerBySql(sb_sql.toString(), Integer.parseInt(curPage), 10, WhiteUserDto.class);
		return pager;
	}
	
	
	@Override
	public Boolean add(String userMd5Id) {
		WhiteUserDto whiteUser = new WhiteUserDto();
		String date = TimeUtil.dateLongToMMHHssString(new Date().getTime());
		if(StringUtil.isNotEmpty(userMd5Id)){
			whiteUser.setUserMd5Id(userMd5Id);
			whiteUser.setStsDate(date);
			Integer id = whiteUserDao.save(whiteUser);
			if(id != null && id > 0){
				return true;
			}
		}
		return false;
	}


	@Override
	public Boolean delete(String id) {
		if(StringUtil.isNotEmpty(id)){
			Integer rows = whiteUserDao.delete(id);
			if(rows != null && rows > 0){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public Boolean deleteAll(String ids) {
		if(StringUtil.isNotEmpty(ids)){
			ids = ids.substring(0, ids.length() - 1);
			Integer rows = whiteUserDao.deleteAll(ids);
			if(rows != null && rows > 0){
				return true;
			}
		}
		return false;
	}

}
