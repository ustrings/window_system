package com.project.dsp.service;

import java.util.List;
import java.util.Map;

import com.project.dsp.entity.Dsp_Users;

public interface DspUsersService {
	/**
     * @Description: 查询用户信息
     * @param usersMap
     * @return List<Dsp_Users>
     */
    public Dsp_Users queryDspUsers(Dsp_Users dsp_Users);
    
}
