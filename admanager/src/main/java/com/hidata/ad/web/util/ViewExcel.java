package com.hidata.ad.web.util;

import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class ViewExcel extends AbstractExcelView {

	@Override
	public void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReadConfigFile rf = new ReadConfigFile("config.properties");
		String filename = rf.getValue("file_name");//设置下载时客户端Excel的名称       
        response.setContentType("application/vnd.ms-excel;charset=utf-8");       
        response.setHeader("Content-disposition", "attachment;filename=" + filename);       
        OutputStream ouputStream = response.getOutputStream();       
        workbook.write(ouputStream);       
        ouputStream.flush();       
        ouputStream.close();   

	}
}
