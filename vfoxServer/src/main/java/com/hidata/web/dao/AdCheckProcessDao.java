package com.hidata.web.dao;

import java.util.List;
import java.util.Map;

import com.hidata.web.dto.AdCheckConfigDto;
import com.hidata.web.dto.AdCheckProcessDto;
import com.hidata.web.dto.AdExtLinkDto;
import com.hidata.web.dto.AdInstanceDto;
import com.hidata.web.dto.CheckHistoryDto;
import com.hidata.web.dto.TAdCheckConfigDto;
import com.hidata.web.dto.TAdCheckProcessDto;
import com.hidata.web.util.Pager;

/**
 * 广告审核DAO
 * Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年12月23日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
public interface AdCheckProcessDao {
    
    public int insertAdCheckProcess(AdCheckProcessDto adCheckProcessDto);
    
    public int insertAdCheckConfig(AdCheckConfigDto adCheckConfigDto);
    
    public AdCheckConfigDto  findAdCheckConfigByPk(String adCheckConfigPkId);
    
    public AdCheckProcessDto  findAdCheckProcessByPk(String adCheckProcessId);
    
    /**
     * 根据角色Id删除角色信息
     * @param adCheckConfigPk
     * @return
     */
	public int deleteAdCheckConfigById(String adCheckConfigPk);
    /**
     * 更新角色信息
     * @param adCheckConfigDto
     * @return
     */
	public int updateAdCheckConfig(AdCheckConfigDto adCheckConfigDto);
    
	/**
	 * 查找绑定该账号的角色
	 * @param userAcctRel
	 * @return
	 */
	public List<AdCheckConfigDto> findAdCheckConfigByUserAcctRel(
			String userAcctRel);
    
	/**
	 * 根据广告Id查找表ad_ext_link
	 * @param adInstanceId
	 * @return
	 */
	public AdExtLinkDto findAdExtLinkByAdInstanceId(String adInstanceId);
    
	/**
	 * 更新广告审核信息
	 * @param adCheckProcessDto
	 * @return
	 */
	public void updateAdCheckProcessInfo(AdCheckProcessDto adCheckProcessDto);
    /**
     * 根据广告Id查找广告信息
     * @param adId
     * @return
     */
	public AdInstanceDto findAdInstanceByAdId(String adId);
    
	/**
	 * 更新广告投放状态
	 * @param adInstanceDto
	 * @return
	 */
	public void updateAdTouFangSts(AdInstanceDto adInstanceDto);
    
	/**
	 * 根据广告Id查找该广告的审核历史进度 ad_check_process
	 * @param adId
	 * @return
	 */
	public List<AdCheckProcessDto> findAdCheckProcessDtoByAdId(String adId);
    
	/**
	 * 根据广告Id查找该广告的审核历史
	 * @param adId
	 * @return
	 */
	public List<CheckHistoryDto> findCheckHistoryByAdId(String adId);
    
	/**
	 * 根据审核人Id删除该审核人审核的广告
	 * @param adCheckConfigPk
	 */
	public int deleteAdCheckProcessByCheckUserId(String adCheckConfigPk);
    /**
     *根据审核人Id查找该审核人下的广告
     * @param checkUserId
     * @return
     */
	public List<AdCheckProcessDto> findAdCheckProcessDtoByCheckUserId(String checkUserId);
    /**
     * 查找用户绑定账号
     * @param userAcctRel
     * @return
     */
	public List<TAdCheckConfigDto> findTAdCheckConfigDtoByUserAcctRel(
			String userAcctRel);
    /**
     * 插入特殊审核人
     * @param t_adCheckConfigDto
     * @return
     */
	public int insertTAdCheckConfig(TAdCheckConfigDto t_adCheckConfigDto);
    /**
     * 查找特殊审核历史
     * @param adId
     * @return
     */
	public List<CheckHistoryDto> findTCheckHistoryByAdId(String adId);

	/**
     * 根据id查找广告审核记录
     * @param adCheckProcessPk
     * @return
     */
	public TAdCheckProcessDto findTAdCheckProcessDtoByPk(String adCheckProcessPk);

    /**
     * 更新审核状态
     * @param t_adCheckProcessDto
     * @return
     */
	public int updateTAdCheckProcessInfo(TAdCheckProcessDto t_adCheckProcessDto);

    /**
     * 根据广告id查找审核记录
     * @param adId
     * @return
     */
	public List<TAdCheckProcessDto> findTAdCheckProcessDtoByAdId(String adId);

}
