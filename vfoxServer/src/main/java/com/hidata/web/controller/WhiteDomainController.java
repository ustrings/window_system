package com.hidata.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hidata.framework.util.StringUtil;
import com.hidata.web.dto.WhiteDomainDto;
import com.hidata.web.service.WhiteDomainService;
import com.hidata.web.util.Pager;

/**
 * 投放域名白名单的Controller
 * @author xiaoming
 * @date 2014-12-31
 */
@RequestMapping("/domain/*")
@Controller
public class WhiteDomainController {
	
	private static Logger logger = LoggerFactory.getLogger(WhiteDomainController.class);
	
	@Autowired
	private WhiteDomainService whiteDomainService;
	
	/**
	 * 初始化页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="init", method=RequestMethod.GET)
	public String initIndex(HttpServletRequest request, HttpServletResponse response, Model model){
		try {
			
		} catch (Exception e) {
			logger.error("WhiteDomainController initIndex error",e);
		}
		return "/domain/whiteList";
	}
	
	
	/**
	 * 获取表格内容
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="list", method=RequestMethod.POST)
	public String getList(HttpServletRequest request, HttpServletResponse response, Model model){
		
		String domainName = request.getParameter("domainName");
		String domainUrl = request.getParameter("domainUrl");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		
		String curPage = request.getParameter("curPage");
		if(StringUtil.isEmpty(curPage)){
			curPage = "1";
		}
		
		try {
			Map<String,String> map = new HashMap<String,String>();
			
			map.put("domainName", domainName);
			map.put("domainUrl", domainUrl);
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			
			Pager pager = whiteDomainService.getPager(map,curPage);
			
			model.addAttribute("pager", pager);
		} catch (Exception e) {
			logger.error("WhiteDomainController  getList error",e);
			e.printStackTrace();
		}
		return "/domain/whiteList_list";
	}
	
	/**
	 * 新增
	 * @param request
	 * @param response
	 * @param whiteDomain
	 * @return
	 */
	@RequestMapping(value="add",method=RequestMethod.POST)
	public String addWhiteDomain(HttpServletRequest request, HttpServletResponse response, WhiteDomainDto whiteDomain){
		try {
			whiteDomainService.addWhiteDomain(whiteDomain);
		} catch (Exception e) {
			logger.error("WhiteDomainController  addWhiteDomain error",e);
			e.printStackTrace();
		}
		return "redirect:/domain/init?navigation=ggtfgl_1";
	}
	
	/**
	 * 初始化编辑页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="editInit", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String, WhiteDomainDto> editInit(HttpServletRequest request, HttpServletResponse response, Model model){
		try {
			String domainId = request.getParameter("domainId");
			Map<String, WhiteDomainDto> map = new HashMap<String, WhiteDomainDto>();
			if(StringUtil.isNotEmpty(domainId)){
				WhiteDomainDto whiteDomain = whiteDomainService.getWhiteDomainById(domainId);
				map.put("whiteDomain", whiteDomain);
			}
			return map;
		} catch (Exception e) {
			logger.error("WhiteDomainController  editInit error",e);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 编辑
	 * @param request
	 * @param response
	 * @param whiteDomain
	 * @return
	 */
	@RequestMapping(value="edit",method=RequestMethod.POST)
	public String edit(HttpServletRequest request, HttpServletResponse response, WhiteDomainDto whiteDomain){
		try {
			if(whiteDomain != null){
				whiteDomainService.edit(whiteDomain);
			}
		} catch (Exception e) {
			logger.error("WhiteDomainController  edit error",e);
			e.printStackTrace();
		}
		return "redirect:/domain/init?navigation=ggtfgl_1";
	}
	
	/**
	 * 删除
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.GET)
	public String delete(HttpServletResponse response, HttpServletRequest request, Model model){
		String domainId = request.getParameter("domainId");
		if(StringUtil.isNotEmpty(domainId)){
			whiteDomainService.delete(domainId);
		}
		return "redirect:/domain/init?navigation=ggtfgl_1";
	}
	
	/**
	 * 批量删除
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/deleteAll",produces="application/json")
	@ResponseBody
	public String deleteAll(HttpServletRequest request, HttpServletResponse response, Model model){
		String domainIds = request.getParameter("domainIds");
		if(StringUtil.isNotEmpty(domainIds)){
			Boolean flag = whiteDomainService.deleteAll(domainIds);
			if(flag){
				return "1";
			}
		}
		return "0";
	}
	
	/**
	 * 上传excle 到数据库表white_domain
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws Exception 
	 */
    @RequestMapping(value="/uploadWhileUrl",method=RequestMethod.POST)
    public String uploadWhileUrl(HttpServletRequest request,HttpServletResponse response){
    	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
    	MultipartFile multipartfile = multipartRequest.getFile("filepath");
    	Long fileSize = multipartfile.getSize();
    	if(fileSize>10485760){
    		String msg =  "error";
    		return "redirect:/domain/init?navigation=ggtfgl_1&error="+msg;
    	}
    	String fileName = multipartfile.getOriginalFilename();
        String filePath = request.getSession().getServletContext().getRealPath("/"+"uploadExcel");
        File dirPath = new File(filePath);   
        if (!dirPath.exists()) {   
            dirPath.mkdir();   
        }   
        String filepath = filePath+"/"+fileName;
    	File file = new File(filepath);	
    	try {   
    		 multipartfile.transferTo(file); // 保存上传的文件   
       
             // 创建对Excel工作簿文件的引用
             HSSFWorkbook wookbook = new HSSFWorkbook(new FileInputStream(filepath));
             // 在Excel文档中，第一张工作表的缺省索引是0
             // 其语句为：HSSFSheet sheet = workbook.getSheetAt(0);
             HSSFSheet sheet = wookbook.getSheet("Sheet1");
             //获取到Excel文件中的所有行数­
             int rows = sheet.getPhysicalNumberOfRows();
             //遍历行­
             for (int i = 1; i < rows; i++) {
                   // 读取左上端单元格­
                   HSSFRow row = sheet.getRow(i);
                   // 行不为空­
                   if (row != null) {
                         //获取到Excel文件中的所有的列­
                         int cells = row.getPhysicalNumberOfCells();
                         String value = "";     
                         //遍历列­
                         for (int j = 0; j < cells; j++) {
                               //获取到列的值­
                               HSSFCell cell = row.getCell(j);
                               if (cell != null) {
                                     switch (cell.getCellType()) {
                                           case HSSFCell.CELL_TYPE_FORMULA:
                                           break;
                                           case HSSFCell.CELL_TYPE_NUMERIC:
                                                 value += cell.getNumericCellValue() + ",";        
                                           break;  
                                           case HSSFCell.CELL_TYPE_STRING:
                                                 value += cell.getStringCellValue() + ",";
                                           break;
                                           default:
                                                 value += "0";
                                           break;
                                     }
                               }      
                         }
                         // 将数据插入到mysql数据库中­
                         String[] val = value.split(",");
                         String domainName = val[0];
                         String domainlUrl = val[1];
                         WhiteDomainDto whiteDomain = new WhiteDomainDto();
                         whiteDomain.setDomainName(domainName);
                         whiteDomain.setDomainUrl(domainlUrl);
                         whiteDomain.setSts("A");
                         whiteDomainService.addWhiteDomain(whiteDomain);
                   }
             }
             return "redirect:/domain/init?navigation=ggtfgl_1";
        } catch (FileNotFoundException e) {
           e.printStackTrace();
        }catch (IllegalStateException e) {   
           e.printStackTrace();   
           } catch (IOException e) {
           e.printStackTrace();
        }
    	return "";
    } 
}
