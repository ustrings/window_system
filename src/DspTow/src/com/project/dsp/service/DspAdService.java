package com.project.dsp.service;

import java.util.List;
import java.util.Map;

import com.project.dsp.entity.Dsp_Ad;
import com.project.dsp.entity.Dsp_Domain;

public interface DspAdService {
	/**
     * @Description: 查询用户信息
     * @param usersMap
     * @return List<Dsp_Users>
     */
    public List<Dsp_Ad> queryDspAdList(Dsp_Ad dsp_Ad);

    /**
     * 修改状态
     */
    public int updateDspAdStatus(Map<String, Object> adMap);
    
    /**
     * 查询详情
     */
    public List<Dsp_Ad> queryOneDspAdList(int id);
    
    /**
     * 查询域名表domain
     */
    public List<Dsp_Domain> queryOneDspDomainList(int ad_id);
    
    /**
     * 删除表中信息
     * 
     */
    public int deleteDspAd(int id);
    public int deleteDspDomain(int ad_id);
    
    /**
     * 添加信息
     */
    public List<Dsp_Ad> queryOneDspAdListPrio(int prio);
    public int insertDspAd(Dsp_Ad ad);
    public int insertDspDomain(Dsp_Domain domain);
    
    /**
     * 修改投送数量
     */
    public int updateDspAdNum(int id);
    public int updateDspDomainNum(int id);

	public String queryAdbyDomaid(int domainId);
	
	public int updateDspAd(Dsp_Ad ad);
	public int updateDspDomain(Dsp_Domain domain);
	
	
	public String insertORUpdate(Dsp_Ad ad);
}
