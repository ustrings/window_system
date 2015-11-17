package com.hidata.ad.web.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hidata.ad.web.dto.GisCrowdDto;
import com.hidata.ad.web.dto.UrlCrowdDto;
import com.hidata.ad.web.model.IntrestLabelCrowd;

/**
 * 人群关键词 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月13日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author sunly
 */
@Component
public interface IntrestLabelCrowdDao {

	/**
	 * 查询 兴趣标签 crowdlist
	 * @param IntrestLabelCrowd
	 * @return
	 * @throws Exception
	 */
	public List<IntrestLabelCrowd> queryIntrestLabelCrowdListByCondition(IntrestLabelCrowd intrestLabelCrowd) throws Exception;

}
