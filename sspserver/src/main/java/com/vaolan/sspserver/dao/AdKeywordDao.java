package com.vaolan.sspserver.dao;

import com.vaolan.sspserver.model.AdPlanKeyWord;

/**
 * 广告标签对应DAO
 * Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年12月15日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
public interface AdKeywordDao {

    /**
     * 通过广告ID查找对应的关键词
     * @param adId
     * @return
     */
    public AdPlanKeyWord qryAdPlanKeyWordByAdId(String adId);
    
    public AdPlanKeyWord qryAdPlanKeyWordNotJSJTByAdId(String adId);
}
