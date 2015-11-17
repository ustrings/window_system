package com.hidata.ad.web.dao;

import java.util.List;
import java.util.Map;

import com.hidata.ad.web.dto.MediaCategoryDto;
import com.hidata.ad.web.dto.VisitorCrowdDto;
import com.hidata.ad.web.model.VisitorCrowd;

/**
 * 重定向人群 DAO
 * @author xiaoming
 * @date 2014年5月26日
 */
public interface VisitorCrowdDao {
	public List<VisitorCrowd> getVisitorList(VisitorCrowd visitor);
	public List<MediaCategoryDto> getAllCategoryList();
//	public void AddVisitorCrowd(VisitorCrowd visitor);
	public int addObjectReturnId(VisitorCrowdDto visitordto);
	public Map<String,Object> getIdByVisitorCrowd(VisitorCrowd visitor);
	public void updateCodeByVisitor(String vc_id, String code);
	public void delVisitorCrowd(String vc_id , String userid);
	public void update(VisitorCrowdDto visitordto);
	public List<VisitorCrowdDto> getVisitorById(String vc_id, String userid);
}