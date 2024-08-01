package com.example.windowPos.orderManagement.repository;

import com.example.windowPos.orderManagement.entity.OrderUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderUpdateRepository extends JpaRepository<OrderUpdate, Long> {
}
