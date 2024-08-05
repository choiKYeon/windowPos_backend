package com.example.windowPos.setting.entity;

import com.example.windowPos.global.baseentity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class OperateStatus extends BaseEntity {

    //    영업 상태
    @Enumerated(EnumType.STRING)
    private com.example.windowPos.setting.settingEnum.OperateStatus operateStatus;

    public void setOperateStatus(com.example.windowPos.setting.settingEnum.OperateStatus operateStatus) {
        this.operateStatus = operateStatus;
    }
}
