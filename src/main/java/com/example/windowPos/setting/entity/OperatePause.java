package com.example.windowPos.setting.entity;

import com.example.windowPos.global.baseentity.BaseEntity;
import com.example.windowPos.setting.settingEnum.OperateStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class OperatePause extends BaseEntity {

    //    영업 상태
    @Enumerated(EnumType.STRING)
    private OperateStatus operateStatus;

    // 영업 일시 정지 시작 시간
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime salesPauseStartTime = LocalTime.now();

    // 영업 일시 정지 종료 시간
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime salesPauseEndTime;

    @OneToOne
    @JoinColumn(name = "setting_id")
    private Setting setting;
}
