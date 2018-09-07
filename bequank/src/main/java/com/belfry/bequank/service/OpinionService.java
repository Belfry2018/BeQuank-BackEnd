package com.belfry.bequank.service;

import net.sf.json.JSONArray;

/**
 * 舆情
 * @author Mr.Wang
 * @version 2018/9/7
 */
public interface OpinionService {

    JSONArray getArticlesByPages(int page);

    JSONArray getGvnHotWords();
}
