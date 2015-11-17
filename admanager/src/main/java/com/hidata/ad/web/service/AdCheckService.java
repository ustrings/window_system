package com.hidata.ad.web.service;

import java.util.List;

import com.hidata.ad.web.dto.AdCheckConfigDto;
import com.hidata.ad.web.dto.AdCheckProcessDto;
import com.hidata.ad.web.dto.AdInstanceDto;
import com.hidata.ad.web.dto.TAdCheckConfigDto;
import com.hidata.ad.web.dto.TAdCheckProcessDto;

/**
 * 广告审核
 * @author ssq
 *
 */
public interface AdCheckService {

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
	public int updateAdCheckSts(AdCheckProcessDto AdCheckProcessDto);

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
	 * 根据审核人插入广告
	 * @param t_adCheckProcess
	 */
	public int insertTAdCheckProcess(TAdCheckProcessDto t_adCheckProcess);
    /**
     * 根据广告Id查找广告审核进度
     * @param valueOf
     * @return
     */
	public List<TAdCheckProcessDto> findTAdCheckProcessByAdId(String valueOf);
    /**
     * 删除广告审核信息
     * @param parseInt
     */
	public void deleteTAdCheckProcessByAdId(int parseInt);
}

