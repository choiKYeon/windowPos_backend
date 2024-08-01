package com.example.windowPos.setting.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstimatedArrivalTimeDto {

    private Long id;

    //    예상 도착시간 자동 설정
    private Boolean estimatedArrivalTimeControl = false;

    //    예상 도착시간
    private Integer estimatedArrivalTime;
}
