package com.example.windowPos.orderManagement.repository;

import com.example.windowPos.orderManagement.entity.SalesPause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesPauseRepository extends JpaRepository<SalesPause, Long> {
}
