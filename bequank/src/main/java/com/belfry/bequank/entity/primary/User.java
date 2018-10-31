package com.belfry.bequank.entity.primary;

import com.belfry.bequank.util.TutorialType;
import lombok.*;
import net.sf.json.JSONObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String userName;//用户名
    private String password;//密码
    private String nickname;//昵称
    private String avatar;//头像url
    private String phone;//电话
    private String email;//邮箱
    private String gender;//性别
    private String birthday;//生日
    private String moneyLevel;//……
    private String bio;//自我介绍
    private String level;// + 通过做问卷得出的结果
    private String role;//权限
    private String registerTime;//注册时间
    private double expectedProfit;//预期收益
    private double riskAbility;//风险承受能力
    private int coins;
    private int exp;
    private boolean hasSignedToday;//今天是否签到
    private String tutorialType;//可以查看的教程级别
    private boolean ratioTrend;
    private boolean trend;

    public User(String userName, String password, String nickname, String avatar, String phone, String email, String gender, String birthday, String moneyLevel, String bio, String level, String role, String registerTime, double expectedProfit, double riskAbility, int coins, int exp, boolean hasSignedToday, String tutorialType, boolean ratioTrend, boolean trend) {
        this.userName = userName;
        this.password = password;
        this.nickname = nickname;
        this.avatar = avatar;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.birthday = birthday;
        this.moneyLevel = moneyLevel;
        this.bio = bio;
        this.level = level;
        this.role = role;
        this.registerTime = registerTime;
        this.expectedProfit = expectedProfit;
        this.riskAbility = riskAbility;
        this.coins = coins;
        this.exp = exp;
        this.hasSignedToday = hasSignedToday;
        this.tutorialType = tutorialType;
        this.ratioTrend = ratioTrend;
        this.trend = trend;
    }

    public JSONObject toJSONObject(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("username",this.userName);
        jsonObject.put("avatar",this.avatar);
        jsonObject.put("nickname",this.nickname);
        return jsonObject;
    }
}
