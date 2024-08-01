package com.example.windowPos.setting.repository;

import com.example.windowPos.setting.entity.ClosedDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClosedDaysRepository extends JpaRepository<ClosedDays, Long> {
}
