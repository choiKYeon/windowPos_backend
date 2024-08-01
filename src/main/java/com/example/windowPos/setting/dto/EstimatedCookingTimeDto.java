package com.example.windowPos.setting.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstimatedCookingTimeDto {

    private Long id;

    //    예상 조리시간 자동 설정
    private Boolean estimatedCookingTimeControl = false;

    //    예상 조리시간
    private Integer estimatedCookingTime;
}
