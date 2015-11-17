package com.hidata.ad.web.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hidata.ad.web.dao.VisitorCrowdDao;
import com.hidata.ad.web.dto.MediaCategoryDto;
import com.hidata.ad.web.dto.VisitorCrowdDto;
import com.hidata.ad.web.model.VisitorCrowd;
import com.hidata.ad.web.service.VisitorCrowdService;

/**
 * 重定向人群的 Service 实现类
 * @author xiaoming
 *
 */
@Service
public class VisitorCrowdServiceImpl implements VisitorCrowdService {
	@Autowired
	private VisitorCrowdDao visitordao;
	
	/**
	 * 获取所有重定向人群
	 */
	@Override
	public List<VisitorCrowd> getVisitorList(VisitorCrowd visitor) {
		
		return visitordao.getVisitorList(visitor);
	}
	
	/**
	 * 获取所有媒体类型
	 */
	@Override
	public List<MediaCategoryDto> getMediaCategoryList() {
		return visitordao.getAllCategoryList();
	}
	
	/**
	 * 添加重定向人群
	 */
	@Override
	@Transactional
	public void addVisitorCrowd(VisitorCrowdDto visitordto) {
		if(visitordto != null){
			int vc_id = visitordao.addObjectReturnId(visitordto);
			//<img src='http://cms.tanx.com/t.gif?tanx_nid=54801212&tanx_cm&vl_adxchannel=tanx&vl_type=2&vl_vcid=1001' width='0' height='0'>
			if(vc_id != 0){
				String code = 
						"<img src='http://cms.tanx.com/t.gif?tanx_nid=54801212&tanx_cm&vl_adxchannel=tanx&vl_type=2&vl_vcid="+vc_id+"' width='0' height='0'>";
				visitordao.updateCodeByVisitor(vc_id + "" , code);
			}
		}
	}
	
	/**
	 * 查询重定向人群
	 */
	@Override
	public Map<String, Object> getIdbyVisitor(VisitorCrowd visitor) {
		return visitordao.getIdByVisitorCrowd(visitor);
	}
	
	/**
	 * 根据ID插入Code
	 */
	@Override
	@Transactional
	public void updateCode(String vc_id, String code) {
		visitordao.updateCodeByVisitor(vc_id, code);
	}
	
	/**
	 *删除
	 */
	@Override
	@Transactional
	public void delVisitor(String vc_id, String userid) {
		visitordao.delVisitorCrowd(vc_id, userid);
	}
	
	/**
	 * 根据id查询对象
	 */
	@Override
	public VisitorCrowdDto getVisotorById(String vc_id, String userid) {
		List<VisitorCrowdDto> list = visitordao.getVisitorById(vc_id, userid);
		if(list != null && list.size() == 1){
			return list.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public void update(VisitorCrowdDto visotordto) {
		visitordao.update(visotordto);
	}
}
