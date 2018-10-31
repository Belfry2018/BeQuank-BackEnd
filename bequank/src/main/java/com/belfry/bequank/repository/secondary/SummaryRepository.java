package com.belfry.bequank.repository.secondary;

import com.belfry.bequank.entity.secondary.Summary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Mr.Wang
 * @version 2018/9/6
 */
@Repository
public interface SummaryRepository extends JpaRepository<Summary, String>, PagingAndSortingRepository<Summary, String> {

//    @Query("select date from Summary j where j.date like :date%")
//    List<Summary> findByDate(@Param("date") String date);
    List<Summary> findByDateStartingWith(String date);

    List<Summary> findByDateBetween(String from, String to);
}
