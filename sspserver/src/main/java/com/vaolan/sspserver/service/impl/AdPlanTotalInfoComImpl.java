package com.vaolan.sspserver.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaolan.sspserver.dao.AdCategoryDao;
import com.vaolan.sspserver.dao.AdDeviceDao;
import com.vaolan.sspserver.dao.AdInstanceDao;
import com.vaolan.sspserver.dao.AdKeywordDao;
import com.vaolan.sspserver.dao.AdLabelRelDao;
import com.vaolan.sspserver.dao.AdMaterialDao;
import com.vaolan.sspserver.dao.AdRegionDao;
import com.vaolan.sspserver.dao.AdSiteDao;
import com.vaolan.sspserver.dao.AdTimeFilterDao;
import com.vaolan.sspserver.dao.AdTimeFrequencyDao;
import com.vaolan.sspserver.dao.AdUserFrequencyDao;
import com.vaolan.sspserver.dao.AdVclinkDao;
import com.vaolan.sspserver.dao.DMPCrowdDao;
import com.vaolan.sspserver.dao.MediaCategoryDao;
import com.vaolan.sspserver.model.AdInstance;
import com.vaolan.sspserver.model.AdPlanKeyWord;
import com.vaolan.sspserver.model.AdPlanLabelRel;
import com.vaolan.sspserver.model.AdvPlan;
import com.vaolan.sspserver.model.AllLabelBaseInfo;
import com.vaolan.sspserver.service.AdPlanTotalInfoCom;
import com.vaolan.sspserver.util.Constant;
import com.vaolan.sspserver.util.FtpUtils;
import com.vaolan.sspserver.util.JavaMd5Util;
import com.vaolan.sspserver.util.JieBaUtils;

/**
 * 按渠道加载系统可投放广告的信息 Date: 2014年7月28日 <br>
 * 
 * @author pj
 * */
@Service
public class AdPlanTotalInfoComImpl implements AdPlanTotalInfoCom {

	private static Logger logger = Logger.getLogger(AdPlanTotalInfoComImpl.class);
    @Autowired
    private AdInstanceDao adInstanceDao;

    @Autowired
    private AdMaterialDao adMaterialDao;
    
    @Autowired
    private AdUserFrequencyDao adFrequencyDao;
    
    @Autowired
    private AdTimeFrequencyDao timeFrequencyDao;

    @Autowired
    private AdCategoryDao categoryDao;

    @Autowired
    private AdSiteDao siteDao;

    @Autowired
    private AdTimeFilterDao timeFilterDao;

    @Autowired
    private MediaCategoryDao mediaCategoryDao;

    @Autowired
    private AdVclinkDao visitorsDao;

    @Autowired
    private DMPCrowdDao dmpCrowdDao;

    @Autowired
    private AdRegionDao adRegionDao;

    @Autowired
    private AdLabelRelDao adLabelRelDao;
    
    @Autowired
    private AdKeywordDao adKeywordDao;
    
    @Autowired
    private AdDeviceDao adDeviceDao;
    
    private void printAdId(List<AdInstance> adInstances) {
    	for(AdInstance instance : adInstances) {
    		System.out.println(instance.getAdId());
    	}
    }

    @Override
    public void getAdvPlans(Map<String, AdvPlan> advPlanMap, String channel) {
        List<AdInstance> adInstances = adInstanceDao.getInstanceByChannel(channel);
//        printAdId(adInstances);
        for (AdInstance instance : adInstances) {
            AdvPlan advPlan = new AdvPlan();
         
            advPlan.setAdInstance(instance);
            advPlan.setAdMaterials(adMaterialDao.getMaterialByAdId(instance.getAdId()));
            advPlan.setAdTimeFrequency(timeFrequencyDao.getFrequencyByAdId(instance.getAdId()));
            //advPlan.setAdCategorys(categoryDao.getCategoryByAdId(instance.getAdId()));
            advPlan.setAdSites(siteDao.getSiteUrlByAdId(instance.getAdId()));
            advPlan.setNegAdSites(siteDao.getNegSiteUrlByAdId(instance.getAdId()));
            advPlan.setAdUrls(siteDao.getUrlByAdId(instance.getAdId()));
            advPlan.setAdTimeFilters(timeFilterDao.getTimeFilterByAdId(instance.getAdId()));
           // advPlan.setMediaCategory(mediaCategoryDao.getMediaCategoryCodeByAdId(instance.getAdId()));
           // advPlan.setVisitors(visitorsDao.getVcIdByAdId(instance.getAdId()));
           // advPlan.setDmpCrowds(dmpCrowdDao.getCrowdIdByAdId(instance.getAdId()));
            advPlan.setIpPosTargetFilters(siteDao.getPosTargetIpByAdId(instance.getAdId()));
            advPlan.setAdAcctTargetFilters(siteDao.getTargetAdAcctByAdId(instance.getAdId()));
            advPlan.setIpNegTargetFilters(siteDao.getNegTargetIpByAdId(instance.getAdId()));
            advPlan.setRegionTargetFilters(adRegionDao.getRegionTargetByAdId(instance.getAdId()));
            advPlan.setAdExtLink(adMaterialDao.getAdExtLinkByAdId(instance.getAdId()));
            advPlan.setAdUserFrequency(adFrequencyDao.getUserFrequencyByAdId(instance.getAdId()));
            
            advPlan.setAdDeviceLinkList(adDeviceDao.getAdDeviceLinks(instance.getAdId()));
            
            
            // 加载广告标签信息
            advPlan.setAdPlanLabelRelList(this.createAdPlanLabeList(instance.getAdId()));
            // 加载关键词信息
            advPlan.setAdPlanKeyWord(this.createAdPlanKeyword(instance.getAdId()));
            advPlanMap.put(instance.getAdId(), advPlan);
            
            
        }
    }
    
    

    private AdPlanKeyWord createAdPlanKeyword(String adId) {
    	AdPlanKeyWord resultList = null;
        resultList = adKeywordDao.qryAdPlanKeyWordByAdId(adId);
        return resultList;
	}
    
    private AdPlanKeyWord createAdPlanKeywordWioutJSJT(String adId) {
    	AdPlanKeyWord resultList = null;
        resultList = adKeywordDao.qryAdPlanKeyWordNotJSJTByAdId(adId);
        return resultList;
	}

	/**
     * 生成广告对应的标签信息
     * 
     * @param adId
     * @return
     * @author zhoubin
     */
    private List<AdPlanLabelRel> createAdPlanLabeList(String adId) {
        List<AdPlanLabelRel> resultList = new ArrayList<AdPlanLabelRel>();
        resultList = adLabelRelDao.qryAdplanLabelByAdId(adId);
        
        for (AdPlanLabelRel iterAdPlanLabelRel : resultList) {
            AllLabelBaseInfo retBaseLabel = adLabelRelDao
                    .qryAllBaseLabelByLabelValue(iterAdPlanLabelRel.getLabelValue());
            StringBuilder label = new StringBuilder();
            label.append(retBaseLabel.getName());
            if (retBaseLabel != null) {
                label = createShowLabelInfo(retBaseLabel.getPid(), label);
            }
            
            logger.info("初始化标签内容：" + label);

            iterAdPlanLabelRel.setLabelName(retBaseLabel.getName());

            String[] labelNameArray = label.toString().split(">");

            String mdEnryLabelKey = JavaMd5Util.md5EncrypFirst16(labelNameArray);
            // 生成全路径加密key值
            iterAdPlanLabelRel.setFullPathNameMDVal(mdEnryLabelKey);

        }

        return resultList;
    }

    /**
     * 递归生成全路径label
     * 
     * @param parentLabelVal
     * @param label
     * @return
     * @author zhoubin
     */
    private StringBuilder createShowLabelInfo(String parentLabelVal, StringBuilder label) {

        AllLabelBaseInfo parentLabel = adLabelRelDao.qryAllBaseLabelByLabelValue(parentLabelVal);

        if (parentLabel != null && !"0".equals(parentLabel.getId())) {
            label.insert(0, parentLabel.getName() + ">");
            label = createShowLabelInfo(parentLabel.getPid(), label);
        }
        return label;
    }

	@Override
	public void getAdKeywordsvPlans(Map<String, Set<String>> keywordsMap,
			String channel) {
		// 获取需要投放的广告
		List<AdInstance> adInstances = adInstanceDao.getInstanceByChannel(channel);
		// 获取需要投放的广告的关键词信息
        for (AdInstance instance : adInstances) {
        	String adId = instance.getAdId();
            // 加载关键词信息
        	AdPlanKeyWord apkw = this.createAdPlanKeywordWioutJSJT(adId);
        	if(apkw==null) {
        		continue;
        	}
            // 判断当前的关键词的格式
        	// 如果是直接输入关键词形式就直接进行分词
        	if(apkw.getKwType().equals(Constant.KEYWORD_TYPE_KW)) {
        		logger.info("对文本框的关键词进行分词开始...");
        		assembFenci(keywordsMap, adId, apkw.getKeyWd());
        	} else if(apkw.getKwType().equals(Constant.KEYWORD_TYPE_TEXT)) {
        		// 如果是上传文件的方式就拿到文件的路径去获取内容，然后进行分词
        		logger.info("对文件的关键词进行分词开始...");
        		logger.info("文件路径：" + apkw.getKwFilePath() + " 文件名称：" + apkw.getKeyWd());
        		
        		List<String> kwContentList = FtpUtils.getFtpFileContent(apkw.getKwFilePath(), apkw.getKeyWd());
        		
        		for(String content : kwContentList) {
//        			logger.info("从" + content);
        			assembFenci(keywordsMap, adId, content);
        		}
        		kwContentList = null;
        	}
        	
        }
	}

	/**
	 * 把结果组装到关键词 hashtable
	 * @param keywordsMap
	 * @param adId
	 * @param keyWordContent
	 */
	private void assembFenci(Map<String, Set<String>> keywordsMap, String adId,
			String keyWordContent) {
		// 分词结果
		Set<String> fenciResult = JieBaUtils.getFenci(keyWordContent);
		for(String fenci :  fenciResult) {
			if(keywordsMap.containsKey(fenci)) {
				keywordsMap.get(fenci).add(adId);
			} else {
				Set<String> adIdsSet = new HashSet<String>();
				adIdsSet.add(adId);
				keywordsMap.put(fenci, adIdsSet);
			}
		}
	}
	

}
