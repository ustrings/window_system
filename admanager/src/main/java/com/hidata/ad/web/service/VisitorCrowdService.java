package com.hidata.ad.web.service;

import java.util.List;
import java.util.Map;

import com.hidata.ad.web.dto.MediaCategoryDto;
import com.hidata.ad.web.dto.VisitorCrowdDto;
import com.hidata.ad.web.model.VisitorCrowd;

/**
 * 重定向人群的Service dao
 * @author xiaoming
 * @date 2014年5月26日
 */
public interface VisitorCrowdService {
	public List<VisitorCrowd> getVisitorList(VisitorCrowd visitor);
	public List<MediaCategoryDto> getMediaCategoryList();
	public void addVisitorCrowd(VisitorCrowdDto visitordto);
	public Map<String,Object> getIdbyVisitor(VisitorCrowd visitor);
	public void updateCode(String vc_id, String code);
	public void delVisitor(String vc_id, String userid);
	public VisitorCrowdDto getVisotorById(String vc_id, String userid);
	public void update(VisitorCrowdDto visotordto);
}
