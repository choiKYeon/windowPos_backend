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

    public void setWeekdayAllDay(Boolean weekdayAllDay) {
        this.weekdayAllDay = weekdayAllDay;
    }

    //    평일 시작 시간
    private LocalTime weekdayStartTime = LocalTime.now().withHour(0).withMinute(0);

    public void setWeekdayStartTime(LocalTime weekdayStartTime) {
        this.weekdayStartTime = weekdayStartTime;
    }

    //    평일 종료 시간
    private LocalTime weekdayEndTime = LocalTime.now().withHour(23).withMinute(59);

    public void setWeekdayEndTime(LocalTime weekdayEndTime) {
        this.weekdayEndTime = weekdayEndTime;
    }

    //    토요일 24시간 영업
    private Boolean saturdayAllDay = false;

    public void setSaturdayAllDay(Boolean saturdayAllDay) {
        this.saturdayAllDay = saturdayAllDay;
    }

    //    토요일 시작 시간
    private LocalTime saturdayStartTime = LocalTime.now().withHour(0).withMinute(0);

    public void setSaturdayStartTime(LocalTime saturdayStartTime) {
        this.saturdayStartTime = saturdayStartTime;
    }

    //    토요일 종료 시간
    private LocalTime saturdayEndTime = LocalTime.now().withHour(23).withMinute(59);

    public void setSaturdayEndTime(LocalTime saturdayEndTime) {
        this.saturdayEndTime = saturdayEndTime;
    }

    //    일요일 24시간 영업
    private Boolean SundayAllDay = false;

    public void setSundayAllDay(Boolean sundayAllDay) {
        this.SundayAllDay = sundayAllDay;
    }

    //    일요일 시작 시간
    private LocalTime SundayStartTime = LocalTime.now().withHour(0).withMinute(0);

    public void setSundayStartTime(LocalTime SundayStartTime) {
        this.SundayStartTime = SundayStartTime;
    }

    //    일요일 종료 시간
    private LocalTime SundayEndTime = LocalTime.now().withHour(23).withMinute(59);

    public void setSundayEndTime(LocalTime SundayEndTime) {
        this.SundayEndTime = SundayEndTime;

    }

    @OneToOne
    @JoinColumn(name = "setting_id")
    private Setting setting;

    public void setSetting(Setting setting) {
        this.setting = setting;
    }
}
