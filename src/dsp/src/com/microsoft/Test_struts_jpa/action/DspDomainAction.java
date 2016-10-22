package com.microsoft.Test_struts_jpa.action;

import java.util.List;

import com.microsoft.Test_struts_jpa.biz.DspDomainBiz;
import com.microsoft.Test_struts_jpa.entity.Dsp_Domain;

public class DspDomainAction  extends BaseAction{
	private DspDomainBiz domainBiz = new DspDomainBiz();
	
	//首页查询
	public String doSelectDomain(){
		List<Dsp_Domain> domainlist = domainBiz.getSelectDomain();
		if(domainlist != null && domainlist.size() >=1){
			this.getSession().put("domainlist", domainlist);
			return "success";
		}else{
			return "error";
		}
	}
	
}
