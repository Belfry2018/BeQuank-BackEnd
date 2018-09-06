package com.belfry.bequank.serviceImpl;

import com.belfry.bequank.entity.primary.Strategy;
import com.belfry.bequank.repository.primary.StrategyRepository;
import com.belfry.bequank.service.NormalUserService;
import com.belfry.bequank.service.UserService;
import com.belfry.bequank.util.JwtUtil;
import jdk.nashorn.internal.ir.IfNode;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.x509.CRLDistributionPointsExtension;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private int[][]points={{1,4,7,8,9},{2,4,6,7,9},{9,7,3,1,0},{2,6,8,9,0},
                                {1,4,8,9,0},{1,3,7,9,0},{1,3,7,9,0},{3,6,7,9,0},
                                {3,5,7,9,0},{1, 3, 5, 7, 0}};

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
    public JSONObject addStrategy(HttpServletRequest request, Strategy strategy) {
        JSONObject res = new JSONObject();

        strategy.setUserId(getUserId(request));
        strategy.setRecordTime(dateFormat.format(new Date()));
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

    private long getUserId(HttpServletRequest request) {
        Map<String, Object> map = jwtUtil.parseToken(request.getHeader("Authorization"));
        return ((long) map.get("userId"));
    }
}
