package com.hidata.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hidata.web.dto.AdInfoDto;
import com.hidata.web.dto.PageInfoDto;
import com.hidata.web.service.AdInfoService;
import com.hidata.web.service.PageInfoService;
import com.hidata.web.service.PagerService;
import com.hidata.web.util.Constant;
import com.hidata.web.util.Pager;

/**
 * 页面信息处理
 * @author zhangtengda
 *
 */
@Controller
@RequestMapping("/ad/*")
public class AdInfoController {
	
	@Autowired
	PageInfoService pageInfoService;
	
	@Autowired
	PagerService pagerService;
	
	@Autowired
	AdInfoService adInfoService;
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String adIndex(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
			List<PageInfoDto> pageInfoDtos = pageInfoService.findAll("1");
			PageInfoDto first = new PageInfoDto();
			first.setPageId("all");
			first.setPageName("全部");
			pageInfoDtos.add(0, first);
			
			model.addAttribute("pageInfoDtos", pageInfoDtos);
			return "/ad/ad-list";
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String pageList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		 		response.setCharacterEncoding("UTF-8"); 
				request.setCharacterEncoding("UTF-8");
				// 获取页面 Id 和 pageName
//				data:{pageConfigId :pageConfigId_val, secondPageConfigId :secondPageConfigId_val,  sts:sts_val, curPage:pageNo},
				
				String pageConfigId = request.getParameter("pageConfigId");
				String secondPageConfigId = request.getParameter("secondPageConfigId");
				String sts = request.getParameter("sts");
				// 获取当前页数
				String curPage = request.getParameter("curPage");
				if(curPage==null || curPage.equals("")) {
					curPage = "1";
				}
				StringBuffer sbSql = new StringBuffer();
				sbSql.append("select * from page_config_ad_rel where 1=1 ");
				if(!pageConfigId.equals("all") && secondPageConfigId.equals("all")) {
					// 获取所有下级的分类 id
					List<PageInfoDto> secondLevels = null;
					secondLevels = pageInfoService.getSecondLevelByParentId(pageConfigId);
					if (secondLevels.size() > 0) {
						StringBuffer sbPageConfigIds = new StringBuffer();
						sbPageConfigIds.append(" and page_config_id in (");
						for (PageInfoDto pageInfoDto : secondLevels) {
							sbPageConfigIds.append(pageInfoDto.getPageId()).append(",");
						}
						sbPageConfigIds.deleteCharAt(sbPageConfigIds.length()-1);
						sbPageConfigIds.append(")");
						sbSql.append(sbPageConfigIds.toString());
					} else {
						sbSql.append(" and page_config_id=" + pageConfigId);
					}
					
					
				} else if(!pageConfigId.equals("all") && secondPageConfigId !=null && !secondPageConfigId.equals("all")) {
					sbSql.append(" and page_config_id=" + secondPageConfigId);
				}
				sbSql.append(" and sts='" + sts + "'");
				
				Pager pager = pagerService.getPagerBySql(sbSql.toString(), Integer.valueOf(curPage), 10, AdInfoDto.class);
				model.addAttribute("pager", pager);
				
				return "/ad/ad-list-content";
	}
	
	
	@RequestMapping(value="/toAdd", method=RequestMethod.GET)
	public String toAddAd(HttpServletRequest request, HttpServletResponse response, Model model){
		List<PageInfoDto> pageInfoDtos = pageInfoService.findAll("1");
		model.addAttribute("pageInfoDtos", pageInfoDtos);
		return "/ad/ad-add";
	}
	
	@RequestMapping(value="/add", produces="application/json")
	@ResponseBody
	public Map<String, Boolean> addAd(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		
		//data:{title :title_val, pageConfigId :pageConfigId_val, sts :sts_val, url :url_val, targeturl :targeturl_val},
		
		String title = request.getParameter("title");
		title = new String(title.getBytes("iso-8859-1"), "utf-8");
		String pageConfigId = request.getParameter("pageConfigId");
		//String sts = request.getParameter("sts");
		String url = request.getParameter("url");
		String targeturl = request.getParameter("targeturl");
		
		AdInfoDto adInfoDto = new AdInfoDto();
		adInfoDto.setTitle(title);
		adInfoDto.setPageConfigId(pageConfigId);
		adInfoDto.setSts(Constant.AD_STS_N);
		adInfoDto.setUrl(url);
		adInfoDto.setTargeturl(targeturl);
		
		int result  = adInfoService.insertAdInfo(adInfoDto);
		if (result > 0) {
			map.put("result", true);
		} else {
			map.put("result", false);
		}
		
		return map;
	}
	
	@RequestMapping(value="/toUpdate/{Id}", method=RequestMethod.GET)
	public String toUpdateAd(HttpServletRequest request, HttpServletResponse response, @PathVariable String Id, Model model){
		// 设置标题为新增
		StringBuffer sql = new  StringBuffer();
		sql.append("select * from page_config_ad_rel where id=" + Id);
		AdInfoDto adInfoDto = adInfoService.findBySql(sql.toString()).get(0);
		model.addAttribute("adInfoDto", adInfoDto);
		// 页面位置集合
		List<PageInfoDto> pageInfoDtos = pageInfoService.findAll("1");
		model.addAttribute("pageInfoDtos", pageInfoDtos);
		
		return "/ad/ad-edit";
	}
	
	@RequestMapping(value="/update", produces="application/json")
	@ResponseBody
	public Map<String, Boolean> updateAd(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Boolean> map = new HashMap<String, Boolean>();
				
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		title = new String(title.getBytes("iso-8859-1"), "utf-8");
		String pageConfigId = request.getParameter("pageConfigId");
		String sts = request.getParameter("sts");
		String url = request.getParameter("url");
		String targeturl = request.getParameter("targeturl");
		
		AdInfoDto adInfoDto = new AdInfoDto();
		adInfoDto.setId(id);
		adInfoDto.setTitle(title);
		adInfoDto.setPageConfigId(pageConfigId);
		adInfoDto.setSts(sts);
		adInfoDto.setUrl(url);
		adInfoDto.setTargeturl(targeturl);
		
		int result  = adInfoService.updateAdInfo(adInfoDto);
		if (result > 0) {
			map.put("result", true);
		} else {
			map.put("result", false);
		}
		
		return map;
	}
	
	@RequestMapping(value="/delete", produces="application/json")
	@ResponseBody
	public Map<String, Boolean> deleteAd(HttpServletRequest request, HttpServletResponse response){
		Map<String, Boolean> map = new HashMap<String, Boolean>();
				
		String id = request.getParameter("id");
		
		AdInfoDto adInfoDto = new AdInfoDto();
		adInfoDto.setId(id);
		adInfoDto.setSts(Constant.AD_STS_D);
		
		int result  = adInfoService.changeAdInfoSts(adInfoDto);
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
		
		AdInfoDto adInfoDto = new AdInfoDto();
		adInfoDto.setId(pageId);
		// 启用网站
		adInfoDto.setSts(Constant.AD_STS_A);
		
		int result  = adInfoService.changeAdInfoSts(adInfoDto);
		if (result > 0) {
			map.put("result", true);
		} else {
			map.put("result", false);
		}
		
		return map;
	}
	
}
