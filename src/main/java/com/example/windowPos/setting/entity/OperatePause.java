package com.example.windowPos.setting.entity;

import com.example.windowPos.global.baseentity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class OperatePause extends BaseEntity {

    // 영업 일시 정지 시작 시간
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime salesPauseStartTime = LocalTime.of(0, 0);

    public void setSalesPauseStartTime(LocalTime salesPauseStartTime) {
        this.salesPauseStartTime = salesPauseStartTime;
    }

    // 영업 일시 정지 종료 시간
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime salesPauseEndTime = LocalTime.of(0, 0);

    public void setSalesPauseEndTime(LocalTime salesPauseEndTime) {
        this.salesPauseEndTime = salesPauseEndTime;
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
