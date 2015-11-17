package com.vaolan.adserver.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.vaolan.adserver.dao.ImpressLogDao;
import com.vaolan.adserver.model.ImpressLog;

@Component
public class ImpressLogDaoImpl implements ImpressLogDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	/* (non-Javadoc)
	 * @see com.vaolan.adserver.dao.impl.ImpressLogDao#save(com.vaolan.adserver.model.ImpressLog)
	 */
	@Override
	public int save(ImpressLog log){
		return jdbcTemplate.update("INSERT INTO ad_impress_log (ad_id, ad_m_id, impress_url,ts, uid, visitor_ip) "
				+ "VALUES (?,?,?,CURRENT_TIMESTAMP(),?,?)",
				log.getAdId(),log.getAdMaterialId(),log.getImpressUrl(),log.getUid(),log.getVisitorIp());
        
	}
	
	/* (non-Javadoc)
	 * @see com.vaolan.adserver.dao.impl.ImpressLogDao#saveReturnId(com.vaolan.adserver.model.ImpressLog)
	 */
	@Override
	public int saveReturnId(ImpressLog log){
		final String adId = log.getAdId();
		final String adMaterialId = log.getAdMaterialId();
		final String uid = log.getUid();
		final String visitorIp = log.getVisitorIp();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps =con.prepareStatement("INSERT INTO ad_impress_log (ad_id, ad_m_id, impress_url,ts, uid, visitor_ip) "
						+ "VALUES (?,?,?,CURRENT_TIMESTAMP(),?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, adId);
				ps.setString(2, adMaterialId);
				ps.setString(3, null);
				ps.setString(4, uid);
				ps.setString(5, visitorIp);
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}
	@Override
	public int updateUidById(ImpressLog log){
		return jdbcTemplate.update("UPDATE  ad_impress_log  SET uid=? WHERE  ad_ilog_id =?", log.getUid(),log.getLogId());
	}
}
