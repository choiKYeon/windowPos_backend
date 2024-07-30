package com.example.windowPos.orderManagement.controller;

import com.example.windowPos.orderManagement.service.OrderManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/order")
public class OrderManagementController {
    private final OrderManagementService orderManagementService;
}
