package com.hidata.ad.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hidata.ad.web.dao.CrowdDao;
import com.hidata.ad.web.model.Crowd;
import com.hidata.ad.web.model.CrowdKeyword;
import com.hidata.ad.web.model.CrowdRule;
import com.hidata.ad.web.model.CrowdUrl;
import com.hidata.ad.web.model.KeywordOcean;
import com.hidata.ad.web.model.UrlOcean;
import com.hidata.ad.web.model.User;
import com.hidata.ad.web.service.CrowdService;
import com.hidata.ad.web.session.SessionContainer;
import com.hidata.ad.web.util.EncodUtil;
import com.hidata.framework.util.DateUtil;
import com.hidata.framework.util.StringUtil;

/**
 * 
 * @Description: TODO(人群服务类)
 * @author chenjinzhao
 * @date 2013年12月30日 下午12:03:40
 * 
 */
@Service
public class CrowdServiceImpl implements CrowdService {



	@Autowired
	private CrowdDao crowdDao;
	


	@Override
	@Transactional("")
	public int addCrowdConfig(String crowdName, String crowdDesc,
			String searchKwFromOcean, String searchKwFormCust,
			String urlFromOcean, String urlFromCust, String searchKwNum,
			String searchKwTimeNum, String searchKwTimeType, String urlNum,
			String urlTimeType, String urlTimeNum, String searchKwSwitch,
			String urlSwitch) {

		String ctStr = DateUtil.getCurrentDateTimeStr();
		User user = SessionContainer.getSession();

		// 添加人群基本信息
		Crowd crowd = new Crowd();
		crowd.setCrowdName(crowdName);
		crowd.setCrowdDesc(crowdDesc);
		crowd.setSts(CROWD_STS_A);
		crowd.setCreateTime(ctStr);
		crowd.setUserId(user.getUserId() + "");

		int crowdId = crowdDao.addCrowd(crowd);

		
		//添加从关键词海洋来的搜索关键词
		if (StringUtil.isNotBlank(searchKwFromOcean)) {
			String[] searchKwFromOceans = searchKwFromOcean.split(",");

			for (int i = 0; i < searchKwFromOceans.length; i++) {
				
				if(StringUtil.isNotBlank(searchKwFromOceans[i])){
					CrowdKeyword crowdKeyword = new CrowdKeyword();
					crowdKeyword.setCreateTime(ctStr);
					crowdKeyword.setKeyword(searchKwFromOceans[i]);
					crowdKeyword.setSts(CROWD_STS_A);
					crowdKeyword.setSourceType(SEARCHKW_SOURCE_TYPE_OCEAN);
					crowdKeyword.setCrowdId(crowdId + "");
					
					crowdDao.addCrowdKeywords(crowdKeyword);
				}
				
			}
		}

		//添加从客户自定义的搜索关键词
		if (StringUtil.isNotBlank(searchKwFormCust)) {
			String[] searchKwFormCusts = searchKwFormCust.split("\r\n");

			for (int i = 0; i < searchKwFormCusts.length; i++) {
				
				if(StringUtil.isNotBlank(searchKwFormCusts[i])){
					CrowdKeyword crowdKeyword = new CrowdKeyword();
					crowdKeyword.setCreateTime(ctStr);
					crowdKeyword.setKeyword(searchKwFormCusts[i]);
					crowdKeyword.setSts(CROWD_STS_A);
					crowdKeyword.setSourceType(SEARCHKW_SOURCE_TYPE_CUST);
					crowdKeyword.setCrowdId(crowdId + "");
					crowdDao.addCrowdKeywords(crowdKeyword);
				}
			
				
				
			}
		}
		
		
		
		//添加从url海洋来的url
		if (StringUtil.isNotBlank(urlFromOcean)) {
			String[] urlFromOceans = urlFromOcean.split("\r\n");

			for (int i = 0; i < urlFromOceans.length; i++) {
				
				if(StringUtil.isNotBlank(urlFromOceans[i])){
					CrowdUrl crowdUrl = new CrowdUrl();
					
					crowdUrl.setCreateTime(ctStr);
					crowdUrl.setUrl(urlFromOceans[i]);
					crowdUrl.setSts(CROWD_STS_A);
					crowdUrl.setSourceType(URL_SOURCE_TYPE_OCEAN);
					crowdUrl.setCrowdId(crowdId + "");
					
					crowdDao.addCrowdUrls(crowdUrl);
				}
				
			}
		}

		//添加从客户自定义的url
		if (StringUtil.isNotBlank(urlFromCust)) {
			String[] urlFromCusts = urlFromCust.split("\r\n");

			for (int i = 0; i < urlFromCusts.length; i++) {
				
				if(StringUtil.isNotBlank(urlFromCusts[i])){
					String url = urlFromCusts[i].trim();
					if(!url.contains("http://")){
						url = new StringBuffer("http://").append(url).toString();
					}
					url = EncodUtil.EncodeUrl(url);
					CrowdUrl crowdUrl = new CrowdUrl();
					crowdUrl.setCreateTime(ctStr);
					crowdUrl.setUrl(url);
					crowdUrl.setSts(CROWD_STS_A);
					crowdUrl.setSourceType(URL_SOURCE_TYPE_CUST);
					crowdUrl.setCrowdId(crowdId + "");
					
					crowdDao.addCrowdUrls(crowdUrl);
				}
			
			}
		}
		
		
		

		if (StringUtil.isNotBlank(searchKwSwitch)) {
			searchKwSwitch = KW_SWITCH_Y;

		} else {
			searchKwSwitch = KW_SWITCH_N;
		}
		
		
		if (StringUtil.isNotBlank(urlSwitch)) {
			urlSwitch = URL_SWITCH_Y;

		} else {
			urlSwitch = URL_SWITCH_N;
		}
		

		CrowdRule crowdRule = new CrowdRule();
		crowdRule.setCreateTime(ctStr);
		crowdRule.setCrowdId(crowdId+"");
		
		crowdRule.setKwNum(searchKwNum);
		crowdRule.setKwSwitch(searchKwSwitch);
		crowdRule.setKwTimeNum(searchKwTimeNum);
		crowdRule.setKwTimeType(searchKwTimeType);
		
		crowdRule.setRemark("");
		crowdRule.setSts(CROWD_STS_A);
		
		crowdRule.setUrlNum(urlNum);
		crowdRule.setUrlSwitch(urlSwitch);
		crowdRule.setUrlTimeNum(urlTimeNum);
		crowdRule.setUrlTimeType(urlTimeType);
		
		crowdDao.addCrowdRule(crowdRule);

		return crowdId;
	}



	@Override
	public List<Crowd> getCrowdList(Crowd crowdQry) {
		List<Crowd> crowdList = null;
		crowdList = crowdDao.getCrowdList(crowdQry);
		return crowdList;
	}



		@Override
	public Crowd getOneCrowd(Crowd crowdQry) {
		Crowd crowd = crowdDao.getOneCrowd(crowdQry);
		return crowd;
	}



	@Override
	public List<CrowdKeyword> getCrowdKeywords(CrowdKeyword ckQry) {
		List<CrowdKeyword> ckList = crowdDao.getCrowdKeywords(ckQry);
		return ckList;
	}



	@Override
	public List<CrowdUrl> getCrowdUrls(CrowdUrl cuQry) {
		List<CrowdUrl> cuList = crowdDao.getCrowdUrls(cuQry);
		return cuList;
	}



	@Override
	public CrowdRule getCrowRule(CrowdRule crQry) {
		CrowdRule cr = null;
		cr = crowdDao.getOneCrowdRule(crQry);
		return cr;
	}



	@Override
	public void editSaveCrowdConfig(String oldCrowdId, String crowdName,
			String crowdDesc, String searchKwFromOcean,
			String searchKwFormCust, String urlFromOcean, String urlFromCust,
			String searchKwNum, String searchKwTimeNum,
			String searchKwTimeType, String urlNum, String urlTimeType,
			String urlTimeNum, String searchKwSwitch, String urlSwitch) {
		
		
		String ctStr = DateUtil.getCurrentDateTimeStr();
		//User user = SessionContainer.getSession();

		//修改人群基本信息
		Crowd crowdEdit = new Crowd();
		crowdEdit.setCrowdId(oldCrowdId);
		crowdEdit.setCrowdName(crowdName);
		crowdEdit.setCrowdDesc(crowdDesc);
		crowdDao.updateCrowd(crowdEdit);
		
		
		//修改人群搜索关键词(1、删除旧的搜索关键词)
		CrowdKeyword ckDel = new CrowdKeyword();
		ckDel.setCrowdId(oldCrowdId);
		crowdDao.delCrowdKeywords(ckDel);
		
		//修改人群搜索关键词(2、把新的搜索关键词保存)
		if (StringUtil.isNotBlank(searchKwFromOcean)) {
			String[] searchKwFromOceans = searchKwFromOcean.split(",");

			for (int i = 0; i < searchKwFromOceans.length; i++) {
				
				if(StringUtil.isNotBlank(searchKwFromOceans[i])){
					CrowdKeyword crowdKeyword = new CrowdKeyword();
					crowdKeyword.setCreateTime(ctStr);
					crowdKeyword.setKeyword(searchKwFromOceans[i]);
					crowdKeyword.setSts(CROWD_STS_A);
					crowdKeyword.setSourceType(SEARCHKW_SOURCE_TYPE_OCEAN);
					crowdKeyword.setCrowdId(oldCrowdId + "");
					
					crowdDao.addCrowdKeywords(crowdKeyword);
				}
				
			}
		}
		

		if (StringUtil.isNotBlank(searchKwFormCust)) {
			String[] searchKwFormCusts = searchKwFormCust.split("\r\n");

			for (int i = 0; i < searchKwFormCusts.length; i++) {
				
				if(StringUtil.isNotBlank(searchKwFormCusts[i])){
					CrowdKeyword crowdKeyword = new CrowdKeyword();
					crowdKeyword.setCreateTime(ctStr);
					crowdKeyword.setKeyword(searchKwFormCusts[i]);
					crowdKeyword.setSts(CROWD_STS_A);
					crowdKeyword.setSourceType(SEARCHKW_SOURCE_TYPE_CUST);
					crowdKeyword.setCrowdId(oldCrowdId + "");
					
					crowdDao.addCrowdKeywords(crowdKeyword);
				}
			
			}
		}
		
		
		
		//修改人群url(1、删除旧的人群url)
		CrowdUrl cuDel = new CrowdUrl();
		cuDel.setCrowdId(oldCrowdId);
		crowdDao.delCrowdUrls(cuDel);
		
		
		
		//修改人群url(2、把新的人群url保存)
		if (StringUtil.isNotBlank(urlFromOcean)) {
			String[] urlFromOceans = urlFromOcean.split(",");

			for (int i = 0; i < urlFromOceans.length; i++) {
				
				if(StringUtil.isNotBlank(urlFromOceans[i])){
					CrowdUrl crowdUrl = new CrowdUrl();
					
					crowdUrl.setCreateTime(ctStr);
					crowdUrl.setUrl(urlFromOceans[i]);
					crowdUrl.setSts(CROWD_STS_A);
					crowdUrl.setSourceType(URL_SOURCE_TYPE_OCEAN);
					crowdUrl.setCrowdId(oldCrowdId + "");
					
					crowdDao.addCrowdUrls(crowdUrl);
				}
				
			}
		}

	
		if (StringUtil.isNotBlank(urlFromCust)) {
			String[] urlFromCusts = urlFromCust.split("\r\n");

			for (int i = 0; i < urlFromCusts.length; i++) {
				
				if(StringUtil.isNotBlank(urlFromCusts[i])){
					String url = urlFromCusts[i].trim();
					if(!url.contains("http://")){
						url = new StringBuffer("http://").append(url).toString();
					}
					CrowdUrl crowdUrl = new CrowdUrl();
					url = EncodUtil.EncodeUrl(url);
					crowdUrl.setCreateTime(ctStr);
					crowdUrl.setUrl(url);
					crowdUrl.setSts(CROWD_STS_A);
					crowdUrl.setSourceType(URL_SOURCE_TYPE_CUST);
					crowdUrl.setCrowdId(oldCrowdId + "");
					
					crowdDao.addCrowdUrls(crowdUrl);
				}
				
			}
		}
		
		
	
		if (StringUtil.isNotBlank(searchKwSwitch)) {
			searchKwSwitch = KW_SWITCH_Y;

		} else {
			searchKwSwitch = KW_SWITCH_N;
		}
		
		
		if (StringUtil.isNotBlank(urlSwitch)) {
			urlSwitch = URL_SWITCH_Y;

		} else {
			urlSwitch = URL_SWITCH_N;
		}
		
		//修改人群规则(1、删除就得规则)
		CrowdRule crDel = new CrowdRule();
		crDel.setCrowdId(oldCrowdId);
		crowdDao.delCrowdRule(crDel);
		
		
		//修改人群规则(2、把新的规则保存)
		CrowdRule crowdRule = new CrowdRule();
		crowdRule.setCreateTime(ctStr);
		crowdRule.setCrowdId(oldCrowdId+"");
		
		crowdRule.setKwNum(searchKwNum);
		crowdRule.setKwSwitch(searchKwSwitch);
		crowdRule.setKwTimeNum(searchKwTimeNum);
		crowdRule.setKwTimeType(searchKwTimeType);
		
		crowdRule.setRemark("");
		crowdRule.setSts(CROWD_STS_A);
		
		crowdRule.setUrlNum(urlNum);
		crowdRule.setUrlSwitch(urlSwitch);
		crowdRule.setUrlTimeNum(urlTimeNum);
		crowdRule.setUrlTimeType(urlTimeType);
		
		crowdDao.addCrowdRule(crowdRule);
		
		
		
	}


	
	/**
	 * 不是真的删除，只是把人群主表的状态改成不在用，
	 * 关键词表、url表、rule表不变动.
	 */
	@Override
	public void delCrowd(Crowd crowdDel) {
		crowdDel.setSts(CROWD_STS_D);
		crowdDao.delCrowd(crowdDel);
	}



	@Override
	public List<KeywordOcean> getKeywordOceanList(KeywordOcean ko) {
		
		List<KeywordOcean> kwoList = new ArrayList<KeywordOcean>();
		
		kwoList = crowdDao.getKeywordOceanList(ko);
		
		return kwoList;
	}



	@Override
	public List<UrlOcean> getUrlOceanList(UrlOcean uo) {
		List<UrlOcean> uoList = new ArrayList<UrlOcean>();
		
		uoList = crowdDao.getUrlOceanList(uo);
		
		return uoList;
	}

	
}
