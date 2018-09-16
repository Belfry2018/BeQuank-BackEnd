package com.belfry.bequank.serviceImpl;

import com.belfry.bequank.entity.primary.RealStock;
import com.belfry.bequank.entity.primary.Stock;
import com.belfry.bequank.entity.primary.Strategy;
import com.belfry.bequank.entity.primary.StrategyItem;
import com.belfry.bequank.repository.primary.RealStockRepository;
import com.belfry.bequank.repository.primary.StrategyRepository;
import com.belfry.bequank.service.NormalUserService;
import com.belfry.bequank.service.UserService;
import com.belfry.bequank.util.HttpHandler;
import com.belfry.bequank.util.JwtUtil;
import jdk.nashorn.internal.ir.IfNode;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sun.security.x509.CRLDistributionPointsExtension;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
        if (score < 39) {
            res.put("type", "不建议投资");
        }
        if (score >= 39) {
            res.put("type", "保守型");
        }
        if (score >= 46) {
            res.put("type", "中度保守型");
        }
        if (score >= 54) {
            res.put("type", "平稳型");
        }
        if (score >= 62) {
            res.put("type", "中度进取型");
        }
        if (score >= 70) {
            res.put("type", "进取型");
        }

        return res;
    }

    @Override
    public JSONObject recommendByProfit(HttpServletRequest request) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        JSONObject object = new JSONObject();
        object.put("todayBenefit", decimalFormat.format(Math.random() * 30));
        object.put("yearBenefit", decimalFormat.format(Math.random() * 30));
        object.put("risk", decimalFormat.format(Math.random() * 30));
        object.put("stocks", getRecommendation());
        object.put("loopback", getLoopBack());
        logger.info("response = {}", object);
        return object;
    }

    private List<Stock>getRecommendation(){
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        Page<RealStock> list = realStockRepository.findAll(PageRequest.of((int) (Math.random() * 602), 6));
        List<Stock> res = list.stream().map(x -> Stock.transform(x)).collect(Collectors.toList());
        double br = 100.0;
        for (int i = 0; i < res.size()-1; i++) {
            double rate = Double.parseDouble(decimalFormat.format(Math.random() * 16));
            br -= rate;
            res.get(i).setBuyRate(rate);
        }
        res.get(res.size() - 1).setBuyRate(Double.parseDouble(decimalFormat.format(br)));
        return res;
    }

    private List<JSONObject>getLoopBack(){
        double[] sz = new double[]{0.024612, -0.00377, 0.016401, -0.02018, -0.00213, 0.049266, -0.09724, -0.02464, -0.02381, 0.005474, -0.082, 0.011214, -0.0453};
        String[] dates = new String[]{"2017-08-31", "2017-09-29", "2017-10-31", "2017-11-30", "2017-12-29", "2018-01-31", "2018-02-28", "2018-03-30", "2018-04-27", "2018-05-31", "2018-06-29", "2018-07-31", "2018-08-31"};
        List<JSONObject> list = new ArrayList<>();
        for (int i = 0; i < sz.length; i++) {
            JSONObject object = new JSONObject();
            object.put("date", dates[i]);
            object.put("上证指数", sz[i]*100);
            object.put("自选股", (Math.random()*2.4+0.1)*sz[i]*100);
            list.add(object);
        }
        logger.info("loopback = {}", list);
        return list;
    }

    @Override
    public JSONObject recommendByRisk(HttpServletRequest request) {
        return recommendByProfit(request);
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
        return strategyRepository.findByUserId(getUserId(request));
    }

    @Override
    public Strategy getAStrategy(HttpServletRequest request, long strategyId) {
        return strategyRepository.findByRecordId(strategyId);
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
