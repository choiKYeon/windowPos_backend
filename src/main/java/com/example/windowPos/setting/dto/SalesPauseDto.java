package com.example.windowPos.setting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    // 영업 일시 정지 시작 시간
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime salesPauseStartTime;

    // 영업 일시 정지 종료 시간
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime salesPauseEndTime;
}