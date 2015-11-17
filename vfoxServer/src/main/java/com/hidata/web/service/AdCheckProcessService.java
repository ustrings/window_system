package com.hidata.web.service;

import java.util.Map;
import java.util.List;

import com.hidata.web.dto.CheckHistoryDto;
import com.hidata.web.dto.AdCheckConfigDto;
import com.hidata.web.dto.AdCheckProcessDto;
import com.hidata.web.dto.AdExtLinkDto;
import com.hidata.web.dto.AdInstanceDto;
import com.hidata.web.dto.TAdCheckConfigDto;
import com.hidata.web.dto.TAdCheckProcessDto;
import com.hidata.web.util.Pager;

/**
 * 广告审核接口
 * Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年12月24日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
public interface AdCheckProcessService {
    
    public int addAdCheckProcess(AdCheckProcessDto  adCheckProcessDto);
    
 
    public int addAdCheckConfig(AdCheckConfigDto  adCheckConfigDto);
    
    
    public AdCheckProcessDto  findAdCheckProcessDtoByPk(String adCheckProcessPk);
    
    
    public AdCheckConfigDto  findAdCheckConfigDtoByPk(String adCheckConfigPk); 
    
    /**
     * 角色维护页面分页展示
     * @param map
     * @param curPage
     * @return
     * 
     * @author ssq
     */
    public Pager getRoleListPager(Map<String,String> map,String curPage);

    /**
     * 根据Id删除角色信息
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
     * 广告审核状态展示
     * @param map
     * @param curPage
     * @return
     */
	public Pager getCheckListPager(Map<String, String> map, String curPage);

    /**
     * 查找绑定该账号的角色
     * @return
     */
	public List<AdCheckConfigDto> findAdCheckConfigDtoByUserAcctRel(String userAcctRel);

    /**
     * 根据广告Id查找表ad_ext_link
     * @param adInstanceId
     * @return
     */
	public AdExtLinkDto findAdExtLinkByAdInstanceId(String adInstanceId);

    /**
     * 更新广告审核信息表
     * @param adCheckProcessDto
     */
	public void updateAdCheckProcessInfo(AdCheckProcessDto adCheckProcessDto);

    /**
     * 根据广告Id查找广告信息 ad_instance
     * @param adId
     * @return
     */
	public AdInstanceDto findAdInstanceByAdId(String adId);

    /**
     * 更改广告投放状态
     * @param adInstanceDto
     */
	public void updateAdTouFangSts(AdInstanceDto adInstanceDto);

    /**
     * 广告审核进度查看列表展示
     * @param map
     * @param curPage
     * @return
     */
	public Pager getCheckHistoryPager(Map<String, String> map, String curPage);

    /**
     * 根据广告Id查找广告的审核进度 表ad_check_process
     * @param adId
     * @return
     */
	public List<AdCheckProcessDto> findAdCheckProcessDtoByAdId(String adId);

    /**
     * 根据该广告ID查找该广告Id的审核历史
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
     * 角色维护页面分页展示
     * @param map
     * @param curPage
     * @return
     * 
     * @author ssq
     */
	public Pager getTRoleListPager(Map<String, String> map, String curPage);

	/**
     * 查找绑定该账号的角色
     * @return
     */
	public List<TAdCheckConfigDto> findTAdCheckConfigDtoByUserAcctRel(
			String userAcctRel);

    /**
     * 添加特殊的审核人
     * @param t_adCheckConfigDto
     * @return 
     */
	public int addTAdCheckConfig(TAdCheckConfigDto t_adCheckConfigDto);

	/**
     * 广告审核进度查看列表展示  TS
     * @param map
     * @param curPage
     * @return
     */
	public Pager getTCheckHistoryPager(Map<String, String> map, String curPage);

    /**
     * 查找审核历史
     * @param adId
     * @return
     */
	public List<CheckHistoryDto> findTCheckHistoryByAdId(String adId);

    /**
     * 展示待审核列表
     * @param map
     * @param curPage
     * @return
     */
	public Pager getTCheckListPager(Map<String, String> map, String curPage);

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
