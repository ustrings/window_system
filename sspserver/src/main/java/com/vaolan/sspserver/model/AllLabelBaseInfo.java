package com.vaolan.sspserver.model;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/**
 * 广告标签基础信息
 * Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年12月15日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
@Table("all_base_label")
public class AllLabelBaseInfo {
    // 主键
    @Column("autoid")
    private String autoId;

    // 标签值
    @Column("id")
    private String id;

    // 父标签值
    @Column("pId")
    private String pid;

    // 标签名称
    @Column("name")
    private String name;

    // 层级
    @Column("level")
    private String level;

    // 子节点个数
    @Column("childnum")
    private String childnum;
    
    
   
    public String getAutoId() {
        return autoId;
    }

    public void setAutoId(String autoId) {
        this.autoId = autoId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getChildnum() {
        return childnum;
    }

    public void setChildnum(String childnum) {
        this.childnum = childnum;
    }

    

}
