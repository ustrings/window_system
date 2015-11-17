package com.hidata.ad.web.service;

import java.util.List;

import com.hidata.ad.web.model.AllBaseLabel;
import com.hidata.ad.web.model.AllLabelAdPlan;
import com.hidata.ad.web.model.IntrestLabelCrowd;
import com.hidata.ad.web.model.IntrestLabelTree;

/*
 * 兴趣标签定向服务
 */
public interface IIntrestsCrowdService {

    /**
     * 添加兴趣标签定向服务
     * 
     * @param intrestDto
     * @return
     * @author zhoubin
     */
    public int addIntrestCrowdInfo(IntrestLabelCrowd intrestDto);

    /**
     * 查询兴趣标签树子节点
     * 
     * @param paraLabelTree
     * @return
     * @author zhoubin
     */
    public List<IntrestLabelTree> qryIntrestLabelChildrenByParent(IntrestLabelTree paraLabelTree);

    /**
     * 定时更新人群总数
     * 
     * @author zhoubin
     */
    public void fixExecuteUpdateCrowdNum(String date);

    /**
     * 查询标签子节点
     * 
     * @param paraAllLabelTree
     * @return
     * @author zhoubin
     */
    public List<AllBaseLabel> qryAllBaseLabelChildrenByParent(AllBaseLabel paraAllLabelTree);

    /**
     * 查找对应标签
     * @param paraBaseLabel
     * @return
     * @author zhoubin
     */
    public AllBaseLabel qryAllBaseLabelByLabelVal(String  paraLabelCode);
    
    /**
     * 根据父标签查找该标签下的子标签
     * @param string
     * @return
     */
	public List<AllBaseLabel> qryAllBaseLabelByPId(String string);

}
