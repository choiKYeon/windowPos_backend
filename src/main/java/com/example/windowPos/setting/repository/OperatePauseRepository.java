package com.example.windowPos.setting.repository;

import com.example.windowPos.setting.entity.OperatePause;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

@Repository
public interface OperatePauseRepository extends JpaRepository<OperatePause, Long> {

    @Query("SELECT sp FROM OperatePause sp WHERE sp.operateStatus = 'START' AND :currentTime BETWEEN sp.salesPauseStartTime AND sp.salesPauseEndTime")
    OperatePause findCurrentSalesPause(@Param("currentTime") LocalTime currentTime);

}
