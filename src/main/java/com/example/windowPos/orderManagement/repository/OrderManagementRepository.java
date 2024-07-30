package com.example.windowPos.orderManagement.repository;

import com.example.windowPos.orderManagement.entity.OrderManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderManagementRepository extends JpaRepository<OrderManagement, Long> {

}
