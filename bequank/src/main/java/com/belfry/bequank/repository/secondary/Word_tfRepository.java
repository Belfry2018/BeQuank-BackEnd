package com.belfry.bequank.repository.secondary;

import com.belfry.bequank.entity.secondary.Word_tf;
import com.belfry.bequank.entity.secondary.Word_tf_PK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author Mr.Wang
 * @version 2018/9/7
 */
@Repository
public interface Word_tfRepository extends JpaRepository<Word_tf, Word_tf_PK> {

    List<Word_tf> findByWord(String word);
}
