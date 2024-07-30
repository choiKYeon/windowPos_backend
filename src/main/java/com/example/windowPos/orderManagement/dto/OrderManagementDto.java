package com.example.windowPos.orderManagement.dto;

import com.example.windowPos.orderManagement.entity.SalesPause;
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

    //    주문 상태
    private String orderStatus;

    //    영업 중단
    private SalesPause salesPause;

    //    메뉴 리스트
    private List<MenuDto> menuDtoList;
}
