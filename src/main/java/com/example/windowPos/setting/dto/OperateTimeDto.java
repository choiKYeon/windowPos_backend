package com.example.windowPos.setting.dto;

import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperateTimeDto {

    private Long id;

    //    평일 24시간 영업
    private Boolean weekdayAllDay = false;

    //    평일 시작 시간
    private LocalTime weekdayStartTime;

    //    평일 종료 시간
    private LocalTime weekdayEndTime;

    //    토요일 24시간 영업
    private Boolean saturdayAllDay = false;

    //    토요일 시작 시간
    private LocalTime saturdayStartTime;

    //    토요일 종료 시간
    private LocalTime saturdayEndTime;

    //    일요일 24시간 영업
    private Boolean SundayAllDay = false;

    //    일요일 시작 시간
    private LocalTime SundayStartTime;

    //    일요일 종료 시간
    private LocalTime SundayEndTime;

}
