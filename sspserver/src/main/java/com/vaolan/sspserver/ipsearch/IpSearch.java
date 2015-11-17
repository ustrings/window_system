package com.vaolan.sspserver.ipsearch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.vaolan.sspserver.util.Config;

public class IpSearch {

	private static List<String> cityList = new ArrayList<String>();
	private static List<IpNode> ipNodes = new ArrayList<IpNode>();

	private static String cityFilePath = Config.getProperty("cityFilePath");
	private static String ipFilePath = Config.getProperty("ipFilePath");

	private static Logger logger = Logger.getLogger(IpSearch.class);
	static {
		//TODO:**********ip load重要，上线必须打开***************
		cityList = readCity(cityFilePath);
		ipNodes = readFile(ipFilePath);
	}

	public static void loadIp() {
		cityList = readCity(cityFilePath);
		ipNodes = readFile(ipFilePath);
	}

	/**
	 * @param ip
	 * @return
	 */
	private static long ip2Num(String ip) {
		long ipNum = 0;
		if (ip != null && ip.trim().length() != 0) {
			String[] subips = ip.split("\\.");
			for (String str : subips) {
				ipNum = ipNum << 8;
				ipNum += Integer.parseInt(str);
			}
		}
		return ipNum;
	}

	private static List<String> readCity(String filePath) {
		List<String> cityList = new ArrayList<String>();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(filePath));
			String s;
			int i = 0;
			while ((s = in.readLine()) != null) {
				i ++;
				if(i % 100000==0) {
					logger.info("size:" + i);
				}
				cityList.add(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return cityList;
	}

	private static List<IpNode> readFile(String filePath) {
		List<IpNode> ipNodes = new ArrayList<IpNode>();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(filePath));
			String s;
			int i = 0;
			while ((s = in.readLine()) != null) {
				// String[] strArr = s.split("\t");
				Pattern p1 = Pattern
						.compile("([1-9][0-9]{0,2}\\.[0-9]{0,3}\\.[0-9]{0,3}\\.[0-9]{0,3})\\s+([1-9][0-9]{0,2}\\.[0-9]{0,3}\\.[0-9]{0,3}\\.[0-9]{0,3})\\s+(.*)");
				i ++;
				if(i%10000==0) {
					logger.info("index:" + i++);
				}
				Matcher m = p1.matcher(s);

				while (m.find()) {
					// System.out.println(m.group(1) + " " + m.group(2) + " " +
					// m.group(3));
					IpNode ipNode = new IpNode();
					ipNode.setIp1(ip2Num(m.group(1)));
					ipNode.setIp2(ip2Num(m.group(2)));
					String cityName = adpterCity(m.group(3));
					if (cityName != null && cityName != "") {
						ipNode.setAddr(cityName);
					} else {
						continue;
					}
					ipNodes.add(ipNode);
				}
			}
			logger.info("size:" + ipNodes.size());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Collections.sort(ipNodes, new IpCompareAsc());
		return ipNodes;
	}

	/**
	 * 根据ip 获取地址
	 * 
	 * @param ip
	 * @return
	 */
	public static String getAddrByIp(String ip) {
		long ipNum = ip2Num(ip);

		int low = 0;
		int high = ipNodes.size() - 1;
		int mid = 0;
		while (low <= high) {
			mid = low + (high - low) / 2;
			IpNode ipNode = ipNodes.get(mid);
			if (ipNode.getIp1() <= ipNum && ipNum <= ipNode.getIp2()) {
				return ipNode.getAddr();
			} else if (ipNode.getIp1() > ipNum) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		
		return "";

	}

	private static String adpterCity(String str) {
		String cityName = "";
		for (int i = 0; i < cityList.size(); i++) {
			String tmpName = cityList.get(i);
			if (str.contains(tmpName)) {
				cityName = tmpName;
				break;
			}
		}
//		logger.info("cityName:" + cityName);
		
		return cityName;
	}

	public static void main(String[] args) throws IOException {

		BufferedReader in = new BufferedReader(new FileReader(
				"/Users/chenjinzhao/Documents/text_v/nanjing.txt"));
		String s;

		int nanjingPvNum = 0;

		Map<String, Integer> nanjingipMap = new HashMap<String, Integer>();

		Map<String, Integer> nanjingUvMap = new HashMap<String, Integer>();

		while ((s = in.readLine()) != null) {

			// System.out.println(s);
			String[] implog = s.split("\t");

			String userid = implog[3];
			String ip = implog[4];

			
			String region ="";
			try {

				 region = IpSearch.getAddrByIp(ip);
			} catch (Exception e) {
				continue;
			}
			
			if (region.indexOf("南京") != -1) {

				if (nanjingipMap.containsKey(ip)) {
					Integer ipPv = nanjingipMap.get(ip);
					ipPv++;
					nanjingipMap.put(ip, ipPv);
				} else {
					nanjingipMap.put(ip, 1);
				}

				if (nanjingUvMap.containsKey(userid)) {
					Integer userIdPv = nanjingUvMap.get(userid);
					userIdPv++;
					nanjingUvMap.put(userid, userIdPv);
				} else {
					nanjingUvMap.put(userid, 1);
				}

				nanjingPvNum++;
			}

		}

		System.out.println("一共pv:" + nanjingPvNum);
		System.out.println("一共ip:" + nanjingipMap.size());
		System.out.println("一共uv:" + nanjingUvMap.size());

		for (String userId : nanjingUvMap.keySet()) {
			Integer dd = nanjingUvMap.get(userId);

			if (dd > 5 && dd <= 7) {
				System.out.println(userId);
			}
		}

		// System.out.println(IpSearch.getAddrByIp("124.193.199.178"));
	}
}
