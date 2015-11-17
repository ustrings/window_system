package com.hidata.ad.web.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hidata.ad.web.model.Crowd;
import com.hidata.ad.web.model.CrowdKeyword;
import com.hidata.ad.web.model.CrowdRule;
import com.hidata.ad.web.model.CrowdUrl;
import com.hidata.ad.web.model.KeywordOcean;
import com.hidata.ad.web.model.UrlOcean;


@Component
public interface CrowdDao {
	
	
	
	/**
	 * 添加人群
	 * @param crowd
	 */
	public int addCrowd(Crowd crowd);
	
	/**
	 * 修改人群
	 * @param crowd
	 * @return
	 */
	public int updateCrowd(Crowd crowd);
	
	
	/**
	 * 删除人群
	 * @param crowd
	 * @return
	 */
	public int delCrowd(Crowd crowd);
	
	
	/**
	 * 查询单个人群
	 * @param crowd
	 * @return
	 */
	public Crowd getOneCrowd(Crowd crowd);
	
	/**
	 * 查询满足条件的人群
	 * @param crowd
	 * @return
	 */
	public List<Crowd> getCrowdList(Crowd crowd);
	
	
	/**
	 * 修改人群状态
	 * @param sts
	 * @return
	 */
	public int updateCrowdSts(Crowd crowd);

	
	/**
	 * 添加人群搜索关键词
	 * @param ck
	 * @return
	 */
	public int addCrowdKeywords(CrowdKeyword ck);
	
	/**
	 * 删除人群搜索关键词
	 * @param ck
	 * @return
	 */
	public int delCrowdKeywords(CrowdKeyword ck);
	
	/**
	 * 查询人群搜索关键词
	 * @param ck
	 * @return
	 */
	public List<CrowdKeyword> getCrowdKeywords(CrowdKeyword ck);
	
	
	/**
	 * 添加人群URL
	 * @param cu
	 * @return
	 */
	public int addCrowdUrls(CrowdUrl cu);
	
	
	/**
	 * 删除人群URL
	 * @param cu
	 * @return
	 */
	public int delCrowdUrls(CrowdUrl cu);
	
	/**
	 * 查询人群url
	 * @param cu
	 * @return
	 */
	public List<CrowdUrl> getCrowdUrls(CrowdUrl cu);
	
	
	/**
	 * 添加人群定向规则
	 * @param cr
	 * @return
	 */
	public int addCrowdRule(CrowdRule cr);
	
	/**
	 * 删除人群定向规则
	 * @param cr
	 * @return
	 */
	public int delCrowdRule(CrowdRule cr);
	
	
	/**
	 * 得到一个人群的规则
	 * @param cr
	 * @return
	 */
	public CrowdRule getOneCrowdRule(CrowdRule cr);
	
	/**
	 * 查询关键词海洋
	 * @return
	 */
	public List<KeywordOcean> getKeywordOceanList(KeywordOcean ko);
	
	
	/**
	 * 查询url海洋
	 * @return
	 */
	public List<UrlOcean> getUrlOceanList(UrlOcean uo);
	
}
