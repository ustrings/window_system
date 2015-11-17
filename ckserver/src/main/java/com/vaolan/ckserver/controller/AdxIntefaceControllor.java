package com.vaolan.ckserver.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hidata.framework.util.StringUtil;
import com.vaolan.ckserver.model.BidResult;
import com.vaolan.ckserver.server.BidResultService;
import com.vaolan.ckserver.util.Constant;
import com.vaolan.ckserver.util.JavaMd5Util;

@Controller
public class AdxIntefaceControllor {

	private static Logger logger = LoggerFactory
			.getLogger(AdxIntefaceControllor.class);
	
	
	@Autowired
	private BidResultService  bidResultService;
//	@Resource(name="taskExecutor")
//	private ThreadPoolTaskExecutor taskExecutor;
	
	@RequestMapping(value = "/bidres", produces = "image/gif")
	@ResponseBody
	public void bidres(HttpServletRequest request, HttpServletResponse response) {
		try {
			String encryption = request.getParameter("p");
			String bid = request.getParameter("bid");
			if(StringUtil.isNotBlank(encryption)){
				BidResult bidResult = new BidResult();
				bidResult.setChannel(request.getParameter("cn"));
				bidResult.setDomain(request.getParameter("dm"));
				bidResult.setAdSize(request.getParameter("s"));
				bidResult.setViewScreen(request.getParameter("vs"));
				bidResult.setAdId(request.getParameter("adId"));
				bidResult.setEncryption(encryption);
				byte[] orig = Base64.decodeBase64(encryption.getBytes());
				byte[] price = JavaMd5Util.getTanxRealPrice(orig,Constant.G_KEY_VAOLAN);
				byte[] crc = JavaMd5Util.getTanxCRC(orig, price,Constant.G_KEY_VAOLAN);
				bidResult.setBid(bid);
				bidResult.setResultPrice(StringUtil.byteArrayToInt(price,0)+"");
				bidResult.setCrcVerify(JavaMd5Util.crcVerify(orig, crc));
				bidResultService.saveBidResult2Log(bidResult);
			}else{
				logger.error("竞价反馈接口收到tanx的密文为null:bid="+bid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("竞价发反馈接口解码时异常：",e);
		}finally{
			/**
			 * 返回一个空1x1像素的gif图片 
			 */
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache, max-age=0, must-revalidate");
			response.setDateHeader("Expires", 0);

			response.setContentType("image/gif");
			BufferedImage image = new BufferedImage(1, 1,BufferedImage.TYPE_INT_RGB);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				ImageIO.write(image, "gif", baos);
				byte[] imgby = baos.toByteArray();
				OutputStream os = response.getOutputStream(); 
				os.write(imgby);
				os.flush();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("竞价反馈接口返回0x0图片时异常：",e);
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		String encryption="AQrtCAMAAFOWxMklhQAu3viL2qwoH1aorg==";
		byte[] orig = Base64.decodeBase64(encryption.getBytes());
		byte[] crcSrc = new byte[4];
		byte[] bid = new byte[16];
		System.arraycopy(orig, 21, crcSrc, 0, 4);
		System.arraycopy(orig, 1, bid, 0, 16);
		byte[] price = JavaMd5Util.getTanxRealPrice(orig,Constant.G_KEY_VAOLAN);
		byte[] crc = JavaMd5Util.getTanxCRC(orig, price,Constant.G_KEY_VAOLAN);
		int realPrice = StringUtil.byteArrayToInt(price,0);
		System.out.println(realPrice);
		System.out.println("crc校验："+Arrays.equals(crc, crcSrc));
		System.out.println("bid:"+new String(bid));
		System.out.println(encryption.length());
	}
}
