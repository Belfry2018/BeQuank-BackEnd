package com.belfry.bequank.repository.secondary;

import com.belfry.bequank.entity.secondary.Summary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Mr.Wang
 * @version 2018/9/6
 */
@Repository
public interface SummaryRepository extends JpaRepository<Summary, String>, PagingAndSortingRepository<Summary, String> {

//    @Query("select date from Summary j where j.date like :date%")
//    List<Summary> findByDate(@Param("date") String date);
    @Query("select s from Summary s where s.date >=:date")
    Page<Summary> findByDateStartingWith(Pageable pageable, @Param("date") String date);

    @Query("select s from Summary s where s.date >=:fromStr and s.date <=:toStr")
    Page<Summary> findByDateBetween(Pageable pageable, @Param("fromStr") String from, @Param("toStr") String to);

//    @Query("select s from Summary s where s.date >=:fromStr and s.date <=:toStr and s.pos =:region and s.origin")
//    List<Summary> findComprehensive();
}
