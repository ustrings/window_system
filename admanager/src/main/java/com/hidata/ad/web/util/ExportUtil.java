package com.hidata.ad.web.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 导出excel的工具类
 * @author xiaoming
 *
 */
public class ExportUtil {
	public HSSFWorkbook getHWB(String columnNames){
		HSSFWorkbook workbook = new HSSFWorkbook(); 
		HSSFSheet sheet = workbook.createSheet("");
		HSSFRow row= sheet.createRow((short)0);  
		HSSFCell cell = null;  
		if(columnNames != null && !"".equals(columnNames)){
			String[] columns = columnNames.split(",");
					int nColumn = columns.length;
			          
			        System.out.println("nColumn: " + nColumn);  
			          
			        //写入各个字段的名称  
			        for(int i=1;i<=nColumn;i++) {  
			              cell = row.createCell((i-1));  
			              cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
			              cell.setCellValue(columns[i]);  
			        }  
			        
			        return workbook;  
		}
		
		return null;
	}
	
}
