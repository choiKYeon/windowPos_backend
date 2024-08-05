package com.example.windowPos.setting.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemporaryHolidayDto {

    private Long id;

    //    임시 휴무 시작 날짜
    private LocalDate temporaryHolidayStartDate;

    //    임시 휴무 종료 날짜
    private LocalDate temporaryHolidayEndDate;

}
