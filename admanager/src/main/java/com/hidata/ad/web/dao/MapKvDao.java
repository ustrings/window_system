package com.hidata.ad.web.dao;

import java.util.List;

import com.hidata.ad.web.model.MapKv;

public interface MapKvDao {
    
    public List<MapKv> findMapKvByType(String paraKvType);
    
    /**
     * 根据attrCode查找实体
     * @param id
     * @return
     */
    public List<MapKv> findMapKvByAttrCode(String attrCode);

}
