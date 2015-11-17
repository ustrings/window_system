package com.hidata.ad.web.dao;

import java.util.List;

import com.hidata.ad.web.model.IntrestLabelCrowd;
import com.hidata.ad.web.model.IntrestLabelTree;

/**
 * 兴趣定向
 * Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年8月27日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
public interface IIntrestCrowdDao {

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

    public List<IntrestLabelCrowd> queryIntrestLabelCrowdListByCrowdId(String crowdId)
			throws Exception;

    public IntrestLabelTree queryIntrestLabelTreeByLabelVal(String labVal);

	int delIntrestCrowdInfoByCrowdId(String crowdId);


}
