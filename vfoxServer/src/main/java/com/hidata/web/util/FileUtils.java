package com.hidata.web.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 此工具类用于文本文件的读写
 * 
 * @author sunly
 */
public class FileUtils {

	// 读取指定路径文本文件
	public static String read(String filePath) {
		StringBuilder str = new StringBuilder();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(filePath));
			String s;
			try {
				while ((s = in.readLine()) != null)
					str.append(s + '\n');
			} finally {
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str.toString();
	}

	// 写入指定的文本文件，append为true表示追加，false表示重头开始写，
	// text是要写入的文本字符串，text为null时直接返回
	public static void write(String filePath, boolean append, String text) {
		if (text == null)
			return;
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(filePath,
					append));
			try {
				out.write(text);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static HttpServletResponse download(String filename,String str,HttpServletRequest request, HttpServletResponse response) {
        try {
            byte[] buffer =  str.getBytes();
            // 清空response
            response.reset();
            // 设置response的Header
            
//            String enableFileName = java.net.URLEncoder.encode(filename, "UTF-8");
//            filename = new String(filename.getBytes(),"ISO-8859-1");
//            String enableFileName = "=?UTF-8?B?" + (new String(Base64.encodeBase64(filename.getBytes("UTF-8")))) + "?="; 
            
            String agent = (String)request.getHeader("USER-AGENT");     
		    if(agent != null && agent.indexOf("MSIE") == -1) {// FF      
		    	 filename = new String(filename.getBytes("UTF-8"), "ISO8859-1"); 
		    } else { // IE      
		    	filename =  java.net.URLEncoder.encode(filename, "UTF-8");
		    }  
           
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            response.addHeader("Content-Length", "" + str.getBytes().length);
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("text/plain");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }
	public static void main(String[] args) {
		String str = FileUtils.read("C:/Users/Administrator/Desktop/crowd_mix.sh");
		System.out.println(str);
		str= str.replace("${dateArray}", "dataArray=(20110101 20330303)");
		FileUtils.write("C:/Users/Administrator/Desktop/crowd_mix1.sh", false, str);
	}
}
