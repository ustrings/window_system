package com.vaolan.sspserver.dao;

import java.util.List;

import com.vaolan.sspserver.model.AdPlanLabelRel;
import com.vaolan.sspserver.model.AllLabelBaseInfo;

/**
 * 广告标签对应DAO
 * Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年12月15日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
public interface AdLabelRelDao {

    /**
     * 通过标签值查找标签
     * @param labelId
     * @return
     * @author zhoubin
     */
    public AllLabelBaseInfo qryAllBaseLabelByLabelValue(String labelId);
    
    /**
     * 通过广告ID查找对应的标签
     * @param adId
     * @return
     * @author zhoubin
     */
    public List<AdPlanLabelRel>  qryAdplanLabelByAdId(String adId);
}
