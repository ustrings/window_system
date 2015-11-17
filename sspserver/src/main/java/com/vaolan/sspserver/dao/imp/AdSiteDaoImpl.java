package com.vaolan.sspserver.dao.imp;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.vaolan.sspserver.dao.AdSiteDao;



@Repository
public class AdSiteDaoImpl implements AdSiteDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;

	
	@Override
	public List<String> getSiteUrlByAdId(String adId){
		String sql = "select url from ad_site where ad_id=? and match_type ='R' and site_type='+'";
		return jdbcTemplate.queryForList(sql,String.class,adId);
		
	}
	
	@Override
	public Set<String> getPosTargetIpByAdId(String adId){
		Set<String> ips = Collections.emptySet();
		String sql = "select ip from ad_ip_targeting where ad_id=? and type ='+'";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql,adId);
		while(rs.next()){
			if(ips.size() == 0){
				ips = new HashSet<String>();
			}
			ips.add(rs.getString(1));
		}
		return ips;
	}
	
	
	@Override
	public Set<String> getTargetAdAcctByAdId(String adId){
		Set<String> ips = Collections.emptySet();
		String sql = "select ad_acct from ad_adacct_targeting where ad_id=?";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql,adId);
		while(rs.next()){
			if(ips.size() == 0){
				ips = new HashSet<String>();
			}
			ips.add(rs.getString(1));
		}
		return ips;
	}
	
	

	@Override
	public List<String> getUrlByAdId(String adId) {
		String sql = "select url from ad_site where ad_id=? and match_type='F' ";
		return jdbcTemplate.queryForList(sql,String.class,adId);
	}

	@Override
	public Set<String> getNegTargetIpByAdId(String adId) {
		Set<String> ips = Collections.emptySet();
		String sql = "select ip from ad_ip_targeting where ad_id=? and type ='-'";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql,adId);
		while(rs.next()){
			if(ips.size() == 0){
				ips = new HashSet<String>();
			}
			ips.add(rs.getString(1));
		}
		return ips;
	}

	@Override
	public List<String> getNegSiteUrlByAdId(String adId) {
		String sql = "select url from ad_site where ad_id=? and match_type ='R' and site_type='-'";
		return jdbcTemplate.queryForList(sql,String.class,adId);
	}
}
