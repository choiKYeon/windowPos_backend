package com.example.windowPos.orderManagement.service;

import com.example.windowPos.orderManagement.entity.OrderManagement;
import com.example.windowPos.orderManagement.repository.OrderManagementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OperationalStatusService {
    private final OrderManagementRepository orderManagementRepository;

    @Transactional
    public void setOperationalStatus(Long id, Boolean status) {
        OrderManagement orderManagement = orderManagementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("주문 관리 객체를 찾을 수 없습니다."));

        orderManagement.setOperate(status);
        orderManagementRepository.save(orderManagement);
    }
}