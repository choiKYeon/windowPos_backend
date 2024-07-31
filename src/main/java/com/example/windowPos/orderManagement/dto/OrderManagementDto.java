package com.example.windowPos.orderManagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime orderTime;

    //    요청사항
    private String request;

    //    고객 주소
    private String address;

    //    총 금액
    private Long totalPrice;

    //    주문 번호
    private Long orderNumber;

    //    주문 상태관리
    private String orderStatus;

    //    포장인지 배달인지 주문 타입
    private String orderType;

    //    주문 거절 사유
    private String rejectionReason;

    //    예상 조리시간
    private Integer estimatedCookingTime;

    //    도착 예정 시간
    private Integer estimatedArrivalTime;

    //    영업 중단
    private SalesPauseDto salesPause;

    //    메뉴 리스트
    private List<MenuDto> menuList;
}
