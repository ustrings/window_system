package com.vaolan.sspserver.dao.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hidata.framework.db.DBManager;
import com.vaolan.sspserver.dao.AdLabelRelDao;
import com.vaolan.sspserver.model.AdPlanLabelRel;
import com.vaolan.sspserver.model.AllLabelBaseInfo;

/**
 * 广告标签对应DAO Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年12月15日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
@Repository
public class AdLabelRelDaoImpl implements AdLabelRelDao {

    @Autowired
    private DBManager db;

    /**
     * 通过标签值查找标签信息
     */
    public AllLabelBaseInfo qryAllBaseLabelByLabelValue(String labelId) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select * from all_base_label a where  a.id=? ");
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(labelId);
        Object[] args = paramList.toArray();
        return db.queryForObject(sqlBuilder.toString(), args, AllLabelBaseInfo.class);
    }

    /**
     * 通过广告ID查找对应的标签
     * 
     * @param adId
     * @return
     * @author zhoubin
     */
    public List<AdPlanLabelRel> qryAdplanLabelByAdId(String adId) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select * from all_label_adplan a where  a.ad_id=? ");
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(adId);
        Object[] args = paramList.toArray();
        return db.queryForListObject(sqlBuilder.toString(), args, AdPlanLabelRel.class);
    }
}
