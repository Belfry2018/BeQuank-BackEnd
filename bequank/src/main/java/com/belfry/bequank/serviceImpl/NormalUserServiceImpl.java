package com.belfry.bequank.serviceImpl;

import com.belfry.bequank.service.NormalUserService;
import com.belfry.bequank.service.UserService;
import jdk.nashorn.internal.ir.IfNode;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.x509.CRLDistributionPointsExtension;

import javax.servlet.http.HttpServletRequest;

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

    private int[][]points={{1,4,7,8,9},{2,4,6,7,9},{9,7,3,1,0},{2,6,8,9,0},
                                {1,4,8,9,0},{1,3,7,9,0},{1,3,7,9,0},{3,6,7,9,0},
                                {3,5,7,9,0},{1, 3, 5, 7, 0}};

    @Override
    public JSONObject getQuestionnairResult(HttpServletRequest request, JSONObject answer) {
        JSONObject res = new JSONObject();
        int score = 0;
        for (int i = 1; i < 11; i++) {
            char op = answer.getString(Integer.toString(i)).charAt(0);
            score += points[i - 1][op - 'A'];
        }
        res.put("score", score);
        if (score >= 70) {
            res.put("type", "进取型");
        }
        if (score >= 62) {
            res.put("type", "中度进取型");
        }
        if (score >= 54) {
            res.put("type", "平稳型");
        }
        if (score >= 46) {
            res.put("type", "中度保守型");
        }
        if (score >= 39) {
            res.put("type", "保守型");
        } else {
            res.put("type", "不建议投资");
        }

        return res;
    }
}
