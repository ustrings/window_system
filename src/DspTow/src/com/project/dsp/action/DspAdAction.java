package com.project.dsp.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.project.dsp.entity.Dsp_Ad;
import com.project.dsp.entity.Dsp_Domain;
import com.project.dsp.service.DspAdService;
import com.project.dsp.utils.StrUtil;

@Controller
@RequestMapping(value = "/dspAdAction")
public class DspAdAction {
	
	@Resource(name="dspusersService")
	private DspAdService dspAdService;
	
	//查询需求
	@RequestMapping(value="/doSelectAd")
	@ResponseBody
    public List<Dsp_Ad> doSelectAd(Dsp_Ad dsp_Ad){
    		return dspAdService.queryDspAdList(dsp_Ad);
    }
    
    @RequestMapping(value="/toAdpage")
    public String doSelectAdList(HttpServletRequest request, 
			HttpServletResponse response,Dsp_Ad dsp_Ad){
    	response.setCharacterEncoding("UTF-8");	
    	List<Dsp_Ad> adlist = dspAdService.queryDspAdList(null);
    	if(adlist != null && adlist.size() >=0){
    		request.setAttribute("adlist", adlist);
    		return "/Admin/query";
    	}else{
    		return "0";
    	}
    }
    //跳转到需求新增页面
    @RequestMapping(value="toAdAddPage")
    public String toAdAddPage(HttpServletRequest request,String id){
    	if(id!=null&&id!=""){
    		List<Dsp_Ad> adlist = dspAdService.queryOneDspAdList(Integer.valueOf(id));
    		
    		List<Dsp_Domain> domainList = dspAdService.queryOneDspDomainList(Integer.valueOf(id));
    		
    		if(adlist != null){
    			adlist.get(0).setTime(adlist.get(0).getBegin_time()+" - "+adlist.get(0).getEnd_time());
    			adlist.get(0).setDomain(domainList.get(0).getDomain());
    			request.setAttribute("adlist", adlist);
    		}
    	}
    	return "/Admin/queryadd";
    	
    }
    //跳转到需求统计页面
    @RequestMapping(value="toAdSelectPage")
    public String toAdSelectPage(){
    	return "/Admin/querystat";
    }
	//需求统计查询
	@RequestMapping(value="/doSelectCountAd")
	@ResponseBody
	public List<Dsp_Ad> doSelectCountAd(Dsp_Ad dsp_Ad){
		return dspAdService.queryDspAdList(dsp_Ad);
	}
	
	
	//需求推送状态修改
	@RequestMapping(value="/doUpdateAd")
	public String doUpdateAd(HttpServletRequest request, 
			HttpServletResponse response){
		String id = StrUtil.convertUTF82(request.getParameter("id"));
	    String push_status = StrUtil.convertUTF82(request.getParameter("push_status"));
		Map<String, Object> adMap = new HashMap<String, Object>();
		adMap.put("id", id);
		adMap.put("push_status", push_status);

		int b = dspAdService.updateDspAdStatus(adMap);
		
		if(b > 0){
	    	request.setAttribute("adlist", queryAdList());
	    	return "/Admin/query";
		}
		return "0";
	}
	
	//需求详情
	@RequestMapping(value="/doDetailAd")
	public String doDetailAd(HttpServletRequest request, 
			HttpServletResponse response){
		String id = StrUtil.convertUTF82(request.getParameter("id"));
		List<Dsp_Ad> adDetaillist = dspAdService.queryOneDspAdList(Integer.valueOf(id));
		
		List<Dsp_Domain> domainList = dspAdService.queryOneDspDomainList(Integer.valueOf(id));
		
		if(adDetaillist != null){
			request.setAttribute("domain", domainList.get(0).getDomain());
			request.setAttribute("adDetaillist", adDetaillist);
			return "/Admin/detail";
		}else{
			return "0";
		}
	}
	
	//删除
	@RequestMapping(value="/doDeleteAd")
	public String doDeleteAd(HttpServletRequest request, 
			HttpServletResponse response,String id){
	
		//关联表，先删除外键，在删主键
		int domainCount = dspAdService.deleteDspDomain(Integer.valueOf(id));
		if(domainCount >= 0){
			int adCount = dspAdService.deleteDspAd(Integer.valueOf(id)); 
			if(adCount > 0){
		    	request.setAttribute("adlist", queryAdList());
		    	return "/Admin/query";
			}
		}
		
		return "0";
	}
	@RequestMapping(value="/queryAdPrio")
	@ResponseBody
	public List<Dsp_Ad> queryAdPrio(){
		List<Dsp_Ad> adList=new ArrayList<Dsp_Ad>();
		for (int i = 0; i < 10; i++) {
			Dsp_Ad ad=new Dsp_Ad();
			
			if(dspAdService.queryOneDspAdListPrio(i).size()<1){
				ad.setPrio(i);
				adList.add(ad);
			}
		}
		return adList;
	}
	//添加
	@RequestMapping(value="/doInsterAd")
	public String doInsterAd(HttpServletRequest request,Dsp_Ad ad){
		 String str= dspAdService.insertORUpdate(ad);
		 request.setAttribute("adlist", queryAdList());
		 return str;
	}
	
	//添加域名信息
	public boolean insertDomain(int ad_id,String domain){
		
		Dsp_Domain domains = new Dsp_Domain();
		domains.setAd_id(ad_id);
		int domainCount = dspAdService.insertDspDomain(domains);
		
		
		if(domainCount>0){
			return true;
		}
		return false;
	}
	public List<Dsp_Ad> queryAdList(){
		return dspAdService.queryDspAdList(null);
	}
	
	//把时间转换为时间戳
	public static Date getStringToDate(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		try{
			date = sdf.parse(time);
			
		}catch(Exception e) {
			// TODO Auto-generated catch block　　
			e.printStackTrace();
		}
		return date;
	}
	
	
	
}
