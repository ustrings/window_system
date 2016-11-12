package com.project.dsp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.project.dsp.entity.Dsp_Ad;
import com.project.dsp.entity.Dsp_Domain;

@Repository
public interface DspAdDao {
	 /**
     * @Description: 查询用户表，是否存在该用户
     * @param usersMap
     * @return List<Dsp_Ad>
     */
   List<Dsp_Ad> queryDspAdList(Dsp_Ad Dsp_Ad);
    
    /**
     * 修改状态
     * @param usersMap
     * @return
     */
    public int updateDspAdStatus(Map<String, Object> usersMap);
    
    /**
     * 查询详情
     */
    public List<Dsp_Ad> queryOneDspAdList(int id);
    
    /**
     * 查看域名表domain
     */
    public List<Dsp_Domain> queryOneDspDomainList(int ad_id);
    
    
    public String queryAdbyDomaid(int domaid);
    
    /**
     * 删除表中信息
     */
    public int deleteDspAd(int id);
    
    /**
     * 删除域名表中信息
     */
    public int deleteDspDomain(int ad_id);
    
    /**
     * 添加信息
     */
    public List<Dsp_Ad> queryOneDspAdListPrio(int prio);
    public int insertDspAd(Dsp_Ad ad);
    public int insertDspDomain(Dsp_Domain domain);
    
    /***
     * 修改投送数量
     */
    public int updateDspAdNum(int id);
    public int updateDspDomainNum(int id);
    
    
    /**
     * 修改需求
     * **/
    public int updateDspAd(Dsp_Ad ad);
    public int updateDspDomain(Dsp_Domain domain);
}

