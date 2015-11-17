package com.vaolan.ckserver.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.framework.db.DBManager;
import com.vaolan.ckserver.dao.AdInstanceDao;
import com.vaolan.ckserver.model.AdInstance;

/**
 * 人群操作接口实现类
 * 
 * @author ZTD
 * 
 */
@Component
public class AdInstanceDaoImpl implements AdInstanceDao {
	
	/**
	 * 
	 */
	private static Logger logger = LoggerFactory.getLogger(AdInstanceDaoImpl.class);
	
    @Autowired
    private DBManager db;

    @Override
    public List<AdInstance> queryAdInstanceListUsing() throws Exception {
        String sql = "select * from ad_instance where now() between start_time and end_time";
        return db.queryForListObject(sql, AdInstance.class);
    }
}
