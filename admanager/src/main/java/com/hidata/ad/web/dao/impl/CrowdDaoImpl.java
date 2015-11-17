package com.hidata.ad.web.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hidata.ad.web.dao.CrowdDao;
import com.hidata.ad.web.model.Crowd;
import com.hidata.ad.web.model.CrowdKeyword;
import com.hidata.ad.web.model.CrowdRule;
import com.hidata.ad.web.model.CrowdUrl;
import com.hidata.ad.web.model.KeywordOcean;
import com.hidata.ad.web.model.UrlOcean;
import com.hidata.ad.web.service.CrowdService;
import com.hidata.framework.db.DBManager;
import com.hidata.framework.util.DateUtil;

/**
 * 
 * @Description: TODO(人群的数据库操作)
 * @author chenjinzhao
 * @date 2013年12月29日 下午2:41:46
 * 
 */

@Repository
public class CrowdDaoImpl implements CrowdDao {

	@Autowired
	private DBManager db;

	@Override
	public int addCrowd(Crowd crowd) {
		return db.insertObjectAndGetAutoIncreaseId(crowd);
	}

	@Override
	public int updateCrowd(Crowd crowd) {
		String sql = "update crowd set crowd_name = ?,crowd_desc = ? where crowd_id = ?  ";
		Object[] args = new Object[] { crowd.getCrowdName(),
				crowd.getCrowdDesc(), crowd.getCrowdId() };

		return db.update(sql, args);
	}

	@Override
	public int delCrowd(Crowd crowd) {
		String sql = "update crowd set sts = ? where crowd_id = ?  ";
		Object[] args = new Object[] { crowd.getSts(), crowd.getCrowdId() };
		return db.update(sql, args);
	}

	@Override
	public Crowd getOneCrowd(Crowd crowd) {

		Crowd crowdInstance = new Crowd();
		String sql = "select crowd_id,crowd_name,crowd_desc,create_time,sts,user_id from crowd where crowd_id = ?";
		Object[] args = new Object[] { crowd.getCrowdId() };

		List<Crowd> crList = db.queryForListObject(sql, args, Crowd.class);
		crowdInstance = crList.get(0);
		return crowdInstance;

	}

	@Override
	public int updateCrowdSts(Crowd crowd) {
		String sql = "update crowd set sts = ? where crowd_id = ?  ";
		Object[] args = new Object[] { crowd.getSts(), crowd.getCrowdId() };
		return db.update(sql, args);

	}

	@Override
	public int addCrowdKeywords(CrowdKeyword ck) {
		return db.insertObject(ck);
	}

	@Override
	public int delCrowdKeywords(CrowdKeyword ck) {
		String sql = "delete from crowd_keyword where crowd_id = ?";
		Object[] args = new Object[] { ck.getCrowdId() };

		return db.update(sql, args);
	}

	@Override
	public List<CrowdKeyword> getCrowdKeywords(CrowdKeyword ck) {

		List<CrowdKeyword> ckList;

		String sql = "select ck_id,keyword,crowd_id,source_type,create_time,sts from crowd_keyword where crowd_id = ? ";
		Object[] args = new Object[] { ck.getCrowdId() };

		ckList = db.queryForListObject(sql, args, CrowdKeyword.class);

		return ckList;
	}

	@Override
	public int addCrowdUrls(CrowdUrl cu) {
		return db.insertObject(cu);
	}

	@Override
	public int delCrowdUrls(CrowdUrl cu) {

		String sql = "delete from crowd_url where crowd_id = ?";
		Object[] args = new Object[] { cu.getCrowdId() };
		return db.update(sql, args);
	}

	@Override
	public List<CrowdUrl> getCrowdUrls(CrowdUrl cu) {
		List<CrowdUrl> cuList = new ArrayList<CrowdUrl>();
		String sql = "select cu_id,url,crowd_id,source_type,create_time,sts from crowd_url where crowd_id = ? ";
		Object[] args = new Object[] { cu.getCrowdId() };

		cuList = db.queryForListObject(sql, args, CrowdUrl.class);
		return cuList;
	}

	@Override
	public int addCrowdRule(CrowdRule cr) {

		return db.insertObject(cr);

	}

	@Override
	public int delCrowdRule(CrowdRule cr) {
		String sql = "delete from crowd_rule where crowd_id = ?";
		Object[] args = new Object[] { cr.getCrowdId() };
		return db.update(sql, args);
	}

	@Override
	public CrowdRule getOneCrowdRule(CrowdRule cr) {

		CrowdRule crRet = new CrowdRule();

		List<CrowdRule> crList;

		String sql = "select cr_id,crowd_id,kw_num,kw_time_type,kw_time_num,"
				+ "url_num,url_time_type,url_time_num,url_switch,"
				+ "create_time,sts,remark,kw_switch "
				+ "from crowd_rule where crowd_id = ? ";
		Object[] args = new Object[] { cr.getCrowdId() };

		crList = db.queryForListObject(sql, args, CrowdRule.class);
		if(crList!=null && crList.size() >0){
			crRet = crList.get(0);
		}
		return crRet;
	}

	@Override
	public List<Crowd> getCrowdList(Crowd crowd) {

		List<Crowd> crowdList = null;
		String sql = "select crowd_id,crowd_name,crowd_desc,create_time,sts,user_id from crowd where sts='"
				+ CrowdService.CROWD_STS_A + "'  and user_id = ?";
		Object[] args = new Object[] { crowd.getUserId() };
		crowdList = db.queryForListObject(sql, args, Crowd.class);

		return crowdList;
	}

	@Override
	public List<KeywordOcean> getKeywordOceanList(KeywordOcean ko) {
		
		List<KeywordOcean> kwoList = new ArrayList<KeywordOcean>();
		String sql = "select kwo_id,keyword,sts,first_type,create_time,remark from keyword_ocean where keyword like '%"+ko.getKeyword()+"%'";
		kwoList = db.queryForListObject(sql, KeywordOcean.class);
		
		return kwoList;
	}

	@Override
	public List<UrlOcean> getUrlOceanList(UrlOcean uo) {
		
		List<UrlOcean> uoList = new ArrayList<UrlOcean>();
		String sql = "select uo_id,url,content,sts,first_type,create_time,remark from url_ocean where content like '%"+uo.getContent()+"%'";
		uoList = db.queryForListObject(sql, UrlOcean.class);
		return uoList;
	}

}
