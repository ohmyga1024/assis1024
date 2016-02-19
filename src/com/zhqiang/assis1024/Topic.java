package com.zhqiang.assis1024;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by zhqiang on 14-3-13.
 */
public class Topic implements Serializable, Comparable<Topic> {

    private static final long serialVersionUID = -6048717489036876834L;

    private String title;
    private String user;
    private String date;
    private String company;
    private String series;
    private String key;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isComplete(){
        boolean flag = false;
        if(title != null && user != null && date != null && url!= null){
            flag = true;
        }
        return flag;
    }

    public String toString(){
        return title +", "+ user +", "+ date +", "+url; //+ ", "+ key + ", "+ company + ", " + series;
    }

    public String toKeyEdge(){
        return title +", "+ user; //+ ", "+ key + ", "+ company + ", " + series;
    }

    public String toKeyDuplicate(){
        return title +", "+url;
    }

    @Override
    public int compareTo(Topic t) {
        return t.getDate().compareTo(this.getDate());
    }
}
