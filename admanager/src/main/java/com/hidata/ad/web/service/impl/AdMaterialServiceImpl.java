package com.hidata.ad.web.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

import com.hidata.ad.web.dao.AdMaterialDao;
import com.hidata.ad.web.dao.AdPlanManageDao;
import com.hidata.ad.web.model.AdInstance;
import com.hidata.ad.web.model.AdMLink;
import com.hidata.ad.web.model.AdMaterial;
import com.hidata.ad.web.model.AdMaterialCache;
import com.hidata.ad.web.model.AdStatRedisInfo;
import com.hidata.ad.web.model.MaterialStatBase;
import com.hidata.ad.web.service.AdMaterialService;
import com.hidata.ad.web.util.AdConstant;
import com.hidata.framework.cache.redis.JedisPoolWriper;
import com.hidata.framework.util.DateUtil;
import com.hidata.framework.util.JsonUtil;

@Component
public class AdMaterialServiceImpl implements AdMaterialService {

	@Autowired
	private AdMaterialDao adMatrialDao;
	
	@Resource(name = "jedisPool219")
	private JedisPoolWriper jedisPool;
	
	@Autowired
	private AdPlanManageDao adPlanManageDao;
	
	private Logger logger = Logger.getLogger(AdMaterialServiceImpl.class);

	
	public boolean addMaterial(AdMaterial material) {
		String richText = material.getRichText();
		material.setRichText("");
		int adMId = adMatrialDao.insertMaterail(material);
		material.setRichText(richText);
		material.setId(adMId);
        adMatrialDao.updateAdMaterialRichText(material);
		
		try{
			MaterialStatBase maStatBaseDay = new MaterialStatBase();
			maStatBaseDay.setMaterial_id(adMId+"");
			maStatBaseDay.setPvNum("0");
			maStatBaseDay.setClickNum("0");
			maStatBaseDay.setIpNum("0");
			maStatBaseDay.setMobileClickNum("0");
			maStatBaseDay.setMobilePvNum("0");
			maStatBaseDay.setNumType("D");
			maStatBaseDay.setTs(DateUtil.getCurrentDayBeginDateTimeStr());
			maStatBaseDay.setUvNum("0");

			adPlanManageDao.saveMaterialStatBase(maStatBaseDay);

			MaterialStatBase maStatBaseHour = new MaterialStatBase();
			maStatBaseHour.setMaterial_id(adMId+"");
			maStatBaseHour.setPvNum("0");
			maStatBaseHour.setClickNum("0");
			maStatBaseHour.setIpNum("0");
			maStatBaseHour.setMobileClickNum("0");
			maStatBaseHour.setMobilePvNum("0");
			maStatBaseHour.setNumType("H");
			maStatBaseHour.setTs(DateUtil.getCurrentHourBeginDateTimeStr());
			maStatBaseHour.setUvNum("0");

			adPlanManageDao.saveMaterialStatBase(maStatBaseHour);

			AdStatRedisInfo adStatRedisInfo = new AdStatRedisInfo();

			adStatRedisInfo.setTotal_pv_num("0");
			adStatRedisInfo.setTotal_click_num("0");
			adStatRedisInfo.setToday_pv_num("0");
			adStatRedisInfo.setToday_uv_num("0");
			adStatRedisInfo.setToday_ip_num("0");
			adStatRedisInfo.setToday_click_num("0");
			adStatRedisInfo.setToday_mobile_click_num("0");
			adStatRedisInfo.setToday_mobile_pv_num("0");
			adStatRedisInfo.setCurrentHour_pv_num("0");
			adStatRedisInfo.setCurrentHour_uv_num("0");
			adStatRedisInfo.setCurrentHour_ip_num("0");
			adStatRedisInfo.setCurrentHour_click_num("0");
			adStatRedisInfo.setCurrentHour_mobile_click_num("0");
			adStatRedisInfo.setCurrentHour_mobile_pv_num("0");

			Jedis client = jedisPool.getJedis();

			String jsonStr = JsonUtil.beanToJson(adStatRedisInfo);
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			Set<String> jsonKeys = jsonObj.keySet();
			for (String field : jsonKeys) {
				String value = jsonObj.getString(field);
				if (StringUtils.isNotBlank(value)) {
					client.hset(AdConstant.MATERIAL_STAT_KEY_PREFIX + adMId,
							field, value);
				}
			}

			jedisPool.releaseBrokenJedis(client);

			}catch(Exception e){
				logger.error("添加物料统计信息出错，redis统计信息出错");
			}
			return true;
	}

	public List<AdMaterial> findAdMaterialListByUserid(int userid) {
		return adMatrialDao.findAdMaterialListByUserid(userid);
	}
	@Override
	public List<AdMaterial> findCheckedAdMaterialListByUserid(int userid) {
		return adMatrialDao.findCheckedAdMaterialListByUserid(userid);
	}
	public AdMaterial findAdMaterialById(int userid, int id) {
		return adMatrialDao.findAdMaterialById(userid, id);
	}

	public int deleteAdMaterialById(int userid, int id) {
		return adMatrialDao.updateAdMaterialStatus(userid, id);
	}
	//更新物料实体，加入图片尺寸 值
	public int editAdMaterial(AdMaterial material) {
		AdMaterial history = findAdMaterialById(material.getUserid(), material.getId());
		history.setLinkUrl(material.getLinkUrl());
		history.setMaterialName(material.getMaterialName());
		history.setMaterialDesc(material.getMaterialDesc());
		history.setMaterialSize(material.getMaterialSize());
		history.setMaterialType(material.getMaterialType());
		history.setMonitorLink(material.getMonitorLink());
		history.setMType(material.getMType());
		history.setRichText(material.getRichText());
		history.setTargetUrl(material.getTargetUrl());
		history.setThirdMonitor(material.getThirdMonitor());
		history.setUserid(material.getUserid());
		history.setMaterialValue(material.getMaterialValue());
		history.setCoverFlag(material.getCoverFlag());
		return adMatrialDao.updateAdMaterial(history);
	}

	@Override
	public int insertMaterialAndGetKey(AdMaterial material) {
		return adMatrialDao.insertMaterialAndGetKey(material);
	}

	@Override
	public List<AdMaterial> findAdMaterialListByAdId(int adId) {
		return adMatrialDao.findAdMaterialListByAdId(adId);
	}

	@Override
	public AdMaterial findAdMaterialById(String id) {
		return adMatrialDao.findAdMaterialById(id);
	}
	
	/**
	 * 根据语料ID查找与之关联的广告
	 * @author xiaoming
	 */

	@Override
	public List<AdInstance> getListByadIds(String ad_m_id ,String userid) {
		StringBuffer ad_ids = new StringBuffer();
		List<AdMLink> adMLinks = adMatrialDao.getA_M_LbYad_mid(ad_m_id);
		if(adMLinks != null && adMLinks.size() > 0){
			for(AdMLink admlink : adMLinks){
				ad_ids.append(admlink.getAdId() + ",");
			}
			String ids = ad_ids.toString().substring(0,ad_ids.toString().length() - 1);
			List<AdInstance> lists = adMatrialDao.getAdInstancesByAdId(ids, userid);
			if(lists != null && lists.size() > 0){
				return lists;
			}
		}
		return null;
	}

	@Override
	public List<AdMaterialCache> findAdMaterialCacheListByAdId(String adId) {
		return adMatrialDao.findMaterialByAdId(adId);
	}

}
