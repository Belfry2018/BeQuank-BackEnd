package com.belfry.bequank.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 舆情
 * @author Mr.Wang
 * @version 2018/9/7
 */
public interface OpinionService {

    /**
     * 根据页数得到8篇文章
     * @author Mr.Wang
     * @param page 页号
     * @return net.sf.json.JSONObject
     */
    JSONObject getArticlesByPages(int page);

    /**
     * 政府热点词汇
     * @author Mr.Wang
     * @param () null
     * @return net.sf.json.JSONArray
     */
    JSONArray getGvnHotWords();

    /**
     * 获得关键词云
     * @author Mr.Wang
     * @param () null
     * @return net.sf.json.JSONArray
     */
    JSONArray getKeywords();

    /**
     * 微博热点
     * @author Mr.Wang
     * @param username 用户名
     * @param page 页号
     * @return net.sf.json.JSONArray
     */
    JSONObject getHotSpots(String username, int page);

    /**
     * 随机展示三个词的舆情
     * @author Mr.Wang
     * @param () null
     * @return net.sf.json.JSONArray
     */
    JSONArray getSentiment();
}
