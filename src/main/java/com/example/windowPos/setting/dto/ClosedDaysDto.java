package com.example.windowPos.setting.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClosedDaysDto {

    private Long id;

//    임시 휴무일
    private List<TemporaryHolidayDto> temporaryHolidayDates;

//    정기 휴무일
    private List<RegularHolidayDto> regularHolidayList;
}
