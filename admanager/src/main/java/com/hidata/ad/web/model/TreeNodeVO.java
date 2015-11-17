package com.hidata.ad.web.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 树节点对象 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年8月28日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
public class TreeNodeVO {

    // 节点ID
    private String id;

    // 节点父ID
    private String pId;

    // 节点名称
    private String name;
    
    //是否展开
    private boolean open = true;
    
 

	//节点图标
    private String  icon;
    
    //节点类型
    private String labelType;
    
    private boolean halfCheck;
    
    private boolean checked;
    
    private String crowdId;

    
    public String getCrowdId() {
		return crowdId;
	}

	public void setCrowdId(String crowdId) {
		this.crowdId = crowdId;
	}

	public boolean getChecked() {
 		return checked;
 	}

 	public void setChecked(boolean checked) {
 		this.checked = checked;
 	}
    public boolean getHalfCheck() {
		return halfCheck;
	}

	public void setHalfCheck(boolean halfCheck) {
		this.halfCheck = halfCheck;
	}

	// 子节点
    private List<TreeNodeVO> children = new ArrayList<TreeNodeVO>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<TreeNodeVO> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNodeVO> children) {
        this.children = children;
    }

    public String getLabelType() {
        return labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
    }
    
    

}
