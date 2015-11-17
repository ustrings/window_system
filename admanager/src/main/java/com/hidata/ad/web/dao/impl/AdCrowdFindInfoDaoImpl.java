package com.hidata.ad.web.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.ad.web.dao.AdCrowdFindInfoDao;
import com.hidata.ad.web.dto.AdCrowdFindInfo;
import com.hidata.ad.web.dto.AdCrowdFindInfoShowDto;
import com.hidata.ad.web.dto.AdCrowdFindSum;
import com.hidata.framework.db.DBManager;

/**
 * 人群操作接口实现类
 * 
 * @author ZTD
 * 
 */
@Component
public class AdCrowdFindInfoDaoImpl implements AdCrowdFindInfoDao {
	
	/**
	 * 
	 */
	private static Logger logger = LoggerFactory.getLogger(AdCrowdFindInfoDaoImpl.class);
	
    @Autowired
    private DBManager db;

    @Override
    public List<AdCrowdFindInfoShowDto> getCrowInfoByUserId(String userId) {

        String QUERY_BY_USERID = " select dbfind.* ,dbsum.crowd_num,dbsum.updateTime from " +
        " (SELECT crowd_id, crowd_name, crowd_sts, create_date,express_date,user_id " +
        "from crowd_find_info where user_id='" + userId + "' and crowd_sts!='D') " +
        " as dbfind   left join " +
       " (SELECT crowd_num,dt as updateTime,crowd_id from crowd_find_sum order by dt desc ) as dbsum  on dbfind.crowd_id=dbsum.crowd_id group by crowd_id ";
        
        return db.queryForListObject(QUERY_BY_USERID, AdCrowdFindInfoShowDto.class);
    }

    private final String UPDATE_SQL = "update crowd_find_info set crowd_sts =? where crowd_id=?";

    @Override
    public int updateState(String id, String stat) {
        Object[] args = new Object[] { stat, id };
        return db.update(UPDATE_SQL, args);
    }

    private final String QUERY_CROWD_BY_CROWDID_SQL = "select * from crowd_find_info where crowd_id=?";

    @Override
    public AdCrowdFindInfo getCrowdByCrowdId(String crowdId) {
        Object[] args = new Object[] { crowdId };
        return db.queryForBean(QUERY_CROWD_BY_CROWDID_SQL, args, AdCrowdFindInfo.class);
    }

    private final String QUERY_CROWDSUM_BY_CROWDID_SQL = "select * from crowd_find_sum where crowd_id=?";

    @Override
    public List<AdCrowdFindSum> getAdCrowdFindSumList(String crowdId) {
        Object[] args = new Object[] { crowdId };
        return db.queryForListObject(QUERY_CROWDSUM_BY_CROWDID_SQL, AdCrowdFindSum.class, args);
    }

    @Override
    public AdCrowdFindSum getAdCrowdFindSum(String crowdId, String date) {
        String sql = "select * from crowd_find_sum where crowd_id=" + crowdId
                + " and dt=str_to_date('" + date + "','%Y%m%d')";
        
        return db.queryForObject(sql, AdCrowdFindSum.class);
    }

    private final String QUERY_CROWD_USER_ID_SQL = "select * from crowd_find_info where user_id=? and crowd_sts!='D'";

    @Override
    public List<AdCrowdFindInfo> getAdCrowdInfosByUserId(String userId) {
        Object[] args = new Object[] { userId };
        return db.queryForListObject(QUERY_CROWD_USER_ID_SQL, args, AdCrowdFindInfo.class);
    }

    @Override
    public void updateAdCrowdSum(AdCrowdFindSum adCrowdFindSum) {
//        String sql = "update crowd_find_sum set crowd_num= ? where crowd_id = ? and dt =date_format(now(),'%Y%m%d')";
//        String sql = "update crowd_find_sum set crowd_num= ? where crowd_id = ? and dt =str_to_date(" + adCrowdFindSum.getDt() + ",'%Y%m%d')";
        String sql = "update crowd_find_sum set crowd_num= ? where crowd_id = ? and dt =date_format(" + adCrowdFindSum.getDt() + ",'%Y%m%d')";

        db.update(sql, new Object[] { adCrowdFindSum.getCrowdNum(), adCrowdFindSum.getCrowdId() });
    }

    @Override
    public int insertAdCrowdFindSum(AdCrowdFindSum adCrowdFindSum) {
        return db.insertObjectAndGetAutoIncreaseId(adCrowdFindSum);
    }

    @Override
    public List<AdCrowdFindSum> findCrowdDetailInfo(String date) {

        String sql = "select count(distinct ad_acct ,ua) as crowd_num,crowd_id from crowd_result where dt =str_to_date(" + date + ",'%Y%m%d') group by crowd_id";

        return db.queryForListObject(sql, AdCrowdFindSum.class);
    }

    String FIND_ALL_ADCROWD_FINDINFO = "select * from crowd_find_info where crowd_sts='A'";
	@Override
	public List<AdCrowdFindInfo> getAllInstance() {
		return db.queryForListObject(FIND_ALL_ADCROWD_FINDINFO, AdCrowdFindInfo.class);
	}

}
