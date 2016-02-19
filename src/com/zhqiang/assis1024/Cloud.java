package com.zhqiang.assis1024;

/**
 * Created by shaoer on 14-7-9.
 */
public class Cloud {

    public static void MGR_A(){
        System.out.println("====管理控制器启动====");
        System.out.println("    检测主备模式...    ");
        System.out.println("      模式：主    ");
        System.out.println("====启动完毕====");
//       TOPOSERVICE_DATA-TOMCAT,TOPOSERVICE_COLL-TOMCAT,ASA-SYSLOG,ASA-TOMCAT,SYSLOG-TOMCAT
        System.out.println("租户注册...");
        System.out.println("服务检测...");
        System.out.println("创建路由：col1-model1");
        System.out.println("创建路由：col1-web1");
        System.out.println("创建路由：col1-alm1");
        System.out.println("创建路由：model1-web1");
        System.out.println("创建路由：model1-alm1");
        System.out.println("创建路由：model1-auto1");
        System.out.println("创建路由：auto1-web1");
        System.out.println("创建路由：alm1-syslog1");
        System.out.println("创建路由：alm1-web1");
        System.out.println("创建路由：syslog1-web1");
    }

    public static void MGR_B(){
        System.out.println("====管理控制器启动====");
        System.out.println("    检测主备模式...    ");
        System.out.println("      模式：被    ");
        System.out.println("      同步数据...    ");
        System.out.println("      同步完毕    ");
        System.out.println("====启动完毕====");
    }

    public static void Agent_A(){
        System.out.println("====Agent启动====");
        System.out.println("    Agent ID : agent1");
        System.out.println("    服务器能力 : web;data;model;alm;auto;syslog;col;mysql;");
        System.out.println("====启动完毕====");
        System.out.println("启动服务：web1");
        System.out.println("启动服务：data1");
        System.out.println("启动服务：model1");
        System.out.println("启动服务：alm1");
        System.out.println("启动服务：auto1");
        System.out.println("启动服务：syslog1");
        System.out.println("启动服务：col1");
        System.out.println("启动服务：mysql1");
    }

    public static void Agent_B(){
        System.out.println("====Agent启动====");
        System.out.println("    Agent ID : agent2");
        System.out.println("    服务器能力 : web;data;model;alm;auto;syslog;col;mysql;");
        System.out.println("====启动完毕====");
    }

    public static void main(String[] args){
//        MGR_A();
//        MGR_B();
        Agent_A();
//        Agent_B();
    }
}
