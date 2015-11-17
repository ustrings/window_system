package com.hidata.ad.web.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.ad.web.dao.IAllLabelAdPlanDao;
import com.hidata.ad.web.dto.AdInstanceDto;
import com.hidata.ad.web.model.AllLabelAdPlan;
import com.hidata.framework.db.DBManager;

/**
 * 标签广告计划 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年12月4日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
@Component
public class AllLabelAdPlanDaoImpl implements IAllLabelAdPlanDao {

    @Autowired
    private DBManager db;

    @Override
    public List<AllLabelAdPlan> queryallLabelListByAdId(String adId) throws Exception {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select * from all_label_adplan a where a.ad_id=?");
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(adId);
        Object[] args = paramList.toArray();
        return db.queryForListObject(sqlBuilder.toString(), args, AllLabelAdPlan.class);
    }

    @Override
    public void addAllLabelAdPlan(AllLabelAdPlan allLabelAdPlan) {

         db.insertObject(allLabelAdPlan);
    }

    
    @Override
    public void delAllLabelAdPlan(String  adId) {
        
        String sql= "delete from all_label_adplan where ad_id = ?";
        db.update(sql,new Object[]{adId});
        
    }
}
