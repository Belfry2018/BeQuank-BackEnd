package com.belfry.bequank.entity.secondary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Mr.Wang
 * @version 2018/10/31
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ZCFB")
public class NewsZCFB implements Serializable {

    @Id
    private String link;

    private String pos, title, date, origin;
}
