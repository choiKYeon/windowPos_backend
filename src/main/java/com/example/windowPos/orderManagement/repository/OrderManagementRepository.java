package com.example.windowPos.orderManagement.repository;

import com.example.windowPos.orderManagement.entity.OrderManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderManagementRepository extends JpaRepository<OrderManagement, Long> {

//    주문번호의 최대값을 가져옴
    @Query("SELECT MAX(o.orderNumber) FROM OrderManagement o")
    Long findMaxOrderNumber();
}
