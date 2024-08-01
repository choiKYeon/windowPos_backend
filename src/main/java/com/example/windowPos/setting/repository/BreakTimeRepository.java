package com.example.windowPos.setting.repository;

import com.example.windowPos.setting.entity.BreakTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreakTimeRepository extends JpaRepository<BreakTime, Long> {
}
