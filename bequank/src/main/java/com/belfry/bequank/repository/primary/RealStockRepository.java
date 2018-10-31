package com.belfry.bequank.repository.primary;

import com.belfry.bequank.entity.primary.RealStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface RealStockRepository extends JpaRepository<RealStock, String> {
    
    Page<RealStock> findAllByStockIdContainingOrStockNameContaining(@Param("pt1") String pattern1, @Param("pt2") String pattern2, Pageable pageable);
    
}
