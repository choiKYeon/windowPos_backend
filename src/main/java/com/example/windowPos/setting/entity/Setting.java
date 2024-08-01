package com.example.windowPos.setting.entity;

import com.example.windowPos.global.baseentity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Setting extends BaseEntity {

    //    영업 중단
    @OneToOne(mappedBy = "setting", cascade = CascadeType.ALL)
    private OperatePause operatePause;

    //    영업 시간
    @OneToOne(mappedBy = "setting", cascade = CascadeType.ALL)
    private OperateTime operateTime;

    //    조리 예상 시간
    @OneToOne(mappedBy = "setting", cascade = CascadeType.ALL)
    private EstimatedCookingTime estimatedCookingTime;

    //    도착 예상 시간
    @OneToOne(mappedBy = "setting", cascade = CascadeType.ALL)
    private EstimatedArrivalTime estimatedArrivalTime;

    //    휴무일
    @OneToOne(mappedBy = "setting", cascade = CascadeType.ALL)
    private ClosedDays closedDays;

    //    브레이크 타임
    @OneToOne(mappedBy = "setting", cascade = CascadeType.ALL)
    private BreakTime breakTime;
}
