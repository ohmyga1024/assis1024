package com.zhqiang.assis1024;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shaoer on 14-3-19.
 */
public class Assistant_Jsoup implements IAssistant{

    public static final String AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.79";

    @Override
    public String getData(String clAddr) {
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

            for(int page=0; page<100; page++){
                int pageC = 0;
                Map<String, Integer> map = new HashMap<String, Integer>();

                String pageUrl = clAddr + strURL + page;
                System.out.println("开始抓取："+pageUrl);
                Document doc = null;
                try {
                    System.out.println("page = "+page);
                    AssisUI.lInfo.setText(forum +" : "+"page "+page+" loading...");
//                    new UseProxy();
                    Connection con = Jsoup.connect(pageUrl)
                            .header("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")
                            .timeout(10000);
//                    con.userAgent(AGENT);
                    doc = con.get();

//                    doc = Jsoup.parse(new File(Props.PATH + "cl.html"), "UTF-8", "");
                    Element body = doc.body();
                    Elements trs = body.getElementsByClass("tr3");
                    for(Element tr : trs){
                        if(tr.hasClass("t_one")){
                            Topic topic = new Topic();

                            Element a_title = tr.child(1).getElementsByTag("a").get(0);
                            topic.setTitle(a_title.html());
                            if(topic.getTitle().trim().startsWith("<") && topic.getTitle().trim().endsWith(">")){
                                continue;
                            }
                            topic.setUrl(clAddr + a_title.attr("href"));

                            Element a_user = tr.child(2).getElementsByTag("a").get(0);
                            topic.setUser(a_user.html());
                            if(topic.getUser().trim().equalsIgnoreCase("administrator")){
                                continue;
                            }
                            Element a_date = tr.child(2).getElementsByTag("div").get(0);
                            topic.setDate(a_date.html().trim());
                            topic.setKey(Assistant.getResultFromStr(topic.getTitle(), Assistant.keyPattern));
                            topic.setCompany(Assistant.getResultFromStr(topic.getKey(), Assistant.companyPattern));
                            topic.setSeries(Assistant.getResultFromStr(topic.getKey(), Assistant.seriesPattern));

                            System.out.println(topic.toString());

                            String edgeA = topic.toKeyEdge();
                            String duplicateKey = topic.toKeyDuplicate();
                            if(Cache.MAP.get(forum)!=null && Cache.MAP.get(forum).size()!=0){
                                String edgeB = Cache.MAP.get(forum).get(0).toKeyEdge();
                                if(edgeA.equalsIgnoreCase(edgeB)){
                                    System.out.println("Last one this time : "+Cache.MAP.get(forum).get(Cache.MAP.get(forum).size()-1).toString());
                                    page = 99999;
                                    break;
                                }
                            }

                            if(map.get(duplicateKey)==null){
                                Cache.MAP.get(forum).add(topic);
                                map.put(duplicateKey, 1);
                                c++;
                                pageC++;
                            }else{
                                System.out.println("removeDuplicate: "+duplicateKey);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    page--;
                }
            }

            Assistant.saveCache(forum);

            int total = Cache.MAP.get(forum)==null ? 0 : Cache.MAP.get(forum).size();
            info += forum + "(Add "+c+" , Total "+total+")   ";

        }

        return info;
    }

    @Override
    public List<String> search(String forum, String field, String k) {
        return null;
    }

    @Override
    public String removeDuplicate() {
        return null;
    }

    public static void main(String[] args) {
        Assistant.init();
        IAssistant assis = new Assistant_Jsoup();
        assis.getData("http://cl.man.lv");
    }
}
