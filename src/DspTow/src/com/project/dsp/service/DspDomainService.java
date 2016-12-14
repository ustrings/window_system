package com.project.dsp.service;

import java.util.List;
import com.project.dsp.entity.Dsp_Ad;
import com.project.dsp.entity.Dsp_Domain;

public interface DspDomainService {
	/**
     * @Description: 查询用户信息
     * @param usersMap
     * @return List<Dsp_Domain>
     */
    public List<Dsp_Domain> queryDspDomainList(Dsp_Domain dsp_Domain);
    
}
