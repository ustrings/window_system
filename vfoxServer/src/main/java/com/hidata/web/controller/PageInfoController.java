package com.hidata.web.controller;

import java.awt.Paint;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.hidata.framework.util.StringUtil;
import com.hidata.web.dto.PageInfoDto;
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
@RequestMapping("/pageInfo/*")
public class PageInfoController {
	
	@Autowired
	PageInfoService pageInfoService;
	
	@Autowired
	PagerService pagerService;
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String pageIndex() {
		return "/page/page-list";
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String pageList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		 		response.setCharacterEncoding("UTF-8"); 
					request.setCharacterEncoding("UTF-8");
				// 获取分页类型
				String pagerSearchType = request.getParameter("pagerSearchType");
				
				// 获取页面 Id 和 pageName
				String pageId = request.getParameter("pageId");
				String pageName = request.getParameter("pageName");
				pageName = new String(pageName.getBytes("iso8859-1"),"utf-8");
				String sts = request.getParameter("sts");
				// 获取当前页数
				String curPage = request.getParameter("curPage");
				if(curPage==null || curPage.equals("")) {
					curPage = "1";
				}
				Map<Object, Object> params = new HashMap<Object, Object>();
				params.put("id", pageId);
				params.put("page_name", pageName);
				params.put("sts", sts);
				
				StringBuffer sbSql = new StringBuffer();
				sbSql.append("SELECT page1.id, page1.page_name, page1.page_width, page1.page_height, page1.jsp_path,page1.sts,page1.create_time,page1.update_time," +
						" page1.publish_time, page1.site_type_code, ifnull(page2.page_name,'无') as parent_config_id,page1.type, page1.level " +
						 " from page_config page1 left join page_config page2 ");
				sbSql.append(" on page1.parent_config_id = page2.id where 1=1 ");
				// 
				Pager pager = null;
				// 如果是查询分页就使用如下方式
				if (pagerSearchType.equals("1")) {
					// 使用联合查询的方式
					if(pageId != null && !pageId.equals("")) {
						sbSql.append(" and page1.id = " + pageId);
					}
					if(pageName != null && !pageName.equals("")) {
						sbSql.append(" and page1.page_name like '%" + pageName + "%'");
					}
					if(sts != null && !sts.equals("")) {
						sbSql.append(" and page1.sts = '" + sts + "'");
					}
					
					// 如果是父类查询
					pager = pagerService.getPagerBySql(sbSql.toString(), Integer.valueOf(curPage), 10, PageInfoDto.class);
				} else if (pagerSearchType.equals("2")) {
					// 如果是子类查询
					sbSql.append(" and page1.parent_config_id=" + pageId);
					pager = pagerService.getPagerBySql(sbSql.toString(), Integer.valueOf(curPage), 10, PageInfoDto.class);
				}
				
				model.addAttribute("pager", pager);
				
				return "/page/page-list-content";
	}
	
	
	
	
	@RequestMapping(value="/toAdd", method=RequestMethod.GET)
	public String toAddPage(HttpServletRequest request, HttpServletResponse response, Model model){
		return "/page/page-add";
	}
	
	@RequestMapping(value="/add", produces="application/json")
	@ResponseBody
	public Map<String, Boolean> addPage(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Boolean> map = new HashMap<String, Boolean>();
				
		String pageName = request.getParameter("pageName");
		if (pageName != null && !pageName.equals("")) {
			pageName = new String(pageName.getBytes("iso8859-1"),"utf-8");
		}
		String pageWidth = request.getParameter("pageWidth");
		String pageHeight = request.getParameter("pageHeight");
//		String sts = request.getParameter("sts");
		String type = request.getParameter("type");
		String level = request.getParameter("level");
		String parentConfigId = request.getParameter("parentConfigId");
		
		PageInfoDto pageInfoDto = new PageInfoDto();
		pageInfoDto.setPageName(pageName);
		pageInfoDto.setPageWidth(pageWidth);
		pageInfoDto.setPageHeight(pageHeight);
		pageInfoDto.setSts(Constant.AD_STS_N);
		pageInfoDto.setType(type);
		pageInfoDto.setLevel(level);
		pageInfoDto.setParentConfigId(parentConfigId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String createTime = sdf.format(new Date());
		pageInfoDto.setCreateTime(createTime);
		pageInfoDto.setUpdateTime(createTime);
		pageInfoDto.setPublishTime(createTime);
		
		int result  = pageInfoService.insertPageInfo(pageInfoDto);
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
		List<PageInfoDto> pageInfoDtos = pageInfoService.findBySql(sql.toString());
		
		if (pageInfoDtos.size() >= 0) {
			for (PageInfoDto pageInfoDto : pageInfoDtos) {
				map.put(pageInfoDto.getPageId(), pageInfoDto.getPageName());
			}
		}
		
		return map;
	}
	

	
	@RequestMapping(value="/toUpdate/{Id}", method=RequestMethod.GET)
	public String toUpdatePage(HttpServletRequest request, HttpServletResponse response, @PathVariable String Id, Model model){
		// 设置标题为新增
		StringBuffer sql = new  StringBuffer();
		sql.append("select * from page_config where id=" + Id);
		PageInfoDto pageInfoDto = pageInfoService.findBySql(sql.toString()).get(0);
		model.addAttribute("pageInfoDto", pageInfoDto);
		// 获取上级分类
		
		if (!pageInfoDto.getParentConfigId().equals("0") && !pageInfoDto.getLevel().equals("1")) {
			StringBuffer sql1 = new  StringBuffer();
			sql1.append("select * from page_config where type=" + pageInfoDto.getType());
			sql1.append(" and level=" + (Integer.valueOf(pageInfoDto.getLevel())-1));
			List<PageInfoDto> parents = pageInfoService.findBySql(sql1.toString());
			model.addAttribute("parentLevels", parents);
		}
		
		return "/page/page-edit";
	}
	
	@RequestMapping(value="/update", produces="application/json")
	@ResponseBody
	public Map<String, Boolean> updatePage(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Boolean> map = new HashMap<String, Boolean>();
				
		String pageId = request.getParameter("pageId");
		String pageName = request.getParameter("pageName");
		if (pageName != null && !pageName.equals("")) {
			pageName = new String(pageName.getBytes("iso8859-1"),"utf-8");
		}
		String pageWidth = request.getParameter("pageWidth");
		String pageHeight = request.getParameter("pageHeight");
		String sts = request.getParameter("sts");
		String type = request.getParameter("type");
		String level = request.getParameter("level");
		String parentConfigId = request.getParameter("parentConfigId");
		
		PageInfoDto pageInfoDto = new PageInfoDto();
		pageInfoDto.setPageId(pageId);
		pageInfoDto.setPageName(pageName);
		pageInfoDto.setPageWidth(pageWidth);
		pageInfoDto.setPageHeight(pageHeight);
		pageInfoDto.setSts(sts);
		pageInfoDto.setType(type);
		pageInfoDto.setLevel(level);
		pageInfoDto.setParentConfigId(parentConfigId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String updateTime = sdf.format(new Date());
		pageInfoDto.setUpdateTime(updateTime);
		
		int result  = pageInfoService.updatePageInfo(pageInfoDto);
		if (result > 0) {
			map.put("result", true);
		} else {
			map.put("result", false);
		}
		
		return map;
	}
	
	
	@RequestMapping(value="/enableSts", produces="application/json")
	@ResponseBody
	public Map<String, Boolean> enableSts(HttpServletRequest request, HttpServletResponse response){
		Map<String, Boolean> map = new HashMap<String, Boolean>();
				
		String pageId = request.getParameter("pageId");
		
		PageInfoDto pageInfoDto = new PageInfoDto();
		pageInfoDto.setPageId(pageId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String updateTime = sdf.format(new Date());
		pageInfoDto.setUpdateTime(updateTime);
		pageInfoDto.setSts(Constant.AD_STS_A);
		
		int result  = pageInfoService.changePageInfoSts(pageInfoDto);
		if (result > 0) {
			map.put("result", true);
		} else {
			map.put("result", false);
		}
		
		return map;
	}
	
	@RequestMapping(value="/delete", produces="application/json")
	@ResponseBody
	public Map<String, Boolean> deletePage(HttpServletRequest request, HttpServletResponse response){
		Map<String, Boolean> map = new HashMap<String, Boolean>();
				
		String pageId = request.getParameter("pageId");
		
		PageInfoDto pageInfoDto = new PageInfoDto();
		pageInfoDto.setPageId(pageId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String updateTime = sdf.format(new Date());
		pageInfoDto.setUpdateTime(updateTime);
		pageInfoDto.setSts(Constant.AD_STS_D);
		
		int result  = pageInfoService.changePageInfoSts(pageInfoDto);
		if (result > 0) {
			map.put("result", true);
		} else {
			map.put("result", false);
		}
		
		return map;
	}
	
}
