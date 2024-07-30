package com.example.windowPos.orderManagement.dto;

import com.example.windowPos.orderManagement.entity.SalesPause;
import com.example.windowPos.orderManagement.orderStatus.OrderStatus;
import com.example.windowPos.orderManagement.orderType.OrderType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderManagementDto {

    private Long id;

    //    주문 시간
    private LocalDateTime orderTime;

    //    요청사항
    private String request;

    //    고객 주소
    private String address;

    //    총 금액
    private Long totalPrice;

    //    주문 번호
    private String orderNumber;

    //    주문 상태관리
    private OrderStatus orderStatus;

    //    포장인지 배달인지 주문 타입
    private OrderType orderType;

    //    주문 거절 사유
    private String rejectionReason;

    //    예상 조리시간
    private Integer estimatedCookingTime;

    //    영업 중단
    private SalesPause salesPause;

    //    메뉴 리스트
    private List<MenuDto> menuDtoList;
}
