package com.hidata.web.controller;

import java.awt.Dimension;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hidata.web.util.FTPUtil;
import com.hidata.web.util.JspToHtml;

@Controller
public class FileUploadController {

    private static final String UPLOAD_FILE_PATH = "/upload/";

    private static Properties props = null;

    static {
        try {
            props = PropertiesLoaderUtils.loadAllProperties("ftp-config.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @RequestMapping(value="/createHtml/index", method=RequestMethod.GET)
	public String adIndex(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
			return "/static/create-static-file";
	}
    
    /**
     * 生成静态文件，并把文件通过 ftp 上传到服务器
     * @param request
     * @param response
     * @param model
     * @return
     * @throws IOException
     * @throws ImageReadException
     */
    @RequestMapping(value = "/createHtmlFile", method = RequestMethod.GET)
    @ResponseBody
    public String createHtmlFile(HttpServletRequest request, HttpServletResponse response, Model model)
            throws IOException, ImageReadException {
    	JSONObject json = new JSONObject();

    	long start = System.currentTimeMillis();
    	String root = request.getSession().getServletContext().getRealPath("/");
    	// 获取文件路径
    	String filePath  =  root + UPLOAD_FILE_PATH.substring(1,UPLOAD_FILE_PATH.length()) +  props.getProperty("htmlName");
    	
    	// 把抓取的网页保存到本地
    	if (JspToHtml.JspToHtmlByURL(props.getProperty("requestUrl"), filePath)) {
    		// 复制文件到 404web 目录下
    		JspToHtml.copyFile(filePath, props.getProperty("html.directory") + props.getProperty("htmlName"));
    		json.put("state", "SUCCESS");
    	} else {
    		json.put("state", "FALSE");
    	}
    	
    	// 把文件拷贝的指定的目录
    	

//    	try {
//
//    		if (props != null) {
//    			String fileName = props.getProperty("htmlName");
//    			File local = new File(filePath);
//    			// 将文件ftp上传至文件服务器（主要包含广告图片、flash）
//    			FTPUtil ftp = FTPUtil.getInstance(props.getProperty("html_host"),
//    					Integer.valueOf(props.getProperty("html_port")));
//    			ftp.connectAndLogin(props.getProperty("html_username"),
//    					props.getProperty("html_password"));
//    			ftp.changeWorkDirectory(props.getProperty("html.directory"));
//    			boolean result = ftp.storeFileToServer(local, fileName);
//    			ftp.logout();
//    			if (result) {
//    				json.put("state", "SUCCESS");
//    			}
//    		} else {
//    			json.put("state", "页面生成有问题");
//    		}
//    	} catch(Exception e) {
//    		e.printStackTrace();
//    	}
    	long end = System.currentTimeMillis();
    	System.out.println("耗时：" + (end - start));
    	return json.toString();
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile(HttpServletRequest request, HttpServletResponse response, Model model)
            throws IOException, ImageReadException {

    	JSONObject json = new JSONObject();

        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

            String fileName = "";
            for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
                MultipartFile mf = entity.getValue();
                fileName = mf.getOriginalFilename();

                String uuid = UUID.randomUUID().toString().replaceAll("\\-", "");//
                String suffix = fileName.indexOf(".") != -1 ? fileName.substring(
                        fileName.lastIndexOf("."), fileName.length()) : null;
                suffix = (suffix != null ? suffix : "");//

                    // 上传文件
                    String temp = request.getSession().getServletContext().getRealPath("/")
                            + UPLOAD_FILE_PATH;
                    System.out.println(temp);
                    String name = uuid + suffix;
                    uploadFile(mf, temp, name);
//                     savePath = UPLOAD_FILE_PATH + name;

                    if (props != null) {
                        File local = new File(temp + name);
                        // 将文件ftp上传至文件服务器（主要包含广告图片、flash）
                        FTPUtil ftp = FTPUtil.getInstance(props.getProperty("host"),
                                Integer.valueOf(props.getProperty("port")));
                        ftp.connectAndLogin(props.getProperty("username"),
                                props.getProperty("password"));
                        ftp.changeWorkDirectory(props.getProperty("image.directory"));
                        boolean result = ftp.storeFileToServer(local, name);
                        ftp.logout();

                        if (result) {
                            json.put("original", mf.getOriginalFilename());
                            json.put("url", props.getProperty("img.domain") + name);
                            System.out.println(props.getProperty("img.domain") + name);
                            json.put("title", mf.getOriginalFilename());
                            json.put("state", "SUCCESS");
                        }
                    }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    private String uploadFile(MultipartFile file, String path, String fileName) throws IOException {
        String filePath = path + fileName;
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath);
            out.write(file.getBytes());
        } catch (IOException e) {
            throw e;
        } finally {
            out.close();
        }
        return filePath;
    }

    // 是否为图片
    private boolean isImage(String suffix) {
        boolean isImage = false;

        if (".jpg".equals(suffix) || ".gif".equals(suffix) || ".png".equals(suffix)
                || ".jpeg".equals(suffix) || ".bmp".equals(suffix)) {
            isImage = true;
        }

        return isImage;
    }
}
