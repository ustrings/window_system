package com.vaolan.adtimer.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;

import com.hidata.framework.util.JsonUtil;
import com.vaolan.adtimer.dao.NHTStatDao;
import com.vaolan.adtimer.model.AdInstance;
import com.vaolan.adtimer.model.AdMaterialCache;
import com.vaolan.adtimer.service.NHTStatService;

@Service
@Transactional
public class NHTStatServiceImpl implements NHTStatService {

	@Autowired
	private NHTStatDao nhtDao;

	private static Logger logger = Logger.getLogger(NHTStatServiceImpl.class);

	@Override
	public void nhtStat2NnhtProcess(String adId, Jedis client)
			throws IOException {

		AdInstance adInst = nhtDao.getAdInstanceByAdId(adId);

		if (StringUtils.isBlank(adInst.getAd3statCodeSub())) {
			logger.error("NHT统计模式 向非NHT模式转化的时候，sub代码不存在，要求sub存在 temp不存在，从sub 转入 temp ！");
		}

		if (StringUtils.isNotBlank(adInst.getAd3statCodeTemp())) {
			logger.error("NHT统计模式 向非NHT模式转化的时候，temp代码已经存在，要求sub存在 temp不存在，从sub 转入 temp ！");
			return;
		}

		nhtDao.subStat2TempStat(adId);

		// 同时更新缓存
		List<AdMaterialCache> adMcacheList = nhtDao.findMaterialByAdId(adId);

		for (AdMaterialCache material : adMcacheList) {
			client.hset(adId, material.getAd_m_id(),
					JsonUtil.beanToJson(material));
		}

	}

	@Override
	public void nNht2nhtStatProcess(String adId, Jedis client)
			throws IOException {

		AdInstance adInst = nhtDao.getAdInstanceByAdId(adId);

		if (StringUtils.isNotBlank(adInst.getAd3statCodeSub())) {
			logger.error("第二天，非NHT统计模式 向NHT模式转化的时候，sub代码已经存在，要求sub不存在 temp存在，从temp 转入 sub ！");
		}

		if (StringUtils.isBlank(adInst.getAd3statCodeTemp())) {
			logger.error("第二天，非NHT统计模式 向NHT模式转化的时候，temp代码不存在，要求sub不存在 temp存在，从temp 转入 sub ！");
			return;
		}

		nhtDao.tempStat2SubStat(adId);

		// 同时更新缓存
		List<AdMaterialCache> adMcacheList = nhtDao.findMaterialByAdId(adId);

		for (AdMaterialCache material : adMcacheList) {
			client.hset(adId, material.getAd_m_id(),
					JsonUtil.beanToJson(material));
		}
	}

}
