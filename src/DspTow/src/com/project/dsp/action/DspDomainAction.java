package com.project.dsp.action;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.dsp.entity.Dsp_Domain;
import com.project.dsp.service.DspDomainService;

@Controller
@RequestMapping(value = "/dspDomainAction")
public class DspDomainAction {
	
	@Resource
	private DspDomainService dspDomainService;
	
	@RequestMapping(value="/toDomainPage")
	public String toDomainPage(){
		
		return "/Admin/domainstat";
	}
	@RequestMapping(value="/doSelectDomain")
	@ResponseBody
    public List<Dsp_Domain> doSelectDomain(Dsp_Domain dsp_Domain){
    	
		return dspDomainService.queryDspDomainList(dsp_Domain);
    }
	
//	public void selectDomain(HttpServletRequest request, 
//			HttpServletResponse response){
//		response.setCharacterEncoding("UTF-8");	
//		List<Dsp_Domain> domainlist = dspDomainService.queryDspDomainList();
//	
//	}
//	ScheduledExecutorService scheduledService = Executors.newScheduledThreadPool(1);  
//	//每隔5秒执行一次任务  
//	scheduledService.scheduleAtFixedRate(selectDomain, 0, 5, TimeUnit.SECONDS); 
	
	
	
//	TimerTask task = new TimerTask() {
//
//	      @Override
//	      public void run() {
//	    	  List<Dsp_Domain> domainlist = dspDomainService.queryDspDomainList();
// System.out.println("Hello !!!");
//
//	      }
//
//	    };
//	
//	    Timer timer = new Timer();
//
//	    long delay = 0;
//
//	    long intevalPeriod = 1 * 1000;
//
//	    // schedules the task to be run in an interval
//
//	    timer.scheduleAtFixedRate(task, delay,intevalPeriod);
//	
//	
//	Runnable runnable = new Runnable() {
//		public void run() {
//			List<Dsp_Domain> domainlist = dspDomainService.queryDspDomainList();
//		}
//	};
//	
//	Thread thread = new Thread(runnable);
//	Thread.State;
	
}


