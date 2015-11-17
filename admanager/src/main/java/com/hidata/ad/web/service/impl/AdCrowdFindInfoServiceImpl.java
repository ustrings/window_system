package com.hidata.ad.web.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.ad.web.dao.AdCrowdFindInfoDao;
import com.hidata.ad.web.dao.CrowdPortraitDao;
import com.hidata.ad.web.dao.GisCrowdDao;
import com.hidata.ad.web.dao.IAllBaseLabelDao;
import com.hidata.ad.web.dao.IAllLabelAdPlanDao;
import com.hidata.ad.web.dao.IIntrestCrowdDao;
import com.hidata.ad.web.dao.IntegerModelCrowdDao;
import com.hidata.ad.web.dao.KeywordCrowdDao;
import com.hidata.ad.web.dao.UrlCrowdDao;
import com.hidata.ad.web.dto.AdCrowdFindInfo;
import com.hidata.ad.web.dto.AdCrowdFindInfoShowDto;
import com.hidata.ad.web.dto.AdCrowdFindSum;
import com.hidata.ad.web.dto.GisCrowdDto;
import com.hidata.ad.web.dto.IntegerModelCrowdDto;
import com.hidata.ad.web.dto.IntrestLabelCrowdDto;
import com.hidata.ad.web.dto.KeyWordCrowdDto;
import com.hidata.ad.web.dto.KeyWordDirectDto;
import com.hidata.ad.web.dto.UrlCrowdDto;
import com.hidata.ad.web.model.AllBaseLabel;
import com.hidata.ad.web.model.AllLabelAdPlan;
import com.hidata.ad.web.model.CrowdPortrait;
import com.hidata.ad.web.model.IntrestLabelCrowd;
import com.hidata.ad.web.model.IntrestLabelTree;
import com.hidata.ad.web.service.AdCrowdFindInfoService;
import com.hidata.ad.web.util.DateUtils;

/**
 * 人群操作接口
 * 
 * @author ZTD
 * 
 */
@Component
public class AdCrowdFindInfoServiceImpl implements AdCrowdFindInfoService {
    @Autowired
    private AdCrowdFindInfoDao adCrowdInfoDao;

    @Autowired
    private KeywordCrowdDao keywordCrowdDao;

    @Autowired
    private UrlCrowdDao urlCrowdDao;

    @Autowired
    private IntegerModelCrowdDao integerModelCrowdDao;

    @Autowired
    private GisCrowdDao gisCrowdDao;

    @Autowired
    private IIntrestCrowdDao iIntrestCrowdDao;

    @Autowired
    private IAllLabelAdPlanDao iAllLabelAdPlanDao;

    @Autowired
    private IAllBaseLabelDao iallBaseLabelDao;

    @Autowired
    private CrowdPortraitDao crowdPortraitDao;

    @Override
    public List<AdCrowdFindInfoShowDto> getCrowInfoByUserId(String userId) {
        return adCrowdInfoDao.getCrowInfoByUserId(userId);
    }

    @Override
    public int updateState(String id, String stat) {
        return adCrowdInfoDao.updateState(id, stat);
    }

    @Override
    public int updateState(String[] ids, String stat) {
        for (String id : ids) {
            try {
                updateState(id, stat);
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }
        return 1;
    }

    @Override
    public Map<String, Object> getFindSum(String crowIds, String userId) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (crowIds != null && !crowIds.equals("")) {
            result = findByCrowdIds(crowIds);
        } else {
            result = findByUserId(userId);
        }
        return result;
    }

    /**
     * 通过 人群 id 来获取人群数量
     * 
     * @param crowdIds
     * @return
     */
    private Map<String, Object> findByUserId(String userId) {
        List<AdCrowdFindInfo> crowdFindInfos = adCrowdInfoDao.getAdCrowdInfosByUserId(userId);
        StringBuffer sb = new StringBuffer();
        if (crowdFindInfos != null) {
            for (AdCrowdFindInfo info : crowdFindInfos) {
                sb.append(info.getCrowdId()).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return findByCrowdIds(sb.toString());
    }

    /**
     * 通过用户 id 来获取数量
     * 
     * @param userId
     * @return
     */
    private Map<String, Object> findByCrowdIds(String crowdIds) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (crowdIds != null) {
            String[] crowdIdArr = crowdIds.split(",");
            for (String crowdId : crowdIdArr) {
                // 获取人群的名称
                String crowdName = adCrowdInfoDao.getCrowdByCrowdId(crowdId).getCrowdName();
                // 获取对应人群的数量信息
                Date start = new Date(new Date().getTime() - 6 * 60 * 60 * 24 * 1000L);
                Date end = new Date();
                String dateFormat = "yyyyMMdd";
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

                String startDateStr = sdf.format(start);
                String endDateStr = sdf.format(end);
                List<String> dateList = DateUtils.getDateArr(startDateStr, endDateStr, dateFormat);
                // var d2 = { "label": "Europe (EU27)","data":[[0, 3], [4, 8], [8, 5], [9, 13]]};

                StringBuffer dataBuf = new StringBuffer().append("[");
                for (int i = 0; i < 7; i++) {
                    String dateStr = dateList.get(i);
                    if (dateStr.equals("20140905")) {
                        System.out.println("");
                    }
                    AdCrowdFindSum sum = adCrowdInfoDao.getAdCrowdFindSum(crowdId, dateStr);
                    // if (sum!=null) {
                    // dataBuf.append("[").append(dateStr).append(",").append(sum.getCrowdNum()).append("]").append(",");
                    // } else {
                    // dataBuf.append("[").append(dateStr).append(",").append(0).append("]").append(",");
                    // }

                    if (sum != null) {
                        dataBuf.append("[").append(dateStr).append(",").append(sum.getCrowdNum())
                                .append("]").append(",");
                    } else {
                        dataBuf.append("[").append(dateStr).append(",").append(0).append("]")
                                .append(",");
                    }
                }
                dataBuf.deleteCharAt(dataBuf.length() - 1);
                dataBuf.append("]");
                result.put(crowdName, dataBuf.toString());
            }
        }

        return result;
    }

    @Override
    public AdCrowdFindInfo getCrowdById(String crowdId) {
        return adCrowdInfoDao.getCrowdByCrowdId(crowdId);
    }

    @Override
    public IntegerModelCrowdDto queryIntegerModelCrowdListByCrowdId(String crowdId)
            throws Exception {
        IntegerModelCrowdDto paramObj = new IntegerModelCrowdDto();
        paramObj.setCrowdId(crowdId);
        List<IntegerModelCrowdDto> integerModelCrowdList = integerModelCrowdDao
                .queryIntegerModelCrowdDtoListByCondition(paramObj);
        IntegerModelCrowdDto integerModelCrowdDto = null;
        if (integerModelCrowdList != null && integerModelCrowdList.size() > 0) {
            integerModelCrowdDto = integerModelCrowdList.get(0);
        }
        return integerModelCrowdDto;
    }

    @Override
    public GisCrowdDto queryGisCrowdListByCrowdId(String crowdId) throws Exception {
        GisCrowdDto paramObj = new GisCrowdDto();
        paramObj.setCrowdId(crowdId);
        List<GisCrowdDto> list = gisCrowdDao.queryGisCrowdListByCondition(paramObj);
        GisCrowdDto gisCrowdDto = new GisCrowdDto();
        if (list != null && list.size() > 0) {
            gisCrowdDto = list.get(0);
        }
        return gisCrowdDto;
    }

    @Override
    public KeyWordDirectDto queryKeyWordDirectInfoByCrowdId(String crowdId) throws Exception {

        KeyWordDirectDto keyWordDirectDto = new KeyWordDirectDto();

        KeyWordCrowdDto keyWordCrowdDto = new KeyWordCrowdDto();
        keyWordCrowdDto.setCrowdId(crowdId);
        List<KeyWordCrowdDto> keyWordCrowdList = keywordCrowdDao
                .queryKeywordCrowdListByCondition(keyWordCrowdDto);

        UrlCrowdDto urlCrowdDto = new UrlCrowdDto();
        urlCrowdDto.setCrowdId(crowdId);
        List<UrlCrowdDto> urlCrowdDtoList = urlCrowdDao.queryUrlCrowdListByCondition(urlCrowdDto);

        StringBuilder keywords = new StringBuilder();
        StringBuilder urls = new StringBuilder();
        if (keyWordCrowdList != null) {
            for (KeyWordCrowdDto entity : keyWordCrowdList) {
                keyWordDirectDto.setIsJisoujitou(entity.getIsJisoujitou());
                keyWordDirectDto.setFetchCicle(entity.getFetchCicle());
                keyWordDirectDto.setSearchType(entity.getSearchType());
                keyWordDirectDto.setKeywordSearchDate(entity.getSearchDate());
                keyWordDirectDto.setKeywordSearchNum(entity.getSearchNum());
                keyWordDirectDto.setKeyWdMatchType(entity.getMatchType());
                keywords.append(entity.getKeyWd()).append("\n");
            }
        }

        if (urlCrowdDtoList != null) {
            for (UrlCrowdDto entity : urlCrowdDtoList) {
                keyWordDirectDto.setIsJisoujitou(entity.getIsJisoujitou());
                keyWordDirectDto.setFetchCicle(entity.getFetchCicle());
                keyWordDirectDto.setUrlSearchDate(entity.getMatchDate());
                keyWordDirectDto.setUrlSearchNum(entity.getMatchNum());
                keyWordDirectDto.setUrlMatchType(entity.getMatchType());
                urls.append(entity.getUrl()).append("\n");
            }
        }

        if (keywords.length() > 0) {
            keyWordDirectDto.setKeyWds(keywords.toString());
        }
        if (urls.length() > 0) {
            keyWordDirectDto.setUrls(urls.toString());
        }
        return keyWordDirectDto;

    }

    /**
     * 
     * @param crowdId
     * @throws Exception
     */
    @Override
    public List<IntrestLabelCrowdDto> queryIntrestLabelCrowdByCrowdId(String crowdId)
            throws Exception {

        List<IntrestLabelCrowd> intrestLabelCrowdList = iIntrestCrowdDao
                .queryIntrestLabelCrowdListByCrowdId(crowdId);
        List<IntrestLabelCrowdDto> intrestLabelCrowdDtoList = null;
        for (IntrestLabelCrowd obj : intrestLabelCrowdList) {
            if (intrestLabelCrowdDtoList == null) {
                intrestLabelCrowdDtoList = new ArrayList<IntrestLabelCrowdDto>();
            }

            IntrestLabelTree intrestLabelTree = iIntrestCrowdDao
                    .queryIntrestLabelTreeByLabelVal(obj.getLabelValue());
            IntrestLabelCrowdDto intrestLabelCrowdDto = new IntrestLabelCrowdDto();
            intrestLabelCrowdDto.setLabelName(intrestLabelTree.getLabelName());
            intrestLabelCrowdDto.setObjid(obj.getLabelValue());
            intrestLabelCrowdDto.setFetchCicle(obj.getFetchCicle());
            StringBuilder label = new StringBuilder();
            label.append(intrestLabelTree.getLabelName());
            if (intrestLabelTree != null) {
                label = getLabelByLabelVal(intrestLabelTree.getParentLabelValue(), label);
            }
            intrestLabelCrowdDto.setLabel(label.toString());
            intrestLabelCrowdDtoList.add(intrestLabelCrowdDto);
        }
        return intrestLabelCrowdDtoList;
    }

    public StringBuilder getLabelByLabelVal(String parentLabelVal, StringBuilder label) {
        IntrestLabelTree parentLabel = iIntrestCrowdDao
                .queryIntrestLabelTreeByLabelVal(parentLabelVal);
        if (parentLabel != null && !"all".equals(parentLabel.getLabelValue())) {
            label.insert(0, parentLabel.getLabelName() + ">");
            label = getLabelByLabelVal(parentLabel.getParentLabelValue(), label);
        }
        return label;
    }

    /**
     * 通过AdId查询对应的兴趣标签信息
     * 
     * @param adId
     * @return
     * @author zhoubin
     */
    public List<AllLabelAdPlan> qryAllLabelInfoByAdId(String adId) {
        List<AllLabelAdPlan> allLabelAdPlanList = new ArrayList<AllLabelAdPlan>();

        try {
            allLabelAdPlanList = iAllLabelAdPlanDao.queryallLabelListByAdId(adId);

            for (AllLabelAdPlan iterLabelAdPlan : allLabelAdPlanList) {

                AllBaseLabel retBaseLabel = iallBaseLabelDao
                        .qryAllBaseLabelByLabelValue(iterLabelAdPlan.getLabelValue());
                StringBuilder label = new StringBuilder();
                label.append(retBaseLabel.getName());
                if (retBaseLabel != null) {
                    label = createShowLabelInfo(retBaseLabel.getPid(), label);
                }
                iterLabelAdPlan.setSelectLabelShow(label.toString());
                iterLabelAdPlan.setLabelName(retBaseLabel.getName());
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return allLabelAdPlanList;

    }

    public StringBuilder createShowLabelInfo(String parentLabelVal, StringBuilder label) {

        AllBaseLabel parentLabel = iallBaseLabelDao.qryAllBaseLabelByLabelValue(parentLabelVal);

        if (parentLabel != null && !"0".equals(parentLabel.getId())) {
            label.insert(0, parentLabel.getName() + ">");
            label = createShowLabelInfo(parentLabel.getPid(), label);
        }
        return label;
    }

    /**
     * 获取选中树节点map
     * 
     * @param crowdId
     * @throws Exception
     */
    @Override
    public Map<String, Boolean> getSelectTreeMapByCrowdId(String crowdId,
            Map<String, Boolean> selectTreeMap) throws Exception {

        List<IntrestLabelCrowd> intrestLabelCrowdList = iIntrestCrowdDao
                .queryIntrestLabelCrowdListByCrowdId(crowdId);

        for (IntrestLabelCrowd obj : intrestLabelCrowdList) {
            IntrestLabelTree intrestLabelTree = iIntrestCrowdDao
                    .queryIntrestLabelTreeByLabelVal(obj.getLabelValue());

            selectTreeMap.put(obj.getLabelValue(), true);// 选中
            if (intrestLabelTree != null) {
                selectTreeMap = getPartSelectTreeMapByParentLabelVal(
                        intrestLabelTree.getParentLabelValue(), selectTreeMap);
            }
        }
        return selectTreeMap;
    }

    private Map<String, Boolean> getPartSelectTreeMapByParentLabelVal(String parentLabelVal,
            Map<String, Boolean> selectTreeMap) throws Exception {
        IntrestLabelTree parentLabel = iIntrestCrowdDao
                .queryIntrestLabelTreeByLabelVal(parentLabelVal);
        if (parentLabel != null && !"all".equals(parentLabel.getLabelValue())) {
            selectTreeMap.put(parentLabel.getLabelValue(), false);// 半选
            selectTreeMap = getPartSelectTreeMapByParentLabelVal(parentLabel.getParentLabelValue(),
                    selectTreeMap);
        }
        return selectTreeMap;
    }

    /**
     * 获取选中和半选tree 节点 key treeid value:true 选中 false:半选
     * 
     * @param adId
     *            广告ID
     * @param selectTreeMap
     * @return
     * @throws Exception
     */
    public Map<String, Boolean> getSelectTreeMapByadId(String adId,
            Map<String, Boolean> selectTreeMap) throws Exception {

        List<AllLabelAdPlan> intrestLabelCrowdList = iAllLabelAdPlanDao
                .queryallLabelListByAdId(adId);

        for (AllLabelAdPlan obj : intrestLabelCrowdList) {
            AllBaseLabel curBaseLabel = iallBaseLabelDao.qryAllBaseLabelByLabelValue(obj
                    .getLabelValue());

            selectTreeMap.put(obj.getLabelValue(), true);// 选中
            if (curBaseLabel != null) {
                selectTreeMap = createParentHalfSelectStatus(curBaseLabel.getPid(), selectTreeMap);
            }
        }
        return selectTreeMap;
    }

    private Map<String, Boolean> createParentHalfSelectStatus(String parentLabelVal,
            Map<String, Boolean> selectTreeMap) throws Exception {
        AllBaseLabel allBaseLabel = iallBaseLabelDao.qryAllBaseLabelByLabelValue(parentLabelVal);
        if (allBaseLabel != null && !"0".equals(allBaseLabel.getId())) {
            selectTreeMap.put(allBaseLabel.getId(), false);// 半选
            selectTreeMap = createParentHalfSelectStatus(allBaseLabel.getPid(), selectTreeMap);
        }
        return selectTreeMap;
    }

    @Override
    public List<CrowdPortrait> queryCrowdPortraitByCrowdId(String crowdId) throws Exception {
        CrowdPortrait crowdPortrait = null;
        CrowdPortrait paramObj = new CrowdPortrait();
        paramObj.setCrowdId(crowdId);
        List<CrowdPortrait> crowdPortraitList = crowdPortraitDao
                .queryCrowdPortraitListByCodition(paramObj);

        return crowdPortraitList;
    }
}
