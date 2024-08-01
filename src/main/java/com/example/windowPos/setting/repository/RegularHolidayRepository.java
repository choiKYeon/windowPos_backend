package com.example.windowPos.setting.repository;

import com.example.windowPos.setting.entity.RegularHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegularHolidayRepository extends JpaRepository<RegularHoliday, Long> {
}
