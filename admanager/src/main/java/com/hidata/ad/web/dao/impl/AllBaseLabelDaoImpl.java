package com.hidata.ad.web.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.ad.web.dao.IAllBaseLabelDao;
import com.hidata.ad.web.model.AllBaseLabel;
import com.hidata.ad.web.model.IntrestLabelTree;
import com.hidata.framework.db.DBManager;

/**
 * 标签DAO Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年12月4日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
@Component
public class AllBaseLabelDaoImpl implements IAllBaseLabelDao {
    @Autowired
    private DBManager db;

    @Override
    public List<AllBaseLabel> qryAllBaseLabelChildrenByParent(AllBaseLabel paraAllLabelTree) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select * from all_base_label a where a.pId=?");
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(paraAllLabelTree.getPid());
        Object[] args = paramList.toArray();
        return db.queryForListObject(sqlBuilder.toString(), args, AllBaseLabel.class);
    }

    
    
    public AllBaseLabel qryAllBaseLabelByLabelValue(String labelId)
    {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select * from all_base_label a where  a.id=? ");
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(labelId);
        Object[] args = paramList.toArray();
        return db.queryForObject(sqlBuilder.toString(), args, AllBaseLabel.class);
    }



	@Override
	public List<AllBaseLabel> qryAllBaseLabelByPId(String parentId) {
		String sql="select * from all_base_label a where a.pId=?";
		Object[] args = new Object[]{parentId};
		return db.queryForListObject(sql, args,AllBaseLabel.class);
	}
    
    
}
