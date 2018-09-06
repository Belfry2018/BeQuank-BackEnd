package com.belfry.bequank.repository.primary;

import com.belfry.bequank.entity.primary.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
}
