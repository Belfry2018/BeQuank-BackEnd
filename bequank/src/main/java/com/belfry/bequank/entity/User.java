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

    private String userName,password, nickName,role;

    public User(String userName, String password, String nickName, String role) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.role = role;
    }
}
