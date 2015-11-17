package com.hidata.web.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.framework.db.DBManager;
import com.hidata.web.dao.AdCheckProcessDao;
import com.hidata.web.dto.AdCheckConfigDto;
import com.hidata.web.dto.AdCheckProcessDto;
import com.hidata.web.dto.AdExtLinkDto;
import com.hidata.web.dto.AdInstanceDto;
import com.hidata.web.dto.CheckHistoryDto;
import com.hidata.web.dto.TAdCheckConfigDto;
import com.hidata.web.dto.TAdCheckProcessDto;
import com.hidata.web.util.Pager;

/**
 * 广告审核管理 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年12月24日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
@Component
public class AdCheckProcessDaoImpl implements AdCheckProcessDao {
    @Autowired
    private DBManager db;

    @Override
    public int insertAdCheckProcess(AdCheckProcessDto adCheckProcessDto) {
        return db.insertObjectAndGetAutoIncreaseId(adCheckProcessDto);
    }

    @Override
    public int insertAdCheckConfig(AdCheckConfigDto adCheckConfigDto) {
        return db.insertObjectAndGetAutoIncreaseId(adCheckConfigDto);
    }

    @Override
    public AdCheckConfigDto findAdCheckConfigByPk(String adCheckConfigPkId) {

        String sql = "select * from ad_check_config where id = ?";

        Object[] args = new Object[] { adCheckConfigPkId };

        return db.queryForObject(sql, args, AdCheckConfigDto.class);
    }

    @Override
    public AdCheckProcessDto findAdCheckProcessByPk(String adCheckProcessId) {

        String sql = "select * from ad_check_process where id = ?";

        Object[] args = new Object[] { adCheckProcessId };

        return db.queryForObject(sql, args, AdCheckProcessDto.class);
    }

	@Override
	public int deleteAdCheckConfigById(String adCheckConfigPk) {
		String sql ="delete from ad_check_config where id = ?";
		Object[] args = new Object[]{adCheckConfigPk};
		return db.update(sql,args);
	}

	@Override
	public int updateAdCheckConfig(AdCheckConfigDto adCheckConfigDto) {
		String sql="update ad_check_config set check_role_name=?,user_name=?,dept_name=?,user_acct_rel=?,"
				+ "tel_nbr=?,email=?,sts_date=? where id=?";
		Object[] args = new Object[]{adCheckConfigDto.getCheckRoleName(),adCheckConfigDto.getUserName(),adCheckConfigDto.getDeptName(),
				adCheckConfigDto.getUserAcctRel(),adCheckConfigDto.getTelNbr(),adCheckConfigDto.getEmail(),adCheckConfigDto.getStsDate(),
				adCheckConfigDto.getId()};
		return db.update(sql,args);
	}

	@Override
	public List<AdCheckConfigDto> findAdCheckConfigByUserAcctRel(
			String userAcctRel) {
		String  sql="select * from ad_check_config where user_acct_rel=?";
		Object[] args = new Object[]{userAcctRel};
		return db.queryForListObject(sql,args,AdCheckConfigDto.class);
	}

	@Override
	public AdExtLinkDto findAdExtLinkByAdInstanceId(String adInstanceId) {
		String sql="select * from ad_ext_link where ad_instance_id =?";
		Object[] args = new Object[]{adInstanceId};
		return db.queryForObject(sql,args,AdExtLinkDto.class);
	}

	@Override
	public void updateAdCheckProcessInfo(AdCheckProcessDto adCheckProcessDto) {
		String sql="update ad_check_process set check_sts=?,check_desc=?,sts_date=? where id=?";
		Object[] args = new Object[]{adCheckProcessDto.getCheckSts(),adCheckProcessDto.getCheckDesc(),
				adCheckProcessDto.getStsDate(),adCheckProcessDto.getId()};
		db.update(sql,args);
	}

	@Override
	public AdInstanceDto findAdInstanceByAdId(String adId) {
		String sql="select * from ad_instance where ad_id=?";
		Object[] args = new Object[]{adId};
		return db.queryForObject(sql,args,AdInstanceDto.class);
	}

	@Override
	public void updateAdTouFangSts(AdInstanceDto adInstanceDto) {
		String sql="update ad_instance set ad_toufang_sts=? where ad_id=?";
		Object[] args = new Object[]{adInstanceDto.getAdToufangSts(),adInstanceDto.getAdId()};
		db.update(sql,args);
	}

	@Override
	public List<AdCheckProcessDto> findAdCheckProcessDtoByAdId(String adId) {
		String sql = "select * from ad_check_process where ad_id = ?";
		Object[] args = new Object[]{adId};
		return db.queryForListObject(sql, args,AdCheckProcessDto.class);
	}

	@Override
	public List<CheckHistoryDto> findCheckHistoryByAdId(String adId) {
		String sql= "select acc.user_name as check_username,acc.dept_name as check_deptname,acp.ad_id as ad_id,acp.check_user_id as check_userid,acp.check_sts as check_sts,acp.check_desc as check_desc,acp.sts_date as check_date"
                    +" from ad_check_config acc,ad_check_process acp"
                    +" where acp.check_user_id = acc.id and acp.ad_id=?";
		Object[] args = new Object[]{adId};
		return db.queryForListObject(sql,args,CheckHistoryDto.class);
	}

	@Override
	public int deleteAdCheckProcessByCheckUserId(String adCheckConfigPk) {
		String sql="delete from ad_check_process where check_user_id=?";
		Object[] args = new Object[]{adCheckConfigPk};
		return db.update(sql,args);
	}

	@Override
	public List<AdCheckProcessDto> findAdCheckProcessDtoByCheckUserId(
			String checkUserId) {
		String sql ="select * from ad_check_process where check_user_id=?";
		Object[] args = new Object[]{checkUserId};
		return db.queryForListObject(sql, args,AdCheckProcessDto.class);
	}

	@Override
	public List<TAdCheckConfigDto> findTAdCheckConfigDtoByUserAcctRel(
			String userAcctRel) {
		String  sql="select * from t_ad_check_config where user_acct_rel=?";
		Object[] args = new Object[]{userAcctRel};
		return db.queryForListObject(sql,args,TAdCheckConfigDto.class);
	}

	@Override
	public int insertTAdCheckConfig(TAdCheckConfigDto t_adCheckConfigDto) {
		return db.insertObjectAndGetAutoIncreaseId(t_adCheckConfigDto);
	}

	@Override
	public List<CheckHistoryDto> findTCheckHistoryByAdId(String adId) {
		String sql= "select acc.user_name as check_username,acc.dept_name as check_deptname,acp.ad_id as ad_id,acp.check_user_id as check_userid,acp.check_sts as check_sts,acp.check_desc as check_desc,acp.sts_date as check_date"
                +" from t_ad_check_config acc,t_ad_check_process acp"
                +" where acp.check_user_id = acc.id and acp.ad_id=?";
	Object[] args = new Object[]{adId};
	return db.queryForListObject(sql,args,CheckHistoryDto.class);
	}

	@Override
	public TAdCheckProcessDto findTAdCheckProcessDtoByPk(String adCheckProcessPk) {
		String sql = "select * from t_ad_check_process where id = ?";

        Object[] args = new Object[] { adCheckProcessPk };

        return db.queryForObject(sql, args, TAdCheckProcessDto.class);
	}

	@Override
	public int updateTAdCheckProcessInfo(TAdCheckProcessDto t_adCheckProcessDto) {
		String sql="update t_ad_check_process set check_sts=?,check_desc=?,sts_date=? where id=?";
		Object[] args = new Object[]{t_adCheckProcessDto.getCheckSts(),t_adCheckProcessDto.getCheckDesc(),
				t_adCheckProcessDto.getStsDate(),t_adCheckProcessDto.getId()};
		return db.update(sql,args);
	}

	@Override
	public List<TAdCheckProcessDto> findTAdCheckProcessDtoByAdId(String adId) {
		String sql = "select * from t_ad_check_process where ad_id = ?";
		Object[] args = new Object[]{adId};
		return db.queryForListObject(sql, args,TAdCheckProcessDto.class);
	}

}
