package com.belfry.bequank.service;

import com.belfry.bequank.entity.primary.Strategy;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 普通用户api
 */
public interface NormalUserService {

    public JSONObject getQuestionnairResult(HttpServletRequest request, JSONObject answer);

    public JSONObject addStrategy(HttpServletRequest request, Strategy strategy);

    public void deleteStrategy(HttpServletRequest request, long strategyId);

    public List<Strategy> getStrategies(HttpServletRequest request);

    public Strategy getAStrategy(HttpServletRequest request, long strategyId);
}
