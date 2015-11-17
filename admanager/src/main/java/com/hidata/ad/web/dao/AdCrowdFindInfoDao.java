package com.hidata.ad.web.dao;

import java.util.List;

import com.hidata.ad.web.dto.AdCrowdFindInfo;
import com.hidata.ad.web.dto.AdCrowdFindInfoShowDto;
import com.hidata.ad.web.dto.AdCrowdFindSum;

/**
 * 人群操作接口
 * @author ZTD
 *
 */
public interface AdCrowdFindInfoDao {
	/**
	 * 根据用户的 id 获取用户的人群信息
	 * @param userId
	 * @return
	 */
	public List<AdCrowdFindInfoShowDto> getCrowInfoByUserId(String userId);
	
	/**
	 * 修改用户的人群的状态
	 * @param id
	 * @param stat
	 * @return
	 */
	public int updateState(String id, String stat);
	
	/**
	 * 根据人群 id 获取人群的数量信息
	 * @param crowdId
	 * @return
	 */
	public AdCrowdFindInfo getCrowdByCrowdId(String crowdId);
	
	/**
	 * 根据人群 id 获取对应的信息
	 * @param crowdId
	 * @return
	 */
	public List<AdCrowdFindSum> getAdCrowdFindSumList(String crowdId);
	
	/**
	 * 根据日期和人群id 获取对应的信息
	 * @param crowdId
	 * @param date
	 * @return
	 */
	public AdCrowdFindSum getAdCrowdFindSum(String crowdId, String date);
	
	public List<AdCrowdFindInfo> getAdCrowdInfosByUserId(String userId);
	
	/**
	 * 更新人群数量
	 * @param adCrowdFindSum
	 * @author zhoubin
	 */
    public void updateAdCrowdSum(AdCrowdFindSum adCrowdFindSum);
    
    /**
     * 插入人群总数
     * @param adCrowdFindSum
     * @return
     * @author zhoubin
     */
    public int insertAdCrowdFindSum(AdCrowdFindSum adCrowdFindSum);
    
    /**
     * 查询人群总数
     * @return
     * @author zhoubin
     */
    public List<AdCrowdFindSum>  findCrowdDetailInfo(String date);
    
    public List<AdCrowdFindInfo> getAllInstance();
	
}
