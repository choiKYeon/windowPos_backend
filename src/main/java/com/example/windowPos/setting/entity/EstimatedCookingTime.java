package com.example.windowPos.setting.entity;

import com.example.windowPos.global.baseentity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
public class EstimatedCookingTime extends BaseEntity {

    //    예상 조리시간 자동 설정
    private Boolean estimatedCookingTimeControl = false;

    //    예상 조리시간
    private Integer estimatedCookingTime;

    @OneToOne
    @JoinColumn(name = "setting_id")
    private Setting setting;
}
