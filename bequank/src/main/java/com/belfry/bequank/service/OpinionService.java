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
     * @param page 页号
     * @return net.sf.json.JSONArray
     */
    JSONObject getHotSpots(int page);

    /**
     * 随机展示三个词的舆情
     * @author Mr.Wang
     * @param () null
     * @return net.sf.json.JSONArray
     */
    JSONArray getSentiment();

    /**
     * 展示一个关键词的舆情走势
     * @author Mr.Wang
     * @param word text
     * @return net.sf.json.JSONArray
     */
    JSONArray getSentimentTrend(String word);

    /**
     * 展示一个词好中坏的评论次数
     *
     * @param word
     * @return net.sf.json.JSONObject
     * @author andi
     */
    JSONObject getCommentsInSenti(String word);

    /**
     * 展示一个词好中坏评论次数的舆情走势
     *
     * @param word
     * @return net.sf.json.JSONArray
     * @author andi
     */
    JSONArray getCommentsInSentiTrend(String word);
}
