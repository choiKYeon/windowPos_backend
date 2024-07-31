package com.example.windowPos.setting.repository;

import com.example.windowPos.setting.entity.SalesPause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesPauseRepository extends JpaRepository<SalesPause, Long> {

    @Query("SELECT sp FROM SalesPause sp WHERE sp.salesStatus = 'START' AND CURRENT_TIMESTAMP BETWEEN sp.salesPauseStartTime AND sp.salesPauseEndTime")
    SalesPause findCurrentSalesPause();
}
