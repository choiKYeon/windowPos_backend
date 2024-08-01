package com.example.windowPos.setting.entity;

import com.example.windowPos.global.baseentity.BaseEntity;
import com.example.windowPos.orderManagement.entity.Menu;
import com.example.windowPos.setting.settingEnum.DayOfTheWeek;
import com.example.windowPos.setting.settingEnum.RegularClosedDays;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class ClosedDays extends BaseEntity {

    //    임시 휴무일
    @OneToMany(mappedBy = "closedDays", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<TemporaryHoliday> temporaryHolidayList = new ArrayList<>();

    public void setTemporaryHolidayList(List<TemporaryHoliday> temporaryHolidayList) {
        this.temporaryHolidayList = temporaryHolidayList;
        if (temporaryHolidayList != null) {
            for (TemporaryHoliday temporaryHoliday : temporaryHolidayList) {
                temporaryHoliday.setClosedDays(this);
            }
        }
    }

    //    정기 휴무일
    @OneToMany(mappedBy = "closedDays", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RegularHoliday> regularHolidayList = new ArrayList<>();

    public void setRegularHolidayList(List<RegularHoliday> regularHolidayList) {
        this.regularHolidayList = regularHolidayList;
        if (regularHolidayList != null) {
            for (RegularHoliday regularHoliday : regularHolidayList) {
                regularHoliday.setClosedDays(this);
            }
        }
    }

    @OneToOne
    @JoinColumn(name = "setting_id")
    private Setting setting;
}
