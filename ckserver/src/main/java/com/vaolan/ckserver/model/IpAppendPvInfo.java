package com.vaolan.ckserver.model;

/**
 * 构造ip追投广告对象 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年6月29日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
public class IpAppendPvInfo {

    private String srcIp;

    private String ua;

    private String ts;

    private String adId;

    private String ipUaKey;

    public String getSrcIp() {
        return srcIp;
    }

    public void setSrcIp(String srcIp) {
        this.srcIp = srcIp;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getIpUaKey() {
        return ipUaKey;
    }

    public void setIpUaKey(String ipUaKey) {
        this.ipUaKey = ipUaKey;
    }

}
