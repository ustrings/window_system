package com.hidata.ad.web.model;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/**
 * 基础数据配置 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年5月12日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
@Table("map_kv")
public class MapKv {
    @Column("m_id")
    private int id;

    @Column("m_type")
    private String MType;

    @Column("attr_code")
    private String attrCode;

    @Column("attr_value")
    private String attrValue;

    @Column("width")
    private String width;

    @Column("height")
    private String height;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMType() {
        return MType;
    }

    public void setMType(String mType) {
        MType = mType;
    }

    public String getAttrCode() {
        return attrCode;
    }

    public void setAttrCode(String attrCode) {
        this.attrCode = attrCode;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

}
