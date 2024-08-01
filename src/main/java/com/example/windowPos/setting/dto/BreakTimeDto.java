package com.example.windowPos.setting.dto;

import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BreakTimeDto {

    private Long id;

    //    브레이크 타임 시작 시간
    private LocalTime breakTimeStart;

    //    브레이크 타임 종료 시간
    private LocalTime breakTimeEnd;
}
