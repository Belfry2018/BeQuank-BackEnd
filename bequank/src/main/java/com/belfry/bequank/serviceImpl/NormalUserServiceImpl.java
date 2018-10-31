package com.belfry.bequank.serviceImpl;

import com.belfry.bequank.entity.primary.*;
import com.belfry.bequank.repository.primary.RealStockRepository;
import com.belfry.bequank.repository.primary.StrategyRepository;
import com.belfry.bequank.repository.primary.UserRepository;
import com.belfry.bequank.service.NormalUserService;
import com.belfry.bequank.service.UserService;
import com.belfry.bequank.util.HttpHandler;
import com.belfry.bequank.util.JwtUtil;
import jdk.nashorn.internal.ir.IfNode;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sun.security.x509.CRLDistributionPointsExtension;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Yang Yuqing
 * @Description:
 * @Date: Created in 10:05 PM 8/17/18
 * @Modifiedby:
 */

@Service
public class NormalUserServiceImpl implements NormalUserService {
    @Autowired
    UserService userService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    StrategyRepository strategyRepository;
    @Autowired
    HttpHandler httpHandler;
    @Autowired
    RealStockRepository realStockRepository;
    @Value("${belfry.header_string}")
    String HEADER;
    @Autowired
    UserRepository userRepository;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private int[][]points={{1,4,7,8,9},{2,4,6,7,9},{9,7,3,1,0},{2,6,8,9,0},
                                {1,4,8,9,0},{1,3,7,9,0},{1,3,7,9,0},{3,6,7,9,0},
                                {3,5,7,9,0},{1, 3, 5, 7, 0}};

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public JSONObject getQuestionnairResult(HttpServletRequest request, JSONObject answer) {
        JSONObject res = new JSONObject();
        int score = 0;
        for (int i = 1; i < 11; i++) {
            char op = answer.getString(Integer.toString(i)).toUpperCase().charAt(0);
            score += points[i - 1][op - 'A'];
        }
        res.put("score", score);
        String type = null;
        if (score < 39) {
            type = "不建议投资";
        }
        if (score >= 39) {
            type = "保守型";
        }
        if (score >= 46) {
            type = "中度保守型";
        }
        if (score >= 54) {
            type = "平稳型";
        }
        if (score >= 62) {
            type = "中度进取型";
        }
        if (score >= 70) {
            type = "进取型";
        }
        res.put("type", type);
        int userid = (int) jwtUtil.parseToken(request.getHeader(HEADER)).get("userId");
        userRepository.updateLevel(userid, type);
        return res;
    }

    @Override
    public String recommendByProfit(HttpServletRequest request) throws IOException {
        int userid = (int) jwtUtil.parseToken(request.getHeader(HEADER)).get("userId");
        User user = userRepository.getById((long) userid);
        if (user.getLevel() == null || user.getLevel().equals("")) {
            return "{\"stocks\":[]}";
        }
        String object = httpHandler.recommendByProfit();
        return object;
    }

    @Override
    public String recommendByRisk(HttpServletRequest request) throws IOException {
        int userid = (int) jwtUtil.parseToken(request.getHeader(HEADER)).get("userId");
        User user = userRepository.getById((long) userid);
        if (user.getLevel() == null || user.getLevel().equals("")) {
            return "{\"stocks\":[]}";
        }
        return httpHandler.recommendByRisk();
    }

    @Override
    public JSONObject addStrategy(HttpServletRequest request, JSONObject object) {
        JSONObject res = new JSONObject();
        Strategy strategy = new Strategy();

        strategy.setRecordName(object.getString("recordName"));
        strategy.setUserId(getUserId(request));
        strategy.setRecordTime(dateFormat.format(new Date()));

        List<JSONObject > mapList = object.getJSONArray("stocks");
        for (JSONObject jsonObject : mapList) {
            StrategyItem item = new StrategyItem(jsonObject.getString("stockId"), jsonObject.getDouble("buyRate"), strategy);
            strategy.add(item);
        }

        long id = strategyRepository.saveAndFlush(strategy).getRecordId();

        res.put("recordId", id);
        res.put("recordTime", strategy.getRecordTime());
        return res;
    }

    @Override
    public void deleteStrategy(HttpServletRequest request, long strategyId) {
        strategyRepository.deleteById(strategyId);
    }

    @Override
    public List<Strategy> getStrategies(HttpServletRequest request) {
        List<Strategy>res=strategyRepository.findByUserId(getUserId(request));
        res.stream().forEach(x -> x.getStocks().stream().forEach(y -> y.setStrategy(null)));
        return res;
    }

    @Override
    public String getAStrategy(HttpServletRequest request, long strategyId) throws MalformedURLException {
//        原来是java直接从数据库获取自选股信息，现在交给python

//        Strategy strategy = strategyRepository.findByRecordId(strategyId);
//        strategy.setStocks(strategy.getStocks().stream().map(x -> new StrategyVO(x, realStockRepository.getOne(x.getStockId()))).collect(Collectors.toList()));
//        return strategy;
        return httpHandler.getAStrategy(strategyId);
    }

    @Override
    public String getAStock(HttpServletRequest request, String code) throws IOException {
        return httpHandler.getAStock("http://127.0.0.1:5000/stock/" + code);
    }

    @Override
    public Page<RealStock> getStocks(HttpServletRequest request, int page) {
        return realStockRepository.findAll(PageRequest.of(page, 20));
    }

    private long getUserId(HttpServletRequest request) {
        Map<String, Object> map = jwtUtil.parseToken(request.getHeader("Authorization"));
        return Integer.toUnsignedLong((int) map.get("userId"));
    }
}
