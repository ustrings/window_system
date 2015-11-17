package com.vaolan.sspserver.dao.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vaolan.sspserver.dao.AdVclinkDao;



@Repository
public class AdVcLinkDaoImpl implements AdVclinkDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/* (non-Javadoc)
	 * @see com.vaolan.adtarget.dao.impl.AdVclinkDao#getVcIdByAdId(java.lang.String)
	 */
	@Override
	public List <String> getVcIdByAdId(String adId){
		String sql = "select vc_id from ad_vc_link where ad_id=?";
		return jdbcTemplate.queryForList(sql, String.class,adId);
	}
}
