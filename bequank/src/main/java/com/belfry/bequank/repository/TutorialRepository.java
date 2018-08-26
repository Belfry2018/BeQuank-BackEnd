package com.belfry.bequank.repository;

import com.belfry.bequank.entity.Tutorial;
import com.belfry.bequank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Yang Yuqing
 * @Description:
 * @Date: Created in 6:02 PM 8/15/18
 * @Modifiedby:
 */
@Repository

public interface TutorialRepository  extends JpaRepository<Tutorial, Long> {
    @Query("select t.id,t.title from Tutorial t ")
    Map<Long,String> getIdTitles();
    @Query("select t from Tutorial t")
    List<Tutorial> getAll();
    @Query("select t from Tutorial t where t.id=:id")
    Tutorial getOne(@Param("id")Long id);
    @Query("select t from Tutorial t where t.title=:title")
    Tutorial findByTitle(@Param("title")String title);
}