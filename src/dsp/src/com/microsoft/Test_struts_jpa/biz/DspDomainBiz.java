package com.microsoft.Test_struts_jpa.biz;

import java.util.List;

import com.microsoft.Test_struts_jpa.dao.DspAdDAO;
import com.microsoft.Test_struts_jpa.dao.DspDomainDAO;
import com.microsoft.Test_struts_jpa.entity.Dsp_Ad;
import com.microsoft.Test_struts_jpa.entity.Dsp_Domain;

public class DspDomainBiz {
	
	
	//查询
	private DspDomainDAO domainDao = new DspDomainDAO();
	public List<Dsp_Domain> getSelectDomain(){
		List<Dsp_Domain> list = domainDao.selectDomain();
		return list;
	}
	//增加投放次数
	public boolean getUdpateTimes(int DomainId){
		return domainDao.udpateDomainTimes(DomainId);
	}
	
	
}
