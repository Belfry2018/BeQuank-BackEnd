package com.belfry.bequank.controller;

import com.belfry.bequank.service.OpinionService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mr.Wang
 * @version 2018/9/7
 */
@RestController
@RequestMapping("/api/v1")
public class OpinionController {

    @Autowired
    OpinionService opinionService;

    /**
     * 政府热点词汇
     * @author Mr.Wang
     * @param () null
     * @return net.sf.json.JSONArray
     */
    @GetMapping(value = "/gvn/words")
    public JSONArray getGvnHotWords() {
        return opinionService.getGvnHotWords();
    }

    /**
     * 查看某一页（8篇）的政府文章
     * @author Mr.Wang
     * @param object JSONObject
     * @return net.sf.json.JSONObject
     */
    @PostMapping(value = "/gvn/passage")
    public JSONObject findArticlesByPages( @RequestBody JSONObject object) {
        return new JSONObject();
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
    public JSONObject getHotSpots(@RequestBody JSONObject jsonObject) {
        return opinionService.getHotSpots(
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
    public JSONArray getSentimentTrend( @RequestBody JSONObject jsonObject) {
        return opinionService.getSentimentTrend(
                jsonObject.getString("word")
        );
    }

    /**
     * 展示一个词好中坏的评论次数
     *
     * @author andi
     */
    @GetMapping(value = "/sentiment/ratio/{word}")
    public JSONObject getCommentsInSenti( @PathVariable String word) {
        return opinionService.getCommentsInSenti(word);
    }

    /**
     * 展示一个词好中坏的评论次数的舆情走势
     *
     * @author andi
     */
    @GetMapping(value = "/sentiment/ratioTrend/{word}")
    public JSONArray getCommentsInSentiTrend( @PathVariable String word) {
        return opinionService.getCommentsInSentiTrend(word);
    }


}
