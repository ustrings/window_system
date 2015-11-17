package com.hidata.ad.web.dao;

import com.hidata.ad.web.dto.AdCrowdFindInfo;
import com.hidata.ad.web.dto.GisCrowdDto;
import com.hidata.ad.web.dto.IntegerModelCrowdDto;
import com.hidata.ad.web.dto.KeyWordCrowdDto;
import com.hidata.ad.web.dto.UrlCrowdDto;
import com.hidata.ad.web.model.IntrestLabelCrowd;

/**
 * 人群新增修改删除操作接口
 * @author ZTD
 *
 */
public interface AdCrowdFindInfoADUDao {
	public int addAdCrowFindInfo(AdCrowdFindInfo info);
	public int addKeyWordCrowd(KeyWordCrowdDto dto);
	public int addUrlCrowd(UrlCrowdDto dto);
	public int addGisCrowd(GisCrowdDto dto);
	public int addIntegerModelCrowdDto(IntegerModelCrowdDto dto);
	public int editAdCrowFindInfo(AdCrowdFindInfo info);
	/**
	 * 删除keyword_crowd
	 * @param crowdId
	 * @return
	 */
	public int delKeyWordCrowdByCrowdId(String crowdId);
	/**
	 * 删除url_crowd
	 * @param crowdId
	 * @return
	 */
	public int delUrlCrowdByCrowdId(String crowdId);
	/**
	 * 删除gis_crowd
	 * @param crowdId
	 * @return
	 */
	int delGisCrowd(String crowdId);
	int delIntegerModelCrowdDto(String crowdId);
	int addIntrestCrowdInfo(IntrestLabelCrowd intrestDto);
	int delIntrestCrowdInfoByCrowdId(String crowdId);
}
