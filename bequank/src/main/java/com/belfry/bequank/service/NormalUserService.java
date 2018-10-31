package com.belfry.bequank.service;

import com.belfry.bequank.entity.primary.RealStock;
import com.belfry.bequank.entity.primary.Strategy;
import com.belfry.bequank.util.HttpHandler;
import net.sf.json.JSONObject;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * 普通用户api
 */
public interface NormalUserService {

    JSONObject getQuestionnairResult(HttpServletRequest request, JSONObject answer);

    String recommendByProfit(HttpServletRequest request) throws IOException;

    String recommendByRisk(HttpServletRequest request) throws IOException;

    JSONObject addStrategy(HttpServletRequest request, JSONObject object);

    void deleteStrategy(HttpServletRequest request, long strategyId);

    List<Strategy> getStrategies(HttpServletRequest request);

    String getAStrategy(HttpServletRequest request, long strategyId) throws MalformedURLException;

    String getAStock(HttpServletRequest request, String code) throws IOException;

    Page<RealStock> getStocks(HttpServletRequest request, String pattern, int page);
}
