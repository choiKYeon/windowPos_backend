package com.example.windowPos.setting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperatePauseDto {

    private Long id;

    //    영업 상태
    private String operateStatus;

    // 영업 일시 정지 시작 시간
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime salesPauseStartTime = LocalTime.now();

    // 영업 일시 정지 종료 시간
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime salesPauseEndTime;

    //    영업 일시 정지 지속 시간
    private Integer durationMinutes;
}
