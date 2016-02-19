package com.zhqiang.assis1024;

import java.util.List;

/**
 * Created by shaoer on 14-3-19.
 */
public interface IAssistant {
    public String getData(String clAddr);

    public List<String> search(String forum, String field, String k);

    public String removeDuplicate();


}
