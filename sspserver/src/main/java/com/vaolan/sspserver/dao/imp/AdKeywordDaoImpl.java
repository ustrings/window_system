package com.vaolan.sspserver.dao.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hidata.framework.db.DBManager;
import com.vaolan.sspserver.dao.AdKeywordDao;
import com.vaolan.sspserver.model.AdPlanKeyWord;

/**
 * 广告标签对应DAO Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年12月15日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhangtengda
 */
@Repository
public class AdKeywordDaoImpl implements AdKeywordDao {

    @Autowired
    private DBManager db;

	@Override
	public AdPlanKeyWord qryAdPlanKeyWordByAdId(String adId) {
		StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select * from adplan_keyword a where  a.ad_id=? ");
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(adId);
        Object[] args = paramList.toArray();
        return db.queryForObject(sqlBuilder.toString(), args, AdPlanKeyWord.class);
	}

	@Override
	public AdPlanKeyWord qryAdPlanKeyWordNotJSJTByAdId(String adId) {
		StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select * from adplan_keyword a where  a.ad_id=? and is_jisoujitou='2'");
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(adId);
        Object[] args = paramList.toArray();
        return db.queryForObject(sqlBuilder.toString(), args, AdPlanKeyWord.class);
	}
}
