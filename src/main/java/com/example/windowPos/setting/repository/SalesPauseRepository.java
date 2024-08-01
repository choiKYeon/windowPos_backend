package com.example.windowPos.setting.repository;

import com.example.windowPos.setting.entity.OperatePause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesPauseRepository extends JpaRepository<OperatePause, Long> {

    @Query("SELECT sp FROM OperatePause sp WHERE sp.salesStatus = 'START' AND CURRENT_TIMESTAMP BETWEEN sp.salesPauseStartTime AND sp.salesPauseEndTime")
    OperatePause findCurrentSalesPause();
}
