package com.belfry.bequank.entity.secondary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 政府网站数据
 * @author Mr.Wang
 * @version 2018/9/6
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Summary implements Serializable {

    @Id
    private String link;

    private String pos, title, date, origin, type;

}
