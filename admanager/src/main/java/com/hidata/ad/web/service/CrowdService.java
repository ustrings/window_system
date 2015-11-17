package com.hidata.ad.web.service;

import java.util.ArrayList;
import java.util.List;

import com.hidata.ad.web.model.Crowd;
import com.hidata.ad.web.model.CrowdKeyword;
import com.hidata.ad.web.model.CrowdRule;
import com.hidata.ad.web.model.CrowdUrl;
import com.hidata.ad.web.model.KeywordOcean;
import com.hidata.ad.web.model.UrlOcean;

public interface CrowdService {

	// 是否启用url匹配开关
	public static final String URL_SWITCH_Y = "Y";
	public static final String URL_SWITCH_N = "N";

	// 是否启用关键词匹配开关
	public static final String KW_SWITCH_Y = "Y";
	public static final String KW_SWITCH_N = "N";

	// 人群状态
	public static final String CROWD_STS_A = "A";
	public static final String CROWD_STS_D = "D";

	// 搜索关键词来源
	public static final String SEARCHKW_SOURCE_TYPE_OCEAN = "O";
	public static final String SEARCHKW_SOURCE_TYPE_CUST = "C";

	// url来源
	public static final String URL_SOURCE_TYPE_OCEAN = "O";
	public static final String URL_SOURCE_TYPE_CUST = "C";

	public int addCrowdConfig(String crowdName, String crowdDesc,
			String searchKwFromOcean, String searchKwFormCust,
			String urlFromOcean, String urlFromCust, String searchKwNum,
			String searchKwTimeNum, String searchKwTimeType, String urlNum,
			String urlTimeType, String urlTimeNum, String searchKwSwitch,
			String urlSwitch);

	public List<Crowd> getCrowdList(Crowd crowdQry);

	public Crowd getOneCrowd(Crowd crowdQry);

	public List<CrowdKeyword> getCrowdKeywords(CrowdKeyword ckQry);

	public List<CrowdUrl> getCrowdUrls(CrowdUrl cuQry);

	public CrowdRule getCrowRule(CrowdRule crQry);

	public void editSaveCrowdConfig(String oldCrowdId, String crowdName,
			String crowdDesc, String searchKwFromOcean,
			String searchKwFormCust, String urlFromOcean, String urlFromCust,
			String searchKwNum, String searchKwTimeNum,
			String searchKwTimeType, String urlNum, String urlTimeType,
			String urlTimeNum, String searchKwSwitch, String urlSwitch);

	public void delCrowd(Crowd crowdDel);

	public List<KeywordOcean> getKeywordOceanList(KeywordOcean ko);

	public List<UrlOcean> getUrlOceanList(UrlOcean uo);

}
