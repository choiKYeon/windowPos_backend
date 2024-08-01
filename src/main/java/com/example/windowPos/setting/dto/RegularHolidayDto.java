package com.example.windowPos.setting.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegularHolidayDto {

    private Long id;

    //    무슨 요일인지
    private String dayOfTheWeek;

    //    매 주 언제 정기휴무인지 ex) 첫째 주 / 둘째 주 / 매 주
    private String regularClosedDays;
}
