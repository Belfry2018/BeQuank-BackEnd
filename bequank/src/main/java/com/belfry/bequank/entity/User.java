package com.belfry.bequank.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private long id;

    private String userName,password, nickName,role;

    public User(String userName, String password, String nickName, String role) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.role = role;
    }
}
