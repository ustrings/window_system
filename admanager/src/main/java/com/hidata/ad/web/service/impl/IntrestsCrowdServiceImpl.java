package com.hidata.ad.web.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hidata.ad.web.dao.AdCrowdFindInfoDao;
import com.hidata.ad.web.dao.IAllBaseLabelDao;
import com.hidata.ad.web.dao.IIntrestCrowdDao;
import com.hidata.ad.web.dto.AdCrowdFindInfo;
import com.hidata.ad.web.dto.AdCrowdFindSum;
import com.hidata.ad.web.model.AllBaseLabel;
import com.hidata.ad.web.model.IntrestLabelCrowd;
import com.hidata.ad.web.model.IntrestLabelTree;
import com.hidata.ad.web.service.IIntrestsCrowdService;

/**
 * 兴趣定向标签 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年8月27日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
@Service
public class IntrestsCrowdServiceImpl implements IIntrestsCrowdService {

    private static Logger logger = LoggerFactory.getLogger(IntrestsCrowdServiceImpl.class);

    @Autowired
    private IIntrestCrowdDao iintrestCrowdDao;

    @Autowired
    private AdCrowdFindInfoDao adCrowdFindInfoDao;

    @Autowired
    private IAllBaseLabelDao iallBaseLabelDao;

    /**
     * 添加用户兴趣定向
     */
    @Override
    public int addIntrestCrowdInfo(IntrestLabelCrowd intrestDto) {
        return iintrestCrowdDao.addIntrestCrowdInfo(intrestDto);
    }

    /**
     * 查询兴趣标签树
     */
    @Override
    public List<IntrestLabelTree> qryIntrestLabelChildrenByParent(IntrestLabelTree paraLabelTree) {
        return iintrestCrowdDao.qryIntrestLabelChildrenByParent(paraLabelTree);
    }

    /**
     * 查询标签子节点
     * 
     * @param paraAllLabelTree
     * @return
     * @author zhoubin
     */
    public List<AllBaseLabel> qryAllBaseLabelChildrenByParent(AllBaseLabel paraAllLabelTree) {
        return iallBaseLabelDao.qryAllBaseLabelChildrenByParent(paraAllLabelTree);
    }

    /**
     * 定时更新人群数量
     */
    @Override
    public void fixExecuteUpdateCrowdNum(String date) {
        // // 查询所有的 crowId
        // List<AdCrowdFindInfo> allInstance = adCrowdFindInfoDao.getAllInstance();
        // // 循环统计点击次数
        // for (AdCrowdFindInfo instance : allInstance) {
        //
        // }

        //
        logger.info(" fixExecuteUpdateCrowdNum 开始 ");
        // 查询所有待处理数据
        Long queryStart = System.currentTimeMillis();
        List<AdCrowdFindSum> resultList = adCrowdFindInfoDao.findCrowdDetailInfo(date);
        Long queryEnd = System.currentTimeMillis();
        logger.info(" fixExecuteUpdateCrowdNum ->findCrowdDetailInfo querytime:"
                + (queryEnd - queryStart));

        if (CollectionUtils.isEmpty(resultList)) {
            return;
        }

        for (AdCrowdFindSum adCrowdFindSum : resultList) {

            if ("0".equals(adCrowdFindSum.getCrowdNum())) {
                continue;
            }

            AdCrowdFindSum curAdCrowdFindSum = adCrowdFindInfoDao.getAdCrowdFindSum(
                    adCrowdFindSum.getCrowdId(), date);

            // 为空就插入，否则就更新
            if (curAdCrowdFindSum == null) {

                adCrowdFindSum.setDt(date);

                adCrowdFindInfoDao.insertAdCrowdFindSum(adCrowdFindSum);

            } else {
                adCrowdFindSum.setDt(date);
                adCrowdFindInfoDao.updateAdCrowdSum(adCrowdFindSum);
            }

        }

        logger.info(" fixExecuteUpdateCrowdNum 结束");
    }

    

    /**
     * 查找对应标签
     * @param paraBaseLabel
     * @return
     * @author zhoubin
     */
    public AllBaseLabel qryAllBaseLabelByLabelVal(String  paraLabelCode)
    {
        return iallBaseLabelDao.qryAllBaseLabelByLabelValue(paraLabelCode);
    }

	@Override
	public List<AllBaseLabel> qryAllBaseLabelByPId(String parentId) {
		
		return iallBaseLabelDao.qryAllBaseLabelByPId(parentId);
	}


}
