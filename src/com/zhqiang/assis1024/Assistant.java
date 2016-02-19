package com.zhqiang.assis1024;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhqiang on 14-3-12.
 */
public class Assistant {

    protected static Pattern titlePattern = Pattern.compile("id=\"\">.*</a>");
    protected static Pattern keyPattern = Pattern.compile("[a-zA-Z]{2,6}\\s-{0,1}\\d{2,4}");
    protected static Pattern companyPattern = Pattern.compile("[a-zA-Z]{2,5}");
    protected static Pattern seriesPattern = Pattern.compile("\\d{2,4}");
    protected static Pattern urlPattern = Pattern.compile("href=\".*\" tar");
    protected static Pattern userPattern = Pattern.compile("\"bl\">.*</a>");
    protected static Pattern datePattern = Pattern.compile("\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d");

    protected static final String myName = "@ohmyga1024";
    protected static final String myMail = "ohmyga1024@yeah.net";

    /**
     * If use local database
     * */
    protected static boolean useDb = false;

    protected static String getResultFromStr(String str, Pattern p){
        String result = null;
        if(str!=null){
            try{
                Matcher m = p.matcher(str);
                while(m.find()){
                    result = m.group();
                    break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }

    protected static String getResultFromStr(String str, Pattern p, int start, int end){
        String result = null;
        if(str!=null){
            try{
                result = getResultFromStr(str, p);
                result = result==null ? null: result.substring(start, result.length()-end);
//            result = result.substring(start, result.length()-end);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }

    /*
    public static String getData(String clAddr){
        String info = "";

        if(!clAddr.endsWith("/")){
            clAddr = clAddr + "/";
        }

        for(String forum : Props.FORUMS){
            String strURL = Props.getParams().get(forum);
            int c = 0;

            System.out.println("Get Data From "+forum);

            if(Cache.MAP.get(forum) != null && Cache.MAP.get(forum).size()!=0){
                System.out.println("First one last time : " + Cache.MAP.get(forum).get(0).toString());
            }

            for(int page=1; page<100; page++){
                int pageC = 0;
                Map<String, Integer> map = new HashMap<String, Integer>();

                String pageUrl = clAddr + strURL + page;
                try {
                    System.out.println("page = "+page);
                    URL clUrl = new URL(pageUrl);
                    HttpURLConnection httpConn = (HttpURLConnection) clUrl.openConnection();
                    httpConn.connect();
                    httpConn.setReadTimeout(10000);
                    httpConn.setConnectTimeout(10000);
                    InputStreamReader input = new InputStreamReader(httpConn.getInputStream(), "gbk");
                    BufferedReader bufReader = new BufferedReader(input);

                    System.out.println(bufReader.read());
                    String line = bufReader.readLine();

                    Topic topic = new Topic();
                    List<String> tmpList = new ArrayList<String>();
                    while (line != null) {
                        tmpList.add(line);
                        System.out.println(line);
                        try{
                            System.out.println(bufReader.read());
                            line = bufReader.readLine();
                        }catch (IOException ioe){
                            System.out.println("Error Line : "+ line);
                            ioe.printStackTrace();
//                            bufReader.close();
//                            httpConn.disconnect();
//
//                            httpConn = (HttpURLConnection) clUrl.openConnection();
//                            httpConn.connect();
//                            httpConn.setReadTimeout(10000);
//                            httpConn.setConnectTimeout(10000);

//                            bufReader = new BufferedReader(input);

//                            tmpList.clear();
                            bufReader.skip(10);
                            System.out.println(bufReader.read());
                            line = bufReader.readLine();
                        }

                    }
                    httpConn.disconnect();
                    System.out.println("load end");

                    for(String tmpLine : tmpList){
//                    line = new String(line.getBytes("ISO-8859-1"), "UTF-8");
                        if(tmpLine.trim().startsWith("<h3>")){
                            String title = getResultFromStr(tmpLine, titlePattern, 6, 4);
                            String key = getResultFromStr(tmpLine, keyPattern);
                            if(key != null){
                                key = key.replace("-", "").toLowerCase();
                                topic.setCompany(getResultFromStr(key, companyPattern));
                                topic.setSeries(getResultFromStr(key, seriesPattern));
                            }

                            String url = getResultFromStr(tmpLine, urlPattern, 6, 5);

                            topic.setTitle(title);
                            topic.setKey(key);
                            topic.setUrl(clAddr + url);
                        }

                        if(tmpLine.trim().startsWith("<td class=\"tal y-style\">")){
                            String user = getResultFromStr(tmpLine, userPattern, 5, 4);
                            String date = getResultFromStr(tmpLine, datePattern);

                            if(user != null){
                                topic.setUser(user);
                            }
                            if(date != null){
                                topic.setDate(date);
                            }
                        }

                        if(topic.isComplete()){
                            String edgeA = topic.toKeyEdge();
                            String duplicateKey = topic.toKeyDuplicate();
                            boolean f = edgeA.startsWith("<");
                            if(Cache.MAP.get(forum)!=null && Cache.MAP.get(forum).size()!=0){
                                String edgeB = Cache.MAP.get(forum).get(0).toKeyEdge();
                                if(edgeA.equalsIgnoreCase(edgeB)){
                                    System.out.println("Last one this time : "+Cache.MAP.get(forum).get(Cache.MAP.get(forum).size()-1).toString());
                                    page = 99999;
                                    break;
                                }
                            }
                            if(f){
                                topic = new Topic();
                                continue;
                            }else{
                                if(map.get(duplicateKey)==null){
                                    Cache.MAP.get(forum).add(topic);
                                    map.put(duplicateKey, 1);
                                    c++;
                                    pageC++;
                                    topic = new Topic();
                                }else{
                                    System.out.println("removeDuplicate: "+duplicateKey);
                                }
                            }
                        }
                    }
                    System.out.println("page c : "+pageC);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

//            saveCache(forum);

            int total = Cache.MAP.get(forum)==null ? 0 : Cache.MAP.get(forum).size();
            info += forum + "(Add "+c+" , Total "+total+")   ";

//        System.out.println("Add "+c+" , Total : "+Cache.getAllList().size());
//        System.out.println("    First : "+Cache.getAllList().get(0).toString());
//        System.out.println("    First : "+Cache.getAllList().get(Cache.getAllList().size()-1).toString());
        }

        return info;
    }
    */

    protected static void saveCache(String forum){
        Collections.sort(Cache.MAP.get(forum));
        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(Props.PATH + forum+".cache"));
            out.writeObject(Cache.MAP.get(forum));
            out.flush();
            out.close();
            System.out.println("Save Successful : "+forum);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void init(){
        Props.getParams();

        if(Cache.MAP.size()==0){
            for(String forum : Props.FORUMS){
                try{
                    System.out.println("Load local cache : "+forum);
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream(Props.PATH + forum+".cache"));
                    Cache.MAP.put(forum, (List<Topic>)in.readObject());
                }catch (Exception e){
                    Cache.MAP.put(forum, new ArrayList<Topic>());
                    e.printStackTrace();
                }
            }
        }

    }

    public static List<String> search(String forum, String field, String k){
//        for (Topic topic : Cache.getAllList()){
//            System.out.println(topic.toString());
//        }

        List<String> result = new ArrayList<String>();
        List<Topic> list = Cache.MAP.get(forum);

        String[] kArray = k.split(" ");
        int n = kArray.length;

        for(Topic topic : list){
            String str = null;
            if(field.equalsIgnoreCase("title")){
                str = topic.getTitle();
            }else if(field.equalsIgnoreCase("user")){
                str = topic.getUser();
            }
            List<Boolean> flagList = new ArrayList<Boolean>();
            for(String key : kArray){
                if(str.toUpperCase().contains(key.toUpperCase())){
                    flagList.add(true);
                }
            }

            if(flagList.size() == n){
                result.add(topic.toString());
            }

        }

        return result;
    }

    public static String removeDuplicate(){
        String info = "";
        for(String forum : Props.FORUMS){
            int before = Cache.MAP.get(forum).size();
            Map<String, Boolean> map = new HashMap<String, Boolean>();
            List<Topic> list = new ArrayList<Topic>();
            for(Topic topic : Cache.MAP.get(forum)){
                if(map.get(topic.toKeyDuplicate()) == null){
                    map.put(topic.toKeyDuplicate(), true);
                    list.add(topic);
                }else{
                    System.out.println(topic.toString());
                }
            }

            Cache.MAP.put(forum, list);
            int after = Cache.MAP.get(forum).size();
            int decrease = before - after;

            saveCache(forum);

            info += forum + "(Decrease : " + decrease + ", Total : " + after+")   ";
            System.out.println(forum + " Before : " + before + ", After : "+after + ", Decrease : " + decrease);
        }
        return info;
    }

    public static void test(){
        try {
            String strURL = "http://cl.man.lv/thread0806.php?fid=15&search=&page=1";
            URL url = new URL(strURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            InputStreamReader input = new InputStreamReader(httpConn.getInputStream(), "utf-8");
            BufferedReader bufReader = new BufferedReader(input);
            String line = "";
            StringBuilder contentBuf = new StringBuilder();
            while ((line = bufReader.readLine()) != null) {
                System.out.println(line);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        test();

        init();
//        saveCache(Props.ASIA_MOSAICKED);
        AssisUI ui = new AssisUI();

//        getData("http://cl.man.lv");

//        removeDuplicate();

//        System.out.println(search(Props.ASIA_MOSAICKED, "Title", "").size());
    }

    public static AssisUI ui;
}
