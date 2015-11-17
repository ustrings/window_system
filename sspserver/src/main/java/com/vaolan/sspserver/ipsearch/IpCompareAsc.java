package com.vaolan.sspserver.ipsearch;

import java.util.Comparator;
/**
 * 升序
 * @author Administrator
 *
 */
public class IpCompareAsc implements Comparator<IpNode>{

	@Override
	public int compare(IpNode o1, IpNode o2) {
		
		if(o1.getIp1()<o2.getIp1()){
			return -1;
		}else if(o1.getIp1()>o2.getIp1()){
			return 1;
		}else{
			return 0;
		}
	}
}
