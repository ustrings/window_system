package com.hidata.web.model;

import java.io.OutputStream;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.hidata.web.util.TimeUtil;

public class ViewExcel extends AbstractExcelView {

	@Override
	public void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String now = TimeUtil.dateLongToYMDString(new Date().getTime());
        response.setContentType("application/vnd.ms-excel;charset=utf-8");       
        response.setHeader("Content-disposition", "attachment;filename=" + "广告报告_"+now);       
        OutputStream ouputStream = response.getOutputStream();       
        workbook.write(ouputStream);       
        ouputStream.flush();       
        ouputStream.close();   

	}
}
