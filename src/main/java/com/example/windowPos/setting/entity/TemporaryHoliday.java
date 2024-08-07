package com.example.windowPos.setting.entity;

import com.example.windowPos.global.baseentity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class TemporaryHoliday extends BaseEntity {

    //    임시 휴무 시작 날짜
    private LocalDate temporaryHolidayStartDate = LocalDate.now();

    public void setTemporaryHolidayStartDate(LocalDate temporaryHolidayStartDate) {
        this.temporaryHolidayStartDate = temporaryHolidayStartDate;
    }

    //    임시 휴무 종료 날짜
    private LocalDate temporaryHolidayEndDate = LocalDate.now().plusDays(1);

    public void setTemporaryHolidayEndDate(LocalDate temporaryHolidayEndDate) {
        this.temporaryHolidayEndDate = temporaryHolidayEndDate;
    }


    @ManyToOne
    @JoinColumn(name = "closed_days_id")
    @JsonBackReference
    private ClosedDays closedDays;

    public void setClosedDays(ClosedDays closedDays) {
        this.closedDays = closedDays;
    }
}
