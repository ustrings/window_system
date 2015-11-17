package com.vaolan.adserver.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import com.vaolan.adserver.dao.CookieUserDao;
import com.vaolan.adserver.model.CookieUser;

@Component
public class CookieUserDaoImpl implements CookieUserDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	/* (non-Javadoc)
	 * @see com.vaolan.adserver.dao.impl.CookieUserDao#save(com.vaolan.adserver.model.CookieUser)
	 */
	@Override
	public int save(CookieUser cookie){
		final String cookieId = cookie.getId();
		final String ipAddress = cookie.getIpAddress();
		final String requestProtocal = cookie.getRequestProtocal();
		final String userAgent = cookie.getUserAgent();
		return jdbcTemplate.update("INSERT INTO ad_cookie_user(cookie_id,ip_address,request_protocal, user_agent,create_time) "
				+ "VALUES (?,?,?,?,CURRENT_TIMESTAMP())", 
        new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1,cookieId);
				ps.setString(2, ipAddress);
				ps.setString(3, requestProtocal);
				ps.setString(4, userAgent);
			}
		});
	}
}
