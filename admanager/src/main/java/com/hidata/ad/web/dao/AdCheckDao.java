package com.hidata.ad.web.dao;

import java.util.List;

import com.hidata.ad.web.dto.AdCheckConfigDto;
import com.hidata.ad.web.dto.AdCheckProcessDto;
import com.hidata.ad.web.dto.AdInstanceDto;
import com.hidata.ad.web.dto.TAdCheckConfigDto;
import com.hidata.ad.web.dto.TAdCheckProcessDto;

public interface AdCheckDao {
    
	/**
	 * 获取审核人列表
	 * @return
	 */
	public List<AdCheckConfigDto> getAdCheckConfigInfo();
    /**
     * 插入审核信息
     * @param adCheckProcessDto
     * @return
     */
	public int insertAdCheckProcess(AdCheckProcessDto adCheckProcessDto);
	/**
	 * 通过广告Id获取该广告的审核信息
	 * @param adId
	 * @return
	 */
	public List<AdCheckProcessDto> findAdCheckProcessByAdId(String adId);
	/**
	 * 更新广告的审核状态
	 * @param AdCheckProcessDto
	 * @return
	 */
	public int updateAdCheckSts(AdCheckProcessDto adCheckProcessDto);
	
	/**
	 * 根据广告Id删除该广告的审核记录
	 * @param id
	 * @return
	 */
	public int deleteAdCheckProcessByAdId(int id);
	/**
	 * 更新广告的投放状态
	 * @param adId
	 * @return
	 */
	public int updateAdInstanceByAdId(AdInstanceDto adInstanceDto);
	/**
	 * 获取特殊审核人
	 * @return
	 */
	public List<TAdCheckConfigDto> getTAdCheckConfigInfo();
	/**
	 * 根据审核人插入广告ts
	 * @param t_adCheckProcess
	 * @return
	 */
	public int insertTAdCheckProcess(TAdCheckProcessDto t_adCheckProcess);
	/**
	 * 根据广告Id查找广告审核信息
	 * @param adId
	 * @return
	 */
	public List<TAdCheckProcessDto> finTAdCheckProcessByAdId(String adId);
	/**
	 *根据广告id删除广告审核信息
	 * @param adId
	 */
	public void deleteTAdCheckProcessByAdId(int adId);
}
