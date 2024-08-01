package com.example.windowPos.setting.repository;

import com.example.windowPos.setting.entity.TemporaryHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemporaryHolidayRepository extends JpaRepository<TemporaryHoliday, Long> {
}
