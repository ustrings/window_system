package com.hidata.ad.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hidata.ad.web.dto.AdCrowdFindInfo;
import com.hidata.ad.web.dto.AdCrowdFindInfoShowDto;
import com.hidata.ad.web.dto.GisCrowdDto;
import com.hidata.ad.web.dto.IntegerModelCrowdDto;
import com.hidata.ad.web.dto.IntrestLabelCrowdDto;
import com.hidata.ad.web.dto.KeyWordDirectDto;
import com.hidata.ad.web.dto.LatLngDto;
import com.hidata.ad.web.handler.CrowdNumSumScheduleJob;
import com.hidata.ad.web.model.Crowd;
import com.hidata.ad.web.model.CrowdKeyword;
import com.hidata.ad.web.model.CrowdPortrait;
import com.hidata.ad.web.model.CrowdRule;
import com.hidata.ad.web.model.CrowdUrl;
import com.hidata.ad.web.model.IntrestLabelTree;
import com.hidata.ad.web.model.KeywordOcean;
import com.hidata.ad.web.model.TreeNodeVO;
import com.hidata.ad.web.model.UrlOcean;
import com.hidata.ad.web.service.AdCrowdFindInfoADUService;
import com.hidata.ad.web.service.AdCrowdFindInfoService;
import com.hidata.ad.web.service.CrowdService;
import com.hidata.ad.web.service.IIntrestsCrowdService;
import com.hidata.ad.web.session.SessionContainer;
import com.hidata.ad.web.util.AdConstant;
import com.hidata.ad.web.util.GetLatAndLngByBaidu;
import com.hidata.framework.annotation.LoginRequired;
import com.hidata.framework.exception.AdException;

/**
 * 
 * @Description: 人群定制
 * @author chenjinzhao
 * @date 2013年12月27日 下午2:30:51
 * 
 */
@Controller
@RequestMapping("/crowd/*")
public class CrowdController {

	@Autowired
	private CrowdService crowdService;
	@Autowired
	private CrowdNumSumScheduleJob crowdNumSumScheduleJob;
	
	@Autowired
	private AdCrowdFindInfoService adCrowdFindInfoService;
	
	@Autowired
	private AdCrowdFindInfoADUService adCrowdFindInfoADUService ;
	
    @Autowired
    private IIntrestsCrowdService iIntrestsCrowdService;

	private static Logger logger = LoggerFactory
			.getLogger(CrowdController.class);

	@RequestMapping("crowdinitaddaction")
	public String crowdAdd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		
		String source = request.getParameter("source");
	
		if(StringUtils.isNotBlank(source) && "adplan".equals(source)){
			
			return "crowd/crowdAddForAdplan";
		}else{
			return "crowd/crowdAdd";
		}
		
	}
	
	/**
	 * 人群的保存
	 * @param request
	 * @param response
	 * @return
	 * @throws AdException
	 */
	@RequestMapping(value="crowdaddsaveaction",method=RequestMethod.POST)
	@LoginRequired
	public String crowdAddSave(HttpServletRequest request,
			HttpServletResponse response) throws AdException {

		try {
			String crowdName = request.getParameter("crowdName");
			String crowdDesc = request.getParameter("crowdDesc");

			String searchKwFromOcean = request
					.getParameter("searchKwFromOcean");
			String searchKwFormCust = request.getParameter("searchKwFormCust");

			String urlFromOcean = request.getParameter("urlFromOcean");
			String urlFromCust = request.getParameter("urlFromCust");

			String searchKwNum = request.getParameter("searchKwNum");
			String searchKwTimeNum = request.getParameter("searchKwTimeNum");
			String searchKwTimeType = request.getParameter("searchKwTimeType");

			String urlNum = request.getParameter("urlNum");
			String urlTimeNum = request.getParameter("urlTimeNum");
			String urlTimeType = request.getParameter("urlTimeType");

			String searchKwSwitch = request.getParameter("searchKwSwitch");
			String urlSwitch = request.getParameter("urlSwitch");

			crowdService.addCrowdConfig(crowdName, crowdDesc,
					searchKwFromOcean, searchKwFormCust, urlFromOcean,
					urlFromCust, searchKwNum, searchKwTimeNum,
					searchKwTimeType, urlNum, urlTimeType, urlTimeNum,
					searchKwSwitch, urlSwitch);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("受众人群添加失败！！" + e, e);
			throw new AdException("受众人群添加失败！！", e);
		}
		return "redirect:/crowd/crowdlistaction";
	}
	
	
	
	
	@RequestMapping(value="crowdaddfapsaveaction",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> crowdAddForAdPlanSave(HttpServletRequest request,
			HttpServletResponse response) throws AdException {
		Map<String,String> jsonRet = new HashMap<String, String>();
		try {
			String crowdName = request.getParameter("crowdName");
			String crowdDesc = request.getParameter("crowdDesc");

			String searchKwFromOcean = request
					.getParameter("searchKwFromOcean");
			String searchKwFormCust = request.getParameter("searchKwFormCust");

			String urlFromOcean = request.getParameter("urlFromOcean");
			String urlFromCust = request.getParameter("urlFromCust");

			String searchKwNum = request.getParameter("searchKwNum");
			String searchKwTimeNum = request.getParameter("searchKwTimeNum");
			String searchKwTimeType = request.getParameter("searchKwTimeType");

			String urlNum = request.getParameter("urlNum");
			String urlTimeNum = request.getParameter("urlTimeNum");
			String urlTimeType = request.getParameter("urlTimeType");

			String searchKwSwitch = request.getParameter("searchKwSwitch");
			String urlSwitch = request.getParameter("urlSwitch");

			int crowdId = crowdService.addCrowdConfig(crowdName, crowdDesc,
					searchKwFromOcean, searchKwFormCust, urlFromOcean,
					urlFromCust, searchKwNum, searchKwTimeNum,
					searchKwTimeType, urlNum, urlTimeType, urlTimeNum,
					searchKwSwitch, urlSwitch);
			
			jsonRet.put("crowdId", crowdId+"");
			jsonRet.put("crowdName", crowdName);
			
			return jsonRet;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ajax方式 受众人群添加失败！！" + e, e);
			throw new AdException("ajax方式  受众人群添加失败！！", e);
		}
	}

	@RequestMapping("crowdlistaction")
	@LoginRequired
	public String crowdList(HttpServletRequest request,
			HttpServletResponse response, Model model) throws AdException {
		return "crowd/crowdList";
	}
	
	@RequestMapping("initCrowdList")
	@LoginRequired
	public String initCrowdList(HttpServletRequest request,
			HttpServletResponse response, Model model) throws AdException {

		try {
			List<AdCrowdFindInfoShowDto> crowdList = adCrowdFindInfoService.getCrowInfoByUserId(SessionContainer.getSession().getUserId() + "");
			model.addAttribute("crowdList", crowdList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询受众人群失败 ！！" + e, e);
			throw new AdException("查询受众人群失败 ！！", e);
		}

		return "crowd/crowdListContent";
	}

	@RequestMapping("updateCrowStat")
	@LoginRequired
	@ResponseBody
	public Map<String, Boolean> updateCrowStat(HttpServletRequest request,
			HttpServletResponse response, Model model) throws AdException {
		Map<String, Boolean> result  = new HashMap<String, Boolean>();
		try {
			String stat = request.getParameter("stat");
			String ids = request.getParameter("ids");
			adCrowdFindInfoService.updateState(ids.split(","), stat);
			result.put("result", true);
		} catch (Exception e) {
			result.put("result", true);
			e.printStackTrace();
			logger.error("查询受众人群失败 ！！" + e, e);
			throw new AdException("查询受众人群失败 ！！", e);
		}

		return result;
	}
	
	@RequestMapping("crowdeditaction/{crowdId}")
	@LoginRequired
	public String crowdEdit(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@PathVariable String crowdId) throws AdException {

		try {
			// 人群基本信息
			Crowd crowdQry = new Crowd();
			crowdQry.setCrowdId(crowdId);
			Crowd crowdRet = crowdService.getOneCrowd(crowdQry);

			// 人群搜索关键词
			CrowdKeyword ckQry = new CrowdKeyword();
			ckQry.setCrowdId(crowdId);
			List<CrowdKeyword> ckList = crowdService.getCrowdKeywords(ckQry);

			// 人群url
			CrowdUrl cuQry = new CrowdUrl();
			cuQry.setCrowdId(crowdId);
			List<CrowdUrl> cuList = crowdService.getCrowdUrls(cuQry);

			// 人群规则
			CrowdRule crQry = new CrowdRule();
			crQry.setCrowdId(crowdId);
			CrowdRule crRet = crowdService.getCrowRule(crQry);

			String htmlKeywordsCust = "";
			if (null != ckList && ckList.size() > 0) {
				for (CrowdKeyword ck : ckList) {
					if (CrowdService.SEARCHKW_SOURCE_TYPE_CUST.equals(ck
							.getSourceType())) {
						htmlKeywordsCust += ck.getKeyword() + "\n";
					}
				}
			}

			String htmlUrlsCust = "";
			if (null != cuList && cuList.size() > 0) {
				for (CrowdUrl cu : cuList) {
					if (CrowdService.URL_SOURCE_TYPE_CUST.equals(cu
							.getSourceType())) {
						htmlUrlsCust += cu.getUrl() + "\n";
					}
				}
			}

			model.addAttribute("crowd", crowdRet);
			model.addAttribute("ckList", ckList);
			model.addAttribute("cuList", cuList);
			model.addAttribute("crowdRule", crRet);
			model.addAttribute("htmlKeywordsCust", htmlKeywordsCust);
			model.addAttribute("htmlUrlsCust", htmlUrlsCust);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("编辑受众人群失败 ！！" + e, e);
			throw new AdException("编辑受众人群失败 ！！", e);
		}

		return "/crowd/crowdEdit";

	}
	
	/**
	 * 人群的编辑
	 * @param request
	 * @param response
	 * @param model
	 * @param crowdId
	 * @return
	 * @throws AdException
	 */
	@RequestMapping(value="crowdeditsaveaction/{crowdId}",method=RequestMethod.POST)
	@LoginRequired
	public String crowdEditSave(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@PathVariable String crowdId) throws AdException {

		try {

			String crowdName = request.getParameter("crowdName");
			String crowdDesc = request.getParameter("crowdDesc");

			String searchKwFromOcean = request
					.getParameter("searchKwFromOcean");
			String searchKwFormCust = request.getParameter("searchKwFormCust");

			String urlFromOcean = request.getParameter("urlFromOcean");
			String urlFromCust = request.getParameter("urlFromCust");

			String searchKwNum = request.getParameter("searchKwNum");
			String searchKwTimeNum = request.getParameter("searchKwTimeNum");
			String searchKwTimeType = request.getParameter("searchKwTimeType");

			String urlNum = request.getParameter("urlNum");
			String urlTimeNum = request.getParameter("urlTimeNum");
			String urlTimeType = request.getParameter("urlTimeType");

			String searchKwSwitch = request.getParameter("searchKwSwitch");
			String urlSwitch = request.getParameter("urlSwitch");

			Crowd crowdEdit = new Crowd();
			crowdEdit.setCrowdId(crowdId);
			crowdEdit.setCrowdDesc(crowdDesc);
			crowdEdit.setCrowdName(crowdName);

			crowdService.editSaveCrowdConfig(crowdId, crowdName, crowdDesc,
					searchKwFromOcean, searchKwFormCust, urlFromOcean,
					urlFromCust, searchKwNum, searchKwTimeNum,
					searchKwTimeType, urlNum, urlTimeType, urlTimeNum,
					searchKwSwitch, urlSwitch);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("编辑受众人群保存失败 ！！" + e, e);
			throw new AdException("编辑受众人群保存失败 ！！", e);
		}
		return "redirect:/crowd/crowdlistaction";
	}

	@RequestMapping("crowdviewaction/{crowdId}")
	@LoginRequired
	public String crowdView(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@PathVariable String crowdId) throws AdException {

		try {
			// 人群基本信息
			Crowd crowdQry = new Crowd();
			crowdQry.setCrowdId(crowdId);
			Crowd crowdRet = crowdService.getOneCrowd(crowdQry);

			// 人群搜索关键词
			CrowdKeyword ckQry = new CrowdKeyword();
			ckQry.setCrowdId(crowdId);
			List<CrowdKeyword> ckList = crowdService.getCrowdKeywords(ckQry);

			// 人群url
			CrowdUrl cuQry = new CrowdUrl();
			cuQry.setCrowdId(crowdId);
			List<CrowdUrl> cuList = crowdService.getCrowdUrls(cuQry);

			// 人群规则
			CrowdRule crQry = new CrowdRule();
			crQry.setCrowdId(crowdId);
			CrowdRule crRet = crowdService.getCrowRule(crQry);

			String htmlKeywordsCust = "";
			if (null != ckList && ckList.size() > 0) {
				for (CrowdKeyword ck : ckList) {
					if (CrowdService.SEARCHKW_SOURCE_TYPE_CUST.equals(ck
							.getSourceType())) {
						htmlKeywordsCust += ck.getKeyword() + "\n";
					}
				}
			}

			String htmlUrlsCust = "";
			if (null != cuList && cuList.size() > 0) {
				for (CrowdUrl cu : cuList) {
					if (CrowdService.URL_SOURCE_TYPE_CUST.equals(cu
							.getSourceType())) {
						htmlUrlsCust += cu.getUrl() + "\n";
					}
				}
			}

			model.addAttribute("crowd", crowdRet);
			model.addAttribute("ckList", ckList);
			model.addAttribute("cuList", cuList);
			model.addAttribute("crowdRule", crRet);
			model.addAttribute("htmlKeywordsCust", htmlKeywordsCust);
			model.addAttribute("htmlUrlsCust", htmlUrlsCust);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查看一个受众人群失败 ！！" + e, e);
			throw new AdException("查看一个受众人群失败 ！！", e);
		}

		return "crowd/crowdView";
	}

	@RequestMapping("crowddelaction/{crowdId}")
	@LoginRequired
	public String crowdDel(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@PathVariable String crowdId) throws AdException {
		try {
			Crowd crowdDel = new Crowd();
			crowdDel.setCrowdId(crowdId);
			crowdService.delCrowd(crowdDel);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除受众人群失败 ！！" + e, e);
			throw new AdException("删除受众人群失败 ！！", e);
		}

		return "redirect:/crowd/crowdlistaction";
	}

	@RequestMapping("keywordoceanaction")
	@LoginRequired
	public String getKeywordOceanList(HttpServletRequest request,
			HttpServletResponse response, Model model) throws AdException {

		try {
			String kw = request.getParameter("searchKw");
			KeywordOcean ko = new KeywordOcean();
			ko.setKeyword(kw);
			List<KeywordOcean> kwoList = crowdService.getKeywordOceanList(ko);

			model.addAttribute("kwoList", kwoList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询关键词海洋失败！！" + e, e);
			throw new AdException("查询关键词海洋失败 ！！", e);
		}

		return "crowd/keywordOceanList";

	}

	@RequestMapping("urloceanaction")
	@LoginRequired
	public String getUrlOceanList(HttpServletRequest request,
			HttpServletResponse response, Model model) throws AdException {

		try{
		String searchUrlContent = request.getParameter("searchUrlContent");
		UrlOcean uo = new UrlOcean();
		uo.setContent(searchUrlContent);
		List<UrlOcean> uoList = crowdService.getUrlOceanList(uo);

		model.addAttribute("uoList", uoList);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询URL海洋失败！！" + e, e);
			throw new AdException("查询URL海洋失败 ！！", e);
		}
		return "crowd/urlOceanList";

	}
	
	/**
	 * 生成图表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/crowd/chart", produces="application/json", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> chart(HttpServletRequest request, HttpServletResponse response){
		
		// 默认选择七天的数据返回
		String crowdIds = request.getParameter("crowdIds");
		String userId = SessionContainer.getSession().getUserId() + "";
		Map<String,Object> map = adCrowdFindInfoService.getFindSum(crowdIds, userId);
		
		return map;
	}
	
	
	@RequestMapping("toAddCrowd")
	@LoginRequired
	public String toAddCrowd(HttpServletRequest request,
			HttpServletResponse response, Model model) throws AdException {
		
		return "crowd/add_person";
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
	@RequestMapping(value = "addCrowdNew",  produces="application/json")
	@ResponseBody
	@LoginRequired
	public Map<String, Boolean> addCrowd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			adCrowdFindInfoADUService.addAdCrowdManageDto(request);
			result.put("result", true);
		} catch (Exception e) {
			result.put("result", false);
			e.printStackTrace();
			logger.error("add adplan失败 ！！" + e, e);
			throw new AdException("add adplan失败 ！！", e);
		}
		return result;
	}
	
	/**
	 * 生成图表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/crowd/checkAddress", produces="application/json")
	@ResponseBody
	public Map<String, String> checkAddress(HttpServletRequest request, HttpServletResponse response){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		// 默认选择七天的数据返回
		String centryAddr = request.getParameter("centryAddr");
		try {
			centryAddr = new String(centryAddr.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		LatLngDto dto = null;
		try {
			dto = GetLatAndLngByBaidu.getCoordinate(centryAddr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String,String> map = new HashMap<String, String>();
		if(dto.getLat()==null || dto.getLng()==null) {
			map.put("stat", "false");
		} else {
			map.put("stat", "true");
		}
		
		return map;
	}
	 /**
     * 初始化兴趣标签树
     * 
     * @param request
     * @param response
     * @return
     * @author zhoubin
     */
    @RequestMapping(value = "/crowd/initInterestTree", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String initInterestTree(HttpServletRequest request, HttpServletResponse response) {

        IntrestLabelTree paraLabelTree = new IntrestLabelTree();

        paraLabelTree.setLabelValue(AdConstant.INTREST_LABEL_ALL);

        String crowdId = request.getParameter("crowdId");
        List<IntrestLabelTree> resultList = iIntrestsCrowdService
                .qryIntrestLabelChildrenByParent(paraLabelTree);

        List<TreeNodeVO> treeNodeChildList = new ArrayList<TreeNodeVO>();

        TreeNodeVO rootTreeNode = new TreeNodeVO();

        rootTreeNode.setId(AdConstant.INTREST_LABEL_ALL);

        rootTreeNode.setName(AdConstant.INTREST_LABEL_ROOT_NAME);

        for (IntrestLabelTree iterLabelTree : resultList) {

            TreeNodeVO treeNodeVO = new TreeNodeVO();

            treeNodeVO.setId(iterLabelTree.getLabelValue());

            treeNodeVO.setpId(iterLabelTree.getParentLabelValue());

            treeNodeVO.setLabelType(iterLabelTree.getLabelType());

            treeNodeVO.setName(iterLabelTree.getLabelName());
            treeNodeVO.setCrowdId(crowdId);
            treeNodeChildList.add(treeNodeVO);
        }

        rootTreeNode.setChildren(treeNodeChildList);

        JSONObject restJsonObj = JSONObject.fromObject(rootTreeNode);

        return restJsonObj.toString();
    }

    @RequestMapping(value = "/crowd/findTreeNextChild", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String findTreeNextChild(HttpServletRequest request, HttpServletResponse response) {

    	
        String parentLabelVal = request.getParameter("curLabel");

        parentLabelVal = request.getParameter("id");
        String crowdId = request.getParameter("crowdId");
        IntrestLabelTree paraLabelTree = new IntrestLabelTree();

        paraLabelTree.setLabelValue(parentLabelVal);

        List<IntrestLabelTree> resultList = iIntrestsCrowdService
                .qryIntrestLabelChildrenByParent(paraLabelTree);

        List<TreeNodeVO> treeNodeChildList = new ArrayList<TreeNodeVO>();
        Map<String, Boolean> selectTreeMap = null;
        if(!StringUtils.isEmpty(crowdId)){
        	selectTreeMap = new HashMap<String, Boolean>();
        	try {
				selectTreeMap = adCrowdFindInfoService.getSelectTreeMapByCrowdId(crowdId, selectTreeMap);

			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        for (IntrestLabelTree iterLabelTree : resultList) {

            TreeNodeVO treeNodeVO = new TreeNodeVO();

            treeNodeVO.setId(iterLabelTree.getLabelValue());

            treeNodeVO.setpId(iterLabelTree.getParentLabelValue());

            treeNodeVO.setLabelType(iterLabelTree.getLabelType());

            treeNodeVO.setName(iterLabelTree.getLabelName());
            treeNodeVO.setCrowdId(crowdId);
            if(selectTreeMap!=null){
            	  if(selectTreeMap.get(iterLabelTree.getLabelValue())!=null){
      				if(selectTreeMap.get(iterLabelTree.getLabelValue())){
      		            treeNodeVO.setHalfCheck(false);
      		            treeNodeVO.setChecked(true);
      				}else{
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
     * 手动更新统计结果
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/crowd/executeUpdateCrowdSum", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String executeUpdateCrowdSumByHand(HttpServletRequest request, HttpServletResponse response) {

        String date = request.getParameter("date");
        String exeResult = "";
        
        if(!StringUtils.isEmpty(date)) {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        	try {
        		sdf.parse(date);
        		 int result = crowdNumSumScheduleJob.executeUpdateCrowdSumByHand(date);
                 if(result > 0) {
                 	exeResult = "更新成功！";
                 } else {
                 	exeResult = "更新失败！";
                 }
        	} catch (Exception e) {
        		e.printStackTrace();
        		exeResult = "日期格式有误，日期格式如下：yyyyMMdd！";
        	}
        	
        } else {
        	exeResult = "请输入日期参数，示例：20140901！";
        }
       
        return exeResult;
    }
    
    @RequestMapping("/toEditCrowd/{crowdId}")
	@LoginRequired
	public String toEditCrowd(HttpServletRequest request,HttpServletResponse response, Model model,@PathVariable String crowdId) throws AdException {
    	
    	try {
    		AdCrowdFindInfo adCrowdFindInfo = adCrowdFindInfoService.getCrowdById(crowdId);
    		KeyWordDirectDto keyWordDirectDto = adCrowdFindInfoService.queryKeyWordDirectInfoByCrowdId(crowdId);
    	
    		IntegerModelCrowdDto integerModelCrowdDto = adCrowdFindInfoService.queryIntegerModelCrowdListByCrowdId(crowdId);
    		
    		List<IntrestLabelCrowdDto> intrestLabelList = adCrowdFindInfoService.queryIntrestLabelCrowdByCrowdId(crowdId);
    		GisCrowdDto gisCrowdDto = adCrowdFindInfoService.queryGisCrowdListByCrowdId(crowdId);
    		List<CrowdPortrait> crowdPortraitList = adCrowdFindInfoService.queryCrowdPortraitByCrowdId(crowdId);
        	model.addAttribute("crowd", adCrowdFindInfo);
        	model.addAttribute("keyWordDirectDto", keyWordDirectDto);
        	model.addAttribute("integerModelCrowdDto", integerModelCrowdDto);
        	if(intrestLabelList!=null&&intrestLabelList.size()>0){
        		model.addAttribute("intrestLabel", intrestLabelList.get(0));
        	}
        	model.addAttribute("intrestLabelList", intrestLabelList);
        	
        	model.addAttribute("gisCrowdDto", gisCrowdDto);
        	
        	model.addAttribute("crowdPortraitList", crowdPortraitList);
        	if(crowdPortraitList!=null&&crowdPortraitList.size()>0){
        		CrowdPortrait crowdPortrait = crowdPortraitList.get(0);
        		model.addAttribute("crowdPortrait", crowdPortrait);
        	}
        	
		} catch (Exception e) {
			e.printStackTrace();	
		}
		
    	return "crowd/edit_person";
	}
}
