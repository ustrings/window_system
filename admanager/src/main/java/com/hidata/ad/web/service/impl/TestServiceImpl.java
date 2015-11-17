package com.hidata.ad.web.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.ad.web.dao.TestDao;
import com.hidata.ad.web.service.TestService;

@Component
public class TestServiceImpl implements TestService {

	@Autowired
	private TestDao dao;
	
	public List<Map<String, Object>> queryKeyword() {
		return dao.queryKeyword();
	}
}
