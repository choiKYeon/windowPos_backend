package com.example.windowPos.setting.entity;

import com.example.windowPos.global.baseentity.BaseEntity;
import com.example.windowPos.setting.settingEnum.OperateStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
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
public class OperatePause extends BaseEntity {

    //    영업 상태
    @Enumerated(EnumType.STRING)
    private OperateStatus operateStatus = OperateStatus.END;

    public void setOperateStatus(OperateStatus operateStatus) {
        this.operateStatus = operateStatus;
    }

    // 영업 일시 정지 시작 시간
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime salesPauseStartTime = LocalTime.now();

    public void setSalesPauseStartTime(LocalTime salesPauseStartTime) {
        this.salesPauseStartTime = salesPauseStartTime;
    }

    // 영업 일시 정지 종료 시간
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime salesPauseEndTime = LocalTime.of(0, 0);

    public void setSalesPauseEndTime(LocalTime salesPauseEndTime) {
        this.salesPauseEndTime = salesPauseEndTime;
    }

    @OneToOne
    @JoinColumn(name = "setting_id")
    private Setting setting;

    public void setSetting(Setting setting) {
        this.setting = setting;
    }
}
