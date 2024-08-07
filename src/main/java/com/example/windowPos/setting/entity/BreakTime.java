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
public class BreakTime extends BaseEntity {

//    브레이크 타임 시작 시간
    private LocalTime breakTimeStart = LocalTime.of(0, 0);

    public void setBreakTimeStart(LocalTime breakTimeStart) {
        this.breakTimeStart = breakTimeStart;
    }

//    브레이크 타임 종료 시간
    private LocalTime breakTimeEnd = LocalTime.of(0, 0);

    public void setBreakTimeEnd(LocalTime breakTimeEnd) {
        this.breakTimeEnd = breakTimeEnd;
    }

//    // 영업 상태
//    @OneToOne
//    @JoinColumn(name = "operate_status_id")
//    private OperateStatusEntity operateStatusEntity;
//
//    public void setOperateStatusEntity(OperateStatusEntity operateStatusEntity) {
//        this.operateStatusEntity = operateStatusEntity;
//    }

    @OneToOne
    @JoinColumn(name = "setting_id")
    private Setting setting;

    public void setSetting(Setting setting) {
        this.setting = setting;
    }
}
