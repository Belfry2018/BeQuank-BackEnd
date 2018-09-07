package com.belfry.bequank.entity.secondary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

/**
 * 热词实体类
 * @author Mr.Wang
 * @version 2018/9/7
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@IdClass(Word_tf_PK.class)
public class Word_tf implements Serializable {

    @Id
    private String word;
    @Id
    private String month;

    private int tf;

}
