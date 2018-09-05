package com.belfry.bequank.repository.primary;

import com.belfry.bequank.entity.primary.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StrategyRepository extends JpaRepository<Strategy, Long> {

    List<Strategy> findByUserId(long userId);

    Strategy findByRecordId(long recordId);
}
