package com.hidata.ad.web.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.hidata.ad.web.dao.IIntrestCrowdDao;
import com.hidata.ad.web.model.IntrestLabelCrowd;
import com.hidata.ad.web.model.IntrestLabelTree;
import com.hidata.framework.db.DBManager;

/**
 * 兴趣标签定向 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年8月27日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
@Component
public class IntrestCrowdDaoImpl implements IIntrestCrowdDao {
    @Autowired
    private DBManager db;

    /**
     * 添加兴趣标签定向
     */
    @Override
    public int addIntrestCrowdInfo(IntrestLabelCrowd intrestDto) {
        return db.insertObjectAndGetAutoIncreaseId(intrestDto);
    }
 
    @Override
	public int delIntrestCrowdInfoByCrowdId(String crowdId){
		StringBuilder sql = new StringBuilder("delete from  intrest_label_crowd where crowd_id=?");
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(crowdId);
		return db.update(sql.toString(), paramList.toArray());
	}
    /**
     * 查询兴趣标签树
     */
    @Override
    public List<IntrestLabelTree> qryIntrestLabelChildrenByParent(IntrestLabelTree paraLabelTree) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select * from intrest_label_tree a where a.parent_label_val=?");

        List<Object> paramList = new ArrayList<Object>();
        paramList.add(paraLabelTree.getLabelValue());
        Object[] args = paramList.toArray();
        return db.queryForListObject(sqlBuilder.toString(), args, IntrestLabelTree.class);
    }
    
    @Override
    public IntrestLabelTree queryIntrestLabelTreeByLabelVal(String labVal){
    	StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select * from intrest_label_tree a where  a.label_val=? ");
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(labVal);
        Object[] args = paramList.toArray();
        return db.queryForObject(sqlBuilder.toString(), args, IntrestLabelTree.class);
    }
    /**
     * 查询兴趣表标签
     * @param crowdId
     * @return
     * @throws Exception
     */
    @Override
    public List<IntrestLabelCrowd> queryIntrestLabelCrowdListByCrowdId(String crowdId)throws Exception{
    	StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select * from intrest_label_crowd	 a where a.crowd_id=?");
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(crowdId);
        Object[] args = paramList.toArray();
        return db.queryForListObject(sqlBuilder.toString(), args, IntrestLabelCrowd.class);
    }

}
