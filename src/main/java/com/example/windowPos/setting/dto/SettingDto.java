package com.example.windowPos.setting.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SettingDto {

    private Long id;

//    영업 중단
    private OperatePauseDto operatePauseDto;

//    영업 시간
    private OperateTimeDto operateTimeDto;

//    조리 예상 시간
    private EstimatedCookingTimeDto estimatedCookingTimeDto;

//    도착 예상 시간
    private EstimatedArrivalTimeDto estimatedArrivalTimeDto;

//    휴무일
    private ClosedDaysDto closedDaysDto;

//    브레이크 타임
    private BreakTimeDto breakTimeDto;

//    영업 상태
    private String operateStatus;

    private Long memberId;
}
