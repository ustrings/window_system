package com.hidata.ad.web.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hidata.ad.web.dto.KeyWordCrowdDto;

/**
 * 人群关键词 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月13日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author sunly
 */
@Component
public interface KeywordCrowdDao {

	/**
	 * 查询 人群关键词
	 * @param keyWordCrowdDto
	 * @return
	 * @throws Exception
	 */
	public List<KeyWordCrowdDto> queryKeywordCrowdListByCondition(KeyWordCrowdDto keyWordCrowdDto) throws Exception;

}
