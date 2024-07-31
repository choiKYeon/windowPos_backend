package com.example.windowPos.orderManagement.controller;

import com.example.windowPos.global.rs.RsData;
import com.example.windowPos.global.websocketHandler.WebSoketMessageHandler;
import com.example.windowPos.orderManagement.dto.DtoConverter;
import com.example.windowPos.orderManagement.dto.OrderManagementDto;
import com.example.windowPos.orderManagement.dto.OrderUpdateRequest;
import com.example.windowPos.orderManagement.entity.OrderManagement;
import com.example.windowPos.orderManagement.repository.OrderManagementRepository;
import com.example.windowPos.orderManagement.service.OrderManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/orders")
public class OrderManagementController {
    private final OrderManagementService orderManagementService;
    private final OrderManagementRepository orderManagementRepository;
    private final WebSoketMessageHandler webSoketMessageHandler;

//    주문 받는 구문
    @PostMapping(value = "/create", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<RsData<OrderManagementDto>> createOrder(@RequestBody OrderManagementDto orderManagementDto) {
        OrderManagement orderManagement = orderManagementService.createOrder(orderManagementDto);
        OrderManagementDto orderManagementDto1 = DtoConverter.convertToDto(orderManagement);
//        웹소켓 브로드캐스트
        webSoketMessageHandler.broadcastOrderUpdate(orderManagementDto1);
        return ResponseEntity.ok(RsData.of("S-1", "주문 생성 성공", orderManagementDto1));
    }

//    주문 상태 업데이트 하는 구문
    @PutMapping("/{id}")
    public ResponseEntity<RsData<OrderManagementDto>> updateOrderStatus(@PathVariable("id") Long id, @RequestBody OrderUpdateRequest orderUpdateRequest) {
        orderUpdateRequest.setId(id);
        orderManagementService.updateOrderStatus(orderUpdateRequest);
        OrderManagement orderManagement = orderManagementRepository.findById(id).orElse(null);
        OrderManagementDto orderManagementDto = DtoConverter.convertToDto(orderManagement);
        webSoketMessageHandler.broadcastOrderUpdate(orderManagementDto);
        return ResponseEntity.ok(RsData.of("S-1", "주문 상태 수정 성공", orderManagementDto));
    }
}