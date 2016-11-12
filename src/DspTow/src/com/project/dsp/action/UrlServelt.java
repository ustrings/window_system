package com.project.dsp.action;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.project.dsp.entity.Dsp_Ad;
import com.project.dsp.entity.Dsp_Domain;
import com.project.dsp.service.DspAdService;

@Controller
@RequestMapping(value = "/urlServelt")
public class UrlServelt{
	private static int loop_Max = 10;
	private static int pushStatus_ON = 1; //推送开关,1:推送,0:关闭
	private static int pushStatus_OFF = 0; 
	private static int pushMethod_DX = 1;//推送方式,1:定向,0:普推
	private static int pushMethod_PT = 0;
	
	@Resource
	private DspAdService dspAdService;
	
	@RequestMapping(value="/doUrl")
	
	//@RequestMapping(value="/doUrl", method = {RequestMethod.GET})
	public String doUrl(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/html; charset=UTF-8");	
		String url = null;
		//获取浏览器中地址栏referer
		String refer = req.getHeader("referer");
		if(refer != null){
			for(int i =0;i<loop_Max;i++){
				//查询优先级(0-9)
		
				List<Dsp_Ad> dspAd = dspAdService.queryOneDspAdListPrio(i);
				if(dspAd == null || dspAd.size() <= 0){
					//如果没有该优先级信息，则退出本次循环
					continue;
				}
				//判断推送是否打开
				if(dspAd.get(0).getPush_status() == pushStatus_OFF){
				//如果推送转态为关闭，则退出本次循环
				continue;
				}
			//判断当前时间是否为推送时间范围
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式 
			Date newtime = new Date(); 
			
			try {
				if(newtime.getTime() < dateFormat.parse(dspAd.get(0).getBegin_time()).getTime() || newtime.getTime() < dateFormat.parse(dspAd.get(0).getEnd_time()).getTime()){
					//如果时间小于开始时间 或者 大于结束时间 ，则退出本次循环
					continue;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//若在投送时间范围内，则判断是否到量
			if(dspAd.get(0).getPush_num() >= dspAd.get(0).getSet_num()){
				//如果投放数量大于等于投放总量 ，则退出本次循环
				continue;
			}
		
			int domainId = 0;
			int show = 0;
			//定向推送
			if(dspAd.get(0).getPush_method() == pushMethod_DX){
				//查询Domain表中的域名
				List<Dsp_Domain> domain = dspAdService.queryOneDspDomainList(dspAd.get(0).getId());
				String [] domainstr=domain.get(0).getDomain().split(",");
				if(domain == null || domain.size() <= 0){
					//domain未null 则没有域名，则退出本次循环
					continue;
				}else{
					for(int k = 0;k<domainstr.length;k++){
						//模糊匹配				
						if(refer.indexOf(domainstr[0]) != -1){
							domainId = domain.get(k).getId(); //获取domain表中id
							show = 1;
							break;
						}
					}
				}
				//根据show值判断该url是否包含有域名
				if(show == 1){
					//选中
					url=dspAdService.queryAdbyDomaid(domainId);
					int adCount = dspAdService.updateDspAdNum(dspAd.get(0).getId());
					int domainCount = dspAdService.updateDspDomainNum(domainId);
					return "redirect:"+url;
 				}else{
					//未选中
					continue;
				}
			
			}
			//以上条件都未达到则，全名匹配
			url = dspAd.get(0).getUrl();
			//增加表中投放数量
			int adCount = dspAdService.updateDspAdNum(dspAd.get(0).getId());
		
			//boolean adBoolean = adBiz.getUdpateAdTimes(dspAd.get(0).getId());
			if(dspAd.get(0).getPush_method() == pushMethod_DX && show == 1){
				int domainCount = dspAdService.updateDspDomainNum(domainId);
				//boolean domainBoolean = domainBiz.getUdpateTimes(domainId);
			}
		
			break;
		}
		}else{
			//refer 为空,作为调试使用
			url = "http://www.dbcpm.com/sc/girl.html";
		}

		//需求没有命中,设置默认投放链接
		if (url == null)
		{
			url = "http://www.dbcpm.com/sc/girl.html";
		}
		return "redirect:"+url;
	}
	

}
