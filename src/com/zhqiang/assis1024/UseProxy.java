package com.zhqiang.assis1024;

import java.util.Properties;

/**
 * Created by shaoer on 15/5/16.
 */
public class UseProxy {
    public UseProxy() {
        Properties prop = System.getProperties();
        prop.setProperty("http.proxyHost", "127.0.0.1");
        prop.setProperty("http.proxyPort",  "7070");
    }
}
