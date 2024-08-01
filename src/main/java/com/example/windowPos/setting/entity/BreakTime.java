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

//    브레이크 타임 종료 시간
    private LocalTime breakTimeEnd = LocalTime.of(0, 0);

    @OneToOne
    @JoinColumn(name = "setting_id")
    private Setting setting;
}
