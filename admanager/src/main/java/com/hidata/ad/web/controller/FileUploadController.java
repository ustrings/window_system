package com.hidata.ad.web.controller;

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

import com.hidata.ad.web.util.FTPUtil;

@Controller
public class FileUploadController {

    private static final String UPLOAD_FILE_PATH = "/upload/";

    // private static final String HOST_DOMAIN = "http://localhost:8080";
    // private static final String FTP_SERVER_HOST = "192.168.1.109";
    // private static final int FTP_PORT = 21;
    // private static final String FTP_IMG_PATH = "/var/www/ftp/img";
    // private static final String FTP_SERVER_USER = "root";
    // private static final String FTP_SERVER_PWD = "123456";

    private static Properties props = null;

    static {
        try {
            props = PropertiesLoaderUtils.loadAllProperties("ftp-config.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
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

                // 图片规格有效
                boolean isValidImage = true;
                if (isImage(suffix.toLowerCase())) {
                    // BufferedImage image = Imaging.getBufferedImage(mf.getInputStream());
                    Dimension dimension = Imaging.getImageSize(mf.getBytes());
                    int width = (int) dimension.getWidth();
                    int height = (int) dimension.getHeight();
                    String imageSize = width + "x" + height;

                    String standardSize = request.getParameter("xsize").trim();

                    if (!imageSize.endsWith(standardSize)) {
                        isValidImage = false;
                    }
                }

                if (isValidImage) {
                    // 上传文件
                    String temp = request.getSession().getServletContext().getRealPath("/")
                            + UPLOAD_FILE_PATH;
                    String name = uuid + suffix;
                    uploadFile(mf, temp, name);
                    // savePath = UPLOAD_FILE_PATH + name;

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
                            json.put("title", mf.getOriginalFilename());
                            json.put("state", "SUCCESS");
                        }
                    }
                } else {
                    json.put("state", "SIZE_NOT_MATCH");
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
