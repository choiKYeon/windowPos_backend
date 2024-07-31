package com.example.windowPos.setting.repository;

import com.example.windowPos.setting.entity.EstimatedArrivalTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstimatedArrivalTimeRepository extends JpaRepository<EstimatedArrivalTime, Long> {
}
