package com.example.windowPos.setting.repository;

import com.example.windowPos.setting.entity.SalesPause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesPauseRepository extends JpaRepository<SalesPause, Long> {
}
