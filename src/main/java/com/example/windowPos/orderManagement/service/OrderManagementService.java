package com.example.windowPos.orderManagement.service;

import com.example.windowPos.orderManagement.repository.OrderManagementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderManagementService {
    private final OrderManagementRepository orderManagementRepository;
}
