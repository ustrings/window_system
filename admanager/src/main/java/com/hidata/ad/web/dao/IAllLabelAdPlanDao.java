package com.hidata.ad.web.dao;

import java.util.List;

import com.hidata.ad.web.dto.AdInstanceDto;
import com.hidata.ad.web.model.AllLabelAdPlan;

public interface IAllLabelAdPlanDao {

    public void addAllLabelAdPlan(AllLabelAdPlan allLabelAdPlan);

    public List<AllLabelAdPlan> queryallLabelListByAdId(String adId) throws Exception;
    
    public void delAllLabelAdPlan(String  adId);
   

}
