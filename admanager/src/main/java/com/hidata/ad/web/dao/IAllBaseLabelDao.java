package com.hidata.ad.web.dao;

import java.util.List;

import com.hidata.ad.web.model.AllBaseLabel;

public interface IAllBaseLabelDao {
    
    public List<AllBaseLabel> qryAllBaseLabelChildrenByParent(AllBaseLabel paraAllLabelTree);
    
    
    public AllBaseLabel qryAllBaseLabelByLabelValue(String labelId);


	public List<AllBaseLabel> qryAllBaseLabelByPId(String parentId);
}
