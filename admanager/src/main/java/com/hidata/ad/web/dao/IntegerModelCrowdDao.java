package com.hidata.ad.web.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hidata.ad.web.dto.IntegerModelCrowdDto;

/**
 * 人群关键词 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月13日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author sunly
 */
@Component
public interface IntegerModelCrowdDao {

	/**
	 * 查询 智能模型人群
	 * @param IntegerModelCrowdDto
	 * @return
	 * @throws Exception
	 */
	public List<IntegerModelCrowdDto> queryIntegerModelCrowdDtoListByCondition(IntegerModelCrowdDto integerModelCrowdDto) throws Exception;

}
