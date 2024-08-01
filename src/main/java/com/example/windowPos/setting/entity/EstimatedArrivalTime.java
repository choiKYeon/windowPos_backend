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
public class EstimatedArrivalTime extends BaseEntity {

    //    예상 도착시간 자동 설정
    private Boolean estimatedArrivalTimeControl= false;

    public void setEstimatedArrivalTimeControl(Boolean estimatedArrivalTimeControl) {
        this.estimatedArrivalTimeControl = estimatedArrivalTimeControl;
    }

    //    예상 도착시간
    private Integer estimatedArrivalTime = 40;

    public void setEstimatedArrivalTime(Integer estimatedArrivalTime) {
        this.estimatedArrivalTime = estimatedArrivalTime;
    }

    @OneToOne
    @JoinColumn(name = "setting_id")
    private Setting setting;

    public void setSetting(Setting setting) {
        this.setting = setting;
    }
}
