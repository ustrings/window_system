package com.project.dsp.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.project.dsp.entity.Dsp_Domain;

@Repository
public interface DspDomainDao {
	 /**
     * @Description: 查询用户表，是否存在该用户
     * @param usersMap
     * @return List<Dsp_Domain>
     */
    public List<Dsp_Domain> queryDspDomainList(Dsp_Domain dsp_Domain);
    
}
