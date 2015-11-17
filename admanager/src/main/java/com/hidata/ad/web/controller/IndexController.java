package com.hidata.ad.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hidata.ad.web.dto.AdInstanceDto;
import com.hidata.ad.web.dto.AdStatisticsViewDto;
import com.hidata.ad.web.model.AdMaterial;
import com.hidata.ad.web.model.AdStatInfo;
import com.hidata.ad.web.model.MaterialStatInfo;
import com.hidata.ad.web.model.UserAccount;
import com.hidata.ad.web.service.AdMaterialService;
import com.hidata.ad.web.service.AdPlanManageService;
import com.hidata.ad.web.service.ReportService;
import com.hidata.ad.web.service.UserAccountService;
import com.hidata.ad.web.session.SessionContainer;
import com.hidata.ad.web.util.ViewExcel;
import com.hidata.framework.util.DateUtil;

@Controller
@RequestMapping("/index/*")
public class IndexController {
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private AdPlanManageService adPlanManageService;
	
	@Autowired
	private AdMaterialService adMaterialService;
	
	@Autowired
	private UserAccountService userAccountService;


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
		return "/indexReport/ad-material-report";
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
	
		String paraAd = request.getParameter("id");
		Integer adId = (paraAd == null ? null : Integer.valueOf(paraAd));
		List<AdStatInfo> list = reportService.calculateIndexAdReportByTimeRange_new(time+" 00:00:00", time + " 23:59:59", SessionContainer.getSession().getUserId(), adId);
		
		//System.out.println(list.toString()  + list.size());
		if (adId != null && adId > 0){
			AdInstanceDto dto = adPlanManageService.getAdplanById(paraAd);
			model.addAttribute("adInstance", dto);
		}
		
		UserAccount userAcct = userAccountService.getUserAccountByUserid(SessionContainer.getSession().getUserId());
		
		AdStatisticsViewDto viewDto = reportService.calculateCurrentAdStatistics(time+" 00:00:00", time + " 23:59:59", SessionContainer.getSession().getUserId());
		
		model.addAttribute("userAcct", userAcct);
		model.addAttribute("viewDto", viewDto);
		model.addAttribute("reportList", list);
		return "/indexReport/ad-report";
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
		return "/indexReport/ad-material-template";
	}
	
	@RequestMapping(value = "/ad/template", method = RequestMethod.GET)
	public String adReportTemplate(HttpServletRequest request, HttpServletResponse response, Model model){
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		//int index = Integer.valueOf(request.getParameter("index"));
		String paraAd = request.getParameter("id");
		Integer adId = (paraAd == null ? null : Integer.valueOf(paraAd));
		List<AdStatInfo> list = reportService.calculateAdReportByTimeRange_new(start+" 00:00:00", end + " 23:59:59", SessionContainer.getSession().getUserId(), adId);
		model.addAttribute("reportList", list);
		return "/indexReport/ad-template";
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
		
		Map<String,Object> map = reportService.indexMaterialViewOrClickChartStatistics(start + " 00:00:00", end + " 23:59:59", SessionContainer.getSession().getUserId(), index, type, reportType, ids);
		
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
		List<AdStatInfo> list = reportService.calculateIndexAdReportByTimeRange_new(start+" 00:00:00", end + " 23:59:59", SessionContainer.getSession().getUserId(), adId);
		Map<String, Object> obj = null;  
		ViewExcel viewExcel = new ViewExcel();      
		 //Map<String, Object> condition = new HashMap<String, Object>();     
	      //获取数据库表生成的workbook  
	    
	    try {  
	    	HSSFWorkbook workbook = reportService.getHWB(list);  
	       viewExcel.buildExcelDocument(obj, workbook, request, response);  
	    } catch (Exception e) {  
	    	  e.printStackTrace();  
	    }  
	      return new ModelAndView(viewExcel, model);
	  }    
}
