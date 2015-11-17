package com.vaolan.adserver.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.vaolan.adserver.dao.CookieUserDao;
import com.vaolan.adserver.dao.ImpressLogDao;
import com.vaolan.adserver.model.CookieUser;
import com.vaolan.adserver.model.ImpressLog;
import com.vaolan.adserver.service.CookieUserService;

@Component
public class CookieUserServiceImpl implements CookieUserService {

	@Autowired
	private CookieUserDao cookieDao;
	@Autowired
	private ImpressLogDao logDao;
	
	/* (non-Javadoc)
	 * @see com.vaolan.adserver.service.impl.CookieUserService#saveCookieInfo(com.vaolan.adserver.model.CookieUser)
	 */
	@Override
	@Transactional
	public int saveCookieInfo(CookieUser cookie,ImpressLog log,boolean isNew){
		if(isNew){
			cookieDao.save(cookie);
		}
		return logDao.save(log);
	}
}
