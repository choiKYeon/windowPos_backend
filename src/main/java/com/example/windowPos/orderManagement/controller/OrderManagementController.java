package com.example.windowPos.orderManagement.controller;

import com.example.windowPos.orderManagement.service.OrderManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class OrderManagementController {
    private final OrderManagementService orderManagementService;
}
