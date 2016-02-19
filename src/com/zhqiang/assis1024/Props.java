package com.zhqiang.assis1024;

import java.util.*;

/**
 * Created by zhqiang on 14-3-17.
 */
public class Props {

    public static Map<String, String> map = null;

    private static final String CFG_FILE = "assis.properties";

    protected static final String CL_URL = "CL_URL";

    protected static final String ASIA_MOSAICKED = "ASIA_MOSAICKED";
    protected static final String ASIA_NONMOSAICKED = "ASIA_NONMOSAICKED";
    protected static final String A_COMIC = "A_COMIC";

    protected static List<String> FORUMS = new ArrayList<String>();

    private static Properties props = new Properties();

    protected static String PATH = null;

    protected static Map<String, String> getParams(){
        try{
            if(map == null){
                map = new HashMap<String, String>();
                props.load(Props.class.getClassLoader().getResourceAsStream(CFG_FILE));
                map.put(CL_URL, props.getProperty(CL_URL));
                map.put(ASIA_MOSAICKED, props.getProperty(ASIA_MOSAICKED));
                map.put(ASIA_NONMOSAICKED, props.getProperty(ASIA_NONMOSAICKED));
                map.put(A_COMIC, props.getProperty(A_COMIC));

                FORUMS.add(ASIA_MOSAICKED);
                //FORUMS.add(ASIA_NONMOSAICKED);
                //FORUMS.add(A_COMIC);

                PATH = Props.class.getClassLoader().getResource("").getPath();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return map;
    }

    public static void main(String[] args){
        System.out.println("1" + Props.class.getClassLoader().getResource("").getPath());
    }
}
