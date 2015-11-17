package com.hidata.ad.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hidata.ad.web.dto.AdInstanceDto;
import com.hidata.ad.web.dto.AdInstanceHostDto;
import com.hidata.ad.web.dto.AdStatisticsViewDto;
import com.hidata.ad.web.dto.AdUrlHostShowDto;
import com.hidata.ad.web.model.AdHost;
import com.hidata.ad.web.model.AdInstance;
import com.hidata.ad.web.model.AdMaterial;
import com.hidata.ad.web.model.AdStatInfo;
import com.hidata.ad.web.model.MaterialStatInfo;
import com.hidata.ad.web.service.AdMaterialService;
import com.hidata.ad.web.service.AdPlanManageService;
import com.hidata.ad.web.service.ReportService;
import com.hidata.ad.web.session.SessionContainer;
import com.hidata.ad.web.util.ViewExcel;
import com.hidata.framework.annotation.LoginRequired;
import com.hidata.framework.util.DateUtil;

@Controller
public class ReportController {
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private AdPlanManageService adPlanManageService;
	
	@Autowired
	private AdMaterialService adMaterialService;
	/**
	 * 广告物料报告
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * 修改：周晓明
	 */
	@RequestMapping(value = "/report/index")
	@LoginRequired
	public String index(HttpServletRequest request, HttpServletResponse response, Model model){
		return "/report/ad-report-index";
	}
	/**
	 * 广告物料报告
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * 修改：周晓明
	 */
	@RequestMapping(value = "/material/report", method = RequestMethod.GET)
	public String showMaterialReport(HttpServletRequest request, HttpServletResponse response, Model model){
		
		String time = DateUtil.format(DateUtil.addDays(DateUtil.getCurrentDate(), 0), DateUtil.C_DATE_PATTON_DEFAULT);
		String para = request.getParameter("id");
		Integer id = (para == null ? null : Integer.valueOf(para));
		List<MaterialStatInfo> list = reportService.calculateAdMaterialReportByTimeRange_new(time+" 00:00:00", time + " 23:59:59", SessionContainer.getSession().getUserId(), id);
		
		if (id != null && id > 0){
			AdMaterial adMaterial = adMaterialService.findAdMaterialById(SessionContainer.getSession().getUserId(), id);
			model.addAttribute("adMaterial", adMaterial);
		}
		
		model.addAttribute("reportList", list);
		return "/report/ad-material-report-content";
	}
	
	/**
	 * 广告物料报告
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/material/singleReport/{id}", method = RequestMethod.GET)
	public String showSingleMaterialReport(HttpServletRequest request, HttpServletResponse response, Model model,@PathVariable Integer id){
		
		String time = DateUtil.format(DateUtil.addDays(DateUtil.getCurrentDate(), 0), DateUtil.C_DATE_PATTON_DEFAULT);
		
		List<MaterialStatInfo> list = reportService.calculateAdMaterialReportByTimeRange_new(time+" 00:00:00", time + " 23:59:59", SessionContainer.getSession().getUserId(), id);
		
		if (id != null && id > 0){
			AdMaterial adMaterial = adMaterialService.findAdMaterialById(SessionContainer.getSession().getUserId(), id);
			model.addAttribute("adMaterial", adMaterial);
		}
		
		model.addAttribute("reportList", list);
		return "/report/single-ad-material-report";
	}
	/**
	 * 广告报告
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * 修改：周晓明
	 */
	@RequestMapping(value = "/ad/report", method = RequestMethod.GET)
	public String adReport(HttpServletRequest request, HttpServletResponse response, Model model){
		//默认为 今天
		String time = DateUtil.format(DateUtil.addDays(DateUtil.getCurrentDate(), 0), DateUtil.C_DATE_PATTON_DEFAULT);
		//int index = Integer.valueOf(request.getParameter("index"));
		String paraAd = request.getParameter("id");
		Integer adId = (paraAd == null ? null : Integer.valueOf(paraAd));
		List<AdStatInfo> list = reportService.calculateAdReportByTimeRange_new(time+" 00:00:00", time + " 23:59:59", SessionContainer.getSession().getUserId(), adId);
		
		//System.out.println(list.toString()  + list.size());
		if (adId != null && adId > 0){
			AdInstanceDto dto = adPlanManageService.getAdplanById(paraAd);
			model.addAttribute("adInstance", dto);
		}
		
		AdStatisticsViewDto viewDto = reportService.calculateAdStatistics(time+" 00:00:00", time + " 23:59:59", SessionContainer.getSession().getUserId(), paraAd);
		model.addAttribute("viewDto", viewDto);
		model.addAttribute("reportList", list);
		return "/report/ad-report";
	}
	
	@RequestMapping(value = "/material/reportResult", method = RequestMethod.GET)
	public String materialReport(HttpServletRequest request, HttpServletResponse response, Model model){
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		//int index = Integer.valueOf(request.getParameter("index"));
		String para = request.getParameter("id");
		Integer id = (para == null ? null : Integer.valueOf(para));
		List<MaterialStatInfo> list = reportService.calculateAdMaterialReportByTimeRange_new(start+" 00:00:00", end + " 23:59:59", SessionContainer.getSession().getUserId(), id);
		model.addAttribute("reportList", list);
		return "/report/ad-material-template";
	}
	
//	@RequestMapping(value = "/ad/report", method = RequestMethod.GET)
//	public String adReport(HttpServletRequest request, HttpServletResponse response, Model model){
//		String time = DateUtil.format(DateUtil.addDays(DateUtil.getCurrentDate(), -1), DateUtil.C_DATE_PATTON_DEFAULT);
//		//int index = Integer.valueOf(request.getParameter("index"));
//		String paraAd = request.getParameter("id");
//		Integer adId = (paraAd == null ? null : Integer.valueOf(paraAd));
//		List<AdReportViewDto> list = reportService.calculateAdReportByTimeRange(time+" 00:00:00", time + " 23:59:59", SessionContainer.getSession().getUserId(), adId);
//		
//		if (adId != null && adId > 0){
//			AdInstanceDto dto = adPlanManageService.getAdplanById(paraAd);
//			model.addAttribute("adInstance", dto);
//		}
//		
//		model.addAttribute("reportList", list);
//		return "/report/ad-report";
//	}
//	
	
	
//	@RequestMapping(value = "/ad/template", method = RequestMethod.GET)
//	public String adReportTemplate(HttpServletRequest request, HttpServletResponse response, Model model){
//		String start = request.getParameter("start");
//		String end = request.getParameter("end");
//		//int index = Integer.valueOf(request.getParameter("index"));
//		String paraAd = request.getParameter("id");
//		Integer adId = (paraAd == null ? null : Integer.valueOf(paraAd));
//		List<AdReportViewDto> list = reportService.calculateAdReportByTimeRange(start+" 00:00:00", end + " 23:59:59", SessionContainer.getSession().getUserId(), adId);
//		model.addAttribute("reportList", list);
//		return "/report/ad-template";
//	}
	
	@RequestMapping(value = "/ad/template", method = RequestMethod.GET)
	public String adReportTemplate(HttpServletRequest request, HttpServletResponse response, Model model){
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		//int index = Integer.valueOf(request.getParameter("index"));
		String paraAd = request.getParameter("id");
		Integer adId = (paraAd == null ? null : Integer.valueOf(paraAd));
		List<AdStatInfo> list = reportService.calculateAdReportByTimeRange_new(start+" 00:00:00", end + " 23:59:59", SessionContainer.getSession().getUserId(), adId);
		model.addAttribute("reportList", list);
		model.addAttribute("singleAdId",paraAd);
		return "/report/ad-template";
	}
	
	@RequestMapping(value="/ad/statistics", produces="application/json")
	@ResponseBody
	public Map<String, Object> adStatistics(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		String adId = request.getParameter("id");
		AdStatisticsViewDto viewDto = reportService.calculateAdStatistics(start+" 00:00:00", end + " 23:59:59", SessionContainer.getSession().getUserId(), adId);
		map.put("totalImpressNum", viewDto.getTotalImpressNum());
		map.put("totalClickNum", viewDto.getTotalClickNum());
		map.put("clickRate", viewDto.getClickRate());
		map.put("cpmPrice", viewDto.getCpm_price());
		map.put("totalAmount", viewDto.getTotalAmount());
		return map;
	}
	
	@RequestMapping(value="/material/chart", produces="application/json")
	@ResponseBody
	public Map<String, Object> chart(HttpServletRequest request, HttpServletResponse response){
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		int index = Integer.valueOf(request.getParameter("index"));
		int type = Integer.valueOf(request.getParameter("type"));
		int reportType = Integer.valueOf(request.getParameter("reportType"));
		String para = request.getParameter("id");
		Integer id = (para == null ? null : Integer.valueOf(para));
		String ids = request.getParameter("ids");
		Map<String,Object> map = null;
		if (id > 0) {
			map = reportService.adMaterialViewOrClickChartStatistics(start + " 00:00:00", end + " 23:59:59", SessionContainer.getSession().getUserId(), index, type, reportType, id);
		} else {
			map = reportService.adMaterialViewOrClickChartStatistics(start + " 00:00:00", end + " 23:59:59", SessionContainer.getSession().getUserId(), index, type, reportType, ids);
		}
		
		return map;
	}
	
	/**  
	* @Title: exportExcel  
	* @Description: 导出用户数据生成的excel文件 
	* @param  model 
	* @param  request 
	* @param  response 
	* @param  设定文件  
	* @return ModelAndView    返回类型  
	* @throws  
	* @author xiaoming
	*/  
	@RequestMapping(value="/export/excel",method=RequestMethod.POST)    
	   public ModelAndView exportExcel(ModelMap model, HttpServletRequest request, HttpServletResponse response) {   

		String start = request.getParameter("start");
		String end = request.getParameter("end");
		String paraAd = request.getParameter("id");
		Integer adId = (paraAd == null ? null : Integer.valueOf(paraAd));
		List<AdStatInfo> list = reportService.calculateAdReportByTimeRange_new(start+" 00:00:00", end + " 23:59:59", SessionContainer.getSession().getUserId(), adId);
		Map<String, Object> obj = null;  
		ViewExcel viewExcel = new ViewExcel();      
		 //Map<String, Object> condition = new HashMap<String, Object>();     
	      //获取数据库表生成的workbook  
	    
	    try {  
	    	HSSFWorkbook workbook = null;
	    	if (adId == null || adId == 0){
	    		workbook = reportService.getHWB(list);
	    	} else {
	    		workbook = reportService.getHWB2(list);
	    	}
	       viewExcel.buildExcelDocument(obj, workbook, request, response);  
	    } catch (Exception e) {  
	    	  e.printStackTrace();  
	    }  
	      return new ModelAndView(viewExcel, model);
	  } 
	
	/**
	 * 广告报告
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ad/singleAdReportIndex/{adId}", method = RequestMethod.GET)
	public String singleAdReportIndex(HttpServletRequest request, HttpServletResponse response, Model model,@PathVariable Integer adId){
		AdInstanceDto dto = adPlanManageService.getAdplanById(String.valueOf(adId));
		model.addAttribute("adInstance", dto);
		model.addAttribute("singleAdId",adId);
		return "/report/single-ad-report";
	}
	
	/**
	 * 广告报告--广告投放网站报告--首页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * 
	 */
	@RequestMapping(value = "/urlCount/report", method = RequestMethod.GET)
	public String showUrlReport(HttpServletRequest request, HttpServletResponse response, Model model){
		
		String time = DateUtil.format(DateUtil.addDays(DateUtil.getCurrentDate(), 0), DateUtil.C_DATE_PATTON_DEFAULT);
					
		List<AdInstance> adInstanceList = adPlanManageService.findAdInstanceListByUser(SessionContainer.getSession().getUserId());
		model.addAttribute("adInstanceList",adInstanceList);
		
		List<AdInstanceHostDto> list = reportService.calculateAdHostReportByTimeRange_new(time+" 00:00:00", time + " 23:59:59",String.valueOf(adInstanceList.get(0).getId()));
		if(list.size()>0&&list!=null){
			model.addAttribute("reportList", list);
		}
		return "/report/ad-host-report-content";
	}
	
	/**
	 * 广告报告--该广告下投放的网址的展现量--点击量--点击率
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/urlCount/reportResult", method = RequestMethod.GET)
	public String urlReport(HttpServletRequest request, HttpServletResponse response, Model model){
		String start = request.getParameter("start");
		String end = request.getParameter("end");		
		String adId = request.getParameter("adId");		
		List<AdInstanceHostDto> list = reportService.calculateAdHostReportByTimeRange_new(start+" 00:00:00", end + " 23:59:59", adId);
		//model.addAttribute("adName",list.get(0).getAdName());
		model.addAttribute("reportList", list);
		return "/report/ad-host-template";
	}
	
	/**
	 * 广告报告--单个网址的
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/urlCount/hostShow",method=RequestMethod.GET)
	public String hostShow(HttpServletRequest request, HttpServletResponse response, Model model){
		String urlHost = request.getParameter("urlHost");
		String adId = request.getParameter("adId");
		String time = DateUtil.format(DateUtil.addDays(DateUtil.getCurrentDate(), 0), DateUtil.C_DATE_PATTON_DEFAULT);
		List<AdUrlHostShowDto> hostShowList = reportService.findAdHostByUrlHost(time+" 00:00:00", time + " 23:59:59",urlHost,adId);
		//广告下的投放域名
		List<AdHost> urlHostList = reportService.findAdHostByAdId(adId);
		model.addAttribute("urlHostList",urlHostList); //该广告投放到的网址的所有域名
		model.addAttribute("hostShowList",hostShowList);  
		model.addAttribute("urlHost",urlHost);  //域名
		model.addAttribute("adId",adId);    //广告id
		return "/report/ad-host-report-content";
	}
	
	/**
	 * 广告报告-每一个网址-不同时间的信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/urlCount/hostShowResult",method=RequestMethod.GET)
	public String hostShowResult(HttpServletRequest request, HttpServletResponse response, Model model){
		String start = request.getParameter("start");
		String end = request.getParameter("end");		
		String adId = request.getParameter("adId");	
		String urlHost = request.getParameter("urlHost");
		List<AdUrlHostShowDto> hostShowList = reportService.findAdHostByUrlHost(start+" 00:00:00", end + " 23:59:59",urlHost,adId);
		model.addAttribute("hostShowList", hostShowList);
		return "/report/ad-host-show";
	}
	
	/**
	 * 广告计划---广告报告
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * 
	 */
	@RequestMapping(value = "/ad/single-ad-report/{adId}", method = RequestMethod.GET)
	public String singleAdReport(HttpServletRequest request, HttpServletResponse response, Model model,@PathVariable Integer adId){
		//默认为 今天
		String time = DateUtil.format(DateUtil.addDays(DateUtil.getCurrentDate(), 0), DateUtil.C_DATE_PATTON_DEFAULT);

		List<AdStatInfo> list = reportService.calculateAdReportByTimeRange_new(time+" 00:00:00", time + " 23:59:59", SessionContainer.getSession().getUserId(), adId);
				
		if (adId != null && adId > 0){
			AdInstanceDto dto = adPlanManageService.getAdplanById(String.valueOf(adId));
			model.addAttribute("adInstance", dto);
		}
		model.addAttribute("reportList", list);
		return "/report/single-ad-report-index";
	}
	
	/**
	 * 广告报告--网址报告首页
	 * @param request
	 * @param response
	 * @param model
	 * @param adId
	 * @return
	 */
	@RequestMapping(value="/ad/single-url-host/{adId}",method=RequestMethod.GET)
	public String singleUrlHost(HttpServletRequest request, HttpServletResponse response, Model model,@PathVariable Integer adId){
		String time = DateUtil.format(DateUtil.addDays(DateUtil.getCurrentDate(), 0), DateUtil.C_DATE_PATTON_DEFAULT);
		List<AdInstanceHostDto> list = reportService.calculateAdHostReportByTimeRange_new(time+" 00:00:00", time + " 23:59:59",String.valueOf(adId));
		if(list.size()>0&&list!=null){
			model.addAttribute("reportList", list);
		}
		model.addAttribute("showUrlAdId",String.valueOf(adId));
		return "/report/single-ad-host";
	}
	
	/**  广告报告--广告下的所有网址
	* @Title: exportExcel  
	* @Description: 导出用户数据生成的excel文件 
	* @param  model 
	* @param  request 
	* @param  response 
	* @param  设定文件  
	* @return ModelAndView    返回类型  
	* @throws  
	* @author ssq
	*/  
	@RequestMapping(value="/exporturl/excel",method=RequestMethod.POST)    
	public ModelAndView exportUrlExcel(ModelMap model, HttpServletRequest request, HttpServletResponse response) {   

		String adId = request.getParameter("adIdExport");
		String start = request.getParameter("startExport");
		String end = request.getParameter("endExport");
		String urlHost = request.getParameter("urlHost");
		String type = request.getParameter("exportType");
		
		Map<String, Object> obj = null;  
		ViewExcel viewExcel = new ViewExcel();
		
		if("1".equals(type)){
			List<AdUrlHostShowDto> hostlist = reportService.findAdHostByUrlHost(start+" 00:00:00", end + " 23:59:59",urlHost,adId);	    
		    try {  
		    	HSSFWorkbook workbook = reportService.getUrlHostBook(hostlist);  
		        viewExcel.buildExcelDocument(obj, workbook, request, response);  
		    } catch (Exception e) {  
		    	  e.printStackTrace();  
		    }  
		}else if("0".equals(type)){
			List<AdInstanceHostDto> urllist = reportService.calculateAdHostReportByTimeRange_new(start+" 00:00:00", end + " 23:59:59",adId);     
		    try {  
		    	HSSFWorkbook workbook = reportService.getUrlBook(urllist);  
		        viewExcel.buildExcelDocument(obj, workbook, request, response);  
		    } catch (Exception e) {  
		    	  e.printStackTrace();  
		    }  
		}	
	      return new ModelAndView(viewExcel, model);
	  }    	
}
