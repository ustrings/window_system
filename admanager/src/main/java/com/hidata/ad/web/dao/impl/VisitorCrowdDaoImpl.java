package com.hidata.ad.web.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hidata.ad.web.dao.VisitorCrowdDao;
import com.hidata.ad.web.dto.MediaCategoryDto;
import com.hidata.ad.web.dto.VisitorCrowdDto;
import com.hidata.ad.web.model.VisitorCrowd;
import com.hidata.framework.db.DBManager;

/**
 * 重定向人群 DAO 的实现类
 * @author xiaoming
 * @date 2014年5月26日
 */
@Repository
public class VisitorCrowdDaoImpl implements VisitorCrowdDao {
	@Autowired
	private DBManager db;
	
	/**
	 * 查询重定向人群
	 */
	@Override
	public List<VisitorCrowd> getVisitorList(VisitorCrowd visitor) {
		String sql = "SELECT visitor_crowd.vc_id, visitor_crowd.vc_name,media_category.name,visitor_crowd.vc_site_desc,visitor_crowd.vc_site_host,visitor_crowd.vc_code FROM visitor_crowd LEFT JOIN media_category ON visitor_crowd.vc_site_type = media_category.code WHERE visitor_crowd.vc_userid = ? AND visitor_crowd.vc_sts = 'A'";
		Object[] args = new Object[] {visitor.getVcUserid()};
		return db.queryForListObject(sql, VisitorCrowd.class, args);
	}
	
	/**
	 * 获取所有媒体类型
	 */
	@Override
	public List<MediaCategoryDto> getAllCategoryList() {
		String sql = "SELECT * FROM media_category WHERE parent_media_category_code = -1 AND sts = 'A'";
		return db.queryForListObject(sql, MediaCategoryDto.class);
	}
	
	/**
	 * 添加重定向人群 并且返回人群
	 */
	@Override
	public  int addObjectReturnId(VisitorCrowdDto visitordto) {
		return db.insertObjectAndGetAutoIncreaseId(visitordto);
	}
	
	/**
	 * 查找重定向人群ID
	 */

	@Override
	public Map<String, Object> getIdByVisitorCrowd(VisitorCrowd visitor) {
		String sql = "SELECT vc_id FROM visitor_crowd WHERE vc_name = ? AND vc_site_type = ? AND vc_site_host = ? AND vc_userid = ? AND vc_sts= 'A'";
		Object args = new Object[]{
				visitor.getName(), visitor.getVcSiteType(),
				visitor.getVcSiteHost(), visitor.getVcUserid()	
		};
		return db.queryForMap(sql, args);
	}

	@Override
	public void updateCodeByVisitor(String vc_id, String code) {
		String sql = "UPDATE visitor_crowd set vc_code = ? WHERE vc_id = ? AND vc_sts = 'A'";
		Object[] args = new Object[]{
				code , vc_id
		};
		db.update(sql, args);
	}
	
	/**
	 * 删除
	 */
	@Override
	public void delVisitorCrowd(String vc_id, String userid) {
		String sql = "UPDATE visitor_crowd set vc_sts = 'D' where vc_id = ? AND vc_userid = ?";
		Object[] args = new Object[]{
				vc_id, userid
		};
		db.update(sql, args);
	}
	
	/**
	 *编辑跟新
	 */
	@Override
	public void update(VisitorCrowdDto visitordto) {
		String sql = "UPDATE visitor_crowd SET vc_name = ? , vc_site_type = ? , vc_site_desc = ? , vc_site_host = ?  WHERE vc_userid = ? AND vc_id = ? AND vc_sts = 'A'";
		Object[] args = new Object[]{
				visitordto.getName(), visitordto.getVcSiteType(), visitordto.getVcSiteDesc(),
				visitordto.getVcSiteHost(),  visitordto.getVcUserid(),
				visitordto.getVcId()
		};
		db.update(sql, args);
	}

	@Override
	public List<VisitorCrowdDto> getVisitorById(String vc_id, String userid) {
		String sql = "SELECT visitor_crowd.vc_id , visitor_crowd.vc_name, visitor_crowd.vc_site_type, visitor_crowd.vc_site_desc, visitor_crowd.vc_site_host FROM visitor_crowd WHERE vc_id =? AND vc_userid = ? AND vc_sts = 'A'";
		Object[] args = new Object[]{
			vc_id, userid	
		};
		return db.queryForListObject(sql, args, VisitorCrowdDto.class);
	}
}
