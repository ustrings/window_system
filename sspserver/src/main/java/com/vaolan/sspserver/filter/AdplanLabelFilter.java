package com.vaolan.sspserver.filter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.nutz.ssdb4j.spi.SSDB;
import org.springframework.stereotype.Service;

import com.hidata.framework.ssdb.SSDBRegister;
import com.hidata.framework.util.StringUtil;
import com.vaolan.sspserver.controller.SSPCenterControllor;
import com.vaolan.sspserver.model.AdFilterElement;
import com.vaolan.sspserver.model.AdPlanLabelRel;
import com.vaolan.sspserver.model.AdvPlan;
import com.vaolan.sspserver.util.DataQueryType;
import com.vaolan.sspserver.util.HttpUtil;
import com.vaolan.sspserver.util.JavaMd5Util;
import com.vaolan.sspserver.util.SSDBUtils;

/**
 * 广告计划对应标签按需生成,如果满足投放需求才加载 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年12月15日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
@Service
public class AdplanLabelFilter {
	private static Logger Log = Logger.getLogger(SSPCenterControllor.class);
	
//	private SSDBRegister SSDBRegister14_8890;
	

    /**
     * 过滤用户标签广告
     * @param advPlan
     * @param adFilterElement
     * @return
     */
    public boolean doFilter(AdvPlan advPlan, AdFilterElement adFilterElement, Map<String, String> userLabelMap) {
        return this.adLabelFilter(advPlan, adFilterElement, userLabelMap);
    }

    /**
     * 判断当前用户的标签中有没有命中的标签广告
     * @param advPlan
     * @param adFilterElement
     * @return
     */
    public boolean adLabelFilter(AdvPlan advPlan, AdFilterElement adFilterElement, Map<String, String> userLabelMap) {
//    	Log.info("标签AdpalnLabel过滤进入...");
        boolean flag = false;
        if (advPlan.isLabelFilter()) {
            if (StringUtil.isBlank(adFilterElement.getAdAcct())
                    && StringUtil.isBlank(adFilterElement.getIp())) {
                return false;
            }
            List<AdPlanLabelRel> adLabelRelList = advPlan.getAdPlanLabelRelList();
//            Log.info("=======标签过滤开始======");
            for (AdPlanLabelRel iterAdPlanLabelRel : adLabelRelList) {
//            	Log.info("广告标签内容：" + iterAdPlanLabelRel.getFullPathNameMDVal());
                if (userLabelMap.containsKey(iterAdPlanLabelRel.getFullPathNameMDVal())) {
                    return true;
                }
            }
//            Log.info("=======标签过滤结束======");

            return false;
        }
        return flag;
    }

   
    
    /**
     * ssdb
     * @param userId
     * @return
     */
//    private Set<String> getUserLabelsByAdUa(String userId) {
////    	userId = "8ffffeb0908050c5efa38cc06d63035e";
//    	
        Set<String> userLabels = new HashSet<String>();
//
//        char c = userId.charAt(0);
//
//        int accii = (int) c;
//
//        int model = accii % 8;
////        switch (model) {
////        case 0:
////        	 sSDBRegister  =  SSDBRegister14_8890;
////            break;
////        case 1:
////        	sSDBRegister  =  SSDBRegister14_8891;
////            break;
////        default:
////            break;
////        }
//        // 上线关闭
//        SSDB ssdb = SSDBRegister14_8890.init();
//        // 上线打开
////     SSDB ssdb = sSDBRegister.init();
//        Map<String, String> map = SSDBUtils.getAllZset(ssdb, userId);
//        for(String key : map.keySet()) {
//        	userLabels.add(key);
//        }
//        try {
//			ssdb._depose();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
        
        
//        return userLabels;
//
//    }
    
    public static void main(String[] args) {
        Set<String> userLabels = new HashSet<String>();

        userLabels.add("电商;家用电器;生活电器");

        userLabels.add("电商;品牌女装;牛仔裤");

        for (String iterUserLabels : userLabels) {
            if (StringUtil.isBlank(iterUserLabels)) {
                continue;
            }
            String[] userLabelArray = iterUserLabels.split(";");

            for (int i = 0; i < userLabelArray.length; i++) {
                String[] iterCurArray = new String[i + 1];

                for (int j = 0; j <= i; j++) {
                    iterCurArray[j] = userLabelArray[j];
                }

                String curMdLabelKey = JavaMd5Util.md5EncrypFirst16(iterCurArray);

                int f = 0;
                System.out.println("数组长度:" + iterCurArray.length);
                for (String iterLabel : iterCurArray) {
                    System.out.println("当前值" + f + "-------" + iterLabel);
                    f++;
                }

            }

        }
    }
    
}
