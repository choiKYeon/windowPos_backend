package com.example.windowPos.setting.entity;

import com.example.windowPos.global.baseentity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class OperateTime extends BaseEntity {

    //    평일 24시간 영업
    private Boolean weekdayAllDay = false;

    //    평일 시작 시간
    private LocalDateTime weekdayStartTime;

    //    평일 종료 시간
    private LocalDateTime weekdayEndTime;

    //    토요일 24시간 영업
    private Boolean saturdayAllDay = false;

    //    토요일 시작 시간
    private LocalDateTime saturdayStartTime;

    //    토요일 종료 시간
    private LocalDateTime saturdayEndTime;

    //    일요일 24시간 영업
    private Boolean SundayAllDay = false;

    //    일요일 시작 시간
    private LocalDateTime SundayStartTime;

    //    일요일 종료 시간
    private LocalDateTime SundayEndTime;

    @OneToOne
    @JoinColumn(name = "setting_id")
    private Setting setting;
}
