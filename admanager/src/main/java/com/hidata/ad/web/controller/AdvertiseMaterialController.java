package com.hidata.ad.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hidata.ad.web.dto.AdInstanceDto;
import com.hidata.ad.web.dto.AdMaterialDto;
import com.hidata.ad.web.dto.DatePressDto;
import com.hidata.ad.web.model.AdInstance;
import com.hidata.ad.web.model.AdMaterial;
import com.hidata.ad.web.model.MapKv;
import com.hidata.ad.web.service.AdImpressLogService;
import com.hidata.ad.web.service.AdMaterialService;
import com.hidata.ad.web.service.AdPlanManageService;
import com.hidata.ad.web.service.MapKvService;
import com.hidata.ad.web.session.SessionContainer;
import com.hidata.ad.web.util.DateUtils;
import com.hidata.framework.annotation.LoginRequired;
import com.hidata.framework.util.DateUtil;

@Controller
public class AdvertiseMaterialController {

    @Autowired
    private AdMaterialService adMaterialService;

    @Autowired
    private MapKvService mapKvService;
    
    @Autowired
	private AdPlanManageService adPlanManageService;
    
	@Autowired
	private AdImpressLogService adImpressLogService;
    
	private static Logger logger = LoggerFactory
			.getLogger(AdImpressLogController.class);

    @RequestMapping(value = "/material/add", method = RequestMethod.GET)
    @LoginRequired
    public String showAdMaterial(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        // 初始化图片尺寸下拉框
        String curType = "material_size";

        List<MapKv> meteriSizeList = mapKvService.findMapKvByType(curType);

        model.addAttribute("jsession", request.getSession().getId());

        model.addAttribute("meterSizeList", meteriSizeList);

        return "/advertisement/material-add";
    }

    @RequestMapping(value = "/material/add", method = RequestMethod.POST)
    @LoginRequired
    public String add(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(value = "adMaterialDto")
            AdMaterialDto dto) {
        AdMaterial material = new AdMaterial();
        int mType = dto.getmType();
        material.setMType(mType);
        material.setMaterialName(dto.getMaterialName());
        material.setCoverFlag(dto.getCoverFlag());
        material.setUsefulType(dto.getUsefulType());
        if (mType == 1 || mType == 2) {
            material.setLinkUrl(dto.getLinkUrl());
            material.setMaterialDesc(dto.getMaterialDesc());
            material.setMaterialSize(dto.getMaterialSize());
            material.setTargetUrl(dto.getTargetUrl());
            material.setMaterialType(dto.getMaterialType());
            // 图片尺寸 值
            material.setMaterialValue(dto.getMaterialValue());
            
        } else {
//           material.setMaterialType(0);
//           material.setRichText(dto.getRichText());
        	 material.setMaterialType(0);
        	 material.setRichText(dto.getRichText());
        	 material.setMaterialSize(dto.getMaterialSize());
        	 material.setMaterialValue(dto.getMaterialValue());
        }

        Integer thirdMonitor = dto.getThirdMonitor();
        if (thirdMonitor != null) {//第三方空控件
            material.setThirdMonitor(thirdMonitor);
            material.setMonitorLink(dto.getMonitorLink());
        }
        material.setUserid(SessionContainer.getSession().getUserId());

        if (dto.getId() == 0) {
            material.setCreateTime(DateUtil.formatToDate(
                    DateUtil.format(new Date(), DateUtil.C_TIME_PATTON_DEFAULT),
                    DateUtil.C_TIME_PATTON_DEFAULT));
            adMaterialService.addMaterial(material);
        } else {
            material.setId(dto.getId());
            adMaterialService.editAdMaterial(material);
        }
        return "redirect:/material/list";
    }
    
    @RequestMapping(value = "/material/pvlist", method = RequestMethod.GET)
    @LoginRequired
	public String pvlist(HttpServletRequest request, HttpServletResponse response,Model model) {
		long start = System.currentTimeMillis();
		List<AdInstance> adList = adPlanManageService.findAdInstanceListByUser(SessionContainer.getSession().getUserId());
		long end = System.currentTimeMillis();
		logger.info("querytime："+(end-start));
		
		model.addAttribute("adList", adList);
		return "/advertisement/pv-list";
    }
    
    @RequestMapping(value = "/material/pvcompare/{adId}", method = RequestMethod.GET)
    @LoginRequired
    public String pvcompare(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String adId, Model model) {
    	// 根据 adId 获取 adInstance
    	
    	AdInstanceDto adInstanceDto = adPlanManageService.getAdplanById(adId);
    	model.addAttribute("adInstanceDto", adInstanceDto);
    	// 开始时间
    	String startTime = adInstanceDto.getStartTime();
    	// 结束时间
    	String endTime = adInstanceDto.getEndTime();
    	String dateFormat = "yyyy-MM-dd";
    	// 获取日期之间的天数
    	long dateNum = DateUtils.getDateNum(startTime, endTime, dateFormat);
    	List<String>	dataArr = DateUtils.getDateArr(startTime, endTime, dateFormat);
    	List<DatePressDto> datePressList = adImpressLogService.getDatePressList(adId);
    	datePressList = assembleDatePress(dataArr, datePressList);
    	// pv 总数
    	long total  = 0;
    	if (!org.apache.commons.lang.StringUtils.isEmpty(adInstanceDto.getTotal())) {
    		total = Long.valueOf(adInstanceDto.getTotal());
    	}
    	// 平均每天的曝光数
    	long avg_exposure = 0l;
    	if (dateNum > 0 ) {
    		avg_exposure = total/dateNum;
    	} else {
    		avg_exposure = total;
    	}
    	// 获取实际曝光数
    	long total_exposure = adImpressLogService.getTotalExposureByAdId(adId);
    	// 剩余曝光数
    	long remain_exposure = total - total_exposure;
    	model.addAttribute("total", total);
    	model.addAttribute("total_exposure", total_exposure);
    	model.addAttribute("remain_exposure", remain_exposure);
    	model.addAttribute("avg_exposure", avg_exposure);
    	model.addAttribute("dataArr", dataArr);
    	model.addAttribute("datePressList", datePressList);
    	
    	return "/advertisement/pv-list-content";
    }
    
    // 组装查询结果
    public static List<DatePressDto> assembleDatePress(List<String> dateArr, List<DatePressDto> tar) {
    	List<DatePressDto> result = new ArrayList<DatePressDto>(dateArr.size());
    	for (String date : dateArr) {
    		DatePressDto dpd = new DatePressDto();
    		dpd.setDate(date);
    		for (DatePressDto dto : tar) {
    			if (!org.apache.commons.lang.StringUtils.isEmpty(dto.getDate())) {
    				if(dto.getDate().equals(date)) {
        				dpd.setPress(dto.getPress());
        			}
    			}
    		}
    		result.add(dpd);
    	}
    	return result;
    }

    @RequestMapping(value = "/material/list", method = RequestMethod.GET)
    @LoginRequired
    public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<AdMaterial> materialList = adMaterialService
                .findAdMaterialListByUserid(SessionContainer.getSession().getUserId());
        model.addAttribute("materialList", materialList);
        return "/advertisement/material-list";
    }

    @RequestMapping(value = "/material/edit/{id}", method = RequestMethod.GET)
    @LoginRequired
    public String edit(HttpServletRequest request, HttpServletResponse response, Model model,
            @PathVariable
            int id) {
        int userid = SessionContainer.getSession().getUserId();
        AdMaterial material = adMaterialService.findAdMaterialById(userid, id);
        material.setRichText(material.getRichText() == null ? "" : material.getRichText().replace(
                "\"", "\'"));
        model.addAttribute("material", material);
        model.addAttribute("jsession", request.getSession().getId());

        // 初始化图片尺寸下拉框
        String curType = "material_size";

        List<MapKv> meteriSizeList = mapKvService.findMapKvByType(curType);

        model.addAttribute("meterSizeList", meteriSizeList);
        return "/advertisement/material-edit";
    }

    @RequestMapping(value = "/material/delete/{id}", method = RequestMethod.GET)
    @LoginRequired
    public String delete(HttpServletRequest request, HttpServletResponse response, Model model,
            @PathVariable
            int id) {
        int userid = SessionContainer.getSession().getUserId();
        adMaterialService.deleteAdMaterialById(userid, id);
        return "redirect:/material/list";
    }

    @RequestMapping(value = "/material/view/{id}", method = RequestMethod.GET)
    @LoginRequired
    public String preView(HttpServletRequest request, HttpServletResponse response, Model model,
            @PathVariable
            int id) {
        int userid = SessionContainer.getSession().getUserId();
        AdMaterial material = adMaterialService.findAdMaterialById(userid, id);
        model.addAttribute("material", material);
        return "/advertisement/material-view";
    }

    @RequestMapping(value = "/material/view_new/{id}", method = RequestMethod.GET)
    @LoginRequired
    public String preViewNew(HttpServletRequest request, HttpServletResponse response, Model model,@PathVariable int id) {
        int userid = SessionContainer.getSession().getUserId();
        AdMaterial material = adMaterialService.findAdMaterialById(userid, id);
        model.addAttribute("material", material);
        return "/advertisement/material-view";
    }
    @RequestMapping(value = "/material/addNew", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addNew(HttpServletRequest request, HttpServletResponse response,
            Model model, @ModelAttribute(value = "adMaterialDto")
            AdMaterialDto dto) {
        AdMaterial material = new AdMaterial();
        int mType = dto.getmType();
        material.setMType(mType);
        material.setMaterialName(dto.getMaterialName());
        if (mType == 1 || mType == 2) {
            material.setLinkUrl(dto.getLinkUrl());
            material.setMaterialDesc(dto.getMaterialDesc());
            material.setMaterialSize(dto.getMaterialSize());
            material.setTargetUrl(dto.getTargetUrl());
            material.setMaterialType(dto.getMaterialType());
        } else {
//            material.setMaterialType(0);
//            material.setRichText(dto.getRichText());
            material.setMaterialType(0);
            material.setRichText(dto.getRichText());
            material.setMaterialSize(dto.getMaterialSize());
        }

        Integer thirdMonitor = dto.getThirdMonitor();
        if (thirdMonitor != null) {
            material.setThirdMonitor(thirdMonitor);
            material.setMonitorLink(dto.getMonitorLink());
        }
        material.setUserid(SessionContainer.getSession().getUserId());
        if (dto.getId() > 0) {
            // 编辑修改
            material.setId(dto.getId());
            adMaterialService.editAdMaterial(material);
        } else {
            // 新增
            material.setCreateTime(DateUtil.formatToDate(
                    DateUtil.format(new Date(), DateUtil.C_TIME_PATTON_DEFAULT),
                    DateUtil.C_TIME_PATTON_DEFAULT));
            int key = adMaterialService.insertMaterialAndGetKey(material);
            material.setId(key);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", material);
        return map;
    }

    @RequestMapping(value = "/material/table-template", method = RequestMethod.GET)
    @LoginRequired
    public String table(HttpServletRequest request, HttpServletResponse response, Model model) {
     
        List<AdMaterial> materialList = adMaterialService.findCheckedAdMaterialListByUserid(SessionContainer.getSession().getUserId());
        
        model.addAttribute("materialList", materialList);
        return "/advertisement/table-template";
    }

    @RequestMapping(value = "/material/edit-template/{id}", method = RequestMethod.GET)
    public String editTmpl(HttpServletRequest request, HttpServletResponse response, Model model,
            @PathVariable
            int id) {
        int userid = SessionContainer.getSession().getUserId();
        AdMaterial material = adMaterialService.findAdMaterialById(userid, id);
        material.setRichText(material.getRichText() == null ? "" : material.getRichText().replace(
                "\"", "\'"));
        model.addAttribute("material", material);
        
        model.addAttribute("jsession", request.getSession().getId());
        
        String curType = "material_size";

        List<MapKv> meterTempList = mapKvService.findMapKvByType(curType);

        model.addAttribute("meterTempList", meterTempList);
        return "/advertisement/edit-material-template";
    }

    @RequestMapping(value = "/material/add-template", method = RequestMethod.GET)
    public String ddTmpl(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("jsession", request.getSession().getId());
        // 初始化图片尺寸下拉框
        String curType = "material_size";

        List<MapKv> meterTempList = mapKvService.findMapKvByType(curType);

        model.addAttribute("meterTempList", meterTempList);

        return "/advertisement/add-material-template";
    }
    
    /**
     * 判断是否可以删除广告物料
     * @param request
     * @param response
     * @param model
     * @param id
     * @return
     */
   
    @RequestMapping(value = "/material/ifdel/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> del(HttpServletRequest request, HttpServletResponse response, Model model,
    		@PathVariable
    		int id) {
    	String userid = SessionContainer.getSession().getUserId() +"";
    	List<AdInstance> instances = adMaterialService.getListByadIds(String.valueOf(id), userid);
    	Map<String, Object> map = new HashMap<String, Object>();
    	if(instances != null && instances.size() > 0){
    		 map.put("data", instances.get(0));
    	}else{
    		 map.put("data", "null");
    	}
       
        return map;
    }
}
