package com.hidata.ad.web.util;

import com.zel.es.manager.ws.client.ESSearchServiceManager;

public class EssSearchInstance {

    private static ESSearchServiceManager eSSearchServiceManager= new ESSearchServiceManager();
    private EssSearchInstance(){}   
    public static synchronized  ESSearchServiceManager getInstance()
    {
        return eSSearchServiceManager;
    }
}
