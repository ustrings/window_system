package com.hidata.ad.web.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.ad.web.dao.AdCheckDao;
import com.hidata.ad.web.dto.AdCheckConfigDto;
import com.hidata.ad.web.dto.AdCheckProcessDto;
import com.hidata.ad.web.dto.AdInstanceDto;
import com.hidata.ad.web.dto.TAdCheckConfigDto;
import com.hidata.ad.web.dto.TAdCheckProcessDto;
import com.hidata.framework.db.DBManager;

@Component
public class AdCheckDaoImpl implements AdCheckDao{

	@Autowired
	private DBManager db;
	
	@Override
	public List<AdCheckConfigDto> getAdCheckConfigInfo() {
		String sql="select * from ad_check_config";
		return db.queryForListObject(sql, AdCheckConfigDto.class);
	}

	@Override
	public int insertAdCheckProcess(AdCheckProcessDto adCheckProcessDto) {
	
		return db.insertObjectAndGetAutoIncreaseId(adCheckProcessDto);
	}

	@Override
	public List<AdCheckProcessDto> findAdCheckProcessByAdId(String adId) {
		String sql="select * from ad_check_process where ad_id=?";
		Object[] args = new Object[]{adId};
		return db.queryForListObject(sql, args,AdCheckProcessDto.class);
	}

	@Override
	public int updateAdCheckSts(AdCheckProcessDto adCheckProcessDto) {
		String sql="update ad_check_process set check_sts=?,check_desc=?,sts_date=? where id=?";
		Object[] args = new Object[]{adCheckProcessDto.getCheckSts(),adCheckProcessDto.getCheckDesc(),
				adCheckProcessDto.getStsDate(),adCheckProcessDto.getId()};
		return db.update(sql,args);
	}

	@Override
	public int deleteAdCheckProcessByAdId(int id) {
		String sql="delete from ad_check_process where ad_id=?";
		Object[] args = new Object[]{id};
		return db.update(sql,args);
	}

	@Override
	public int updateAdInstanceByAdId(AdInstanceDto adInstanceDto) {
		String sql="update ad_instance set ad_toufang_sts=? where ad_id=?";
		Object[] args = new Object[]{adInstanceDto.getAdToufangSts(),adInstanceDto.getAdId()};
		return db.update(sql,args);
	}

	@Override
	public List<TAdCheckConfigDto> getTAdCheckConfigInfo() {
		String sql="select * from t_ad_check_config";
		return db.queryForListObject(sql, TAdCheckConfigDto.class);
	}

	@Override
	public int insertTAdCheckProcess(TAdCheckProcessDto t_adCheckProcess) {
		
		return db.insertObjectAndGetAutoIncreaseId(t_adCheckProcess);
	}

	@Override
	public List<TAdCheckProcessDto> finTAdCheckProcessByAdId(String adId) {
		String sql="select * from t_ad_check_process where ad_id=?";
		Object[] args = new Object[]{adId};
		return db.queryForListObject(sql, args,TAdCheckProcessDto.class);
	}

	@Override
	public void deleteTAdCheckProcessByAdId(int id) {
		String sql="delete from t_ad_check_process where ad_id=?";
		Object[] args = new Object[]{id};
		db.update(sql,args);	
	}

}
