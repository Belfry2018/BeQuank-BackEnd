package com.belfry.bequank.controller;

import com.belfry.bequank.service.OpinionService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Mr.Wang
 * @version 2018/9/7
 */
@RestController
public class OpinionController {

    @Autowired
    OpinionService opinionService;

    /**
     * 根据页数得到8篇文章
     * @author Mr.Wang
     * @param page 页号
     * @return net.sf.json.JSONObject
     */
    @GetMapping(value = "/gvn/passage/{page}")
    public JSONObject findArticlesByPages(@PathVariable int page) {
        return opinionService.getArticlesByPages(page);
    }

    /**
     * 获得关键词云
     * @return net.sf.json.JSONArray
     * @author Mr.Wang
     */
    @GetMapping(value = "/keywords")
    public JSONArray getKeywords() {
        return opinionService.getKeywords();
    }

    /**
     * 微博热点
     * @author Mr.Wang
     * @return net.sf.json.JSONArray
     */
    @PostMapping(value = "/hotspot")
    public JSONObject getHotSpots(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
        return opinionService.getHotSpots(
                jsonObject.getString("userName"),
                jsonObject.getInt("page")
        );
    }

    /**
     * 随机展示三个词的舆情
     * @author Mr.Wang
     * @param () null
     * @return net.sf.json.JSONArray
     */
    @GetMapping(value = "/sentiment")
    public JSONArray getSentiment() {
        return opinionService.getSentiment();
    }

    /**
     * 展示一个关键词的舆情走势
     * @author Mr.Wang
     * @return net.sf.json.JSONArray
     */
    @PostMapping(value = "/sentiment/trend")
    public JSONArray getSentimentTrend(HttpServlet request, @RequestBody JSONObject jsonObject) {
        return opinionService.getSentimentTrend(
                jsonObject.getString("word")
        );
    }
}
