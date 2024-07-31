package com.example.windowPos.orderManagement.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderUpdateRequest {
    private Long id;
//    주문 상태
    private String orderStatus;
//    주문 거절 이유
    private String rejectionReason;
//    조리 예상 시간
    private Integer estimatedCookingTime;
//    주문 도착 시간
    private Integer estimatedArrivalTime;

}
