package com.hidata.ad.web.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

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
	public static boolean write(String filePath,String fileName,boolean append, String text) {
		boolean result = false;
		File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
		if (text == null)
			return result;
		try {
			filePath=filePath+fileName;
			BufferedWriter out = new BufferedWriter(new FileWriter(filePath,
					append));
			try {
				out.write(text);
				result=true;
			} finally {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static HttpServletResponse download(String filename,String str,HttpServletRequest request, HttpServletResponse response) {
        try {
            byte[] buffer =  str.getBytes();
            // 清空response
            response.reset();
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

	public static boolean uploadFile(MultipartFile file, String path,String fileName,boolean append) throws IOException {
		boolean result = false;
		String filePath = path + fileName;
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		OutputStream out = null;
		try {
			out = new FileOutputStream(filePath, append);
			out.write(file.getBytes());
			result = true;
		} catch (IOException e) {
			throw e;
		} finally {
			out.close();
		}
		return result;
	}
	public static void main(String[] args) {
		String str = FileUtils.read("C:/Users/Administrator/Desktop/crowd_mix.sh");
		System.out.println(str);
		str= str.replace("${dateArray}", "dataArray=(20110101 20330303)");
		FileUtils.write("C:/Users/Administrator/Desktop/", "crowd_mix1.sh",false, str);
	}
}
