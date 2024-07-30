package com.example.windowPos.orderManagement.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesPauseDto {

    private Long id;

    //    영업 상태
    private String salesStatus;

    //    영업 일시 정지 시간
    private LocalDateTime salesPauseTime;
}
