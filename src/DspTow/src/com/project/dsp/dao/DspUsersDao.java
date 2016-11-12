package com.project.dsp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.project.dsp.entity.Dsp_Users;

@Repository
public interface DspUsersDao {
	 /**
     * @Description: 查询用户表，是否存在该用户
     * @param usersMap
     * @return List<Dsp_Users>
     */
    public Dsp_Users queryDspUsersList(Dsp_Users dsp_Users);
    
}
