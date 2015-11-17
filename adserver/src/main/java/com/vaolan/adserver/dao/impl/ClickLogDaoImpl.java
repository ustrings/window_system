package com.vaolan.adserver.dao.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Component;

import com.vaolan.adserver.dao.ClickLogDao;

import com.vaolan.adserver.model.ImpressLog;

@Component
public class ClickLogDaoImpl implements ClickLogDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	/* (non-Javadoc)
	 * @see com.vaolan.adserver.dao.impl.ClickLogDao#save(com.vaolan.adserver.model.ImpressLog)
	 */
	@Override
	public int save(ImpressLog log){
		Object[] args = new Object[]{log.getAdId(),log.getAdMaterialId(),log.getUid(),log.getSrcUrl(),log.getVisitorIp()};
		return jdbcTemplate.update("INSERT INTO  ad_clicked_log ( ad_id, ad_m_id, click_time, uid,src_url,visitor_ip) "
				+ "VALUES (?,?,CURRENT_TIMESTAMP(),?,?,?)", args);
        
	}
	
	
}
