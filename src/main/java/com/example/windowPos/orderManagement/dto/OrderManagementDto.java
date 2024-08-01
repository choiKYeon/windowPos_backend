package com.example.windowPos.orderManagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderManagementDto {

    private Long id;

    //    주문 시간
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime orderTime;

    //    요청사항
    private String request;

    //    고객 주소
    private String address;

    //    메뉴 총 금액
    private Long menuTotalPrice;

    //    총 금액
    private Long totalPrice;

    //    수저포크
    private Boolean spoonFork;

    //    배달비
    private Long deliveryFee;

    //    주문 번호
    private Long orderNumber;

    //    주문 상태관리
    private String orderStatus;

    //    포장인지 배달인지 주문 타입
    private String orderType;

    //    주문 업데이트 상태
    private OrderUpdateRequest orderUpdateRequest;

    //    메뉴 리스트
    private List<MenuDto> menuList = new ArrayList<>();
}
