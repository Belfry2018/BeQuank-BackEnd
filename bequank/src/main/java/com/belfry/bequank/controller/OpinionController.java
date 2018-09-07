package com.belfry.bequank.controller;

import com.belfry.bequank.service.OpinionService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mr.Wang
 * @version 2018/9/7
 */
@RestController
public class OpinionController {

    @Autowired
    OpinionService opinionService;

    @GetMapping(value = "/gvn/passage/{page}")
    public JSONArray findArticlesByPages(@PathVariable int page) {
        return opinionService.getArticlesByPages(page);
    }
}
