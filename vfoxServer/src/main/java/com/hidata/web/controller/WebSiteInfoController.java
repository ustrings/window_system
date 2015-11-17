package com.hidata.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hidata.framework.util.StringUtil;
import com.hidata.web.dto.PageInfoDto;
import com.hidata.web.dto.WebSiteInfoDto;
import com.hidata.web.dto.WebSiteInfoShowDto;
import com.hidata.web.service.PageInfoService;
import com.hidata.web.service.PagerService;
import com.hidata.web.service.WebSiteInfoService;
import com.hidata.web.util.Constant;
import com.hidata.web.util.Pager;

/**
 * 页面信息处理
 * @author zhangtengda
 *
 */
@Controller
@RequestMapping("/website/*")
public class WebSiteInfoController {
	
	@Resource
	WebSiteInfoService webSiteInfoService;
	
	@Autowired
	PagerService pagerService;
	
	@Autowired
	PageInfoService pageInfoService;
	
	
	@RequestMapping(value="/initPageLevelAndPicSize", produces="application/json")
	@ResponseBody
	public Map<String, Map<String, String>> initPageLevelAndPicSize(HttpServletRequest request, HttpServletResponse response){
		Map<String, String> firstMap = new HashMap<String, String>();
		Map<String, String> secondMap = new HashMap<String, String>();
		Map<String, String> sizeMap = new HashMap<String, String>();
		
		List<PageInfoDto> firstLevels = null;
		List<PageInfoDto> secondLevels = null;
		firstLevels = pageInfoService.getFirstLevel();
		if (firstLevels.size() > 0 && firstLevels.get(0).getPageId() != null) {
			secondLevels = pageInfoService.getSecondLevelByParentId(firstLevels.get(0).getPageId());
		}
		if (firstLevels.size() > 0) {
			for (PageInfoDto pageInfoDto : firstLevels) {
				firstMap.put(pageInfoDto.getPageId(), pageInfoDto.getPageName());
			}
		}
		if (secondLevels.size() > 0) {
			for (PageInfoDto pageInfoDto : secondLevels) {
				secondMap.put(pageInfoDto.getPageId(), pageInfoDto.getPageName());
				sizeMap.put(pageInfoDto.getPageId(), pageInfoDto.getPageWidth() + "x" + pageInfoDto.getPageHeight());
			}
		} else {
			sizeMap.put(firstLevels.get(0).getPageId(), firstLevels.get(0).getPageWidth() + "x" + firstLevels.get(0).getPageHeight());
		}
		
		Map<String, Map<String, String>> resultMap = new HashMap<String, Map<String, String>>();
		resultMap.put("firstMap", firstMap);
		resultMap.put("secondMap", secondMap);
		resultMap.put("sizeMap", sizeMap);
		
		return resultMap;
	}
	
	@RequestMapping(value="/initSecondLevelAndPicSize", produces="application/json")
	@ResponseBody
	public Map<String, Map<String, String>> initSecondLevelAndPicSize(HttpServletRequest request, HttpServletResponse response){
		String pageConfigId = request.getParameter("pageConfigId");
		
		Map<String, String> secondMap = new HashMap<String, String>();
		Map<String, String> sizeMap = new HashMap<String, String>();
		
		List<PageInfoDto> secondLevels = null;

		secondLevels = pageInfoService.getSecondLevelByParentId(pageConfigId);
		
		if (secondLevels.size() > 0) {
			for (PageInfoDto pageInfoDto : secondLevels) {
				secondMap.put(pageInfoDto.getPageId(), pageInfoDto.getPageName());
				sizeMap.put(pageInfoDto.getPageId(), pageInfoDto.getPageWidth() + "x" + pageInfoDto.getPageHeight());
			}
		}
		
		Map<String, Map<String, String>> resultMap = new HashMap<String, Map<String, String>>();
		resultMap.put("secondMap", secondMap);
		resultMap.put("sizeMap", sizeMap);
		
		return resultMap;
	}
	
	@RequestMapping(value="/initSecondPageinfoLevel", produces="application/json")
	@ResponseBody
	public Map<String, Map<String, String>> initSecondLevel(HttpServletRequest request, HttpServletResponse response){
		String pageConfigId = request.getParameter("pageConfigId");
		
		Map<String, String> secondMap = new HashMap<String, String>();
		
		List<PageInfoDto> secondLevels = null;
		secondLevels = pageInfoService.getSecondLevelByParentId(pageConfigId);
		
		if (secondLevels.size() > 0) {
			for (PageInfoDto pageInfoDto : secondLevels) {
				secondMap.put(pageInfoDto.getPageId(), pageInfoDto.getPageName());
			}
		}
		
		Map<String, Map<String, String>> resultMap = new HashMap<String, Map<String, String>>();
		resultMap.put("secondMap", secondMap);
		
		return resultMap;
	}
	
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String websiteIndex(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		List<PageInfoDto> pageInfoDtos = pageInfoService.findAll("2");
		PageInfoDto first = new PageInfoDto();
		first.setPageId("all");
		first.setPageName("全部");
		pageInfoDtos.add(0, first);
		
		model.addAttribute("pageInfoDtos", pageInfoDtos);
		return "/website/website-list";
}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String pageList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		response.setCharacterEncoding("UTF-8"); 
		request.setCharacterEncoding("UTF-8");
		// {webSiteName : webSiteName_val, webSiteDesc :webSiteDesc_val, sts :sts_val, curPage :pageNo},
		// 获取页面 Id 和 pageName
		String pageConfigId = request.getParameter("pageConfigId");
		String secondPageConfigId = request.getParameter("secondPageConfigId");
		
		String webSiteName = request.getParameter("webSiteName");
		if (webSiteName != null && !webSiteName.equals("")) {
			webSiteName = new String(webSiteName.getBytes("iso8859-1"),"utf-8");
		}
		String webSiteDesc = request.getParameter("webSiteDesc");
		if (webSiteDesc != null && !webSiteDesc.equals("")) {
			webSiteDesc = new String(webSiteDesc.getBytes("iso8859-1"),"utf-8");
		}
		String sts = request.getParameter("sts");

		// 获取当前页数
		String curPage = request.getParameter("curPage");
		if(curPage==null || curPage.equals("")) {
			curPage = "1";
		}
		Map<Object, Object> params = new HashMap<Object, Object>();
		params.put("web_site_name", webSiteName);
		params.put("web_site_desc", webSiteDesc);
		params.put("sts", sts);
		
		StringBuffer sql = new StringBuffer(
				"SELECT website.id, website.web_site_name, website.sts, page.page_width, page.page_height, " +
				"page.page_name name1, page1.page_name name2, website.web_site_imge_url, website.web_site_desc,"+
				"website.priority" +
				" from page_config page,page_config page1, web_site_info website" +
				" WHERE" +
				" website.page_config_id=page.id" +
				" and" + 
				" website.page_config_id_two=page1.id ");
		for (Map.Entry<Object, Object> param : params.entrySet()) {
			if (param.getKey() != null && !param.getKey().equals("")) {
				if(param.getValue() != null && !param.getValue().equals("")) {
					sql.append(" and website.").append(param.getKey()).append(" like '%").append(param.getValue()).append("%'");
				}
			}
		}
		
		// 获取页面 Id 和 pageName
		if(!StringUtils.isEmpty(pageConfigId)&&!pageConfigId.equals("all")) {
			sql.append(" and website.page_config_id=" + pageConfigId);
		}
		
		if(!StringUtils.isEmpty(secondPageConfigId) && !secondPageConfigId.equals("null") && !secondPageConfigId.equals("all")) {
			sql.append(" and website.page_config_id_two=" + secondPageConfigId);
		} else if(!StringUtils.isEmpty(secondPageConfigId) && !secondPageConfigId.equals("null") && secondPageConfigId.equals("all")) {
			// 获取所有下级的分类 id
			List<PageInfoDto> secondLevels = null;
			secondLevels = pageInfoService.getSecondLevelByParentId(pageConfigId);
			if (secondLevels.size() > 0) {
				StringBuffer sbPageConfigIds = new StringBuffer();
				sbPageConfigIds.append(" and website.page_config_id_two in (");
				for (PageInfoDto pageInfoDto : secondLevels) {
					sbPageConfigIds.append(pageInfoDto.getPageId()).append(",");
				}
				sbPageConfigIds.deleteCharAt(sbPageConfigIds.length()-1);
				sbPageConfigIds.append(")");
				sql.append(sbPageConfigIds.toString());
			}
		}
		
		Pager pager = pagerService.getPagerBySqlForBeanPropertyRowMapper(sql.toString(), Integer.valueOf(curPage), 10, WebSiteInfoShowDto.class);
		model.addAttribute("pager", pager);

		return "/website/website-list-content";
	}
	
	
	@RequestMapping(value="/toAdd", method=RequestMethod.GET)
	public String toAddWebsite(HttpServletRequest request, HttpServletResponse response, Model model){
		model.addAttribute("jsession", request.getSession().getId());
		return "/website/website-add";
	}
	
	@RequestMapping(value="/add", produces="application/json")
	@ResponseBody
	public Map<String, Boolean> addWebsite(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		String webSiteName = request.getParameter("webSiteName");
		if (webSiteName != null && !webSiteName.equals("")) {
			webSiteName = new String(webSiteName.getBytes("iso8859-1"),"utf-8");
		}
		String host= request.getParameter("host");
		if (host != null && !host.equals("")) {
			host = new String(host.getBytes("iso8859-1"),"utf-8");
		}
		String webSiteDesc = request.getParameter("webSiteDesc");
		if (webSiteDesc != null && !webSiteDesc.equals("")) {
			webSiteDesc = new String(webSiteDesc.getBytes("iso8859-1"),"utf-8");
		}
		String webSiteName2 = request.getParameter("webSiteName2");
		if (webSiteName2 != null && !webSiteName2.equals("")) {
			webSiteName2 = new String(webSiteName2.getBytes("iso8859-1"),"utf-8");
		}
		String host2= request.getParameter("host2");
		if (host2 != null && !host2.equals("")) {
			host2 = new String(host2.getBytes("iso8859-1"),"utf-8");
		}
//		String sts = request.getParameter("sts");
		String pageConfigId = request.getParameter("pageConfigId");
		String pageConfigIdTwo = request.getParameter("pageConfigIdTwo");
		String webSiteImgeUrl = request.getParameter("webSiteImgeUrl");
		String priority = request.getParameter("priority");
		
		WebSiteInfoDto webSiteInfoDto = new WebSiteInfoDto();
		webSiteInfoDto.setWebSiteName(webSiteName);
		webSiteInfoDto.setHost(host);
		webSiteInfoDto.setWebSiteDesc(webSiteDesc);
		webSiteInfoDto.setWebSiteName2(webSiteName2);
		webSiteInfoDto.setHost2(host2);
		webSiteInfoDto.setSts(Constant.AD_STS_N);
		webSiteInfoDto.setPageConfigId(pageConfigId);
		webSiteInfoDto.setPageConfigIdTwo(pageConfigIdTwo);
		webSiteInfoDto.setWebSiteImgeUrl(webSiteImgeUrl);
		webSiteInfoDto.setPriority(priority);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String createTime = sdf.format(new Date());
		webSiteInfoDto.setCreateTime(createTime);
		webSiteInfoDto.setUpdateTime(createTime);
		webSiteInfoDto.setPublishTime(createTime);
		
		int result  = webSiteInfoService.insertPageInfo(webSiteInfoDto);
		if (result > 0) {
			map.put("result", true);
		} else {
			map.put("result", false);
		}
		
		return map;
	}
	
	
	@RequestMapping(value="/getParentLevel", produces="application/json")
	@ResponseBody
	public Map<String, String> getParentLevel(HttpServletRequest request, HttpServletResponse response){
		Map<String, String> map = new HashMap<String, String>();
		// 
		String level = request.getParameter("level");
		String type = request.getParameter("type");
		StringBuffer sql = new  StringBuffer();
		sql.append("select * from page_config where 1=1");
		if(!StringUtil.isEmpty(level)) {
			sql.append(" and level =" + level);
		}
		if(!StringUtil.isEmpty(type)) {
			sql.append(" and type=" + type);
		}
//		List<WebSiteInfoDto> pageInfoDtos = webSiteInfoService.findBySql(sql.toString());
//		
//		if (pageInfoDtos.size() >= 0) {
//			for (WebSiteInfoDto webSiteInfoDto : pageInfoDtos) {
//				map.put(webSiteInfoDto.getPageId(), webSiteInfoDto.getPageName());
//			}
//		}
		
		return map;
	}
	

	
	@RequestMapping(value="/toUpdate/{Id}", method=RequestMethod.GET)
	public String toUpdateWebsite(HttpServletRequest request, HttpServletResponse response, @PathVariable String Id, Model model){
		String sql = "select * from web_site_info where id=" + Id;
		// 获取 对应的数据 webSiteInfoDto
		WebSiteInfoDto webSiteInfoDto = webSiteInfoService.findBySql(sql, WebSiteInfoDto.class).get(0);
		// 获取一级分类
		sql = "select * from page_config where level = 1";
		List<PageInfoDto> firstPageInfoDtos = webSiteInfoService.findBySql(sql, PageInfoDto.class);
		// 获取二级分类
		sql = "SELECT * from page_config where parent_config_id = (select parent_config_id from page_config where id=" + webSiteInfoDto.getPageConfigIdTwo() +")";
		List<PageInfoDto> secondPageInfoDtos = webSiteInfoService.findBySql(sql, PageInfoDto.class);
		
		model.addAttribute("webSiteInfoDto", webSiteInfoDto);
		model.addAttribute("firstPageInfoDtos", firstPageInfoDtos);
		model.addAttribute("secondPageInfoDtos", secondPageInfoDtos);
		model.addAttribute("jsession", request.getSession().getId());
		// 获取上级分类
//		
//		if (!webSiteInfoDto.getParentConfigId().equals("0") && !webSiteInfoDto.getLevel().equals("1")) {
//			StringBuffer sql1 = new  StringBuffer();
//			sql1.append("select * from page_config where type=" + webSiteInfoDto.getType());
//			sql1.append(" and level=" + (Integer.valueOf(webSiteInfoDto.getLevel())-1));
//			List<WebSiteInfoDto> parents = webSiteInfoService.findBySql(sql1.toString());
//			model.addAttribute("parentLevels", parents);
//		}
		
		return "/website/website-edit";
	}
	
	@RequestMapping(value="/update", produces="application/json")
	@ResponseBody
	public Map<String, Boolean> updateWebSite(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		/*
		 * pageId :pageId_val,
			webSiteName : webSiteName_val, host :host_val, webSiteDesc :webSiteDesc_val, webSiteName2 :webSiteName2_val, 
			host2 :host2_val, sts :sts_val, pageConfigId :pageConfigId_val, pageConfigIdTwo :pageConfigIdTwo_val, 
			webSiteImgeUrl :webSiteImgeUrl_val, priority :priority_val
		 */
		String id = request.getParameter("id");
		String webSiteName = request.getParameter("webSiteName");
		if (webSiteName != null && !webSiteName.equals("")) {
			webSiteName = new String(webSiteName.getBytes("iso8859-1"),"utf-8");
		}
		String host = request.getParameter("host");
		if (host != null && !host.equals("")) {
			host = new String(host.getBytes("iso8859-1"),"utf-8");
		}
		String webSiteDesc = request.getParameter("webSiteDesc");
		if (webSiteDesc != null && !webSiteDesc.equals("")) {
			webSiteDesc = new String(webSiteDesc.getBytes("iso8859-1"),"utf-8");
		}
		String webSiteName2 = request.getParameter("webSiteName2");
		if (webSiteName2 != null && !webSiteName2.equals("")) {
			webSiteName2 = new String(webSiteName2.getBytes("iso8859-1"),"utf-8");
		}
		String host2 = request.getParameter("host2");
		if (host2 != null && !host2.equals("")) {
			host2 = new String(host2.getBytes("iso8859-1"),"utf-8");
		}
		String sts = request.getParameter("sts");
		String pageConfigId = request.getParameter("pageConfigId");
		String pageConfigIdTwo = request.getParameter("pageConfigIdTwo");
		String webSiteImgeUrl = request.getParameter("webSiteImgeUrl");
		String priority = request.getParameter("priority");
		
		WebSiteInfoDto webSiteInfoDto = new WebSiteInfoDto();
		/*
		 * String UPDATE_SQL = "update into web_site_info set"
			+ " page_config_id=?, web_site_name=?, page_config_id_two=?, web_site_imge_url=?, priority=?, "
			+ " web_site_desc=?, update_time=?, sts=?, host=?, web_site_name2=?, host2=?"
			+ " where id=?";
		 */
		webSiteInfoDto.setId(id);
		webSiteInfoDto.setPageConfigId(pageConfigId);
		webSiteInfoDto.setWebSiteName(webSiteName);
		webSiteInfoDto.setPageConfigIdTwo(pageConfigIdTwo);
		webSiteInfoDto.setWebSiteImgeUrl(webSiteImgeUrl);
		webSiteInfoDto.setPriority(priority);
		webSiteInfoDto.setWebSiteDesc(webSiteDesc);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String updateTime = sdf.format(new Date());
		webSiteInfoDto.setUpdateTime(updateTime);
		
		webSiteInfoDto.setSts(sts);
		webSiteInfoDto.setHost(host);
		webSiteInfoDto.setWebSiteName2(webSiteName2);
		webSiteInfoDto.setHost2(host2);
		
		int result  = webSiteInfoService.updatePageInfo(webSiteInfoDto);
		
		if (result > 0) {
			map.put("result", true);
		} else {
			map.put("result", false);
		}
		
		return map;
	}
	
	@RequestMapping(value="/delete", produces="application/json")
	@ResponseBody
	public Map<String, Boolean> deleteWebsite(HttpServletRequest request, HttpServletResponse response){
		Map<String, Boolean> map = new HashMap<String, Boolean>();
				
		String pageId = request.getParameter("pageId");
		
		WebSiteInfoDto webSiteInfoDto = new WebSiteInfoDto();
		webSiteInfoDto.setId(pageId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String updateTime = sdf.format(new Date());
		webSiteInfoDto.setUpdateTime(updateTime);
		webSiteInfoDto.setSts(Constant.AD_STS_D);
		
		int result  = webSiteInfoService.changePageInfoSts(webSiteInfoDto);
		if (result > 0) {
			map.put("result", true);
		} else {
			map.put("result", false);
		}
		
		return map;
	}
	
	@RequestMapping(value="/enableSts", produces="application/json")
	@ResponseBody
	public Map<String, Boolean> enableWebsite(HttpServletRequest request, HttpServletResponse response){
		Map<String, Boolean> map = new HashMap<String, Boolean>();
				
		String pageId = request.getParameter("pageId");
		
		WebSiteInfoDto webSiteInfoDto = new WebSiteInfoDto();
		webSiteInfoDto.setId(pageId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String updateTime = sdf.format(new Date());
		webSiteInfoDto.setUpdateTime(updateTime);
		// 启用网站
		webSiteInfoDto.setSts(Constant.AD_STS_A);
		
		int result  = webSiteInfoService.changePageInfoSts(webSiteInfoDto);
		if (result > 0) {
			map.put("result", true);
		} else {
			map.put("result", false);
		}
		
		return map;
	}
	
}
