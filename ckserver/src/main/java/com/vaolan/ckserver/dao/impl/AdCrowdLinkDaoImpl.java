package com.vaolan.ckserver.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.framework.db.DBManager;
import com.vaolan.ckserver.dao.AdCrowdLinkDao;
import com.vaolan.ckserver.model.AdCrowdLink;

/**
 * 人群操作接口实现类
 * 
 * @author ZTD
 * 
 */
@Component
public class AdCrowdLinkDaoImpl implements AdCrowdLinkDao {
	
	/**
	 * 
	 */
	private static Logger logger = LoggerFactory.getLogger(AdCrowdLinkDaoImpl.class);
	
    @Autowired
    private DBManager db;

    public List<AdCrowdLink> queryAdCrowdLinkByAdId(String  adId) throws Exception{
    	 String sql = "select * from ad_crowd_link where ad_id=?";
         return db.queryForListObject(sql,new Object[]{adId},AdCrowdLink.class);
    }
}
