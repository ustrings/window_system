package com.hidata.ad.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.aspectj.apache.bcel.classfile.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.hidata.ad.web.dto.AdCategoryDto;
import com.hidata.ad.web.dto.AdCheckConfigDto;
import com.hidata.ad.web.dto.AdCheckProcessDto;
import com.hidata.ad.web.dto.AdDeviceLinkDto;
import com.hidata.ad.web.dto.AdExtLinkDto;
import com.hidata.ad.web.dto.AdInstanceDto;
import com.hidata.ad.web.dto.AdInstanceShowDto;
import com.hidata.ad.web.dto.AdPlanManageInfoDto;
import com.hidata.ad.web.dto.AdSiteDto;
import com.hidata.ad.web.dto.MediaCategoryDto;
import com.hidata.ad.web.dto.TAdCheckConfigDto;
import com.hidata.ad.web.dto.TAdCheckProcessDto;
import com.hidata.ad.web.dto.mongo.AdDeviceLink;
import com.hidata.ad.web.model.AdMaterial;
import com.hidata.ad.web.model.AdMaterialCache;
import com.hidata.ad.web.model.AdPlanPortrait;
import com.hidata.ad.web.model.AllBaseLabel;
import com.hidata.ad.web.model.AllLabelAdPlan;
import com.hidata.ad.web.model.ChannelBase;
import com.hidata.ad.web.model.ChannelSiteRel;
import com.hidata.ad.web.model.KeyWordAdPlan;
import com.hidata.ad.web.model.MapKv;
import com.hidata.ad.web.model.RegionTargeting;
import com.hidata.ad.web.model.TreeNodeVO;
import com.hidata.ad.web.service.AdCheckService;
import com.hidata.ad.web.service.AdCrowdFindInfoService;
import com.hidata.ad.web.service.AdExtLinkService;
import com.hidata.ad.web.service.AdMaterialService;
import com.hidata.ad.web.service.AdPlanManageService;
import com.hidata.ad.web.service.IIntrestsCrowdService;
import com.hidata.ad.web.service.MapKvService;
import com.hidata.ad.web.service.TerminalBaseInfoService;
import com.hidata.ad.web.service.VisitorCrowdService;
import com.hidata.ad.web.session.SessionContainer;
import com.hidata.ad.web.util.FileUtils;
import com.hidata.ad.web.util.FtpUtils;
import com.hidata.ad.web.util.JieBaUtils;
import com.hidata.framework.annotation.LoginRequired;
import com.hidata.framework.exception.AdException;
import com.hidata.framework.util.DateUtil;
import com.hidata.framework.util.StringUtil;
import com.hidata.ad.web.model.User;
import com.zel.enums.OperatorTypeEnum;
import com.zel.join.manager.JoinCalcManager;
import com.zel.utils.DBUtils;


/**
 * 广告计划制定主action Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月13日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
@Controller
public class AdPlanController {
	@Autowired
    private MapKvService mapKvService;
    // 重定向人群 Service
    @Autowired
    private VisitorCrowdService visitorservice;

    @Autowired
    private AdPlanManageService adPlanManageService;

    // @Autowired
    // private CrowdService crowdService;
    //
    @Autowired
    private AdMaterialService adMaterialService;

    // 终端重定向
    @Autowired
    private TerminalBaseInfoService terminalService;

    @Autowired
    private AdCrowdFindInfoService adCrowdFindInfoService;

    @Autowired
    private IIntrestsCrowdService iIntrestsCrowdService;
    
    @Autowired
    private AdExtLinkService adExtLinkservice;
    
    @Autowired
    private AdCheckService adCheckService;

    private static Logger logger = LoggerFactory.getLogger(AdPlanController.class);
    
    private static Properties props = null;

    static {
        try {
            props = PropertiesLoaderUtils.loadAllProperties("ftp-config.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 广告计划新增页面初始化
     * 
     * @param request
     * @param response
     * @return
     * @author zhoubin
     * @throws AdException
     */
    @RequestMapping("/adplan/initadd")
    public String adPlanInitAdd(HttpServletRequest request, HttpServletResponse response,
            Model model) throws AdException {
        try {
        	//初始化尺寸规格：
        	// 初始化图片尺寸下拉框
            String curType = "material_size";
            List<MapKv> meteriSizeList = mapKvService.findMapKvByType(curType);
            model.addAttribute("meterSizeList", meteriSizeList);
            
//            Crowd crowdQry = new Crowd();
//            crowdQry.setUserId(SessionContainer.getSession().getUserId() + "");
            // List<Crowd> crowdList = crowdService.getCrowdList(crowdQry);

//            List<AdCrowdFindInfoShowDto> crowdList = adCrowdFindInfoService
//                    .getCrowInfoByUserId(SessionContainer.getSession().getUserId() + "");

//            List<MediaCategoryDto> mediaCategoryDtoLeveOneList = adPlanManageService
//                    .findMediaCategoryDtoListByParendCode("-1"); //媒体分类
//            List<AdCategoryDto> adCategoryDtoLeveOneList = adPlanManageService
//                    .findAdCategoryDtoListByParentCode("-1");

            List<RegionTargeting> regionTargetingList = adPlanManageService
                    .findRegionListByParendCode("-1");

      //      model.addAttribute("mediaCategoryDtoLeveOneList", mediaCategoryDtoLeveOneList);
//            model.addAttribute("adCategoryDtoLeveOneList", adCategoryDtoLeveOneList); //广告类型定向

            model.addAttribute("regionTargetingList", regionTargetingList);// 地区定向
//            model.addAttribute("crowdList", crowdList);

            // 加入重定向人群
//            VisitorCrowd visitor = new VisitorCrowd();
//            visitor.setVcUserid(SessionContainer.getSession().getUserId() + "");
//            List<VisitorCrowd> visitorList = visitorservice.getVisitorList(visitor);
//            model.addAttribute("visitorList", visitorList);

            // 终端重定向
//            Map<String, List<TerminalBaseInfo>> terminal_map = terminalService.getAllGroupByType();
//            model.addAttribute("terminal_map", terminal_map);
            
            //域名定向    域名频道列展示
           // List<ChannelBase> channelBaseList = adPlanManageService.getChannelBaseInfo();
            //model.addAttribute("channelBaseList",channelBaseList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("add adplan 查询受众人群失败 ！！" + e, e);
            throw new AdException("add adplan 查询受众人群失败 ！！", e);
        }
        return "adplan/adplanAdd";
    }

    /**
     * 
     * @param request
     * @param response
     * @param model
     * @return
     * @author chenjinzhao
     * @throws AdException
     */
    @RequestMapping("/adplan/initedit/{adId}")
    public String adPlanInitEdit(HttpServletRequest request, HttpServletResponse response,
            @PathVariable
            String adId, Model model) throws AdException {
    	//初始化尺寸规格：
    	// 初始化图片尺寸下拉框
    	String curType = "material_size";
        List<MapKv> meteriSizeList = mapKvService.findMapKvByType(curType);
        model.addAttribute("meterSizeList", meteriSizeList);
    	//投放链接
       
        
        AdPlanManageInfoDto adPlanManageInfo = adPlanManageService.getOneAdplanInfo(adId);
        
        AdExtLinkDto adExtLink = adExtLinkservice.getAdExtLinkByadId(adId);
        if("E".equals(adPlanManageInfo.getAdInstanceDto().getLinkType())){
        	 model.addAttribute("adExtLink", adExtLink);
        }else if("J".equals(adPlanManageInfo.getAdInstanceDto().getLinkType()) || "L".equals(adPlanManageInfo.getAdInstanceDto().getLinkType())){
        	adPlanManageInfo.setJsContent(adExtLink.getThrowUrl());
        	adPlanManageInfo.setJsSize(adExtLink.getPicSize());
        	adPlanManageInfo.setJsType(adPlanManageInfo.getAdInstanceDto().getLinkType());
        	adPlanManageInfo.getAdInstanceDto().setLinkType("J");
        	/*adExtLink.setPicSize("");
        	adExtLink.setThrowUrl("");
        	model.addAttribute("adExtLink", adExtLink);*/
        }
       
//        Crowd crowdQry = new Crowd();
//        crowdQry.setUserId(SessionContainer.getSession().getUserId() + "");
        // List<Crowd> crowdList = crowdService.getCrowdList(crowdQry);
//        List<AdCrowdFindInfoShowDto> crowdList = adCrowdFindInfoService
//                .getCrowInfoByUserId(SessionContainer.getSession().getUserId() + "");
        
        /**********************************初始化关键词*ssq**************************/
        KeyWordAdPlan adKeyword  = adPlanManageService.getAdPlanKeyWordByAdId(adId);
        if(adKeyword!=null){
        	model.addAttribute("adKeyword",adKeyword);
        }
        
        /*********************************************************************/
        /**************************初始化人群画像定向**********************************/
        //AdPlanPortrait portrait = adPlanManageService.getAdPlanPortraitByAdId(adId);
        //if(portrait!=null){
        	//model.addAttribute("portrait",portrait);
        //}
        /*********************************************************************/
        // 加入重定向人群
//        VisitorCrowd visitor = new VisitorCrowd();
//        visitor.setVcUserid(SessionContainer.getSession().getUserId() + "");
//        List<VisitorCrowd> visitorList = visitorservice.getVisitorList(visitor);
//        model.addAttribute("visitorList", visitorList);

        // 加入终端定向
//        Map<String, List<TerminalBaseInfo>> terminal_map = terminalService.getAllGroupByType();
//        model.addAttribute("terminal_map", terminal_map);

        List<AdMaterial> materialList = adMaterialService.findAdMaterialListByAdId(Integer
                .valueOf(adId));

//        List<MediaCategoryDto> mediaCategoryDtoLeveOneList = adPlanManageService
//                .findMediaCategoryDtoListByParendCode("-1");
//        List<MediaCategoryDto> adMediaCategoryDtoList = adPlanManageService
//                .findMediaCategoryDtoListByAdId(adId);
//
//        List<AdCategoryDto> adCategoryDtoLeveOneList = adPlanManageService
//                .findAdCategoryDtoListByParentCode("-1");
//        List<AdCategoryDto> adCategoryDtoList = adPlanManageService
//                .findAdCategoryDtoListByAdId(adId);// 已选广告分类
//        model.addAttribute("adCategoryDtoLeveOneList", adCategoryDtoLeveOneList);
//        model.addAttribute("adCategoryDtoList", adCategoryDtoList);
//        model.addAttribute("crowdList", crowdList);
        model.addAttribute("adPlanManageInfo", adPlanManageInfo);
        model.addAttribute("materialList", materialList);

//        model.addAttribute("mediaCategoryDtoLeveOneList", mediaCategoryDtoLeveOneList);
//        model.addAttribute("adMediaCategoryDtoList", adMediaCategoryDtoList);

        List<RegionTargeting> regionList = adPlanManageService
                .findRegionTargetingListByParendCode("-1");
        List<RegionTargeting> adRegionList = adPlanManageService.findAdRegtionListByAdId(adId);// 已选区域
        model.addAttribute("regionList", regionList);
        model.addAttribute("adRegionList", adRegionList);
        // 查找已选择标签信息
       // List<AllLabelAdPlan> selectLabelPlanList = adCrowdFindInfoService
         //       .qryAllLabelInfoByAdId(adId);

        //model.addAttribute("selectLabelPlanList", selectLabelPlanList);
        
      //域名定向    域名频道列展示
       // List<ChannelBase> channelBaseList = adPlanManageService.getChannelBaseInfo();
       // model.addAttribute("channelBaseList",channelBaseList);
        
        
        
       
        model.addAttribute("adId",adId);
        return "adplan/adplanEdit";
    }

    /**
     * 广告计划新增
     * 
     * @param request
     * @param response
     * @param dto
     * @return
     * @author zhoubin
     * @throws AdException
     */
    @RequestMapping(value = "/adplan/add", method = RequestMethod.POST)
    @LoginRequired
    public String addAdPlan(HttpServletRequest request, HttpServletResponse response,
            AdPlanManageInfoDto adPlanManageInfoDto) throws AdException {   	
        try {       	
        	 	
            adPlanManageService.addAdPlan(request,adPlanManageInfoDto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("add adplan失败 ！！" + e, e);
            throw new AdException("add adplan失败 ！！", e);
        }
        return "redirect:/adplan/list";
    }

    @RequestMapping(value = "/adplan/adplaneditsaveaction/{adId}", method = RequestMethod.POST)
    @LoginRequired
    public String adPlanEditSave(HttpServletRequest request, HttpServletResponse response,
            AdPlanManageInfoDto adPlanManageInfoDto, @PathVariable
            String adId) throws AdException {
        //先删除保存的文件   	
        try {
  
            adPlanManageService.editAdPlan(request,adPlanManageInfoDto, adId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("edit adplan失败 ！！" + e, e);
            throw new AdException("edit adplan失败 ！！", e);
        }
        return "redirect:/adplan/list";
    }

    /**
     * 查询广告计划（修改）
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/adplan/list", method = RequestMethod.GET)
    public String adlist_new(HttpServletRequest request, HttpServletResponse response, Model model){
    	 List<AdInstanceShowDto> adList = adPlanManageService.getListAdShow(SessionContainer
                 .getSession().getUserId());
    	 try {
			if(adList != null && adList.size() > 0){
				 for(AdInstanceShowDto ad : adList){
					String adToufangSts = ad.getAdToufangSts();
					if("-1".equals(adToufangSts)){
						ad.setStatue("待提交审核");
					}else if("0".equals(adToufangSts)){
						ad.setStatue("待审核");
					}else if("2".equals(adToufangSts)){
						ad.setStatue("投放中");
					}else if("3".equals(adToufangSts)){
						ad.setStatue("暂停投放");
					}else if("4".equals(adToufangSts)){
						ad.setStatue("投放结束");
					}else if("5".equals(adToufangSts)){
						ad.setStatue("审核失败");
					}else if("6".equals(adToufangSts)){
						ad.setStatue("废除广告");
					}
				 }
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
         model.addAttribute("adList", adList);
         return "/adplan/adplan-list";
    }

    /**
     * 删除广告计划
     * 
     * @param request
     * @param response
     * @param id
     * @return
     */
    @RequestMapping(value = "/adplan/delete", produces="application/json",method = RequestMethod.GET)
    @ResponseBody
    public String del(HttpServletRequest request, HttpServletResponse response) {
    	User user = SessionContainer.getSession();
    	String id = request.getParameter("adId");
    	int result = adPlanManageService.updateAdInstanceByIdAndUser(SessionContainer.getSession().getUserId(),Integer.parseInt(id));
    	if("2".equals(user.getUsertype())){ 		
            List<AdCheckProcessDto> list = adCheckService.findAdCheckProcessByAdId(String.valueOf(id));
            if(result>0&&list.size()>0){
            	adCheckService.deleteAdCheckProcessByAdId(Integer.parseInt(id));
            }
    	}else if("3".equals(user.getUsertype())){
            List<TAdCheckProcessDto> list = adCheckService.findTAdCheckProcessByAdId(String.valueOf(id));
            if(result>0&&list.size()>0){
            	adCheckService.deleteTAdCheckProcessByAdId(Integer.parseInt(id));
            }
    	}
    	
        return "0";
    }

    @RequestMapping(value = "/adplan/mediaCateLeveTwo-template/{code}", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response, @PathVariable
    String code, Model model) {
        List<MediaCategoryDto> mediaCateList = adPlanManageService
                .findMediaCategoryDtoListByParendCode(code);
        model.addAttribute("mediaCateList", mediaCateList);
        return "/adplan/mediaCateLeveTwo-template";
    }

    @RequestMapping(value = "/adplan/regionLeveTwo-template/{code}", method = RequestMethod.GET)
    public String regionList(HttpServletRequest request, HttpServletResponse response,
            @PathVariable
            String code, Model model) {
        List<RegionTargeting> regionList = adPlanManageService.findRegionListByParendCode(code);
        model.addAttribute("regionList", regionList);
        return "/adplan/regionLeveTwo-template";
    }

    @RequestMapping(value = "/adplan/getSubMediaCateNum/{code}", method = RequestMethod.GET)
    @ResponseBody
    public String getSubMediaCateNumByCode(HttpServletRequest request,
            HttpServletResponse response, @PathVariable
            String code, Model model) {
        List<MediaCategoryDto> mediaCateList = adPlanManageService
                .findMediaCategoryDtoListByParendCode(code);
        int mediaCateTotalNum = 0;
        if (mediaCateList != null) {
            mediaCateTotalNum = mediaCateList.size();
        }
        return String.valueOf(mediaCateTotalNum);
    }

    @RequestMapping(value = "/adplan/adCategoryLeveTwo-template/{code}", method = RequestMethod.GET)
    public String adCategoryLeveTwolist(HttpServletRequest request, HttpServletResponse response,
            @PathVariable
            String code, Model model) {
        List<AdCategoryDto> adCateList = adPlanManageService
                .findAdCategoryDtoListByParentCode(code);
        model.addAttribute("adCateList", adCateList);
        return "/adplan/adCateLeveTwo-template";
    }

    @RequestMapping(value = "/adplan/getSubAdCateNum/{code}", method = RequestMethod.GET)
    @ResponseBody
    public String getSubAdCateNumByCode(HttpServletRequest request, HttpServletResponse response,
            @PathVariable
            String code, Model model) {
        List<AdCategoryDto> mediaCateList = adPlanManageService
                .findAdCategoryDtoListByParentCode(code);
        int mediaCateTotalNum = 0;
        if (mediaCateList != null) {
            mediaCateTotalNum = mediaCateList.size();
        }
        return String.valueOf(mediaCateTotalNum);
    }

    @RequestMapping(value = "/adplan/getSubAdRegionNum/{code}", method = RequestMethod.GET)
    @ResponseBody
    public String getSubAdRegionNumByCode(HttpServletRequest request, HttpServletResponse response,
            @PathVariable
            String code, Model model) {
        List<RegionTargeting> regionTargetingList = adPlanManageService
                .findRegionListByParendCode(code);
        int mediaCateTotalNum = 0;
        if (regionTargetingList != null) {
            mediaCateTotalNum = regionTargetingList.size();
        }
        return String.valueOf(mediaCateTotalNum);
    }

    /**
     * 初始化兴趣标签树
     * 
     * @param request
     * @param response
     * @return
     * @author zhoubin
     */
    @RequestMapping(value = "/adplan/initInterestTree", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String initInterestTree(HttpServletRequest request, HttpServletResponse response) {

        String crowdId = request.getParameter("crowdId");

        AllBaseLabel allBaseLabel = new AllBaseLabel();

        allBaseLabel.setPid("0");

        List<AllBaseLabel> resultList = iIntrestsCrowdService
                .qryAllBaseLabelChildrenByParent(allBaseLabel);

        List<TreeNodeVO> treeNodeChildList = new ArrayList<TreeNodeVO>();

        TreeNodeVO rootTreeNode = new TreeNodeVO();

        rootTreeNode.setId("0");

        rootTreeNode.setName("兴趣标签树");

        for (AllBaseLabel iterBaseLabel : resultList) {

            TreeNodeVO treeNodeVO = new TreeNodeVO();

            treeNodeVO.setId(iterBaseLabel.getId());

            treeNodeVO.setpId(iterBaseLabel.getPid());

            treeNodeVO.setName(iterBaseLabel.getName());

            treeNodeVO.setCrowdId(crowdId);

            treeNodeChildList.add(treeNodeVO);
        }

        rootTreeNode.setChildren(treeNodeChildList);

        JSONObject restJsonObj = JSONObject.fromObject(rootTreeNode);

        return restJsonObj.toString();
    }

    /**
     * 查找子节点
     * 
     * @param request
     * @param response
     * @return
     * @author zhoubin
     */
    @RequestMapping(value = "/adplan/findTreeNextChild", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String findTreeNextChild(HttpServletRequest request, HttpServletResponse response) {

        String parentLabelVal = request.getParameter("curLabel");

        parentLabelVal = request.getParameter("id");
        String crowdId = request.getParameter("crowdId");
        AllBaseLabel paraLabelTree = new AllBaseLabel();

        paraLabelTree.setPid(parentLabelVal);

        List<AllBaseLabel> resultList = iIntrestsCrowdService
                .qryAllBaseLabelChildrenByParent(paraLabelTree);

        List<TreeNodeVO> treeNodeChildList = new ArrayList<TreeNodeVO>();
        Map<String, Boolean> selectTreeMap = null;
        if (!StringUtils.isEmpty(crowdId)) {
            selectTreeMap = new HashMap<String, Boolean>();
            try {
                selectTreeMap = adCrowdFindInfoService.getSelectTreeMapByadId(crowdId,
                        selectTreeMap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (AllBaseLabel iterLabelTree : resultList) {

            TreeNodeVO treeNodeVO = new TreeNodeVO();

            treeNodeVO.setId(iterLabelTree.getId());

            treeNodeVO.setpId(iterLabelTree.getPid());
            treeNodeVO.setName(iterLabelTree.getName());
            treeNodeVO.setCrowdId(crowdId);
            if (selectTreeMap != null) {
                if (selectTreeMap.get(iterLabelTree.getId()) != null) {
                    if (selectTreeMap.get(iterLabelTree.getId())) {
                        treeNodeVO.setHalfCheck(false);
                        treeNodeVO.setChecked(true);
                    } else {
                        treeNodeVO.setHalfCheck(true);
                        treeNodeVO.setChecked(true);
                    }
                }
            }

            treeNodeChildList.add(treeNodeVO);
        }

        JSONArray restJsonObj = JSONArray.fromObject(treeNodeChildList);

        return restJsonObj.toString();
    }

    /**
     * 计算标签对应受众数量
     * 
     * @param request
     * @param response
     * @return
     * @author zhoubin
     */
//    @RequestMapping(value = "/adplan/caluteLabelCrowdNum", produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public String caluteLabelCrowdNum(HttpServletRequest request, HttpServletResponse response) {
//
//        String allLables = request.getParameter("allLables");
//
//        String[] labelArray = allLables.split(",");
//
//        String crowdNum = "";
//        try {
//
//            SearchConditionPojo searchConditionPojo = new SearchConditionPojo();
//
//            searchConditionPojo.setStart(0);
//            searchConditionPojo.setPageSize(10);
//            searchConditionPojo.setSearchType(SearchType.OR);
//
//            // SearchConditionUnit中的list
//            List<SearchConditionUnit> searchConditionUnitList = new ArrayList<SearchConditionUnit>();
//
//            for (String iterLabelCode : labelArray) {
//                if (StringUtil.isEmpty(iterLabelCode)) {
//                    continue;
//                }
//
//                AllBaseLabel retBaseLabel = iIntrestsCrowdService
//                        .qryAllBaseLabelByLabelVal(iterLabelCode);
//                if (null == retBaseLabel) {
//                    continue;
//                }
//
//                List<SearchConditionItem> searchConditionItemList = new ArrayList<SearchConditionItem>();
//
//                SearchConditionItem searchConditionItem = new SearchConditionItem();
//                searchConditionItem.setSearchType(SearchType.AND);
//                searchConditionItem.setMatchType(MatchType.Not_Analyzer);
//
//                if ("0".equals(retBaseLabel.getLevel())) {
//                    searchConditionItem.setName("classname");
//
//                } else {
//                    searchConditionItem.setName("label" + retBaseLabel.getLevel());
//                }
//
//                searchConditionItem.setValue(retBaseLabel.getName());
//
//                searchConditionItemList.add(searchConditionItem);
//
//                SearchConditionUnit searUnit = new SearchConditionUnit();
//
//                searUnit.setSearchConditionItemList(searchConditionItemList);
//
//                searUnit.setSearchType(SearchType.OR);
//
//                searchConditionUnitList.add(searUnit);
//            }
//
//            searchConditionPojo.setSearchConditionUnitList(searchConditionUnitList);
//
//            searchConditionPojo.setIndexName("basic_label");
//
//            searchConditionPojo.setRemoveCommon("ad_ua_encode");
//
//            List<String> retFiledList = new ArrayList<String>();
//
//            retFiledList.add("ad_ua_encode");
//
////            searchConditionPojo.setFiledList(retFiledList);
//            
//           // searchConditionPojo.setPageSize(10000);
//
//            SearchSourceBuilder sb = SearchSourceBuilderGeneratorUtil
//                    .getSearchSourceBuilder(searchConditionPojo);
//            if(sb == null){
//            	return "";
//            }
//            System.out.println(sb.toString());
//            DefaultHttpClient client = new DefaultHttpClient();
////            HttpPost httpPost = new HttpPost(
////                    "http://222.247.62.139:9200/product_label/label/_search");
//            HttpPost httpPost = new HttpPost(
//                    "http://222.247.62.139:9200/basic_label/jiangsu/_search");
//            StringEntity entity = new StringEntity(sb.toString(), "utf-8");
//            httpPost.setEntity(entity);
//            HttpResponse resp = client.execute(httpPost);
//
//            InputStream stream = resp.getEntity().getContent();
//            BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
//            StringBuffer buf = new StringBuffer();
//            String line;
//            while (null != (line = br.readLine())) {
//                buf.append(line).append("\n");
//            }
//            System.out.println(buf.toString());
//            String crowd_baseInfo = buf.toString();
//
//            Map<String, JSONObject> jsonObj = (Map<String, JSONObject>) JSON.parse(crowd_baseInfo);
//            Map<String, Map<String, Integer>> aggregationsMap = (Map<String, Map<String, Integer>>) jsonObj
//                    .get("aggregations");
//            Integer value = aggregationsMap.get("agg_name").get("value");
//            crowdNum = String.valueOf(value);
//            // 耗时
//            String takeMs = String.valueOf(jsonObj.get("took"));
//            System.out.println(takeMs);
//
//            Map<String, Object> resultDetailMap = (Map<String, Object>) jsonObj.get("hits");
//
//            String totalCount = String.valueOf(resultDetailMap.get("total"));
//
//            System.out.println(totalCount);
//
//            JSONObject jsonObject = new JSONObject();
//
//            jsonObject.put("takeMs", takeMs);
//
//            jsonObject.put("pvCount", totalCount);
//
//            jsonObject.put("uvCount", crowdNum);
//
////            List<Map<String,Map<String,Object>>> resultList = (List<Map<String,Map<String,Object>>>) resultDetailMap.get("hits");
////            
////            for(Map<String,Map<String,Object>> iterObject :resultList)
////            {
////                
////                Map<String,Object>  iterMap = iterObject.get("fields");
////                
////                List<String> adValueList = (List<String>)iterMap.get("ad_ua_encode");
////               
////               System.out.println(adValueList.get(0));
////            }
//
//            return jsonObject.toString();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return "";
//    }
   /* private void testCreateFile(SearchConditionPojo searchConditionPojo,int totalNum,int pageSize)
    {
        
        int pageNum=0;
        
        if(totalNum%pageSize==0)
        {
            pageNum = totalNum/pageSize;
        }else{
            pageNum   = totalNum/pageSize+1;
            
        }
        

        for(int i=0;i<pageNum;i++)
        {
            int offset = pageSize*i;
            
            searchConditionPojo.setPageSize(pageSize);
            
            searchConditionPojo.setStart(offset);
            
            System.out.println("现在正在取数据从:------"+offset+"--------------开始");
            
            this.createTextFile(searchConditionPojo);
            
        }
        
    }

    private void createTextFile(SearchConditionPojo searchConditionPojo) {

        try {

            SearchSourceBuilder sb = SearchSourceBuilderGeneratorUtil
                    .getSearchSourceBuilder(searchConditionPojo);

//            System.out.println(sb.toString());

            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(
                    "http://222.247.62.139:9200/product_label/label/_search");
            StringEntity entity = new StringEntity(sb.toString(), "utf-8");
            httpPost.setEntity(entity);
            HttpResponse resp = client.execute(httpPost);

            InputStream stream = resp.getEntity().getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            StringBuffer buf = new StringBuffer();
            String line;
            while (null != (line = br.readLine())) {
                buf.append(line).append("\n");
            }
   //         System.out.println(buf.toString());
            String crowd_baseInfo = buf.toString();

            Map<String, JSONObject> jsonObj = (Map<String, JSONObject>) JSON.parse(crowd_baseInfo);

            Map<String, Object> resultDetailMap = (Map<String, Object>) jsonObj.get("hits");

            List<Map<String, Map<String, Object>>> resultList = (List<Map<String, Map<String, Object>>>) resultDetailMap
                    .get("hits");

            StringBuffer pageStringBuffer = new StringBuffer();

            for (Map<String, Map<String, Object>> iterObject : resultList) {

                Map<String, Object> iterMap = iterObject.get("fields");

                List<String> adValueList = (List<String>) iterMap.get("ad_ua_encode");

                pageStringBuffer.append(adValueList.get(0) + "\r\n");

                // System.out.println(adValueList.get(0));
            }

            URL curClassPath = Thread.currentThread().getContextClassLoader().getResource("");

            String destFile = curClassPath.getPath().toString()+"/adresult/";

            FileUtils.write(destFile, "adresult.txt", true, pageStringBuffer.toString());

        } catch (Exception e) {
            // TODO: handle exception
        }

    }
    */
    /**
     * 查找匹配的关键字
     * @param request
     * @param response
     * @param model
     * @return
     */
//    @RequestMapping(value="/adplan/searchKeywords",produces="application/json")
//    @ResponseBody
//    public String SearchKeywords(HttpServletRequest request,HttpServletResponse response,Model model){
//    	String keywords = request.getParameter("keywords");
//    	String keywordAdPlan="";
//    	String fetchCicle = request.getParameter("kwUrlFetchCicle");
//    	String matchType = request.getParameter("matchType");  //匹配关系  1. 精准   2.中心 
//    	Date nowDate = new Date();
//		  SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
//		  GregorianCalendar gc = new GregorianCalendar();
//		  gc.setTime(nowDate);
//		 
//		  if(fetchCicle.equals("1")){
//			  gc.add(5, -1);    //一天前
//		  }else if(fetchCicle.equals("3")){
//			  gc.add(5,-3 );    //三天前
//		  }else if(fetchCicle.equals("7")){
//			  gc.add(3, -1);    //一周前
//		  }else if(fetchCicle.equals("15")){
//			  gc.add(5,-15);    //半个月前
//		  }else if(fetchCicle.equals("30")){
//			  gc.add(2, -1);    //一个月前
//		  }
//		  String beforeDate = formatter.format(gc.getTime());
//		  String nowdate = formatter.format(nowDate);
//        try {		
//			SearchConditionPojo searchConditionPojo = new SearchConditionPojo();		
//			searchConditionPojo.setStart(0);		
//			searchConditionPojo.setPageSize(1);
//			searchConditionPojo.setSearchType(SearchType.AND);
//
//			List<SearchConditionUnit> searchConditionUnitList = new ArrayList<SearchConditionUnit>();  
//			
//			SearchConditionUnit searchConditionUnit_WILD = new SearchConditionUnit();
//			List<SearchConditionItem> searchConditionItemList4Unit = new ArrayList<SearchConditionItem>();
//			searchConditionUnit_WILD.setSearchType(SearchType.AND);	
//			SearchConditionItem searchConditionItem =null;
//			if(!keywords.equals("")){
//			  keywords = keywords.replace(" ","");
//			  String[] keyWords = keywords.split("\\n");
//			  for(int i=0;i<keyWords.length;i++){
//				  if(!keyWords[i].equals("")){
//				     searchConditionItem = new SearchConditionItem();
//				     if(matchType.equals("1")){  //精准
//				    	 searchConditionItem.setSearchType(SearchType.OR);
//				    	 searchConditionItem.setMatchType(MatchType.Not_Analyzer);
//				    	 searchConditionItem.setName("keyword_not_analyzer");
//				     }else if(matchType.equals("2")){  //模糊
//				    	 searchConditionItem.setSearchType(SearchType.OR);
//						 searchConditionItem.setMatchType(MatchType.Analyzer);
//						 searchConditionItem.setName("keyword");
//				     }				 
//					
//					 searchConditionItem.setValue(keyWords[i]);
//					 searchConditionItemList4Unit.add(searchConditionItem);
//				  }
//			  }
//			}
//			searchConditionUnit_WILD.setSearchConditionItemList(searchConditionItemList4Unit);
//			searchConditionUnitList.add(searchConditionUnit_WILD);
//			
//			//时间 限制			  
//			SearchConditionUnit searchConditionUnit_RANGE = new SearchConditionUnit();
//			List<SearchConditionItem> searchConditionItemList4Unit_RANGE = new ArrayList<SearchConditionItem>();
//			searchConditionUnit_RANGE.setSearchType(SearchType.AND);	
//			  
//			SearchConditionItem searchConditionItem1 = new SearchConditionItem();
//			searchConditionItem1.setSearchType(SearchType.RANGE);
//			searchConditionItem1.setMatchType(MatchType.Not_Analyzer);
//			searchConditionItem1.setName("dt");
//			searchConditionItem1.setFromObj(beforeDate);
//			searchConditionItem1.setToObj(nowdate);
//			searchConditionItemList4Unit_RANGE.add(searchConditionItem1);
//             
//			searchConditionUnit_RANGE.setSearchConditionItemList(searchConditionItemList4Unit_RANGE);
//			searchConditionUnitList.add(searchConditionUnit_RANGE);
//						
//			searchConditionPojo.setSearchConditionUnitList(searchConditionUnitList);
//			searchConditionPojo.setIndexName("crowd_keyword");
//			searchConditionPojo.setRemoveCommon("ad_ua_encode");
//			
//			SearchSourceBuilder sb = SearchSourceBuilderGeneratorUtil.getSearchSourceBuilder(searchConditionPojo);
//			 System.out.println(sb.toString());
//			Map<String,JSONObject> jsonObj = (Map<String, JSONObject>) JSON.parse(httpClient(sb.toString(),"http://222.247.62.139:9200/crowd_keyword/_search/"));
//		    Map<String,Map<String,Integer>>  aggregationsMap = (Map<String, Map<String, Integer>>) jsonObj.get("aggregations");
//		    Integer value = aggregationsMap.get("agg_name").get("value");
//		    keywordAdPlan = String.valueOf(value); //去重后的人群数
//		    String takeMs = String.valueOf(jsonObj.get("took")); //所花费的时间
//		    
//		    Map<String, Object> resultDetailMap = (Map<String, Object>) jsonObj.get("hits");
//		    String totalCount = String.valueOf(resultDetailMap.get("total"));  //去重前的人群数
//            JSONObject jsonObject = new JSONObject();
//
//            jsonObject.put("takeMs", takeMs);
//            jsonObject.put("totalCount", totalCount);
//            jsonObject.put("keyWordCount", keywordAdPlan);
//            
//            return jsonObject.toString();
//            
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//        return "";	
//    }
    
    /**
     * 向es发送请求
     * @param searchSql
     * @param url
     * @return
     */
    public static String httpClient(String searchSql,String url){ 
		 String crowd_baseInfo="";
		try{		
		   DefaultHttpClient  client = new DefaultHttpClient();
		   HttpPost httpPost=new HttpPost(url);
		   StringEntity entity = new StringEntity(searchSql,"utf-8");
		   entity.setContentType("application/json");
		   entity.setContentEncoding("utf-8");
		   httpPost.setEntity(entity);
          HttpResponse resp=client.execute(httpPost);
       
		   InputStream stream = resp.getEntity().getContent(); 
		   BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8")); 
		   StringBuffer buf = new StringBuffer();  
	       String line;  
	       while (null != (line = br.readLine())) {  
	          buf.append(line).append("\n");  
	       }  
	       crowd_baseInfo = buf.toString();
	       System.out.println(crowd_baseInfo);
		}catch(Exception e){
			e.printStackTrace();
		}
		return crowd_baseInfo;
	}
    
    /**
     * 域名频道列表展示   channel_site_rel
     * @param request
     * @param response
     * @param model
     * @return
     * @author ssq
     */
    @RequestMapping(value="/adplan/channelList", method = RequestMethod.GET)
    @LoginRequired
    public String channelList(HttpServletRequest request,HttpServletResponse response,Model model){
    	String channels = request.getParameter("channels");
    	channels = channels.trim().substring(0, channels.length() - 1);
    	List<ChannelSiteRel> channelSiteRel = adPlanManageService.findChannelSiteRelByChannelIds(channels);
    	model.addAttribute("channelSiteRel",channelSiteRel);
    	return "/adplan/adChannel_list";
    }
    
    /**
     * 新增域名
     * @param request
     * @param response
     * @param model
     * @return
     * @author ssq
     */
    @RequestMapping(value="/adplan/setChannelUrl", method = RequestMethod.GET)
    @ResponseBody
    public String setChannelUrl(HttpServletRequest request,HttpServletResponse response,Model model){
    	
    	String urls = request.getParameter("channelurl");
    	String channelId = request.getParameter("channelTypeSelected");
    	
        HttpSession session = request.getSession();
    	return "/adplan/addChannelUrl";
    }
    
    /**
     * 修改时初始化频道域名
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="/adplan/initChannelurl", method = RequestMethod.GET)
    @LoginRequired
    public String initChannelurl(HttpServletRequest request,HttpServletResponse response,Model model){
    	String adId = request.getParameter("adId");
    	
    	List<AdSiteDto> adSiteList = adPlanManageService.findAdSiteDtoByAdId(adId);
    	for(int i=0;i<adSiteList.size();i++){
    		 AdSiteDto adSiteDto = adSiteList.get(i);
    		 String channelId = adSiteDto.getChannelId();
    		 ChannelBase channelBase = adPlanManageService.findChannelBaseByChannelId(channelId);
    		 String channelName = channelBase.getChannelName();
    		 adSiteDto.setChannelName(channelName);
    	}
    	model.addAttribute("adSiteList",adSiteList);
    	return "/adplan/selectedChannelUrl";
    }
    
    /**
     * 已选择的域名的展示
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="/adplan/selectChannelUrl", method = RequestMethod.GET)
    @LoginRequired
    public String selectChannelUrl(HttpServletRequest request,HttpServletResponse response,Model model){
    	String channelIds = request.getParameter("channelIds");
    	List<ChannelSiteRel> channelSiteRel = adPlanManageService.findChannelSiteRelByIds(channelIds);
    	model.addAttribute("channelSiteRel",channelSiteRel);
    	return "/adplan/selectedChannelUrl";
    }
    
    /**
     * 将广告插入到审核平台进行审核
     * @param request
     * @param response
     * @param adId
     * @return
     * @throws AdException
     */
    @RequestMapping(value="/adplan/submitCheck/{adId}",method = RequestMethod.GET)
    @LoginRequired
    public String submitCheck(HttpServletRequest request, HttpServletResponse response,
           @PathVariable String adId) throws AdException {
    	try {
    		User user = SessionContainer.getSession();
    		if("2".equals(user.getUsertype())){
    			List<AdCheckConfigDto> adCheckConfig =  adCheckService.getAdCheckConfigInfo();
                for(int i=0;i<adCheckConfig.size();i++){
                	AdCheckConfigDto adCheckConfigDto = adCheckConfig.get(i);
                	AdCheckProcessDto adCheckProcess  = new AdCheckProcessDto();
                	adCheckProcess.setAdId(adId);
                	adCheckProcess.setCheckUserId(adCheckConfigDto.getId());
                	adCheckProcess.setCheckSts("1");
                	adCheckProcess.setCheckDesc("待审核");
                	adCheckProcess.setSts("A");
                	adCheckProcess.setStsDate(DateUtil.getCurrentDateTimeStr());         	
                	adCheckService.insertAdCheckProcess(adCheckProcess);
                }
    		}else if("3".equals(user.getUsertype())){
    			List<TAdCheckConfigDto> t_adCheckConfig =  adCheckService.getTAdCheckConfigInfo();
                for(int i=0;i<t_adCheckConfig.size();i++){
                	TAdCheckConfigDto t_adCheckConfigDto = t_adCheckConfig.get(i);
                	TAdCheckProcessDto t_adCheckProcess  = new TAdCheckProcessDto();
                	t_adCheckProcess.setAdId(adId);
                	t_adCheckProcess.setCheckUserId(t_adCheckConfigDto.getId());
                	t_adCheckProcess.setCheckSts("1");
                	t_adCheckProcess.setCheckDesc("待审核");
                	t_adCheckProcess.setSts("A");
                	t_adCheckProcess.setStsDate(DateUtil.getCurrentDateTimeStr());         	
                	adCheckService.insertTAdCheckProcess(t_adCheckProcess);
                }
    		}  		
            AdInstanceDto adInstanceDto = new AdInstanceDto();
            adInstanceDto.setAdToufangSts("0");
            adInstanceDto.setAdId(adId);
            adCheckService.updateAdInstanceByAdId(adInstanceDto);              		
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("submitCheck失败 ！！" + e, e);
            throw new AdException("submitCheck失败 ！！", e);
        }
    	return "redirect:/adplan/list";
    }
    
    /**
     * 关键词上传的检验
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/adplan/checkkw",method=RequestMethod.POST)
    @ResponseBody
    public String checkkw(HttpServletRequest request,HttpServletResponse response){
    	String kwfilename =  request.getParameter("kwfilepath");
    	String type = request.getParameter("type");
    	String filePath = request.getSession().getServletContext().getRealPath("/upload_kw");
    	File file = new File(filePath+"/"+kwfilename); 
    	if("1".equals(type)&&file.exists()){   		 
    	     return "0";    	    
    	}else if("2".equals(type)){
    		String adId = request.getParameter("adId");
        	KeyWordAdPlan keywordAdPlan = adPlanManageService.findAdPlanKeywordByAdId(adId);
        	String keyWd = keywordAdPlan.getKeyWd();
        	if (file.exists()&&!kwfilename.equals(keyWd)) {   
                return "0";  
            } 
    	}     
    	return "2";  
    }
    
    /**
     * 关键词下载
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/adplan/kwFileDownload",method=RequestMethod.POST)
    public  ModelAndView downFileFtp(HttpServletRequest request,HttpServletResponse response)throws Exception{
    	String adId = request.getParameter("adPlanId");
    	KeyWordAdPlan keywordAdPlan = adPlanManageService.findAdPlanKeywordByAdId(adId);  
    	String fileName = keywordAdPlan.getKeyWd();
    	String filePath = request.getSession().getServletContext().getRealPath("/upload_kw");
        File dirPath = new File(filePath);   
        if (!dirPath.exists()) {   
           dirPath.mkdir();   
        }
    	String hostname = props.getProperty("kw_host");
    	String port = props.getProperty("kw_port");
    	String username = props.getProperty("kw_username");
    	String password = props.getProperty("kw_password");
    	String remotePath = props.getProperty("kw.directory");       
        boolean result = FtpUtils.downFile(hostname,Integer.parseInt(port),username,password,remotePath,fileName,"D:\\");  
        if(result){       	
        	BufferedInputStream bis = null;
        	BufferedOutputStream bos = null;     	
        	try{
        		long fileLength = new File(filePath+"/"+fileName).length();
        		response.setContentType("application/x-msdownload");
        		response.setHeader("Content-disposition","attachment; filename="
        				 + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
        		response.setHeader("Content-Length", String.valueOf(fileLength));
        		bis = new BufferedInputStream(new FileInputStream(filePath+"/"+fileName)); 
        		bos = new BufferedOutputStream(response.getOutputStream());
        		
        		byte[] buff = new byte[2048];   
                int bytesRead;   
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {   
                    bos.write(buff, 0, bytesRead);   
                }      	
        	}catch(Exception e){
        		e.printStackTrace();
        	}finally{
        		if(bis != null){
        			bis.close();
        		}
        		if(bos != null){
        			bos.close();
        		}
        		File file1 = new File(filePath+"/"+fileName);
        	    if(file1.exists()){
        	    	file1.delete();
        	    }  
        	} 	
        }
        return null;     
    }
    
    /**
     * js代码展示
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="/adplan/jsShow", method = RequestMethod.GET)
    public String showJsCodeAd(HttpServletRequest request,HttpServletResponse response,Model model){
    	String adId = request.getParameter("adId");
    	if(StringUtil.isNotEmpty(adId)){
    		AdExtLinkDto adExtLink =  adPlanManageService.findAdExtLinkByAdId(adId);//getAdExtLinkByAdId(adId);
        	model.addAttribute("adExtLink", adExtLink);
    	}
    	return "/html/jsCode";
    }
    
    
    /**
     * js代码展示
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="/adplan/simulationShow", method = RequestMethod.GET)
    public String simulationShow(HttpServletRequest request,HttpServletResponse response,Model model){
    	String adId = request.getParameter("adId");
    	String linkType = request.getParameter("linkType");
    	String pageUrl = request.getParameter("pageUrl");
    	
    	if(StringUtil.isNotEmpty(adId)){
    		
    		String w="";
    		String h="";
    		
    		String urlOrJs = "";
    		
    		String url ="";
    		
    		String jsCode = "";
    		String jsLocation ="";
    		
    		
    		if("M".equals(linkType)){
    			AdInstanceDto  adInst = adPlanManageService.getAdInstanceDtoByAdId(adId);
        		List<AdMaterialCache> adMList = adMaterialService.findAdMaterialCacheListByAdId(adId);
        		
        		for(AdMaterialCache adc : adMList){
        			w = adc.getWidth();
        			h = adc.getHeight();
        			break;
        		}
        		urlOrJs="URL";
        		url  = adInst.getAdUrl();
        		
    		}else if("E".equals(linkType)){
    			AdExtLinkDto adExtLink =  adPlanManageService.findAdExtLinkByAdId(adId);//getAdExtLinkByAdId(adId);
    			w = adExtLink.getWidth();
    			h = adExtLink.getHeight();
    			urlOrJs = "URL";
    			url = adExtLink.getThrowUrl();
    		}else if("J".equals(linkType)){
    			AdExtLinkDto adExtLink =  adPlanManageService.findAdExtLinkByAdId(adId);//getAdExtLinkByAdId(adId);
    			w = adExtLink.getWidth();
    			h = adExtLink.getHeight();
    			urlOrJs = "JS";
    			jsCode = adExtLink.getThrowUrl();
    			
    			jsLocation  ="J";
    		}else if("L".equals(linkType)){
    			AdExtLinkDto adExtLink =  adPlanManageService.findAdExtLinkByAdId(adId);//getAdExtLinkByAdId(adId);
    			w = adExtLink.getWidth();
    			h = adExtLink.getHeight();
    			urlOrJs = "JS";
    			jsCode = adExtLink.getThrowUrl();
    			
    			jsLocation  ="L";
    		}
    		
    		
    		
        	model.addAttribute("w", w);
        	model.addAttribute("h", h);
        	model.addAttribute("urlOrJs", urlOrJs);
        	model.addAttribute("url", url);
        	model.addAttribute("jsCode", jsCode);
        	model.addAttribute("jsLocation", jsLocation);
        	model.addAttribute("pageUrl", pageUrl);
    	}
    	return "/html/simulationShow";
    }
    
    
    
    /**
     * 计算标签对应受众数量
     * 
     * @param request
     * @param response
     * @return
     * @author zhoubin
     */
    @RequestMapping(value = "/adplan/caluteLabelCrowdNum", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String caluteLabelCrowdNum(HttpServletRequest request, HttpServletResponse response)throws Exception{
    	
    	DBUtils dBUtils = new DBUtils();
    	String allLables = request.getParameter("allLables"); 	
        String[] labelArray = allLables.split(",");
        String[] crowdNum = new String[labelArray.length];
        for (int m=0;m<labelArray.length;m++){
        	String iterLabelCode = labelArray[m];
            if (StringUtil.isEmpty(iterLabelCode)) {
              continue;
            }
            String labels="";
            AllBaseLabel retbaselabel = iIntrestsCrowdService.qryAllBaseLabelByLabelVal(iterLabelCode);
            if(retbaselabel==null){
          	  break;
            }
            String pId = retbaselabel.getPid();
            String lName = retbaselabel.getName();
            String level = retbaselabel.getLevel();
            if("0".equals(pId)){
            	List<AllBaseLabel> allBaseLabelList = iIntrestsCrowdService.qryAllBaseLabelByPId(iterLabelCode);
            	String[] pcNum = new String[allBaseLabelList.size()];
            	if(allBaseLabelList!=null&&allBaseLabelList.size()>0){
            		for(int l=0;l<allBaseLabelList.size();l++){
            		   String lname = allBaseLabelList.get(l).getName();
            		   String id = allBaseLabelList.get(l).getId();
            		   String p_c_label = lName +"/"+lname;
            		   if("basic".equals(lName)){
            			   List<AllBaseLabel> allList = iIntrestsCrowdService.qryAllBaseLabelByPId(id);
            			   if(allList.size()>0&&allList!=null){
                           	  String[] childName = new String[allList.size()];
                           	  for(int o=0;o<allList.size();o++){
                           		  AllBaseLabel allbase = allList.get(o);
                           		  childName[o] = String.valueOf(dBUtils.getFreq4Tag(p_c_label+"/"+allbase.getName()));                   		
                           	  }
                              long temp2=0;
                              long result2=0;
                              for(int i=0;i<childName.length;i=i+2){       	
                               	  result2 = JoinCalcManager.calc(temp2,Long.parseLong(childName[i]) ,OperatorTypeEnum.Union);
                               	  if(i==childName.length-1){
                               		  break;
                               	  }
                               	  temp2=JoinCalcManager.calc(result2,Long.parseLong(childName[i+1]),OperatorTypeEnum.Union);       	
                              }     
                              if(childName.length%2==0){
                            	  pcNum[l]=String.valueOf(temp2);
                              }else{
                            	  pcNum[l]=String.valueOf(result2);
                              }
                            }
            		     }else{
            		    	 pcNum[l] = String.valueOf(dBUtils.getFreq4Tag(p_c_label));
            		     }         		     
            		}
            		long temp=0;
            		long result=0;                  
                    for(int i=0;i<pcNum.length;i=i+2){       	
                    	result = JoinCalcManager.calc(temp,Long.parseLong(pcNum[i]) ,OperatorTypeEnum.Union);
                    	if(i==pcNum.length-1){
                    		break;
                    	}
                    	temp=JoinCalcManager.calc(result,Long.parseLong(pcNum[i+1]),OperatorTypeEnum.Union);       	
                    } 
                    if(pcNum.length%2==0){
                    	crowdNum[m]=String.valueOf(temp);
                    }else{
                    	crowdNum[m]=String.valueOf(result);
                    }
            	}
            }else if(!"0".equals(pId)){            
               for(int k=0;;k++){
            	  AllBaseLabel retBaseLabel = iIntrestsCrowdService.qryAllBaseLabelByLabelVal(iterLabelCode);
                  if(retBaseLabel==null){
              	     break;
                  }
                  String parentId = retBaseLabel.getPid();
                  String labelName = retBaseLabel.getName();
			      if(!"-1".equals(parentId)){
				      labels = labels+labelName+",";
				      iterLabelCode  = parentId;
			      }else{
				      break;
			      }			   
			    }
                labels=labels.trim().substring(0,labels.length()-1);
                String[] label = labels.split(",");
                String labelName="";
                for(int i=label.length-1;i>=0;i--){
            	   labelName=labelName+"/"+label[i];
                }
                labelName=labelName.trim().substring(1,labelName.length());
                String[] labelsplit = labelName.split("/");
                if("1".equals(level)&&"basic".equals(labelsplit[0])){
                	List<AllBaseLabel> allList = iIntrestsCrowdService.qryAllBaseLabelByPId(labelArray[m]);
                    if(allList.size()>0&&allList!=null){
                    	String[] childName = new String[allList.size()];
                    	for(int o=0;o<allList.size();o++){
                    		AllBaseLabel allbase = allList.get(o);
                    		childName[o] = String.valueOf(dBUtils.getFreq4Tag(labelName+"/"+allbase.getName()));                   		
                    	}
                    	long temp1=0;
                    	long result1=0;
                        for(int i=0;i<childName.length;i=i+2){       	
                        	result1 = JoinCalcManager.calc(temp1,Long.parseLong(childName[i]) ,OperatorTypeEnum.Union);
                        	if(i==childName.length-1){
                        		break;
                        	}
                        	temp1=JoinCalcManager.calc(result1,Long.parseLong(childName[i+1]),OperatorTypeEnum.Union);       	
                        }     
                        if(childName.length%2==0){
                        	crowdNum[m]=String.valueOf(temp1);
                        }else{
                        	crowdNum[m]=String.valueOf(result1);
                        }
                    }
                }else{
                	crowdNum[m]=String.valueOf(dBUtils.getFreq4Tag(labelName));
                }              
              }
        }
        //计算
        long temp=0;
        long result=0;
        for(int i=0;i<crowdNum.length;i=i+2){       	
        	result = JoinCalcManager.calc(temp,Long.parseLong(crowdNum[i]) ,OperatorTypeEnum.Union);
        	if(i==crowdNum.length-1){
        		break;
        	}
        	temp=JoinCalcManager.calc(result,Long.parseLong(crowdNum[i+1]),OperatorTypeEnum.Union);       	
        }     
        JSONObject jsonObject = new JSONObject();
        if(crowdNum.length%2==0){
        	jsonObject.put("pvCount", temp);
        }else{
        	jsonObject.put("pvCount", result);
        }
        
        return jsonObject.toString();
    }
    
    /**
     * 计算关键词的人群
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/adplan/searchKeywords",produces="application/json")
    @ResponseBody
    public String SearchKeywords(HttpServletRequest request,HttpServletResponse response,Model model) throws Exception{
    	String keywords = request.getParameter("keywords");
	    keywords = keywords.replace(" ","");
	    String[] keyWords = keywords.split("\\n");
	    DBUtils dBUtils = new DBUtils();
	    String[] crowdNum = new String[keyWords.length];
		for(int i=0;i<keyWords.length;i++){
			Set<String> result = JieBaUtils.getFenci(keyWords[i]);
	    	String kws = result.toString();
	    	kws=kws.substring(1,kws.length()-1);
	    	String[] fencikws = kws.split(",");
	    	Set<String> kwFCSet = new HashSet<String>();
	    	for(int j=0;j<fencikws.length;j++){
	    		kwFCSet.add(fencikws[j]);   	    	  
	    	}
	    	String fenciKw = kwFCSet.toString();
			fenciKw=fenciKw.substring(1,fenciKw.length()-1);
			String[] fencikw = fenciKw.split(",");
			String[] fc_crowdNum = new String[fencikw.length];
			for(int n=0;n<fencikw.length;n++){
				fc_crowdNum[n] = String.valueOf(dBUtils.getFreq4Keyword(fencikw[n]));
			}
			long temp=0;
			long res=0;
		    for(int m=0;m<fc_crowdNum.length;m=m+2){       	
		        res = JoinCalcManager.calc(temp,Long.parseLong(fc_crowdNum[m]) ,OperatorTypeEnum.Union);
		        if(m==fc_crowdNum.length-1){
		        	break;
		        }
		        temp=JoinCalcManager.calc(res,Long.parseLong(fc_crowdNum[m+1]),OperatorTypeEnum.Union);       	
		    }  
		    if(fc_crowdNum.length%2==0){
		    	crowdNum[i]=String.valueOf(temp);
		    }else{
		    	crowdNum[i]=String.valueOf(res);
		    }
		    
	    }
		long temp=0;
		long result=0;
	    for(int i=0;i<crowdNum.length;i=i+2){       	
	        result = JoinCalcManager.calc(temp,Long.parseLong(crowdNum[i]),OperatorTypeEnum.Union);
	        if(i==crowdNum.length-1){
	        	break;
	        }
	        temp=JoinCalcManager.calc(result,Long.parseLong(crowdNum[i+1]),OperatorTypeEnum.Union);       	
	    }     
	    JSONObject jsonObject = new JSONObject();
	    if(crowdNum.length%2==0){
	    	jsonObject.put("keyWordCount", temp);
	    }else{
	    	jsonObject.put("keyWordCount", result);
	    }	    
	    return jsonObject.toString();    	   	
    }
}