package com.example.windowPos.setting.repository;

import com.example.windowPos.setting.entity.EstimatedCookingTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstimatedCookingTimeRepository extends JpaRepository<EstimatedCookingTime, Long> {
}
