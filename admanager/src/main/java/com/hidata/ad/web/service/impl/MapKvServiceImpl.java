package com.hidata.ad.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.ad.web.dao.MapKvDao;
import com.hidata.ad.web.model.MapKv;
import com.hidata.ad.web.service.MapKvService;
@Component
public class MapKvServiceImpl implements MapKvService {
    @Autowired
    private MapKvDao mapKvDao;
    @Override
    public List<MapKv> findMapKvByType(String paraKvType) {
        return mapKvDao.findMapKvByType(paraKvType);
    }
	@Override
	public MapKv findMapKvByAttrCode(String attrCode) {
		List<MapKv> list = mapKvDao.findMapKvByAttrCode(attrCode);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

}
