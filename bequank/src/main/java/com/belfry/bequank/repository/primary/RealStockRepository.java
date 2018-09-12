package com.belfry.bequank.repository.primary;

import com.belfry.bequank.entity.primary.RealStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface RealStockRepository extends JpaRepository<RealStock, String> {

}
