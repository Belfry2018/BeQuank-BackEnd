package com.belfry.bequank.repository.secondary;

import com.belfry.bequank.entity.secondary.HYPX;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Mr.Wang
 * @version 2018/10/31
 */
@Repository
public interface HYPXRepository extends JpaRepository<HYPX, String>, PagingAndSortingRepository<HYPX, String> {

    @Query("select n from HYPX n where n.date >=:fromStr and n.date <=:toStr and n.pos =:region")
    Page<HYPX> findComprehensive(Pageable pageable, @Param("fromStr") String from, @Param("toStr") String to, @Param("region") String region);
}
