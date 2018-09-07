package com.belfry.bequank.repository.secondary;

import com.belfry.bequank.entity.secondary.Jbw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Mr.Wang
 * @version 2018/9/6
 */
@Repository
public interface JbwRepository extends JpaRepository<Jbw, String>, PagingAndSortingRepository<Jbw, String> {

//    @Query("select date from Jbw j where j.date like :date%")
//    List<Jbw> findByDate(@Param("date") String date);
    List<Jbw> findByDateStartingWith(String date);

}
