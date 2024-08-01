package com.example.windowPos.setting.entity;

import com.example.windowPos.global.baseentity.BaseEntity;
import com.example.windowPos.setting.settingEnum.DayOfTheWeek;
import com.example.windowPos.setting.settingEnum.RegularClosedDays;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class RegularHoliday extends BaseEntity {

    //    무슨 요일인지
    @Enumerated(EnumType.STRING)
    private DayOfTheWeek dayOfTheWeek = DayOfTheWeek.MONDAY;

    //    매 주 언제 정기휴무인지 ex) 첫째 주 / 둘째 주 / 매 주
    @Enumerated(EnumType.STRING)
    private RegularClosedDays regularClosedDays = RegularClosedDays.FIRST;

    @ManyToOne
    @JoinColumn(name = "closed_days_id")
    @JsonBackReference
    private ClosedDays closedDays;

    public void setClosedDays(ClosedDays closedDays) {
        this.closedDays = closedDays;
    }
}
