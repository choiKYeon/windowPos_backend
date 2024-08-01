package com.example.windowPos.setting.entity;

import com.example.windowPos.global.baseentity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class OperateTime extends BaseEntity {

    //    평일 24시간 영업
    private Boolean weekdayAllDay = false;

    //    평일 시작 시간
    private LocalTime weekdayStartTime = LocalTime.now().withHour(0).withMinute(0);

    //    평일 종료 시간
    private LocalTime weekdayEndTime = LocalTime.now().withHour(23).withMinute(59);

    //    토요일 24시간 영업
    private Boolean saturdayAllDay = false;

    //    토요일 시작 시간
    private LocalTime saturdayStartTime = LocalTime.now().withHour(0).withMinute(0);

    //    토요일 종료 시간
    private LocalTime saturdayEndTime = LocalTime.now().withHour(23).withMinute(59);

    //    일요일 24시간 영업
    private Boolean SundayAllDay = false;

    //    일요일 시작 시간
    private LocalTime SundayStartTime = LocalTime.now().withHour(0).withMinute(0);

    //    일요일 종료 시간
    private LocalTime SundayEndTime = LocalTime.now().withHour(23).withMinute(59);

    @OneToOne
    @JoinColumn(name = "setting_id")
    private Setting setting;
}
