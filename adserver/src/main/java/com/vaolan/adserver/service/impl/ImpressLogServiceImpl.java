package com.vaolan.adserver.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.vaolan.adserver.dao.ClickLogDao;
import com.vaolan.adserver.model.ImpressLog;
import com.vaolan.adserver.service.impressLogService;

@Component
public class ImpressLogServiceImpl implements impressLogService {

	@Autowired
	private ClickLogDao clickDao;

	/* (non-Javadoc)
	 * @see com.vaolan.adserver.service.impl.impressLogService#saveLogReturnId(com.vaolan.adserver.model.ImpressLog)
	 */
	@Override
	@Transactional
	public int saveClickLog(ImpressLog log){
		return clickDao.save(log);
	}

}
