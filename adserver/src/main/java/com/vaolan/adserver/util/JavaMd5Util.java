package com.vaolan.adserver.util;


import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Description: 对字符串进行md5加密
 * ClassName: JavaMd5Util
 * Date: 2013年9月20日
 *
 * @author chenjinzhao
 */
public class JavaMd5Util {
	public static Logger log = LoggerFactory.getLogger(JavaMd5Util.class);
    
	/**
	 * 解密tanx竞价的真实价格
	 * @param orig
	 * @param byte[] 解密秘钥
	 * @return byte[]  tanx成交价格
	 * @author pujie
	 * */
	public static byte[] getTanxRealPrice(byte[] orig,byte[] secretKey){
		byte[] cpDst = new byte[32];
		byte[] pEncrypt = new byte[4];
		byte[] h4 = new byte[4];
		byte[] price = new byte[4];
		System.arraycopy(orig, 1, cpDst, 0,16);
		System.arraycopy(orig, 17, pEncrypt, 0,pEncrypt.length);
		System.arraycopy(secretKey, 0, cpDst, 16,secretKey.length);		
		System.arraycopy(DigestUtils.md5(cpDst),0,h4, 0,h4.length);
		for(int i=0;i<h4.length;i++){
			price[i] = (byte) (h4[i] ^ pEncrypt[i]);
		}
		return price;
	}
	/**
	 * 计算tanx的CRC
	 * @param orig
	 * @param byte[] 成交交个price
	 * @param byte[] 解密秘钥
	 * @return byte[]  CRC
	 * @author pujie
	 * */
	public static byte[] getTanxCRC(byte[] orig,byte[] price,byte[] secretKey){
		byte[] crc = new byte[4];
		byte[] md5Src = new byte[37];
		System.arraycopy(orig, 0, md5Src, 0, 17);
		System.arraycopy(price, 0, md5Src, 17, price.length);
		System.arraycopy(secretKey, 0, md5Src, 21,secretKey.length);
		System.arraycopy(DigestUtils.md5(md5Src),0,crc, 0, crc.length);
		return crc;
	}
//	/**
//	 * CRC校验
//	 * @param orig
//	 * @param byte[]  CRC
//	 * @return 0 or 1   0:fail 1:success
//	 * @author pujie
//	 * */
//	public static String crcVerify(byte[] orig,byte[] crc){
//		String result = Constant.CRC_FAIL;
//		if(orig!=null && null != orig){
//			byte[] crcSrc = new byte[4];
//			System.arraycopy(orig, 21, crcSrc, 0, 4);
//			result = Arrays.equals(crc, crcSrc)?Constant.CRC_SUCCESS:Constant.CRC_FAIL;
//		}
//		return result;
//	}
	public static String md5Encryp(String... vals) {
		
		StringBuffer source = new StringBuffer();
		for(String val: vals) {
			source.append(val);
        }
		
		StringBuffer sb = new StringBuffer(32);
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(source.toString().getBytes("utf-8"));

			for (int i = 0; i < array.length; i++) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.toUpperCase().substring(1, 3));
			}
		} catch (Exception e) {
			log.error("Can not encode the string '" + source + "' to MD5!", e);
			return null;
		}

		return sb.toString();
	}
	
	public static String md5Encryp16Byte(String... vals) {
		
		
		String byte32Str = md5Encryp(vals);
		return byte32Str.substring(8, 24);
	}
	
	
	public static String md5EncrypFirst16(String... vals) {

		String byte32Str = md5Encryp(vals);
		return byte32Str.substring(0, 16);
	}
	public static void main(String[] args){
		String appId = "10000000";
		String[] app = {appId,"sd"};
		
		System.out.println("appId:"+ JavaMd5Util.md5Encryp16Byte(appId, "sd"));
		
		System.out.println("appId:"+ JavaMd5Util.md5Encryp16Byte(app));
		
		String appKey = "appKey10000000";
		System.out.println("appKey:"+ JavaMd5Util.md5Encryp(appKey, "sd"));
	}
}
