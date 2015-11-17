package com.vaolan.sspserver.dao.imp;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.vaolan.sspserver.dao.AdRegionDao;


@Repository
public class AdRegionDaoImpl implements AdRegionDao {

	
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	
	@Override
	public Set<String> getRegionTargetByAdId(String adId) {
		
			Set<String> regions = Collections.emptySet();
			String sql = "select region_name from ad_region_link where ad_id=?";
			SqlRowSet rs = jdbcTemplate.queryForRowSet(sql,adId);
			while(rs.next()){
				if(regions.size() == 0){
					regions = new HashSet<String>();
				}
				regions.add(rs.getString(1));
			}
			
		
		
		return regions;
	}

}
