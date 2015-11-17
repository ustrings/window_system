package com.hidata.ad.web.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.ad.web.dao.AdCrowdFindInfoADUDao;
import com.hidata.ad.web.dto.AdCrowdFindInfo;
import com.hidata.ad.web.dto.GisCrowdDto;
import com.hidata.ad.web.dto.IntegerModelCrowdDto;
import com.hidata.ad.web.dto.KeyWordCrowdDto;
import com.hidata.ad.web.dto.UrlCrowdDto;
import com.hidata.ad.web.model.IntrestLabelCrowd;
import com.hidata.framework.db.DBManager;

/**
 * 人群操作接口实现类
 * @author ZTD
 *
 */
@Component
public class AdCrowdFindInfoADUDaoImpl implements AdCrowdFindInfoADUDao {
	@Autowired
	private DBManager db;
	
	@Override
	public int addAdCrowFindInfo(AdCrowdFindInfo info) {
		String sql = "insert into crowd_find_info (crowd_name,crowd_sts,create_date,express_date,user_id, direct_type) values (?,?,?,?,?,?)";
		db.update(sql,new String[]{info.getCrowdName(),info.getCrowdSts(),info.getCreateDate(),info.getExpressDate(),info.getUserId(),info.getDirectType()});
		int id = db.queryForInt("SELECT LAST_INSERT_ID()");
		return id;
//		return db.insertObjectAndGetAutoIncreaseId(info);
	}
	@Override
	public int editAdCrowFindInfo(AdCrowdFindInfo info) {
		StringBuilder sql = new StringBuilder("update crowd_find_info set crowd_name=? ,express_date=?,direct_type=? where crowd_id=?");
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(info.getCrowdName());
		paramList.add(info.getExpressDate());
		paramList.add(info.getDirectType());
		paramList.add(info.getCrowdId());
		
		return db.update(sql.toString(), paramList.toArray());
	}
	@Override
	public int addKeyWordCrowd(KeyWordCrowdDto dto) {
		return db.insertObject(dto);
	}
	@Override
	public int delKeyWordCrowdByCrowdId(String crowdId){
		StringBuilder sql = new StringBuilder("delete from  keyword_crowd where crowd_id=?");
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(crowdId);
		return db.update(sql.toString(), paramList.toArray());
	}

	@Override
	public int addUrlCrowd(UrlCrowdDto dto) {
		return db.insertObject(dto);
	}
	@Override
	public int delUrlCrowdByCrowdId(String crowdId) {
		StringBuilder sql = new StringBuilder("delete from  url_crowd where crowd_id=?");
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(crowdId);
		return db.update(sql.toString(), paramList.toArray());
	}
	public final String INSERT_SQL = 
			"insert into gis_crowd (centry_addr, x_position, y_position, distance_value, crowd_id, sts, sts_date,fetch_cicle) " +
			" values (?, ?, ?, ?, ?, ?, ?, ?)";
	@Override
	public int addGisCrowd(GisCrowdDto dto) {
		Object[] args = new Object[]{
				dto.getCentryAddr(), dto.getxPosition(), dto.getyPosition(), dto.getDistanceValue(),
				dto.getCrowdId(), dto.getSts(), dto.getStsDate(), dto.getFetchCicle()
		};
		return db.update(INSERT_SQL, args);
	}
	@Override
	public int delGisCrowd(String crowdId){
		StringBuilder sql = new StringBuilder("delete from  gis_crowd where crowd_id=?");
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(crowdId);
		return db.update(sql.toString(), paramList.toArray());
	}
	@Override
	public int addIntegerModelCrowdDto(IntegerModelCrowdDto dto) {
		return db.insertObject(dto);
	}
	
	@Override
	public int delIntegerModelCrowdDto(String crowdId){
		StringBuilder sql = new StringBuilder("delete from  integer_model_crowd where crowd_id=?");
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(crowdId);
		return db.update(sql.toString(), paramList.toArray());
	}
	
	   /**
     * 添加兴趣标签定向
     */
    @Override
    public int addIntrestCrowdInfo(IntrestLabelCrowd intrestDto) {
    	
    	String sql ="insert into intrest_label_crowd (fetch_cicle,label_value,crowd_id,sts,sts_date) values (?,?,?,?,?)";
    	
        return db.update(sql, new String[]{intrestDto.getFetchCicle(),intrestDto.getLabelValue(),intrestDto.getCrowdId(),intrestDto.getSts(),intrestDto.getSts_date()});
    }
 
    @Override
	public int delIntrestCrowdInfoByCrowdId(String crowdId){
		StringBuilder sql = new StringBuilder("delete from  intrest_label_crowd where crowd_id=?");
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(crowdId);
		return db.update(sql.toString(), paramList.toArray());
	}
}
