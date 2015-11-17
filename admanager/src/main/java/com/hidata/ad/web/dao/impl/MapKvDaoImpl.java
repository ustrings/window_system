package com.hidata.ad.web.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.hidata.ad.web.dao.MapKvDao;
import com.hidata.ad.web.model.MapKv;
import com.hidata.framework.db.DBManager;
@Repository
public class MapKvDaoImpl implements MapKvDao {
    @Autowired
    private DBManager db;

    private static final String QUERY_MAPKV_LIST_BY_TYPE = "select m_id, m_type, attr_code, attr_value, width, height from map_kv a where a.m_type = ?";

    public List<MapKv> findMapKvByType(String paraKvType) {
        Object[] args = new Object[] { paraKvType };
        return db.queryForListObject(QUERY_MAPKV_LIST_BY_TYPE, args, MapKv.class);
    }

	@Override
	public List<MapKv> findMapKvByAttrCode(String attrCode) {
		String sql = "SELECT * FROM map_kv WHERE attr_code = " + attrCode;
		try {
			List<MapKv> list = db.queryForListObject(sql, MapKv.class);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
    
}
