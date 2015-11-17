package com.hidata.ad.web.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import com.hidata.ad.web.dao.AdMLinkDao;
import com.hidata.ad.web.dto.AdMLinkDto;
import com.hidata.ad.web.dto.AdMaterialLinkDto;
import com.hidata.ad.web.model.AdMLink;
import com.hidata.framework.db.DBManager;

@Component
public class AdMLinkDaoImpl implements AdMLinkDao {

	@Autowired
	private DBManager db;
	
	private static final String QUERY_MATERIAL_LIST_BY_MATERIALID= "select a.id, a.ad_id, a.ad_m_id, a.sts from ad_m_link a where a.ad_m_id = ? and a.sts = 'A'";
	@Override
	public List<AdMLink> findAdMLinkListByMaterialId(int materialId) {
		Object[] args = new Object[]{materialId};
		return db.queryForListObject(QUERY_MATERIAL_LIST_BY_MATERIALID, args, AdMLink.class);
	}
	
	// 查询出来需要获取状态的 ad_m_link 表中的记录
	private static final String QUERY_ADMLINK_TO_CHECK = 
			"select a.id, a.ad_id, a.ad_m_id, a.sts, a.check_status, a.comment from ad_m_link a "
			+ " where (a.check_status='SENT' or a.check_status='WAITING' or a.check_status='CHECKERROR' ) and a.sts = 'A'";
	@Override
	public List<AdMLink> findMaterialToCheck() {
		return db.queryForListObject(QUERY_ADMLINK_TO_CHECK, AdMLink.class);
	}
	
	private static final String UPDATE_SQL = "update ad_m_link a set check_status=?,comment=? where a.id = ?";
	@Override
	public void editAdMLinkForCheckJob(AdMLink adMLink) {
		Object[] args = new Object[]{adMLink.getCheckStatus(), adMLink.getComment(), adMLink.getId()};
		db.update(UPDATE_SQL, args);
	}
    
	private static final String QUERY_ADMLINK_BY_AD_ID = 
	"select a.id, a.ad_id, a.ad_m_id, a.sts, a.check_status, a.comment, m.material_name, m.m_type, m.link_url, m.rich_text"
	+ " from ad_m_link a, ad_material m" + 
	" where a.sts = 'A' and a.ad_m_id = m.ad_m_id and a.ad_id=";
	
	@Override
	public List<AdMLinkDto> findAdMLinkDtoByAdId(int adId) {
		List<AdMLinkDto> adMLinkDtos = db.getTemplate().query(QUERY_ADMLINK_BY_AD_ID + adId, new BeanPropertyRowMapper(AdMLinkDto.class));
		return adMLinkDtos;
	}
	
	
	private static final String QUERY_ADMATERIAL_LINK_BY_AD_ID = 
	"select a.id, a.ad_id, a.ad_m_id, a.create_time, a.sts, a.check_status, a.comment"
	+ " from ad_m_link a" + 
	" where a.sts = 'A' and a.ad_id=?";
	
	@Override
	public List<AdMaterialLinkDto> findAdMLinkListByAdId(String adId) {
		List<AdMaterialLinkDto> adMLinks = db.queryForListObject(QUERY_ADMATERIAL_LINK_BY_AD_ID, new Object[]{adId}, AdMaterialLinkDto.class);
		return adMLinks;
	}

	private static final String QUERY_ADMLINK_BY_ID = 
			"select a.id, a.ad_id, a.ad_m_id, a.sts, a.check_status, a.comment"
			+ " from ad_m_link a" + 
			" where a.id=?";
	@Override
	public AdMLink getAdMLinkById(String id) {
		return db.queryForBean(QUERY_ADMLINK_BY_ID, new Object[]{id},AdMLink.class);
	}
	@Override
	public List<AdMLinkDto> findAdMLinkDtoByAdId(int adId, String status) {
		// TODO Auto-generated method stub
		return null;
	}
}
