package com.belfry.bequank.service;

import com.belfry.bequank.entity.primary.RealStock;
import com.belfry.bequank.entity.primary.Strategy;
import com.belfry.bequank.util.HttpHandler;
import net.sf.json.JSONObject;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 普通用户api
 */
public interface NormalUserService {

    JSONObject getQuestionnairResult(HttpServletRequest request, JSONObject answer);

    JSONObject recommendByProfit(HttpServletRequest request);

    JSONObject recommendByRisk(HttpServletRequest request);

    JSONObject addStrategy(HttpServletRequest request, JSONObject object);

    void deleteStrategy(HttpServletRequest request, long strategyId);

    List<Strategy> getStrategies(HttpServletRequest request);

    Strategy getAStrategy(HttpServletRequest request, long strategyId);

    String getAStock(HttpServletRequest request, String code) throws IOException;

    Page<RealStock> getStocks(HttpServletRequest request, int page);
}
