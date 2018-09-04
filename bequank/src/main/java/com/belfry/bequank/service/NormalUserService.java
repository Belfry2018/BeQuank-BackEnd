package com.belfry.bequank.service;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * 普通用户api
 */
public interface NormalUserService {

    public JSONObject getQuestionnairResult(HttpServletRequest request, JSONObject answer);
}
