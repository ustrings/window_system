package com.hidata.web.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hidata.framework.util.StringUtil;
import com.hidata.web.dao.PagerDao;
import com.hidata.web.dao.WhiteDomainDao;
import com.hidata.web.dto.WhiteDomainDto;
import com.hidata.web.service.WhiteDomainService;
import com.hidata.web.util.Pager;
import com.hidata.web.util.TimeUtil;

@Service
public class WhiteDomainServiceImpl implements WhiteDomainService{
	
	@Autowired
	private PagerDao pagerDao;
	
	@Autowired
	private WhiteDomainDao whiteDomainDao;
	
	@Override
	public Pager getPager(Map<String, String> map, String curPage) {
		Pager pager = null;
		StringBuffer sb_sql = new StringBuffer();
		sb_sql.append("SELECT * FROM white_domain WHERE sts = 'A' ");
		if(map != null && map.size() > 0){
			String domainName = map.get("domainName");
			if(StringUtil.isNotEmpty(domainName)){
				sb_sql.append(" AND domain_name LIKE '%" + domainName +"%'");
			}
			
			String domainUrl = map.get("domainUrl");
			if(StringUtil.isNotEmpty(domainUrl)){
				sb_sql.append(" AND domain_url LIKE '%" + domainUrl +"%'");
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
		pager = pagerDao.getPagerBySql(sb_sql.toString(), Integer.parseInt(curPage), 10, WhiteDomainDto.class);
		return pager;
	}

	@Override
	public Boolean addWhiteDomain(WhiteDomainDto whiteDomain) {
		if(whiteDomain != null){
			String date = TimeUtil.dateLongToMMHHssString(new Date().getTime());
			whiteDomain.setStsDate(date);
			Integer id = whiteDomainDao.save(whiteDomain);
			if(id != null && id > 0){
				return true;
			}
		}
		return false;
	}

	@Override
	public WhiteDomainDto getWhiteDomainById(String id) {
		if(StringUtil.isNotEmpty(id)){
			List<WhiteDomainDto> list = whiteDomainDao.getObjectByPkId(id);
			if(list != null && list.size() == 1){
				return list.get(0);
			}
		}
		return null;
	}

	@Override
	public Boolean edit(WhiteDomainDto whiteDomain) {
		if(whiteDomain != null){
			String date = TimeUtil.dateLongToMMHHssString(new Date().getTime());
			whiteDomain.setStsDate(date);
			Integer rows = whiteDomainDao.update(whiteDomain);
			if(rows != null){
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean delete(String domainId) {
		if(StringUtil.isNotEmpty(domainId)){
			Integer rows = whiteDomainDao.delete(domainId);
			if(rows != null && rows > 0){
				return true;
			}
			
		}
		return false;
	}

	@Override
	public Boolean deleteAll(String domainIds) {
		if(StringUtil.isNotEmpty(domainIds)){
			domainIds = domainIds.substring(0, domainIds.length() - 1);
			Integer rows = whiteDomainDao.deleteAll(domainIds);
			if(rows != null && rows > 0){
				return true;
			}
		}
		return false;
	}

}
