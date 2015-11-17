package com.hidata.ad.web.service;

import java.util.List;
import java.util.Map;

import com.hidata.ad.web.dto.AdCrowdFindInfo;
import com.hidata.ad.web.dto.AdCrowdFindInfoShowDto;
import com.hidata.ad.web.dto.GisCrowdDto;
import com.hidata.ad.web.dto.IntegerModelCrowdDto;
import com.hidata.ad.web.dto.IntrestLabelCrowdDto;
import com.hidata.ad.web.dto.KeyWordDirectDto;
import com.hidata.ad.web.model.AllLabelAdPlan;
import com.hidata.ad.web.model.CrowdPortrait;

/**
 * 人群操作接口
 * @author ZTD
 *
 */
public interface AdCrowdFindInfoService {
	public List<AdCrowdFindInfoShowDto> getCrowInfoByUserId(String userId);
	public int updateState(String id, String stat);
	public int updateState(String[] ids, String stat);
	public Map<String, Object> getFindSum(String crowIds, String userId);
	public AdCrowdFindInfo getCrowdById(String crowdId);
	
	/**
	 * 查询关键词定向信息
	 * @param crowdId
	 * @return
	 * @throws Exception
	 */
	public KeyWordDirectDto queryKeyWordDirectInfoByCrowdId(String crowdId)throws Exception;
	/**
	 * 查询只能模型
	 * @param crowdId
	 * @return
	 * @throws Exception
	 */
	public IntegerModelCrowdDto queryIntegerModelCrowdListByCrowdId(String crowdId) throws Exception;
	/**
	 * 查询lbs信息
	 * @param crowdId
	 * @return
	 * @throws Exception
	 */
	public GisCrowdDto queryGisCrowdListByCrowdId(String crowdId)throws Exception;
	/**
	 * 查询兴趣定向
	 * @param crowdId
	 * @return
	 * @throws Exception
	 */
	public List<IntrestLabelCrowdDto> queryIntrestLabelCrowdByCrowdId(String crowdId)
			throws Exception;
	/**
	 * 获取选中和半选tree 节点   key   treeid   value:true  选中   false:半选
	 * @param crowdId
	 * @param selectTreeMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, Boolean> getSelectTreeMapByCrowdId(String crowdId,
			Map<String, Boolean> selectTreeMap) throws Exception;
	public List<CrowdPortrait> queryCrowdPortraitByCrowdId(String crowdId) throws Exception;
	
	   /**
     * 获取选中和半选tree 节点   key   treeid   value:true  选中   false:半选
     * @param adId 广告ID
     * @param selectTreeMap
     * @return
     * @throws Exception
     */
    public Map<String, Boolean> getSelectTreeMapByadId(String adId,
            Map<String, Boolean> selectTreeMap) throws Exception;
    /**
     * 通过AdId查询对应的兴趣标签信息
     * @param adId
     * @return
     * @author zhoubin
     */
    public List<AllLabelAdPlan> qryAllLabelInfoByAdId(String adId);
}
