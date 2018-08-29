package com.belfry.bequank.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
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
    private double expectedProfit;
    private double riskAbility;

    public User(String userName, String password, String nickname, String avatar, String phone, String email, String gender, String birthday, String moneyLevel, String bio, double expectedProfit, double riskAbility) {
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
        this.expectedProfit = expectedProfit;
        this.riskAbility = riskAbility;
    }
}
