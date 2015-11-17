package com.hidata.ad.web.service;

import java.util.List;

import com.hidata.ad.web.model.MapKv;

public interface MapKvService {

    public List<MapKv> findMapKvByType(String paraKvType);
    
    /**
     * 根据AttrCode查找实体
     * @param id
     * @return
     */
    public MapKv findMapKvByAttrCode(String attrCode);
}
