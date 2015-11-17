package com.vaolan.sspserver.dao.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vaolan.sspserver.dao.DMPCrowdDao;

@Repository
public class DMPCrowdDaoImpl implements DMPCrowdDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	/* (non-Javadoc)
	 * @see com.vaolan.adtarget.rtb.dao.DMPCrowdDao#getCrowdIdByAdId(java.lang.String)
	 */
	@Override
	public List<String> getCrowdIdByAdId(String adId){
		String sql = "select crowd_id from ad_crowd_link where ad_id=?";
		return jdbcTemplate.queryForList(sql, String.class,adId);
	}
}
