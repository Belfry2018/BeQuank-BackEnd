package com.belfry.bequank.entity.secondary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 热词复合主键类
 * @author Mr.Wang
 * @version 2018/9/7
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Word_tf_PK implements Serializable {

    private String word;
    private String month;

    //重写hashCode()和equals()方法

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((word == null) ? 0 : word.hashCode());
        result = PRIME * result + ((month == null) ? 0 : month.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final Word_tf_PK other = (Word_tf_PK)obj;
        if (word == null) {
            if (other.word != null) {
                return false;
            }
        } else if (!word.equals(other.word)) {
            return false;
        }

        if (month == null) {
            if (other.month != null) {
                return false;
            }
        } else if (!month.equals(other.month)) {
            return false;
        }

        return true;
    }
}
